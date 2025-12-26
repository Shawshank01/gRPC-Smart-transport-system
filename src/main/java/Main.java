import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int TRAFFIC_LIGHT_PORT = 50051;
    private static final int PARKING_PORT = 50052;
    private static final int VEHICLE_PORT = 50053;
    private static final int TELEMETRY_PORT = 50054;

    private static TrafficLightServer trafficLightServer;
    private static ParkingServer parkingServer;
    private static VehicleServer vehicleServer;
    private static TelemetryServer telemetryServer;

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
                if (choice == null) {
                    System.out.println("Input closed. Exiting.");
                    return;
                }
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
                        return;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 0 and 4.");
                        break;
                }
            }
        } finally {
            stopServers(); // Ensure all servers are stopped even if an exception occurs
        }
    }

    private static void startServers() throws IOException {
        trafficLightServer = new TrafficLightServer();
        parkingServer = new ParkingServer();
        vehicleServer = new VehicleServer();
        telemetryServer = new TelemetryServer();

        try {
            trafficLightServer.start();
            parkingServer.start();
            vehicleServer.start();
            telemetryServer.start();
        } catch (IOException e) {
            stopServers();
            throw e;
        }
    }

    private static void stopServers() {
        if (telemetryServer != null) {
            telemetryServer.stop();
        }
        if (vehicleServer != null) {
            vehicleServer.stop();
        }
        if (parkingServer != null) {
            parkingServer.stop();
        }
        if (trafficLightServer != null) {
            trafficLightServer.stop();
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
