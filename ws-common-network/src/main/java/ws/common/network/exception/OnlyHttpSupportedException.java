package ws.common.network.exception;

public class OnlyHttpSupportedException extends Exception {
    private static final long serialVersionUID = 1L;

    public OnlyHttpSupportedException(String url) {
        super("只支持http请求！ url=" + url);
    }
}
