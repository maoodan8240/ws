package ws.common.utils.message.implement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ws.common.utils.message.interfaces.InnerMsg;
import ws.common.utils.message.interfaces.ResultCode;

public abstract class AbstractInnerMsg implements InnerMsg, Serializable {
    private static final long serialVersionUID = 1L;
    private List<String> receivers = new ArrayList<>();
    protected ResultCode resultCode;

    public AbstractInnerMsg() {
    }

    public AbstractInnerMsg(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }

    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    @Override
    public void addReceiver(String receiver) {
        this.receivers.add(receiver);
    }

    public List<String> getReceivers() {
        return new ArrayList<>(receivers);
    }
}
