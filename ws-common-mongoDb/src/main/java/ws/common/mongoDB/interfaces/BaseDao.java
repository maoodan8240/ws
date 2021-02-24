package ws.common.mongoDB.interfaces;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import ws.common.mongoDB.interfaces.classification.DeleteDao;
import ws.common.mongoDB.interfaces.classification.FindDao;
import ws.common.mongoDB.interfaces.classification.InsertDao;
import ws.common.mongoDB.interfaces.classification.UpdateDao;

public interface BaseDao<T extends TopLevelPojo> extends FindDao<T>, InsertDao<T>, UpdateDao<T>, DeleteDao<T> {

    void init(MongoDBClient mongoDbClient);

    void init(MongoDBClient mongoDbClient, String dbName);

    MongoCollection<Document> getMongoCollection();

    MongoDatabase getMongoDatabase();

    Class<T> getCls();
}
