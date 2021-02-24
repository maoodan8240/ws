package ws.common.network.server.config;

public class MessageFrameConfig {
    /**
     * 消息帧Header所占的固定字节数
     */
    public static int MESSAGE_HEADER_NEED_BYTE_NUM = 10;
    /**
     * 发送的消息体的长度超过该值，则进行压缩
     */
    public static int MESSAGE_MAX_COMPRESS_LEN = 100000;
    /**
     * 服务器 接收到的网络包最大的长度
     */
    public static int SERVER_RECEIVE_MESSAGE_PACKAGE_RECEIVED_MAX_LEN = 1000;
    /**
     * 客户端 接收到的网络包最大的长度
     */
    public static int CLIENT_RECEIVE_MESSAGE_PACKAGE_RECEIVED_MAX_LEN = 102400;// 100 KB
    /**
     * 消息帧的占位符字节数（当前byte）
     */
    public static int MESSAGE_PACKAGE_PLACEHOLDER_BYTE_NUM = 1;
    /**
     * 整个消息帧的长度的值所占的字节数（当前short）
     */
    public static int MESSAGE_PACKAGE_TOTAL_BYTE_NUM = 2;
    /**
     * 消息帧Header长度的值所占的节数数（当前short）
     */
    public static int MESSAGE_PACKAGE_HEADER_BYTE_NUM = 2;

}
