package ws.common.table.table.interfaces.table;

import ws.common.table.data.TableDataHeader;
import ws.common.table.table.exception.TableHeaderParseFailedException;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    List<String> getAllOrderColumnNames();

    /***
     * 获取所有列名
     */
    Set<String> getAllColumnNames();

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

    /***
     * 加载tableheader数据
     * @param file
     * @throws TableHeaderParseFailedException
     */
    void parse(File file) throws TableHeaderParseFailedException;
}
