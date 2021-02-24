package ws.common.network.server.config.interfaces;

import io.netty.channel.ChannelOption;

public interface ChannelConfig<T> {

    /**
     * 网络连接设定项
     * 
     * @return
     */
    ChannelOption<T> getOption();

    /**
     * 网络连接设定值
     * 
     * @return
     */
    T getValue();
}