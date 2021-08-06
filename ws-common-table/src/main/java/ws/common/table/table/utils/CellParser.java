package ws.common.table.table.utils;

import org.apache.commons.lang3.StringUtils;
import ws.common.table.table.exception.CellParseFailedException;
import ws.common.table.table.implement.cell._ListCell;
import ws.common.table.table.implement.cell._TupleCell;
import ws.common.table.table.implement.cell._TupleListCell;
import ws.common.table.table.interfaces.cell.ListCell;
import ws.common.table.table.interfaces.cell.TupleCell;
import ws.common.table.table.interfaces.cell.TupleListCell;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 单元格解析器
 */
public class CellParser {

    /**
     * 单元格类型
     */
    public enum CellType {
        /**
         * 简单
         */
        SIMPLE,
        /**
         * 元组
         */
        TUPLE,
        /**
         * 列表
         */
        LIST,
        /**
         * 列表元组
         */
        TUPLE_LIST;
    }

    /**
     * 是否为空的单元格
     * <p>
     * <pre>
     * isEmptyCell(null)                = true
     * isEmptyCell("null")              = true
     * isEmptyCell("NULL")              = true
     * isEmptyCell("NulL")              = true
     * isEmptyCell(" null  ")           = true
     * isEmptyCell(" ")                 = true
     * isEmptyCell("      ")            = true
     * isEmptyCell(" 1")                = false
     * isEmptyCell("   ####11111111  ") = true
     * isEmptyCell("####11111111  ")    = true
     * isEmptyCell("####")              = true
     * isEmptyCell("  ####  ")          = true
     * isEmptyCell("###")               = false
     * isEmptyCell(" ###")              = false
     * isEmptyCell(" ###1")             = false
     * </pre>
     *
     * @param rawString
     * @return
     */
    public static boolean isEmptyCell(String rawString) {
        if (StringUtils.isEmpty(rawString)) {
            return true;
        }
        // 不区分大小写替换 null
        Pattern p = Pattern.compile("null", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(rawString);
        rawString = m.replaceAll("");
        rawString = rawString.replaceFirst("####.*$", "");
        if (StringUtils.isBlank(rawString)) {
            return true;
        }
        return false;
    }

    /**
     * 解析int, 默认值为 0
     *
     * @param rawString
     * @return
     */
    public static Integer parseInt(String rawString) {
        String value = parseString(rawString);
        return "".equals(value) ? 0 : new Integer(value.trim());
    }

    /**
     * 解析long, 默认值为 0
     *
     * @param rawString
     * @return
     */
    public static Long parseLong(String rawString) {
        String value = parseString(rawString);
        return "".equals(value) ? 0 : new Long(value.trim());
    }

    /**
     * 解析float, 默认值为 0.0
     *
     * @param rawString
     * @return
     */
    public static Double parseDouble(String rawString) {
        String value = parseString(rawString);
        return "".equals(value) ? 0.0 : new Double(value.trim());
    }

    /**
     * 解析bool，只有为1的时候才为true
     *
     * @param rawString
     * @return
     */
    public static Boolean parseBool(String rawString) {
        return parseInt(rawString) == 1;
    }

    /**
     * <pre>
     * {@link #isEmptyCell(String rawString)}方法判断为true的单元格解析为"",否则返回原内容
     * 此方法不会返回 null, 有可能返回空字符
     * </pre>
     *
     * @param rawString
     * @return
     */
    public static String parseString(String rawString) {
        return isEmptyCell(rawString) ? "" : rawString;
    }

    /**
     * 解析简单单元格
     *
     * @param columnName
     * @param columnNameMaptoValue
     * @param elementType
     * @return
     * @throws CellParseFailedException
     */
    public static <ElementType> ElementType parseSimpleCell(String columnName, Map<String, String> columnNameMaptoValue, Class<ElementType> elementType) throws CellParseFailedException {
        String cellValue = columnNameMaptoValue.get(columnName);
        return parseSimpleCell(columnName, cellValue, elementType);
    }


    /**
     * 解析简单单元格
     *
     * @param columnName
     * @param cellValue
     * @param elementType
     * @return
     * @throws CellParseFailedException
     */
    @SuppressWarnings("unchecked")
    public static <ElementType> ElementType parseSimpleCell(String columnName, String cellValue, Class<ElementType> elementType) throws CellParseFailedException {
        try {
            if (Integer.class.equals(elementType)) {
                return (ElementType) parseInt(cellValue);
            } else if (Long.class.equals(elementType)) {
                return (ElementType) parseLong(cellValue);
            } else if (Double.class.equals(elementType)) {
                return (ElementType) parseDouble(cellValue);
            } else if (Boolean.class.equals(elementType)) {
                return (ElementType) parseBool(cellValue);
            } else if (String.class.equals(elementType)) {
                return (ElementType) parseString(cellValue);
            }
            throw new Exception("不支持的数据类型！");
        } catch (Exception e) {
            throw new CellParseFailedException(CellType.SIMPLE, elementType, columnName, cellValue, e);
        }
    }

    /**
     * 解析元组单元格
     *
     * @param columnName
     * @param columnNameMaptoValue
     * @param elementType
     * @return
     * @throws CellParseFailedException
     */
    public static <ElementType> TupleCell<ElementType> parseTupleCell(String columnName, Map<String, String> columnNameMaptoValue, Class<ElementType> elementType) throws CellParseFailedException {
        String cellValue = columnNameMaptoValue.get(columnName);
        return parseTupleCell(columnName, cellValue, elementType);
    }

    /**
     * 解析元组单元格
     *
     * @param columnName
     * @param cellValue
     * @param elementType
     * @return
     * @throws CellParseFailedException
     */
    @SuppressWarnings("unchecked")
    public static <ElementType> TupleCell<ElementType> parseTupleCell(String columnName, String cellValue, Class<ElementType> elementType) throws CellParseFailedException {
        try {
            String[] values = null;
            int elementCount = 0;
            if (!isEmptyCell(cellValue)) {
                values = cellValue.split("[" + TupleCell.ELEMENT_SEPARATOR + "]");
                elementCount = values.length;
            }
            ElementType[] elements = (ElementType[]) Array.newInstance(elementType, elementCount);
            for (int i = 0; i < elementCount; i++) {
                elements[i] = parseSimpleCell(null, values[i], elementType);
            }
            return new _TupleCell<ElementType>(elements);
        } catch (Exception e) {
            throw new CellParseFailedException(CellType.TUPLE, elementType, columnName, cellValue, e);
        }
    }

    /**
     * 解析列表单元格
     *
     * @param columnName
     * @param columnNameMaptoValue
     * @param elementType
     * @return
     * @throws CellParseFailedException
     */
    public static <ElementType> ListCell<ElementType> parseListCell(String columnName, Map<String, String> columnNameMaptoValue, Class<ElementType> elementType) throws CellParseFailedException {
        String cellValue = columnNameMaptoValue.get(columnName);
        return parseListCell(columnName, cellValue, elementType);
    }

    /**
     * 解析列表单元格
     *
     * @param columnName
     * @param cellValue
     * @param elementType
     * @return
     * @throws CellParseFailedException
     */
    public static <ElementType> ListCell<ElementType> parseListCell(String columnName, String cellValue, Class<ElementType> elementType) throws CellParseFailedException {
        try {
            List<ElementType> elements = new ArrayList<ElementType>();
            if (!isEmptyCell(cellValue)) {
                for (String value : cellValue.split("[" + ListCell.ELEMENT_SEPARATOR + "]")) {
                    elements.add(parseSimpleCell(null, value, elementType));
                }
            }
            return new _ListCell<ElementType>(elements);
        } catch (Exception e) {
            throw new CellParseFailedException(CellType.LIST, elementType, columnName, cellValue, e);
        }
    }

    /**
     * 解析元组列表单元格
     *
     * @param columnName
     * @param columnNameMaptoValue
     * @param elementType
     * @return
     * @throws CellParseFailedException
     */
    public static <ElementType> TupleListCell<ElementType> parseTupleListCell(String columnName, Map<String, String> columnNameMaptoValue, Class<ElementType> elementType) throws CellParseFailedException {
        String cellValue = columnNameMaptoValue.get(columnName);
        return parseTupleListCell(columnName, cellValue, elementType);
    }

    /**
     * 解析元组列表单元格
     *
     * @param columnName
     * @param cellValue
     * @param elementType
     * @return
     * @throws CellParseFailedException
     */
    public static <ElementType> TupleListCell<ElementType> parseTupleListCell(String columnName, String cellValue, Class<ElementType> elementType) throws CellParseFailedException {
        try {
            List<TupleCell<ElementType>> elements = new ArrayList<TupleCell<ElementType>>();
            if (!isEmptyCell(cellValue)) {
                for (String value : cellValue.split("[" + ListCell.ELEMENT_SEPARATOR + "]")) {
                    elements.add(parseTupleCell(null, value, elementType));
                }
            }
            return new _TupleListCell<ElementType>(elements);
        } catch (Exception e) {
            throw new CellParseFailedException(CellType.TUPLE_LIST, elementType, columnName, cellValue, e);
        }
    }

}
