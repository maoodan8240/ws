package ws.common.network.client.http;

import com.google.protobuf.Message;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.commons.lang3.StringUtils;
import ws.common.network.client.http.initializer.HttpClientInitializer;
import ws.common.network.exception.HttpUrlParseException;
import ws.common.network.exception.OnlyHttpSupportedException;
import ws.common.network.server.config.interfaces.ConnConfig;
import ws.common.network.server.implement._NetworkContext;
import ws.common.network.server.implement._NetworkHandler;
import ws.common.network.server.interfaces.NetworkContext;
import ws.common.network.server.interfaces.NetworkHandler;

import java.net.URI;

public class _HttpClient implements HttpClient {
    private NetworkContext networkContext = new _NetworkContext();
    private String url;
    private EventLoopGroup group;
    private Channel channel;
    private ConnConfig connConfig;
    private NetworkHandler networkHandler = new _NetworkHandler();

    public _HttpClient(NetworkContext networkContext, String url) {
        this.networkContext = networkContext;
        this.url = url;
    }

    public _HttpClient(ConnConfig connConfig) {
        this.connConfig = connConfig;
    }


    public void connect() {
        group = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(group);
            b.channel(NioSocketChannel.class);
            b.handler(new ChannelInitializer<SocketChannel>() {
                protected void initChannel(SocketChannel ch) throws Exception {
                    new HttpClientInitializer(ch.pipeline()).initHttpChannel(connConfig, networkHandler, networkContext);
                }
            });
//            b.option(ChannelOption.SO_KEEPALIVE, true);
//            b.handler(new ChannelInitializer<SocketChannel>() {
//                @Override
//                public void initChannel(SocketChannel ch) throws Exception {
//                    // 客户端接收到的是httpResponse响应，所以要使用HttpResponseDecoder进行解码
//                    ch.pipeline().addLast(new HttpResponseDecoder());
//                    // 客户端发送的是httprequest，所以要使用HttpRequestEncoder进行编码
//                    ch.pipeline().addLast(new HttpRequestEncoder());
//                    ch.pipeline().addLast(new HttpClientInboundHandler());
//                }
//            });

            // Start the client.
//            ChannelFuture f = b.connect(connConfig.getHost(), connConfig.getPort()).sync();

//            URI uri = new URI("http://127.0.0.1:8844");
//            String msg = this.url;
//            DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET,
//                    uri.toASCIIString(), Unpooled.wrappedBuffer(msg.getBytes("UTF-8")));
//
//            // 构建http请求
//            request.headers().set(HttpHeaders.Names.HOST, connConfig.getHost());
//            request.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
//            request.headers().set(HttpHeaders.Names.CONTENT_LENGTH, request.content().readableBytes());
//            // 发送http请求
//            f.channel().write(request);
//            f.channel().flush();
            channel = b.connect(connConfig.getHost(), connConfig.getPort()).sync().channel();
//            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        finally {
//            group.shutdownGracefully();
//        }

    }

    @Override
    public NetworkContext getNetworkContext() {
        return this.networkContext;
    }

    @Override
    public NetworkHandler getNetworkHandler() {
        return this.networkHandler;
    }

    public void start() throws Exception {
        URI uri = new URI(url);
        String scheme = uri.getScheme();
        String host = uri.getHost();
        int port = uri.getPort();
        if (StringUtils.isEmpty(scheme) || StringUtils.isEmpty(host) || port <= 0) {
            throw new HttpUrlParseException(url);
        }
        if (!"http".equalsIgnoreCase(scheme)) {
            throw new OnlyHttpSupportedException(url);
        }
        group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.option(ChannelOption.SO_REUSEADDR, true);
            b.group(group).channel(NioSocketChannel.class).handler(new HttpClientInitializer(networkContext, uri, host));
            channel = b.connect(host, port).sync().channel();
            // channel.closeFuture().sync();
        } finally {
            // group.shutdownGracefully();
        }
    }

    @Override
    public void stop() throws Exception {
        channel.close();
        group.shutdownGracefully();
    }

    @Override
    public void sendMsg(Message message) throws Exception {
        if (channel.isRegistered() && channel.isActive()) {
            channel.writeAndFlush(message);
            channel.close();
            return;
        }
        stop();
        connect();
        sendMsg(message);
    }
}
