package ws.common.table.table.interfaces;

import ws.common.table.table.exception.RowParseFailedException;
import ws.common.table.table.interfaces.table.Table;

import java.util.Map;

/**
 * 行
 */
public interface Row {

    /**
     * 查询表
     *
     * @return
     */
    Table<?> getTable();

    /**
     * 设置表
     *
     * @param table
     */
    void setTable(Table<?> table);

    /**
     * 查询Id
     *
     * @return
     */
    int getId();

    /**
     * 每个剧本的自增id
     *
     * @return
     */
    int getIdx();

    /**
     * 解析行
     *
     * @param rowNumber            行号
     * @param columnNameMaptoValue 列名映射至列值
     * @throws RowParseFailedException
     */
    void parse(int rowNumber, Map<String, String> columnNameMaptoValue) throws RowParseFailedException;

    String toString();
}
