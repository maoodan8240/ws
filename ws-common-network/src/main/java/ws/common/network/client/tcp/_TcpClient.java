package ws.common.network.client.tcp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import ws.common.network.client.tcp.initializer.TcpClientInitializer;
import ws.common.network.exception.TcpClientCloseException;
import ws.common.network.exception.TcpClientStartException;
import ws.common.network.server.config.interfaces.ServerConfig;
import ws.common.network.server.implement._NetworkContext;
import ws.common.network.server.implement._NetworkHandler;
import ws.common.network.server.implement._TcpConnection;
import ws.common.network.server.interfaces.Connection;
import ws.common.network.server.interfaces.NetworkContext;
import ws.common.network.server.interfaces.NetworkHandler;

public final class _TcpClient implements TcpClient {
    private ServerConfig serverConfig;
    private NetworkHandler networkHandler = new _NetworkHandler();
    private NetworkContext networkContext = new _NetworkContext();

    private Channel channel;
    private Connection connection;

    public _TcpClient(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
    }

    @Override
    public void start() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap client = new Bootstrap();
            client.group(group).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
                protected void initChannel(SocketChannel ch) throws Exception {
                    new TcpClientInitializer(ch.pipeline()).initChannel(serverConfig, networkHandler, networkContext);
                };
            });
            ChannelFuture future = client.connect(serverConfig.getConnConfig().getHost(), serverConfig.getConnConfig().getPort()).sync();
            this.channel = future.channel();
            this.connection = new _TcpConnection(future.channel());
        } catch (Exception e) {
            throw new TcpClientStartException("TcpClient 开启异常！ serverConfig=" + serverConfig.getConnConfig().toString() + "\n", e);
        }
    }

    @Override
    public void stop() {
        try {
            channel.close();
            connection = null;
        } catch (Exception e) {
            throw new TcpClientCloseException("TcpClient 关闭异常！ serverConfig=" + serverConfig.getConnConfig().toString() + "\n", e);
        }
    }

    @Override
    public NetworkHandler getNetworkHandler() {
        return networkHandler;
    }

    @Override
    public NetworkContext getNetworkContext() {
        return networkContext;
    }

    @Override
    public Connection getConnection() {
        return connection;
    }
}
