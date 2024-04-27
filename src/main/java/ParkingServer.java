import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import parking.ParkingServiceGrpc;
import parking.Parking.CheckAvailabilityRequest;
import parking.Parking.CheckAvailabilityResponse;
import parking.Parking.ReserveSpotRequest;
import parking.Parking.ReserveSpotResponse;

import java.io.IOException;

public class ParkingServer {
    private Server server;

    private void start() throws IOException {
        int port = 50052;
        server = ServerBuilder.forPort(port)
                .addService(new ParkingServiceImpl())
                .build()
                .start();
        System.out.println("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            ParkingServer.this.stop();
            System.err.println("*** server shut down");
        }));
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    static class ParkingServiceImpl extends ParkingServiceGrpc.ParkingServiceImplBase {
        @Override
        public void checkAvailability(CheckAvailabilityRequest req, StreamObserver<CheckAvailabilityResponse> responseObserver) {
            // Implementation here should interact with your parking management system
            int availableSpots = 10; // Example value
            CheckAvailabilityResponse response = CheckAvailabilityResponse.newBuilder()
                    .setAvailableSpots(availableSpots)
                    .setSuccess(true)
                    .setMessage("10 spots available")
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

        @Override
        public void reserveSpot(ReserveSpotRequest req, StreamObserver<ReserveSpotResponse> responseObserver) {
            // Implementation here should reserve a spot and handle errors
            ReserveSpotResponse response = ReserveSpotResponse.newBuilder()
                    .setSuccess(true)
                    .setMessage("Spot reserved successfully")
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final ParkingServer server = new ParkingServer();
        server.start();
        server.blockUntilShutdown();
    }
}
