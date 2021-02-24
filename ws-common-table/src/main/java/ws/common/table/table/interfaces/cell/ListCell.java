package ws.common.table.table.interfaces.cell;

import java.util.List;

import ws.common.table.table.exception.CellParseFailedException;

/**
 * 列表单元格
 * 
 * @param <ElementType>
 *            元素类型
 */
public interface ListCell<ElementType> {

    /**
     * 元素分隔符
     */
    static final String ELEMENT_SEPARATOR = ",";

    /**
     * 查询, index从0开始
     * 
     * @param index
     * @return
     */
    ElementType get(int index);

    /**
     * 查询全部
     * 
     * @return
     */
    List<ElementType> getAll();

    /**
     * 元素个数
     * 
     * @return
     */
    int size();

    /**
     * 解析列表单元格
     * 
     * @param columnName
     * @param cellValue
     * @param elementType
     * @throws CellParseFailedException
     */
    void parse(String columnName, String cellValue, Class<ElementType> elementType) throws CellParseFailedException;
}
