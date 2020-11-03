package com.javaAdcance.netty.gateway.router;

import java.util.List;
import java.util.Random;

/**
 * @author hitopei
 *
 * 实现一个路由
 */
public class HttpRouter implements HttpEndpointRouter {
    @Override
    public String route(List<String> endpoints) {
        Random random = new Random();
        //随机从数组中取出一个路径
        return endpoints.get(random.nextInt(3));
    }
}
