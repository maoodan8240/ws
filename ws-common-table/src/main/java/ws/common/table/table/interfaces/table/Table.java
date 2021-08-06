package ws.common.table.table.interfaces.table;

import ws.common.table.data.TableData;
import ws.common.table.table.exception.RowParseFailedException;
import ws.common.table.table.exception.TableParseFailedException;
import ws.common.table.table.interfaces.Row;

import java.util.List;

/**
 * 表
 *
 * @param <RowType>
 */
public interface Table<RowType extends Row> {

    /**
     * 查询名字
     *
     * @return
     */
    String getName();

    /**
     * 是否包含指定id的行
     *
     * @param id
     * @return
     */
    boolean has(int id);

    /**
     * 查询行
     *
     * @param id
     * @return
     */
    RowType get(int id);

    /**
     * 查询行数
     *
     * @return
     */
    int size();

    /**
     * 查询全部
     *
     * @return
     */
    List<RowType> values();

    /**
     * 查询行类型
     *
     * @return
     */
    Class<RowType> getRowClass();

    /**
     * 查询表头
     *
     * @return
     */
    TableHeader getTableHeader();

    /**
     * 解析表文本数据
     *
     * @param rowClass     行对象类型
     * @param tableDataTxt 表的所有文本数据
     * @throws Exception
     */
    void parse(Class<RowType> rowClass, TableData tableDataTxt) throws InstantiationException, IllegalAccessException, RowParseFailedException, TableParseFailedException;

    /**
     * 解析表文本并增加特殊的Id
     *
     * @param rowClass
     * @param tableDataTxt
     * @param specialId
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws RowParseFailedException
     * @throws TableParseFailedException
     */
    void parse(Class<RowType> rowClass, TableData tableDataTxt, int specialId) throws InstantiationException, IllegalAccessException, RowParseFailedException, TableParseFailedException;

    /**
     * 增量解析表文本并增加特殊的Id
     *
     * @param rowClass
     * @param tableDataTxt
     * @param specialId
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws RowParseFailedException
     * @throws TableParseFailedException
     */
    void incrementParse(Class<RowType> rowClass, TableData tableDataTxt, int specialId) throws InstantiationException, IllegalAccessException, RowParseFailedException, TableParseFailedException;

    /**
     * 清空并解析表文本并增加特殊的Id
     */
    void clearAndIncrementParse(Class<RowType> rowClass, TableData tableDataTxt, int specialId) throws InstantiationException, IllegalAccessException, RowParseFailedException, TableParseFailedException;
}
