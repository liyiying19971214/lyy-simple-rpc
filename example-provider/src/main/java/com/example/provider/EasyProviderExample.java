package com.example.provider;

import com.example.RpcApplication;
import com.example.registry.LocalRegistry;
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
        //启动web服务
        HttpServer server = new VertxHttpServer();
        server.doStart(RpcApplication.getConfig().getServerPort());
    }
}
