package ws.common.redis.operation;

import java.util.List;
import java.util.Map;
import java.util.Set;

import ws.common.redis.exception.RedisOprationNewParmBuilderException;
import ws.common.redis.operation.bean.RedisTuple;

public class RedisOprationEnum {

    public static interface RedisDataStructureOp<I, O> {

        I newParmBuilder(int dbIdx);

        I newParmBuilder();

        Class<O> getResultClzz();

        O parseResult(Object object);

        String getNickName();
    }

    public static abstract class _RedisDataStructureOp<I, O> implements RedisDataStructureOp<I, O> {
        protected Class<I> builderClzz;
        protected Class<O> resultClzz;
        private String nickName;

        @SuppressWarnings("unchecked")
        protected _RedisDataStructureOp(Class<I> builderClzz, Class<?> resultClzz, String nickName) {
            this.builderClzz = builderClzz;
            this.resultClzz = (Class<O>) resultClzz;
            this.nickName = nickName;
        }

        public I newParmBuilder(int dbIdx) {
            try {
                return (I) builderClzz.getDeclaredConstructor(int.class, RedisDataStructureOp.class).newInstance(dbIdx, this);
            } catch (Exception e) {
                throw new RedisOprationNewParmBuilderException(builderClzz);
            }
        }

        public I newParmBuilder() {
            return newParmBuilder(0);
        }

        public Class<O> getResultClzz() {
            return resultClzz;
        }

        public String getNickName() {
            return nickName;
        }

        @SuppressWarnings("unchecked")
        @Override
        public O parseResult(Object object) {
            return (O) object;
        }
    }

    public static class Strings<I, O> extends _RedisDataStructureOp<I, O> {

