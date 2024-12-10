package com.example.server;

import com.example.model.RpcRequest;
import com.example.model.RpcResponse;
import com.example.registry.LocalRegistry;
import com.example.serializer.JdkSerializer;
import com.example.serializer.Serializer;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author lyy
 * @Type HttpServerHandler.java
 * @Desc
 * @date 2024/12/5 22:22
 */
public class HttpServerHandler  implements Handler<HttpServerRequest> {


    @Override
    public void handle(HttpServerRequest httpServerRequest) {
        final Serializer serializer=new JdkSerializer();
        System.out.printf("纪录日志");
        httpServerRequest.bodyHandler(body -> {
            byte []  aByte = body.getBytes();
            RpcRequest rpcRequest = null;
            try {
                rpcRequest = serializer.deserialize(aByte, RpcRequest.class);
            } catch (IOException e) {
                e.printStackTrace();
            }

            RpcResponse rpcResponse;
            Class<?> aClass = LocalRegistry.get(rpcRequest.getServiceName());
            try {
                Method method = aClass.getMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes());
                Object invoke = method.invoke(aClass.newInstance(), rpcRequest.getArgs());
                //封装结果
                rpcResponse = RpcResponse.builder().data(invoke).message("is ok").build();

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            doResponse(httpServerRequest,rpcResponse,serializer);
        });
    }

    //响应内容
    private void doResponse(HttpServerRequest httpServerRequest, RpcResponse rpcResponse, Serializer serializer) {
        //序列化
        byte[] serialize;
        try {
             serialize = serializer.serialize(rpcResponse);
            httpServerRequest.response().end(Buffer.buffer(serialize));
        } catch (IOException e) {
            //打印异常
            e.printStackTrace();
            httpServerRequest.response().end(Buffer.buffer());
        }
    }
}
