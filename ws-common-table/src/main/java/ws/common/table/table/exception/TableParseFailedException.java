package ws.common.table.table.exception;

import ws.common.table.table.interfaces.table.Table;

/**
 * 表解析失败异常
 * 
 */
public class TableParseFailedException extends Exception {
    private static final long serialVersionUID = 1L;

    public TableParseFailedException(Table<?> table) {
        this(table, null);
    }

    public TableParseFailedException(Table<?> table, Throwable cause) {
        super(new StringBuffer("解析数据表异常：") //
                .append("表名称: \"").append(table.getName()).append("\"") //
                .append("}").toString(), cause);
    }
}