        /** public String set(String key, String value); */
        public static final RedisOprationEnum.Strings<RedisParams.Params_310.Builder, String> set = new RedisOprationEnum.Strings<>(RedisParams.Params_310.Builder.class, String.class, "set");
        /** public String set(String key, String value, String nxxx, String expx, long time); */
        public static final RedisOprationEnum.Strings<RedisParams.Params_360.Builder, String> set_1 = new RedisOprationEnum.Strings<>(RedisParams.Params_360.Builder.class, String.class, "set");
        /** public String set(String key, String value, String nxxx); */
        public static final RedisOprationEnum.Strings<RedisParams.Params_340.Builder, String> set_2 = new RedisOprationEnum.Strings<>(RedisParams.Params_340.Builder.class, String.class, "set");
        /** public String get(String key); */
        public static final RedisOprationEnum.Strings<RedisParams.Params_110.Builder, String> get = new RedisOprationEnum.Strings<>(RedisParams.Params_110.Builder.class, String.class, "get");
        /** public Boolean setbit(String key, long offset, boolean value); */
        public static final RedisOprationEnum.Strings<RedisParams.Params_240.Builder, Boolean> setbit = new RedisOprationEnum.Strings<>(RedisParams.Params_240.Builder.class, Boolean.class, "setbit");
        /** public Boolean setbit(String key, long offset, String value); */
        public static final RedisOprationEnum.Strings<RedisParams.Params_260.Builder, Boolean> setbit_2 = new RedisOprationEnum.Strings<>(RedisParams.Params_260.Builder.class, Boolean.class, "setbit");
        /** public Boolean getbit(String key, long offset); */
        public static final RedisOprationEnum.Strings<RedisParams.Params_230.Builder, Boolean> getbit = new RedisOprationEnum.Strings<>(RedisParams.Params_230.Builder.class, Boolean.class, "getbit");
        /** public Long setrange(String key, long offset, String value); */
        public static final RedisOprationEnum.Strings<RedisParams.Params_260.Builder, Long> setrange = new RedisOprationEnum.Strings<>(RedisParams.Params_260.Builder.class, Long.class, "setrange");
        /** public String getrange(String key, long startOffset, long endOffset); */
        public static final RedisOprationEnum.Strings<RedisParams.Params_250.Builder, String> getrange = new RedisOprationEnum.Strings<>(RedisParams.Params_250.Builder.class, String.class, "getrange");
        /** public String getSet(String key, String value); */
        public static final RedisOprationEnum.Strings<RedisParams.Params_310.Builder, String> getSet = new RedisOprationEnum.Strings<>(RedisParams.Params_310.Builder.class, String.class, "getSet");
        /** public Long setnx(String key, String value); */
        public static final RedisOprationEnum.Strings<RedisParams.Params_310.Builder, Long> setnx = new RedisOprationEnum.Strings<>(RedisParams.Params_310.Builder.class, Long.class, "setnx");
        /** public String setex(String key, int seconds, String value); */
        public static final RedisOprationEnum.Strings<RedisParams.Params_210.Builder, String> setex = new RedisOprationEnum.Strings<>(RedisParams.Params_210.Builder.class, String.class, "setex");
        /** public String psetex(String key, long milliseconds, String value); */
        public static final RedisOprationEnum.Strings<RedisParams.Params_260.Builder, String> psetex = new RedisOprationEnum.Strings<>(RedisParams.Params_260.Builder.class, String.class, "psetex");
        /** public Long decrBy(String key, long integer); */
        public static final RedisOprationEnum.Strings<RedisParams.Params_230.Builder, Long> decrBy = new RedisOprationEnum.Strings<>(RedisParams.Params_230.Builder.class, Long.class, "decrBy");
        /** public Long decr(String key); */
        public static final RedisOprationEnum.Strings<RedisParams.Params_110.Builder, Long> decr = new RedisOprationEnum.Strings<>(RedisParams.Params_110.Builder.class, Long.class, "decr");
        /** public Long incrBy(String key, long integer); */
        public static final RedisOprationEnum.Strings<RedisParams.Params_230.Builder, Long> incrBy = new RedisOprationEnum.Strings<>(RedisParams.Params_230.Builder.class, Long.class, "incrBy");
        /** public Double incrByFloat(String key, double integer); */
        public static final RedisOprationEnum.Strings<RedisParams.Params_140.Builder, Double> incrByFloat = new RedisOprationEnum.Strings<>(RedisParams.Params_140.Builder.class, Double.class, "incrByFloat");
        /** public Long incr(String key); */
        public static final RedisOprationEnum.Strings<RedisParams.Params_110.Builder, Long> incr = new RedisOprationEnum.Strings<>(RedisParams.Params_110.Builder.class, Long.class, "incr");
        /** public Long append(String key, String value); */
        public static final RedisOprationEnum.Strings<RedisParams.Params_310.Builder, Long> append = new RedisOprationEnum.Strings<>(RedisParams.Params_310.Builder.class, Long.class, "append");
        /** public Long strlen(final String key); */
        public static final RedisOprationEnum.Strings<RedisParams.Params_110.Builder, Long> strlen = new RedisOprationEnum.Strings<>(RedisParams.Params_110.Builder.class, Long.class, "strlen");
        /** public Long bitcount(final String key); */
        public static final RedisOprationEnum.Strings<RedisParams.Params_110.Builder, Long> bitcount = new RedisOprationEnum.Strings<>(RedisParams.Params_110.Builder.class, Long.class, "bitcount");
        /** public Long bitcount(final String key, long start, long end); */
        public static final RedisOprationEnum.Strings<RedisParams.Params_250.Builder, Long> bitcount_2 = new RedisOprationEnum.Strings<>(RedisParams.Params_250.Builder.class, Long.class, "bitcount");
        /** public Long bitpos(String key, boolean value); */
        public static final RedisOprationEnum.Strings<RedisParams.Params_120.Builder, Long> bitpos = new RedisOprationEnum.Strings<>(RedisParams.Params_120.Builder.class, Long.class, "bitpos");
        /** public Long bitpos(String key, boolean value, BitPosParams params); */
        public static final RedisOprationEnum.Strings<RedisParams.Params_130.Builder, Long> bitpos_2 = new RedisOprationEnum.Strings<>(RedisParams.Params_130.Builder.class, Long.class, "bitpos");

        protected Strings(Class<I> builderClzz, Class<?> resultClzz, String nickName) {
            super(builderClzz, resultClzz, nickName);
        }
    }

    public static class SortedSets<I, O> extends _RedisDataStructureOp<I, O> {

