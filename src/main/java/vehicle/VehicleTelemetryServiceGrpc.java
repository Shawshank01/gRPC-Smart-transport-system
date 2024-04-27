package vehicle;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * The service definition for streaming telemetry data from vehicles to the server.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.61.1)",
    comments = "Source: vehicle_telemetry.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class VehicleTelemetryServiceGrpc {

  private VehicleTelemetryServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "vehicle.VehicleTelemetryService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<vehicle.VehicleTelemetry.TelemetryData,
      vehicle.VehicleTelemetry.TelemetryResponse> getStreamTelemetryMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "StreamTelemetry",
      requestType = vehicle.VehicleTelemetry.TelemetryData.class,
      responseType = vehicle.VehicleTelemetry.TelemetryResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<vehicle.VehicleTelemetry.TelemetryData,
      vehicle.VehicleTelemetry.TelemetryResponse> getStreamTelemetryMethod() {
    io.grpc.MethodDescriptor<vehicle.VehicleTelemetry.TelemetryData, vehicle.VehicleTelemetry.TelemetryResponse> getStreamTelemetryMethod;
    if ((getStreamTelemetryMethod = VehicleTelemetryServiceGrpc.getStreamTelemetryMethod) == null) {
      synchronized (VehicleTelemetryServiceGrpc.class) {
        if ((getStreamTelemetryMethod = VehicleTelemetryServiceGrpc.getStreamTelemetryMethod) == null) {
          VehicleTelemetryServiceGrpc.getStreamTelemetryMethod = getStreamTelemetryMethod =
              io.grpc.MethodDescriptor.<vehicle.VehicleTelemetry.TelemetryData, vehicle.VehicleTelemetry.TelemetryResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "StreamTelemetry"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  vehicle.VehicleTelemetry.TelemetryData.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  vehicle.VehicleTelemetry.TelemetryResponse.getDefaultInstance()))
              .setSchemaDescriptor(new VehicleTelemetryServiceMethodDescriptorSupplier("StreamTelemetry"))
              .build();
        }
      }
    }
    return getStreamTelemetryMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static VehicleTelemetryServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<VehicleTelemetryServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<VehicleTelemetryServiceStub>() {
        @java.lang.Override
        public VehicleTelemetryServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new VehicleTelemetryServiceStub(channel, callOptions);
        }
      };
    return VehicleTelemetryServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static VehicleTelemetryServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<VehicleTelemetryServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<VehicleTelemetryServiceBlockingStub>() {
        @java.lang.Override
        public VehicleTelemetryServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new VehicleTelemetryServiceBlockingStub(channel, callOptions);
        }
      };
    return VehicleTelemetryServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static VehicleTelemetryServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<VehicleTelemetryServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<VehicleTelemetryServiceFutureStub>() {
        @java.lang.Override
        public VehicleTelemetryServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new VehicleTelemetryServiceFutureStub(channel, callOptions);
        }
      };
    return VehicleTelemetryServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * The service definition for streaming telemetry data from vehicles to the server.
   * </pre>
   */
  public interface AsyncService {

    /**
     * <pre>
     * Client streams telemetry data to the server
     * </pre>
     */
    default io.grpc.stub.StreamObserver<vehicle.VehicleTelemetry.TelemetryData> streamTelemetry(
        io.grpc.stub.StreamObserver<vehicle.VehicleTelemetry.TelemetryResponse> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getStreamTelemetryMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service VehicleTelemetryService.
   * <pre>
   * The service definition for streaming telemetry data from vehicles to the server.
   * </pre>
   */
  public static abstract class VehicleTelemetryServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return VehicleTelemetryServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service VehicleTelemetryService.
   * <pre>
   * The service definition for streaming telemetry data from vehicles to the server.
   * </pre>
   */
  public static final class VehicleTelemetryServiceStub
      extends io.grpc.stub.AbstractAsyncStub<VehicleTelemetryServiceStub> {
    private VehicleTelemetryServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected VehicleTelemetryServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new VehicleTelemetryServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Client streams telemetry data to the server
     * </pre>
     */
    public io.grpc.stub.StreamObserver<vehicle.VehicleTelemetry.TelemetryData> streamTelemetry(
        io.grpc.stub.StreamObserver<vehicle.VehicleTelemetry.TelemetryResponse> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncClientStreamingCall(
          getChannel().newCall(getStreamTelemetryMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service VehicleTelemetryService.
   * <pre>
   * The service definition for streaming telemetry data from vehicles to the server.
   * </pre>
   */
  public static final class VehicleTelemetryServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<VehicleTelemetryServiceBlockingStub> {
    private VehicleTelemetryServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected VehicleTelemetryServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new VehicleTelemetryServiceBlockingStub(channel, callOptions);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service VehicleTelemetryService.
   * <pre>
   * The service definition for streaming telemetry data from vehicles to the server.
   * </pre>
   */
  public static final class VehicleTelemetryServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<VehicleTelemetryServiceFutureStub> {
    private VehicleTelemetryServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected VehicleTelemetryServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new VehicleTelemetryServiceFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_STREAM_TELEMETRY = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_STREAM_TELEMETRY:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.streamTelemetry(
              (io.grpc.stub.StreamObserver<vehicle.VehicleTelemetry.TelemetryResponse>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getStreamTelemetryMethod(),
          io.grpc.stub.ServerCalls.asyncClientStreamingCall(
            new MethodHandlers<
              vehicle.VehicleTelemetry.TelemetryData,
              vehicle.VehicleTelemetry.TelemetryResponse>(
                service, METHODID_STREAM_TELEMETRY)))
        .build();
  }

  private static abstract class VehicleTelemetryServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    VehicleTelemetryServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return vehicle.VehicleTelemetry.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("VehicleTelemetryService");
    }
  }

  private static final class VehicleTelemetryServiceFileDescriptorSupplier
      extends VehicleTelemetryServiceBaseDescriptorSupplier {
    VehicleTelemetryServiceFileDescriptorSupplier() {}
  }

  private static final class VehicleTelemetryServiceMethodDescriptorSupplier
      extends VehicleTelemetryServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    VehicleTelemetryServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (VehicleTelemetryServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new VehicleTelemetryServiceFileDescriptorSupplier())
              .addMethod(getStreamTelemetryMethod())
              .build();
        }
      }
    }
    return result;
  }
}
