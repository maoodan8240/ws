package ws.common.network.server.initializer;

import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import ws.common.network.server.config.MessageFrameConfig;
import ws.common.network.server.config.interfaces.ServerConfig;
import ws.common.network.server.handler.tcp.BuildPackageUseForClientHandler;
import ws.common.network.server.handler.tcp.IdleDisconnectedHandler;
import ws.common.network.server.handler.tcp.IdleOfflineHandler;
import ws.common.network.server.handler.tcp.ParsePackageFromClientHandler;
import ws.common.network.server.interfaces.NetworkContext;
import ws.common.network.server.interfaces.NetworkHandler;

public class TcpServerInitializer {
    private ChannelPipeline pipeline;

    public TcpServerInitializer(ChannelPipeline pipeline) {
        this.pipeline = pipeline;
    }

    public void initChannel(ServerConfig serverConfig, NetworkHandler networkHandler, NetworkContext networkContext) throws Exception {
        /**
         * 客户端连接异常断开处理[readerIdleTime 服务器一定时间内未接受到客户端消息, writerIdleTime 服务器一定时间内向客户端发送消息, allIdleTime 同时没有读写的时间]
         */
        pipeline.addLast(new IdleStateHandler(serverConfig.getConnConfig().getOfflineTimeout(), 0, 0));
        pipeline.addLast(new IdleOfflineHandler(networkHandler));
        pipeline.addLast(new IdleStateHandler(serverConfig.getConnConfig().getDisconnTimeout(), 0, 0));
        pipeline.addLast(new IdleDisconnectedHandler(networkHandler));
        pipeline.addLast(new BuildPackageUseForClientHandler(networkContext)); // OUT -> 组装发送的报文
        pipeline.addLast(new LengthFieldBasedFrameDecoder(
                // 默认为大端处理
                MessageFrameConfig.SERVER_RECEIVE_MESSAGE_PACKAGE_RECEIVED_MAX_LEN, // 消息的最大长度
                MessageFrameConfig.MESSAGE_PACKAGE_PLACEHOLDER_BYTE_NUM, // HDR1(1) 消息帧的占位符字节数(位于发送的字节数组中下标的位置)长度域
                MessageFrameConfig.MESSAGE_PACKAGE_PLACEHOLDER_INT_NUM, // Length(2) 整个消息帧的长度的值所占的字节数（当前short）(长度域的自己的字节数长度)
                -(MessageFrameConfig.MESSAGE_PACKAGE_PLACEHOLDER_BYTE_NUM + MessageFrameConfig.MESSAGE_PACKAGE_TOTAL_BYTE_NUM), // -(HDR1(1) + Length(2))
                (MessageFrameConfig.MESSAGE_PACKAGE_PLACEHOLDER_BYTE_NUM + MessageFrameConfig.MESSAGE_PACKAGE_PLACEHOLDER_INT_NUM), // (HDR1(1) + Length(2))
                true));
        pipeline.addLast(new ParsePackageFromClientHandler(networkHandler, networkContext));
    }
}
