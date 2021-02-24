package ws.common.utils.cooldown.implement;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;

import io.netty.util.Timeout;
import ws.common.utils.concurrent.ReentrantReadWriteLockSupport;
import ws.common.utils.cooldown.interfaces.CdList;
import ws.common.utils.cooldown.utils.GlobalTimer;

/**
 * 线程安全的自清除CD列表
 */
public class AutoClearCdList extends ReentrantReadWriteLockSupport implements CdList {

    private final Map<String, Date> typeMaptoExpireDate = new HashMap<>();
    private final Map<String, Timeout> typeMaptoTimeout = new HashMap<>();
    private CallbackOnExpire callbackOnExpire;

    @Override
    public boolean has(String type) {
        lockRead();
        try {
            return typeMaptoExpireDate.containsKey(type);
        } finally {
            unlockRead();
        }
    }

    @Override
    public Date get(String type) {
        lockRead();
        try {
            return typeMaptoExpireDate.get(type);
        } finally {
            unlockRead();
        }
    }

    @Override
    public Set<String> getAllTypes() {
        lockRead();
        try {
            return new HashSet<>(typeMaptoExpireDate.keySet());
        } finally {
            unlockRead();
        }
    }

    @Override
    public Map<String, Date> getAll() {
        lockRead();
        try {
            return new HashMap<>(typeMaptoExpireDate);
        } finally {
            unlockRead();
        }
    }

    @Override
    public int size() {
        lockRead();
        try {
            return typeMaptoExpireDate.size();
        } finally {
            unlockRead();
        }
    }

    @Override
    public void add(String type, Date expireDate) {
        if (expireDate.getTime() <= System.currentTimeMillis()) {
            throw new RuntimeException("expireDate must greater than System.currentTimeMillis()");
        }
        if (has(type)) {
            throw new RuntimeException("already has " + type);
        }
        lockWrite();
        try {
            typeMaptoExpireDate.put(type, expireDate);
            AutoClearCdList cdList = this;
            Timeout timeout = GlobalTimer.newTimeout((x) -> {
                cdList.clear(type);
                cdList.callbackOnExpire.run(type);
            } , expireDate);
            typeMaptoTimeout.put(type, timeout);
        } finally {
            unlockWrite();
        }
    }

    @Override
    public void add(String type, int timeToLive, int timeUnit) {
        if (timeToLive <= 0) {
            throw new RuntimeException("timeToLive must greater than 0");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.add(timeUnit, timeToLive);
        add(type, calendar.getTime());
    }

    @Override
    public void setCallbackOnExpire(CallbackOnExpire callback) {
        callbackOnExpire = callback;
    }

    public CallbackOnExpire getCallbackOnExpire() {
        return callbackOnExpire;
    }

    @Override
    public void clear(String type) {
        lockWrite();
        try {
            typeMaptoExpireDate.remove(type);
            if (typeMaptoTimeout.containsKey(type)) {
                typeMaptoTimeout.get(type).cancel();
                typeMaptoTimeout.remove(type);
            }
        } finally {
            unlockWrite();
        }
    }

    @Override
    public void clearAll() {
        lockWrite();
        try {
            getAllTypes().forEach(type -> clear(type));
        } finally {
            unlockWrite();
        }
    }

    @Override
    public String toJson() {
        lockRead();
        try {
            return JSON.toJSONString(typeMaptoExpireDate);
        } finally {
            unlockRead();
        }
    }

}
