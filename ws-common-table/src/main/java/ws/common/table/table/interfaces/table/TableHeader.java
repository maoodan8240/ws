package ws.common.table.table.interfaces.table;

import ws.common.table.data.TableDataHeader;

import java.util.List;
import java.util.Map;

/**
 * 表头
 */
public interface TableHeader {

    /**
     * 查询Id列列名
     *
     * @return
     */
    String getIdColumnName();

    /**
     * 查询全部列名
     *
     * @return
     */
    List<String> getAllColumnNames();

    /**
     * 查询列名与列类型映射
     *
     * @return
     */
    Map<String, String> getColumnNameToType();

    /**
     * 查询列名与列描述映射
     *
     * @return
     */
    Map<String, String> getColumnNameToDesc();

    /**
     * 查询列数
     *
     * @return
     */
    int getColumnSize();

    /**
     * 加载 tableheader 数据
     *
     * @param tableName
     * @param headerDatas
     */
    void parse(String tableName, List<TableDataHeader> headerDatas);
}
