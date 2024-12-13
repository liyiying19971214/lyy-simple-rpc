package com.example.proxy;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.example.RpcApplication;
import com.example.config.RegistryConfig;
import com.example.config.RpcConfig;
import com.example.model.RpcRequest;
import com.example.model.RpcResponse;
import com.example.model.ServiceMetaInfo;
import com.example.registry.Registry;
import com.example.registry.RegistryFactory;
import com.example.serializer.JdkSerializer;
import com.example.serializer.Serializer;
import com.example.serializer.SerializerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

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
        RpcConfig config = RpcApplication.getConfig();
        String key = config.getSerializer();
        Serializer serializer = SerializerFactory.getInstance(key);
        System.out.println("使用的实例是======"+ serializer.getClass().getName());

        //从etcd中去取数据内容信息
        RegistryConfig registryConfig = config.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        String  serviceName =method.getDeclaringClass().getName();
        List<ServiceMetaInfo> serviceMetaInfos = registry.serviceDiscovery(serviceName);
        if(CollectionUtil.isEmpty(serviceMetaInfos)){
            throw new RuntimeException("暂无服务地址");
        }

        String serviceAddress = serviceMetaInfos.get(0).getServiceAddress();
        RpcRequest rpcRequest = RpcRequest.builder()
                .methodName(method.getName())
                .serviceName(method.getDeclaringClass().getName())
                .parameterTypes(method.getParameterTypes())
                .args(args).build();
        //序列化
        byte[] bodyByte = serializer.serialize(rpcRequest);


        try (HttpResponse execute = HttpRequest.post(serviceAddress)
                .body(bodyByte).execute()) {
            byte[] bytes = execute.bodyBytes();
            RpcResponse rpcResponse = serializer.deserialize(bytes, RpcResponse.class);
            return rpcResponse.getData();
        }

    }
}
