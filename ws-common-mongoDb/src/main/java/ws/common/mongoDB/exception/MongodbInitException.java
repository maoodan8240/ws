package ws.common.mongoDB.exception;

import ws.common.mongoDB.config.MongoConfig;

public class MongodbInitException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public MongodbInitException(MongoConfig config, Throwable t) {
        super("Config为：" + config.toString(), t);
    }
}
