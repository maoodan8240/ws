package ws.common.network.server.interfaces;

import ws.common.network.server.handler.tcp.MessageReceiveHolder;

public interface NetworkHandler {

    /**
     * 增加一个网络事件监听者
     * 
     * @param networkListener
     */
    void addListener(NetworkListener networkListener);

    /**
     * 增加一组网络事件监听者
     * 
     * @param networkListeners
     */
    void addListener(NetworkListener... networkListeners);

    /**
     * 移除一个网络事件监听者
     * 
     * @param networkListener
     */
    void removeaddListener(NetworkListener networkListener);

    /**
     * 移除一组网络渐渐监听者
     * 
     * @param networkListeners
     */
    void removeaddListener(NetworkListener... networkListeners);

    /**
     * 将网络事件(收到交互信息)广播给事件监听者
     * 
     * @param receiveHolder
     */
    void onReceive(MessageReceiveHolder receiveHolder);

    /**
     * 将网络事件(收到离线信息)广播给事件监听者
     * 
     * @param connection
     */
    void onOffline(Connection connection);

    /**
     * 将网络事件(收到掉线信息)广播给事件监or (NetworkListener networkListener :
     * tcpServerHandlerContext.getNetworkListeners()) { }听者
     * 
     * @param connection
     */
    void onDisconnected(Connection connection);

    /**
     * 将网络事件(收到心跳信息)广播给事件监听者
     * 
     * @param connection
     */
    void onHeartBeating(Connection connection);

}