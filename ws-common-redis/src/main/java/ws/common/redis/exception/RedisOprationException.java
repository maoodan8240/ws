package ws.common.redis.exception;

public class RedisOprationException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public RedisOprationException(String msg, Throwable t) {
        super(msg, t);
    }
}
