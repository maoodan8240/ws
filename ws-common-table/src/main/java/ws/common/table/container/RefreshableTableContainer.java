package ws.common.table.container;

import ws.common.table.data.PlanningTableData;
import ws.common.table.data.TableData;
import ws.common.table.table.interfaces.Row;
import ws.common.table.table.interfaces.table.Table;

import java.util.List;

public interface RefreshableTableContainer {

    /**
     * 设置策划表数据操作接口
     *
     * @param planningTableData
     */
    void setPlanningTableData(PlanningTableData planningTableData);

    /**
     * 设置监听器
     *
     * @param listener
     */
    void setListener(RefreshableTableContainerListener listener);

    /**
     * 按行类型查询表, 优先查询匿名表
     *
     * @param rowType
     * @return
     */
    <RowType extends Row> Table<RowType> get(Class<RowType> rowType);

    /**
     * 按行类型、和名字查询表, 不查询匿名表
     *
     * @param rowType
     * @param hierarchyTableName
     * @return
     */
    <RowType extends Row> Table<RowType> get(Class<RowType> rowType, String hierarchyTableName);

    /**
     * 刷新全部
     *
     * @return 被刷新的表
     */
    List<Class<? extends Row>> refresh() throws Exception;

    /**
     * 添加匿名表
     *
     * @param rowType      行对象类型
     * @param tableDataTxt 当前表的所有数据
     * @throws Exception
     */
    <RowType extends Row> void addTable(Class<RowType> rowType, TableData tableDataTxt) throws Exception;

    /**
     * 添加表
     *
     * @param hierarchyTableName 所属层级名
     * @param rowType            行对象类型
     * @param tableDataTxt       当前表的所有数据
     * @throws Exception
     */
    <RowType extends Row> void addTable(String hierarchyTableName, Class<RowType> rowType, TableData tableDataTxt) throws Exception;

    /**
     * 添加带有特殊Id的匿名表
     *
     * @param rowType
     * @param tableDataTxt
     * @param specialId
     * @param <RowType>
     * @throws Exception
     */
    <RowType extends Row> void addTable(Class<RowType> rowType, TableData tableDataTxt, int specialId) throws Exception;

    /**
     * 添加带有特殊Id的表
     *
     * @param hierarchyTableName
     * @param rowType
     * @param tableDataTxt
     * @param specialId
     * @param <RowType>
     * @throws Exception
     */
    <RowType extends Row> void addTable(String hierarchyTableName, Class<RowType> rowType, TableData tableDataTxt, int specialId) throws Exception;


    /**
     * 通过表名获取TableRow
     *
     * @param tableName
     * @return
     */
    <RowType extends Row> Table<RowType> get(String tableName);

    /**
     * 先清空再添加带有特殊Id的表
     *
     * @param rowType
     * @param tableDataTxt
     * @param specialId
     * @param isClear
     * @param <RowType>
     * @throws Exception
     */
    <RowType extends Row> void addTable(Class<RowType> rowType, TableData tableDataTxt, int specialId, boolean isClear) throws Exception;
}
