package xyz.crearts.iot.service;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import xyz.crearts.grps.GreeterGrpc;
import xyz.crearts.grps.Greeting;

@GrpcService
public class GreetingService extends GreeterGrpc.GreeterImplBase {
    @Override
    public void sayHello(Greeting.HelloRequest request, StreamObserver<Greeting.HelloReply> responseObserver) {
        responseObserver.onNext(Greeting.HelloReply.newBuilder().setMessage("Hello, " + request.getName()).build());
        responseObserver.onCompleted();
    }
}
