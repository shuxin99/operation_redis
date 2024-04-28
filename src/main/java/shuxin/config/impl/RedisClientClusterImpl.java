package shuxin.config.impl;


import redis.clients.jedis.*;
import redis.clients.jedis.args.ListDirection;
import redis.clients.jedis.commands.ProtocolCommand;
import redis.clients.jedis.params.*;
import redis.clients.jedis.resps.KeyedListElement;
import redis.clients.jedis.resps.KeyedZSetElement;
import redis.clients.jedis.resps.LCSMatchResult;
import redis.clients.jedis.util.KeyMergeUtil;
import redis.clients.jedis.util.Pool;
import shuxin.config.RedisClient;
import shuxin.config.RedisType;
import shuxin.config.pipeline.BasePipelineTaskWithResult;
import shuxin.config.pipeline.BasePipelineTaskWithResultList;
import shuxin.config.pipeline.BasePipelineTaskWithResultMap;
import shuxin.config.pipeline.BasePipelineTaskWithoutResult;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RedisClientClusterImpl implements RedisClient {

    private final JedisClusterExtend cluster;

    private final RedisType type;

    @Override
    public RedisType getType() {
        return this.type;
    }

    public RedisClientClusterImpl(JedisClusterExtend cluster, RedisType type) {
        this.cluster = cluster;
        this.type = type;
    }

    public <T> List<T> pipeline(String sampleKey, BasePipelineTaskWithResultList<T> p) {
        return this.cluster.pipeline(sampleKey, p);
    }

    public <T> Map<String, T> pipeline(String sampleKey, BasePipelineTaskWithResultMap<T> p) {
        return this.cluster.pipeline(sampleKey, p);
    }

    public <T> T pipeline(String sampleKey, BasePipelineTaskWithResult<T> p) {
        return this.cluster.pipeline(sampleKey, p);
    }

    public void pipeline(String sampleKey, BasePipelineTaskWithoutResult p) {
        this.cluster.pipeline(sampleKey, p);
    }

    public <T> List<T> pipeline(BasePipelineTaskWithResultList<T> p) {
        throw new RuntimeException("Cluster must use pipeline(sampleKey, PipelineTask*) method");
    }

    public <T> Map<String, T> pipeline(BasePipelineTaskWithResultMap<T> p) {
        throw new RuntimeException("Cluster must use pipeline(sampleKey, PipelineTask*) method");
    }

    public <T> T pipeline(BasePipelineTaskWithResult<T> p) {
        throw new RuntimeException("Cluster must use pipeline(sampleKey, PipelineTask*) method");
    }

    public void pipeline(BasePipelineTaskWithoutResult p) {
        throw new RuntimeException("Cluster must use pipeline(sampleKey, PipelineTask*) method");
    }


    public Boolean copy(final String srcKey, final String dstKey, final boolean replace) {
        return this.cluster.copy(srcKey, dstKey, replace);
    }

    public String set(final String key, final String value) {
        return this.cluster.set(key, value);
    }

    public String set(final String key, final String value, final SetParams params) {
        return this.cluster.set(key, value, params);
    }

    public String get(final String key) {
        return this.cluster.get(key);
    }


    public String getDel(final String key) {
        return this.cluster.getDel(key);
    }

    public String getEx(final String key, final GetExParams params) {
        return this.cluster.getEx(key, params);
    }


    public Boolean exists(final String key) {
        return this.cluster.exists(key);
    }


    public Long exists(final String... keys) {
        return this.cluster.exists(keys);
    }


    public Long persist(final String key) {
        return this.cluster.persist(key);
    }


    public String type(final String key) {
        return this.cluster.type(key);
    }

    public byte[] dump(final String key) {
        return this.cluster.dump(key);
    }

    public String restore(final String key, final long ttl, final byte[] serializedValue) {
        return this.cluster.restore(key, ttl, serializedValue);
    }

    public String restore(final String key, final long ttl, final byte[] serializedValue, final RestoreParams params) {
        return this.restore(key, ttl, serializedValue, params);
    }


    public Long expire(final String key, final long seconds) {
        return this.cluster.expire(key, seconds);
    }

    public Long pexpire(final String key, final long milliseconds) {
        return this.cluster.pexpire(key, milliseconds);
    }


    public Long expireAt(final String key, final long unixTime) {
        return this.expireAt(key, unixTime);
    }


    public Long pexpireAt(final String key, final long millisecondsTimestamp) {
        return this.cluster.pexpireAt(key, millisecondsTimestamp);
    }

    public Long ttl(final String key) {
        return this.cluster.ttl(key);
    }

    public Long pttl(final String key) {
        return this.cluster.pttl(key);
    }


    public Long touch(final String key) {
        return this.cluster.touch(key);
    }


    public Long touch(final String... keys) {
        return this.cluster.touch(keys);
    }


    public Boolean setbit(final String key, final long offset, final boolean value) {
        return this.cluster.setbit(key, offset, value);
    }

    public Boolean setbit(final String key, final long offset, final String value) {
        return this.cluster.setbit(key, offset, value);
    }

    public Boolean getbit(final String key, final long offset) {
        return this.cluster.getbit(key, offset);
    }

    public Long setrange(final String key, final long offset, final String value) {
        return this.cluster.setrange(key, offset, value);
    }


    public String getrange(final String key, final long startOffset, final long endOffset) {
        return this.cluster.getrange(key, startOffset, endOffset);
    }

    public String getSet(final String key, final String value) {
        return this.cluster.getSet(key, value);
    }


    public Long setnx(final String key, final String value) {
        return this.cluster.setnx(key, value);
    }

    @Override
    public long setnx(String key, long second, String value) {
        return 0;
    }

    public String setex(final String key, final long seconds, final String value) {
        return this.cluster.setex(key, seconds, value);
    }


    public String psetex(final String key, final long milliseconds, final String value) {
        return this.cluster.psetex(key, milliseconds, value);
    }

    public Long decrBy(final String key, final long decrement) {
        return this.cluster.decrBy(key, decrement);
    }

    public Long decr(final String key) {
        return this.cluster.decr(key);
    }


    public Long incrBy(final String key, final long increment) {
        return this.cluster.incrBy(key, increment);
    }

    public long incrBy(final String key, final long expireSeconds, final long increment) {
        return expireSeconds < 1L ? this.cluster.incrBy(key, increment) : (Long) this.pipeline(key, new BasePipelineTaskWithResult<Long>() {
            public Response<Long> exec(Pipeline p) {
                Response<Long> incr = p.incrBy(key, increment);
                p.expire(key, expireSeconds);
                return incr;
            }
        });
    }

    public Double incrByFloat(final String key, final double increment) {
        return this.cluster.incrByFloat(key, increment);
    }


    public Long incr(final String key) {
        return this.cluster.incr(key);
    }

    public long incr(final String key, final long expireSeconds) {
        return expireSeconds < 1L ? this.cluster.incr(key) : (Long) this.pipeline(key, new BasePipelineTaskWithResult<Long>() {
            public Response<Long> exec(Pipeline p) {
                Response<Long> incr = p.incr(key);
                p.expire(key, expireSeconds);
                return incr;
            }
        });
    }

    public Long append(final String key, final String value) {
        return this.cluster.append(key, value);
    }


    public String substr(final String key, final int start, final int end) {
        return this.cluster.substr(key, start, end);
    }


    public Long hset(final String key, final String field, final String value) {
        return this.cluster.hset(key, field, value);
    }

    public Long hset(final String key, final Map<String, String> hash) {
        return this.cluster.hset(key, hash);
    }

    @Override
    public long hset(String key, long expireSeconds, String field, String value) {
        return 0;
    }

    @Override
    public long hset(String key, long expireSeconds, Map<String, String> hash) {
        return 0;
    }


    public String hget(final String key, final String field) {
        return this.cluster.hget(key, field);
    }

    public Long hsetnx(final String key, final String field, final String value) {
        return this.cluster.hsetnx(key, field, value);
    }


    public String hmset(final String key, final Map<String, String> hash) {
        return this.cluster.hmset(key, hash);
    }

    @Override
    public long hsetnx(String key, long expireSeconds, String field, String value) {
        return 0;
    }

    @Override
    public String hmset(String key, long expireSeconds, Map<String, String> hash) {
        return expireSeconds < 1L ? this.cluster.hmset(key, hash) : (String) this.pipeline(key, new BasePipelineTaskWithResult<String>() {
            public Response<String> exec(Pipeline p) {
                Response<String> s = p.hmset(key, hash);
                p.expire(key, expireSeconds);
                return s;
            }
        });
    }

    public List<String> hmget(final String key, final String... fields) {
        return this.cluster.hmget(key, fields);
    }

    public Long hincrBy(final String key, final String field, final long value) {
        return this.cluster.hincrBy(key, field, value);
    }


    public Double hincrByFloat(final String key, final String field, final double value) {
        return this.cluster.hincrByFloat(key, field, value);
    }

    @Override
    public double hincrByFloat(String key, long expireSeconds, String field, double value) {
        return 0;
    }


    public Boolean hexists(final String key, final String field) {
        return this.cluster.hexists(key, field);
    }


    public Long hdel(final String key, final String... field) {
        return this.cluster.hdel(key, field);
    }


    public Long hlen(final String key) {
        return this.cluster.hlen(key);
    }


    public Set<String> hkeys(final String key) {
        return this.cluster.hkeys(key);
    }


    public List<String> hvals(final String key) {
        return this.cluster.hvals(key);
    }


    public Map<String, String> hgetAll(final String key) {
        return this.cluster.hgetAll(key);
    }


    public String hrandfield(final String key) {
        return this.cluster.hrandfield(key);
    }

    public List<String> hrandfield(final String key, final long count) {
        return this.cluster.hrandfield(key, count);
    }


    public Map<String, String> hrandfieldWithValues(final String key, final long count) {
        return this.cluster.hrandfieldWithValues(key, count);
    }


    public Long rpush(final String key, final String... string) {
        return this.cluster.rpush(key, string);
    }


    public Long lpush(final String key, final String... string) {
        return this.cluster.lpush(key, string);
    }

    @Override
    public long rpush(String key, long expireSeconds, String... strings) {
        return 0;
    }

    @Override
    public long lpush(String key, long expireSeconds, String... strings) {
        return 0;
    }


    public Long llen(final String key) {
        return this.cluster.llen(key);
    }


    public List<String> lrange(final String key, final long start, final long stop) {
        return this.cluster.lrange(key, start, stop);
    }


    public String ltrim(final String key, final long start, final long stop) {
        return this.cluster.ltrim(key, start, stop);
    }


    public String lindex(final String key, final long index) {
        return this.cluster.lindex(key, index);
    }


    public String lset(final String key, final long index, final String value) {
        return this.cluster.lset(key, index, value);
    }


    public Long lrem(final String key, final long count, final String value) {
        return this.cluster.lrem(key, count, value);
    }


    public String lpop(final String key) {
        return this.cluster.lpop(key);
    }


    public List<String> lpop(final String key, final int count) {
        return this.cluster.lpop(key, count);
    }


    public Long lpos(final String key, final String element) {
        return this.cluster.lpos(key, element);
    }


    public Long lpos(final String key, final String element, final LPosParams params) {
        return this.cluster.lpos(key, element, params);
    }


    public List<Long> lpos(final String key, final String element, final LPosParams params, final long count) {
        return this.cluster.lpos(key, element, params, count);
    }


    public String rpop(final String key) {
        return this.cluster.rpop(key);
    }

    public List<String> rpop(final String key, final int count) {
        return this.cluster.rpop(key, count);
    }


    public Long sadd(final String key, final String... member) {
        return this.cluster.sadd(key, member);
    }

    @Override
    public boolean scriptExists(String sha1) {
        return false;
    }

    @Override
    public long sadd(String key, long expireSeconds, String... members) {
        return 0;
    }

    public Set<String> smembers(final String key) {
        return this.cluster.smembers(key);
    }

    public Long srem(final String key, final String... member) {
        return this.cluster.srem(key, member);
    }

    public String spop(final String key) {
        return this.cluster.spop(key);
    }

    public Set<String> spop(final String key, final long count) {
        return this.cluster.spop(key, count);
    }

    public Long scard(final String key) {
        return this.cluster.scard(key);
    }

    public Boolean sismember(final String key, final String member) {
        return this.cluster.sismember(key, member);
    }

    public List<Boolean> smismember(final String key, final String... members) {
        return this.cluster.smismember(key, members);
    }

    public String srandmember(final String key) {
        return this.cluster.srandmember(key);
    }

    public List<String> srandmember(final String key, final int count) {
        return this.cluster.srandmember(key, count);
    }

    @Override
    public double incrByFloat(String key, long seconds, double increment) {
        return 0;
    }

    @Override
    public long decr(String key, long seconds) {
        return 0;
    }

    @Override
    public long decrBy(String key, long seconds, long decrement) {
        return 0;
    }

    @Override
    public long append(String key, long seconds, String value) {
        return 0;
    }

    public Long strlen(final String key) {
        return this.cluster.strlen(key);
    }

    public LCSMatchResult strAlgoLCSKeys(final String keyA, final String keyB, final StrAlgoLCSParams params) {
        return this.cluster.strAlgoLCSKeys(keyA, keyB, params);
    }

    public LCSMatchResult strAlgoLCSStrings(final String strA, final String strB, final StrAlgoLCSParams params) {
        return this.cluster.strAlgoLCSStrings(strA, strB, params);
    }

    public Long zadd(final String key, final double score, final String member) {
        return this.cluster.zadd(key, score, member);
    }

    public Long zadd(final String key, final double score, final String member, final ZAddParams params) {
        return this.cluster.zadd(key, score, member, params);
    }

    public Long zadd(final String key, final Map<String, Double> scoreMembers) {
        return this.cluster.zadd(key, scoreMembers);
    }

    public Long zadd(final String key, final Map<String, Double> scoreMembers, final ZAddParams params) {
        return this.cluster.zadd(key, scoreMembers, params);
    }

    public Double zaddIncr(final String key, final double score, final String member, final ZAddParams params) {
        return this.cluster.zaddIncr(key, score, member, params);
    }

    public Set<String> zdiff(final String... keys) {
        return this.cluster.zdiff(keys);
    }

    public Set<Tuple> zdiffWithScores(final String... keys) {
        return this.cluster.zdiffWithScores(keys);
    }

    public Long zdiffStore(final String dstkey, final String... keys) {
        return this.cluster.zdiffStore(dstkey, keys);
    }

    public Set<String> zrange(final String key, final long start, final long stop) {
        return this.cluster.zrange(key, start, stop);
    }

    @Override
    public long zadd(String key, long expireSeconds, double score, String member) {
        return 0;
    }

    @Override
    public long zadd(String key, long expireSeconds, double score, String member, ZAddParams params) {
        return 0;
    }

    @Override
    public long zadd(String key, long expireSeconds, Map<String, Double> scoreMembers) {
        return 0;
    }

    @Override
    public long zadd(String key, long expireSeconds, Map<String, Double> scoreMembers, ZAddParams params) {
        return 0;
    }

    public Long zrem(final String key, final String... members) {
        return this.cluster.zrem(key, members);
    }

    public Double zincrby(final String key, final double increment, final String member) {
        return this.cluster.zincrby(key, increment, member);
    }

    public Double zincrby(final String key, final double increment, final String member, final ZIncrByParams params) {
        return this.cluster.zincrby(key, increment, member, params);
    }

    public Long zrank(final String key, final String member) {
        return this.cluster.zrank(key, member);
    }

    public Long zrevrank(final String key, final String member) {
        return this.cluster.zrevrank(key, member);
    }

    public Set<String> zrevrange(final String key, final long start, final long stop) {
        return this.cluster.zrevrange(key, start, stop);
    }

    public Set<Tuple> zrangeWithScores(final String key, final long start, final long stop) {
        return this.cluster.zrangeWithScores(key, start, stop);
    }

    public Set<Tuple> zrevrangeWithScores(final String key, final long start, final long stop) {
        return this.cluster.zrevrangeWithScores(key, start, stop);
    }

    public String zrandmember(final String key) {
        return this.cluster.zrandmember(key);
    }

    public Set<String> zrandmember(final String key, final long count) {
        return this.cluster.zrandmember(key, count);
    }

    public Set<Tuple> zrandmemberWithScores(final String key, final long count) {
        return this.cluster.zrandmemberWithScores(key, count);
    }

    public Long zcard(final String key) {
        return this.cluster.zcard(key);
    }

    public Double zscore(final String key, final String member) {
        return this.cluster.zscore(key, member);
    }

    public List<Double> zmscore(final String key, final String... members) {
        return this.cluster.zmscore(key, members);
    }

    public Tuple zpopmax(final String key) {
        return this.cluster.zpopmax(key);
    }

    public Set<Tuple> zpopmax(final String key, final int count) {
        return this.cluster.zpopmax(key, count);
    }

    public Tuple zpopmin(final String key) {
        return this.cluster.zpopmin(key);
    }

    public Set<Tuple> zpopmin(final String key, final int count) {
        return this.cluster.zpopmin(key, count);
    }

    public List<String> sort(final String key) {
        return this.cluster.sort(key);
    }

    public List<String> sort(final String key, final SortingParams sortingParameters) {
        return this.cluster.sort(key, sortingParameters);
    }

    public Long zcount(final String key, final double min, final double max) {
        return this.cluster.zcount(key, min, max);
    }

    public Long zcount(final String key, final String min, final String max) {
        return this.cluster.zcount(key, min, max);
    }

    public Set<String> zrangeByScore(final String key, final double min, final double max) {
        return this.cluster.zrangeByScore(key, min, max);
    }

    public Set<String> zrangeByScore(final String key, final String min, final String max) {
        return this.cluster.zrangeByScore(key, min, max);
    }

    public Set<String> zrevrangeByScore(final String key, final double max, final double min) {
        return this.cluster.zrevrangeByScore(key, max, min);
    }

    public Set<String> zrangeByScore(final String key, final double min, final double max, final int offset, final int count) {
        return this.cluster.zrangeByScore(key, min, max, offset, count);
    }

    public Set<String> zrevrangeByScore(final String key, final String max, final String min) {
        return this.cluster.zrevrangeByScore(key, max, min);
    }

    public Set<String> zrangeByScore(final String key, final String min, final String max, final int offset, final int count) {
        return this.cluster.zrangeByScore(key, min, max, offset, count);
    }

    public Set<String> zrevrangeByScore(final String key, final double max, final double min, final int offset, final int count) {
        return this.cluster.zrevrangeByScore(key, max, min, offset, count);
    }

    public Set<Tuple> zrangeByScoreWithScores(final String key, final double min, final double max) {
        return this.cluster.zrangeByScoreWithScores(key, min, max);
    }

    public Set<Tuple> zrevrangeByScoreWithScores(final String key, final double max, final double min) {
        return this.cluster.zrevrangeByScoreWithScores(key, max, min);
    }

    public Set<Tuple> zrangeByScoreWithScores(final String key, final double min, final double max, final int offset, final int count) {
        return this.cluster.zrangeByScoreWithScores(key, min, max, offset, count);
    }

    public Set<String> zrevrangeByScore(final String key, final String max, final String min, final int offset, final int count) {
        return this.cluster.zrevrangeByScore(key, max, min, offset, count);
    }

    public Set<Tuple> zrangeByScoreWithScores(final String key, final String min, final String max) {
        return this.cluster.zrangeByScoreWithScores(key, min, max);
    }

    public Set<Tuple> zrevrangeByScoreWithScores(final String key, final String max, final String min) {
        return this.cluster.zrevrangeByScoreWithScores(key, max, min);
    }

    public Set<Tuple> zrangeByScoreWithScores(final String key, final String min, final String max, final int offset, final int count) {
        return this.cluster.zrangeByScoreWithScores(key, min, max, offset, count);
    }

    public Set<Tuple> zrevrangeByScoreWithScores(final String key, final double max, final double min, final int offset, final int count) {
        return this.cluster.zrevrangeByScoreWithScores(key, max, min, offset, count);
    }

    public Set<Tuple> zrevrangeByScoreWithScores(final String key, final String max, final String min, final int offset, final int count) {
        return this.cluster.zrevrangeByScoreWithScores(key, max, min, offset, count);
    }

    public Long zremrangeByRank(final String key, final long start, final long stop) {
        return this.cluster.zremrangeByRank(key, start, stop);
    }

    public Long zremrangeByScore(final String key, final double min, final double max) {
        return this.cluster.zremrangeByScore(key, min, max);
    }

    public Long zremrangeByScore(final String key, final String min, final String max) {
        return this.cluster.zremrangeByScore(key, min, max);
    }

    public Long zlexcount(final String key, final String min, final String max) {
        return this.cluster.zlexcount(key, min, max);
    }

    public Set<String> zrangeByLex(final String key, final String min, final String max) {
        return this.cluster.zrangeByLex(key, min, max);
    }

    public Set<String> zrangeByLex(final String key, final String min, final String max, final int offset, final int count) {
        return this.cluster.zrangeByLex(key, min, max, offset, count);
    }

    public Set<String> zrevrangeByLex(final String key, final String max, final String min) {
        return this.cluster.zrevrangeByLex(key, max, min);
    }

    public Set<String> zrevrangeByLex(final String key, final String max, final String min, final int offset, final int count) {
        return this.cluster.zrevrangeByLex(key, max, min, offset, count);
    }

    public Long zremrangeByLex(final String key, final String min, final String max) {
        return this.cluster.zremrangeByLex(key, min, max);
    }

    public Long linsert(final String key, final ListPosition where, final String pivot, final String value) {
        return this.cluster.linsert(key, where, pivot, value);
    }

    public Long lpushx(final String key, final String... string) {
        return this.cluster.lpushx(key, string);
    }

    public Long rpushx(final String key, final String... string) {
        return this.cluster.rpushx(key, string);
    }

    public Long del(final String key) {
        return this.cluster.del(key);
    }

    public Long unlink(final String key) {
        return this.cluster.unlink(key);
    }

    public Long unlink(final String... keys) {
        return this.cluster.unlink(keys);
    }

    public String echo(final String string) {
        return this.cluster.echo(string);
    }

    public Long bitcount(final String key) {
        return this.cluster.bitcount(key);
    }

    public Long bitcount(final String key, final long start, final long end) {
        return this.cluster.bitcount(key, start, end);
    }

    public Set<String> keys(final String pattern) {
        return this.cluster.keys(pattern);
    }

    public ScanResult<String> scan(String cursor, ScanParams params) {
        return this.scan(cursor, params, (String) null);
    }

    public ScanResult<String> scan(final String cursor, final ScanParams params, final String type) {
        return this.cluster.scan(cursor, params, type);
    }

    public ScanResult<Map.Entry<String, String>> hscan(final String key, final String cursor) {
        return this.cluster.hscan(key, cursor);
    }


    public ScanResult<Map.Entry<String, String>> hscan(String key, String cursor, ScanParams params) {
        return this.cluster.hscan(key, cursor, params);
    }

    public ScanResult<String> sscan(final String key, final String cursor) {
        return this.cluster.sscan(key, cursor);
    }

    public ScanResult<String> sscan(final String key, final String cursor, ScanParams params) {
        return this.cluster.sscan(key, cursor, params);
    }

    public ScanResult<Tuple> zscan(final String key, final String cursor) {
        return this.cluster.zscan(key, cursor);
    }

    public ScanResult<Tuple> zscan(final String key, final String cursor, ScanParams params) {
        return this.cluster.zscan(key, cursor, params);
    }

    public Long pfadd(final String key, final String... elements) {
        return this.cluster.pfadd(key, elements);
    }

    public long pfcount(final String key) {
        return this.cluster.pfcount(key);
    }

    public Long del(final String... keys) {
        return this.cluster.del(keys);
    }

    public String lmove(final String srcKey, final String dstKey, final ListDirection from, final ListDirection to) {
        return this.cluster.lmove(srcKey, dstKey, from, to);
    }

    public String blmove(final String srcKey, final String dstKey, final ListDirection from, final ListDirection to, final double timeout) {
        return this.cluster.blmove(srcKey, dstKey, from, to, timeout);
    }

    @Override
    public long lpushx(String key, long expireSeconds, String... strings) {
        return 0;
    }

    @Override
    public long rpushx(String key, long expireSeconds, String... strings) {
        return 0;
    }

    public List<String> blpop(final int timeout, final String... keys) {
        return this.cluster.blpop(timeout, keys);
    }

    public KeyedListElement blpop(final double timeout, final String... keys) {
        return this.cluster.blpop(timeout, keys);
    }

    public List<String> brpop(final int timeout, final String... keys) {
        return this.cluster.brpop(timeout, keys);
    }

    public KeyedListElement brpop(final double timeout, final String... keys) {
        return this.cluster.brpop(timeout, keys);
    }

    public KeyedZSetElement bzpopmax(final double timeout, final String... keys) {
        return this.cluster.bzpopmax(timeout, keys);
    }

    public KeyedZSetElement bzpopmin(final double timeout, final String... keys) {
        return this.cluster.bzpopmin(timeout, keys);
    }

    public List<String> blpop(final int timeout, final String key) {
        return this.cluster.blpop(timeout, key);
    }

    public KeyedListElement blpop(final double timeout, final String key) {
        return this.cluster.blpop(timeout, key);
    }

    public List<String> brpop(final int timeout, final String key) {
        return this.cluster.brpop(timeout, key);
    }

    public KeyedListElement brpop(final double timeout, final String key) {
        return this.cluster.brpop(timeout, key);
    }

    public List<String> mget(final String... keys) {
        return this.cluster.mget(keys);
    }

    public String mset(final String... keysvalues) {
        return this.cluster.mset(keysvalues);
    }

    public void mset(final long expireSeconds, final String... keysvalues) {
        if (expireSeconds < 1L) {
            this.cluster.mset(keysvalues);
        } else {
            this.pipeline(keysvalues[0], new BasePipelineTaskWithoutResult() {
                public void exec(Pipeline p) {
                    p.mset(keysvalues);
                    String[] var2 = keysvalues;
                    int var3 = var2.length;
                    for (int var4 = 0; var4 < var3; ++var4) {
                        String k = var2[var4];
                        p.expire(k, expireSeconds);
                    }

                }
            });
        }
    }

    public Long msetnx(final String... keysvalues) {
        return this.cluster.msetnx(keysvalues);
    }

    public long msetnx(final long expireSeconds, final String... keysvalues) {
        return expireSeconds < 1L ? this.cluster.msetnx(keysvalues) : (Long) this.pipeline(keysvalues[0], new BasePipelineTaskWithResult<Long>() {
            public Response<Long> exec(Pipeline p) {
                Response<Long> msetnx = p.msetnx(keysvalues);
                String[] var3 = keysvalues;
                int var4 = var3.length;
                for (int var5 = 0; var5 < var4; ++var5) {
                    String k = var3[var5];
                    p.expire(k, expireSeconds);
                }
                return msetnx;
            }
        });
    }

    public String rename(final String oldkey, final String newkey) {
        return this.cluster.rename(oldkey, newkey);
    }

    public Long renamenx(final String oldkey, final String newkey) {
        return this.cluster.renamenx(oldkey, newkey);
    }

    public String rpoplpush(final String srckey, final String dstkey) {
        return this.cluster.rpoplpush(srckey, dstkey);
    }

    public Set<String> sdiff(final String... keys) {
        return this.cluster.sdiff(keys);
    }

    public Long sdiffstore(final String dstkey, final String... keys) {
        return this.cluster.sdiffstore(dstkey, keys);
    }

    public Set<String> sinter(final String... keys) {
        return this.cluster.sinter(keys);
    }

    public Long sinterstore(final String dstkey, final String... keys) {
        return this.cluster.sinterstore(dstkey, keys);
    }

    public Long smove(final String srckey, final String dstkey, final String member) {
        return this.cluster.smove(srckey, dstkey, member);
    }

    public Long sort(final String key, final SortingParams sortingParameters, final String dstkey) {
        return this.cluster.sort(key, sortingParameters, dstkey);
    }

    public Long sort(final String key, final String dstkey) {
        return this.cluster.sort(key, dstkey);
    }

    public Set<String> sunion(final String... keys) {
        return this.cluster.sunion(keys);
    }

    public Long sunionstore(final String dstkey, final String... keys) {
        return this.cluster.sunionstore(dstkey, keys);
    }

    public Set<String> zinter(final ZParams params, final String... keys) {
        return this.cluster.zinter(params, keys);
    }

    public Set<Tuple> zinterWithScores(final ZParams params, final String... keys) {
        return this.cluster.zinterWithScores(params, keys);
    }

    public Long zinterstore(final String dstkey, final String... sets) {
        String[] wholeKeys = KeyMergeUtil.merge(dstkey, sets);
        return this.cluster.zinterstore(dstkey, sets);
    }

    public Long zinterstore(final String dstkey, final ZParams params, final String... sets) {
        return this.cluster.zinterstore(dstkey, params, sets);
    }

    public Set<String> zunion(final ZParams params, final String... keys) {
        return this.cluster.zunion(params, keys);
    }

    public Set<Tuple> zunionWithScores(final ZParams params, final String... keys) {
        return this.cluster.zunionWithScores(params, keys);
    }

    public Long zunionstore(final String dstkey, final String... sets) {
        return this.cluster.zunionstore(dstkey, sets);
    }

    public Long zunionstore(final String dstkey, final ZParams params, final String... sets) {
        return this.cluster.zunionstore(dstkey, params, sets);
    }

    public String brpoplpush(final String source, final String destination, final int timeout) {
        return this.cluster.brpoplpush(source, destination, timeout);
    }

    public Long publish(final String channel, final String message) {
        return this.cluster.publish(channel, message);
    }

    public void subscribe(final JedisPubSub jedisPubSub, final String... channels) {
        this.cluster.subscribe(jedisPubSub, channels);
    }

    public void psubscribe(final JedisPubSub jedisPubSub, final String... patterns) {
        this.cluster.psubscribe(jedisPubSub, patterns);
    }

    public Long bitop(final BitOP op, final String destKey, final String... srcKeys) {
        return this.cluster.bitop(op, destKey, srcKeys);
    }

    public String pfmerge(final String destkey, final String... sourcekeys) {
        return this.cluster.pfmerge(destkey, sourcekeys);
    }

    public long pfcount(final String... keys) {
        return this.cluster.pfcount(keys);
    }

    public Object eval(final String script, final int keyCount, final String... params) {
        return this.cluster.eval(script, keyCount, params);
    }

    public Object eval(final String script, String sampleKey) {
        return this.cluster.eval(script, sampleKey);
    }

    public Object eval(final String script, final List<String> keys, final List<String> args) {
        return this.cluster.eval(script, keys, args);
    }

    @Override
    public <T extends Pool<Jedis>> Map<String, Pool<Jedis>> getNodes() {
        throw new RuntimeException("Cluster must use getClusterNodes()");
    }

    @Override
    public Object evalsha(String sha1) {
        throw new RuntimeException("Cluster no such method");
    }

    public Object evalsha(final String sha1, final int keyCount, final String... params) {
        return this.cluster.evalsha(sha1, keyCount, params);
    }

    public Object evalsha(final String sha1, final List<String> keys, final List<String> args) {
        return this.cluster.evalsha(sha1, keys, args);
    }

    public Object evalsha(final String sha1, String sampleKey) {
        return this.cluster.evalsha(sha1, sampleKey);
    }

    public Boolean scriptExists(final String sha1, String sampleKey) {
        return this.cluster.scriptExists(sha1, sampleKey);
    }

    public List<Boolean> scriptExists(String sampleKey, final String... sha1) {
        return this.cluster.scriptExists(sampleKey, sha1);
    }

    public String scriptLoad(final String script, String sampleKey) {
        return this.cluster.scriptLoad(script, sampleKey);
    }

    public String scriptFlush(String sampleKey) {
        return this.cluster.scriptFlush(sampleKey);
    }

    public String scriptKill(String sampleKey) {
        return this.cluster.scriptKill(sampleKey);
    }

    public Long geoadd(final String key, final double longitude, final double latitude, final String member) {
        return this.cluster.geoadd(key, longitude, latitude, member);
    }

    public Long geoadd(final String key, final Map<String, GeoCoordinate> memberCoordinateMap) {
        return this.cluster.geoadd(key, memberCoordinateMap);
    }

    public Long geoadd(final String key, final GeoAddParams params, final Map<String, GeoCoordinate> memberCoordinateMap) {
        return this.cluster.geoadd(key, params, memberCoordinateMap);
    }

    public Double geodist(final String key, final String member1, final String member2) {
        return this.cluster.geodist(key, member1, member2);
    }

    public Double geodist(final String key, final String member1, final String member2, final GeoUnit unit) {
        return this.cluster.geodist(key, member1, member2, unit);
    }

    public List<String> geohash(final String key, final String... members) {
        return this.cluster.geohash(key, members);
    }

    public List<GeoCoordinate> geopos(final String key, final String... members) {
        return this.cluster.geopos(key, members);
    }

    public List<GeoRadiusResponse> georadius(final String key, final double longitude, final double latitude, final double radius, final GeoUnit unit) {
        return this.cluster.georadius(key, longitude, latitude, radius, unit);
    }

    public List<GeoRadiusResponse> georadiusReadonly(final String key, final double longitude, final double latitude, final double radius, final GeoUnit unit) {
        return this.cluster.georadiusReadonly(key, longitude, latitude, radius, unit);
    }

    public List<GeoRadiusResponse> georadius(final String key, final double longitude, final double latitude, final double radius, final GeoUnit unit, final GeoRadiusParam param) {
        return this.cluster.georadius(key, longitude, latitude, radius, unit, param);
    }

    public Long georadiusStore(final String key, final double longitude, final double latitude, final double radius, final GeoUnit unit, final GeoRadiusParam param, final GeoRadiusStoreParam storeParam) {
        return this.cluster.georadiusStore(key, longitude, latitude, radius, unit, param, storeParam);
    }

    public List<GeoRadiusResponse> georadiusReadonly(final String key, final double longitude, final double latitude, final double radius, final GeoUnit unit, final GeoRadiusParam param) {
        return this.cluster.georadiusReadonly(key, longitude, latitude, radius, unit, param);
    }

    public List<GeoRadiusResponse> georadiusByMember(final String key, final String member, final double radius, final GeoUnit unit) {
        return this.cluster.georadiusByMember(key, member, radius, unit);
    }

    public List<GeoRadiusResponse> georadiusByMemberReadonly(final String key, final String member, final double radius, final GeoUnit unit) {
        return this.cluster.georadiusByMemberReadonly(key, member, radius, unit);
    }

    public List<GeoRadiusResponse> georadiusByMember(final String key, final String member, final double radius, final GeoUnit unit, final GeoRadiusParam param) {
        return this.cluster.georadiusByMember(key, member, radius, unit, param);
    }

    public Long georadiusByMemberStore(final String key, final String member, final double radius, final GeoUnit unit, final GeoRadiusParam param, final GeoRadiusStoreParam storeParam) {
        return this.cluster.georadiusByMemberStore(key, member, radius, unit, param, storeParam);
    }

    public List<GeoRadiusResponse> georadiusByMemberReadonly(final String key, final String member, final double radius, final GeoUnit unit, final GeoRadiusParam param) {
        return this.cluster.georadiusByMemberReadonly(key, member, radius, unit, param);
    }

    public List<Long> bitfield(final String key, final String... arguments) {
        return this.cluster.bitfield(key, arguments);
    }

    public List<Long> bitfieldReadonly(final String key, final String... arguments) {
        return this.cluster.bitfieldReadonly(key, arguments);
    }

    public Long hstrlen(final String key, final String field) {
        return this.cluster.hstrlen(key, field);
    }

    public Long memoryUsage(final String key) {
        return this.cluster.memoryUsage(key);
    }

    public Long memoryUsage(final String key, final int samples) {
        return this.cluster.memoryUsage(key, samples);
    }

    public StreamEntryID xadd(final String key, final StreamEntryID id, final Map<String, String> hash) {
        return this.cluster.xadd(key, id, hash);
    }

    public StreamEntryID xadd(final String key, final StreamEntryID id, final Map<String, String> hash, final long maxLen, final boolean approximateLength) {
        return this.cluster.xadd(key, id, hash, maxLen, approximateLength);
    }

    public StreamEntryID xadd(final String key, final Map<String, String> hash, final XAddParams params) {
        return this.cluster.xadd(key, hash, params);
    }

    public Long xlen(final String key) {
        return this.cluster.xlen(key);
    }

    public List<StreamEntry> xrange(final String key, final StreamEntryID start, final StreamEntryID end) {
        return this.cluster.xrange(key, start, end);
    }

    public List<StreamEntry> xrange(final String key, final StreamEntryID start, final StreamEntryID end, final int count) {
        return this.cluster.xrange(key, start, end, count);
    }

    public List<StreamEntry> xrevrange(final String key, final StreamEntryID end, final StreamEntryID start) {
        return this.cluster.xrevrange(key, end, start);
    }

    public List<StreamEntry> xrevrange(final String key, final StreamEntryID end, final StreamEntryID start, final int count) {
        return this.cluster.xrevrange(key, end, start, count);
    }

    public List<Map.Entry<String, List<StreamEntry>>> xread(final int count, final long block, final Map.Entry<String, StreamEntryID>... streams) {
        return this.cluster.xread(count, block, streams);
    }

    public List<Map.Entry<String, List<StreamEntry>>> xread(final XReadParams xReadParams, final Map<String, StreamEntryID> streams) {
        return this.cluster.xread(xReadParams, streams);
    }

    public Long xack(final String key, final String group, final StreamEntryID... ids) {
        return this.cluster.xack(key, group, ids);
    }

    public String xgroupCreate(final String key, final String groupname, final StreamEntryID id, final boolean makeStream) {
        return this.cluster.xgroupCreate(key, groupname, id, makeStream);
    }

    public String xgroupSetID(final String key, final String groupname, final StreamEntryID id) {
        return this.cluster.xgroupSetID(key, groupname, id);
    }

    public Long xgroupDestroy(final String key, final String groupname) {
        return this.cluster.xgroupDestroy(key, groupname);
    }

    public Long xgroupDelConsumer(final String key, final String groupname, final String consumername) {
        return this.cluster.xgroupDelConsumer(key, groupname, consumername);
    }

    public List<Map.Entry<String, List<StreamEntry>>> xreadGroup(final String groupname, final String consumer, final int count, final long block, final boolean noAck, final Map.Entry<String, StreamEntryID>... streams) {
        return this.cluster.xreadGroup(groupname, consumer, count, block, noAck, streams);
    }

    public List<Map.Entry<String, List<StreamEntry>>> xreadGroup(final String groupname, final String consumer, final XReadGroupParams xReadGroupParams, final Map<String, StreamEntryID> streams) {
        return this.cluster.xreadGroup(groupname, consumer, xReadGroupParams, streams);
    }

    public StreamPendingSummary xpending(final String key, final String groupname) {
        return this.cluster.xpending(key, groupname);
    }

    public List<StreamPendingEntry> xpending(final String key, final String groupname, final StreamEntryID start, final StreamEntryID end, final int count, final String consumername) {
        return this.cluster.xpending(key, groupname, start, end, count, consumername);
    }

    public List<StreamPendingEntry> xpending(final String key, final String groupname, final XPendingParams params) {
        return this.cluster.xpending(key, groupname, params);
    }

    public Long xdel(final String key, final StreamEntryID... ids) {
        return this.cluster.xdel(key, ids);
    }

    public Long xtrim(final String key, final long maxLen, final boolean approximateLength) {
        return this.cluster.xtrim(key, maxLen, approximateLength);
    }

    public Long xtrim(final String key, final XTrimParams params) {
        return this.cluster.xtrim(key, params);
    }

    public List<StreamEntry> xclaim(final String key, final String group, final String consumername, final long minIdleTime, final long newIdleTime, final int retries, final boolean force, final StreamEntryID... ids) {
        return this.cluster.xclaim(key, group, consumername, minIdleTime, newIdleTime, retries, force, ids);
    }

    public List<StreamEntry> xclaim(final String key, final String group, final String consumername, final long minIdleTime, final XClaimParams params, final StreamEntryID... ids) {
        return this.cluster.xclaim(key, group, consumername, minIdleTime, params, ids);
    }

    public List<StreamEntryID> xclaimJustId(final String key, final String group, final String consumername, final long minIdleTime, final XClaimParams params, final StreamEntryID... ids) {
        return this.cluster.xclaimJustId(key, group, consumername, minIdleTime, params, ids);
    }

    public Map.Entry<StreamEntryID, List<StreamEntry>> xautoclaim(final String key, final String group, final String consumerName, final long minIdleTime, final StreamEntryID start, final XAutoClaimParams params) {
        return this.cluster.xautoclaim(key, group, consumerName, minIdleTime, start, params);
    }

    public Map.Entry<StreamEntryID, List<StreamEntryID>> xautoclaimJustId(final String key, final String group, final String consumerName, final long minIdleTime, final StreamEntryID start, final XAutoClaimParams params) {
        return this.cluster.xautoclaimJustId(key, group, consumerName, minIdleTime, start, params);
    }

    public Long waitReplicas(String key, final int replicas, final long timeout) {
        return this.cluster.waitReplicas(key, replicas, timeout);
    }

    public Object sendCommand(String sampleKey, final ProtocolCommand cmd, final String... args) {
        return this.cluster.sendCommand(sampleKey, cmd, args);
    }

    public Object sendBlockingCommand(String sampleKey, final ProtocolCommand cmd, final String... args) {
        return this.cluster.sendBlockingCommand(sampleKey, cmd, args);
    }

    @Override
    public String getWithDefault(String key, String defaultValue) {
        throw new RuntimeException("Cluster no such method");
    }

    @Override
    public <T> T getObjectWithDefault(String key, Class<T> clazz, T defaultValue) {
        throw new RuntimeException("Cluster no such method");
    }

    @Override
    public String hgetWithDefault(String key, String field, String defaultValue) {
        throw new RuntimeException("Cluster no such method");
    }

    @Override
    public <T> T hgetObjectWithDefault(String key, String field, Class<T> clazz, T defaultValue) {
        throw new RuntimeException("Cluster no such method");
    }

    @Override
    public String setObject(String key, Serializable objValue, SetParams params) {
        throw new RuntimeException("Cluster no such method");
    }

    @Override
    public <T> T getObject(String key, Class<T> clazz) {
        throw new RuntimeException("Cluster no such method");
    }

    @Override
    public long setnxObject(String key, long second, Serializable objValue) {
        throw new RuntimeException("Cluster no such method");
    }

    @Override
    public String setexObject(String key, long seconds, Serializable objValue) {
        throw new RuntimeException("Cluster no such method");
    }

    @Override
    public String psetexObject(String key, long milliseconds, Serializable objValue) {
        throw new RuntimeException("Cluster no such method");
    }

    @Override
    public long hsetObject(String key, long expireSeconds, String field, Serializable objValue) {
        throw new RuntimeException("Cluster no such method");
    }

    @Override
    public <T> T hgetObject(String key, String field, Class<T> clazz) {
        throw new RuntimeException("Cluster no such method");
    }

    @Override
    public long hsetnxObject(String key, long expireSeconds, String field, Serializable objValue) {
        throw new RuntimeException("Cluster no such method");
    }

    @Override
    public Map<String, JedisPool> getClusterNodes() {
        return this.cluster.getClusterNodes();
    }

    private static String[] getKeys(Map<String, ?> map) {
        return (String[]) map.keySet().toArray(new String[map.size()]);
    }

}
