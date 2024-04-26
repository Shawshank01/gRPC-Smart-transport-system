package parking;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * The parking service definition.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.61.1)",
    comments = "Source: parking.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ParkingServiceGrpc {

  private ParkingServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "parking.ParkingService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<parking.Parking.CheckAvailabilityRequest,
      parking.Parking.CheckAvailabilityResponse> getCheckAvailabilityMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CheckAvailability",
      requestType = parking.Parking.CheckAvailabilityRequest.class,
      responseType = parking.Parking.CheckAvailabilityResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<parking.Parking.CheckAvailabilityRequest,
      parking.Parking.CheckAvailabilityResponse> getCheckAvailabilityMethod() {
    io.grpc.MethodDescriptor<parking.Parking.CheckAvailabilityRequest, parking.Parking.CheckAvailabilityResponse> getCheckAvailabilityMethod;
    if ((getCheckAvailabilityMethod = ParkingServiceGrpc.getCheckAvailabilityMethod) == null) {
      synchronized (ParkingServiceGrpc.class) {
        if ((getCheckAvailabilityMethod = ParkingServiceGrpc.getCheckAvailabilityMethod) == null) {
          ParkingServiceGrpc.getCheckAvailabilityMethod = getCheckAvailabilityMethod =
              io.grpc.MethodDescriptor.<parking.Parking.CheckAvailabilityRequest, parking.Parking.CheckAvailabilityResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CheckAvailability"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  parking.Parking.CheckAvailabilityRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  parking.Parking.CheckAvailabilityResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ParkingServiceMethodDescriptorSupplier("CheckAvailability"))
              .build();
        }
      }
    }
    return getCheckAvailabilityMethod;
  }

  private static volatile io.grpc.MethodDescriptor<parking.Parking.ReserveSpotRequest,
      parking.Parking.ReserveSpotResponse> getReserveSpotMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ReserveSpot",
      requestType = parking.Parking.ReserveSpotRequest.class,
      responseType = parking.Parking.ReserveSpotResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<parking.Parking.ReserveSpotRequest,
      parking.Parking.ReserveSpotResponse> getReserveSpotMethod() {
    io.grpc.MethodDescriptor<parking.Parking.ReserveSpotRequest, parking.Parking.ReserveSpotResponse> getReserveSpotMethod;
    if ((getReserveSpotMethod = ParkingServiceGrpc.getReserveSpotMethod) == null) {
      synchronized (ParkingServiceGrpc.class) {
        if ((getReserveSpotMethod = ParkingServiceGrpc.getReserveSpotMethod) == null) {
          ParkingServiceGrpc.getReserveSpotMethod = getReserveSpotMethod =
              io.grpc.MethodDescriptor.<parking.Parking.ReserveSpotRequest, parking.Parking.ReserveSpotResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ReserveSpot"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  parking.Parking.ReserveSpotRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  parking.Parking.ReserveSpotResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ParkingServiceMethodDescriptorSupplier("ReserveSpot"))
              .build();
        }
      }
    }
    return getReserveSpotMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ParkingServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ParkingServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ParkingServiceStub>() {
        @java.lang.Override
        public ParkingServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ParkingServiceStub(channel, callOptions);
        }
      };
    return ParkingServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ParkingServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ParkingServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ParkingServiceBlockingStub>() {
        @java.lang.Override
        public ParkingServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ParkingServiceBlockingStub(channel, callOptions);
        }
      };
    return ParkingServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ParkingServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ParkingServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ParkingServiceFutureStub>() {
        @java.lang.Override
        public ParkingServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ParkingServiceFutureStub(channel, callOptions);
        }
      };
    return ParkingServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * The parking service definition.
   * </pre>
   */
  public interface AsyncService {

    /**
     * <pre>
     * Request the current availability of parking spots.
     * </pre>
     */
    default void checkAvailability(parking.Parking.CheckAvailabilityRequest request,
        io.grpc.stub.StreamObserver<parking.Parking.CheckAvailabilityResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCheckAvailabilityMethod(), responseObserver);
    }

    /**
     * <pre>
     * Reserve a parking spot.
     * </pre>
     */
    default void reserveSpot(parking.Parking.ReserveSpotRequest request,
        io.grpc.stub.StreamObserver<parking.Parking.ReserveSpotResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getReserveSpotMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service ParkingService.
   * <pre>
   * The parking service definition.
   * </pre>
   */
  public static abstract class ParkingServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return ParkingServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service ParkingService.
   * <pre>
   * The parking service definition.
   * </pre>
   */
  public static final class ParkingServiceStub
      extends io.grpc.stub.AbstractAsyncStub<ParkingServiceStub> {
    private ParkingServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ParkingServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ParkingServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Request the current availability of parking spots.
     * </pre>
     */
    public void checkAvailability(parking.Parking.CheckAvailabilityRequest request,
        io.grpc.stub.StreamObserver<parking.Parking.CheckAvailabilityResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCheckAvailabilityMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Reserve a parking spot.
     * </pre>
     */
    public void reserveSpot(parking.Parking.ReserveSpotRequest request,
        io.grpc.stub.StreamObserver<parking.Parking.ReserveSpotResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getReserveSpotMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service ParkingService.
   * <pre>
   * The parking service definition.
   * </pre>
   */
  public static final class ParkingServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<ParkingServiceBlockingStub> {
    private ParkingServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ParkingServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ParkingServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Request the current availability of parking spots.
     * </pre>
     */
    public parking.Parking.CheckAvailabilityResponse checkAvailability(parking.Parking.CheckAvailabilityRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCheckAvailabilityMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Reserve a parking spot.
     * </pre>
     */
    public parking.Parking.ReserveSpotResponse reserveSpot(parking.Parking.ReserveSpotRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getReserveSpotMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service ParkingService.
   * <pre>
   * The parking service definition.
   * </pre>
   */
  public static final class ParkingServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<ParkingServiceFutureStub> {
    private ParkingServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ParkingServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ParkingServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Request the current availability of parking spots.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<parking.Parking.CheckAvailabilityResponse> checkAvailability(
        parking.Parking.CheckAvailabilityRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCheckAvailabilityMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Reserve a parking spot.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<parking.Parking.ReserveSpotResponse> reserveSpot(
        parking.Parking.ReserveSpotRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getReserveSpotMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CHECK_AVAILABILITY = 0;
  private static final int METHODID_RESERVE_SPOT = 1;

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
        case METHODID_CHECK_AVAILABILITY:
          serviceImpl.checkAvailability((parking.Parking.CheckAvailabilityRequest) request,
              (io.grpc.stub.StreamObserver<parking.Parking.CheckAvailabilityResponse>) responseObserver);
          break;
        case METHODID_RESERVE_SPOT:
          serviceImpl.reserveSpot((parking.Parking.ReserveSpotRequest) request,
              (io.grpc.stub.StreamObserver<parking.Parking.ReserveSpotResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getCheckAvailabilityMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              parking.Parking.CheckAvailabilityRequest,
              parking.Parking.CheckAvailabilityResponse>(
                service, METHODID_CHECK_AVAILABILITY)))
        .addMethod(
          getReserveSpotMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              parking.Parking.ReserveSpotRequest,
              parking.Parking.ReserveSpotResponse>(
                service, METHODID_RESERVE_SPOT)))
        .build();
  }

  private static abstract class ParkingServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ParkingServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return parking.Parking.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ParkingService");
    }
  }

  private static final class ParkingServiceFileDescriptorSupplier
      extends ParkingServiceBaseDescriptorSupplier {
    ParkingServiceFileDescriptorSupplier() {}
  }

  private static final class ParkingServiceMethodDescriptorSupplier
      extends ParkingServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    ParkingServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (ParkingServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ParkingServiceFileDescriptorSupplier())
              .addMethod(getCheckAvailabilityMethod())
              .addMethod(getReserveSpotMethod())
              .build();
        }
      }
    }
    return result;
  }
}
