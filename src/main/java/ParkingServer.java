import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import parking.ParkingServiceGrpc;
import parking.Parking.CheckAvailabilityRequest;
import parking.Parking.CheckAvailabilityResponse;
import parking.Parking.ReserveSpotRequest;
import parking.Parking.ReserveSpotResponse;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ParkingServer {
    private static final int DEFAULT_AVAILABLE_SPOTS = 10;
    private static final ConcurrentMap<String, AtomicInteger> availability = new ConcurrentHashMap<>();

    private Server server;

    /**
     * Starts the gRPC server.
     *
     * @throws IOException if there is an error starting the server.
     */
    void start() throws IOException {
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
    void stop() {
        if (server != null) {
            server.shutdown();
            try {
                if (!server.awaitTermination(5, TimeUnit.SECONDS)) {
                    server.shutdownNow();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                server.shutdownNow();
            }
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
        private AtomicInteger getAvailabilityCounter(String locationId) {
            return availability.computeIfAbsent(locationId, id -> new AtomicInteger(DEFAULT_AVAILABLE_SPOTS));
        }

        /**
         * Implements the Check Availability service call.
         *
         * @param req              The request containing the location ID.
         * @param responseObserver The observer to send responses back to the client.
         */
        @Override
        public void checkAvailability(CheckAvailabilityRequest req,
                StreamObserver<CheckAvailabilityResponse> responseObserver) {
            String locationId = req.getLocationId();
            AtomicInteger counter = getAvailabilityCounter(locationId);
            int availableSpots = counter.get();
            CheckAvailabilityResponse response = CheckAvailabilityResponse.newBuilder()
                    .setAvailableSpots(availableSpots)
                    .setSuccess(true)
                    .setMessage(availableSpots + " spots available")
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
            String locationId = req.getLocationId();
            AtomicInteger counter = getAvailabilityCounter(locationId);
            ReserveSpotResponse response;
            while (true) {
                int current = counter.get();
                if (current <= 0) {
                    response = ReserveSpotResponse.newBuilder()
                            .setSuccess(false)
                            .setMessage("No spots available")
                            .build();
                    break;
                }
                if (counter.compareAndSet(current, current - 1)) {
                    response = ReserveSpotResponse.newBuilder()
                            .setSuccess(true)
                            .setMessage("Spot reserved successfully. Remaining spots: " + (current - 1))
                            .build();
                    break;
                }
            }
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
     * @throws InterruptedException If the thread is interrupted during server
     *                              shutdown.
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        final ParkingServer server = new ParkingServer();
        server.start();
        server.blockUntilShutdown();
    }
}
