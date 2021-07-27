package ws.common.network.server.initializer;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import ws.common.network.server.config.interfaces.ServerConfig;
import ws.common.network.server.interfaces.NetworkContext;
import ws.common.network.server.interfaces.NetworkHandler;

public class ServerInitializer extends ChannelInitializer<SocketChannel> {
    private ServerConfig serverConfig;
    private NetworkHandler networkHandler;
    private NetworkContext networkContext;

    public ServerInitializer(ServerConfig serverConfig, NetworkHandler networkHandler, NetworkContext networkContext) {
        this.serverConfig = serverConfig;
        this.networkHandler = networkHandler;
        this.networkContext = networkContext;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        switch (serverConfig.getConnConfig().getProtocolType()) {
            case TCP:
                new TcpServerInitializer(pipeline).initChannel(serverConfig, networkHandler, networkContext);
                break;
            case HTTP:
                new HttpServerInitializer(pipeline).initChannel(serverConfig, networkHandler, networkContext);
                break;
            default:
                break;
        }
    }
}
