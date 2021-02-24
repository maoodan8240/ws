package ws.common.utils.exception;

public class FileReaderException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public FileReaderException(String msg, Throwable t) {
        super(msg, t);
    }
}
