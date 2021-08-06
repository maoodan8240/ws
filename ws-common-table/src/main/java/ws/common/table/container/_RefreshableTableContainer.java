package ws.common.table.container;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.table.data.PlanningTableData;
import ws.common.table.data.TableData;
import ws.common.table.table.implement.container._HierarchyName;
import ws.common.table.table.implement.container._TableContainer;
import ws.common.table.table.implement.table._Table;
import ws.common.table.table.interfaces.Row;
import ws.common.table.table.interfaces.container.HierarchyName;
import ws.common.table.table.interfaces.container.TableContainer;
import ws.common.table.table.interfaces.table.Table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class _RefreshableTableContainer implements RefreshableTableContainer {
    private static Logger LOGGER = LoggerFactory.getLogger(_RefreshableTableContainer.class);
    private TableContainer tc = new _TableContainer();
    private Map<String, Class<? extends Row>> tableNameToRowClass = new HashMap<>();
    private PlanningTableData planningTableData;// 操作策划表数据
    private RefreshableTableContainerListener listener;// 策划表数据变化监听

    public void setPlanningTableData(PlanningTableData planningTableData) {
        this.planningTableData = planningTableData;
    }

    public void setListener(RefreshableTableContainerListener listener) {
        this.listener = listener;
    }

    @Override
    public <RowType extends Row> Table<RowType> get(Class<RowType> rowType) {
        return tc.get(rowType);
    }

    @Override
    public <RowType extends Row> Table<RowType> get(Class<RowType> rowType, String hierarchyTableName) {
        return tc.get(rowType, hierarchyTableName);
    }

    @Override
    public synchronized List<Class<? extends Row>> refresh() throws Exception {
        listener.preRefresh();
        List<Class<? extends Row>> changedRowClass = new ArrayList<>();
        List<String> changedTablesName = planningTableData.loadChangedTablesData();
        LOGGER.debug("有{}个表刷新了数据！changedTablesName={}", changedTablesName.size(), changedTablesName);
        List<String> noNeedClearTableNames = new ArrayList<>();
        for (String tableName : changedTablesName) {
            if (tableNameToRowClass.containsKey(tableName)) {
                if (tableName.equals("Table_SceneList")) {
                    addTable(tableNameToRowClass.get(tableName), planningTableData.getTableNameToTableData().get(tableName));
                    changedRowClass.add(tableNameToRowClass.get(tableName));
                } else {
                    int dramaId = Integer.valueOf(tableName.substring(tableName.lastIndexOf("_") + 1, tableName.length()));
                    String tabName = tableName.substring(0, tableName.lastIndexOf(dramaId + "") - 1);

                    addTable(tableNameToRowClass.get(tableName), planningTableData.getTableNameToTableData().get(tableName), dramaId, !noNeedClearTableNames.contains(tabName));
                    noNeedClearTableNames.add(tabName);
                }
            } else {
                LOGGER.warn("变化的表tableName={},不存在对应的Tab Class文件！");
            }
        }
        listener.postRefresh(changedRowClass);
        return changedRowClass;
    }

    @Override
    public <RowType extends Row> void addTable(Class<RowType> rowType, TableData tableDataTxt) throws Exception {
        addTable(null, rowType, tableDataTxt);
    }

    @Override
    public <RowType extends Row> void addTable(String hierarchyTableName, Class<RowType> rowType, TableData tableDataTxt) throws Exception {
        _addTable(hierarchyTableName, rowType, tableDataTxt);
    }

    @Override
    public <RowType extends Row> void addTable(Class<RowType> rowType, TableData tableDataTxt, int specialId) throws Exception {
        addTable(null, rowType, tableDataTxt, specialId);
    }

    @Override
    public <RowType extends Row> void addTable(String hierarchyTableName, Class<RowType> rowType, TableData tableDataTxt, int specialId) throws Exception {
        _addTable(hierarchyTableName, rowType, tableDataTxt, specialId);
    }

    @Override
    public <RowType extends Row> void addTable(Class<RowType> rowType, TableData tableDataTxt, int specialId, boolean isClear) throws Exception {
        _addTable(null, rowType, tableDataTxt, specialId, isClear);
    }

    @Override
    public <RowType extends Row> Table<RowType> get(String tableName) {
        return tc.get(tableName);
    }

    private <RowType extends Row> void _addTable(String hierarchyTableName, Class<RowType> rowType, TableData tableDataTxt) throws Exception {
        HierarchyName name = new _HierarchyName(hierarchyTableName);
        Table<RowType> table = new _Table<>(name.getFirstName(true));
        //将table数据装换到row中
        table.parse(rowType, tableDataTxt);
        if (name.isAnonymous()) {
            tc.addAnonymousTable(table);
        } else {
            tc.addNamedTable(name.getRemainingName(true), table);
        }
        tableNameToRowClass.put(tableDataTxt.getTableName(), rowType);
        LOGGER.debug("加载了策划表数据！tableName={} rowClass={} !", tableDataTxt.getTableName(), rowType);
    }

    private <RowType extends Row> void _addTable(String hierarchyTableName, Class<RowType> rowType, TableData tableDataTxt, int specialId, boolean isClear) throws Exception {
        HierarchyName name = new _HierarchyName(hierarchyTableName);
        Table<RowType> table;
        //将table数据装换到row中
        if (tc.has(rowType)) {
            table = tc.get(rowType);
            if (isClear) {
                table.clearAndIncrementParse(rowType, tableDataTxt, specialId);
            } else {
                table.incrementParse(rowType, tableDataTxt, specialId);
            }

        } else {
            table = new _Table<>(name.getFirstName(true));
            table.parse(rowType, tableDataTxt, specialId);
        }
        if (name.isAnonymous()) {
            tc.addAnonymousTable(table);
        } else {
            tc.addNamedTable(name.getRemainingName(true), table);
        }
        tableNameToRowClass.put(tableDataTxt.getTableName(), rowType);
        LOGGER.debug("加载了策划表数据！tableName={} rowClass={} !", tableDataTxt.getTableName(), rowType);
    }

    private <RowType extends Row> void _addTable(String hierarchyTableName, Class<RowType> rowType, TableData
            tableDataTxt, int specialId) throws Exception {
        HierarchyName name = new _HierarchyName(hierarchyTableName);
        Table<RowType> table;
        //将table数据装换到row中
        if (tc.has(rowType)) {
            table = tc.get(rowType);
            table.incrementParse(rowType, tableDataTxt, specialId);
        } else {
            table = new _Table<>(name.getFirstName(true));
            table.parse(rowType, tableDataTxt, specialId);

        }
        if (name.isAnonymous()) {
            tc.addAnonymousTable(table);
        } else {
            tc.addNamedTable(name.getRemainingName(true), table);
        }
        tableNameToRowClass.put(tableDataTxt.getTableName(), rowType);
        LOGGER.debug("加载了策划表数据！tableName={} rowClass={} !", tableDataTxt.getTableName(), rowType);
    }
}
