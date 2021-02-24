package ws.common.utils.message.interfaces;

import java.util.List;

/**
 * 内部消息
 */
public interface InnerMsg extends Message {

    ResultCode getResultCode();

    void addReceiver(String receiver);

    List<String> getReceivers();
}
