package ws.common.table.table.implement.cell;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import ws.common.table.table.exception.CellParseFailedException;
import ws.common.table.table.interfaces.cell.ListCell;
import ws.common.table.table.utils.CellParser;

public class _ListCell<ElementType> implements ListCell<ElementType>, Serializable {
    private static final long serialVersionUID = 1L;

    private List<ElementType> elements;

    public _ListCell() {
    }

    public _ListCell(List<ElementType> _elements) {
        elements = _elements;
    }

    @Override
    public ElementType get(int index) {
        return elements.get(index);
    }

    @Override
    public List<ElementType> getAll() {
        return elements;
    }

    @Override
    public int size() {
        return elements.size();
    }

    @Override
    public void parse(String columnName, String cellValue, Class<ElementType> elementType) throws CellParseFailedException {
        elements = CellParser.parseListCell(columnName, cellValue, elementType).getAll();
    }

    @Override
    public String toString() {
        return "List[" + StringUtils.join(elements, ListCell.ELEMENT_SEPARATOR) + "]";
    }
}
