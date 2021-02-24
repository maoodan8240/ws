package ws.common.network.server.implement;

import java.util.HashMap;
import java.util.Map;

import com.google.protobuf.Message;

import ws.common.network.exception.CodeOfMessagePrototypeAlreadyExistsException;
import ws.common.network.server.interfaces.CodeToMessagePrototype;

public class _CodeToMessagePrototype implements CodeToMessagePrototype {
    // 协议号-协议原型
    private final Map<Integer, Message> codeToMsgPrototype = new HashMap<>();
    // 协议原型的类型-协议号
    private final Map<Class<? extends Message>, Integer> msgTypeToCode = new HashMap<>();

    @Override
    public boolean contains(int code) {
        return codeToMsgPrototype.containsKey(code);
    }

    @Override
    public boolean contains(Class<? extends Message> type) {
        return msgTypeToCode.containsKey(type);
    }

    @Override
    public Message query(int code) {
        return codeToMsgPrototype.get(code);
    }

    @Override
    public int queryCode(Class<? extends Message> type) {
        return msgTypeToCode.get(type);
    }

    @Override
    public Map<Integer, Message> queryAll() {
        return new HashMap<>(codeToMsgPrototype);
    }

    @Override
    public void add(int code, Message msgPrototype) {
        Class<? extends Message> type = msgPrototype.getClass();
        if (contains(code) || contains(type)) { // code和type必须一一对应
            throw new CodeOfMessagePrototypeAlreadyExistsException(code, type);
        }
        codeToMsgPrototype.put(code, msgPrototype);
        msgTypeToCode.put(type, code);
    }

    @Override
    public void clear(int code) {
        msgTypeToCode.remove(query(code).getClass());
        codeToMsgPrototype.remove(code);
    }

    @Override
    public void clear(Class<? extends Message> type) {
        codeToMsgPrototype.remove(queryCode(type));
        msgTypeToCode.remove(type);
    }

    @Override
    public void clearAll() {
        codeToMsgPrototype.clear();
        msgTypeToCode.clear();
    }

    @Override
    public int size() {
        return codeToMsgPrototype.size();
    }

}
