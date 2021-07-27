package ws.common.network.server.implement;

import com.google.protobuf.Message;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValue;
import drama.protos.CodesProtos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.network.exception.CodeOfMessagePrototypeAlreadyExistsException;
import ws.common.network.server.interfaces.CodeToMessagePrototype;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class _CodeToMessagePrototype implements CodeToMessagePrototype {
    private static final Logger LOGGER = LoggerFactory.getLogger(_CodeToMessagePrototype.class);
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

    public _CodeToMessagePrototype() {
        init();
    }

    public InputStream getJarFile() throws IOException {
        String path = CodesProtos.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        JarFile localJarFile = new JarFile(new File(path));
        InputStream inputStream = null;
        Enumeration<JarEntry> entries = localJarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry jarEntry = entries.nextElement();
            String innerPath = jarEntry.getName();
            if (innerPath.endsWith(".conf")) {
                LOGGER.debug("configPath ={} ", innerPath);
                inputStream = CodesProtos.class.getClassLoader().getResourceAsStream(innerPath);
                break;
            }
        }
        return inputStream;
    }

    private void init() {
        try {
            InputStream ins = getJarFile();
            File file = File.createTempFile("temp", ".txt");
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();


            Config config = ConfigFactory.parseFile(file);
            for (Map.Entry<String, ConfigValue> entry : config.entrySet()) {
                if (entry.getKey().startsWith("drama.protos.")) {
                    String codeStr = entry.getKey().replace("drama.protos.", "");
                    CodesProtos.ProtoCodes.Code code = CodesProtos.ProtoCodes.Code.valueOf(codeStr);
                    add(code.getNumber(), dynamicGenMessage(config.getString(entry.getKey())));
                    LOGGER.debug("加载code=[{}]--->message=[{}]", codeStr, entry.getKey());
                }
            }
        } catch (IOException e) {
            String msg = String.format("加载消息码-消息原型出错！ e=%s", e);
            throw new RuntimeException(msg);
        }

    }

    /**
     * 根据 proto的类型名称，动态生成Message
     *
     * @param protoTypeClassName
     * @return
     */
    private Message dynamicGenMessage(String protoTypeClassName) {
        try {
            Class clzz = Class.forName(protoTypeClassName);
            Method method = clzz.getMethod("newBuilder");
            Object builderObj = method.invoke(null);
            Method getInsMethod = builderObj.getClass().getMethod("getDefaultInstanceForType");
            return (Message) getInsMethod.invoke(builderObj);
        } catch (Exception e) {
            String msg = String.format("加载Proto类出错！ protoTypeClassName=%s", protoTypeClassName);
            throw new RuntimeException(msg);
        }
    }


}
