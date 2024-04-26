import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import traffic.Traffic.ChangeLightRequest;
import traffic.Traffic.ChangeLightResponse;
import traffic.Traffic.Empty;
import traffic.Traffic.TrafficLightState;
import traffic.TrafficLightServiceGrpc;

public class TrafficLightClient {
    private final TrafficLightServiceGrpc.TrafficLightServiceBlockingStub blockingStub;

    public TrafficLightClient(ManagedChannel channel) {
        blockingStub = TrafficLightServiceGrpc.newBlockingStub(channel);
    }

    public void changeLight(TrafficLightState.State state) {
        ChangeLightRequest request = ChangeLightRequest.newBuilder()
                .setState(TrafficLightState.newBuilder().setCurrentState(state).build())
                .build();
        ChangeLightResponse response;
        try {
            response = blockingStub.changeLightState(request);
            System.out.println("Server response: " + response.getMessage());
        } catch (StatusRuntimeException e) {
            System.err.println("RPC failed: " + e.getStatus());
        }
    }

    public void checkCurrentState() {
        Empty request = Empty.newBuilder().build();
        TrafficLightState response;
        try {
            response = blockingStub.getCurrentState(request);
            System.out.println("Current traffic light state: " + response.getCurrentState());
        } catch (StatusRuntimeException e) {
            System.err.println("RPC failed: " + e.getStatus());
        }
    }

    public static void main(String[] args) throws Exception {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();
        try {
            TrafficLightClient client = new TrafficLightClient(channel);
            client.changeLight(TrafficLightState.State.GREEN);
            client.checkCurrentState();
        } finally {
            channel.shutdownNow().awaitTermination(5, java.util.concurrent.TimeUnit.SECONDS);
        }
    }
}
