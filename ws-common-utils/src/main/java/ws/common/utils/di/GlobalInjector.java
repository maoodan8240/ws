package ws.common.utils.di;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.Injector;

/**
 * 全局{@link Injector}
 */
public class GlobalInjector {
    private static Injector DEFAULT_INJECTOR;
    private static final Map<String, Injector> MAP = new HashMap<>();

    /**
     * 返回默认{@link Injector}
     * 
     * @return
     */
    public static Injector getDefaultInjector() {
        return DEFAULT_INJECTOR;
    }

    /**
     * 设置默认{@link Injector}
     * 
     * @param injector
     */
    public static void setDefaultInjector(Injector injector) {
        DEFAULT_INJECTOR = injector;
    }

    /**
     * 返回指定名字的{@link Injector}
     * 
     * @param name
     * @return
     */
    public static Injector get(String name) {
        return MAP.get(name);
    }

    /**
     * 设置指定名字的{@link Injector}
     * 
     * @param name
     * @param injector
     */
    public static void set(String name, Injector injector) {
        MAP.put(name, injector);
    }

    /**
     * 等同于GlobalInjector.getDefaultInjector().getInstance(type)
     * 
     * @param type
     * @return
     */
    public static <T> T getInstance(Class<T> type) {
        return DEFAULT_INJECTOR.getInstance(type);
    }

}
