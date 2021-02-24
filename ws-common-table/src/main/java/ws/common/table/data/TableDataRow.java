package ws.common.table.data;

import java.util.ArrayList;
import java.util.List;

public class TableDataRow {
    private int rowIdx;
    private List<TableDataCell> cells = new ArrayList<>();

    public List<TableDataCell> getCells() {
        return cells;
    }

    public void setCells(List<TableDataCell> cells) {
        this.cells = cells;
    }

    public int getRowIdx() {
        return rowIdx;
    }

    public void setRowIdx(int rowIdx) {
        this.rowIdx = rowIdx;
    }
}
