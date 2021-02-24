package ws.common.utils.dataSource.mysql;

import javax.sql.DataSource;

public class DataSourceAlreadyExistsException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DataSourceAlreadyExistsException(DataSource dataSource, String dbURI, String dbName, String dbuname, String dbupswd) {
        super(//
                new StringBuffer("新增加的DataSource已经存在了!").append("\n")//
                        .append("    ").append("新增的为：").append("dbURI=").append(dbURI).append("  dbName=").append(dbName)//
                        .append("  dbuname=").append(dbuname).append("  dbupswd=").append(dbupswd).toString()
                //
        );
    }
}
