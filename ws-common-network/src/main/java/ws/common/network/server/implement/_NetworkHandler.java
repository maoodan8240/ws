package ws.common.network.server.implement;

import java.util.ArrayList;
import java.util.List;

import ws.common.network.server.handler.tcp.MessageReceiveHolder;
import ws.common.network.server.interfaces.Connection;
import ws.common.network.server.interfaces.NetworkHandler;
import ws.common.network.server.interfaces.NetworkListener;

public class _NetworkHandler implements NetworkHandler {
    private List<NetworkListener> networkListeners = new ArrayList<>();

    @Override
    public void addListener(NetworkListener networkListener) {
        networkListeners.add(networkListener);
    }

    @Override
    public void addListener(NetworkListener... networkListeners) {
        for (NetworkListener networkListener : networkListeners) {
            this.networkListeners.add(networkListener);
        }
    }

    @Override
    public void removeaddListener(NetworkListener networkListener) {
        this.networkListeners.remove(networkListener);
    }

    @Override
    public void removeaddListener(NetworkListener... networkListeners) {
        for (NetworkListener networkListener : networkListeners) {
            this.networkListeners.remove(networkListener);
        }
    }

    @Override
    public void onReceive(MessageReceiveHolder receiveHolder) {
        for (NetworkListener networkListener : networkListeners) {
            networkListener.onReceive(receiveHolder);
        }
    }

    @Override
    public void onOffline(Connection connection) {
        for (NetworkListener networkListener : networkListeners) {
            networkListener.onOffline(connection);
        }
    }

    @Override
    public void onDisconnected(Connection connection) {
        for (NetworkListener networkListener : networkListeners) {
            networkListener.onDisconnected(connection);
        }
    }

    @Override
    public void onHeartBeating(Connection connection) {
        for (NetworkListener networkListener : networkListeners) {
            networkListener.onHeartBeating(connection);
        }
    }

}