        /** public Long zadd(String key, double score, String member); */
        public static final RedisOprationEnum.SortedSets<RedisParams.Params_170.Builder, Long> zadd = new RedisOprationEnum.SortedSets<>(RedisParams.Params_170.Builder.class, Long.class, "zadd");
        /** public Long zadd(String key, double score, String member, ZAddParams params); */
        public static final RedisOprationEnum.SortedSets<RedisParams.Params_180.Builder, Long> zadd_1 = new RedisOprationEnum.SortedSets<>(RedisParams.Params_180.Builder.class, Long.class, "zadd");
        /** public Long zadd(String key, Map<String, Double> scoreMembers); */
        public static final RedisOprationEnum.SortedSets<RedisParams.Params_270.Builder, Long> zadd_2 = new RedisOprationEnum.SortedSets<>(RedisParams.Params_270.Builder.class, Long.class, "zadd");
        /** public Long zadd(String key, Map<String, Double> scoreMembers, ZAddParams params); */
        public static final RedisOprationEnum.SortedSets<RedisParams.Params_280.Builder, Long> zadd_3 = new RedisOprationEnum.SortedSets<>(RedisParams.Params_280.Builder.class, Long.class, "zadd");
        /** public Set<String> zrange(String key, long start, long end); */
        public static final RedisOprationEnum.SortedSets<RedisParams.Params_250.Builder, Set<String>> zrange = new RedisOprationEnum.SortedSets<>(RedisParams.Params_250.Builder.class, Set.class, "zrange");
        /** public Long zrem(String key, String... members); */
        public static final RedisOprationEnum.SortedSets<RedisParams.Params_370.Builder, Long> zrem = new RedisOprationEnum.SortedSets<>(RedisParams.Params_370.Builder.class, Long.class, "zrem");
        /** public Double zincrby(String key, double score, String member); */
        public static final RedisOprationEnum.SortedSets<RedisParams.Params_170.Builder, Double> zincrby = new RedisOprationEnum.SortedSets<>(RedisParams.Params_170.Builder.class, Double.class, "zincrby");
        /** public Double zincrby(String key, double score, String member, ZIncrByParams params); */
        public static final RedisOprationEnum.SortedSets<RedisParams.Params_190.Builder, Double> zincrby_1 = new RedisOprationEnum.SortedSets<>(RedisParams.Params_190.Builder.class, Double.class, "zincrby");
        /** public Long zrank(String key, String member); */
        public static final RedisOprationEnum.SortedSets<RedisParams.Params_310.Builder, Long> zrank = new RedisOprationEnum.SortedSets<>(RedisParams.Params_310.Builder.class, Long.class, "zrank");
        /** public Long zrevrank(String key, String member); */
        public static final RedisOprationEnum.SortedSets<RedisParams.Params_310.Builder, Long> zrevrank = new RedisOprationEnum.SortedSets<>(RedisParams.Params_310.Builder.class, Long.class, "zrevrank");
        /** public Set<String> zrevrange(String key, long start, long end); */
        public static final RedisOprationEnum.SortedSets<RedisParams.Params_250.Builder, Set<String>> zrevrange = new RedisOprationEnum.SortedSets<>(RedisParams.Params_250.Builder.class, Set.class, "zrevrange");
        /** public Set<RedisTuple> zrangeWithScores(String key, long start, long end); */
        public static final RedisOprationEnum.SortedSets<RedisParams.Params_250.Builder, Set<RedisTuple>> zrangeWithScores = new RedisOprationEnum.SortedSets<>(RedisParams.Params_250.Builder.class, Set.class, "zrangeWithScores");
        /** public Set<RedisTuple> zrevrangeWithScores(String key, long start, long end); */
        public static final RedisOprationEnum.SortedSets<RedisParams.Params_250.Builder, Set<RedisTuple>> zrevrangeWithScores = new RedisOprationEnum.SortedSets<>(RedisParams.Params_250.Builder.class, Set.class, "zrevrangeWithScores");
        /** public Long zcard(String key); */
        public static final RedisOprationEnum.SortedSets<RedisParams.Params_110.Builder, Long> zcard = new RedisOprationEnum.SortedSets<>(RedisParams.Params_110.Builder.class, Long.class, "zcard");
        /** public Double zscore(String key, String member); */
        public static final RedisOprationEnum.SortedSets<RedisParams.Params_310.Builder, Double> zscore = new RedisOprationEnum.SortedSets<>(RedisParams.Params_310.Builder.class, Double.class, "zscore");
        /** public Long zcount(String key, double min, double max); */
        public static final RedisOprationEnum.SortedSets<RedisParams.Params_150.Builder, Long> zcount = new RedisOprationEnum.SortedSets<>(RedisParams.Params_150.Builder.class, Long.class, "zcount");
        /** public Long zcount(String key, String min, String max); */
        public static final RedisOprationEnum.SortedSets<RedisParams.Params_340.Builder, Long> zcount_1 = new RedisOprationEnum.SortedSets<>(RedisParams.Params_340.Builder.class, Long.class, "zcount");
        /** public Set<String> zrangeByScore(String key, double min, double max); */
        public static final RedisOprationEnum.SortedSets<RedisParams.Params_150.Builder, Set<String>> zrangeByScore = new RedisOprationEnum.SortedSets<>(RedisParams.Params_150.Builder.class, Set.class, "zrangeByScore");
        /** public Set<String> zrangeByScore(String key, double min, double max, int offset, int count); */
        public static final RedisOprationEnum.SortedSets<RedisParams.Params_160.Builder, Set<String>> zrangeByScore_1 = new RedisOprationEnum.SortedSets<>(RedisParams.Params_160.Builder.class, Set.class, "zrangeByScore");
        /** public Set<String> zrangeByScore(String key, String min, String max); */
        public static final RedisOprationEnum.SortedSets<RedisParams.Params_340.Builder, Set<String>> zrangeByScore_2 = new RedisOprationEnum.SortedSets<>(RedisParams.Params_340.Builder.class, Set.class, "zrangeByScore");
        /** public Set<String> zrangeByScore(String key, String min, String max, int offset, int count); */
        public static final RedisOprationEnum.SortedSets<RedisParams.Params_350.Builder, Set<String>> zrangeByScore_3 = new RedisOprationEnum.SortedSets<>(RedisParams.Params_350.Builder.class, Set.class, "zrangeByScore");
        /** public Set<String> zrevrangeByScore(String key, double max, double min); */
        public static final RedisOprationEnum.SortedSets<RedisParams.Params_150.Builder, Set<String>> zrevrangeByScore = new RedisOprationEnum.SortedSets<>(RedisParams.Params_150.Builder.class, Set.class, "zrevrangeByScore");
        /** public Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count); */
        public static final RedisOprationEnum.SortedSets<RedisParams.Params_160.Builder, Set<String>> zrevrangeByScore_1 = new RedisOprationEnum.SortedSets<>(RedisParams.Params_160.Builder.class, Set.class, "zrevrangeByScore");
        /** public Set<String> zrevrangeByScore(String key, String max, String min); */
        public static final RedisOprationEnum.SortedSets<RedisParams.Params_340.Builder, Set<String>> zrevrangeByScore_2 = new RedisOprationEnum.SortedSets<>(RedisParams.Params_340.Builder.class, Set.class, "zrevrangeByScore");
        /** public Set<String> zrevrangeByScore(String key, String max, String min, int offset, int count); */
        public static final RedisOprationEnum.SortedSets<RedisParams.Params_350.Builder, Set<String>> zrevrangeByScore_3 = new RedisOprationEnum.SortedSets<>(RedisParams.Params_350.Builder.class, Set.class, "zrevrangeByScore");
        /** public Set<RedisTuple> zrangeByScoreWithScores(String key, double min, double max); */
        public static final RedisOprationEnum.SortedSets<RedisParams.Params_150.Builder, Set<RedisTuple>> zrangeByScoreWithScores = new RedisOprationEnum.SortedSets<>(RedisParams.Params_150.Builder.class, Set.class, "zrangeByScoreWithScores");
        /** public Set<RedisTuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count); */
        public static final RedisOprationEnum.SortedSets<RedisParams.Params_160.Builder, Set<RedisTuple>> zrangeByScoreWithScores_1 = new RedisOprationEnum.SortedSets<>(RedisParams.Params_160.Builder.class, Set.class, "zrangeByScoreWithScores");
        /** public Set<RedisTuple> zrangeByScoreWithScores(String key, String min, String max); */
        public static final RedisOprationEnum.SortedSets<RedisParams.Params_340.Builder, Set<RedisTuple>> zrangeByScoreWithScores_2 = new RedisOprationEnum.SortedSets<>(RedisParams.Params_340.Builder.class, Set.class, "zrangeByScoreWithScores");
        /** public Set<RedisTuple> zrangeByScoreWithScores(String key, String min, String max, int offset, int count); */
        public static final RedisOprationEnum.SortedSets<RedisParams.Params_350.Builder, Set<RedisTuple>> zrangeByScoreWithScores_3 = new RedisOprationEnum.SortedSets<>(RedisParams.Params_350.Builder.class, Set.class, "zrangeByScoreWithScores");
        /** public Set<RedisTuple> zrevrangeByScoreWithScores(String key, double max, double min); */
        public static final RedisOprationEnum.SortedSets<RedisParams.Params_150.Builder, Set<RedisTuple>> zrevrangeByScoreWithScores = new RedisOprationEnum.SortedSets<>(RedisParams.Params_150.Builder.class, Set.class, "zrevrangeByScoreWithScores");
        /** public Set<RedisTuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count); */
        public static final RedisOprationEnum.SortedSets<RedisParams.Params_160.Builder, Set<RedisTuple>> zrevrangeByScoreWithScores_1 = new RedisOprationEnum.SortedSets<>(RedisParams.Params_160.Builder.class, Set.class, "zrevrangeByScoreWithScores");
        /** public Set<RedisTuple> zrevrangeByScoreWithScores(String key, String max, String min); */
        public static final RedisOprationEnum.SortedSets<RedisParams.Params_340.Builder, Set<RedisTuple>> zrevrangeByScoreWithScores_2 = new RedisOprationEnum.SortedSets<>(RedisParams.Params_340.Builder.class, Set.class, "zrevrangeByScoreWithScores");
        /** public Set<RedisTuple> zrevrangeByScoreWithScores(String key, String max, String min, int offset, int count); */
        public static final RedisOprationEnum.SortedSets<RedisParams.Params_350.Builder, Set<RedisTuple>> zrevrangeByScoreWithScores_3 = new RedisOprationEnum.SortedSets<>(RedisParams.Params_350.Builder.class, Set.class, "zrevrangeByScoreWithScores");
        /** public Long zremrangeByRank(String key, long start, long end); */
        public static final RedisOprationEnum.SortedSets<RedisParams.Params_250.Builder, Long> zremrangeByRank = new RedisOprationEnum.SortedSets<>(RedisParams.Params_250.Builder.class, Long.class, "zremrangeByRank");
        /** public Long zremrangeByScore(String key, double start, double end); */
        public static final RedisOprationEnum.SortedSets<RedisParams.Params_150.Builder, Long> zremrangeByScore = new RedisOprationEnum.SortedSets<>(RedisParams.Params_150.Builder.class, Long.class, "zremrangeByScore");
        /** public Long zremrangeByScore(String key, String start, String end); */
        public static final RedisOprationEnum.SortedSets<RedisParams.Params_340.Builder, Long> zremrangeByScore_1 = new RedisOprationEnum.SortedSets<>(RedisParams.Params_340.Builder.class, Long.class, "zremrangeByScore");
        /** public Long zlexcount(final String key, final String min, final String max); */
        public static final RedisOprationEnum.SortedSets<RedisParams.Params_340.Builder, Long> zlexcount = new RedisOprationEnum.SortedSets<>(RedisParams.Params_340.Builder.class, Long.class, "zlexcount");
        /** public Set<String> zrangeByLex(final String key, final String min, final String max); */
        public static final RedisOprationEnum.SortedSets<RedisParams.Params_340.Builder, Set<String>> zrangeByLex = new RedisOprationEnum.SortedSets<>(RedisParams.Params_340.Builder.class, Set.class, "zrangeByLex");
        /** public Set<String> zrangeByLex(final String key, final String min, final String max, final int offset, final int count); */
        public static final RedisOprationEnum.SortedSets<RedisParams.Params_350.Builder, Set<String>> zrangeByLex_1 = new RedisOprationEnum.SortedSets<>(RedisParams.Params_350.Builder.class, Set.class, "zrangeByLex");
        /** public Set<String> zrevrangeByLex(String key, String max, String min); */
        public static final RedisOprationEnum.SortedSets<RedisParams.Params_340.Builder, Set<String>> zrevrangeByLex = new RedisOprationEnum.SortedSets<>(RedisParams.Params_340.Builder.class, Set.class, "zrevrangeByLex");
        /** public Set<String> zrevrangeByLex(String key, String max, String min, int offset, int count); */
        public static final RedisOprationEnum.SortedSets<RedisParams.Params_350.Builder, Set<String>> zrevrangeByLex_1 = new RedisOprationEnum.SortedSets<>(RedisParams.Params_350.Builder.class, Set.class, "zrevrangeByLex");
        /** public Long zremrangeByLex(final String key, final String min, final String max); */
        public static final RedisOprationEnum.SortedSets<RedisParams.Params_340.Builder, Long> zremrangeByLex = new RedisOprationEnum.SortedSets<>(RedisParams.Params_340.Builder.class, Long.class, "zremrangeByLex");

