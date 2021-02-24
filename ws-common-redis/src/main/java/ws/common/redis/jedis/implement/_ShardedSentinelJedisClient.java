package ws.common.redis.jedis.implement;

import java.util.List;
import java.util.Set;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.util.Pool;
import ws.common.redis.jedis.JedisPoolConfigCreator;
import ws.common.redis.jedis.ShardedSentinelJedis;
import ws.common.redis.jedis.interfaces.ShardedSentinelJedisClient;
import ws.common.redis.jedis.pool.ShardedJedisSentinelPool;

public class _ShardedSentinelJedisClient implements ShardedSentinelJedisClient {
    private ShardedJedisSentinelPool pool;
    private JedisPoolConfig poolConfig;
    private String pwsd;
    private List<String> masterNames;
    private Set<String> sentinelIpAndPorts;

    public _ShardedSentinelJedisClient(int maxTotal, int maxIdlel, int maxWaitSeconds, String pwsd, List<String> masterNames, Set<String> sentinelIpAndPorts) {
        poolConfig = JedisPoolConfigCreator.create(maxTotal, maxIdlel, maxWaitSeconds);
        this.pwsd = pwsd;
        this.masterNames = masterNames;
        this.sentinelIpAndPorts = sentinelIpAndPorts;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public synchronized ShardedSentinelJedis getShardedSentinelJedis() {
        if (pool == null) {
            createJedisSentinelPool();
        }
        ShardedSentinelJedis shardedSentinelJedis = pool.getResource();
        shardedSentinelJedis.setDataSource((Pool) pool);
        return shardedSentinelJedis;
    }

    private void createJedisSentinelPool() {
        pool = new ShardedJedisSentinelPool(masterNames, sentinelIpAndPorts, poolConfig, pwsd);
    }

    @Override
    public void returnResource(ShardedSentinelJedis shardedSentinelJedis) {
        if (shardedSentinelJedis != null) {
            shardedSentinelJedis.close();
        }
    }

    @Override
    public synchronized void close() {
        if (pool != null) {
            pool.close();
        }
    }
}