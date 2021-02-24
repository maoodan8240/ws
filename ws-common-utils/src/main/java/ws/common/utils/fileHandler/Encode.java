package ws.common.utils.fileHandler;

public enum Encode {
    UTF_8("UTF-8"), ISO_8859_1("ISO-8859-1"), GBK("GBK"), GB2312("GB2312");
    private String code;

    private Encode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}