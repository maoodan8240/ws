package ws.common.network.server.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ws.common.network.exception.HttpServerStartException;
import ws.common.network.server.config.interfaces.ServerConfig;
import ws.common.network.server.implement._NetworkContext;
import ws.common.network.server.implement._NetworkHandler;
import ws.common.network.server.initializer.ServerInitializer;
import ws.common.network.server.interfaces.NetworkContext;
import ws.common.network.server.interfaces.NetworkHandler;

public class _HttpServer implements HttpServer {
    private static Log log = LogFactory.getLog(HttpServer.class);
    private ServerConfig serverConfig;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private NetworkContext networkContext = new _NetworkContext();
    private NetworkHandler networkHandler = new _NetworkHandler();


    public _HttpServer(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
    }


    @Override
    public void start() {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
//                    .childHandler(new ChannelInitializer<SocketChannel>() {
//                        @Override
//                        public void initChannel(SocketChannel ch) throws Exception {
//                            // server端发送的是httpResponse，所以要使用HttpResponseEncoder进行编码
////                            ch.pipeline().addLast(new HttpResponseEncoder());
////                            // server端接收到的是httpRequest，所以要使用HttpRequestDecoder进行解码
////                            ch.pipeline().addLast(new HttpRequestDecoder());
////                            ch.pipeline().addLast(new HttpServerInboundHandler());
//
//
//                        }
//                    })
                    //.handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ServerInitializer(serverConfig, networkHandler, networkContext));
//                    .option(ChannelOption.SO_BACKLOG, 128)
//                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = null;
            f = b.bind(serverConfig.getConnConfig().getHost(), serverConfig.getConnConfig().getPort()).sync();


//            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new HttpServerStartException("HttpServer 开启异常！ serverConfig=" + serverConfig.getConnConfig().toString() + "\n", e);
        }
//        finally {
//            workerGroup.shutdownGracefully();
//            bossGroup.shutdownGracefully();
//        }
    }

    @Override
    public void stop() {

    }

    @Override
    public NetworkContext getNetworkContext() {
        return networkContext;
    }

    @Override
    public NetworkHandler getNetworkHandler() {
        return networkHandler;
    }
}
