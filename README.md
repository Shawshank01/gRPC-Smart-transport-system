# Smart Transport System: gRPC-based Distributed Services Report

For x86 platform only

## Domain Description

The proposed Smart Transport System is designed to leverage modern distributed system principles to enhance urban
traffic management and vehicle services. This system encapsulates functionalities aimed at improving traffic light
management, parking availability, vehicle telemetry, and event notification services. Utilizing gRPC, these services are
designed to communicate effectively over a network, ensuring real-time data exchange and control.

Include Parking for simple RPC,  
Vehicle for server-side streaming RPC,  
Telemetry for client-side streaming RPC,  
Traffic light for bidirectional streaming RPC.

### **Traffic Light Service**

This service manages traffic light on intersection. It offers dynamic control of traffic signals based on real-time
traffic data, significantly reducing congestion and improving traffic flow. The service can alter the state of traffic
lights (red, green) based on the commands it receives from traffic management centres or automated systems.

### **Parking Service**

The Parking Service manages parking spaces by providing real-time data on availability and allowing users to reserve
spaces. This service helps in reducing time spent searching for parking and improves overall satisfaction for drivers.
It supports functionalities such as checking parking availability and reserving a parking spot.

### **Vehicle Telemetry Service**

This service focuses on collecting and streaming real-time data from vehicles, such as speed, engine temperature, and
GPS coordinates. This data is essential for real-time monitoring, predictive maintenance, and other analytics that can
enhance vehicle performance and safety.

### **Event Notification Service**

This service provides real-time alerts and notifications to vehicles about various events like accidents, road closures,
and weather conditions. This service ensures that drivers are well-informed, promoting safer and more efficient driving
conditions.

## Service Definition and RPC

### **Traffic Light Service**

- **RPC Type**: Bidirectional Streaming
- **Functionality**: Manage traffic lights by receiving state change commands and sending acknowledgments.
- **Request (TrafficCommand)**: Contains `intersectionId` (string) and `command` (enum: TURN_RED, TURN_GREEN).
- **Response (TrafficState)**: Returns `intersectionId` (string) and `currentState` (enum: RED, GREEN).

### **Parking Service**

- **RPC Types**: Simple RPC
- **Functionality 1**: Check parking spot availability.
    - **Request (CheckAvailabilityRequest)**: Includes `locationId` (string).
    - **Response (CheckAvailabilityResponse)**: Provides `availableSpots` (int32), `success` (bool), and `message` (
      string).
- **Functionality 2**: Reserve a parking spot.
    - **Request (ReserveSpotRequest)**: Includes `locationId` (string) and `userId` (string).
    - **Response (ReserveSpotResponse)**: Provides `success` (bool) and `message` (string).

### **Vehicle Telemetry Service**

- **RPC Type**: Client-side Streaming
- **Functionality**: Stream telemetry data from vehicles to the server.
- **Request (TelemetryData)**: Includes `vehicleId` (string), `latitude` (double), `longitude` (double), `speed` (
  float), `engineTemp` (float), `fuelLevel` (float), and `timestamp` (int64).
- **Response (TelemetryResponse)**: Provides `success` (bool) and `message` (string).

### **Event Notification Service**

- **RPC Type**: Server-side Streaming
- **Functionality**: Send real-time event notifications to vehicles.
- **Request (EventRequest)**: Includes `vehicleId` (string).
- **Response (EventNotification)**: Streams `message` (string) and `timestamp` (int64).

## Service Implementations

Each service is implemented using gRPC's robust system which allows for real-time, bi-directional streaming, secure, and
efficient communication between clients and servers. Implementations include server setup, stream handling, and
client-server interactions ensuring data integrity and error handling.

## Naming Services

Services are named according to their functionality:

- `TrafficLightService`
- `ParkingService`
- `VehicleTelemetryService`
- `EventNotificationService`

These names are intuitive, reflecting the operations they handle and ensuring that they are easily recognizable within
the system architecture.

## Remote Error Handling & Advanced Features

Error handling is managed through gRPC's built-in mechanisms. Each service implements error handling in its stream
observers (`onError` method), ensuring any issues like network failures or data inconsistencies are logged and managed
properly. Advanced features include context passing for maintaining state and metadata in requests, deadlines, and
timeout management for calls.

## Client - Command Prompts

**Main Module Implementation**

The `Main` module serves as the central entry point for the Smart Transport System. It is responsible for orchestrating
the startup of all service servers, managing user interactions, and cleanly shutting down resources when required. Below
is a detailed breakdown of its functionalities and operations:

- **Server Initialization**: `Main` initializes all the service servers including Traffic Light, Parking, Vehicle
  Telemetry, and Event Notification. Each server is started in its own thread, ensuring they operate concurrently
  without blocking each other or the main application flow.

- **Service Interaction Management**: It provides a command-line interface that allows administrators or users to
  interact with different services by selecting appropriate options. This is crucial for demonstrating the system's
  capabilities in real-time and for testing purposes.

- **Graceful Shutdown**: When a user decides to exit the application (by entering "0"), `Main` ensures a graceful
  shutdown of all servers and the main application itself. It handles the interruption and closure of server threads,
  ensuring there are no resource leaks or abrupt terminations.

- **Error Handling and Logging**: Throughout its execution, the `Main` module handles exceptions and errors gracefully,
  providing logs for debugging and maintenance purposes. This ensures that any issues can be traced and rectified
  promptly.

### Conclusion

The Smart Transport System utilizing gRPC exemplifies a modern approach to handling distributed systems in traffic and
vehicle management. Through the efficient use of different RPC types, it promises scalability, real-time operations, and
high reliability, crucial for the demanding dynamics of urban transport management.

The Main module effectively demonstrates the integration and management capabilities within a distributed system
architecture using gRPC. By providing an interface for interaction and ensuring robust management of service life
cycles, it plays a pivotal role in the overall functionality and reliability of the Smart Transport System. This setup
is ideal for environments that require high availability, scalability, and effective management of transport-related
services.
