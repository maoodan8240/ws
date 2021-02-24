package ws.common.redis;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.BitPosParams;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.params.sortedset.ZAddParams;
import redis.clients.jedis.params.sortedset.ZIncrByParams;
import ws.common.redis.exception.RedisOprationException;
import ws.common.redis.exception.RedisOprationNonsupportRedisParamsException;
import ws.common.redis.jedis.ShardedSentinelJedis;
import ws.common.redis.jedis.implement._ShardedSentinelJedisClient;
import ws.common.redis.jedis.interfaces.ShardedSentinelJedisClient;
import ws.common.redis.operation.In_RedisOperation;
import ws.common.redis.operation.RedisOprationEnum.RedisDataStructureOp;
import ws.common.redis.operation.RedisParams;
import ws.common.redis.operation.RedisParams.AbstractBuilder;
import ws.common.redis.operation.bean.RedisTuple;

import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class _RedisOpration implements RedisOpration {
    private static final Map<String, Method> methodFlagToMethod = new ConcurrentHashMap<>();
    private ShardedSentinelJedisClient client;

    @Override
    public void init(int maxTotal, int maxIdlel, int maxWaitSeconds, String pwsd, List<String> masterNames, Set<String> sentinelIpAndPorts) {
        client = new _ShardedSentinelJedisClient(maxTotal, maxIdlel, maxWaitSeconds, pwsd, masterNames, sentinelIpAndPorts);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T execute(In_RedisOperation operation) {
        ShardedSentinelJedis jedis = null;
        try {
            jedis = client.getShardedSentinelJedis();
            Object rs = _execute(jedis, operation);
            return (T) parseResult(rs, operation.getParams().getOp().getResultClzz());
        } catch (Exception e) {
            throw new RedisOprationException("执行redis操作失败！ operation=" + operation, e);
        } finally {
            client.returnResource(jedis);
            jedis = null;
        }
    }

    private Object _execute(ShardedSentinelJedis jedis, In_RedisOperation operation) throws Exception {
        RedisParams.Params params = operation.getParams();
        RedisDataStructureOp<? extends AbstractBuilder, ?> op = params.getOp();

        if (params instanceof RedisParams.Params_100) {
            RedisParams.Params_100 p = (RedisParams.Params_100) params;
            Class<?>[] parameterTypes = new Class<?>[]{int.class, String.class};
            return getMethod(jedis, op, parameterTypes).invoke(jedis, p.getArg1(), p.getArg2());
        } else if (params instanceof RedisParams.Params_110) {
            RedisParams.Params_110 p = (RedisParams.Params_110) params;
            Class<?>[] parameterTypes = new Class<?>[]{String.class,};
            return getMethod(jedis, op, parameterTypes).invoke(jedis, p.getArg1());
        } else if (params instanceof RedisParams.Params_120) {
            RedisParams.Params_120 p = (RedisParams.Params_120) params;
            Class<?>[] parameterTypes = new Class<?>[]{String.class, boolean.class};
            return getMethod(jedis, op, parameterTypes).invoke(jedis, p.getArg1(), p.getArg2());
        } else if (params instanceof RedisParams.Params_130) {
            RedisParams.Params_130 p = (RedisParams.Params_130) params;
            Class<?>[] parameterTypes = new Class<?>[]{String.class, boolean.class, BitPosParams.class};
            return getMethod(jedis, op, parameterTypes).invoke(jedis, p.getArg1(), p.getArg2(), p.getArg3());
        } else if (params instanceof RedisParams.Params_140) {
            RedisParams.Params_140 p = (RedisParams.Params_140) params;
            Class<?>[] parameterTypes = new Class<?>[]{String.class, double.class};
            return getMethod(jedis, op, parameterTypes).invoke(jedis, p.getArg1(), p.getArg2());
        } else if (params instanceof RedisParams.Params_150) {
            RedisParams.Params_150 p = (RedisParams.Params_150) params;
            Class<?>[] parameterTypes = new Class<?>[]{String.class, double.class, double.class};
            return getMethod(jedis, op, parameterTypes).invoke(jedis, p.getArg1(), p.getArg2(), p.getArg3());
        } else if (params instanceof RedisParams.Params_160) {
            RedisParams.Params_160 p = (RedisParams.Params_160) params;
            Class<?>[] parameterTypes = new Class<?>[]{String.class, double.class, double.class, int.class, int.class};
            return getMethod(jedis, op, parameterTypes).invoke(jedis, p.getArg1(), p.getArg2(), p.getArg3(), p.getArg4(), p.getArg5());
        } else if (params instanceof RedisParams.Params_170) {
            RedisParams.Params_170 p = (RedisParams.Params_170) params;
            Class<?>[] parameterTypes = new Class<?>[]{String.class, double.class, String.class};
            return getMethod(jedis, op, parameterTypes).invoke(jedis, p.getArg1(), p.getArg2(), p.getArg3());
        } else if (params instanceof RedisParams.Params_180) {
            RedisParams.Params_180 p = (RedisParams.Params_180) params;
            Class<?>[] parameterTypes = new Class<?>[]{String.class, double.class, String.class, ZAddParams.class};
            return getMethod(jedis, op, parameterTypes).invoke(jedis, p.getArg1(), p.getArg2(), p.getArg3(), p.getArg4());
        } else if (params instanceof RedisParams.Params_190) {
            RedisParams.Params_190 p = (RedisParams.Params_190) params;
            Class<?>[] parameterTypes = new Class<?>[]{String.class, double.class, String.class, ZIncrByParams.class};
            return getMethod(jedis, op, parameterTypes).invoke(jedis, p.getArg1(), p.getArg2(), p.getArg3(), p.getArg4());
        } else if (params instanceof RedisParams.Params_200) {
            RedisParams.Params_200 p = (RedisParams.Params_200) params;
            Class<?>[] parameterTypes = new Class<?>[]{String.class, int.class};
            return getMethod(jedis, op, parameterTypes).invoke(jedis, p.getArg1(), p.getArg2());
        } else if (params instanceof RedisParams.Params_210) {
            RedisParams.Params_210 p = (RedisParams.Params_210) params;
            Class<?>[] parameterTypes = new Class<?>[]{String.class, int.class, String.class};
            return getMethod(jedis, op, parameterTypes).invoke(jedis, p.getArg1(), p.getArg2(), p.getArg3());
        } else if (params instanceof RedisParams.Params_220) {
            RedisParams.Params_220 p = (RedisParams.Params_220) params;
            Class<?>[] parameterTypes = new Class<?>[]{String.class, LIST_POSITION.class, String.class, String.class};
            return getMethod(jedis, op, parameterTypes).invoke(jedis, p.getArg1(), p.getArg2(), p.getArg3(), p.getArg4());
        } else if (params instanceof RedisParams.Params_230) {
            RedisParams.Params_230 p = (RedisParams.Params_230) params;
            Class<?>[] parameterTypes = new Class<?>[]{String.class, long.class};
            return getMethod(jedis, op, parameterTypes).invoke(jedis, p.getArg1(), p.getArg2());
        } else if (params instanceof RedisParams.Params_240) {
            RedisParams.Params_240 p = (RedisParams.Params_240) params;
            Class<?>[] parameterTypes = new Class<?>[]{String.class, long.class, boolean.class};
            return getMethod(jedis, op, parameterTypes).invoke(jedis, p.getArg1(), p.getArg2(), p.getArg3());
        } else if (params instanceof RedisParams.Params_250) {
            RedisParams.Params_250 p = (RedisParams.Params_250) params;
            Class<?>[] parameterTypes = new Class<?>[]{String.class, long.class, long.class};
            return getMethod(jedis, op, parameterTypes).invoke(jedis, p.getArg1(), p.getArg2(), p.getArg3());
        } else if (params instanceof RedisParams.Params_260) {
            RedisParams.Params_260 p = (RedisParams.Params_260) params;
            Class<?>[] parameterTypes = new Class<?>[]{String.class, long.class, String.class};
            return getMethod(jedis, op, parameterTypes).invoke(jedis, p.getArg1(), p.getArg2(), p.getArg3());
        } else if (params instanceof RedisParams.Params_270) {
            RedisParams.Params_270 p = (RedisParams.Params_270) params;
            Class<?>[] parameterTypes = new Class<?>[]{String.class, Map.class};
            return getMethod(jedis, op, parameterTypes).invoke(jedis, p.getArg1(), p.getArg2());
        } else if (params instanceof RedisParams.Params_280) {
            RedisParams.Params_280 p = (RedisParams.Params_280) params;
            Class<?>[] parameterTypes = new Class<?>[]{String.class, Map.class, ZAddParams.class};
            return getMethod(jedis, op, parameterTypes).invoke(jedis, p.getArg1(), p.getArg2(), p.getArg3());
        } else if (params instanceof RedisParams.Params_290) {
            RedisParams.Params_290 p = (RedisParams.Params_290) params;
            Class<?>[] parameterTypes = new Class<?>[]{String.class, Map.class};
            return getMethod(jedis, op, parameterTypes).invoke(jedis, p.getArg1(), p.getArg2());
        } else if (params instanceof RedisParams.Params_300) {
            RedisParams.Params_300 p = (RedisParams.Params_300) params;
            Class<?>[] parameterTypes = new Class<?>[]{String.class, SortingParams.class};
            return getMethod(jedis, op, parameterTypes).invoke(jedis, p.getArg1(), p.getArg2());
        } else if (params instanceof RedisParams.Params_310) {
            RedisParams.Params_310 p = (RedisParams.Params_310) params;
            Class<?>[] parameterTypes = new Class<?>[]{String.class, String.class};
            return getMethod(jedis, op, parameterTypes).invoke(jedis, p.getArg1(), p.getArg2());
        } else if (params instanceof RedisParams.Params_320) {
            RedisParams.Params_320 p = (RedisParams.Params_320) params;
            Class<?>[] parameterTypes = new Class<?>[]{String.class, String.class, double.class};
            return getMethod(jedis, op, parameterTypes).invoke(jedis, p.getArg1(), p.getArg2(), p.getArg3());
        } else if (params instanceof RedisParams.Params_330) {
            RedisParams.Params_330 p = (RedisParams.Params_330) params;
            Class<?>[] parameterTypes = new Class<?>[]{String.class, String.class, long.class};
            return getMethod(jedis, op, parameterTypes).invoke(jedis, p.getArg1(), p.getArg2(), p.getArg3());
        } else if (params instanceof RedisParams.Params_340) {
            RedisParams.Params_340 p = (RedisParams.Params_340) params;
            Class<?>[] parameterTypes = new Class<?>[]{String.class, String.class, String.class};
            return getMethod(jedis, op, parameterTypes).invoke(jedis, p.getArg1(), p.getArg2(), p.getArg3());
        } else if (params instanceof RedisParams.Params_350) {
            RedisParams.Params_350 p = (RedisParams.Params_350) params;
            Class<?>[] parameterTypes = new Class<?>[]{String.class, String.class, String.class, int.class, int.class};
            return getMethod(jedis, op, parameterTypes).invoke(jedis, p.getArg1(), p.getArg2(), p.getArg3(), p.getArg4(), p.getArg5());
        } else if (params instanceof RedisParams.Params_360) {
            RedisParams.Params_360 p = (RedisParams.Params_360) params;
            Class<?>[] parameterTypes = new Class<?>[]{String.class, String.class, String.class, String.class, long.class};
            return getMethod(jedis, op, parameterTypes).invoke(jedis, p.getArg1(), p.getArg2(), p.getArg3(), p.getArg4(), p.getArg5());
        } else if (params instanceof RedisParams.Params_370) {
            RedisParams.Params_370 p = (RedisParams.Params_370) params;
            Class<?>[] parameterTypes = new Class<?>[]{String.class, String[].class};
            return getMethod(jedis, op, parameterTypes).invoke(jedis, p.getArg1(), p.getArg2());
        }
        throw new RedisOprationNonsupportRedisParamsException("不支持的RedisParams！ operation=" + operation);
    }

    private static Object parseResult(Object obj, Class<?> resultClzz) {
        if (obj == null) {
            return null;
        }
        if (resultClzz.getClass().equals(Integer.class)) {
            return new Integer(obj.toString());
        } else if (resultClzz.equals(Double.class)) {
            return new Double(obj.toString());
        } else if (resultClzz.equals(Long.class)) {
            return new Long(obj.toString());
        } else if (resultClzz.equals(Boolean.class)) {
            return new Boolean(obj.toString());
        } else if (resultClzz.equals(Float.class)) {
            return new Float(obj.toString());
        } else if (resultClzz.equals(Set.class)) {
            return parseSet((Set<?>) obj);
        }
        return obj;
    }

    private static Set<Object> parseSet(Set<?> set) {
        Set<Object> setNew = new LinkedHashSet<>();
        for (Object object : set) {
            if (object instanceof Tuple) {
                setNew.add(new RedisTuple((Tuple) object));
            } else {
                setNew.add(object);
            }
        }
        return setNew;
    }

    private static String getMethodArgsFlag(Class<?>[] parameterTypes) {
        StringBuffer sb = new StringBuffer();
        for (Class<?> clazz : parameterTypes) {
            sb.append(clazz.getSimpleName()).append("->");
        }
        return sb.toString();
    }

    private static Method getMethod(ShardedSentinelJedis jedis, RedisDataStructureOp<? extends AbstractBuilder, ?> op, Class<?>[] parameterTypes) throws Exception {
        Method method = null;
        String methodName = op.getNickName();
        String methodFlag = methodName + ":" + getMethodArgsFlag(parameterTypes);
        if (methodFlagToMethod.containsKey(methodFlag)) {
            return methodFlagToMethod.get(methodFlag);
        } else {
            method = jedis.getClass().getMethod(methodName, parameterTypes);
            methodFlagToMethod.put(methodFlag, method);
        }
        return method;
    }
}
