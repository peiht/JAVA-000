package com.javaAdcance.netty.gateway.inbound;

import com.javaAdcance.netty.gateway.filter.HttpInboundFilter;
import com.javaAdcance.netty.gateway.outbound.HttpOutboundHandler;
import com.javaAdcance.netty.gateway.outbound.OkHttpOutboundHandler;
import com.javaAdcance.netty.gateway.router.HttpEndpointRouter;
import com.javaAdcance.netty.gateway.router.HttpRouter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.ReferenceCountUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author hitopei
 *
 * inbound handler
 */
public class HttpInboundHandler extends ChannelInboundHandlerAdapter {

    private OkHttpOutboundHandler okHttpOutboundHandler;
    private HttpEndpointRouter router;
    /**
     * 路由的三个地址
     */
    private final String[] routeArray = {"http://localhost:8801", "http://localhost:8802", "http://localhost:8803"};

    public HttpInboundHandler(String proxyServer) {
        List<String> routes = new ArrayList<>(Arrays.asList(routeArray));
        this.router = new HttpRouter();
        String routerUrl = router.route(routes);
        //每次访问输出访问的代理路径
        System.out.println("路由地址为：" + routerUrl);
        okHttpOutboundHandler = new OkHttpOutboundHandler(routerUrl);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            FullHttpRequest fullHttpRequest = (FullHttpRequest) msg;
            HttpInboundFilter filter = new HttpInboundFilter();
            filter.filter(fullHttpRequest, ctx);
            okHttpOutboundHandler.handle(fullHttpRequest, ctx);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }
}
