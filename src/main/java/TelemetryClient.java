import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import vehicle_telemetry.VehicleTelemetryServiceGrpc;
import vehicle_telemetry.VehicleTelemetry.TelemetryData;
import vehicle_telemetry.VehicleTelemetry.TelemetryResponse;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class TelemetryClient {
    private final VehicleTelemetryServiceGrpc.VehicleTelemetryServiceStub asyncStub;

    public TelemetryClient(ManagedChannel channel) {
        asyncStub = VehicleTelemetryServiceGrpc.newStub(channel);
    }

    public void sendTelemetryData(String vehicleId) throws InterruptedException {
        CountDownLatch finishLatch = new CountDownLatch(1);
        StreamObserver<TelemetryData> requestObserver = asyncStub.streamTelemetry(new StreamObserver<TelemetryResponse>() {
            @Override
            public void onNext(TelemetryResponse response) {
                System.out.println("Server response: " + response.getMessage());
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Telemetry streaming failed: " + t);
                finishLatch.countDown();
            }

            @Override
            public void onCompleted() {
                System.out.println("Finished sending telemetry data");
                finishLatch.countDown();
            }
        });

        try {
            // Simulate sending telemetry data
            for (int i = 0; i < 10; i++) {
                TelemetryData data = TelemetryData.newBuilder()
                        .setVehicleId(vehicleId)
                        .setLatitude(34.0522)
                        .setLongitude(-118.2437)
                        .setSpeed(60 + i)
                        .setEngineTemp(100 + i)
                        .setFuelLevel(50 - i * 5)
                        .setTimestamp(System.currentTimeMillis())
                        .build();
                requestObserver.onNext(data);
                Thread.sleep(1000); // Sleep to simulate delay between data points
            }
        } catch (RuntimeException e) {
            requestObserver.onError(e);
            throw e;
        } finally {
            requestObserver.onCompleted();
            finishLatch.await(1, TimeUnit.MINUTES);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50054)
                .usePlaintext()
                .build();
        try {
            TelemetryClient client = new TelemetryClient(channel);
            client.sendTelemetryData("Toyota");
        } finally {
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}
