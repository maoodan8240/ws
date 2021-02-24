package ws.common.utils.dataSource.mysql;

import java.sql.Connection;

public interface DataSourceManager {

    Connection getDBConnection(String dbName) throws Exception;

    /**
     * @param dbURI   例如：jdbc:mysql://192.168.100.98:3306/
     * @param dbName  例如：y6
     * @param dbuname
     * @param dbupswd
     * @throws Exception
     */
    void addDataSource(String dbURI, String dbName, String dbuname, String dbupswd) throws Exception;

}