package ws.common.utils.exception;

public class GetLocalAndNetIpException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public GetLocalAndNetIpException(String msg, Throwable t) {
        super(msg, t);
    }

}
