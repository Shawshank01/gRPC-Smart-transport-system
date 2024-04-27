import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import parking.ParkingServiceGrpc;
import parking.Parking.CheckAvailabilityRequest;
import parking.Parking.CheckAvailabilityResponse;
import parking.Parking.ReserveSpotRequest;
import parking.Parking.ReserveSpotResponse;

public class ParkingClient {
    private final ParkingServiceGrpc.ParkingServiceBlockingStub blockingStub;

    public ParkingClient(ManagedChannel channel) {
        blockingStub = ParkingServiceGrpc.newBlockingStub(channel);
    }

    public void checkAvailability(String locationId) {
        CheckAvailabilityRequest request = CheckAvailabilityRequest.newBuilder()
                .setLocationId(locationId)
                .build();
        CheckAvailabilityResponse response = blockingStub.checkAvailability(request);
        System.out.println("Response from server: " + response.getMessage());
    }

    public void reserveSpot(String locationId, String userId) {
        ReserveSpotRequest request = ReserveSpotRequest.newBuilder()
                .setLocationId(locationId)
                .setUserId(userId)
                .build();
        ReserveSpotResponse response = blockingStub.reserveSpot(request);
        System.out.println("Response from server: " + response.getMessage());
    }

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50052)
                .usePlaintext()
                .build();
        try {
            ParkingClient client = new ParkingClient(channel);
            client.checkAvailability("location1");
            client.reserveSpot("location1", "user123");
        } finally {
            channel.shutdown();
        }
    }
}
