package ws.common.network.server.config.implement;

import java.util.ArrayList;
import java.util.List;

import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.AdaptiveRecvByteBufAllocator;
import io.netty.channel.ChannelOption;
import io.netty.channel.RecvByteBufAllocator;
import ws.common.network.server.config.interfaces.ChannelConfig;
import ws.common.network.server.config.interfaces.ConnConfig;
import ws.common.network.server.config.interfaces.ServerConfig;

public class _ServerConfig implements ServerConfig {
    private ConnConfig connConfig; // 连接设置
    private List<ChannelConfig<?>> channelConfigs = new ArrayList<>(); // 网络连接选项

    public _ServerConfig(ConnConfig connConfig) {
        this.connConfig = connConfig;
        initDefaultChannelConfig();
    }

    public _ServerConfig(ConnConfig connConfig, List<ChannelConfig<?>> channelConfigs) {
        this.connConfig = connConfig;
        this.channelConfigs = channelConfigs;
    }

    @Override
    public ConnConfig getConnConfig() {
        return connConfig;
    }

    @Override
    public List<ChannelConfig<?>> getChannelConfigs() {
        return channelConfigs;
    }

    /**
     * <pre>
     * this.channelConfigs.add(new _ChannelConfig<Integer>(ChannelOption.SO_TIMEOUT, new Integer(60)));
     * this.channelConfigs.add(new _ChannelConfig<Integer>(ChannelOption.CONNECT_TIMEOUT_MILLIS, new Integer(60)));
     * </pre>
     */
    private void initDefaultChannelConfig() {
        channelConfigs.clear();
        this.channelConfigs.add(new _ChannelConfig<Boolean>(ChannelOption.TCP_NODELAY, new Boolean(true)));
        this.channelConfigs.add(new _ChannelConfig<Integer>(ChannelOption.SO_BACKLOG, new Integer(1024)));// 连接队列的最大长度
        this.channelConfigs.add(new _ChannelConfig<Boolean>(ChannelOption.SO_KEEPALIVE, new Boolean(true)));
        this.channelConfigs.add(new _ChannelConfig<ByteBufAllocator>(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT));
        this.channelConfigs.add(new _ChannelConfig<RecvByteBufAllocator>(ChannelOption.RCVBUF_ALLOCATOR, AdaptiveRecvByteBufAllocator.DEFAULT));
    }
}
