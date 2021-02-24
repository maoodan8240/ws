package ws.common.network.server.handler.tcp;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import ws.common.network.server.implement._TcpConnection;
import ws.common.network.server.interfaces.NetworkHandler;

public class IdleOfflineHandler extends ChannelDuplexHandler {
    private NetworkHandler networkHandler;

    public IdleOfflineHandler(NetworkHandler networkHandler) {
        this.networkHandler = networkHandler;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {
            IdleStateEvent idleEvt = (IdleStateEvent) evt;
            if (idleEvt.state() == IdleState.READER_IDLE) {
                broadcasOffline(ctx.channel());
            }
        }
    }

    private void broadcasOffline(Channel channel) {
        networkHandler.onOffline(new _TcpConnection(channel));
    }
}
