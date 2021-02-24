package ws.common.network.server.handler.http;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import java.util.List;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders.Values;
import ws.common.network.server.interfaces.NetworkContext;
import ws.common.network.utils.HttpUtils;

public class SendFullHttpResponseHandler extends SendFullHttpMessageHandler {
    public SendFullHttpResponseHandler(NetworkContext networkContext) {
        super(networkContext);
    }

    @Override
    protected void write(List<Object> out, byte[] array_msg_all) {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(array_msg_all));
        response.headers().set(CONTENT_TYPE, HttpUtils.CONTENT_TYPE_HTML_VALUE);
        response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
        response.headers().set(CONNECTION, Values.KEEP_ALIVE);
        out.add(response);
    }
}
