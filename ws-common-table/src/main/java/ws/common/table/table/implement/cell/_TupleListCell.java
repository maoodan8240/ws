package ws.common.table.table.implement.cell;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import ws.common.table.table.exception.CellParseFailedException;
import ws.common.table.table.interfaces.cell.ListCell;
import ws.common.table.table.interfaces.cell.TupleCell;
import ws.common.table.table.interfaces.cell.TupleListCell;
import ws.common.table.table.utils.CellParser;

public class _TupleListCell<ElementType> implements TupleListCell<ElementType>, Serializable {
    private static final long serialVersionUID = 1L;

    private List<TupleCell<ElementType>> elements;

    public _TupleListCell() {
    }

    public _TupleListCell(List<TupleCell<ElementType>> _elements) {
        elements = _elements;
    }

    @Override
    public TupleCell<ElementType> get(int index) {
        return elements.get(index);
    }

    @Override
    public List<TupleCell<ElementType>> getAll() {
        return elements;
    }

    @Override
    public int size() {
        return elements.size();
    }

    @Override
    public TupleCell<ElementType> query(@SuppressWarnings("unchecked") ElementType... conditions) {
        return query(false, conditions);
    }

    @Override
    public TupleCell<ElementType> query(boolean reverse, @SuppressWarnings("unchecked") ElementType... conditions) {
        for (TupleCell<ElementType> element : elements) {
            if (element.match(reverse, conditions)) {
                return element;
            }
        }
        return null;
    }

    @Override
    public List<TupleCell<ElementType>> queryAll(@SuppressWarnings("unchecked") ElementType... conditions) {
        return queryAll(false, conditions);
    }

    @Override
    public List<TupleCell<ElementType>> queryAll(boolean reverse, @SuppressWarnings("unchecked") ElementType... conditions) {
        List<TupleCell<ElementType>> list = new ArrayList<TupleCell<ElementType>>();
        for (TupleCell<ElementType> element : elements) {
            if (element.match(reverse, conditions)) {
                list.add(element);
            }
        }
        return list;
    }

    @Override
    public List<ElementType> collect(int indexInTuple) {
        List<ElementType> list = new ArrayList<ElementType>();
        for (TupleCell<ElementType> element : elements) {
            list.add(element.get(indexInTuple));
        }
        return list;
    }

    @Override
    @SuppressWarnings("unchecked")
    public long sum(int indexInTuple) {
        long total = 0;
        List<Integer> list = (List<Integer>) collect(indexInTuple);
        for (Integer integer : list) {
            total += integer;
        }
        return total;
    }

    @Override
    public void parse(String columnName, String cellValue, Class<ElementType> elementType) throws CellParseFailedException {
        elements = CellParser.parseTupleListCell(columnName, cellValue, elementType).getAll();
    }

    @Override
    public String toString() {
        return "TupleList[" + StringUtils.join(elements, ListCell.ELEMENT_SEPARATOR) + "]";
    }
}
