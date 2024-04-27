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

    private void start() throws IOException {
        int port = 50053;
        server = ServerBuilder.forPort(port)
                .addService(new VehicleServiceImpl())
                .build()
                .start();
        System.out.println("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            VehicleServer.this.stop();
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

    static class VehicleServiceImpl extends VehicleServiceGrpc.VehicleServiceImplBase {
        @Override
        public void streamEvents(EventRequest req, StreamObserver<EventNotification> responseObserver) {
            for (int i = 0; i < 10; i++) {  // Example loop to send 10 notifications
                EventNotification notification = EventNotification.newBuilder()
                        .setVehicleId(req.getVehicleId())
                        .setMessage("Event #" + i)
                        .setTimestamp(System.currentTimeMillis())
                        .build();
                responseObserver.onNext(notification);
                try {
                    TimeUnit.SECONDS.sleep(1);  // Pause for a second between notifications
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            responseObserver.onCompleted();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final VehicleServer server = new VehicleServer();
        server.start();
        server.blockUntilShutdown();
    }
}
