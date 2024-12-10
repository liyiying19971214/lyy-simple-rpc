package com.example.proxy;

import com.example.RpcApplication;
import com.example.config.RpcConfig;

import java.lang.reflect.Proxy;

/**
 * @author lyy
 * @Type ProxyFactory.java
 * @Desc
 * @date 2024/12/5 23:19
 */
public class ProxyFactory {

     public  static <T> T getProxy(Class<T> serviceClass){
         if (RpcApplication.getConfig().getMock()){
             getMockProxy(serviceClass);
         }
        return (T) Proxy.newProxyInstance(serviceClass.getClassLoader(),new Class[]{serviceClass},new ServiceProxy());
      }



    public  static <T> T getMockProxy(Class<T> serviceClass){
        return (T) Proxy.newProxyInstance(serviceClass.getClassLoader(),new Class[]{serviceClass},new ServiceMockProxy());
    }

}
