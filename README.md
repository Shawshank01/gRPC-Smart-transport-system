# Smart Transport System: gRPC-based Distributed Services Report

## Domain Description

The proposed Smart Transport System is designed to leverage modern distributed system principles to enhance urban traffic management and vehicle services. This system encapsulates functionalities aimed at improving traffic light management, parking availability, vehicle telemetry, and event notification services. Utilizing gRPC, these services are designed to communicate effectively over a network, ensuring real-time data exchange and control.

### **Traffic Light Service**
This service manages traffic lights across multiple intersections. It offers dynamic control of traffic signals based on real-time traffic data, significantly reducing congestion and improving traffic flow. The service can alter the state of traffic lights (red, green) based on the commands it receives from traffic management centers or automated systems.

### **Parking Service**
The Parking Service manages parking spaces by providing real-time data on availability and allowing users to reserve spaces. This service helps in reducing time spent searching for parking and improves overall satisfaction for drivers. It supports functionalities such as checking parking availability and reserving a parking spot.

### **Vehicle Telemetry Service**
This service focuses on collecting and streaming real-time data from vehicles, such as speed, engine temperature, and GPS coordinates. This data is essential for real-time monitoring, predictive maintenance, and other analytics that can enhance vehicle performance and safety.

### **Event Notification Service**
This service provides real-time alerts and notifications to vehicles about various events like accidents, road closures, and weather conditions. This service ensures that drivers are well-informed, promoting safer and more efficient driving conditions.

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
    - **Response (CheckAvailabilityResponse)**: Provides `availableSpots` (int32), `success` (bool), and `message` (string).
- **Functionality 2**: Reserve a parking spot.
    - **Request (ReserveSpotRequest)**: Includes `locationId` (string) and `userId` (string).
    - **Response (ReserveSpotResponse)**: Provides `success` (bool) and `message` (string).

### **Vehicle Telemetry Service**
- **RPC Type**: Client-side Streaming
- **Functionality**: Stream telemetry data from vehicles to the server.
- **Request (TelemetryData)**: Includes `vehicleId` (string), `latitude` (double), `longitude` (double), `speed` (float), `engineTemp` (float), `fuelLevel` (float), and `timestamp` (int64).
- **Response (TelemetryResponse)**: Provides `success` (bool) and `message` (string).

### **Event Notification Service**
- **RPC Type**: Server-side Streaming
- **Functionality**: Send real-time event notifications to vehicles.
- **Request (EventRequest)**: Includes `vehicleId` (string).
- **Response (EventNotification)**: Streams `message` (string) and `timestamp` (int64).

## Service Implementations

Each service is implemented using gRPC's robust system which allows for real-time, bi-directional streaming, secure, and efficient communication between clients and servers. Implementations include server setup, stream handling, and client-server interactions ensuring data integrity and error handling.

## Naming Services

Services are named according to their functionality:
- `TrafficLightService`
- `ParkingService`
- `VehicleTelemetryService`
- `EventNotificationService`

These names are intuitive, reflecting the operations they handle and ensuring that they are easily recognizable within the system architecture.

## Remote Error Handling & Advanced Features

Error handling is managed through gRPC's built-in mechanisms. Each service implements error handling in its stream observers (`onError` method), ensuring any issues like network failures or data inconsistencies are logged and managed properly. Advanced features include context passing for maintaining state and metadata in requests, deadlines, and timeout management for calls.

## Client - Graphical User Interface (GUI)/Command Prompts

For the demonstration of functionalities, command-line interfaces are used for clients. Each client allows users to send commands or data to the server and receive responses. This setup is ideal for testing and can be scaled or transformed into a GUI for more user-friendly interaction, depending on the application requirements.

### Conclusion

The Smart Transport System utilizing gRPC exemplifies a modern approach to handling distributed systems in traffic and vehicle management. Through the efficient use of different RPC types, it promises scalability, real-time operations, and high reliability, crucial for

the demanding dynamics of urban transport management.

# gRPC-Smart-transport-system
This is the CA of Distributed Systems (HDCSDEV_INT)  
For x86 platform only

Include Parking for simple RPC,  
Vehicle for server-side streaming RPC,  
Telemetry for client-side streaming RPC,  
Traffic light for bidirectional streaming RPC.