package ws.common.utils.exception;

public class DHCoderException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DHCoderException(String msg, Throwable t) {
        super(msg, t);
    }
}
