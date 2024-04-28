import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int TRAFFIC_LIGHT_PORT = 50051;
    private static final int PARKING_PORT = 50052;
    private static final int VEHICLE_PORT = 50053;
    private static final int TELEMETRY_PORT = 50054;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        startServers(); // Start all servers

        try {
            while (true) {
                System.out.println("\nSelect the service to interact with:");
                System.out.println("1. Traffic Light Service");
                System.out.println("2. Parking Service");
                System.out.println("3. Vehicle Telemetry Service");
                System.out.println("4. Event Notification Service");
                System.out.println("0. Exit");

                String choice = br.readLine();
                switch (choice) {
                    case "1":
                        interactWithTrafficLightService(br);
                        break;
                    case "2":
                        interactWithParkingService();
                        break;
                    case "3":
                        interactWithVehicleTelemetryService();
                        break;
                    case "4":
                        interactWithEventNotificationService();
                        break;
                    case "0":
                        stopServers();  // Stop all servers
                        return;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 0 and 4.");
                        break;
                }
            }
        } finally {
            stopServers();  // Ensure all servers are stopped even if an exception occurs
        }
    }

    // List to keep track of server threads for graceful shutdown
    private static final List<Thread> serverThreads = new ArrayList<>();

    private static void startServers() {
        serverThreads.add(new Thread(() -> {
            try {
                TrafficLightServer.main(null);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }));
        serverThreads.add(new Thread(() -> {
            try {
                ParkingServer.main(null);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }));
        serverThreads.add(new Thread(() -> {
            try {
                VehicleServer.main(null);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }));
        serverThreads.add(new Thread(() -> {
            try {
                TelemetryServer.main(null);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }));

        for (Thread thread : serverThreads) {
            thread.start();
        }
    }

    private static void stopServers() {
        // Signal all server threads to stop
        for (Thread serverThread : serverThreads) {
            serverThread.interrupt();
        }

        // Wait for all server threads to finish
        for (Thread serverThread : serverThreads) {
            try {
                serverThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();  // Properly handle InterruptedException
                System.out.println("Failed to stop server thread gracefully: " + e.getMessage());
            }
        }
    }

    private static void interactWithTrafficLightService(BufferedReader br) throws Exception {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(SERVER_ADDRESS, TRAFFIC_LIGHT_PORT)
                .usePlaintext()
                .build();
        TrafficLightClient.interactWithTrafficLightService(channel, br);
        channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
    }

    private static void interactWithParkingService() throws Exception {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(SERVER_ADDRESS, PARKING_PORT)
                .usePlaintext()
                .build();
        ParkingClient.interactWithParkingService(channel);
        channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
    }

    private static void interactWithVehicleTelemetryService() throws Exception {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(SERVER_ADDRESS, TELEMETRY_PORT)
                .usePlaintext()
                .build();
        TelemetryClient.interactWithVehicleTelemetryService(channel);
        channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
    }

    private static void interactWithEventNotificationService() throws Exception {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(SERVER_ADDRESS, VEHICLE_PORT)
                .usePlaintext()
                .build();
        VehicleClient.interactWithEventNotificationService(channel);
        channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
    }
}
