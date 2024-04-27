import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import vehicle_telemetry.VehicleTelemetryServiceGrpc;
import vehicle_telemetry.VehicleTelemetry.TelemetryData;
import vehicle_telemetry.VehicleTelemetry.TelemetryResponse;
import java.io.IOException;

public class TelemetryServer {
    private Server server;

    private void start() throws IOException {
        int port = 50054;
        server = ServerBuilder.forPort(port)
                .addService(new VehicleTelemetryServiceImpl())
                .build()
                .start();
        System.out.println("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            TelemetryServer.this.stop();
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

    static class VehicleTelemetryServiceImpl extends VehicleTelemetryServiceGrpc.VehicleTelemetryServiceImplBase {
        @Override
        public StreamObserver<TelemetryData> streamTelemetry(StreamObserver<TelemetryResponse> responseObserver) {
            return new StreamObserver<TelemetryData>() {
                @Override
                public void onNext(TelemetryData data) {
                    System.out.println("Received telemetry data from vehicle " + data.getVehicleId() + " at " + data.getTimestamp());
                }

                @Override
                public void onError(Throwable t) {
                    System.err.println("Telemetry streaming error: " + t);
                }

                @Override
                public void onCompleted() {
                    responseObserver.onNext(TelemetryResponse.newBuilder()
                            .setSuccess(true)
                            .setMessage("All telemetry data processed successfully.")
                            .build());
                    responseObserver.onCompleted();
                }
            };
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final TelemetryServer server = new TelemetryServer();
        server.start();
        server.blockUntilShutdown();
    }
}
