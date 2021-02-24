package ws.common.network.exception;

public class TcpServerCloseException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public TcpServerCloseException(String message) {
        super(message, null);
    }

    public TcpServerCloseException(String message, Throwable cause) {
        super(message, cause);
    }

}
