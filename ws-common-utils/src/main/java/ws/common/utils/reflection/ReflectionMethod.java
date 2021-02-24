package ws.common.utils.reflection;

import java.lang.reflect.Field;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReflectionMethod {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionMethod.class);
    private static final ClassFieldsCache cfc = ClassFieldsCache.getInstance();

    public static <T> Object readField(T obj, String fieldName) {
        if (cfc.isContainsClassField(obj.getClass(), fieldName)) {
            Field field = cfc.getContainsClassField(obj.getClass(), fieldName);
            try {
                return FieldUtils.readField(field, obj, true);
            } catch (IllegalAccessException e) {
                logger.error("ReflectionMethod readField error! {} {}", obj, fieldName, e);
                return null;
            }
        }
        return null;
    }

    public static <T> void writeField(T obj, String fieldName, Object value) {
        if (cfc.isContainsClassField(obj.getClass(), fieldName)) {
            Field field = cfc.getContainsClassField(obj.getClass(), fieldName);
            try {
                FieldUtils.writeField(field, obj, value, true);
            } catch (IllegalAccessException e) {
                logger.error("ReflectionMethod readField error! {} {}", obj, fieldName, e);
            }
        }
    }
}
