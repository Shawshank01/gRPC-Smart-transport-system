import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import vehicle_telemetry.VehicleTelemetryServiceGrpc;
import vehicle_telemetry.VehicleTelemetry.TelemetryData;
import vehicle_telemetry.VehicleTelemetry.TelemetryResponse;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class TelemetryClient {
    // Stub that facilitates asynchronous communication with the server.
    private final VehicleTelemetryServiceGrpc.VehicleTelemetryServiceStub asyncStub;

    /**
     * Constructs the TelemetryClient.
     *
     * @param channel The channel over which to communicate with the server.
     */
    public TelemetryClient(ManagedChannel channel) {
        asyncStub = VehicleTelemetryServiceGrpc.newStub(channel);
    }

    /**
     * Sends telemetry data to the server in a streaming fashion.
     *
     * @param vehicleId The identifier for the vehicle sending data.
     * @throws InterruptedException if the thread is interrupted while waiting for the stream to complete.
     */
    public void sendTelemetryData(String vehicleId) throws InterruptedException {
        // A latch to wait for the stream to complete.
        CountDownLatch finishLatch = new CountDownLatch(1);
        // Begin a client-side stream to the server.
        StreamObserver<TelemetryData> requestObserver = asyncStub.streamTelemetry(new StreamObserver<TelemetryResponse>() {
            @Override
            public void onNext(TelemetryResponse response) {
                // Handle responses received from the server.
                System.out.println("Server response: " + response.getMessage());
            }

            @Override
            public void onError(Throwable t) {
                // Handle errors encountered during the stream.
                System.err.println("Telemetry streaming failed: " + t);
                finishLatch.countDown();
            }

            @Override
            public void onCompleted() {
                // Complete the client side stream and print a confirmation.
                System.out.println("Finished sending telemetry data");
                finishLatch.countDown();
            }
        });

        try {
            // Loop to simulate sending telemetry data.
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
                Thread.sleep(1000); // Pause to simulate time between data points.
            }
        } catch (RuntimeException e) {
            // Inform the server that an error has occurred.
            requestObserver.onError(e);
            throw e;
        } finally {
            // Complete the stream.
            requestObserver.onCompleted();
            // Wait for the server to confirm the stream has finished.
            finishLatch.await(1, TimeUnit.MINUTES);
        }
    }

    /**
     * Main method to run the telemetry client.
     *
     * @param args Command line arguments.
     * @throws InterruptedException if the thread is interrupted during operation.
     */
    public static void main(String[] args) throws InterruptedException {
        // Build the channel to communicate with the server.
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50054)
                .usePlaintext()  // Note: insecure for production environments.
                .build();
        try {
            // Create the client and send telemetry data.
            TelemetryClient client = new TelemetryClient(channel);
            client.sendTelemetryData("Toyota");
        } finally {
            // Ensure the channel is shutdown cleanly.
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}
