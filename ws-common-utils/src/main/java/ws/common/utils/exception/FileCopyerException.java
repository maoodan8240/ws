package ws.common.utils.exception;

public class FileCopyerException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public FileCopyerException(String msg, Throwable t) {
        super(msg, t);
    }
}
