package ws.common.table.table.utils;

import ws.common.table.table.exception.CellParseFailedException;
import ws.common.table.table.exception.CellUnsupportedDataTypeException;
import ws.common.table.table.interfaces.cell.ListCell;
import ws.common.table.table.interfaces.cell.TupleCell;
import ws.common.table.table.interfaces.cell.TupleListCell;

public class CellUtils {

    /**
     * 
     * @param type
     *            int,float,bool,string,int2,arrayint,arrayint2,arraystring(没有string2)...
     * @param value
     * @return
     * @throws CellParseFailedException
     * @throws CellUnsupportedDataTypeException
     */
    public static Object parse(String type, String value) throws CellParseFailedException, CellUnsupportedDataTypeException {
        boolean isArray = isArray(type);
        boolean isTuple = isTuple(type);
        Class<?> elementType = getJavaType(type);
        if (elementType == null) {
            throw new CellUnsupportedDataTypeException(type);
        }
        if (isArray && isTuple) {
            return CellParser.parseTupleListCell(null, value, elementType);
        } else if (isArray) {
            return CellParser.parseListCell(null, value, elementType);
        } else if (isTuple) {
            return CellParser.parseTupleCell(null, value, elementType);
        } else {
            return CellParser.parseSimpleCell(null, value, elementType);
        }
    }

    private static boolean isArray(String type) {
        return type.matches("^array.*$");
    }

    private static boolean isTuple(String type) {
        return type.matches("^.*\\d+$");
    }

    private static Class<?> getJavaType(String type) {
        if (type.contains("int")) {
            return Integer.class;
        } else if (type.contains("float")) {
            return Double.class;
        } else if (type.contains("bool")) {
            return Boolean.class;
        } else if (type.contains("string")) {
            return String.class;
        } else {
            return null;
        }
    }

    /**
     * 针对 TupleCell ListCell TupleListCell 判断其单元格是否为空、""、null、####、"          "等
     * 
     * @param obj
     * @return
     */
    public static boolean isEmptyCell(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof TupleCell) {
            TupleCell<?> cell = (TupleCell<?>) obj;
            if (cell.size() == 0) {
                return true;
            }
        } else if (obj instanceof ListCell) {
            ListCell<?> cell = (ListCell<?>) obj;
            if (cell.size() == 0) {
                return true;
            }
        } else if (obj instanceof TupleListCell) {
            TupleListCell<?> cell = (TupleListCell<?>) obj;
            if (cell.size() == 0) {
                return true;
            }
            if (cell.size() == 1 && cell.get(0).size() == 0) {
                return true;
            }
        }
        return false;
    }
}
