package ws.common.table.table.implement.cell;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import ws.common.table.table.exception.CellParseFailedException;
import ws.common.table.table.interfaces.cell.TupleCell;
import ws.common.table.table.utils.CellParser;

public class _TupleCell<ElementType> implements TupleCell<ElementType>, Serializable {
    private static final long serialVersionUID = 1L;

    private ElementType[] elements;

    public _TupleCell() {
    }

    public _TupleCell(ElementType[] _elements) {
        elements = _elements;
    }

    @Override
    public ElementType get(int index) {
        return elements[index];
    }

    @Override
    public ElementType[] getAll() {
        return elements;
    }

    @Override
    public int size() {
        return elements.length;
    }

    @Override
    public boolean match(@SuppressWarnings("unchecked") ElementType... conditions) {
        return match(false, conditions);
    }

    @Override
    public boolean match(boolean reverse, @SuppressWarnings("unchecked") ElementType... conditions) {
        int elementCount = (elements == null) ? 0 : elements.length;
        if (elementCount == 0) {
            return true;
        }
        int conditionCount = (conditions == null) ? 0 : conditions.length;
        if (conditionCount == 0) {
            return true;
        }
        int loopCount = Math.min(elementCount, conditionCount);
        for (int i = 0; i < loopCount; i++) {
            ElementType condition = conditions[i];
            if (condition == null) { // 跳过该位置
                continue;
            }
            ElementType element = reverse ? elements[elementCount - i - 1] : elements[i];
            if (element == null || !element.equals(condition)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void parse(String columnName, String cellValue, Class<ElementType> elementType) throws CellParseFailedException {
        elements = CellParser.parseTupleCell(columnName, cellValue, elementType).getAll();
    }

    @Override
    public String toString() {
        return "Tuple[" + StringUtils.join(elements, TupleCell.ELEMENT_SEPARATOR) + "]";
    }
}