        protected SortedSets(Class<I> builderClzz, Class<?> resultClzz, String nickName) {
            super(builderClzz, resultClzz, nickName);
        }
    }

    public static class Sets<I, O> extends _RedisDataStructureOp<I, O> {

        /** public Long sadd(String key, String... members); */
        public static final RedisOprationEnum.Sets<RedisParams.Params_370.Builder, Long> sadd = new RedisOprationEnum.Sets<>(RedisParams.Params_370.Builder.class, Long.class, "sadd");
        /** public Set<String> smembers(String key); */
        public static final RedisOprationEnum.Sets<RedisParams.Params_110.Builder, Set<String>> smembers = new RedisOprationEnum.Sets<>(RedisParams.Params_110.Builder.class, Set.class, "smembers");
        /** public Long srem(String key, String... members); */
        public static final RedisOprationEnum.Sets<RedisParams.Params_370.Builder, Long> srem = new RedisOprationEnum.Sets<>(RedisParams.Params_370.Builder.class, Long.class, "srem");
        /** public String spop(String key); */
        public static final RedisOprationEnum.Sets<RedisParams.Params_110.Builder, String> spop = new RedisOprationEnum.Sets<>(RedisParams.Params_110.Builder.class, String.class, "spop");
        /** public Set<String> spop(String key, long count); */
        public static final RedisOprationEnum.Sets<RedisParams.Params_230.Builder, Set<String>> spop_1 = new RedisOprationEnum.Sets<>(RedisParams.Params_230.Builder.class, Set.class, "spop");
        /** public Long scard(String key); */
        public static final RedisOprationEnum.Sets<RedisParams.Params_110.Builder, Long> scard = new RedisOprationEnum.Sets<>(RedisParams.Params_110.Builder.class, Long.class, "scard");
        /** public Boolean sismember(String key, String member); */
        public static final RedisOprationEnum.Sets<RedisParams.Params_310.Builder, Boolean> sismember = new RedisOprationEnum.Sets<>(RedisParams.Params_310.Builder.class, Boolean.class, "sismember");
        /** public String srandmember(String key); */
        public static final RedisOprationEnum.Sets<RedisParams.Params_110.Builder, String> srandmember = new RedisOprationEnum.Sets<>(RedisParams.Params_110.Builder.class, String.class, "srandmember");
        /** public List<String> srandmember(String key, int count); */
        public static final RedisOprationEnum.Sets<RedisParams.Params_200.Builder, List<String>> srandmember_1 = new RedisOprationEnum.Sets<>(RedisParams.Params_200.Builder.class, List.class, "srandmember");

