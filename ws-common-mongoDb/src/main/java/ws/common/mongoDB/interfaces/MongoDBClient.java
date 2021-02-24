package ws.common.mongoDB.interfaces;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import ws.common.mongoDB.config.MongoConfig;

public interface MongoDBClient {
    void init(MongoConfig config);

    MongoDatabase getSpecifiedDatabase(String dbName);

    MongoDatabase getConfigDatabase();

    MongoClient getMongoClient();
}
