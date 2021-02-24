package ws.common.network.server.config.interfaces;

public interface ConnConfig {
    static enum ServerProtocolType {
        TCP, HTTP
    }

    /**
     * 获取当前Server的传输协议类型
     * 
     * @return
     */
    ServerProtocolType getProtocolType();

    /**
     * 获取连接host
     * 
     * @return
     */
    String getHost();

    /**
     * 获取连接port
     * 
     * @return
     */
    int getPort();

    /**
     * tcp长连接-离线时间，单位秒
     * 
     * @return
     */
    int getOfflineTimeout();

    /**
     * tcp长连接-断线时间，单位秒
     * 
     * @return
     */
    int getDisconnTimeout();

    /**
     * 连接上面一定时间内没有读到数据，则断开连接，单位秒
     * 
     * @return
     */
    int getReadTimeout();

}