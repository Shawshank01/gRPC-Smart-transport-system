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

    /**
     * Starts the gRPC server.
     *
     * @throws IOException if there is an error starting the server.
     */
    private void start() throws IOException {
        int port = 50052;
        // Build and start a server on the specified port.
        server = ServerBuilder.forPort(port)
                .addService(new ParkingServiceImpl())
                .build()
                .start();
        System.out.println("Server started, listening on " + port);
        // Add shutdown hook to clean up resources gracefully when the JVM shuts down.
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            ParkingServer.this.stop();
            System.err.println("*** server shut down");
        }));
    }

    /**
     * Stops the server.
     */
    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    /**
     * Blocks until server shutdown.
     *
     * @throws InterruptedException if the thread is interrupted while waiting.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    /**
     * Service implementation defining the server behaviors.
     */
    static class ParkingServiceImpl extends ParkingServiceGrpc.ParkingServiceImplBase {
        /**
         * Implements the Check Availability service call.
         *
         * @param req              The request containing the location ID.
         * @param responseObserver The observer to send responses back to the client.
         */
        @Override
        public void checkAvailability(CheckAvailabilityRequest req, StreamObserver<CheckAvailabilityResponse> responseObserver) {
            int availableSpots = 10; // Example hardcoded value for available spots
            // Build the response object.
            CheckAvailabilityResponse response = CheckAvailabilityResponse.newBuilder()
                    .setAvailableSpots(availableSpots)
                    .setSuccess(true)
                    .setMessage("10 spots available")
                    .build();
            // Send the response back to the client.
            responseObserver.onNext(response);
            // Complete the RPC call.
            responseObserver.onCompleted();
        }

        /**
         * Implements the Reserve Spot service call.
         *
         * @param req              The request containing the location and user IDs.
         * @param responseObserver The observer to send responses back to the client.
         */
        @Override
        public void reserveSpot(ReserveSpotRequest req, StreamObserver<ReserveSpotResponse> responseObserver) {
            // Build the response object indicating a successful reservation.
            ReserveSpotResponse response = ReserveSpotResponse.newBuilder()
                    .setSuccess(true)
                    .setMessage("Spot reserved successfully")
                    .build();
            // Send the reservation success response back to the client.
            responseObserver.onNext(response);
            // Complete the RPC call.
            responseObserver.onCompleted();
        }
    }

    /**
     * The main method to start the server.
     *
     * @param args Command line arguments.
     * @throws IOException          If there is an issue starting the server.
     * @throws InterruptedException If the thread is interrupted during server shutdown.
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        final ParkingServer server = new ParkingServer();
        server.start();
        server.blockUntilShutdown();
    }
}
