import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import vehicle_telemetry.VehicleTelemetryServiceGrpc;
import vehicle_telemetry.VehicleTelemetry.TelemetryData;
import vehicle_telemetry.VehicleTelemetry.TelemetryResponse;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class TelemetryServer {
    private Server server;

    /**
     * Starts the gRPC server.
     *
     * @throws IOException if there is an error starting the server.
     */
    void start() throws IOException {
        int port = 50054;
        // Create the server on the specified port and add the telemetry service.
        server = ServerBuilder.forPort(port)
                .addService(new VehicleTelemetryServiceImpl())
                .build()
                .start();
        System.out.println("Server started, listening on " + port);
        // Add a shutdown hook to clean up resources gracefully when the JVM shuts down.
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            TelemetryServer.this.stop();
            System.err.println("*** server shut down");
        }));
    }

    /**
     * Stops the server and releases resources.
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
     * Blocks until the server is terminated.
     *
     * @throws InterruptedException if the thread is interrupted while waiting for
     *                              server termination.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    /**
     * Implementation of the Vehicle Telemetry Service.
     * Handles streaming of telemetry data from vehicles.
     */
    static class VehicleTelemetryServiceImpl extends VehicleTelemetryServiceGrpc.VehicleTelemetryServiceImplBase {
        /**
         * Receives streams of telemetry data from clients.
         *
         * @param responseObserver The observer used to send responses back to the
         *                         client.
         * @return StreamObserver to handle incoming telemetry data from the client.
         */
        @Override
        public StreamObserver<TelemetryData> streamTelemetry(StreamObserver<TelemetryResponse> responseObserver) {
            return new StreamObserver<TelemetryData>() {
                @Override
                public void onNext(TelemetryData data) {
                    // Log received telemetry data.
                    System.out.println("Received telemetry data from vehicle " + data.getVehicleId() + " at "
                            + data.getTimestamp());
                }

                @Override
                public void onError(Throwable t) {
                    // Log any errors encountered during the stream.
                    System.err.println("Telemetry streaming error: " + t);
                }

                @Override
                public void onCompleted() {
                    // Once all data is received, send a success response.
                    responseObserver.onNext(TelemetryResponse.newBuilder()
                            .setSuccess(true)
                            .setMessage("All telemetry data processed successfully.")
                            .build());
                    // Mark the end of the response stream.
                    responseObserver.onCompleted();
                }
            };
        }
    }

    /**
     * Main method to launch the telemetry server.
     *
     * @param args Command line arguments.
     * @throws IOException          if there is an error starting the server.
     * @throws InterruptedException if the thread is interrupted during server
     *                              shutdown.
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        final TelemetryServer server = new TelemetryServer();
        server.start();
        server.blockUntilShutdown();
    }
}
