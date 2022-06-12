package com.mini.rpc.handler;

import com.mini.rpc.resp.ResponseSample;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.concurrent.EventExecutorGroup;

public class ResponseSampleEncoder extends MessageToByteEncoder<ResponseSample> {

    @Override

    protected void encode(ChannelHandlerContext ctx, ResponseSample msg, ByteBuf out) {

        if (msg != null) {

            out.writeBytes(msg.getCode().getBytes());

            out.writeBytes(msg.getData().getBytes());

            out.writeLong(msg.getTimestamp());

        }

    }

}