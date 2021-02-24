package ws.common.network.client.tcp;

import ws.common.network.server.interfaces.Connection;
import ws.common.network.server.interfaces.NetworkContext;
import ws.common.network.server.interfaces.NetworkHandler;

public interface TcpClient {

    void start();

    void stop();

    NetworkHandler getNetworkHandler();

    NetworkContext getNetworkContext();

    Connection getConnection();

}