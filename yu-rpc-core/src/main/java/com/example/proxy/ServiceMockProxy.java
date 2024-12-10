package com.example.proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@Slf4j
public class ServiceMockProxy implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Class<?> returnType = method.getReturnType();
        log.info("method内容等于："+method.getName());
        return   getDeaultObject(returnType);
    }

    private Object getDeaultObject(Class<?> type) {
        if (type.isPrimitive()){
            if (type==Boolean.class){
                return false;
            }else if(type==String.class){
                return "";
            }else if(type==int.class){
                return 0;
            }else  if(type==long.class){
                return  0L;
            }
        }
        return  null;
    }
}
