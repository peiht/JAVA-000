package io.kimmking.rpcfx.client.netty;

import com.alibaba.fastjson.JSON;
import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.client.cache.NettyCache;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

/**
 * @author hitopei
 *
 * 客户端处理handler
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    private final RpcfxRequest request;
    private final String url;
    private final CountDownLatch latch;

    public ClientHandler(RpcfxRequest request, String url, CountDownLatch latch){
        this.request = request;
        this.url = url;
        this.latch = latch;
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception{
        URI uri = new URI(url);
        String json = JSON.toJSONString(request);
        FullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_0, HttpMethod.POST, uri.toASCIIString(),
                Unpooled.wrappedBuffer(json.getBytes(StandardCharsets.UTF_8)));
        request.headers().add(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        request.headers().add(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes())
        .add(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON);
        ctx.writeAndFlush(request);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof FullHttpResponse){
            FullHttpResponse response = (FullHttpResponse) msg;
            ByteBuf buf = response.content();
            String res = buf.toString(CharsetUtil.UTF_8);
            final String key = JSON.toJSONString(request);
            NettyCache.put(key, res);
            System.out.println("content : " + res);
            latch.countDown();
        }
        ctx.channel().close();
    }
}
