package ws.common.network.server.config.implement;

import ws.common.network.server.config.interfaces.ConnConfig;

public class _ConnConfig implements ConnConfig {

    private ServerProtocolType protocolType; // 传输协议类型

    private String host; // 连接host

    private int port; // 连接port

    private int offlineTimeout; // tcp长连接-离线时间，单位秒
    private int disconnTimeout; // tcp长连接-断线时间，单位秒
    private int readTimeout;// HTTP 连接上面一定时间内没有读到数据，则断开连接，单位秒

    public _ConnConfig(ServerProtocolType protocolType, String host, int port) {
        this.protocolType = protocolType;
        this.host = host;
        this.port = port;
    }

    public _ConnConfig(ServerProtocolType protocolType, String host, int port, int readTimeout) {
        this.protocolType = protocolType;
        this.host = host;
        this.port = port;
        this.readTimeout = readTimeout;
    }

    public _ConnConfig(ServerProtocolType protocolType, String host, int port, int offlineTimeout, int disconnTimeout) {
        this.protocolType = protocolType;
        this.host = host;
        this.port = port;
        this.offlineTimeout = offlineTimeout;
        this.disconnTimeout = disconnTimeout;
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public int getOfflineTimeout() {
        return offlineTimeout;
    }

    @Override
    public int getDisconnTimeout() {
        return disconnTimeout;
    }

    @Override
    public ServerProtocolType getProtocolType() {
        return protocolType;
    }

    @Override
    public int getReadTimeout() {
        return readTimeout;
    }
}
