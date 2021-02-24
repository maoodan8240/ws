package ws.common.mongoDB.implement;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import org.bson.Document;
import org.bson.types.ObjectId;
import ws.common.mongoDB.exception.DaoUninitializedException;
import ws.common.mongoDB.handler.OrderType;
import ws.common.mongoDB.handler.PojoHandler;
import ws.common.mongoDB.interfaces.BaseDao;
import ws.common.mongoDB.interfaces.MongoDBClient;
import ws.common.mongoDB.interfaces.TopLevelPojo;
import ws.common.mongoDB.utils.WsJsonUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBaseDao<T extends TopLevelPojo> implements BaseDao<T> {
    protected MongoCollection<Document> mongoCollection;
    protected MongoDBClient mongoDbClient;
    protected MongoDatabase mongoDatabase;
    protected Class<T> cls;

    public AbstractBaseDao(Class<T> cls) {
        this.cls = cls;
    }

    public void init(MongoDBClient mongoDbClient) {
        this.mongoDbClient = mongoDbClient;
        this.mongoDatabase = mongoDbClient.getConfigDatabase();
        this.mongoCollection = mongoDatabase.getCollection(cls.getSimpleName());
    }

    public void init(MongoDBClient mongoDbClient, String dbName) {
        this.mongoDbClient = mongoDbClient;
        this.mongoDatabase = mongoDbClient.getSpecifiedDatabase(dbName);
        this.mongoCollection = mongoDatabase.getCollection(cls.getSimpleName());
    }

    /**
     * ----------------Query-------------------
     */
    @Override
    public List<T> findAll() {
        return findAll(new Document());
    }

    @Override
    public List<T> findAll(Document query) {
        FindIterable<Document> iterable = getMongoCollection().find(query);
        return getTlist(iterable.iterator());
    }

    @Override
    public List<T> findAll(Document query, String fieldName, OrderType orderType, int limit) {
        FindIterable<Document> iterable = getMongoCollection().find(query).sort(new Document(fieldName, orderType.getValue())).limit(limit);
        return getTlist(iterable.iterator());
    }

    @Override
    public boolean exists(String objectId) {
        if (ObjectId.isValid(objectId)) {
            return findAllCount(new Document(PojoHandler.TopLevelPojoIdName, new ObjectId(objectId))) > 0;
        }
        return false;
    }

    @Override
    public long findAllCount() {
        return getMongoCollection().count();
    }

    @Override
    public long findAllCount(Document query) {
        return getMongoCollection().count(query);
    }

    @Override
    public T findOne(String objectId) {
        if (ObjectId.isValid(objectId)) {
            return findOne(new Document(PojoHandler.TopLevelPojoIdName, new ObjectId(objectId)));
        }
        return null;
    }

    @Override
    public T findOne(Document query) {
        FindIterable<Document> iterable = getMongoCollection().find(query);
        Document document = iterable.first();
        if (document == null) {
            return null;
        }
        return PojoHandler.documentToPojo(document, cls);
    }

    /**
     * ----------------insert-------------------
     */

    @Override
    public void insert(T obj) {
        Document document = PojoHandler.pojoToDocument(obj);
        getMongoCollection().insertOne(document);
    }

    @Override
    public void insert(List<T> objs) {
        List<Document> documents = new ArrayList<>();
        for (T obj : objs) {
            documents.add(PojoHandler.pojoToDocument(obj));
        }
        getMongoCollection().insertMany(documents);
    }

    @Override
    public void insertIfExistThenReplace(T obj) {
        Document iDDocument = getIDDocument(obj);
        Document objDoc = PojoHandler.pojoToDocument(obj);
        getMongoCollection().findOneAndReplace(iDDocument, objDoc, new FindOneAndReplaceOptions().upsert(true));
    }

    /**
     * ----------------delete-------------------
     */

    @Override
    public void delete(T obj) {
        Document iDDocument = getIDDocument(obj);
        getMongoCollection().findOneAndDelete(iDDocument);
    }

    /**
     * ----------------其他-------------------
     */

    private List<T> getTlist(MongoCursor<Document> cursor) {
        List<T> lis = new ArrayList<T>();
        try {
            if (cursor == null) {
                return lis;
            }
            while (cursor.hasNext()) {
                Document document = cursor.next();
                if (document != null) {
                    T t = PojoHandler.documentToPojo(document, cls);
                    lis.add(t);
                }
            }
        } finally {
            cursor.close();
        }
        return lis;
    }

    private Document getIDDocument(T obj) {
        JSONObject jsonObject = WsJsonUtils.javaObjectToJSONObject(obj);
        return new Document(PojoHandler.TopLevelPojoIdName, new ObjectId(jsonObject.getString(PojoHandler.TopLevelPojoIdName)));
    }

    @Override
    public MongoCollection<Document> getMongoCollection() {
        if (mongoDatabase == null || mongoCollection == null) {
            throw new DaoUninitializedException();
        }
        return mongoCollection;
    }

    @Override
    public MongoDatabase getMongoDatabase() {
        if (mongoDatabase == null || mongoCollection == null) {
            throw new DaoUninitializedException();
        }
        return mongoDatabase;
    }

    public Class<T> getCls() {
        return cls;
    }
}
