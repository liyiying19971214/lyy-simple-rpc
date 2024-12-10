package com.example.server;


import io.vertx.core.Vertx;

/**
 * @author lyy
 * @Type VertxHttpServer.java
 * @Desc
 * @date 2024/12/5 21:03
 */
public class VertxHttpServer  implements HttpServer {
    @Override
    public void doStart(int port) {
        //创建实例
        Vertx vertx = Vertx.vertx();
        //创建Http服务器
        io.vertx.core.http.HttpServer server = vertx.createHttpServer();
        //绑定handel
        server.requestHandler(new HttpServerHandler());
        //启动端口监听
        server.listen(port,result->{
            if (result.succeeded()) {
                System.out.printf("连接成功");
            } else {
                System.out.printf("连接失败");
            }
        });
    }
}
