package ws.common.table.data;

import java.util.List;
import java.util.Map;

public interface PlanningTableData {

    /**
     * 加载所有dict表的数据：表头、表的内容
     *
     * @throws Exception
     */
    void loadAllTablesData() throws Exception;

    /**
     * 加载所有变化列的表的数据
     *
     * @return
     * @throws Exception
     */
    List<String> loadChangedTablesData() throws Exception;

    /**
     * 所有表名-表的所有数据
     *
     * @return
     */
    Map<String, TableData> getTableNameToTableData();
}