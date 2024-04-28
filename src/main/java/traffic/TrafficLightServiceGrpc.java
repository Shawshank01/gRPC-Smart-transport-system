package traffic;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * Define a service with RPC methods
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
  private static volatile io.grpc.MethodDescriptor<traffic.Traffic.TrafficCommand,
      traffic.Traffic.TrafficState> getManageTrafficMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ManageTraffic",
      requestType = traffic.Traffic.TrafficCommand.class,
      responseType = traffic.Traffic.TrafficState.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<traffic.Traffic.TrafficCommand,
      traffic.Traffic.TrafficState> getManageTrafficMethod() {
    io.grpc.MethodDescriptor<traffic.Traffic.TrafficCommand, traffic.Traffic.TrafficState> getManageTrafficMethod;
    if ((getManageTrafficMethod = TrafficLightServiceGrpc.getManageTrafficMethod) == null) {
      synchronized (TrafficLightServiceGrpc.class) {
        if ((getManageTrafficMethod = TrafficLightServiceGrpc.getManageTrafficMethod) == null) {
          TrafficLightServiceGrpc.getManageTrafficMethod = getManageTrafficMethod =
              io.grpc.MethodDescriptor.<traffic.Traffic.TrafficCommand, traffic.Traffic.TrafficState>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ManageTraffic"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  traffic.Traffic.TrafficCommand.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  traffic.Traffic.TrafficState.getDefaultInstance()))
              .setSchemaDescriptor(new TrafficLightServiceMethodDescriptorSupplier("ManageTraffic"))
              .build();
        }
      }
    }
    return getManageTrafficMethod;
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
   * Define a service with RPC methods
   * </pre>
   */
  public interface AsyncService {

    /**
     * <pre>
     * Define a bidirectional streaming RPC
     * </pre>
     */
    default io.grpc.stub.StreamObserver<traffic.Traffic.TrafficCommand> manageTraffic(
        io.grpc.stub.StreamObserver<traffic.Traffic.TrafficState> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getManageTrafficMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service TrafficLightService.
   * <pre>
   * Define a service with RPC methods
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
   * Define a service with RPC methods
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
     * Define a bidirectional streaming RPC
     * </pre>
     */
    public io.grpc.stub.StreamObserver<traffic.Traffic.TrafficCommand> manageTraffic(
        io.grpc.stub.StreamObserver<traffic.Traffic.TrafficState> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncBidiStreamingCall(
          getChannel().newCall(getManageTrafficMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service TrafficLightService.
   * <pre>
   * Define a service with RPC methods
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
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service TrafficLightService.
   * <pre>
   * Define a service with RPC methods
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
  }

  private static final int METHODID_MANAGE_TRAFFIC = 0;

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
        case METHODID_MANAGE_TRAFFIC:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.manageTraffic(
              (io.grpc.stub.StreamObserver<traffic.Traffic.TrafficState>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getManageTrafficMethod(),
          io.grpc.stub.ServerCalls.asyncBidiStreamingCall(
            new MethodHandlers<
              traffic.Traffic.TrafficCommand,
              traffic.Traffic.TrafficState>(
                service, METHODID_MANAGE_TRAFFIC)))
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
              .addMethod(getManageTrafficMethod())
              .build();
        }
      }
    }
    return result;
  }
}
