package ws.common.mongoDB.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class WsJsonUtils {

    public static String javaObjectToJSONStr(Object obj) {
        // 默认的时间处理 JSON.DEFFAULT_DATE_FORMAT yyyy-MM-dd HH:mm:ss
        // Student s1 = new Student("s1", 16); maps.put("s1", s1); maps.put("s2", s1);
        // 没有SerializerFeature.DisableCircularReferenceDetect --> {"s1":{"age":16,"name":"s1"},"s2":{"$ref":"$.s1"}}
        // 启用SerializerFeature.DisableCircularReferenceDetect --> {"s1":{"age":16,"name":"s1"},"s2":{"age":16,"name":"s1"}}
        return com.alibaba.fastjson.JSON.toJSONString(obj, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteDateUseDateFormat);
    }

    public static JSONObject javaObjectToJSONObject(Object obj) {
        return JSONObject.parseObject(javaObjectToJSONStr(obj));
    }
}
