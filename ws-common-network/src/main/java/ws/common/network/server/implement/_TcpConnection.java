package ws.common.network.server.implement;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import ws.common.network.server.handler.tcp.MessageSendHolder;
import ws.common.network.server.interfaces.Connection;

import java.io.Serializable;

public class _TcpConnection implements Connection, Serializable {
    private static final long serialVersionUID = 1L;
    private Channel channel;

    public _TcpConnection(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void send(MessageSendHolder sendHolder) {
        ChannelFuture f = channel.writeAndFlush(sendHolder);
        f.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
    }

    @Override
    public boolean equals(Object obj) {
        boolean equals = false;
        if (obj != null && _TcpConnection.class.isAssignableFrom(obj.getClass())) {
            _TcpConnection _conn = (_TcpConnection) obj;
            equals = (new EqualsBuilder().append(channel, _conn.channel)).isEquals();
        }
        return equals;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(channel).toHashCode();
    }
}
