package ws.common.redis.jedis.interfaces;

import ws.common.redis.jedis.ShardedSentinelJedis;

public interface ShardedSentinelJedisClient {

    ShardedSentinelJedis getShardedSentinelJedis();

    void returnResource(ShardedSentinelJedis shardedSentinelJedis);

    void close();
}