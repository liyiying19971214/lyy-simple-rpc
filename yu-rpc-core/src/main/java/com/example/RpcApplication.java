package com.example;

import com.example.config.RpcConfig;
import com.example.utils.Configutils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lyy
 * @Type RpcApplication.java
 * @Desc
 * @date 2024/12/8 17:24
 * 用来管理配置文件的
 */
@Slf4j
public class RpcApplication {
     private   static  volatile RpcConfig rpcConfig;

     public  static  RpcConfig init(){
         //双重锁机制
         if(rpcConfig == null){
             synchronized (RpcApplication.class){
                 if(rpcConfig == null){
                     //通过utils来加载
                     try {
                         rpcConfig  = Configutils.loadConfig(RpcConfig.class, "rpc");
                     }catch (Exception e){
                         e.printStackTrace();
                         //创建一个默认参数的
                         rpcConfig = new RpcConfig();
                     }
                 }
             }
         }
      return rpcConfig;
     }

     //用于对外提供内容
     public  static  RpcConfig getConfig(){
         if(rpcConfig == null){
             synchronized (RpcApplication.class){
                 if(rpcConfig == null){
                     init();
                 }
             }
         }
         return rpcConfig;
     }
}
