package ws.common.table.table.implement.table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import ws.common.table.data.TableDataHeader;
import ws.common.table.table.interfaces.table.TableHeader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class _TableHeader implements TableHeader, Serializable {
    private static final long serialVersionUID = 1L;

    private String idColumnName; // id列的名称
    private List<String> orderColumnNames = new ArrayList<>(); // 有顺序的列名 不包含id
    private Map<String, String> columnNameToType = new ConcurrentHashMap<>(); // 列名-类的类型
    private Map<String, String> columnNameToDesc = new ConcurrentHashMap<>(); // 列名-类的描述

    @Override
    public List<String> getAllColumnNames() {
        return new ArrayList<>(orderColumnNames);
    }

    @Override
    public int getColumnSize() {
        return columnNameToType.size();
    }

    public String getIdColumnName() {
        return idColumnName;
    }

    public Map<String, String> getColumnNameToType() {
        return new HashMap<>(columnNameToType);
    }

    public Map<String, String> getColumnNameToDesc() {
        return new HashMap<>(columnNameToDesc);
    }

    @Override
    public void parse(String tableName, List<TableDataHeader> headerDatas) {
        // 列[列名,列的注释,列的类型],列[列名,列的注释,列的类型]],列[列名,列的注释,列的类型]]...
        /** 列名|列的描述|列的类型，列名|列的描述|列的类型，列名|列的描述|列的类型 ... */
        idColumnName = headerDatas.get(0).getName();
        for (TableDataHeader header : headerDatas) {
            columnNameToDesc.put(header.getName(), header.getDesc());
            columnNameToType.put(header.getName(), header.getType());
            orderColumnNames.add(header.getName());
        }
    }

    @Override
    public boolean equals(Object o) {
        boolean equals = false;
        if (o != null && TableHeader.class.isAssignableFrom(o.getClass())) {
            TableHeader header = (TableHeader) o;
            equals = (new EqualsBuilder()//
                    .append(idColumnName, header.getIdColumnName())//
                    .append(columnNameToType, header.getColumnNameToType())//
            ).isEquals();
        }
        return equals;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(idColumnName).append(columnNameToType).toHashCode();
    }

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("idColumnName", idColumnName);
        builder.append("orderColumnNames", orderColumnNames);
        builder.append("columnNameToType", columnNameToType);
        builder.append("columnNameToDesc", columnNameToDesc);
        return builder.toString();
    }
}
