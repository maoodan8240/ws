package ws.common.utils.exception;

public class FileWriterException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public FileWriterException(String msg, Throwable t) {
        super(msg, t);
    }
}
