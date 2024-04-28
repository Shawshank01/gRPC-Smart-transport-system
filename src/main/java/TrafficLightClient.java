import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import traffic.TrafficLightServiceGrpc;
import traffic.Traffic.TrafficCommand;
import traffic.Traffic.TrafficState;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class TrafficLightClient {
    // Stub that allows asynchronous communication with the traffic light service.
    private final TrafficLightServiceGrpc.TrafficLightServiceStub asyncStub;

    /**
     * Constructor to build the traffic light client.
     *
     * @param channel The channel used to communicate with the server.
     */
    public TrafficLightClient(ManagedChannel channel) {
        asyncStub = TrafficLightServiceGrpc.newStub(channel);
    }

    /**
     * Manages traffic light states based on commands entered by the user.
     *
     * @throws InterruptedException if thread is interrupted while waiting.
     * @throws IOException          if an I/O error occurs when reading input.
     */
    public void manageTraffic() throws InterruptedException, IOException {
        // Latch to wait for the completion of the streaming.
        CountDownLatch finishLatch = new CountDownLatch(1);
        // Start a bidirectional stream to manage traffic lights.
        StreamObserver<TrafficCommand> requestObserver = asyncStub.manageTraffic(new StreamObserver<TrafficState>() {
            @Override
            public void onNext(TrafficState state) {
                // Process each state response from the server.
                System.out.println("Traffic light at " + state.getIntersectionId() + " is now " + state.getCurrentState());
            }

            @Override
            public void onError(Throwable t) {
                // Handle errors encountered during the stream.
                System.err.println("Error in manageTraffic: " + t.getMessage());
                finishLatch.countDown();
            }

            @Override
            public void onCompleted() {
                // Complete the client side stream and print a confirmation.
                System.out.println("Stream is completed");
                finishLatch.countDown();
            }
        });

        // Read commands from the user to control the traffic lights.
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String line;
            System.out.println("Enter commands ('TURN_GREEN' or 'TURN_RED'), type 'q' to quit:");
            while (!(line = br.readLine()).equals("q")) {
                try {
                    // Convert the user input into a command.
                    TrafficCommand.Command command = TrafficCommand.Command.valueOf(line.trim());
                    requestObserver.onNext(TrafficCommand.newBuilder()
                            .setIntersectionId("Station Road") // Set the intersection ID for the command.
                            .setCommand(command)
                            .build());
                } catch (IllegalArgumentException iae) {
                    // Handle cases where the command is not recognized.
                    System.err.println("Invalid command. Please type 'TURN_GREEN' or 'TURN_RED'.");
                }
            }
        } finally {
            // Complete the stream and close resources.
            requestObserver.onCompleted();
            finishLatch.await(1, TimeUnit.MINUTES);  // Wait for the server to confirm the stream is closed
        }
    }

    /**
     * Main method to run the traffic light client.
     *
     * @param args Command line arguments.
     * @throws Exception if an error occurs during client operation.
     */
    public static void main(String[] args) throws Exception {
        // Create a channel to the server.
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()  // Note: insecure for production, suitable for development.
                .build();
        try {
            // Initialize the client and manage traffic.
            TrafficLightClient client = new TrafficLightClient(channel);
            client.manageTraffic();
        } finally {
            // Ensure the channel is shutdown cleanly.
            channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}
