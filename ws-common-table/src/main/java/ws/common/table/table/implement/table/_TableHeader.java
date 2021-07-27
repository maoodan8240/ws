package ws.common.table.table.implement.table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import ws.common.table.data.TableDataHeader;
import ws.common.table.table.exception.TableHeaderParseFailedException;
import ws.common.table.table.interfaces.table.TableHeader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class _TableHeader implements TableHeader, Serializable {
    private static final long serialVersionUID = 1L;

    private String idColumnName; // id列的名称
    private List<String> orderColumnNames = new ArrayList<>(); // 有顺序的列名 不包含id
    private Map<String, String> columnNameToType = new ConcurrentHashMap<>(); // 列名-类的类型
    private Map<String, String> columnNameToDesc = new ConcurrentHashMap<>(); // 列名-类的描述

    @Override
    public List<String> getAllOrderColumnNames() {
        return new ArrayList<>(orderColumnNames);
    }

    @Override
    public Set<String> getAllColumnNames() {
        return this.columnNameToType.keySet();
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
    public void parse(File file) throws TableHeaderParseFailedException {
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;

        try {
            inputStreamReader = new InputStreamReader(new FileInputStream(file), Charset.forName("utf-8"));
            bufferedReader = new BufferedReader(inputStreamReader);
            String[] columnDescs = bufferedReader.readLine().split("\t");
            String[] columnNames = bufferedReader.readLine().split("\t");
            String[] columnTypes = bufferedReader.readLine().split("\t");
            this.idColumnName = columnNames[0];

            for (int i = 0; i < columnNames.length; ++i) {
                this.columnNameToDesc.put(columnNames[i], columnDescs[i]);
                this.columnNameToType.put(columnNames[i], columnTypes[i]);
            }
        } catch (Exception var15) {
            throw new TableHeaderParseFailedException(this, file, var15);
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }

                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }

                bufferedReader = null;
                inputStreamReader = null;
            } catch (IOException var14) {
                var14.printStackTrace();
            }

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
