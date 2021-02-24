package ws.common.redis.jedis.pool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.util.Hashing;
import redis.clients.util.Pool;
import ws.common.redis.jedis.ShardedSentinelJedis;

public class ShardedJedisSentinelPool extends Pool<ShardedSentinelJedis> {
    private static final Logger logger = LoggerFactory.getLogger(ShardedJedisSentinelPool.class);
    public static final int MAX_RETRY_SENTINEL = 10;

    protected int timeout = Protocol.DEFAULT_TIMEOUT;

    protected int database = Protocol.DEFAULT_DATABASE;

    protected String password;

    protected GenericObjectPoolConfig poolConfig;

    protected Set<MasterListener> masterListeners = new HashSet<MasterListener>();

    private int sentinelRetry = 0;

    private volatile List<HostAndPort> currentHostMasters;

    public ShardedJedisSentinelPool(List<String> masters, Set<String> sentinels) {
        this(masters, sentinels, new GenericObjectPoolConfig(), Protocol.DEFAULT_TIMEOUT, null, Protocol.DEFAULT_DATABASE);
    }

    public ShardedJedisSentinelPool(List<String> masters, Set<String> sentinels, String password) {
        this(masters, sentinels, new GenericObjectPoolConfig(), Protocol.DEFAULT_TIMEOUT, password);
    }

    public ShardedJedisSentinelPool(List<String> masters, Set<String> sentinels, final GenericObjectPoolConfig poolConfig) {
        this(masters, sentinels, poolConfig, Protocol.DEFAULT_TIMEOUT, null, Protocol.DEFAULT_DATABASE);
    }

    public ShardedJedisSentinelPool(List<String> masters, Set<String> sentinels, final GenericObjectPoolConfig poolConfig, int timeout, final String password) {
        this(masters, sentinels, poolConfig, timeout, password, Protocol.DEFAULT_DATABASE);
    }

    public ShardedJedisSentinelPool(List<String> masters, Set<String> sentinels, final GenericObjectPoolConfig poolConfig, final int timeout) {
        this(masters, sentinels, poolConfig, timeout, null, Protocol.DEFAULT_DATABASE);
    }

    public ShardedJedisSentinelPool(List<String> masters, Set<String> sentinels, final GenericObjectPoolConfig poolConfig, final String password) {
        this(masters, sentinels, poolConfig, Protocol.DEFAULT_TIMEOUT, password);
    }

    public ShardedJedisSentinelPool(List<String> masters, Set<String> sentinels, final GenericObjectPoolConfig poolConfig, int timeout, final String password, final int database) {
        this.poolConfig = poolConfig;
        this.timeout = timeout;
        this.password = password;
        this.database = database;
        List<HostAndPort> masterList = initSentinels(sentinels, masters);
        initPool(masterList);
    }

    public void destroy() {
        for (MasterListener m : masterListeners) {
            m.shutdown();
        }
        super.destroy();
    }

    public List<HostAndPort> getCurrentHostMaster() {
        return currentHostMasters;
    }

    private void initPool(List<HostAndPort> masters) {
        if (!equals(currentHostMasters, masters)) {
            StringBuffer sb = new StringBuffer();
            for (HostAndPort master : masters) {
                sb.append(master.toString());
                sb.append(" ");
            }
            logger.debug("Created ShardedJedisPool to master at [" + sb.toString() + "]");
            List<JedisShardInfo> shardMasters = makeShardInfoList(masters);
            initPool(poolConfig, new ShardedJedisFactory(shardMasters, Hashing.MURMUR_HASH, null));
            currentHostMasters = masters;
        }
    }

