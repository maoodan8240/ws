package ws.common.network.exception;

public class HttpServerStartException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public HttpServerStartException(String message) {
        super(message, null);
    }

    public HttpServerStartException(String message, Throwable cause) {
        super(message, cause);
    }
}
