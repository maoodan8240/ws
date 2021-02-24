package ws.common.redis.jedis;

import java.util.List;
import java.util.regex.Pattern;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.util.Hashing;

public class ShardedSentinelJedis extends ShardedJedis {

    public ShardedSentinelJedis(List<JedisShardInfo> shards) {
        super(shards);
    }

    public ShardedSentinelJedis(List<JedisShardInfo> shards, Hashing algo) {
        super(shards, algo);
    }

    public ShardedSentinelJedis(List<JedisShardInfo> shards, Pattern keyTagPattern) {
        super(shards, keyTagPattern);
    }

    public ShardedSentinelJedis(List<JedisShardInfo> shards, Hashing algo, Pattern keyTagPattern) {
        super(shards, algo, keyTagPattern);
    }

    public void subscribe(JedisPubSub jedisPubSub, String channel) {
        Jedis j = (Jedis) getShard(channel);
        j.subscribe(jedisPubSub, channel);
    }

    public Long publish(String channel, String message) {
        Jedis j = (Jedis) getShard(channel);
        return j.publish(channel, message);
    }
}