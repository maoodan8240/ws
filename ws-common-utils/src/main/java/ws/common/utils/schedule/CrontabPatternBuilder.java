package ws.common.utils.schedule;

import java.util.HashMap;
import java.util.Map;

public class CrontabPatternBuilder {
    private static String DEFAULT_VALUE = "*";
    private Map<Field, String> map = new HashMap<>();

    public CrontabPatternBuilder append(Field field, Object value) {
        map.put(field, value.toString());
        return this;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (Field field : Field.values()) {
            sb.append(map.containsKey(field) ? map.get(field) : DEFAULT_VALUE);
            sb.append(" ");
        }
        return sb.toString();
    }

    public static enum Field {
        MINUTES(), HOURS(), DAYS_OF_MONTH(), MONTHS(), DAYS_OF_WEEK();
    }
}
