package traffic;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * The greeter service definition.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.61.1)",
    comments = "Source: traffic.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class TrafficLightServiceGrpc {

  private TrafficLightServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "traffic.TrafficLightService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<traffic.Traffic.ChangeLightRequest,
      traffic.Traffic.ChangeLightResponse> getChangeLightStateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ChangeLightState",
      requestType = traffic.Traffic.ChangeLightRequest.class,
      responseType = traffic.Traffic.ChangeLightResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<traffic.Traffic.ChangeLightRequest,
      traffic.Traffic.ChangeLightResponse> getChangeLightStateMethod() {
    io.grpc.MethodDescriptor<traffic.Traffic.ChangeLightRequest, traffic.Traffic.ChangeLightResponse> getChangeLightStateMethod;
    if ((getChangeLightStateMethod = TrafficLightServiceGrpc.getChangeLightStateMethod) == null) {
      synchronized (TrafficLightServiceGrpc.class) {
        if ((getChangeLightStateMethod = TrafficLightServiceGrpc.getChangeLightStateMethod) == null) {
          TrafficLightServiceGrpc.getChangeLightStateMethod = getChangeLightStateMethod =
              io.grpc.MethodDescriptor.<traffic.Traffic.ChangeLightRequest, traffic.Traffic.ChangeLightResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ChangeLightState"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  traffic.Traffic.ChangeLightRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  traffic.Traffic.ChangeLightResponse.getDefaultInstance()))
              .setSchemaDescriptor(new TrafficLightServiceMethodDescriptorSupplier("ChangeLightState"))
              .build();
        }
      }
    }
    return getChangeLightStateMethod;
  }

  private static volatile io.grpc.MethodDescriptor<traffic.Traffic.Empty,
      traffic.Traffic.TrafficLightState> getGetCurrentStateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetCurrentState",
      requestType = traffic.Traffic.Empty.class,
      responseType = traffic.Traffic.TrafficLightState.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<traffic.Traffic.Empty,
      traffic.Traffic.TrafficLightState> getGetCurrentStateMethod() {
    io.grpc.MethodDescriptor<traffic.Traffic.Empty, traffic.Traffic.TrafficLightState> getGetCurrentStateMethod;
    if ((getGetCurrentStateMethod = TrafficLightServiceGrpc.getGetCurrentStateMethod) == null) {
      synchronized (TrafficLightServiceGrpc.class) {
        if ((getGetCurrentStateMethod = TrafficLightServiceGrpc.getGetCurrentStateMethod) == null) {
          TrafficLightServiceGrpc.getGetCurrentStateMethod = getGetCurrentStateMethod =
              io.grpc.MethodDescriptor.<traffic.Traffic.Empty, traffic.Traffic.TrafficLightState>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetCurrentState"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  traffic.Traffic.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  traffic.Traffic.TrafficLightState.getDefaultInstance()))
              .setSchemaDescriptor(new TrafficLightServiceMethodDescriptorSupplier("GetCurrentState"))
              .build();
        }
      }
    }
    return getGetCurrentStateMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static TrafficLightServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TrafficLightServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TrafficLightServiceStub>() {
        @java.lang.Override
        public TrafficLightServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TrafficLightServiceStub(channel, callOptions);
        }
      };
    return TrafficLightServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static TrafficLightServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TrafficLightServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TrafficLightServiceBlockingStub>() {
        @java.lang.Override
        public TrafficLightServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TrafficLightServiceBlockingStub(channel, callOptions);
        }
      };
    return TrafficLightServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static TrafficLightServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TrafficLightServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TrafficLightServiceFutureStub>() {
        @java.lang.Override
        public TrafficLightServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TrafficLightServiceFutureStub(channel, callOptions);
        }
      };
    return TrafficLightServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * The greeter service definition.
   * </pre>
   */
  public interface AsyncService {

    /**
     * <pre>
     * Sends a command to change the traffic light state
     * </pre>
     */
    default void changeLightState(traffic.Traffic.ChangeLightRequest request,
        io.grpc.stub.StreamObserver<traffic.Traffic.ChangeLightResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getChangeLightStateMethod(), responseObserver);
    }

    /**
     * <pre>
     * Retrieves the current state of the traffic light
     * </pre>
     */
    default void getCurrentState(traffic.Traffic.Empty request,
        io.grpc.stub.StreamObserver<traffic.Traffic.TrafficLightState> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetCurrentStateMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service TrafficLightService.
   * <pre>
   * The greeter service definition.
   * </pre>
   */
  public static abstract class TrafficLightServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return TrafficLightServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service TrafficLightService.
   * <pre>
   * The greeter service definition.
   * </pre>
   */
  public static final class TrafficLightServiceStub
      extends io.grpc.stub.AbstractAsyncStub<TrafficLightServiceStub> {
    private TrafficLightServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TrafficLightServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TrafficLightServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends a command to change the traffic light state
     * </pre>
     */
    public void changeLightState(traffic.Traffic.ChangeLightRequest request,
        io.grpc.stub.StreamObserver<traffic.Traffic.ChangeLightResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getChangeLightStateMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Retrieves the current state of the traffic light
     * </pre>
     */
    public void getCurrentState(traffic.Traffic.Empty request,
        io.grpc.stub.StreamObserver<traffic.Traffic.TrafficLightState> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetCurrentStateMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service TrafficLightService.
   * <pre>
   * The greeter service definition.
   * </pre>
   */
  public static final class TrafficLightServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<TrafficLightServiceBlockingStub> {
    private TrafficLightServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TrafficLightServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TrafficLightServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends a command to change the traffic light state
     * </pre>
     */
    public traffic.Traffic.ChangeLightResponse changeLightState(traffic.Traffic.ChangeLightRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getChangeLightStateMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Retrieves the current state of the traffic light
     * </pre>
     */
    public traffic.Traffic.TrafficLightState getCurrentState(traffic.Traffic.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetCurrentStateMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service TrafficLightService.
   * <pre>
   * The greeter service definition.
   * </pre>
   */
  public static final class TrafficLightServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<TrafficLightServiceFutureStub> {
    private TrafficLightServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TrafficLightServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TrafficLightServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends a command to change the traffic light state
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<traffic.Traffic.ChangeLightResponse> changeLightState(
        traffic.Traffic.ChangeLightRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getChangeLightStateMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Retrieves the current state of the traffic light
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<traffic.Traffic.TrafficLightState> getCurrentState(
        traffic.Traffic.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetCurrentStateMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CHANGE_LIGHT_STATE = 0;
  private static final int METHODID_GET_CURRENT_STATE = 1;

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
        case METHODID_CHANGE_LIGHT_STATE:
          serviceImpl.changeLightState((traffic.Traffic.ChangeLightRequest) request,
              (io.grpc.stub.StreamObserver<traffic.Traffic.ChangeLightResponse>) responseObserver);
          break;
        case METHODID_GET_CURRENT_STATE:
          serviceImpl.getCurrentState((traffic.Traffic.Empty) request,
              (io.grpc.stub.StreamObserver<traffic.Traffic.TrafficLightState>) responseObserver);
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
          getChangeLightStateMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              traffic.Traffic.ChangeLightRequest,
              traffic.Traffic.ChangeLightResponse>(
                service, METHODID_CHANGE_LIGHT_STATE)))
        .addMethod(
          getGetCurrentStateMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              traffic.Traffic.Empty,
              traffic.Traffic.TrafficLightState>(
                service, METHODID_GET_CURRENT_STATE)))
        .build();
  }

  private static abstract class TrafficLightServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    TrafficLightServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return traffic.Traffic.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("TrafficLightService");
    }
  }

  private static final class TrafficLightServiceFileDescriptorSupplier
      extends TrafficLightServiceBaseDescriptorSupplier {
    TrafficLightServiceFileDescriptorSupplier() {}
  }

  private static final class TrafficLightServiceMethodDescriptorSupplier
      extends TrafficLightServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    TrafficLightServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (TrafficLightServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new TrafficLightServiceFileDescriptorSupplier())
              .addMethod(getChangeLightStateMethod())
              .addMethod(getGetCurrentStateMethod())
              .build();
        }
      }
    }
    return result;
  }
}
