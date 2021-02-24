package ws.common.network.server;

import ws.common.network.server.interfaces.NetworkContext;
import ws.common.network.server.interfaces.NetworkHandler;

public interface TcpServer {

    /**
     * 启动TcpServer
     */
    void start();

    /**
     * 停止TcpServer
     */
    void stop();

    /**
     * 获取TcpServer网络处理对象
     * 
     * @return
     */
    NetworkHandler getNetworkHandler();

    /**
     * 获取TcpServer上下文
     * 
     * @return
     */
    NetworkContext getNetworkContext();

}