package ws.common.table.table.implement.table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import ws.common.table.data.TableData;
import ws.common.table.data.TableDataRow;
import ws.common.table.table.exception.RowParseFailedException;
import ws.common.table.table.exception.TableParseFailedException;
import ws.common.table.table.interfaces.Row;
import ws.common.table.table.interfaces.table.Table;
import ws.common.table.table.interfaces.table.TableHeader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

public class _Table<RowType extends Row> implements Table<RowType>, Serializable {
    private static final long serialVersionUID = 1L;

    private String name; // 表名
    private Class<RowType> rowClass; // 行对象的类型
    private Map<Integer, RowType> idToRow = new ConcurrentSkipListMap<Integer, RowType>();// 所有：行号-行对象。有序的：Id从小到大排序
    private TableHeader tableHeader = new _TableHeader(); // 表头对象
    private int maxId;

    public _Table() {
    }

    public _Table(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean has(int id) {
        return idToRow.containsKey(id);
    }

    @Override
    public RowType get(int id) {
        return idToRow.get(id);
    }

    @Override
    public int size() {
        return idToRow.size();
    }

    @Override
    public List<RowType> values() {
        return new ArrayList<RowType>(idToRow.values());
    }

    @Override
    public Class<RowType> getRowClass() {
        return rowClass;
    }

    @Override
    public TableHeader getTableHeader() {
        return tableHeader;
    }

    @Override
    public void parse(Class<RowType> rowClass, TableData tableDataTxt) throws InstantiationException, IllegalAccessException, RowParseFailedException, TableParseFailedException {
        this.tableHeader.parse(tableDataTxt.getTableName(), tableDataTxt.getHeaderDatas());
        this.rowClass = rowClass;
        for (int rowIdx = 0; rowIdx < tableDataTxt.getRows().size(); rowIdx++) {
            TableDataRow row = tableDataTxt.getRows().get(rowIdx);
            Map<String, String> columnNameMaptoValueOfOneRow = new HashMap<>();// 一行中的 每个列名-每个单元格的值
            for (int columnIdx = 0; columnIdx < row.getCells().size(); columnIdx++) {
                String columnName = tableDataTxt.getHeaderDatas().get(columnIdx).getName();
                columnNameMaptoValueOfOneRow.put(columnName, row.getCells().get(columnIdx).getCell());// 每行的每个单元格数据
            }
            RowType rowType = (RowType) rowClass.newInstance();
            rowType.setTable(this);
            rowType.parse(rowIdx, columnNameMaptoValueOfOneRow);
            int rowId = rowType.getId();
            if (has(rowId)) {
                Throwable cause = new Exception("重复的行号： id=" + rowId);
                throw new TableParseFailedException(this, cause);
            }
            idToRow.put(rowId, rowType);
            columnNameMaptoValueOfOneRow.clear();
            columnNameMaptoValueOfOneRow = null;
        }
    }

    @Override
    public void parse(Class<RowType> rowClass, TableData tableDataTxt, int specialId) throws InstantiationException, IllegalAccessException, RowParseFailedException, TableParseFailedException {
        this.tableHeader.parse(tableDataTxt.getTableName(), tableDataTxt.getHeaderDatas());
        this.rowClass = rowClass;
        for (int rowIdx = 0; rowIdx < tableDataTxt.getRows().size(); rowIdx++) {
            TableDataRow row = tableDataTxt.getRows().get(rowIdx);
            Map<String, String> columnNameMaptoValueOfOneRow = new HashMap<>();// 一行中的 每个列名-每个单元格的值
            columnNameMaptoValueOfOneRow.put("DramaId", specialId + "");
            for (int columnIdx = 0; columnIdx < row.getCells().size(); columnIdx++) {
                String columnName = tableDataTxt.getHeaderDatas().get(columnIdx).getName();
                columnNameMaptoValueOfOneRow.put(columnName, row.getCells().get(columnIdx).getCell());// 每行的每个单元格数据
            }
            RowType rowType = (RowType) rowClass.newInstance();
            rowType.setTable(this);
            rowType.parse(rowIdx, columnNameMaptoValueOfOneRow);
            maxId += 1;
            idToRow.put(maxId, rowType);
            columnNameMaptoValueOfOneRow.clear();
            columnNameMaptoValueOfOneRow = null;
        }
    }

    @Override
    public void incrementParse(Class<RowType> rowClass, TableData tableDataTxt, int specialId) throws InstantiationException, IllegalAccessException, RowParseFailedException, TableParseFailedException {
        this.rowClass = rowClass;
        for (int rowIdx = 0; rowIdx < tableDataTxt.getRows().size(); rowIdx++) {
            maxId += 1;
            TableDataRow row = tableDataTxt.getRows().get(rowIdx);
            Map<String, String> columnNameMaptoValueOfOneRow = new HashMap<>();// 一行中的 每个列名-每个单元格的值
            columnNameMaptoValueOfOneRow.put("DramaId", specialId + "");
            for (int columnIdx = 0; columnIdx < row.getCells().size(); columnIdx++) {
                String columnName = tableDataTxt.getHeaderDatas().get(columnIdx).getName();
                if (columnName.equals("Id")) {
                    columnNameMaptoValueOfOneRow.put("Id", maxId + "");
                } else {
                    columnNameMaptoValueOfOneRow.put(columnName, row.getCells().get(columnIdx).getCell());// 每行的每个单元格数据
                }
            }
            RowType rowType = (RowType) rowClass.newInstance();
            rowType.setTable(this);
            rowType.parse(maxId, columnNameMaptoValueOfOneRow);
            int rowId = maxId;
            idToRow.put(rowId, rowType);
            columnNameMaptoValueOfOneRow.clear();
            columnNameMaptoValueOfOneRow = null;

        }
    }

    @Override
    public void clearAndIncrementParse(Class<RowType> rowClass, TableData tableDataTxt, int specialId) throws InstantiationException, IllegalAccessException, RowParseFailedException, TableParseFailedException {
        this.idToRow.clear();
        this.rowClass = rowClass;
        for (int rowIdx = 0; rowIdx < tableDataTxt.getRows().size(); rowIdx++) {
            maxId += 1;
            TableDataRow row = tableDataTxt.getRows().get(rowIdx);
            Map<String, String> columnNameMaptoValueOfOneRow = new HashMap<>();// 一行中的 每个列名-每个单元格的值
            columnNameMaptoValueOfOneRow.put("DramaId", specialId + "");
            for (int columnIdx = 0; columnIdx < row.getCells().size(); columnIdx++) {
                String columnName = tableDataTxt.getHeaderDatas().get(columnIdx).getName();
                if (columnName.equals("Id")) {
                    columnNameMaptoValueOfOneRow.put("Id", maxId + "");
                } else {
                    columnNameMaptoValueOfOneRow.put(columnName, row.getCells().get(columnIdx).getCell());// 每行的每个单元格数据
                }
            }
            RowType rowType = (RowType) rowClass.newInstance();
            rowType.setTable(this);
            rowType.parse(maxId, columnNameMaptoValueOfOneRow);
            int rowId = maxId;
            idToRow.put(rowId, rowType);
            columnNameMaptoValueOfOneRow.clear();
            columnNameMaptoValueOfOneRow = null;

        }
    }

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
        builder.append("name", name);
        builder.append("rowClass", rowClass);
        builder.append("tableHeader", tableHeader);
        builder.append("idToRow", idToRow);
        builder.append("size", size());
        return builder.toString();
    }
}
