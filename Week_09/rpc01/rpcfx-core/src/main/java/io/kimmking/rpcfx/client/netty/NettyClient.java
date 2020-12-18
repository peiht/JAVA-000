package io.kimmking.rpcfx.client.netty;

import com.alibaba.fastjson.JSON;
import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.client.cache.NettyCache;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;

import java.util.concurrent.CountDownLatch;

/**
 * @author hitopei
 * netty 实现请求rpc
 */
public class NettyClient {

    /**
     * rpc 请求
     */
    private final RpcfxRequest request;
    /**
     * 请求路径
     */
    private final String url;
    /**
     * 请求host
     */
    private final String host;
    /**
     * 请求端口
     */
    private final int port;

    private final CountDownLatch latch = new CountDownLatch(1);


    public NettyClient(RpcfxRequest request, String url, String host, int port) {
        this.host = host;
        this.url = url;
        this.port = port;
        this.request = request;
    }

    /**
     *
     * @return res
     */
    public String doRequest(){
        String result;
        Bootstrap client = new Bootstrap();

        EventLoopGroup group = new NioEventLoopGroup();
        client.group(group).remoteAddress(host, port);
        client.channel(NioSocketChannel.class);
        client.handler(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch){
                ch.pipeline().addLast(new HttpClientCodec());
                ch.pipeline().addLast(new HttpObjectAggregator(65536));
                ch.pipeline().addLast(new HttpContentDecompressor());
                ch.pipeline().addLast(new ClientHandler(request, url, latch));
            }
        });

        try {
            ChannelFuture future = client.connect().sync();
            latch.await();
            System.out.println(NettyCache.size());
            result = NettyCache.get(JSON.toJSONString(request));
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "";
        } finally {
            group.shutdownGracefully();
        }
        return result;
    }


}