        protected Sets(Class<I> builderClzz, Class<?> resultClzz, String nickName) {
            super(builderClzz, resultClzz, nickName);
        }
    }

    public static class Lists<I, O> extends _RedisDataStructureOp<I, O> {

        /** public List<String> blpop(String arg); */
        public static final RedisOprationEnum.Lists<RedisParams.Params_110.Builder, List<String>> blpop = new RedisOprationEnum.Lists<>(RedisParams.Params_110.Builder.class, List.class, "blpop");
        /** public List<String> blpop(int timeout, String key); */
        public static final RedisOprationEnum.Lists<RedisParams.Params_100.Builder, List<String>> blpop_1 = new RedisOprationEnum.Lists<>(RedisParams.Params_100.Builder.class, List.class, "blpop");
        /** public List<String> brpop(String arg); */
        public static final RedisOprationEnum.Lists<RedisParams.Params_110.Builder, List<String>> brpop = new RedisOprationEnum.Lists<>(RedisParams.Params_110.Builder.class, List.class, "brpop");
        /** public List<String> brpop(int timeout, String key); */
        public static final RedisOprationEnum.Lists<RedisParams.Params_100.Builder, List<String>> brpop_1 = new RedisOprationEnum.Lists<>(RedisParams.Params_100.Builder.class, List.class, "brpop");
        /** public Long rpush(String key, String... strings); */
        public static final RedisOprationEnum.Lists<RedisParams.Params_370.Builder, Long> rpush = new RedisOprationEnum.Lists<>(RedisParams.Params_370.Builder.class, Long.class, "rpush");
        /** public Long lpush(String key, String... strings); */
        public static final RedisOprationEnum.Lists<RedisParams.Params_370.Builder, Long> lpush = new RedisOprationEnum.Lists<>(RedisParams.Params_370.Builder.class, Long.class, "lpush");
        /** public Long lpushx(String key, String... string); */
        public static final RedisOprationEnum.Lists<RedisParams.Params_370.Builder, Long> lpushx = new RedisOprationEnum.Lists<>(RedisParams.Params_370.Builder.class, Long.class, "lpushx");
        /** public Long rpushx(String key, String... string); */
        public static final RedisOprationEnum.Lists<RedisParams.Params_370.Builder, Long> rpushx = new RedisOprationEnum.Lists<>(RedisParams.Params_370.Builder.class, Long.class, "rpushx");
        /** public Long llen(String key); */
        public static final RedisOprationEnum.Lists<RedisParams.Params_110.Builder, Long> llen = new RedisOprationEnum.Lists<>(RedisParams.Params_110.Builder.class, Long.class, "llen");
        /** public List<String> lrange(String key, long start, long end); */
        public static final RedisOprationEnum.Lists<RedisParams.Params_250.Builder, List<String>> lrange = new RedisOprationEnum.Lists<>(RedisParams.Params_250.Builder.class, List.class, "lrange");
        /** public String ltrim(String key, long start, long end); */
        public static final RedisOprationEnum.Lists<RedisParams.Params_250.Builder, String> ltrim = new RedisOprationEnum.Lists<>(RedisParams.Params_250.Builder.class, String.class, "ltrim");
        /** public String lindex(String key, long index); */
        public static final RedisOprationEnum.Lists<RedisParams.Params_230.Builder, String> lindex = new RedisOprationEnum.Lists<>(RedisParams.Params_230.Builder.class, String.class, "lindex");
        /** public String lset(String key, long index, String value); */
        public static final RedisOprationEnum.Lists<RedisParams.Params_260.Builder, String> lset = new RedisOprationEnum.Lists<>(RedisParams.Params_260.Builder.class, String.class, "lset");
        /** public Long lrem(String key, long count, String value); */
        public static final RedisOprationEnum.Lists<RedisParams.Params_260.Builder, Long> lrem = new RedisOprationEnum.Lists<>(RedisParams.Params_260.Builder.class, Long.class, "lrem");
        /** public String lpop(String key); */
        public static final RedisOprationEnum.Lists<RedisParams.Params_110.Builder, String> lpop = new RedisOprationEnum.Lists<>(RedisParams.Params_110.Builder.class, String.class, "lpop");
        /** public String rpop(String key); */
        public static final RedisOprationEnum.Lists<RedisParams.Params_110.Builder, String> rpop = new RedisOprationEnum.Lists<>(RedisParams.Params_110.Builder.class, String.class, "rpop");
        /** public Long linsert(String key, LIST_POSITION where, String pivot, String value); */
        public static final RedisOprationEnum.Lists<RedisParams.Params_220.Builder, Long> linsert = new RedisOprationEnum.Lists<>(RedisParams.Params_220.Builder.class, Long.class, "linsert");