    private boolean equals(List<HostAndPort> currentShardMasters, List<HostAndPort> shardMasters) {
        if (currentShardMasters != null && shardMasters != null) {
            if (currentShardMasters.size() == shardMasters.size()) {
                for (int i = 0; i < currentShardMasters.size(); i++) {
                    if (!currentShardMasters.get(i).equals(shardMasters.get(i))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    private List<JedisShardInfo> makeShardInfoList(List<HostAndPort> masters) {
        List<JedisShardInfo> shardMasters = new ArrayList<JedisShardInfo>();
        for (HostAndPort master : masters) {
            JedisShardInfo jedisShardInfo = new JedisShardInfo(master.getHost(), master.getPort(), timeout);
            jedisShardInfo.setPassword(password);
            shardMasters.add(jedisShardInfo);
        }
        return shardMasters;
    }

    private List<HostAndPort> initSentinels(Set<String> sentinels, final List<String> masterNames) {
        Map<String, HostAndPort> masterMap = new HashMap<String, HostAndPort>();
        List<HostAndPort> shardMasters = new ArrayList<HostAndPort>();
        logger.debug("Trying to find all master from available Sentinels...");
        for (String masterName : masterNames) {
            HostAndPort master = null;
            boolean fetched = false;
            while (!fetched && sentinelRetry < MAX_RETRY_SENTINEL) {
                for (String sentinel : sentinels) {
                    final HostAndPort hap = toHostAndPort(Arrays.asList(sentinel.split(":")));
                    logger.debug("Connecting to Sentinel " + hap);
                    Jedis jedis = new Jedis(hap.getHost(), hap.getPort());
                    try {
                        master = masterMap.get(masterName);
                        if (master == null) {
                            List<String> hostAndPort = jedis.sentinelGetMasterAddrByName(masterName);
                            if (hostAndPort != null && hostAndPort.size() > 0) {
                                master = toHostAndPort(hostAndPort);
                                logger.debug("Found Redis master at " + master);
                                shardMasters.add(master);
                                masterMap.put(masterName, master);
                                fetched = true;
                                jedis.disconnect();
                                break;
                            }
                        }
                    } finally {
                        jedis.close();
                    }
                }
                if (null == master) {
                    try {
                        logger.debug("All sentinels down, cannot determine where is " + masterName + " master is running... sleeping 1000ms, Will try again.");
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        logger.error("", e);
                    }
                    fetched = false;
                    sentinelRetry++;
                }
            }
            // Try MAX_RETRY_SENTINEL times.
            if (!fetched && sentinelRetry >= MAX_RETRY_SENTINEL) {
                logger.debug("All sentinels down and try " + MAX_RETRY_SENTINEL + " times, Abort.");
                throw new JedisConnectionException("Cannot connect all sentinels, Abort.");
            }
        }
        // All shards master must been accessed.
        if (masterNames.size() != 0 && masterNames.size() == shardMasters.size()) {
            logger.debug("Starting Sentinel listeners...");
            for (String sentinel : sentinels) {
                final HostAndPort hap = toHostAndPort(Arrays.asList(sentinel.split(":")));
                MasterListener masterListener = new MasterListener(masterNames, hap.getHost(), hap.getPort());
                masterListeners.add(masterListener);
                masterListener.start();
            }
        }
        return shardMasters;
    }

    private HostAndPort toHostAndPort(List<String> getMasterAddrByNameResult) {
        String host = getMasterAddrByNameResult.get(0);
        int port = Integer.parseInt(getMasterAddrByNameResult.get(1));
        return new HostAndPort(host, port);
    }

    /**
     * PoolableObjectFactory custom impl.
     */
    protected static class ShardedJedisFactory implements PooledObjectFactory<ShardedSentinelJedis> {
        private List<JedisShardInfo> shards;
        private Hashing algo;
        private Pattern keyTagPattern;

        public ShardedJedisFactory(List<JedisShardInfo> shards, Hashing algo, Pattern keyTagPattern) {
            this.shards = shards;
            this.algo = algo;
            this.keyTagPattern = keyTagPattern;
        }

        public PooledObject<ShardedSentinelJedis> makeObject() throws Exception {
            ShardedSentinelJedis jedis = new ShardedSentinelJedis(shards, algo, keyTagPattern);
            return new DefaultPooledObject<ShardedSentinelJedis>(jedis);
        }

        public void destroyObject(PooledObject<ShardedSentinelJedis> pooledShardedJedis) throws Exception {
            final ShardedSentinelJedis shardedJedis = pooledShardedJedis.getObject();
            for (Jedis jedis : shardedJedis.getAllShards()) {
                try {
                    try {
                        jedis.quit();
                    } catch (Exception e) {
                        logger.error(e.toString(), e);
                    }
                    jedis.disconnect();
                } catch (Exception e) {
                    logger.error(e.toString(), e);
                }
            }
        }

        public boolean validateObject(PooledObject<ShardedSentinelJedis> pooledShardedJedis) {
            try {
                ShardedSentinelJedis jedis = pooledShardedJedis.getObject();
                for (Jedis shard : jedis.getAllShards()) {
                    if (!shard.ping().equals("PONG")) {
                        return false;
                    }
                }
                return true;
            } catch (Exception e) {
                logger.error(e.toString(), e);
                return false;
            }
        }

        public void activateObject(PooledObject<ShardedSentinelJedis> p) throws Exception {
        }

        public void passivateObject(PooledObject<ShardedSentinelJedis> p) throws Exception {
        }
    }

    protected class JedisPubSubAdapter extends JedisPubSub {
        @Override
        public void onMessage(String channel, String message) {
        }

        @Override
        public void onPMessage(String pattern, String channel, String message) {
        }

        @Override
        public void onPSubscribe(String pattern, int subscribedChannels) {
        }

        @Override
        public void onPUnsubscribe(String pattern, int subscribedChannels) {
        }

        @Override
        public void onSubscribe(String channel, int subscribedChannels) {
        }

        @Override
        public void onUnsubscribe(String channel, int subscribedChannels) {
        }
    }

    protected class MasterListener extends Thread {
        protected List<String> masters;
        protected String host;
        protected int port;
        protected long subscribeRetryWaitTimeMillis = 5000;
        protected Jedis jedis;
        protected AtomicBoolean running = new AtomicBoolean(false);

        protected MasterListener() {
        }

        public MasterListener(List<String> masters, String host, int port) {
            this.masters = masters;
            this.host = host;
            this.port = port;
        }

        public MasterListener(List<String> masters, String host, int port, long subscribeRetryWaitTimeMillis) {
            this(masters, host, port);
            this.subscribeRetryWaitTimeMillis = subscribeRetryWaitTimeMillis;
        }

        public void run() {
            running.set(true);
            while (running.get()) {
                jedis = new Jedis(host, port);
                try {
                    jedis.subscribe(new JedisPubSubAdapter() {
                        @Override
                        public void onMessage(String channel, String message) {
                            logger.debug("Sentinel " + host + ":" + port + " published: " + message + ".");
                            String[] switchMasterMsg = message.split(" ");
                            if (switchMasterMsg.length > 3) {
                                int index = masters.indexOf(switchMasterMsg[0]);
                                if (index >= 0) {
                                    HostAndPort newHostMaster = toHostAndPort(Arrays.asList(switchMasterMsg[3], switchMasterMsg[4]));
                                    List<HostAndPort> newHostMasters = new ArrayList<HostAndPort>();
                                    for (int i = 0; i < masters.size(); i++) {
                                        newHostMasters.add(null);
                                    }
                                    Collections.copy(newHostMasters, currentHostMasters);
                                    newHostMasters.set(index, newHostMaster);
                                    initPool(newHostMasters);
                                } else {
                                    StringBuffer sb = new StringBuffer();
                                    for (String masterName : masters) {
                                        sb.append(masterName);
                                        sb.append(",");
                                    }
                                    logger.debug("Ignoring message on +switch-master for master name " + switchMasterMsg[0] + ", our monitor master name are [" + sb + "]");
                                }
                            } else {
                                logger.debug("Invalid message received on Sentinel " + host + ":" + port + " on channel +switch-master: " + message);
                            }
                        }
                    }, "+switch-master");
                } catch (JedisConnectionException e) {
                    logger.error(e.toString(), e);
                    if (running.get()) {
                        logger.debug("Lost connection to Sentinel at " + host + ":" + port + ". Sleeping 5000ms and retrying.");
                        try {
                            Thread.sleep(subscribeRetryWaitTimeMillis);
                        } catch (InterruptedException e1) {
                            logger.error(e1.toString(), e1);
                        }
                    } else {
                        logger.debug("Unsubscribing from Sentinel at " + host + ":" + port);
                    }
                }
            }
        }

        public void shutdown() {
            try {
                logger.debug("Shutting down listener on " + host + ":" + port);
                running.set(false);
                // This isn't good, the Jedis object is not thread safe
                jedis.disconnect();
            } catch (Exception e) {
                logger.debug("Caught exception while shutting down: " + e.getMessage(), e);
            }
        }
    }
}
