package ws.common.mongoDB.handler;

import com.alibaba.fastjson.JSONObject;
import org.bson.Document;
import org.bson.types.ObjectId;
import ws.common.mongoDB.interfaces.TopLevelPojo;
import ws.common.mongoDB.utils.WsJsonUtils;

public class PojoHandler {
    public static final String TopLevelPojoIdName = "_id";
    private static final String MongoDbOIdName = "oid";

    public static Document pojoToDocument(TopLevelPojo topLevelPojo) {
        String oid = topLevelPojo.getOid();
        JSONObject jsonObject = WsJsonUtils.javaObjectToJSONObject(topLevelPojo);
        jsonObject.remove(MongoDbOIdName);
        jsonObject.remove(TopLevelPojoIdName);
        Document document = Document.parse(jsonObject.toJSONString());
        return document.append(TopLevelPojoIdName, new ObjectId(oid));
    }

    public static <T> T documentToPojo(Document document, Class<T> cls) {
        if (TopLevelPojo.class.isAssignableFrom(cls)) {
            String id = document.get(TopLevelPojoIdName).toString();
            document.remove(TopLevelPojoIdName);
            document.put(TopLevelPojoIdName, id);
        }
        String jsonStr = com.mongodb.util.JSON.serialize(document);
        return com.alibaba.fastjson.JSON.parseObject(jsonStr, cls);
    }
}
