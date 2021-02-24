package ws.common.network.server.interfaces;

import java.util.Map;

import com.google.protobuf.Message;

public interface CodeToMessagePrototype {

    /**
     * 是否存在某个[协议号]
     * 
     * @param code
     * @return
     */
    boolean contains(int code);

    /**
     * 是否存在某个[协议原型的类型]
     * 
     * @param type
     * @return
     */
    boolean contains(Class<? extends Message> type);

    /**
     * 根据协议号获取[协议原型]
     * 
     * @param code
     * @return
     */
    Message query(int code);

    /**
     * 根据[协议原型的类型]获取协议号
     * 
     * @param type
     * @return
     */
    int queryCode(Class<? extends Message> type);

    /**
     * 获取所有[协议号]-[协议原型]
     * 
     * @return
     */
    Map<Integer, Message> queryAll();

    /**
     * 添加[协议号]-[协议原型]
     * 
     * @param code
     * @param msgPrototype
     */
    void add(int code, Message msgPrototype);

    /**
     * 根据[协议号]清除
     * 
     * @param code
     */
    void clear(int code);

    /**
     * 根据[协议原型的类型]清除
     * 
     * @param type
     */
    void clear(Class<? extends Message> type);

    /**
     * 清除所有
     */
    void clearAll();

    /**
     * 队列大小
     * 
     * @return
     */
    int size();
}
