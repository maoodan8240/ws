package ws.common.utils.dataSource.mysql;

public class DataSourceNotExistsException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DataSourceNotExistsException(String dbName) {
        super(//
                "dbName=" + dbName + " 的DataSource 不存在！"
                //
        );
    }
}
