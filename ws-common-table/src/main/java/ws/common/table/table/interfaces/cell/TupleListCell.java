package ws.common.table.table.interfaces.cell;

import java.util.List;

import ws.common.table.table.exception.CellParseFailedException;

/**
 * 元组列表单元格
 * 
 * @param <ElementType>
 *            元素类型
 */
public interface TupleListCell<ElementType> {

    /**
     * 查询, index从0开始
     * 
     * @param index
     * @return
     */
    TupleCell<ElementType> get(int index);

    /**
     * 查询全部
     * 
     * @return
     */
    List<TupleCell<ElementType>> getAll();

    /**
     * 元组个数
     * 
     * @return
     */
    int size();

    /**
     * equals {@link #query(boolean, Object...) query(false, conditions)}
     * 
     * @param conditions
     * @return
     */
    TupleCell<ElementType> query(@SuppressWarnings("unchecked") ElementType... conditions);

    /**
     * 查询
     * 
     * @param reverse
     *            是否逆序
     * @param conditions
     * @return
     */
    TupleCell<ElementType> query(boolean reverse, @SuppressWarnings("unchecked") ElementType... conditions);

    /**
     * equals {@link #queryAll(boolean, Object...) queryAll(false, conditions)}
     * 
     * @param conditions
     * @return
     */
    List<TupleCell<ElementType>> queryAll(@SuppressWarnings("unchecked") ElementType... conditions);

    /**
     * 查询全部
     * 
     * @param reverse
     *            是否逆序
     * @param conditions
     * @return
     */
    List<TupleCell<ElementType>> queryAll(boolean reverse, @SuppressWarnings("unchecked") ElementType... conditions);

    /**
     * 收集元组中的元素
     * 
     * @param indexInTuple
     * @return
     */
    List<ElementType> collect(int indexInTuple);

    /**
     * 对元组中的元素求和, 该方法假定元素类型为{@link Integer}
     * 
     * @param indexInTuple
     * @return
     */
    long sum(int indexInTuple);

    /**
     * 解析元组列表单元格
     * 
     * @param columnName
     * @param cellValue
     * @param elementType
     * @throws CellParseFailedException
     */
    void parse(String columnName, String cellValue, Class<ElementType> elementType) throws CellParseFailedException;
}
