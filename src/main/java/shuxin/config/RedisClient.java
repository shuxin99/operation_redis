package shuxin.config;

import redis.clients.jedis.*;
import redis.clients.jedis.args.ListDirection;
import redis.clients.jedis.commands.ProtocolCommand;
import redis.clients.jedis.params.*;
import redis.clients.jedis.resps.KeyedListElement;
import redis.clients.jedis.resps.KeyedZSetElement;
import redis.clients.jedis.resps.LCSMatchResult;
import redis.clients.jedis.util.Pool;
import shuxin.config.pipeline.BasePipelineTaskWithResult;
import shuxin.config.pipeline.BasePipelineTaskWithResultList;
import shuxin.config.pipeline.BasePipelineTaskWithResultMap;
import shuxin.config.pipeline.BasePipelineTaskWithoutResult;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RedisClient {

    RedisType getType();

    public <T> List<T> pipeline(String sampleKey, BasePipelineTaskWithResultList<T> p);


    public <T> Map<String, T> pipeline(String sampleKey, BasePipelineTaskWithResultMap<T> p);


    public <T> T pipeline(String sampleKey, BasePipelineTaskWithResult<T> p);


    public void pipeline(String sampleKey, BasePipelineTaskWithoutResult p);


    public <T> List<T> pipeline(BasePipelineTaskWithResultList<T> p);


    public <T> Map<String, T> pipeline(BasePipelineTaskWithResultMap<T> p);


    public <T> T pipeline(BasePipelineTaskWithResult<T> p);


    public void pipeline(BasePipelineTaskWithoutResult p);


    public Boolean copy(final String srcKey, final String dstKey, final boolean replace);


    public String set(final String key, final String value);


    public String set(final String key, final String value, final SetParams params);


    public String get(final String key);


    public String getDel(final String key);


    public String getEx(final String key, final GetExParams params);


    public Boolean exists(final String key);


    public Long exists(final String... keys);


    public Long persist(final String key);


    public String type(final String key);


    public byte[] dump(final String key);


    public String restore(final String key, final long ttl, final byte[] serializedValue);


    public String restore(final String key, final long ttl, final byte[] serializedValue, final RestoreParams params);


    public Long expire(final String key, final long seconds);


    public Long pexpire(final String key, final long milliseconds);


    public Long expireAt(final String key, final long unixTime);


    public Long pexpireAt(final String key, final long millisecondsTimestamp);


    public Long ttl(final String key);


    public Long pttl(final String key);


    public Long touch(final String key);


    public Long touch(final String... keys);


    public Boolean setbit(final String key, final long offset, final boolean value);


    public Boolean setbit(final String key, final long offset, final String value);


    public Boolean getbit(final String key, final long offset);


    public Long setrange(final String key, final long offset, final String value);


    public String getrange(final String key, final long startOffset, final long endOffset);


    public String getSet(final String key, final String value);


    public Long setnx(final String key, final String value);


    long setnx(String key, long second, String value);

    public String setex(final String key, final long seconds, final String value);


    public String psetex(final String key, final long milliseconds, final String value);


    public Long decrBy(final String key, final long decrement);


    public Long decr(final String key);


    public Long incrBy(final String key, final long increment);


    public long incrBy(final String key, final long expireSeconds, final long increment);


    public Double incrByFloat(final String key, final double increment);


    public Long incr(final String key);


    public long incr(final String key, final long expireSeconds);


    public Long append(final String key, final String value);


    public String substr(final String key, final int start, final int end);


    public Long hset(final String key, final String field, final String value);


    public Long hset(final String key, final Map<String, String> hash);


    long hset(String key, long expireSeconds, String field, String value);

    long hset(String key, long expireSeconds, Map<String, String> hash);

    public String hget(final String key, final String field);


    public Long hsetnx(final String key, final String field, final String value);


    public String hmset(final String key, final Map<String, String> hash);


    long hsetnx(String key, long expireSeconds, String field, String value);

    String hmset(String key, long expireSeconds, Map<String, String> hash);

    public List<String> hmget(final String key, final String... fields);


    public Long hincrBy(final String key, final String field, final long value);


    public Double hincrByFloat(final String key, final String field, final double value);


    double hincrByFloat(String key, long expireSeconds, String field, double value);

    public Boolean hexists(final String key, final String field);


    public Long hdel(final String key, final String... field);


    public Long hlen(final String key);


    public Set<String> hkeys(final String key);


    public List<String> hvals(final String key);


    public Map<String, String> hgetAll(final String key);


    public String hrandfield(final String key);


    public List<String> hrandfield(final String key, final long count);


    public Map<String, String> hrandfieldWithValues(final String key, final long count);


    public Long rpush(final String key, final String... string);


    public Long lpush(final String key, final String... string);


    long rpush(String key, long expireSeconds, String... strings);

    long lpush(String key, long expireSeconds, String... strings);

    public Long llen(final String key);


    public List<String> lrange(final String key, final long start, final long stop);


    public String ltrim(final String key, final long start, final long stop);


    public String lindex(final String key, final long index);


    public String lset(final String key, final long index, final String value);


    public Long lrem(final String key, final long count, final String value);


    public String lpop(final String key);


    public List<String> lpop(final String key, final int count);


    public Long lpos(final String key, final String element);


    public Long lpos(final String key, final String element, final LPosParams params);


    public List<Long> lpos(final String key, final String element, final LPosParams params, final long count);


    public String rpop(final String key);


    public List<String> rpop(final String key, final int count);


    public Long sadd(final String key, final String... member);


    boolean scriptExists(String sha1);

    long sadd(String key, long expireSeconds, String... members);

    public Set<String> smembers(final String key);


    public Long srem(final String key, final String... member);


    public String spop(final String key);


    public Set<String> spop(final String key, final long count);


    public Long scard(final String key);


    public Boolean sismember(final String key, final String member);


    public List<Boolean> smismember(final String key, final String... members);


    public String srandmember(final String key);


    public List<String> srandmember(final String key, final int count);


    double incrByFloat(String key, long seconds, double increment);

    long decr(String key, long seconds);

    long decrBy(String key, long seconds, long decrement);

    long append(String key, long seconds, String value);

    public Long strlen(final String key);


    public LCSMatchResult strAlgoLCSKeys(final String keyA, final String keyB, final StrAlgoLCSParams params);


    public LCSMatchResult strAlgoLCSStrings(final String strA, final String strB, final StrAlgoLCSParams params);


    public Long zadd(final String key, final double score, final String member);


    public Long zadd(final String key, final double score, final String member, final ZAddParams params);


    public Long zadd(final String key, final Map<String, Double> scoreMembers);


    public Long zadd(final String key, final Map<String, Double> scoreMembers, final ZAddParams params);


    public Double zaddIncr(final String key, final double score, final String member, final ZAddParams params);


    public Set<String> zdiff(final String... keys);


    public Set<Tuple> zdiffWithScores(final String... keys);


    public Long zdiffStore(final String dstkey, final String... keys);


    public Set<String> zrange(final String key, final long start, final long stop);


    long zadd(String key, long expireSeconds, double score, String member);

    long zadd(String key, long expireSeconds, double score, String member, ZAddParams params);

    long zadd(String key, long expireSeconds, Map<String, Double> scoreMembers);

    long zadd(String key, long expireSeconds, Map<String, Double> scoreMembers, ZAddParams params);

    public Long zrem(final String key, final String... members);


    public Double zincrby(final String key, final double increment, final String member);


    public Double zincrby(final String key, final double increment, final String member, final ZIncrByParams params);


    public Long zrank(final String key, final String member);


    public Long zrevrank(final String key, final String member);


    public Set<String> zrevrange(final String key, final long start, final long stop);


    public Set<Tuple> zrangeWithScores(final String key, final long start, final long stop);


    public Set<Tuple> zrevrangeWithScores(final String key, final long start, final long stop);


    public String zrandmember(final String key);


    public Set<String> zrandmember(final String key, final long count);


    public Set<Tuple> zrandmemberWithScores(final String key, final long count);


    public Long zcard(final String key);


    public Double zscore(final String key, final String member);


    public List<Double> zmscore(final String key, final String... members);


    public Tuple zpopmax(final String key);


    public Set<Tuple> zpopmax(final String key, final int count);


    public Tuple zpopmin(final String key);


    public Set<Tuple> zpopmin(final String key, final int count);


    public List<String> sort(final String key);


    public List<String> sort(final String key, final SortingParams sortingParameters);


    public Long zcount(final String key, final double min, final double max);


    public Long zcount(final String key, final String min, final String max);


    public Set<String> zrangeByScore(final String key, final double min, final double max);


    public Set<String> zrangeByScore(final String key, final String min, final String max);


    public Set<String> zrevrangeByScore(final String key, final double max, final double min);


    public Set<String> zrangeByScore(final String key, final double min, final double max, final int offset, final int count);


    public Set<String> zrevrangeByScore(final String key, final String max, final String min);


    public Set<String> zrangeByScore(final String key, final String min, final String max, final int offset, final int count);


    public Set<String> zrevrangeByScore(final String key, final double max, final double min, final int offset, final int count);


    public Set<Tuple> zrangeByScoreWithScores(final String key, final double min, final double max);


    public Set<Tuple> zrevrangeByScoreWithScores(final String key, final double max, final double min);


    public Set<Tuple> zrangeByScoreWithScores(final String key, final double min, final double max, final int offset, final int count);


    public Set<String> zrevrangeByScore(final String key, final String max, final String min, final int offset, final int count);


    public Set<Tuple> zrangeByScoreWithScores(final String key, final String min, final String max);


    public Set<Tuple> zrevrangeByScoreWithScores(final String key, final String max, final String min);


    public Set<Tuple> zrangeByScoreWithScores(final String key, final String min, final String max, final int offset, final int count);


    public Set<Tuple> zrevrangeByScoreWithScores(final String key, final double max, final double min, final int offset, final int count);


    public Set<Tuple> zrevrangeByScoreWithScores(final String key, final String max, final String min, final int offset, final int count);


    public Long zremrangeByRank(final String key, final long start, final long stop);


    public Long zremrangeByScore(final String key, final double min, final double max);


    public Long zremrangeByScore(final String key, final String min, final String max);


    public Long zlexcount(final String key, final String min, final String max);


    public Set<String> zrangeByLex(final String key, final String min, final String max);


    public Set<String> zrangeByLex(final String key, final String min, final String max, final int offset, final int count);


    public Set<String> zrevrangeByLex(final String key, final String max, final String min);


    public Set<String> zrevrangeByLex(final String key, final String max, final String min, final int offset, final int count);


    public Long zremrangeByLex(final String key, final String min, final String max);


    public Long linsert(final String key, final ListPosition where, final String pivot, final String value);


    public Long lpushx(final String key, final String... string);


    public Long rpushx(final String key, final String... string);


    public Long del(final String key);


    public Long unlink(final String key);


    public Long unlink(final String... keys);


    public String echo(final String string);


    public Long bitcount(final String key);


    public Long bitcount(final String key, final long start, final long end);


    public Set<String> keys(final String pattern);


    public ScanResult<String> scan(String cursor, ScanParams params);


    public ScanResult<String> scan(final String cursor, final ScanParams params, final String type);


    public ScanResult<Map.Entry<String, String>> hscan(final String key, final String cursor);


    public ScanResult<String> sscan(final String key, final String cursor);

    public ScanResult<String> sscan(final String key, final String cursor, ScanParams params);


    public ScanResult<Tuple> zscan(final String key, final String cursor);

    public ScanResult<Tuple> zscan(final String key, final String cursor, ScanParams params);


    public Long pfadd(final String key, final String... elements);


    public long pfcount(final String key);


    public Long del(final String... keys);


    public String lmove(final String srcKey, final String dstKey, final ListDirection from, final ListDirection to);


    public String blmove(final String srcKey, final String dstKey, final ListDirection from, final ListDirection to, final double timeout);


    long lpushx(String key, long expireSeconds, String... strings);

    long rpushx(String key, long expireSeconds, String... strings);

    public List<String> blpop(final int timeout, final String... keys);


    public KeyedListElement blpop(final double timeout, final String... keys);


    public List<String> brpop(final int timeout, final String... keys);


    public KeyedListElement brpop(final double timeout, final String... keys);


    public KeyedZSetElement bzpopmax(final double timeout, final String... keys);


    public KeyedZSetElement bzpopmin(final double timeout, final String... keys);


    public List<String> blpop(final int timeout, final String key);


    public KeyedListElement blpop(final double timeout, final String key);


    public List<String> brpop(final int timeout, final String key);


    public KeyedListElement brpop(final double timeout, final String key);


    public List<String> mget(final String... keys);


    public String mset(final String... keysvalues);


    public void mset(final long expireSeconds, final String... keysvalues);


    public Long msetnx(final String... keysvalues);


    public long msetnx(final long expireSeconds, final String... keysvalues);


    public String rename(final String oldkey, final String newkey);


    public Long renamenx(final String oldkey, final String newkey);


    public String rpoplpush(final String srckey, final String dstkey);


    public Set<String> sdiff(final String... keys);


    public Long sdiffstore(final String dstkey, final String... keys);


    public Set<String> sinter(final String... keys);


    public Long sinterstore(final String dstkey, final String... keys);


    public Long smove(final String srckey, final String dstkey, final String member);


    public Long sort(final String key, final SortingParams sortingParameters, final String dstkey);


    public Long sort(final String key, final String dstkey);


    public Set<String> sunion(final String... keys);


    public Long sunionstore(final String dstkey, final String... keys);


    public Set<String> zinter(final ZParams params, final String... keys);


    public Set<Tuple> zinterWithScores(final ZParams params, final String... keys);


    public Long zinterstore(final String dstkey, final String... sets);


    public Long zinterstore(final String dstkey, final ZParams params, final String... sets);


    public Set<String> zunion(final ZParams params, final String... keys);


    public Set<Tuple> zunionWithScores(final ZParams params, final String... keys);


    public Long zunionstore(final String dstkey, final String... sets);


    public Long zunionstore(final String dstkey, final ZParams params, final String... sets);


    public String brpoplpush(final String source, final String destination, final int timeout);


    public Long publish(final String channel, final String message);


    public void subscribe(final JedisPubSub jedisPubSub, final String... channels);


    public void psubscribe(final JedisPubSub jedisPubSub, final String... patterns);


    public Long bitop(final BitOP op, final String destKey, final String... srcKeys);


    public String pfmerge(final String destkey, final String... sourcekeys);


    public long pfcount(final String... keys);


    public Object eval(final String script, final int keyCount, final String... params);


    public Object eval(final String script, String sampleKey);


    public Object eval(final String script, final List<String> keys, final List<String> args);


    Object evalsha(String sha1);

    public Object evalsha(final String sha1, final int keyCount, final String... params);


    public Object evalsha(final String sha1, final List<String> keys, final List<String> args);


    public Object evalsha(final String sha1, String sampleKey);


    public Boolean scriptExists(final String sha1, String sampleKey);


    public List<Boolean> scriptExists(String sampleKey, final String... sha1);


    public String scriptLoad(final String script, String sampleKey);


    public String scriptFlush(String sampleKey);


    public String scriptKill(String sampleKey);


    public Long geoadd(final String key, final double longitude, final double latitude, final String member);


    public Long geoadd(final String key, final Map<String, GeoCoordinate> memberCoordinateMap);


    public Long geoadd(final String key, final GeoAddParams params, final Map<String, GeoCoordinate> memberCoordinateMap);


    public Double geodist(final String key, final String member1, final String member2);


    public Double geodist(final String key, final String member1, final String member2, final GeoUnit unit);


    public List<String> geohash(final String key, final String... members);


    public List<GeoCoordinate> geopos(final String key, final String... members);


    public List<GeoRadiusResponse> georadius(final String key, final double longitude, final double latitude, final double radius, final GeoUnit unit);


    public List<GeoRadiusResponse> georadiusReadonly(final String key, final double longitude, final double latitude, final double radius, final GeoUnit unit);


    public List<GeoRadiusResponse> georadius(final String key, final double longitude, final double latitude, final double radius, final GeoUnit unit, final GeoRadiusParam param);


    public Long georadiusStore(final String key, final double longitude, final double latitude, final double radius, final GeoUnit unit, final GeoRadiusParam param, final GeoRadiusStoreParam storeParam);


    public List<GeoRadiusResponse> georadiusReadonly(final String key, final double longitude, final double latitude, final double radius, final GeoUnit unit, final GeoRadiusParam param);


    public List<GeoRadiusResponse> georadiusByMember(final String key, final String member, final double radius, final GeoUnit unit);


    public List<GeoRadiusResponse> georadiusByMemberReadonly(final String key, final String member, final double radius, final GeoUnit unit);


    public List<GeoRadiusResponse> georadiusByMember(final String key, final String member, final double radius, final GeoUnit unit, final GeoRadiusParam param);


    public Long georadiusByMemberStore(final String key, final String member, final double radius, final GeoUnit unit, final GeoRadiusParam param, final GeoRadiusStoreParam storeParam);


    public List<GeoRadiusResponse> georadiusByMemberReadonly(final String key, final String member, final double radius, final GeoUnit unit, final GeoRadiusParam param);


    public List<Long> bitfield(final String key, final String... arguments);


    public List<Long> bitfieldReadonly(final String key, final String... arguments);


    public Long hstrlen(final String key, final String field);


    public Long memoryUsage(final String key);


    public Long memoryUsage(final String key, final int samples);


    public StreamEntryID xadd(final String key, final StreamEntryID id, final Map<String, String> hash);


    public StreamEntryID xadd(final String key, final StreamEntryID id, final Map<String, String> hash, final long maxLen, final boolean approximateLength);


    public StreamEntryID xadd(final String key, final Map<String, String> hash, final XAddParams params);


    public Long xlen(final String key);


    public List<StreamEntry> xrange(final String key, final StreamEntryID start, final StreamEntryID end);


    public List<StreamEntry> xrange(final String key, final StreamEntryID start, final StreamEntryID end, final int count);


    public List<StreamEntry> xrevrange(final String key, final StreamEntryID end, final StreamEntryID start);


    public List<StreamEntry> xrevrange(final String key, final StreamEntryID end, final StreamEntryID start, final int count);


    public List<Map.Entry<String, List<StreamEntry>>> xread(final int count, final long block, final Map.Entry<String, StreamEntryID>... streams);


    public List<Map.Entry<String, List<StreamEntry>>> xread(final XReadParams xReadParams, final Map<String, StreamEntryID> streams);


    public Long xack(final String key, final String group, final StreamEntryID... ids);


    public String xgroupCreate(final String key, final String groupname, final StreamEntryID id, final boolean makeStream);


    public String xgroupSetID(final String key, final String groupname, final StreamEntryID id);


    public Long xgroupDestroy(final String key, final String groupname);


    public Long xgroupDelConsumer(final String key, final String groupname, final String consumername);


    public List<Map.Entry<String, List<StreamEntry>>> xreadGroup(final String groupname, final String consumer, final int count, final long block, final boolean noAck, final Map.Entry<String, StreamEntryID>... streams);


    public List<Map.Entry<String, List<StreamEntry>>> xreadGroup(final String groupname, final String consumer, final XReadGroupParams xReadGroupParams, final Map<String, StreamEntryID> streams);


    public StreamPendingSummary xpending(final String key, final String groupname);


    public List<StreamPendingEntry> xpending(final String key, final String groupname, final StreamEntryID start, final StreamEntryID end, final int count, final String consumername);


    public List<StreamPendingEntry> xpending(final String key, final String groupname, final XPendingParams params);


    public Long xdel(final String key, final StreamEntryID... ids);


    public Long xtrim(final String key, final long maxLen, final boolean approximateLength);


    public Long xtrim(final String key, final XTrimParams params);


    public List<StreamEntry> xclaim(final String key, final String group, final String consumername, final long minIdleTime, final long newIdleTime, final int retries, final boolean force, final StreamEntryID... ids);


    public List<StreamEntry> xclaim(final String key, final String group, final String consumername, final long minIdleTime, final XClaimParams params, final StreamEntryID... ids);


    public List<StreamEntryID> xclaimJustId(final String key, final String group, final String consumername, final long minIdleTime, final XClaimParams params, final StreamEntryID... ids);


    public Map.Entry<StreamEntryID, List<StreamEntry>> xautoclaim(final String key, final String group, final String consumerName, final long minIdleTime, final StreamEntryID start, final XAutoClaimParams params);


    public Map.Entry<StreamEntryID, List<StreamEntryID>> xautoclaimJustId(final String key, final String group, final String consumerName, final long minIdleTime, final StreamEntryID start, final XAutoClaimParams params);


    public Long waitReplicas(String key, final int replicas, final long timeout);


    public Object sendCommand(String sampleKey, final ProtocolCommand cmd, final String... args);


    public Object sendBlockingCommand(String sampleKey, final ProtocolCommand cmd, final String... args);


    String getWithDefault(String key, String defaultValue);

    <T> T getObjectWithDefault(String key, Class<T> clazz, T defaultValue);

    String hgetWithDefault(String key, String field, String defaultValue);

    <T> T hgetObjectWithDefault(String key, String field, Class<T> clazz, T defaultValue);

    String setObject(String key, Serializable objValue, SetParams params);

    <T> T getObject(String key, Class<T> clazz);

    long setnxObject(String key, long second, Serializable objValue);

    String setexObject(String key, long seconds, Serializable objValue);

    String psetexObject(String key, long milliseconds, Serializable objValue);

    long hsetObject(String key, long expireSeconds, String field, Serializable objValue);

    <T> T hgetObject(String key, String field, Class<T> clazz);

    long hsetnxObject(String key, long expireSeconds, String field, Serializable objValue);

    /**
     * 集群获取节点
     *
     * @return
     */
    public Map<String, JedisPool> getClusterNodes();

    /**
     * 非集群获取节点
     *
     * @param <T>
     * @return
     */
    <T extends Pool<Jedis>> Map<String, Pool<Jedis>> getNodes();


    public ScanResult<Map.Entry<String, String>> hscan(final String key, final String cursor, ScanParams params);
}