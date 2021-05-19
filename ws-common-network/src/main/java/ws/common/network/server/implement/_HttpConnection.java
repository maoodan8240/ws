package ws.common.network.server.implement;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import ws.common.network.server.handler.tcp.MessageSendHolder;
import ws.common.network.server.interfaces.Connection;

public class _HttpConnection implements Connection {
    private ChannelHandlerContext context;
    private FullHttpRequest request;

    public _HttpConnection(ChannelHandlerContext context, FullHttpRequest request) {
        this.context = context;
        this.request = request;
    }

    @Override
    public void send(MessageSendHolder sendHolder) {
        context.writeAndFlush(sendHolder);
        if (!HttpHeaders.isKeepAlive(request)) {
            context.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void close() {
        context.close();
    }
}
