package com.example.provider;

import com.example.RpcApplication;
import com.example.config.RegistryConfig;
import com.example.config.RpcConfig;
import com.example.model.ServiceMetaInfo;
import com.example.registry.LocalRegistry;
import com.example.registry.Registry;
import com.example.registry.RegistryFactory;
import com.example.server.HttpServer;
import com.example.server.VertxHttpServer;
import com.lyy.example.common.service.UserService;

/**
 * @author lyy
 * @Type EasyProviderExample.java
 * @Desc
 * @date 2024/12/5 21:21
 */
public class EasyProviderExample {
    public static void main(String[] args) {
        //框架初始化
        RpcApplication.init();
        //注册服务
        LocalRegistry.register(UserService.class.getName() , UserServiceImpl.class);
        //注册到注册中心
        String serviceName = UserService.class.getName();
        RpcConfig config = RpcApplication.getConfig();
        RegistryConfig registryConfig = config.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        registry.init(registryConfig);
        ServiceMetaInfo  serviceMetaInfo=new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(serviceName);
        serviceMetaInfo.setServicePort(config.getServerPort());
        serviceMetaInfo.setServiceHost(config.getServerHost());
        try {
            registry.registry(serviceMetaInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //启动web服务
        HttpServer server = new VertxHttpServer();
        server.doStart(RpcApplication.getConfig().getServerPort());
    }
}
