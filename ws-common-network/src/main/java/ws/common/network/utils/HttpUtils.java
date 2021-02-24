package ws.common.network.utils;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONNECTION;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpHeaders.Values;
import io.netty.util.CharsetUtil;

public class HttpUtils {
    public static final Logger LOGGER = LoggerFactory.getLogger(HttpUtils.class);
    public static final String CONTENT_TYPE_VALUE = "text/plain; charset=UTF-8";
    public static final String CONTENT_TYPE_HTML_VALUE = "text/html; charset=UTF-8";

    public static void sendHttpResponse(ChannelHandlerContext context, FullHttpRequest request, FullHttpResponse response) {
        try {
            if (response.getStatus().code() != 200) {
                ByteBuf buf = Unpooled.copiedBuffer(response.getStatus().toString(), CharsetUtil.UTF_8);
                response.content().writeBytes(buf);
                buf.release();
                HttpHeaders.setContentLength(response, response.content().readableBytes());
            }
            if (HttpHeaders.isKeepAlive(request)) {
                response.headers().set(CONNECTION, Values.KEEP_ALIVE);
            }
            context.writeAndFlush(response);
            if (!HttpHeaders.isKeepAlive(request) || response.getStatus().code() != 200) {
                context.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
            }
        } catch (Exception e) {
            LOGGER.error("发送HttpResponse异常！", e);
        } finally {
            context = null;
            request = null;
            response = null;
        }
    }
}
