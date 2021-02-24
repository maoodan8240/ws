package ws.common.utils.cooldown.interfaces;

import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * CD列表
 */
public interface CdList {

    @FunctionalInterface
    static interface CallbackOnExpire {
        void run(String type);
    }

    /**
     * 是否包含指定类型的CD
     * 
     * @param type
     * @return
     */
    boolean has(String type);

    /**
     * 返回指定类型的CD的过期时间
     * 
     * @param type
     * @return
     */
    Date get(String type);

    /**
     * 返回全部CD类型
     * 
     * @return
     */
    Set<String> getAllTypes();

    /**
     * 返回全部CD及其过期时间
     * 
     * @return
     */
    Map<String, Date> getAll();

    /**
     * 返回CD个数
     * 
     * @return
     */
    int size();

    /**
     * 添加指定类型的CD
     * 
     * @param type
     * @param expireDate
     *            必须大于当前时间
     */
    void add(String type, Date expireDate);

    /**
     * 添加指定类型的CD
     * 
     * @param type
     * @param timeToLive
     *            必须大于0
     * @param timeUnit
     *            可选值 Calendar.MILLISECOND, Calendar.SECOND, Calendar.MINUTE,
     *            Calendar.HOUR_OF_DAY, Calendar.DAY_OF_MONTH
     */
    void add(String type, int timeToLive, int timeUnit);

    /**
     * 设置CD过期回调
     * 
     * @param callback
     */
    void setCallbackOnExpire(CallbackOnExpire callback);

    CallbackOnExpire getCallbackOnExpire();

    /**
     * 清除指定类型的CD
     * 
     * @param type
     */
    void clear(String type);

    /**
     * 清除全部
     */
    void clearAll();

    /**
     * 转换为JSON字符串
     * 
     * @return
     */
    String toJson();

}
