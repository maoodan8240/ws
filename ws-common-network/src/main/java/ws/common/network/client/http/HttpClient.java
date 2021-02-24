package ws.common.network.client.http;

import com.google.protobuf.Message;

public interface HttpClient {

    /**
     * 启动TcpClient
     * 
     * @throws Exception
     */
    void start() throws Exception;

    /**
     * 停止TcpClient
     * 
     * @throws Exception
     */
    void stop() throws Exception;

    /**
     * 发送protobuf信息
     * 
     * @param message
     * @throws Exception
     */
    void sendMsg(Message message) throws Exception;
}