import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import vehicle.VehicleServiceGrpc;
import vehicle.Vehicle.EventRequest;
import vehicle.Vehicle.EventNotification;

import java.util.concurrent.TimeUnit;

public class VehicleClient {
    private final VehicleServiceGrpc.VehicleServiceStub asyncStub;

    public VehicleClient(ManagedChannel channel) {
        asyncStub = VehicleServiceGrpc.newStub(channel);
    }

    public void subscribeToEvents(String vehicleId) {
        EventRequest request = EventRequest.newBuilder().setVehicleId(vehicleId).build();
        asyncStub.streamEvents(request, new StreamObserver<EventNotification>() {
            @Override
            public void onNext(EventNotification notification) {
                System.out.println("Received notification: " + notification.getMessage());
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Stream Error: " + t);
            }

            @Override
            public void onCompleted() {
                System.out.println("Stream completed");
            }
        });
    }

    public static void main(String[] args) throws Exception {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50053)
                .usePlaintext()
                .build();
        try {
            VehicleClient client = new VehicleClient(channel);
            client.subscribeToEvents("vehicle123");
            // Wait for the server to finish transmitting
            Thread.sleep(15000);
        } finally {
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}
