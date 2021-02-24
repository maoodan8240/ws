package ws.common.redis.jedis.implement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;
import ws.common.redis.jedis.interfaces.JedisClient;

public class _JedisClient implements JedisClient {
    private static final Logger logger = LoggerFactory.getLogger(_JedisClient.class);
    private JedisPool pool;

    private String host;
    private int port;
    private String pwsd;
    private int maxTotal;
    private int maxIdlel;
    private long maxWaitMillis;// 获取连接时的最大等待毫秒数
    private int defaultObjectsCount;

    /**
     * 
     * @param host
     *            master ip
     * @param port
     *            master port
     * @param pwsd
     *            master pwsd
     * @param maxTotal
     *            最大连接数
     * @param maxIdlel
     *            最大空闲连接数
     * @param second
     *            获取连接时的最大等待秒数
     * @param defaultObjectsCount
     *            初始化poll中的连接数
     */
    public _JedisClient(String host, int port, String pwsd, int maxTotal, int maxIdlel, int second, int defaultObjectsCount) {
        this.host = host;
        this.port = port;
        this.pwsd = pwsd;
        this.maxTotal = maxTotal;
        this.maxIdlel = maxIdlel;
        this.maxWaitMillis = second * 1000;
        this.defaultObjectsCount = defaultObjectsCount;
    }

    private JedisPoolConfig createJedisPoolConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        // 连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
        config.setBlockWhenExhausted(true);
        // 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),超时就抛异常, 小于零:阻塞不确定的时间, 默认-1
        config.setMaxWaitMillis(maxWaitMillis);
        config.setMaxIdle(maxIdlel);
        config.setJmxEnabled(true);
        config.setJmxNamePrefix("JedisPool");
        config.setTestOnBorrow(true);
        logger.debug("Jedis pool args host={} port={} maxTotal={} maxIdlel={} maxWaitMillis={} defaultObjectsCount={} !", host, port, maxTotal, maxIdlel, maxWaitMillis, defaultObjectsCount);
        return config;
    }

    @Override
    public Jedis getJedis() {
        if (pool == null) {
            createJedisPool();
        }
        Jedis jedis = pool.getResource();
        return jedis;
    }

    @Override
    public synchronized Jedis getJedis(int dbIdx) {
        Jedis jedis = pool.getResource();
        jedis.select(dbIdx);
        return jedis;
    }

    private void createJedisPool() {
        JedisPoolConfig config = createJedisPoolConfig();
        pool = new JedisPool(config, host, port, Protocol.DEFAULT_TIMEOUT, pwsd);
        pool.addObjects(defaultObjectsCount);
    }

    @Override
    public void returnResource(Jedis jedis) {
        if (jedis == null) {
            return;
        }
        try {
            jedis.close();
        } catch (Exception e) {
            logger.error("返回 Jedis 资源失败！", e);
        }
    }

    @Override
    public synchronized void close() {
        if (pool != null) {
            pool.close();
        }
    }
}