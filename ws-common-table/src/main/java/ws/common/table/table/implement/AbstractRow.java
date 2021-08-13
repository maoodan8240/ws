package ws.common.table.table.implement;

import ws.common.table.table.exception.CellParseFailedException;
import ws.common.table.table.exception.RowParseFailedException;
import ws.common.table.table.interfaces.Row;
import ws.common.table.table.interfaces.table.Table;
import ws.common.table.table.utils.CellParser;

import java.io.Serializable;
import java.util.Map;

public abstract class AbstractRow implements Row, Serializable {
    private static final long serialVersionUID = 1L;

    private Table<?> table;
    protected int id;
    protected int idx;
    protected int dramaId;

    @Override
    public Table<?> getTable() {
        return table;
    }

    @Override
    public void setTable(Table<?> table) {
        this.table = table;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getIdx() {
        return idx;
    }

    public Integer getDramaId() {
        return dramaId;
    }

    /**
     * 解析本行
     *
     * @param map 列名映射到列值 columnNameMaptoValue
     * @throws CellParseFailedException
     */
    public abstract void parseRow(Map<String, String> map) throws CellParseFailedException;

    @Override
    public final void parse(int rowNumber, Map<String, String> columnNameMaptoValue) throws RowParseFailedException {
        try {
            String idColumnName = table.getTableHeader().getIdColumnName();
            id = CellParser.parseSimpleCell(idColumnName, columnNameMaptoValue, Integer.class);
            idx = CellParser.parseSimpleCell("Idx", columnNameMaptoValue, Integer.class);
            dramaId = CellParser.parseSimpleCell("DramaId", columnNameMaptoValue, Integer.class);
            parseRow(columnNameMaptoValue);
        } catch (Exception e) {
            throw new RowParseFailedException(this, rowNumber, e);
        }
    }


}
