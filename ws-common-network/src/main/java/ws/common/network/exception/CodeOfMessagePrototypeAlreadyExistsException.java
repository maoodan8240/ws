package ws.common.network.exception;

import com.google.protobuf.Message;

public class CodeOfMessagePrototypeAlreadyExistsException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CodeOfMessagePrototypeAlreadyExistsException(int code, Class<? extends Message> type) {
        this(code, type, null);
    }

    public CodeOfMessagePrototypeAlreadyExistsException(int code, Class<? extends Message> type, Throwable cause) {
        super("code:" + code + ", type:" + type.getName(), cause);
    }
}
