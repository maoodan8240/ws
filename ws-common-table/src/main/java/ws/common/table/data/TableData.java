package ws.common.table.data;

import java.util.ArrayList;
import java.util.List;

public class TableData {
    /**
     * 表的最后修改时间
     */
    private long lastModifiedTime;
    /**
     * 表名称
     */
    private String tableName;
    /**
     * 列名|列的描述|列的类型，列名|列的描述|列的类型，列名|列的描述|列的类型 ...
     */
    private List<TableDataHeader> headerDatas = new ArrayList<>();
    /**
     * <pre>
     * 第一行内容为[列名|列名|列名]
     * 第一行[行一的第一列|行一的第二列|行一的第三列]，
     * 第二行...
     * 第三行...
     * </pre>
     */
    private List<TableDataRow> rows = new ArrayList<>();
    private List<List<String>> bodyDatas = new ArrayList<>();

    public TableData(String tableName) {
        this.tableName = tableName;
    }

    public long getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(long lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<TableDataHeader> getHeaderDatas() {
        return headerDatas;
    }

    public void setHeaderDatas(List<TableDataHeader> headerDatas) {
        this.headerDatas = headerDatas;
    }

    public List<List<String>> getBodyDatas() {
        return bodyDatas;
    }

    public void setBodyDatas(List<List<String>> bodyDatas) {
        this.bodyDatas = bodyDatas;
    }

    public List<TableDataRow> getRows() {
        return rows;
    }

    public void setRows(List<TableDataRow> rows) {
        this.rows = rows;
    }
}
