package com.example.config;

import lombok.Data;

/**
 * @author lyy
 * @Type RpcConfig.java
 * @Desc
 * @date 2024/12/8 17:05
 */
@Data
public class RpcConfig {
    private  String  name="yu-rpc";
    private  String  version="1.0";
    private  String  serverHost="localhost";
    private  int    serverPort=8080;
}
