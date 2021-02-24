package ws.common.network.server.handler.tcp;

import java.util.ArrayList;
import java.util.List;

import com.google.protobuf.Message;

public class MessageSendHolder {
    private Message message;
    private String msgAction;
    private List<Long> times = new ArrayList<>();

    public MessageSendHolder(Message message, String msgAction, List<Long> times) {
        this.message = message;
        this.msgAction = msgAction;
        this.times = times;
    }

    public Message getMessage() {
        return message;
    }

    public List<Long> getTimes() {
        return times;
    }

    public String getMsgAction() {
        return msgAction;
    }
}
