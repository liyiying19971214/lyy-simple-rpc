package com.example.proxy;

import java.lang.reflect.Proxy;

/**
 * @author lyy
 * @Type ProxyFactory.java
 * @Desc
 * @date 2024/12/5 23:19
 */
public class ProxyFactory {

     public  static <T> T getProxy(Class<T> serviceClass){
        return (T) Proxy.newProxyInstance(serviceClass.getClassLoader(),new Class[]{serviceClass},new ServiceProxy());

      }
}
