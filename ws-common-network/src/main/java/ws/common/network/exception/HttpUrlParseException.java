package ws.common.network.exception;

public class HttpUrlParseException extends Exception {
    private static final long serialVersionUID = 1L;

    public HttpUrlParseException(String url) {
        super("解析http url 失败！ url=" + url);
    }
}
