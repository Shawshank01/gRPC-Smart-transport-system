import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import traffic.Traffic.TrafficLightState;
import traffic.Traffic.ChangeLightRequest;
import traffic.Traffic.ChangeLightResponse;
import traffic.Traffic.Empty;
import traffic.TrafficLightServiceGrpc;

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
        public void changeLightState(ChangeLightRequest req, StreamObserver<ChangeLightResponse> responseObserver) {
            TrafficLightState state = req.getState();
            System.out.println("Changing light to: " + state.getCurrentState());
            ChangeLightResponse response = ChangeLightResponse.newBuilder()
                    .setSuccess(true)
                    .setMessage("Traffic light changed to: " + state.getCurrentState())
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

        @Override
        public void getCurrentState(Empty request, StreamObserver<TrafficLightState> responseObserver) {
            TrafficLightState state = TrafficLightState.newBuilder()
                    .setCurrentState(TrafficLightState.State.RED) // Default or track the current state
                    .build();
            responseObserver.onNext(state);
            responseObserver.onCompleted();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final TrafficLightServer server = new TrafficLightServer();
        server.start();
        server.blockUntilShutdown();
    }
}
