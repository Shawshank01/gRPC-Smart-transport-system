import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import traffic.Traffic.ChangeLightRequest;
import traffic.Traffic.ChangeLightResponse;
import traffic.Traffic.Empty;
import traffic.Traffic.TrafficLightState;
import traffic.TrafficLightServiceGrpc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TrafficLightClient {
    private final TrafficLightServiceGrpc.TrafficLightServiceBlockingStub blockingStub;
    private volatile boolean keepRunning = true;

    public TrafficLightClient(ManagedChannel channel) {
        blockingStub = TrafficLightServiceGrpc.newBlockingStub(channel);
    }

    public void toggleLight() {
        TrafficLightState current = getCurrentState();
        TrafficLightState.State newState = current.getCurrentState() == TrafficLightState.State.RED ?
                TrafficLightState.State.GREEN :
                TrafficLightState.State.RED;

        ChangeLightRequest request = ChangeLightRequest.newBuilder()
                .setState(TrafficLightState.newBuilder().setCurrentState(newState).build())
                .build();
        ChangeLightResponse response;
        try {
            response = blockingStub.changeLightState(request);
            System.out.println("Server response: " + response.getMessage());
        } catch (StatusRuntimeException e) {
            System.err.println("RPC failed: " + e.getStatus());
        }
    }

    private TrafficLightState getCurrentState() {
        Empty request = Empty.newBuilder().build();
        try {
            return blockingStub.getCurrentState(request);
        } catch (StatusRuntimeException e) {
            System.err.println("Failed to get current state: " + e.getStatus());
            return null; // Handle null appropriately in your logic
        }
    }

    public void startLightChanging() {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        Runnable task = this::toggleLight;

        executor.scheduleAtFixedRate(task, 0, 5, TimeUnit.SECONDS);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Enter 'q' to quit.");
            while (keepRunning) {
                if (reader.ready()) {
                    if ("q".equals(reader.readLine().trim().toLowerCase())) {
                        keepRunning = false;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
            try {
                if (!executor.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();
        try {
            TrafficLightClient client = new TrafficLightClient(channel);
            client.startLightChanging();
        } finally {
            channel.shutdownNow().awaitTermination(5, java.util.concurrent.TimeUnit.SECONDS);
        }
    }
}
