package ws.common.mongoDB.config;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class MongoConfig {
    private String host;
    private int port;
    private String userName;
    private String password;
    private String dbName;
    private int connectionsPerHost;

    public MongoConfig() {
    }

    public MongoConfig(String host, int port, String userName, String password, String dbName, int connectionsPerHost) {
        this.host = host;
        this.port = port;
        this.userName = userName;
        this.password = password;
        this.dbName = dbName;
        this.connectionsPerHost = connectionsPerHost;
    }
 
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public int getConnectionsPerHost() {
        return connectionsPerHost;
    }

    public void setConnectionsPerHost(int connectionsPerHost) {
        this.connectionsPerHost = connectionsPerHost;
    }

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
        builder.append("host", host);
        builder.append("port", port);
        builder.append("userName", userName);
        builder.append("password", password);
        builder.append("dbName", dbName);
        builder.append("connectionsPerHost", connectionsPerHost);
        return builder.toString();
    }
}
