package ws.common.network.exception;

public class TcpClientCloseException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public TcpClientCloseException(String message) {
        super(message, null);
    }

    public TcpClientCloseException(String message, Throwable cause) {
        super(message, cause);
    }

}
