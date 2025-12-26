import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import traffic.TrafficLightServiceGrpc;
import traffic.Traffic.TrafficCommand;
import traffic.Traffic.TrafficState;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class TrafficLightServer {
    private Server server;

    void start() throws IOException {
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

    void stop() {
        if (server != null) {
            server.shutdown();
            try {
                if (!server.awaitTermination(5, TimeUnit.SECONDS)) {
                    server.shutdownNow();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                server.shutdownNow();
            }
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
                    if (command.getCommand() != TrafficCommand.Command.END_STREAM) {
                        TrafficState state = TrafficState.newBuilder()
                                .setIntersectionId(command.getIntersectionId())
                                .setCurrentState(convertCommandToState(command.getCommand()))
                                .build();
                        responseObserver.onNext(state);
                    } else {
                        // If an END_STREAM command is received, complete the stream
                        responseObserver.onCompleted();
                    }
                }

                @Override
                public void onError(Throwable t) {
                    System.err.println("Error in manageTraffic: " + t);
                    responseObserver.onCompleted();
                }

                @Override
                public void onCompleted() {
                    // Complete the client side stream and cleanup
                    responseObserver.onCompleted();
                }
            };
        }

        private TrafficState.State convertCommandToState(TrafficCommand.Command command) {
            switch (command) {
                case TURN_GREEN:
                    return TrafficState.State.GREEN;
                case TURN_RED:
                    return TrafficState.State.RED;
                default:
                    return TrafficState.State.UNKNOWN;
            }
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final TrafficLightServer server = new TrafficLightServer();
        server.start();
        server.blockUntilShutdown();
    }
}
