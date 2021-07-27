package ws.common.redis.jedis.interfaces;

import redis.clients.jedis.Jedis;

public interface JedisClient {

    Jedis getJedis();

    Jedis getJedis(int dbIdx);

    void returnResource(Jedis jedis);

    void close();

    void _init(String host, int port, String pwsd, int maxTotal, int maxIdlel, int second, int defaultObjectsCount);

}