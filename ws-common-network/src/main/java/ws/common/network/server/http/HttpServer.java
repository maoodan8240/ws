package ws.common.network.server.http;

import ws.common.network.server.interfaces.NetworkContext;
import ws.common.network.server.interfaces.NetworkHandler;

public interface HttpServer {
    void start();

    void stop();

    NetworkContext getNetworkContext();

    NetworkHandler getNetworkHandler();

}
