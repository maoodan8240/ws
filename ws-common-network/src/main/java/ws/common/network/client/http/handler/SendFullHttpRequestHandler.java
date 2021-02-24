package ws.common.network.client.http.handler;

import java.net.URI;
import java.util.List;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.cookie.ClientCookieEncoder;
import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.DefaultCookie;
import ws.common.network.server.handler.http.SendFullHttpMessageHandler;
import ws.common.network.server.interfaces.NetworkContext;

public class SendFullHttpRequestHandler extends SendFullHttpMessageHandler {
    private URI uri;
    private String host;

    public SendFullHttpRequestHandler(NetworkContext networkContext, URI uri, String host) {
        super(networkContext);
        this.uri = uri;
        this.host = host;
    }

    @Override
    protected void write(List<Object> out, byte[] array_msg_all) {
        DefaultFullHttpRequest request = new DefaultFullHttpRequest(//
                HttpVersion.HTTP_1_1, //
                HttpMethod.GET, //
                uri.toASCIIString(), //
                Unpooled.wrappedBuffer(array_msg_all));
        // 构建http请求
        request.headers().set(HttpHeaders.Names.HOST, host);
        request.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
        request.headers().set(HttpHeaders.Names.ACCEPT_ENCODING, HttpHeaders.Values.GZIP);
        request.headers().set(HttpHeaders.Names.CONTENT_LENGTH, request.content().readableBytes());

        Cookie cookie = new DefaultCookie("my-cookie", "foo");
        cookie.setHttpOnly(true);
        request.headers().set(HttpHeaders.Names.COOKIE, ClientCookieEncoder.STRICT.encode(cookie));
        out.add(request);
    }
}
