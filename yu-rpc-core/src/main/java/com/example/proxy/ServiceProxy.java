package com.example.proxy;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.example.RpcApplication;
import com.example.model.RpcRequest;
import com.example.model.RpcResponse;
import com.example.serializer.JdkSerializer;
import com.example.serializer.Serializer;
import com.example.serializer.SerializerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author lyy
 * @Type UserServiceProxy.java
 * @Desc
 * @date 2024/12/5 23:03
 */
public class ServiceProxy implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //动态的序列化
        String key = RpcApplication.getConfig().getSerializer();
        Serializer serializer = SerializerFactory.getInstance(key);
        System.out.println("使用的实例是======"+        serializer.getClass().getName());
        RpcRequest rpcRequest = RpcRequest.builder()
                .methodName(method.getName())
                .serviceName(method.getDeclaringClass().getName())
                .parameterTypes(method.getParameterTypes())
                .args(args).build();
        try (HttpResponse execute = HttpRequest.post("http://127.0.0.1:8080")
                .body(serializer.serialize(rpcRequest)).execute()) {
            byte[] bytes = execute.bodyBytes();
            RpcResponse deserialize = serializer.deserialize(bytes, RpcResponse.class);
            return deserialize;
        }


    }
}
