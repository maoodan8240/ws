package ws.common.utils.dataSource.mysql;

import org.apache.commons.lang3.StringUtils;
import ws.common.table.data.PlanningTableData;
import ws.common.table.data.TableData;
import ws.common.table.data.TableDataHeader;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class _PlanningTableData implements PlanningTableData {
    private static final String DICT_TABLE_PREFIX = "dict_";
    private Map<String, TableData> tableNameToTableData = new ConcurrentHashMap<>();
    private DataSourceManager manager;
    private String dbName;

    public _PlanningTableData(DataSourceManager manager, String dbName) {
        this.manager = manager;
        this.dbName = dbName;
    }

    @Override
    public void loadAllTablesData() throws Exception {
        Connection conn = null;
        try {
            conn = manager.getDBConnection(dbName);
            loadTableHeaderData(conn); // 加载表头数据
            loadTableBodyData(conn);// 加载表的内容
            loadTableLastUptateTime(conn);// 加载表的最后在trigger_log中的更新时间点
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    @Override
    public List<String> loadChangedTablesData() throws Exception {
        List<String> changedTableNames = new ArrayList<>();
        Connection conn = null;
        Statement statement = null;
        try {
            conn = manager.getDBConnection(this.dbName);
            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select * from trigger_log;");
            while (rs.next()) {
                String tableName = rs.getString(1);
                long time = rs.getTimestamp(2).getTime();
                long lastModifiedTime = tableNameToTableData.get(tableName).getLastModifiedTime();
                if (lastModifiedTime < time) {
                    changedTableNames.add(tableName);
                }
                tableNameToTableData.get(tableName).setLastModifiedTime(time);
            }
            loadChangedTableBodyData(conn, changedTableNames);
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
        return changedTableNames;
    }

    /**
     * 加载表的最后在trigger_log中的更新时间点
     *
     * @param conn
     * @throws Exception
     */
    private void loadTableLastUptateTime(Connection conn) throws Exception {
        // 默认先都已当前的时间为最后加载点
        for (String tableName : tableNameToTableData.keySet()) {
            tableNameToTableData.get(tableName).setLastModifiedTime(System.currentTimeMillis());
        }
        // 从数据库trigger_log中加载真实的最后加载点
        Statement statement = null;
        try {
            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select * from trigger_log");
            while (rs.next()) {
                String tableName = rs.getString(1);
                long time = rs.getTimestamp(2).getTime();
                tableNameToTableData.get(tableName).setLastModifiedTime(time);
            }
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }

    /**
     * 加载表头数据：列的字段名、列的字段类型、列的字段备注
     *
     * @param conn
     * @throws SQLException
     */
    private void loadTableHeaderData(Connection conn) throws SQLException {
        Statement statement = null;
        ResultSet rs = null;
        try {
            statement = conn.createStatement();
            rs = statement.executeQuery("SHOW TABLES;");
            ResultSetMetaData metaTableName = rs.getMetaData();
            int numcolsTable = metaTableName.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= numcolsTable; i++) {
                    String tableName = rs.getString(i);
                    if (!StringUtils.isEmpty(tableName)) {
                        if (tableName.toLowerCase().startsWith(DICT_TABLE_PREFIX)) {
                            List<TableDataHeader> tableDataTxtHeaders = new ArrayList<>();
                            addheaderNameDescData(conn, tableName, tableDataTxtHeaders);
                            addheaderNameTypeData(conn, tableName, tableDataTxtHeaders);
                            if (!tableNameToTableData.containsKey(tableName)) {
                                tableNameToTableData.put(tableName, new TableData(tableName));
                            }
                            tableNameToTableData.get(tableName).setHeaderDatas(tableDataTxtHeaders);
                        }
                    }
                }
            }
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }

    /**
     * 加载表头数据：列的字段名、列的字段备注
     *
     * @param conn
     * @param tableName
     * @throws SQLException
     */
    private static void addheaderNameDescData(Connection conn, String tableName, List<TableDataHeader> tableDataTxtHeaders) throws SQLException {
        Statement statement = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT COLUMN_NAME, COLUMN_COMMENT FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '" + tableName + "'";
            statement = conn.createStatement();
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                TableDataHeader header = new TableDataHeader();
                header.setName(rs.getString(1)); // COLUMN_NAME
                if (StringUtils.isEmpty(rs.getString(2))) {
                    header.setDesc(rs.getString(1)); // COLUMN_NAME
                } else {
                    header.setDesc(rs.getString(2)); // COLUMN_COMMENT
                }
                tableDataTxtHeaders.add(header);
            }
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }

    /**
     * 加载表头数据：列的字段类型
     *
     * @param conn
     * @param tableName
     * @throws SQLException
     */
    private static void addheaderNameTypeData(Connection conn, String tableName, List<TableDataHeader> tableDataTxtHeaders) throws SQLException {
        Statement statement = null;
        ResultSet rs = null;
        try {
            String sql = "select * from " + tableName;
            statement = conn.createStatement();
            rs = statement.executeQuery(sql);
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                TableDataHeader header = tableDataTxtHeaders.get(i - 1);
                String columnClassName = metaData.getColumnClassName(i);
                header.setType(columnClassName);
            }
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }

    private void loadChangedTableBodyData(Connection conn, List<String> changedTableNames) throws Exception {
        for (String tableName : changedTableNames) {
            if (tableName.toLowerCase().startsWith(DICT_TABLE_PREFIX)) {
                List<List<String>> bodyDatas = new ArrayList<>();
                addColumnNameData(conn, tableName, bodyDatas);
                addRowsData(conn, tableName, bodyDatas);
                tableNameToTableData.get(tableName).setBodyDatas(bodyDatas);
            }
        }
    }

    /**
     * 加载所有表的内容
     *
     * @param conn
     * @throws Exception
     */
    private void loadTableBodyData(Connection conn) throws Exception {
        Statement statement = null;
        ResultSet rs = null;
        try {
            statement = conn.createStatement();
            rs = statement.executeQuery("show tables;");
            ResultSetMetaData metaTableName = rs.getMetaData();
            int numcolsTable = metaTableName.getColumnCount();
            List<String> allTableNames = new ArrayList<>();
            while (rs.next()) {
                for (int i = 1; i <= numcolsTable; i++) {
                    String tableName = rs.getString(i);
                    if (!StringUtils.isEmpty(tableName)) {
                        if (tableName.toLowerCase().startsWith(DICT_TABLE_PREFIX)) {
                            allTableNames.add(tableName);
                        }
                    }
                }
            }
            loadChangedTableBodyData(conn, allTableNames);
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }

    /**
     * 加载列表
     *
     * @param conn
     * @param tableName
     * @param bodyDatas
     * @throws Exception
     */
    private static void addColumnNameData(Connection conn, String tableName, List<List<String>> bodyDatas) throws Exception {
        Statement statement = null;
        ResultSet rs = null;
        try {
            String sql = "select * from " + tableName;
            statement = conn.createStatement();
            rs = statement.executeQuery(sql);
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            List<String> data = new ArrayList<>();
            for (int i = 1; i <= columnCount; i++) {
                data.add(metaData.getColumnName(i));
            }
            bodyDatas.add(data);
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }

    /**
     * 加载表的所有数据行的内容
     *
     * @param conn
     * @param tableName
     * @param bodyDatas
     * @throws Exception
     */
    private static void addRowsData(Connection conn, String tableName, List<List<String>> bodyDatas) throws Exception {
        Statement statement = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM " + tableName;
            statement = conn.createStatement();
            rs = statement.executeQuery(sql);
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (rs.next()) {
                List<String> data = new ArrayList<>();
                for (int i = 1; i <= columnCount; i++) {
                    data.add(rs.getString(i));
                }
                bodyDatas.add(data);
            }
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }

    @Override
    public Map<String, TableData> getTableNameToTableData() {
        return tableNameToTableData;
    }
}
