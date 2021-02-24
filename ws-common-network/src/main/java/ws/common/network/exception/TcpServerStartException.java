package ws.common.network.exception;

public class TcpServerStartException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public TcpServerStartException(String message) {
        super(message, null);
    }

    public TcpServerStartException(String message, Throwable cause) {
        super(message, cause);
    }

}
