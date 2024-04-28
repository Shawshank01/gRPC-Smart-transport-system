import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import vehicle.VehicleServiceGrpc;
import vehicle.Vehicle.EventRequest;
import vehicle.Vehicle.EventNotification;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class VehicleServer {
    private Server server;

    /**
     * Starts the gRPC server.
     *
     * @throws IOException if there is an error starting the server.
     */
    private void start() throws IOException {
        int port = 50053;
        // Build and start the server on the specified port, adding the implemented service.
        server = ServerBuilder.forPort(port)
                .addService(new VehicleServiceImpl())
                .build()
                .start();
        System.out.println("Server started, listening on " + port);
        // Add a shutdown hook to ensure clean shutdown, such as when the JVM is shutting down.
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            VehicleServer.this.stop();
            System.err.println("*** server shut down");
        }));
    }

    /**
     * Stops the server and releases all resources.
     */
    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    /**
     * Blocks until the server is completely shut down, meaning it will not accept any more incoming calls.
     *
     * @throws InterruptedException if the thread is interrupted while waiting for server termination.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    /**
     * Implementation of the VehicleService from the gRPC definition. This service streams event notifications.
     */
    static class VehicleServiceImpl extends VehicleServiceGrpc.VehicleServiceImplBase {
        /**
         * Streams a series of event notifications to a connected client.
         *
         * @param req              The request containing information like the vehicle ID.
         * @param responseObserver The observer used to send event notifications back to the client.
         */
        @Override
        public void streamEvents(EventRequest req, StreamObserver<EventNotification> responseObserver) {
            // Loop to send 10 notifications, simulating event updates.
            for (int i = 0; i < 10; i++) {
                EventNotification notification = EventNotification.newBuilder()
                        .setVehicleId(req.getVehicleId())
                        .setMessage("Event #" + i)
                        .setTimestamp(System.currentTimeMillis())
                        .build();
                responseObserver.onNext(notification);
                // Wait for a second between each notification to simulate time delay.
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // Mark the end of the stream.
            responseObserver.onCompleted();
        }
    }

    /**
     * The main method to start the server and handle shutdown appropriately.
     *
     * @param args Command line arguments.
     * @throws IOException          If there is an issue starting the server.
     * @throws InterruptedException If the server is interrupted during its execution.
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        final VehicleServer server = new VehicleServer();
        server.start();
        server.blockUntilShutdown();
    }
}
