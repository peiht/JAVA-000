package com.javaAdcance.netty.gateway.outbound;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import okhttp3.*;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.*;

/**
 * @author hitopei
 *
 * OKHttp实现的请求代理地址
 */
public class OkHttpOutboundHandler {

    private String backendUrl;
    private ExecutorService executorService;
    private OkHttpClient okHttpClient;

    public OkHttpOutboundHandler(String backendUrl){
        this.backendUrl = backendUrl.endsWith("/") ? backendUrl.substring(0, backendUrl.length() - 1) : backendUrl;

        int cores = Runtime.getRuntime().availableProcessors() * 2;
        long keepAliveTime = 1000;
        int queueSize = 2048;
        //拒绝策略
        RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();
        //线程池
        executorService = new ThreadPoolExecutor(
                cores, cores, keepAliveTime, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(queueSize),
                new NamedThreadFactory("proxyService"), handler
        );

        //初始化OKHttpClient
        okHttpClient = new OkHttpClient();
        okHttpClient.newBuilder().callTimeout(1000, TimeUnit.MILLISECONDS).build();
    }

    public void handle(final FullHttpRequest fullHttpRequest, final ChannelHandlerContext ctx) {
        HttpHeaders headers = fullHttpRequest.headers();
        //输出过滤器添加的header中的value
        System.out.println("过滤器中header添加的值为" + headers.get("nio"));
        final String url = this.backendUrl + fullHttpRequest.uri();
        executorService.submit(() -> fetchGet(fullHttpRequest, ctx, url));
    }

    /**
     * OKHttp 的请求方法
     * @param inbound
     * @param ctx
     * @param url
     */
    private void fetchGet(final FullHttpRequest inbound, final ChannelHandlerContext ctx, final String url) {
        Request request = new Request.Builder()
                .url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                call.cancel();
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                handleResponse(inbound, ctx, response);
            }
        });
    }

    /**
     * 处理response
     * @param request
     * @param ctx
     * @param resultResponse
     */
    private void handleResponse(final FullHttpRequest request, final ChannelHandlerContext ctx,
                                final Response resultResponse){
        FullHttpResponse response = null;
        try {
            //这里的resultResponse的body只能消费一次，再次调用会报状态的错误
            byte[] body = resultResponse.body().bytes();
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(body));
            response.headers().set("Content-Type", "application/json");
            response.headers().setInt("Content-Length", Integer.parseInt(resultResponse.header("Content-Length")));
        } catch (Exception e) {
            e.printStackTrace();
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NO_CONTENT);
            exceptionCaught(ctx, e);
        } finally {
            if (request != null) {
                if (HttpUtil.isKeepAlive(request)) {
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    ctx.write(response);
                }
            }
            ctx.flush();
        }
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
