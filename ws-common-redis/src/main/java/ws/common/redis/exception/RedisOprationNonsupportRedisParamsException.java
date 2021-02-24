package ws.common.redis.exception;

public class RedisOprationNonsupportRedisParamsException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public RedisOprationNonsupportRedisParamsException(String msg) {
        super(msg);
    }
}
