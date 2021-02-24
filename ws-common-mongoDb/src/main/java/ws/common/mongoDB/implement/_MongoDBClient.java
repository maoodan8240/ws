package ws.common.mongoDB.implement;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.mongoDB.config.MongoConfig;
import ws.common.mongoDB.exception.MongodbInitException;
import ws.common.mongoDB.interfaces.MongoDBClient;

import java.util.ArrayList;
import java.util.List;

public class _MongoDBClient implements MongoDBClient {
    private static final Logger logger = LoggerFactory.getLogger(_MongoDBClient.class);
    private MongoConfig config;
    private MongoClient client;

    public void init(MongoConfig config) {
        try {
            this.config = config;
            MongoClientOptions.Builder builder = optionsBuilder();
            List<MongoCredential> credentialsList = new ArrayList<MongoCredential>();
            MongoCredential mongoCredential = MongoCredential.createMongoCRCredential(config.getUserName(), "admin", config.getPassword().toCharArray());
            credentialsList.add(mongoCredential);
            client = new MongoClient(new ServerAddress(config.getHost(), config.getPort()), credentialsList, builder.build());
        } catch (Exception e) {
            throw new MongodbInitException(config, e);
        }
        logger.info("MongoClient connected! {}", config.toString());
    }

    @Override
    public MongoClient getMongoClient() {
        return client;
    }

    @Override
    public MongoDatabase getSpecifiedDatabase(String dbName) {
        if (StringUtils.isEmpty(dbName)) {
            throw new RuntimeException("dbName 不能为空！");
        }
        return client.getDatabase(dbName);
    }

    @Override
    public MongoDatabase getConfigDatabase() {
        if (StringUtils.isEmpty(config.getDbName())) {
            throw new RuntimeException("config.getDbName 不能为空！");
        }
        return client.getDatabase(config.getDbName());
    }

    private MongoClientOptions.Builder optionsBuilder() {
        MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
        builder.connectionsPerHost(config.getConnectionsPerHost());
        return builder;
    }
}
