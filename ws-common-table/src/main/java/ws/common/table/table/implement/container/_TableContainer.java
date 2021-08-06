package ws.common.table.table.implement.container;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import ws.common.table.table.interfaces.Row;
import ws.common.table.table.interfaces.container.HierarchyName;
import ws.common.table.table.interfaces.container.TableContainer;
import ws.common.table.table.interfaces.table.Table;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class _TableContainer implements TableContainer, Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private TableContainer owner;
    private Map<Class<? extends Row>, Table<? extends Row>> rowTypeToAnonymousTable = new HashMap<>();
    private Map<String, Table<? extends Row>> tableNameToTable = new HashMap<>();
    private Map<String, TableContainer> nameToContainer = new HashMap<>();

    public _TableContainer() {
    }

    public _TableContainer(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public TableContainer getOwner() {
        return owner;
    }

    @Override
    public void setOwner(TableContainer tableContainer) {
        this.owner = tableContainer;
    }

    @Override
    public <RowType extends Row> boolean hasAnonymousTable(Class<RowType> rowType) {
        return rowTypeToAnonymousTable.containsKey(rowType);
    }

    @Override
    public <RowType extends Row> boolean has(Class<RowType> rowType) {
        return has(rowType, true);
    }

    @Override
    public <RowType extends Row> boolean has(Class<RowType> rowType, boolean recursive) {
        return get(rowType, recursive) != null;
    }

    @Override
    public boolean has(String hierarchyTableName) {
        return has(null, hierarchyTableName);
    }

    @Override
    public <RowType extends Row> boolean has(Class<RowType> rowType, String hierarchyTableName) {
        return get(rowType, hierarchyTableName) != null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <RowType extends Row> Table<RowType> getAnonymousTable(Class<RowType> rowType) {
        return (Table<RowType>) rowTypeToAnonymousTable.get(rowType);
    }

    @Override
    public <RowType extends Row> Table<RowType> get(Class<RowType> rowType) {
        return get(rowType, true);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <RowType extends Row> Table<RowType> get(Class<RowType> rowType, boolean recursive) {
        if (hasAnonymousTable(rowType)) {
            return getAnonymousTable(rowType);
        }
        for (Table<? extends Row> table : tableNameToTable.values()) {
            if (table.getRowClass().equals(rowType)) {
                return (Table<RowType>) table;
            }
        }
        if (recursive) {
            for (TableContainer container : nameToContainer.values()) {
                if (container.has(rowType)) {
                    return container.get(rowType);
                }
            }
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <RowType extends Row> Table<RowType> get(Class<RowType> rowType, String hierarchyTableName) {
        HierarchyName name = HierarchyNameBuilder.build(hierarchyTableName);
        if (!name.isHierarchy()) {
            return (Table<RowType>) tableNameToTable.get(name.getFirstName());
        } else {
            String remainingName = name.getRemainingName(true);
            String lastName = name.getFirstName(true);
            TableContainer subContainer = getSubContainer(remainingName);
            if (subContainer == null) {
                return null;
            } else {
                return subContainer.get(rowType, lastName);
            }
        }
    }

    @Override
    public <RowType extends Row> Table<RowType> get(String tableName) {
        return (Table<RowType>) tableNameToTable.get(tableName);
    }

    @Override
    public void addAnonymousTable(Table<? extends Row> table) {
        rowTypeToAnonymousTable.put(table.getRowClass(), table);
    }

//    @Override
//    public void addAnonymousTable(Table<? extends Row> table, int specialId) {
//        rowTypeToAnonymousTable.put(table.getRowClass(), table);
//    }

    @Override
    public void addNamedTable(Table<? extends Row> table) {
        addNamedTable(null, table);
    }

    @Override
    public void addNamedTable(String hierarchySubContainerName, Table<? extends Row> table) {
        HierarchyName name = HierarchyNameBuilder.build(hierarchySubContainerName);
        if (name.isAnonymous()) {
            tableNameToTable.put(table.getName(), table);
        } else {
            buildSubContainer(hierarchySubContainerName);
            getSubContainer(hierarchySubContainerName).addNamedTable(table);
        }
    }

//    @Override
//    public void addNamedTable(String hierarchySubContainerName, Table<? extends Row> table, int specialId) {
//        HierarchyName name = HierarchyNameBuilder.build(hierarchySubContainerName);
//        if (name.isAnonymous()) {
//            tableNameToTable.put(table.getName(), table);
//        } else {
//            buildSubContainer(hierarchySubContainerName);
//            getSubContainer(hierarchySubContainerName).addNamedTable(table, specialId);
//        }
//    }

//    @Override
//    public void addNamedTable(Table<? extends Row> table, int specialId) {
//        addNamedTable(null, table, specialId);
//    }

    @Override
    public void addTable(Table<? extends Row> table) {
        HierarchyName name = HierarchyNameBuilder.build(table.getName());
        if (name.isAnonymous()) {
            addAnonymousTable(table);
        } else {
            addNamedTable(table);
        }
    }

//    @Override
//    public void addTable(Table<? extends Row> table, int specialId) {
//        HierarchyName name = HierarchyNameBuilder.build(table.getName());
//        if (name.isAnonymous()) {
//            addAnonymousTable(table, specialId);
//        } else {
//            addNamedTable(table, specialId);
//        }
//    }

    @Override
    public boolean hasSubContainer(String hierarchySubContainerName) {
        return getSubContainer(hierarchySubContainerName) != null;
    }

    @Override
    public TableContainer getSubContainer(String hierarchySubContainerName) {
        HierarchyName name = HierarchyNameBuilder.build(hierarchySubContainerName);
        if (!name.isHierarchy()) {
            String containerName = name.getFirstName();
            return nameToContainer.get(containerName);
        } else {
            String remainingName = name.getRemainingName(true);
            String lastName = name.getFirstName(true);
            TableContainer subContainer = getSubContainer(remainingName);
            if (subContainer == null) {
                return null;
            } else {
                return subContainer.getSubContainer(lastName);
            }
        }
    }

    @Override
    public void addSubContainer(String hierarchySubContainerName, TableContainer subContainer) {
        HierarchyName name = HierarchyNameBuilder.build(hierarchySubContainerName);
        if (!name.isAnonymous()) {
            subContainer.setOwner(this);
            nameToContainer.put(subContainer.getName(), subContainer);
        } else {
            buildSubContainer(hierarchySubContainerName);
            getSubContainer(hierarchySubContainerName).addSubContainer(null, subContainer);
        }
    }

    @Override
    public void buildSubContainer(String hierarchySubContainerName) {
        HierarchyName name = HierarchyNameBuilder.build(hierarchySubContainerName);
        if (!name.isHierarchy()) {
            String containerName = name.getFirstName();
            if (!nameToContainer.containsKey(containerName)) {
                TableContainer subContainer = new _TableContainer(containerName);
                subContainer.setOwner(this);
                nameToContainer.put(containerName, subContainer);
            }
        } else {
            String firstName = name.getFirstName();
            String remainingName = name.getRemainingName();
            if (!nameToContainer.containsKey(firstName)) {
                TableContainer subContainer = new _TableContainer(firstName);
                subContainer.setOwner(this);
                nameToContainer.put(firstName, subContainer);
            }
            getSubContainer(firstName).buildSubContainer(remainingName);
        }
    }

    @Override
    public int subContainerSize() {
        return subContainerSize(false);
    }

    @Override
    public int subContainerSize(boolean recursive) {
        int size = nameToContainer.size();
        if (recursive) {
            for (TableContainer subContainer : nameToContainer.values()) {
                size += subContainer.subContainerSize(true);
            }
        }
        return size;
    }

    @Override
    public int anonymousTableSize() {
        return anonymousTableSize(false);
    }

    @Override
    public int anonymousTableSize(boolean recursive) {
        int size = rowTypeToAnonymousTable.size();
        if (recursive) {
            for (TableContainer subContainer : nameToContainer.values()) {
                size += subContainer.anonymousTableSize(true);
            }
        }
        return size;
    }

    @Override
    public int size() {
        return size(false);
    }

    @Override
    public int size(boolean recursive) {
        int size = tableNameToTable.size() + anonymousTableSize(recursive);
        if (recursive) {
            for (TableContainer subContainer : nameToContainer.values()) {
                size += subContainer.size(true);
            }
        }
        return size;
    }

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
        builder.append("name", name);
        builder.append("owner", owner);
        builder.append("anonymousTableSize", anonymousTableSize() + "/" + anonymousTableSize(true));
        builder.append("size", size() + "/" + size(true));
        builder.append("subContainerSize", subContainerSize() + "/" + subContainerSize(true));
        return builder.toString();
    }

}
