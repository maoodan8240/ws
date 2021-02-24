package ws.common.network.server.handler.tcp;

import com.google.protobuf.Message;

import ws.common.network.server.interfaces.Connection;

public class MessageReceiveHolder {
    private Connection connection;
    private Message message;
    private long timeRev;
    private long timeEnd;

    public MessageReceiveHolder(Connection connection, Message message, long timeRev, long timeEnd) {
        this.connection = connection;
        this.message = message;
        this.timeRev = timeRev;
        this.timeEnd = timeEnd;
    }

    public Connection getConnection() {
        return connection;
    }

    public Message getMessage() {
        return message;
    }

    public long getTimeRev() {
        return timeRev;
    }

    public long getTimeEnd() {
        return timeEnd;
    }

    public void clear() {
        connection = null;
        message = null;
        timeRev = 0;
        timeEnd = 0;
    }
}
