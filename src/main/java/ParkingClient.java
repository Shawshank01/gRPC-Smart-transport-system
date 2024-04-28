import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import parking.ParkingServiceGrpc;
import parking.Parking.CheckAvailabilityRequest;
import parking.Parking.CheckAvailabilityResponse;
import parking.Parking.ReserveSpotRequest;
import parking.Parking.ReserveSpotResponse;

public class ParkingClient {
    // Stub that allows us to create connections for server calls.
    private final ParkingServiceGrpc.ParkingServiceBlockingStub blockingStub;

    /**
     * Constructor to build the parking client.
     *
     * @param channel gRPC channel used to communicate with the server.
     */
    public ParkingClient(ManagedChannel channel) {
        // Initialize the stub on the provided channel.
        blockingStub = ParkingServiceGrpc.newBlockingStub(channel);
    }

    /**
     * Check the availability of parking spots at a given location.
     *
     * @param locationId the identifier of the location to check.
     */
    public void checkAvailability(String locationId) {
        // Create a request with the location ID.
        CheckAvailabilityRequest request = CheckAvailabilityRequest.newBuilder()
                .setLocationId(locationId)
                .build();
        // Execute the remote call to the server and get the response.
        CheckAvailabilityResponse response = blockingStub.checkAvailability(request);
        // Print the response from the server.
        System.out.println("Response from server: " + response.getMessage());
    }

    /**
     * Reserve a parking spot at a specific location for a user.
     *
     * @param locationId the identifier of the location where a spot is to be reserved.
     * @param userId     the identifier of the user reserving the spot.
     */
    public void reserveSpot(String locationId, String userId) {
        // Build the request with location and user ID.
        ReserveSpotRequest request = ReserveSpotRequest.newBuilder()
                .setLocationId(locationId)
                .setUserId(userId)
                .build();
        // Execute the remote call to reserve the spot and get the response.
        ReserveSpotResponse response = blockingStub.reserveSpot(request);
        // Print the server response.
        System.out.println("Response from server: " + response.getMessage());
    }

    /**
     * Main method to run the parking client operations.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        // Create a new channel to connect to the server at specified address and port.
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50052)
                .usePlaintext()  // Insecure channel, not for production use.
                .build();
        try {
            // Create a new client using the channel.
            ParkingClient client = new ParkingClient(channel);
            // Check parking availability at location "location1".
            client.checkAvailability("location1");
            // Reserve a parking spot at "location1" for user "user123".
            client.reserveSpot("location1", "user123");
        } finally {
            // Always shutdown the channel afterwards.
            channel.shutdown();
        }
    }
}
