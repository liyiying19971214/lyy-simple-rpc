package com.example.consumer;


import com.example.RpcApplication;
import com.example.config.RpcConfig;
import com.example.proxy.ProxyFactory;
import com.lyy.example.common.model.User;
import com.lyy.example.common.service.UserService;

/**
 * 简易服务消费者示例
 */
public class EasyConsumerExample {

    public static void main(String[] args) {


        // todo 需要获取 UserService 的实现类对象
        User user = new User();
        user.setName("lyy");
        // 调用
        UserService userService = ProxyFactory.getProxy(UserService.class);
        User newUser = userService.getUser(user);
        if (newUser != null) {
            System.out.println(newUser.getName());
        } else {
            System.out.println("user == null");
        }
    }
}
