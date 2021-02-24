package ws.common.table.table.exception;

import ws.common.table.table.utils.CellParser;

/**
 * 单元格解析失败异常
 * 
 */
public class CellParseFailedException extends Exception {

    private static final long serialVersionUID = 1L;

    public CellParseFailedException(CellParser.CellType columnType, Class<?> elementType, String columnName, String columnValue) {
        this(columnType, elementType, columnName, columnValue, null);
    }

    public CellParseFailedException(CellParser.CellType columnType, Class<?> elementType, String columnName, String columnValue, Throwable cause) {
        super(new StringBuffer("不能解析单元格内容： {") //
                .append("  columnType: \"").append(columnType).append("\"") //
                .append(", elementType: \"").append(elementType).append("\"") //
                .append(", columnName: \"").append(columnName).append("\"") //
                .append(", columnValue: \"").append(columnValue).append("\"") //
                .append("}").toString(), cause);
    }
}
