import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import traffic.TrafficLightServiceGrpc;
import traffic.Traffic.TrafficCommand;
import traffic.Traffic.TrafficState;
import java.io.IOException;

public class TrafficLightServer {
    private Server server;

    private void start() throws IOException {
        int port = 50051;
        server = ServerBuilder.forPort(port)
                .addService(new TrafficLightServiceImpl())
                .build()
                .start();
        System.out.println("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            TrafficLightServer.this.stop();
            System.err.println("*** server shut down");
        }));
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    static class TrafficLightServiceImpl extends TrafficLightServiceGrpc.TrafficLightServiceImplBase {
        @Override
        public StreamObserver<TrafficCommand> manageTraffic(StreamObserver<TrafficState> responseObserver) {
            return new StreamObserver<TrafficCommand>() {
                @Override
                public void onNext(TrafficCommand command) {
                    // Handle each command
                    TrafficState.State newState = command.getCommand() == TrafficCommand.Command.TURN_RED ? TrafficState.State.RED : TrafficState.State.GREEN;
                    TrafficState state = TrafficState.newBuilder()
                            .setIntersectionId(command.getIntersectionId())
                            .setCurrentState(newState)
                            .build();
                    responseObserver.onNext(state);
                }

                @Override
                public void onError(Throwable t) {
                    System.err.println("Error in manageTraffic: " + t.getMessage());
                }

                @Override
                public void onCompleted() {
                    responseObserver.onCompleted();
                }
            };
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final TrafficLightServer server = new TrafficLightServer();
        server.start();
        server.blockUntilShutdown();
    }
}
