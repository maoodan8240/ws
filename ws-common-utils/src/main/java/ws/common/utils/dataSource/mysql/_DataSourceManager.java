package ws.common.utils.dataSource.mysql;

import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDataSource;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class _DataSourceManager implements DataSourceManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(_DataSourceManager.class);
    private Map<String, DataSource> dbNameMaptoDataSource = new HashMap<>();

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            LOGGER.error("加载com.mysql.jdbc.Driver失败！", e);
        }
    }

    @Override
    public Connection getDBConnection(String dbName) throws Exception {
        if (!this.dbNameMaptoDataSource.containsKey(dbName)) {
            throw new DataSourceNotExistsException(dbName);
        }
        return this.dbNameMaptoDataSource.get(dbName).getConnection();
    }

    /**
     * @param dbURI   jdbc:mysql://192.168.100.98:3306/
     * @param dbName  y6
     * @param dbuname
     * @param dbupswd
     * @throws Exception
     */
    @Override
    public void addDataSource(String dbURI, String dbName, String dbuname, String dbupswd) throws Exception {
        if (this.dbNameMaptoDataSource.containsKey(dbName)) {
            throw new DataSourceAlreadyExistsException(dbNameMaptoDataSource.get(dbName), dbURI, dbName, dbuname, dbupswd);
        }
        DataSource dataSource = createDataSource(dbURI + dbName, dbuname, dbupswd);
        this.dbNameMaptoDataSource.put(dbName, dataSource);
    }

    private DataSource createDataSource(String connectUri, String uname, String passwd) {
        ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(connectUri, uname, passwd);
        PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(connectionFactory, null);
        ObjectPool<PoolableConnection> connectionPool = new GenericObjectPool<>(poolableConnectionFactory);
        poolableConnectionFactory.setPool(connectionPool);
        PoolingDataSource<PoolableConnection> dataSource = new PoolingDataSource<>(connectionPool);
        return dataSource;
    }
}
