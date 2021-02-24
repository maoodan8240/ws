package ws.common.utils.exception;

public class ClassFinderException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ClassFinderException(String msg, Throwable t) {
        super(msg, t);
    }
}
