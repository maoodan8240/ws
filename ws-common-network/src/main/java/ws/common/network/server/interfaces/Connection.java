package ws.common.network.server.interfaces;

import ws.common.network.server.handler.tcp.MessageSendHolder;

public interface Connection {

    /**
     * 发送protobuf消息
     * 
     * @param sendHolder
     */
    void send(MessageSendHolder sendHolder);

    /**
     * 关闭连接
     */
    void close();
}
