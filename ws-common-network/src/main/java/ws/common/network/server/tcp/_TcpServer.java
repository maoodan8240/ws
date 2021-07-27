package ws.common.network.server.tcp;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import ws.common.network.exception.TcpServerCloseException;
import ws.common.network.exception.TcpServerStartException;
import ws.common.network.server.config.interfaces.ChannelConfig;
import ws.common.network.server.config.interfaces.ServerConfig;
import ws.common.network.server.implement._NetworkContext;
import ws.common.network.server.implement._NetworkHandler;
import ws.common.network.server.initializer.ServerInitializer;
import ws.common.network.server.interfaces.NetworkContext;
import ws.common.network.server.interfaces.NetworkHandler;

public final class _TcpServer implements TcpServer {
    private ServerConfig serverConfig;
    private NetworkHandler networkHandler = new _NetworkHandler();
    private NetworkContext networkContext = new _NetworkContext();

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private Channel channel;

    public _TcpServer(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void start() {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            for (ChannelConfig channelConfig : serverConfig.getChannelConfigs()) {
                b.option(channelConfig.getOption(), channelConfig.getValue());
            }
            b.group(bossGroup, workerGroup)//
                    .channel(NioServerSocketChannel.class)//
                    // 设置NioServerSocketChannel的REGISTERED、BIND、ACTIVE、RECEIVED等信息的log级别
                    .handler(new LoggingHandler(LogLevel.INFO))//
                    .childHandler(new ServerInitializer(serverConfig, networkHandler, networkContext));
            ChannelFuture channelFuture = b.bind(serverConfig.getConnConfig().getHost(), serverConfig.getConnConfig().getPort()).sync();
            channel = channelFuture.channel();
            channel.closeFuture().sync();

        } catch (Exception e) {
            throw new TcpServerStartException("TcpServer 开启异常！ serverConfig=" + serverConfig.getConnConfig().toString() + "\n", e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    @Override
    public void stop() {
        try {
            channel.close();
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        } catch (Exception e) {
            throw new TcpServerCloseException("TcpServer 关闭异常！ serverConfig=" + serverConfig.getConnConfig().toString() + "\n", e);
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


}
