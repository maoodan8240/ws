package ws.common.network.exception;

public class TcpClientStartException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public TcpClientStartException(String message) {
        super(message, null);
    }

    public TcpClientStartException(String message, Throwable cause) {
        super(message, cause);
    }

}
