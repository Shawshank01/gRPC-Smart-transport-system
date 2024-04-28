import io.grpc.ManagedChannel;
import io.grpc.stub.StreamObserver;
import traffic.TrafficLightServiceGrpc;
import traffic.Traffic.TrafficCommand;
import traffic.Traffic.TrafficState;

import java.io.BufferedReader;
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
     * Method to interact with the traffic light service.
     *
     * @param channel The gRPC channel.
     * @param br      BufferedReader to handle user input.
     */
    public static void interactWithTrafficLightService(ManagedChannel channel, BufferedReader br) throws IOException, InterruptedException {
        TrafficLightClient client = new TrafficLightClient(channel);
        client.manageTraffic(br);
        channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
    }

    /**
     * Handles the traffic light management based on user input.
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
                System.out.println("Traffic light at " + state.getIntersectionId() + " is now " + state.getCurrentState());
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
            while (!(line = br.readLine()).equals("q")) {
                try {
                    TrafficCommand.Command command = TrafficCommand.Command.valueOf(line.trim().toUpperCase());
                    requestObserver.onNext(TrafficCommand.newBuilder()
                            .setIntersectionId("Station Road")
                            .setCommand(command)
                            .build());
                } catch (IllegalArgumentException iae) {
                    System.err.println("Invalid command. Please type 'TURN_GREEN' or 'TURN_RED'.");
                }
            }
            requestObserver.onCompleted();  // Notify server to complete the stream
        } finally {
            finishLatch.await(1, TimeUnit.MINUTES);  // Wait for the server to confirm the stream is closed
        }
    }
}
