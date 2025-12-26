import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import traffic.TrafficLightServiceGrpc;
import traffic.Traffic.TrafficCommand;
import traffic.Traffic.TrafficState;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Client for managing traffic lights via a gRPC service.
 */
public class TrafficLightClient {
    private final TrafficLightServiceGrpc.TrafficLightServiceStub asyncStub;

    /**
     * Constructs a client for interacting with the traffic light gRPC service.
     *
     * @param channel The channel used to communicate with the server.
     */
    public TrafficLightClient(ManagedChannel channel) {
        asyncStub = TrafficLightServiceGrpc.newStub(channel);
    }

    /**
     * Main method to run the client application.
     *
     * @param args Command line arguments (not used here).
     */
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try {
            interactWithTrafficLightService(channel, br);
        } catch (IOException | InterruptedException e) {
            System.err.println("Failed to manage traffic lights: " + e.getMessage());
        } finally {
            try {
                br.close();
                channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
            } catch (IOException | InterruptedException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }

    /**
     * Interacts with the traffic light service.
     *
     * @param channel The gRPC channel.
     * @param br      BufferedReader to handle user input.
     * @throws IOException          if an input/output error occurs.
     * @throws InterruptedException if interrupted during wait.
     */
    public static void interactWithTrafficLightService(ManagedChannel channel, BufferedReader br)
            throws IOException, InterruptedException {
        TrafficLightClient client = new TrafficLightClient(channel);
        client.manageTraffic(br);
    }

    /**
     * Manages the traffic light based on user commands.
     *
     * @param br BufferedReader to read commands from the user.
     * @throws InterruptedException if interrupted during wait.
     * @throws IOException          if an input/output error occurs.
     */
    public void manageTraffic(BufferedReader br) throws InterruptedException, IOException {
        CountDownLatch finishLatch = new CountDownLatch(1);
        StreamObserver<TrafficCommand> requestObserver = asyncStub.manageTraffic(new StreamObserver<TrafficState>() {
            @Override
            public void onNext(TrafficState state) {
                System.out.println(
                        "Traffic light at " + state.getIntersectionId() + " is now " + state.getCurrentState());
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Error in manageTraffic: " + t.getMessage());
                finishLatch.countDown();
            }

            @Override
            public void onCompleted() {
                System.out.println("Stream is completed");
                finishLatch.countDown();
            }
        });

        try {
            System.out.println("Enter commands ('TURN_GREEN' or 'TURN_RED'), type 'q' to return:");
            String line;
            while ((line = br.readLine()) != null) {
                String trimmed = line.trim();
                if ("q".equalsIgnoreCase(trimmed)) {
                    break;
                }
                try {
                    TrafficCommand.Command command = TrafficCommand.Command.valueOf(trimmed.toUpperCase());
                    requestObserver.onNext(TrafficCommand.newBuilder()
                            .setIntersectionId("Station Road")
                            .setCommand(command)
                            .build());
                } catch (IllegalArgumentException iae) {
                    System.err.println("Invalid command. Please type 'TURN_GREEN' or 'TURN_RED'.");
                }
            }
            requestObserver.onCompleted(); // Notify server to complete the stream
        } finally {
            finishLatch.await(1, TimeUnit.MINUTES); // Wait for the server to confirm the stream is closed
        }
    }
}
