package ws.common.utils.exception;

public class WsDateParseException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public WsDateParseException(String msg, Throwable t) {
        super(msg, t);
    }
}
