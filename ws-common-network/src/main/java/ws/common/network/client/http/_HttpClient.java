package ws.common.network.client.http;

import com.google.protobuf.Message;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.commons.lang3.StringUtils;
import ws.common.network.client.http.initializer.HttpClientInitializer;
import ws.common.network.exception.HttpUrlParseException;
import ws.common.network.exception.OnlyHttpSupportedException;
import ws.common.network.server.interfaces.NetworkContext;

import java.net.URI;

public class _HttpClient implements HttpClient {
    private NetworkContext networkContext;
    private String url;
    private EventLoopGroup group;
    private Channel channel;

    public _HttpClient(NetworkContext networkContext, String url) {
        this.networkContext = networkContext;
        this.url = url;
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
        start();
        sendMsg(message);
    }
}
