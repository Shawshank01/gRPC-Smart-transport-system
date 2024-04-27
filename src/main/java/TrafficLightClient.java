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
    private final TrafficLightServiceGrpc.TrafficLightServiceStub asyncStub;

    public TrafficLightClient(ManagedChannel channel) {
        asyncStub = TrafficLightServiceGrpc.newStub(channel);
    }

    public void manageTraffic() throws InterruptedException, IOException {
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

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            String line;
            System.out.println("Enter commands ('TURN_GREEN' or 'TURN_RED'), type 'q' to quit:");
            while (!(line = br.readLine()).equals("q")) {
                try {
                    TrafficCommand.Command command = TrafficCommand.Command.valueOf(line.trim());
                    requestObserver.onNext(TrafficCommand.newBuilder()
                            .setIntersectionId("Station Road") // Example intersection place
                            .setCommand(command)
                            .build());
                } catch (IllegalArgumentException iae) {
                    System.err.println("Invalid command. Please type 'TURN_GREEN' or 'TURN_RED'.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            requestObserver.onCompleted();
            finishLatch.await(1, TimeUnit.MINUTES);  // Wait for the server to confirm the stream is closed
            br.close();
        }
    }

    public static void main(String[] args) throws Exception {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();
        try {
            TrafficLightClient client = new TrafficLightClient(channel);
            client.manageTraffic();
        } finally {
            channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}
