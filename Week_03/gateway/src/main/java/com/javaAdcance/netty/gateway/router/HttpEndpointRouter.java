package com.javaAdcance.netty.gateway.router;

import java.util.List;

/**
 * router接口
 * @author hitopei
 */
public interface HttpEndpointRouter {

    String route(List<String> endpoints);
}
