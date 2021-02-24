package ws.common.redis.jedis.implement;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;
import ws.common.redis.jedis.interfaces.JedisClient;

public class _JedisSentinelClient implements JedisClient {
    private static final Logger logger = LoggerFactory.getLogger(_JedisSentinelClient.class);
    private JedisSentinelPool pool;
    private String masterName;
    private String pwsd;
    private int maxTotal;
    private int maxIdlel;
    private long maxWaitMillis;// 获取连接时的最大等待毫秒数
    private int defaultObjectsCount;
    private String[] sentinelsIpAndPort;

    /**
     * @param masterName
     *            在开启Sentinel监听的机器上执行 redis-cli -p 7001 > get-master-addr-by-name {@link #masterName}
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
     * @param sentinelsIpAndPort
     *            sentinel所在服务器ip及sentinel端口，格式为：["127.0.0.1:7001","127.0.0.1:7002"]
     */
    public _JedisSentinelClient(String masterName, String pwsd, int maxTotal, int maxIdlel, int second, int defaultObjectsCount, String... sentinelsIpAndPort) {
        this.masterName = masterName;
        this.pwsd = pwsd;
        this.maxTotal = maxTotal;
        this.maxIdlel = maxIdlel;
        this.maxWaitMillis = second * 1000;
        this.defaultObjectsCount = defaultObjectsCount;
        this.sentinelsIpAndPort = sentinelsIpAndPort;
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
        logger.debug("Jedis pool config args：maxTotal={} maxIdlel={} maxWaitMillis={} defaultObjectsCount={} !", maxTotal, maxIdlel, maxWaitMillis, defaultObjectsCount);
        return config;
    }

    @Override
    public Jedis getJedis() {
        if (pool == null) {
            createJedisSentinelPool();
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

    private void createJedisSentinelPool() {
        JedisPoolConfig config = createJedisPoolConfig();
        Set<String> sentinels = new HashSet<String>();
        if (sentinelsIpAndPort != null) {
            for (int i = 0; i < sentinelsIpAndPort.length; i++) {
                sentinels.add(sentinelsIpAndPort[i]);
            }
        }
        pool = new JedisSentinelPool(masterName, sentinels, config, pwsd);
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