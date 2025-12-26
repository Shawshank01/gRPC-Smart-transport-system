import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import vehicle.VehicleServiceGrpc;
import vehicle.Vehicle.EventRequest;
import vehicle.Vehicle.EventNotification;

import java.util.concurrent.TimeUnit;

public class VehicleClient {
    // Asynchronous stub for non-blocking rpc calls.
    private final VehicleServiceGrpc.VehicleServiceStub asyncStub;

    /**
     * Constructor to build the vehicle client.
     *
     * @param channel The communication channel to the gRPC server.
     */
    public VehicleClient(ManagedChannel channel) {
        // Initializing asynchronous stub with the channel.
        asyncStub = VehicleServiceGrpc.newStub(channel);
    }

    public static void interactWithEventNotificationService(ManagedChannel channel) throws InterruptedException {
        VehicleClient client = new VehicleClient(channel);
        System.out.println("Subscribing to vehicle events...");
        client.subscribeToEvents("Toyota"); // Example vehicle ID, could be dynamic based on user input
        // Implement a wait strategy as necessary, e.g., await on a latch if needed
        Thread.sleep(15000); // Example wait, adjust as per the actual flow needed
    }

    /**
     * Subscribes to event notifications for a specific vehicle.
     *
     * @param vehicleId The identifier of the vehicle to receive events for.
     */
    public void subscribeToEvents(String vehicleId) {
        // Create an event request with the vehicle ID.
        EventRequest request = EventRequest.newBuilder().setVehicleId(vehicleId).build();
        // Async call to the server to stream events.
        asyncStub.streamEvents(request, new StreamObserver<EventNotification>() {
            @Override
            public void onNext(EventNotification notification) {
                // Handling each notification received from the server.
                System.out.println("Received notification: " + notification.getMessage());
            }

            @Override
            public void onError(Throwable t) {
                // Handling errors during the streaming.
                System.err.println("Stream Error: " + t);
            }

            @Override
            public void onCompleted() {
                // Notifying when the stream is completed.
                System.out.println("Stream completed");
            }
        });
    }

    /**
     * Main method to run the vehicle client.
     *
     * @param args Command line arguments.
     * @throws Exception if there is an interruption during the execution.
     */
    public static void main(String[] args) throws Exception {
        // Establishing connection to the server.
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50053)
                .usePlaintext() // Note: Insecure for production use, only suitable for development.
                .build();
        try {
            // Creating the client and subscribing to events for "vehicle123".
            VehicleClient client = new VehicleClient(channel);
            client.subscribeToEvents("Toyota");
            // Wait for the server to finish transmitting events.
            Thread.sleep(15000);
        } finally {
            // Ensure proper shutdown of the channel to free resources.
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}
