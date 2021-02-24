package ws.common.redis.exception;

public class RedisOprationNewParmBuilderException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public RedisOprationNewParmBuilderException(Class<?> builderClzz) {
        super("Params.Builder newBuilder 异常！ builderClzz=" + builderClzz.toString());
    }
}
