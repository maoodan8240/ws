package ws.common.redis.jedis;

import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolConfigCreator {

    /**
     * 
     * @param maxTotal
     *            最大连接数
     * @param maxIdlel
     *            最大空闲连接数
     * @param maxWaitSeconds
     *            获取连接时的最大等待秒数
     * @return
     */
    public static JedisPoolConfig create(int maxTotal, int maxIdlel, int maxWaitSeconds) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        // 连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
        config.setBlockWhenExhausted(true);
        // 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),超时就抛异常, 小于零:阻塞不确定的时间, 默认-1
        config.setMaxWaitMillis(maxWaitSeconds * 1000);
        config.setMaxIdle(maxIdlel);
        config.setJmxEnabled(true);
        config.setJmxNamePrefix("JedisPool");
        config.setTestOnBorrow(true);
        return config;
    }
}
