import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import traffic.TrafficLightServiceGrpc;
import traffic.Traffic.TrafficCommand;
import traffic.Traffic.TrafficState;

import java.io.IOException;

public class TrafficLightServer {
    private Server server;

    /**
     * Starts the gRPC server.
     *
     * @throws IOException if there is an error starting the server.
     */
    private void start() throws IOException {
        int port = 50051;
        // Create the server and start it, listening on the specified port.
        server = ServerBuilder.forPort(port)
                .addService(new TrafficLightServiceImpl())
                .build()
                .start();
        System.out.println("Server started, listening on " + port);
        // Add a shutdown hook to ensure clean shutdown handling.
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            TrafficLightServer.this.stop();
            System.err.println("*** server shut down");
        }));
    }

    /**
     * Stops the server.
     */
    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    /**
     * Blocks until server shutdown is complete.
     *
     * @throws InterruptedException if the thread is interrupted while waiting.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    /**
     * Implements the Traffic Light Service defined in the gRPC interface.
     */
    static class TrafficLightServiceImpl extends TrafficLightServiceGrpc.TrafficLightServiceImplBase {
        /**
         * Handles bidirectional streaming of traffic light commands and states.
         *
         * @param responseObserver StreamObserver for sending state responses back to the client.
         * @return StreamObserver to receive commands from the client.
         */
        @Override
        public StreamObserver<TrafficCommand> manageTraffic(StreamObserver<TrafficState> responseObserver) {
            return new StreamObserver<TrafficCommand>() {
                /**
                 * Receives a traffic command and processes it.
                 * @param command The traffic command from the client.
                 */
                @Override
                public void onNext(TrafficCommand command) {
                    // Determine new state based on the command received.
                    TrafficState.State newState = command.getCommand() == TrafficCommand.Command.TURN_RED ?
                            TrafficState.State.RED : TrafficState.State.GREEN;

                    // Create and send a new state back to the client.
                    TrafficState state = TrafficState.newBuilder()
                            .setIntersectionId(command.getIntersectionId())
                            .setCurrentState(newState)
                            .build();
                    responseObserver.onNext(state);
                }

                /**
                 * Handles errors in the stream.
                 * @param t The throwable error encountered.
                 */
                @Override
                public void onError(Throwable t) {
                    System.err.println("Error in manageTraffic: " + t.getMessage());
                }

                /**
                 * Completes the stream once all commands have been processed.
                 */
                @Override
                public void onCompleted() {
                    responseObserver.onCompleted();
                }
            };
        }
    }

    /**
     * Main method to start the server and handle shutdown appropriately.
     *
     * @param args Command line arguments.
     * @throws IOException          If there is an issue starting the server.
     * @throws InterruptedException If the server is interrupted during its execution.
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        final TrafficLightServer server = new TrafficLightServer();
        server.start();
        server.blockUntilShutdown();
    }
}
