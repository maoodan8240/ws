package ws.common.table.table.exception;

public class CellUnsupportedDataTypeException extends Exception {
    private static final long serialVersionUID = 1L;

    public CellUnsupportedDataTypeException(String columnType) {
        super("不支持的数据类型=" + columnType + "!");
    }

    public CellUnsupportedDataTypeException(String columnName, String columnType) {
        super("columnName=" + columnName + ", 不支持的数据类型=" + columnType + "!");
    }

}
