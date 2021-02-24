package ws.common.network.server.config.implement;

import io.netty.channel.ChannelOption;
import ws.common.network.server.config.interfaces.ChannelConfig;

public class _ChannelConfig<T> implements ChannelConfig<T> {
    private ChannelOption<T> option;// 网络连接设定项
    private T value;// 网络连接设定值

    public _ChannelConfig(ChannelOption<T> option, T value) {
        this.option = option;
        this.value = value;
    }

    @Override
    public ChannelOption<T> getOption() {
        return option;
    }

    @Override
    public T getValue() {
        return value;
    }
}