        protected Lists(Class<I> builderClzz, Class<?> resultClzz, String nickName) {
            super(builderClzz, resultClzz, nickName);
        }
    }

    public static class Keys<I, O> extends _RedisDataStructureOp<I, O> {

        /** public Boolean exists(String key); */
        public static final RedisOprationEnum.Keys<RedisParams.Params_110.Builder, Boolean> exists = new RedisOprationEnum.Keys<>(RedisParams.Params_110.Builder.class, Boolean.class, "exists");
        /** public String type(String key); */
        public static final RedisOprationEnum.Keys<RedisParams.Params_110.Builder, String> type = new RedisOprationEnum.Keys<>(RedisParams.Params_110.Builder.class, String.class, "type");
        /** public Long expire(String key, int seconds); */
        public static final RedisOprationEnum.Keys<RedisParams.Params_200.Builder, Long> expire = new RedisOprationEnum.Keys<>(RedisParams.Params_200.Builder.class, Long.class, "expire");
        /** public Long pexpire(final String key, final long milliseconds); */
        public static final RedisOprationEnum.Keys<RedisParams.Params_230.Builder, Long> pexpire = new RedisOprationEnum.Keys<>(RedisParams.Params_230.Builder.class, Long.class, "pexpire");
        /** public Long expireAt(String key, long unixTime); */
        public static final RedisOprationEnum.Keys<RedisParams.Params_230.Builder, Long> expireAt = new RedisOprationEnum.Keys<>(RedisParams.Params_230.Builder.class, Long.class, "expireAt");
        /** public Long pexpireAt(String key, long millisecondsTimestamp); */
        public static final RedisOprationEnum.Keys<RedisParams.Params_230.Builder, Long> pexpireAt = new RedisOprationEnum.Keys<>(RedisParams.Params_230.Builder.class, Long.class, "pexpireAt");
        /** public Long ttl(String key); */
        public static final RedisOprationEnum.Keys<RedisParams.Params_110.Builder, Long> ttl = new RedisOprationEnum.Keys<>(RedisParams.Params_110.Builder.class, Long.class, "ttl");
        /** public Long pttl(String key); */
        public static final RedisOprationEnum.Keys<RedisParams.Params_110.Builder, Long> pttl = new RedisOprationEnum.Keys<>(RedisParams.Params_110.Builder.class, Long.class, "pttl");
        /** public Long del(String key); */
        public static final RedisOprationEnum.Keys<RedisParams.Params_110.Builder, Long> del = new RedisOprationEnum.Keys<>(RedisParams.Params_110.Builder.class, Long.class, "del");
        /** public Long move(String key, int dbIndex); */
        public static final RedisOprationEnum.Keys<RedisParams.Params_200.Builder, Long> move = new RedisOprationEnum.Keys<>(RedisParams.Params_200.Builder.class, Long.class, "move");
        /** public Long persist(final String key); */
        public static final RedisOprationEnum.Keys<RedisParams.Params_110.Builder, Long> persist = new RedisOprationEnum.Keys<>(RedisParams.Params_110.Builder.class, Long.class, "persist");
        /** public List<String> sort(String key); */
        public static final RedisOprationEnum.Keys<RedisParams.Params_110.Builder, List<String>> sort = new RedisOprationEnum.Keys<>(RedisParams.Params_110.Builder.class, List.class, "sort");
        /** public List<String> sort(String key, SortingParams sortingParameters); */
        public static final RedisOprationEnum.Keys<RedisParams.Params_300.Builder, List<String>> sort_1 = new RedisOprationEnum.Keys<>(RedisParams.Params_300.Builder.class, List.class, "sort");

