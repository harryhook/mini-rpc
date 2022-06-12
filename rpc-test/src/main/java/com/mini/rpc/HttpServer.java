package com.mini.rpc;

import com.mini.rpc.handler.ExceptionHandler;
import com.mini.rpc.handler.HttpServerHandler;
import com.mini.rpc.handler.SampleInBoundHandler;
import com.mini.rpc.handler.SampleOutBoundHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

import java.net.InetSocketAddress;

public class HttpServer {

    public void start(int port) throws Exception {

        EventLoopGroup bossGroup = new NioEventLoopGroup();

        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {

            ServerBootstrap b = new ServerBootstrap();

            b.group(bossGroup, workerGroup)

                    .channel(NioServerSocketChannel.class)

                    .localAddress(new InetSocketAddress(port))


                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override

                        public void initChannel(SocketChannel ch) {

                            ch.pipeline()

                                    .addLast(new SampleInBoundHandler("SampleInBoundHandlerA", false))

                                    .addLast(new SampleInBoundHandler("SampleInBoundHandlerB", false))

                                    .addLast(new SampleInBoundHandler("SampleInBoundHandlerC", true))
                                    .addLast(new ExceptionHandler());
                            ch.pipeline()

                                    .addLast(new SampleOutBoundHandler("SampleOutBoundHandlerA"))

                                    .addLast(new SampleOutBoundHandler("SampleOutBoundHandlerB"))

                                    .addLast(new SampleOutBoundHandler("SampleOutBoundHandlerC"));

                        }

                    })

                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = b.bind().sync();

            System.out.println("Http Server started， Listening on " + port);

            f.channel().closeFuture().sync();

        } finally {

            workerGroup.shutdownGracefully();

            bossGroup.shutdownGracefully();

        }

    }

    public static void main(String[] args) throws Exception {

        new HttpServer().start(8087);

    }

}