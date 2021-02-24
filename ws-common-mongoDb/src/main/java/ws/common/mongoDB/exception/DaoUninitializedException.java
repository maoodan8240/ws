package ws.common.mongoDB.exception;

public class DaoUninitializedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DaoUninitializedException() {
        super("Dao未进行初始化！");
    }
}