        protected Keys(Class<I> builderClzz, Class<?> resultClzz, String nickName) {
            super(builderClzz, resultClzz, nickName);
        }
    }

    public static class Hashes<I, O> extends _RedisDataStructureOp<I, O> {

        /** public Long hset(String key, String field, String value); */
        public static final RedisOprationEnum.Hashes<RedisParams.Params_340.Builder, Long> hset = new RedisOprationEnum.Hashes<>(RedisParams.Params_340.Builder.class, Long.class, "hset");
        /** public String hget(String key, String field); */
        public static final RedisOprationEnum.Hashes<RedisParams.Params_310.Builder, String> hget = new RedisOprationEnum.Hashes<>(RedisParams.Params_310.Builder.class, String.class, "hget");
        /** public Long hsetnx(String key, String field, String value); */
        public static final RedisOprationEnum.Hashes<RedisParams.Params_340.Builder, Long> hsetnx = new RedisOprationEnum.Hashes<>(RedisParams.Params_340.Builder.class, Long.class, "hsetnx");
        /** public String hmset(String key, Map<String, String> hash); */
        public static final RedisOprationEnum.Hashes<RedisParams.Params_290.Builder, String> hmset = new RedisOprationEnum.Hashes<>(RedisParams.Params_290.Builder.class, String.class, "hmset");
        /** public List<String> hmget(String key, String... fields); */
        public static final RedisOprationEnum.Hashes<RedisParams.Params_370.Builder, List<String>> hmget = new RedisOprationEnum.Hashes<>(RedisParams.Params_370.Builder.class, List.class, "hmget");
        /** public Long hincrBy(String key, String field, long value); */
        public static final RedisOprationEnum.Hashes<RedisParams.Params_330.Builder, Long> hincrBy = new RedisOprationEnum.Hashes<>(RedisParams.Params_330.Builder.class, Long.class, "hincrBy");
        /** public Double hincrByFloat(String key, String field, double value); */
        public static final RedisOprationEnum.Hashes<RedisParams.Params_320.Builder, Double> hincrByFloat = new RedisOprationEnum.Hashes<>(RedisParams.Params_320.Builder.class, Double.class, "hincrByFloat");
        /** public Boolean hexists(String key, String field); */
        public static final RedisOprationEnum.Hashes<RedisParams.Params_310.Builder, Boolean> hexists = new RedisOprationEnum.Hashes<>(RedisParams.Params_310.Builder.class, Boolean.class, "hexists");
        /** public Long hdel(String key, String... fields); */
        public static final RedisOprationEnum.Hashes<RedisParams.Params_370.Builder, Long> hdel = new RedisOprationEnum.Hashes<>(RedisParams.Params_370.Builder.class, Long.class, "hdel");
        /** public Long hlen(String key); */
        public static final RedisOprationEnum.Hashes<RedisParams.Params_110.Builder, Long> hlen = new RedisOprationEnum.Hashes<>(RedisParams.Params_110.Builder.class, Long.class, "hlen");
        /** public Set<String> hkeys(String key); */
        public static final RedisOprationEnum.Hashes<RedisParams.Params_110.Builder, Set<String>> hkeys = new RedisOprationEnum.Hashes<>(RedisParams.Params_110.Builder.class, Set.class, "hkeys");
        /** public List<String> hvals(String key); */
        public static final RedisOprationEnum.Hashes<RedisParams.Params_110.Builder, List<String>> hvals = new RedisOprationEnum.Hashes<>(RedisParams.Params_110.Builder.class, List.class, "hvals");
        /** public Map<String, String> hgetAll(String key); */
        public static final RedisOprationEnum.Hashes<RedisParams.Params_110.Builder, Map<String, String>> hgetAll = new RedisOprationEnum.Hashes<>(RedisParams.Params_110.Builder.class, Map.class, "hgetAll");

        protected Hashes(Class<I> builderClzz, Class<?> resultClzz, String nickName) {
            super(builderClzz, resultClzz, nickName);
        }
    }
}
