package com.javaAdcance.netty.gateway;

import com.javaAdcance.netty.gateway.inbound.HttpInboundServer;

/**
 * netty网关服务启动
 *
 * @author hitopei
 */
public class NettyServerApplication {

    public final static String GATEWAY_NAME = "NIOGateway";
    public final static String GATEWAY_VERSION = "1.0.0";

    public static void main(String[] args) {

        String proxyServer = System.getProperty("proxyServer", "http://localhost:8801");
        String proxyPort = System.getProperty("proxyPort", "8888");

        int port = Integer.parseInt(proxyPort);
        System.out.println(GATEWAY_NAME + " " + GATEWAY_VERSION + " starting....");
        HttpInboundServer server = new HttpInboundServer(port, proxyServer);
        System.out.println(GATEWAY_NAME + " " + GATEWAY_VERSION + " starting at http://localhost:" + proxyServer);

        server.run();
    }
}
