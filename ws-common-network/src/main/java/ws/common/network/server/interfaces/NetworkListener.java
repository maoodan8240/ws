package ws.common.network.server.interfaces;

import ws.common.network.server.handler.tcp.MessageReceiveHolder;

public interface NetworkListener {

    /**
     * 接收到消息
     * 
     * @param receiveHolder
     */
    void onReceive(MessageReceiveHolder receiveHolder);

    /**
     * 离线状态
     * 
     * @param connection
     */
    void onOffline(Connection connection);

    /**
     * 断线后
     * 
     * @param connection
     */
    void onDisconnected(Connection connection);

    /**
     * 收到心跳包
     * 
     * @param connection
     */
    void onHeartBeating(Connection connection);

}
