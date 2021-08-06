package ws.common.table.table.interfaces.container;

import ws.common.table.table.interfaces.Row;
import ws.common.table.table.interfaces.table.Table;

/**
 * 表容器
 */
public interface TableContainer {

    /**
     * 查询名字
     *
     * @return
     */
    String getName();

    /**
     * 设置名字
     *
     * @param name
     */
    void setName(String name);

    /**
     * 查询父容器
     *
     * @return
     */
    TableContainer getOwner();

    /**
     * 设置父容器
     *
     * @param tableContainer
     */
    void setOwner(TableContainer tableContainer);

    /**
     * 是否包含指定行类型的匿名表
     *
     * @param rowType
     * @return
     */
    <RowType extends Row> boolean hasAnonymousTable(Class<RowType> rowType);

    /**
     * equals {@link #has(Class, boolean) has(rowType, true)}
     *
     * @param rowType
     * @return
     */
    <RowType extends Row> boolean has(Class<RowType> rowType);

    /**
     * 是否包含指定行类型的表, 优先查询匿名表
     *
     * @param rowType
     * @param recursive 是否递归查询子容器
     * @return
     */
    <RowType extends Row> boolean has(Class<RowType> rowType, boolean recursive);

    /**
     * equals {@link #has(Class, String) has(null, hierarchyTableName)}
     *
     * @param hierarchyTableName
     * @return
     */
    boolean has(String hierarchyTableName);

    /**
     * 是否包含指定行类型、和名字的表, 不查询匿名表
     *
     * @param rowType
     * @param hierarchyTableName
     * @return
     */
    <RowType extends Row> boolean has(Class<RowType> rowType, String hierarchyTableName);

    /**
     * 按行类型查询匿名表
     *
     * @param rowType
     * @return
     */
    <RowType extends Row> Table<RowType> getAnonymousTable(Class<RowType> rowType);

    /**
     * equals {@link #get(Class, boolean) get(rowType, true)}
     *
     * @param rowType
     * @return
     */
    <RowType extends Row> Table<RowType> get(Class<RowType> rowType);

    /**
     * 按行类型查询表, 优先查询匿名表
     *
     * @param rowType
     * @param recursive 是否递归查询子容器
     * @return
     */
    <RowType extends Row> Table<RowType> get(Class<RowType> rowType, boolean recursive);

    /**
     * 按行类型、和名字查询表, 不查询匿名表
     *
     * @param rowType
     * @param hierarchyTableName
     * @return
     */
    <RowType extends Row> Table<RowType> get(Class<RowType> rowType, String hierarchyTableName);

    /**
     * 按表名返回
     *
     * @param tableName
     * @param <RowType>
     * @return
     */
    <RowType extends Row> Table<RowType> get(String tableName);

    /**
     * 按行类型设置匿名表
     *
     * @param table
     */
    void addAnonymousTable(Table<? extends Row> table);
//
//    /**
//     * 按行类型设置带有特殊Id的匿名表
//     *
//     * @param table
//     * @param specialId
//     */
//    void addAnonymousTable(Table<? extends Row> table, int specialId);

    /**
     * equals {@link #setNamedTable(String, Table) setNamedTable(null, table)}
     *
     * @param table
     */
    void addNamedTable(Table<? extends Row> table);

    /**
     * 设置命名表
     *
     * @param hierarchySubContainerName
     * @param table
     */
    void addNamedTable(String hierarchySubContainerName, Table<? extends Row> table);

//    /**
//     * 设置命名带有特殊Id的表
//     *
//     * @param hierarchySubContainerName
//     * @param table
//     * @param specialId
//     */
//    void addNamedTable(String hierarchySubContainerName, Table<? extends Row> table, int specialId);

//    /**
//     * equals {@link #setNamedTable(String, Table) setNamedTable(null, table)}
//     *
//     * @param table
//     */
//    void addNamedTable(Table<? extends Row> table, int specialId);

    /**
     * 设置匿名表、或命名表
     *
     * @param table
     */
    void addTable(Table<? extends Row> table);

//    /**
//     * 设置匿名表或命名表并带有特殊Id
//     *
//     * @param table
//     * @param specialId
//     */
//    void addTable(Table<? extends Row> table, int specialId);

    /**
     * 是否包含指定子容器
     *
     * @param hierarchySubContainerName
     * @return
     */
    boolean hasSubContainer(String hierarchySubContainerName);

    /**
     * 查询子容器
     *
     * @param hierarchySubContainerName
     * @return
     */
    TableContainer getSubContainer(String hierarchySubContainerName);

    /**
     * 设置子容器
     *
     * @param hierarchySubContainerName
     * @param subContainer
     */
    void addSubContainer(String hierarchySubContainerName, TableContainer subContainer);

    /**
     * 建立子容器
     *
     * @param hierarchySubContainerName
     */
    void buildSubContainer(String hierarchySubContainerName);

    /**
     * equals {@link #subContainerSize(boolean) subContainerSize(false)}
     *
     * @return
     */
    int subContainerSize();

    /**
     * 子容器总数
     *
     * @param recursive 是否递归查询子容器
     * @return
     */
    int subContainerSize(boolean recursive);

    /**
     * equals {@link #anonymousTableSize(boolean) anonymousTableSize(false)}
     *
     * @return
     */
    int anonymousTableSize();

    /**
     * 匿名表总数
     *
     * @param recursive 是否递归查询子容器
     * @return
     */
    int anonymousTableSize(boolean recursive);

    /**
     * equals {@link #size(boolean) size(false)}
     *
     * @return
     */
    int size();

    /**
     * 查询总表数, 包含匿名表
     *
     * @param recursive 是否递归查询子容器
     * @return
     */
    int size(boolean recursive);
}
