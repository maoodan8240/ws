package ws.common.utils.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.alibaba.fastjson.annotation.JSONField;

public class ClassFieldsCache {
    /**
     * Class <---> FieldName OR JsonFieldName <---> Field
     */
    private final ConcurrentMap<Class<?>, Map<String, Field>> classFieldsCache = new ConcurrentHashMap<Class<?>, Map<String, Field>>();

    private static ClassFieldsCache _instance = new ClassFieldsCache();

    private ClassFieldsCache() {
    }

    public static ClassFieldsCache getInstance() {
        return _instance;
    }

    /**
     * 获取一个类中除去过滤掉的field的其他字段
     * 
     * @param cls
     * @return
     */
    public Map<String, Field> getAllFields(Class<?> cls) {
        Map<String, Field> allFieldsMap = new HashMap<String, Field>();
        allFieldsMap.putAll(filterFields(cls.getDeclaredFields()));
        Class<?> parent = cls.getSuperclass();
        while ((parent != null) && (parent != Object.class)) {
            allFieldsMap.putAll(filterFields(parent.getDeclaredFields()));
            parent = parent.getSuperclass();
        }
        return allFieldsMap;
    }

    /**
     * 过滤一些field
     * 
     * @param fields
     * @return
     */
    private Map<String, Field> filterFields(Field[] fields) {
        Map<String, Field> resultMap = new HashMap<String, Field>();
        for (Field field : fields) {
            if (!Modifier.isStatic(field.getModifiers())) {
                field.setAccessible(true);
                if (!resultMap.containsKey(field.getName())) {
                    if (field.isAnnotationPresent(JSONField.class)) {
                        JSONField jsonf = field.getAnnotation(JSONField.class);
                        resultMap.put(jsonf.name(), field); // jsonf.name()为该字段的fastjson别名
                    }
                    resultMap.put(field.getName(), field);
                }
            }
        }
        return resultMap;
    }

    /**
     * 判断某个类中是否存在某个字段
     * 
     * @param cls
     * @param fieldName
     * @return
     */
    public boolean isContainsClassField(Class<?> cls, String fieldName) {
        Map<String, Field> allFieldsMap = classFieldsCache.get(cls);
        if (allFieldsMap == null) {
            allFieldsMap = getAllFields(cls);
            this.classFieldsCache.put(cls, allFieldsMap);
        }
        return allFieldsMap.containsKey(fieldName);
    }

    /**
     * 通过FieldName获取Field
     * 
     * @param cls
     * @param fieldName
     * @return
     */
    public Field getContainsClassField(Class<?> cls, String fieldName) {
        Map<String, Field> allFieldsMap = classFieldsCache.get(cls);
        if (allFieldsMap == null) {
            allFieldsMap = getAllFields(cls);
            this.classFieldsCache.put(cls, allFieldsMap);
        }
        return allFieldsMap.get(fieldName);
    }
}
