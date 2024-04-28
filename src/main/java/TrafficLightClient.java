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

public class TrafficLightClient {
    private final TrafficLightServiceGrpc.TrafficLightServiceStub asyncStub;

    public TrafficLightClient(ManagedChannel channel) {
        asyncStub = TrafficLightServiceGrpc.newStub(channel);
    }

    /**
     * Entry point for managing traffic from Main.java.
     *
     * @param channel The gRPC channel.
     */
    public static void interactWithTrafficLightService(ManagedChannel channel) throws InterruptedException, IOException {
        TrafficLightClient client = new TrafficLightClient(channel);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            client.manageTraffic(br);
        } finally {
            channel.shutdown();
            channel.awaitTermination(5, TimeUnit.SECONDS);
        }
    }

    /**
     * Manages traffic light states based on commands entered by the user.
     *
     * @param br BufferedReader to read user input.
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
            System.out.println("Enter commands ('TURN_GREEN' or 'TURN_RED'), type 'q' to quit:");
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
        } catch (IOException e) {
            requestObserver.onError(e);
        } finally {
            finishLatch.await(1, TimeUnit.MINUTES);
        }
    }

    public static void main(String[] args) throws Exception {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();
        interactWithTrafficLightService(channel);
    }
}
