package shuxin.config.impl;


import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableMap;
import redis.clients.jedis.*;
import redis.clients.jedis.args.ListDirection;
import redis.clients.jedis.commands.ProtocolCommand;
import redis.clients.jedis.params.*;
import redis.clients.jedis.resps.KeyedListElement;
import redis.clients.jedis.resps.KeyedZSetElement;
import redis.clients.jedis.resps.LCSMatchResult;
import redis.clients.jedis.util.Pool;
import shuxin.config.RedisClient;
import shuxin.config.RedisType;
import shuxin.config.pipeline.BasePipelineTaskWithResult;
import shuxin.config.pipeline.BasePipelineTaskWithResultList;
import shuxin.config.pipeline.BasePipelineTaskWithResultMap;
import shuxin.config.pipeline.BasePipelineTaskWithoutResult;

import java.io.Serializable;
import java.util.*;

public class RedisClientImpl implements RedisClient {
    private final Pool<Jedis> pool;

    private final RedisType type;

    public RedisClientImpl(Pool<Jedis> pool, RedisType type) {
        this.pool = pool;
        this.type = type;
    }

    @Override
    public RedisType getType() {
        return this.type;
    }

    public String set(String key, String value, SetParams params) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var5 = null;

        String var6;
        try {
            var6 = jedis.set(key, value, params);
        } catch (Throwable var15) {
            var5 = var15;
            throw var15;
        } finally {
            if (jedis != null) {
                if (var5 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var14) {
                        var5.addSuppressed(var14);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var6;
    }

    @Override
    public String get(String key) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;

        String var4;
        try {
            var4 = jedis.get(key);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var4;
    }

    @Override
    public String getDel(String key) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        String var4;
        try {
            var4 = jedis.getDel(key);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return var4;
    }

    @Override
    public String getEx(String key, GetExParams params) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        String var4;
        try {
            var4 = jedis.getEx(key, params);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return var4;
    }

    @Override
    public Boolean setbit(String key, long offset, boolean value) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var6 = null;

        boolean var7;
        try {
            var7 = jedis.setbit(key, offset, value);
        } catch (Throwable var16) {
            var6 = var16;
            throw var16;
        } finally {
            if (jedis != null) {
                if (var6 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var15) {
                        var6.addSuppressed(var15);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var7;
    }

    @Override
    public Boolean setbit(String key, long offset, String value) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var6 = null;
        boolean var7;
        try {
            var7 = jedis.setbit(key, offset, value);
        } catch (Throwable var16) {
            var6 = var16;
            throw var16;
        } finally {
            if (jedis != null) {
                if (var6 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var15) {
                        var6.addSuppressed(var15);
                    }
                } else {
                    jedis.close();
                }
            }

        }
        return var7;
    }

    @Override
    public Boolean getbit(String key, long offset) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var5 = null;

        boolean var6;
        try {
            var6 = jedis.getbit(key, offset);
        } catch (Throwable var15) {
            var5 = var15;
            throw var15;
        } finally {
            if (jedis != null) {
                if (var5 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var14) {
                        var5.addSuppressed(var14);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var6;
    }

    @Override
    public Long setrange(String key, long offset, String value) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var6 = null;

        long var7;
        try {
            var7 = jedis.setrange(key, offset, value);
        } catch (Throwable var17) {
            var6 = var17;
            throw var17;
        } finally {
            if (jedis != null) {
                if (var6 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var16) {
                        var6.addSuppressed(var16);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var7;
    }

    @Override
    public String getrange(String key, long startOffset, long endOffset) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var7 = null;

        String var8;
        try {
            var8 = jedis.getrange(key, startOffset, endOffset);
        } catch (Throwable var17) {
            var7 = var17;
            throw var17;
        } finally {
            if (jedis != null) {
                if (var7 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var16) {
                        var7.addSuppressed(var16);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var8;
    }

    @Override
    public String getSet(String key, String value) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var7 = null;

        String var8;
        try {
            var8 = jedis.getSet(key, value);
        } catch (Throwable var17) {
            var7 = var17;
            throw var17;
        } finally {
            if (jedis != null) {
                if (var7 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var16) {
                        var7.addSuppressed(var16);
                    }
                } else {
                    jedis.close();
                }
            }

        }
        return var8;
    }

    @Override
    public Long setnx(String key, String value) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var7 = null;
        Long var8;
        try {
            var8 = jedis.setnx(key, value);
        } catch (Throwable var17) {
            var7 = var17;
            throw var17;
        } finally {
            if (jedis != null) {
                if (var7 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var16) {
                        var7.addSuppressed(var16);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return var8;
    }

    @Override
    public long setnx(final String key, final long second, final String value) {
        return (Long) this.pipeline(new BasePipelineTaskWithResult<Long>() {
            public Response<Long> exec(Pipeline p) {
                Response<Long> setnx = p.setnx(key, value);
                p.expire(key, second);
                return setnx;
            }
        });
    }

    @Override
    public String setex(String key, long seconds, String value) {
        Jedis jedis;
        Throwable var6;
        String var7;
        if (seconds < 1L) {
            jedis = (Jedis) this.pool.getResource();
            var6 = null;

            try {
                var7 = jedis.set(key, value);
            } catch (Throwable var30) {
                var6 = var30;
                throw var30;
            } finally {
                if (jedis != null) {
                    if (var6 != null) {
                        try {
                            jedis.close();
                        } catch (Throwable var28) {
                            var6.addSuppressed(var28);
                        }
                    } else {
                        jedis.close();
                    }
                }

            }

            return var7;
        } else {
            jedis = (Jedis) this.pool.getResource();
            var6 = null;

            try {
                var7 = jedis.setex(key, seconds, value);
            } catch (Throwable var31) {
                var6 = var31;
                throw var31;
            } finally {
                if (jedis != null) {
                    if (var6 != null) {
                        try {
                            jedis.close();
                        } catch (Throwable var29) {
                            var6.addSuppressed(var29);
                        }
                    } else {
                        jedis.close();
                    }
                }

            }

            return var7;
        }
    }

    @Override
    public String psetex(String key, long milliseconds, String value) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var6 = null;
        String var7;
        try {
            var7 = jedis.psetex(key, milliseconds, value);
        } catch (Throwable var16) {
            var6 = var16;
            throw var16;
        } finally {
            if (jedis != null) {
                if (var6 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var15) {
                        var6.addSuppressed(var15);
                    }
                } else {
                    jedis.close();
                }
            }

        }
        return var7;
    }

    @Override
    public Long decrBy(String key, long decrement) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var6 = null;
        Long var7;
        try {
            var7 = jedis.decrBy(key, decrement);
        } catch (Throwable var16) {
            var6 = var16;
            throw var16;
        } finally {
            if (jedis != null) {
                if (var6 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var15) {
                        var6.addSuppressed(var15);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return var7;
    }

    @Override
    public Long decr(String key) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var6 = null;
        Long var7;
        try {
            var7 = jedis.decr(key);
        } catch (Throwable var16) {
            var6 = var16;
            throw var16;
        } finally {
            if (jedis != null) {
                if (var6 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var15) {
                        var6.addSuppressed(var15);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return var7;
    }

    @Override
    public Long incrBy(String key, long increment) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var6 = null;
        Long var7;
        try {
            var7 = jedis.incrBy(key, increment);
        } catch (Throwable var16) {
            var6 = var16;
            throw var16;
        } finally {
            if (jedis != null) {
                if (var6 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var15) {
                        var6.addSuppressed(var15);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return var7;
    }

    @Override
    public List<String> mget(String... keys) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;

        List var4;
        try {
            var4 = jedis.mget(keys);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var4;
    }

    @Override
    public String mset(String... keysvalues) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;

        String var4;
        try {
            var4 = jedis.mset(keysvalues);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return var4;
    }

    @Override
    public void mset(final long seconds, final String... keysvalues) {
        this.pipeline(new BasePipelineTaskWithoutResult() {
            public void exec(Pipeline p) {
                for (int i = 0; i < keysvalues.length / 2; i += 2) {
                    p.setex(keysvalues[i], seconds, keysvalues[i + 1]);
                }

            }
        });
    }

    @Override
    public Long msetnx(String... keysvalues) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        Long var4;
        try {
            var4 = jedis.msetnx(keysvalues);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return var4;
    }

    @Override
    public long msetnx(final long seconds, final String... keysvalues) {
        Map<String, Long> map = this.pipeline(new BasePipelineTaskWithResultMap<Long>() {
            public Map<String, Response<Long>> exec(Pipeline p) {
                Map<String, Response<Long>> map = new HashMap(keysvalues.length / 2);

                for (int i = 0; i < keysvalues.length / 2; i += 2) {
                    String key = keysvalues[i];
                    map.put(key, p.setnx(key, keysvalues[i + 1]));
                }

                return map;
            }
        });
        if (map.size() > 0) {
            final List<String> keys = new ArrayList(map.size());
            Iterator var6 = map.entrySet().iterator();

            while (var6.hasNext()) {
                Map.Entry<String, Long> e = (Map.Entry) var6.next();
                if (((Long) e.getValue()).intValue() == 1) {
                    keys.add(e.getKey());
                }
            }

            if (keys.size() > 0) {
                this.pipeline(new BasePipelineTaskWithoutResult() {
                    public void exec(Pipeline p) {
                        Iterator var2 = keys.iterator();

                        while (var2.hasNext()) {
                            String k = (String) var2.next();
                            p.expire(k, seconds);
                        }

                    }
                });
            }

            return (long) keys.size();
        } else {
            return 0L;
        }
    }

    @Override
    public String rename(String oldkey, String newkey) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        String var4;
        try {
            var4 = jedis.rename(oldkey, newkey);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return var4;
    }

    @Override
    public Long renamenx(String oldkey, String newkey) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        Long var4;
        try {
            var4 = jedis.renamenx(oldkey, newkey);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return var4;
    }

    @Override
    public String rpoplpush(String srckey, String dstkey) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        String var4;
        try {
            var4 = jedis.rpoplpush(srckey, dstkey);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return var4;
    }

    @Override
    public Set<String> sdiff(String... keys) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        Set<String> var4;
        try {
            var4 = jedis.sdiff(keys);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return var4;
    }

    @Override
    public Long sdiffstore(String dstkey, String... keys) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        Long var4;
        try {
            var4 = jedis.sdiffstore(dstkey, keys);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return var4;
    }

    @Override
    public Set<String> sinter(String... keys) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        Set<String> var4;
        try {
            var4 = jedis.sinter(keys);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return var4;
    }

    @Override
    public Long sinterstore(String dstkey, String... keys) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        Long var4;
        try {
            var4 = jedis.sinterstore(dstkey, keys);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return var4;
    }

    @Override
    public Long smove(String srckey, String dstkey, String member) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        Long var4;
        try {
            var4 = jedis.smove(srckey, dstkey, member);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return var4;
    }

    @Override
    public Long sort(String key, SortingParams sortingParameters, String dstkey) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        Long var4;
        try {
            var4 = jedis.sort(key, sortingParameters, dstkey);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return var4;
    }

    @Override
    public Long sort(String key, String dstkey) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        Long var4;
        try {
            var4 = jedis.sort(key, dstkey);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return var4;
    }

    @Override
    public Set<String> sunion(String... keys) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        Set<String> var4;
        try {
            var4 = jedis.sunion(keys);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return var4;
    }

    @Override
    public Long sunionstore(String dstkey, String... keys) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        Long var4;
        try {
            var4 = jedis.sunionstore(dstkey, keys);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return var4;
    }

    @Override
    public Set<String> zinter(ZParams params, String... keys) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        Set<String> var4;
        try {
            var4 = jedis.zinter(params, keys);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return var4;
    }

    @Override
    public Set<Tuple> zinterWithScores(ZParams params, String... keys) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        Set<Tuple> var4;
        try {
            var4 = jedis.zinterWithScores(params, keys);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return var4;
    }

    @Override
    public Long zinterstore(String dstkey, String... sets) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        Long var4;
        try {
            var4 = jedis.zinterstore(dstkey, sets);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return var4;
    }

    @Override
    public Long zinterstore(String dstkey, ZParams params, String... sets) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        Long var4;
        try {
            var4 = jedis.zinterstore(dstkey, params, sets);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return var4;
    }

    @Override
    public Set<String> zunion(ZParams params, String... keys) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        Set<String> var4;
        try {
            var4 = jedis.zunion(params, keys);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return var4;
    }

    @Override
    public Set<Tuple> zunionWithScores(ZParams params, String... keys) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        Set<Tuple> var4;
        try {
            var4 = jedis.zunionWithScores(params, keys);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return var4;
    }

    @Override
    public Long zunionstore(String dstkey, String... sets) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        Long var4;
        try {
            var4 = jedis.zunionstore(dstkey, sets);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return var4;
    }

    @Override
    public Long zunionstore(String dstkey, ZParams params, String... sets) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        Long var4;
        try {
            var4 = jedis.zunionstore(dstkey, params, sets);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return var4;
    }

    @Override
    public String brpoplpush(String source, String destination, int timeout) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        String var4;
        try {
            var4 = jedis.brpoplpush(source, destination, timeout);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return var4;
    }

    @Override
    public Long publish(String channel, String message) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        Long var4;
        try {
            var4 = jedis.publish(channel, message);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return var4;
    }

    @Override
    public void subscribe(JedisPubSub jedisPubSub, String... channels) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        try {
            jedis.subscribe(jedisPubSub, channels);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
    }

    @Override
    public void psubscribe(JedisPubSub jedisPubSub, String... patterns) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        try {
            jedis.psubscribe(jedisPubSub, patterns);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
    }

    @Override
    public long incr(final String key, final long expireSeconds) {
        if (expireSeconds < 1L) {
            Jedis jedis = (Jedis) this.pool.getResource();
            Throwable var5 = null;

            long var6;
            try {
                var6 = jedis.incr(key);
            } catch (Throwable var16) {
                var5 = var16;
                throw var16;
            } finally {
                if (jedis != null) {
                    if (var5 != null) {
                        try {
                            jedis.close();
                        } catch (Throwable var15) {
                            var5.addSuppressed(var15);
                        }
                    } else {
                        jedis.close();
                    }
                }

            }

            return var6;
        } else {
            return (Long) this.pipeline(new BasePipelineTaskWithResult<Long>() {
                public Response<Long> exec(Pipeline p) {
                    Response<Long> incr = p.incr(key);
                    p.expire(key, expireSeconds);
                    return incr;
                }
            });
        }
    }

    @Override
    public Long append(String key, String value) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        Long result;
        try {
            result = jedis.append(key, value);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public String substr(String key, int start, int end) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        String result;
        try {
            result = jedis.substr(key, start, end);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public Long hset(String key, String field, String value) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        Long result;
        try {
            result = jedis.hset(key, field, value);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public Long hset(String key, Map<String, String> hash) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        Long result;
        try {
            result = jedis.hset(key, hash);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public long incrBy(final String key, final long expireSeconds, final long increment) {
        if (expireSeconds < 1L) {
            Jedis jedis = (Jedis) this.pool.getResource();
            Throwable var7 = null;

            long var8;
            try {
                var8 = jedis.incrBy(key, increment);
            } catch (Throwable var18) {
                var7 = var18;
                throw var18;
            } finally {
                if (jedis != null) {
                    if (var7 != null) {
                        try {
                            jedis.close();
                        } catch (Throwable var17) {
                            var7.addSuppressed(var17);
                        }
                    } else {
                        jedis.close();
                    }
                }

            }

            return var8;
        } else {
            return (Long) this.pipeline(new BasePipelineTaskWithResult<Long>() {
                public Response<Long> exec(Pipeline p) {
                    Response<Long> incr = p.incrBy(key, increment);
                    p.expire(key, expireSeconds);
                    return incr;
                }
            });
        }
    }

    @Override
    public Double incrByFloat(String key, double increment) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        Double result;
        try {
            result = jedis.incrByFloat(key, increment);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public Long incr(String key) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        Long result;
        try {
            result = jedis.incr(key);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public double incrByFloat(final String key, final long seconds, final double increment) {
        if (seconds < 1L) {
            Jedis jedis = (Jedis) this.pool.getResource();
            Throwable var7 = null;

            double var8;
            try {
                var8 = jedis.incrByFloat(key, increment);
            } catch (Throwable var18) {
                var7 = var18;
                throw var18;
            } finally {
                if (jedis != null) {
                    if (var7 != null) {
                        try {
                            jedis.close();
                        } catch (Throwable var17) {
                            var7.addSuppressed(var17);
                        }
                    } else {
                        jedis.close();
                    }
                }

            }

            return var8;
        } else {
            return (Double) this.pipeline(new BasePipelineTaskWithResult<Double>() {
                public Response<Double> exec(Pipeline p) {
                    Response<Double> incr = p.incrByFloat(key, increment);
                    p.expire(key, seconds);
                    return incr;
                }
            });
        }
    }

    @Override
    public long decr(final String key, final long seconds) {
        if (seconds < 1L) {
            Jedis jedis = (Jedis) this.pool.getResource();
            Throwable var5 = null;

            long var6;
            try {
                var6 = jedis.decr(key);
            } catch (Throwable var16) {
                var5 = var16;
                throw var16;
            } finally {
                if (jedis != null) {
                    if (var5 != null) {
                        try {
                            jedis.close();
                        } catch (Throwable var15) {
                            var5.addSuppressed(var15);
                        }
                    } else {
                        jedis.close();
                    }
                }

            }

            return var6;
        } else {
            return (Long) this.pipeline(new BasePipelineTaskWithResult<Long>() {
                public Response<Long> exec(Pipeline p) {
                    Response<Long> incr = p.decr(key);
                    p.expire(key, seconds);
                    return incr;
                }
            });
        }
    }

    @Override
    public long decrBy(final String key, final long seconds, final long decrement) {
        if (seconds < 1L) {
            Jedis jedis = (Jedis) this.pool.getResource();
            Throwable var7 = null;

            long var8;
            try {
                var8 = jedis.decrBy(key, decrement);
            } catch (Throwable var18) {
                var7 = var18;
                throw var18;
            } finally {
                if (jedis != null) {
                    if (var7 != null) {
                        try {
                            jedis.close();
                        } catch (Throwable var17) {
                            var7.addSuppressed(var17);
                        }
                    } else {
                        jedis.close();
                    }
                }

            }

            return var8;
        } else {
            return (Long) this.pipeline(new BasePipelineTaskWithResult<Long>() {
                public Response<Long> exec(Pipeline p) {
                    Response<Long> incr = p.decrBy(key, decrement);
                    p.expire(key, seconds);
                    return incr;
                }
            });
        }
    }

    @Override
    public long append(final String key, final long seconds, final String value) {
        if (seconds < 1L) {
            Jedis jedis = (Jedis) this.pool.getResource();
            Throwable var6 = null;

            long var7;
            try {
                var7 = jedis.append(key, value);
            } catch (Throwable var17) {
                var6 = var17;
                throw var17;
            } finally {
                if (jedis != null) {
                    if (var6 != null) {
                        try {
                            jedis.close();
                        } catch (Throwable var16) {
                            var6.addSuppressed(var16);
                        }
                    } else {
                        jedis.close();
                    }
                }

            }

            return var7;
        } else {
            return (Long) this.pipeline(new BasePipelineTaskWithResult<Long>() {
                public Response<Long> exec(Pipeline p) {
                    Response<Long> incr = p.append(key, value);
                    p.expire(key, seconds);
                    return incr;
                }
            });
        }
    }

    @Override
    public Long strlen(String key) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;

        long var4;
        try {
            var4 = jedis.strlen(key);
        } catch (Throwable var14) {
            var3 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var3.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var4;
    }

    @Override
    public LCSMatchResult strAlgoLCSKeys(String keyA, String keyB, StrAlgoLCSParams params) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        LCSMatchResult result;
        try {
            result = jedis.strAlgoLCSKeys(keyA, keyB, params);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public LCSMatchResult strAlgoLCSStrings(String strA, String strB, StrAlgoLCSParams params) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        LCSMatchResult result;
        try {
            result = jedis.strAlgoLCSStrings(strA, strB, params);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public Long zadd(String key, double score, String member) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        Long result;
        try {
            result = jedis.zadd(key, score, member);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public Long zadd(String key, double score, String member, ZAddParams params) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        Long result;
        try {
            result = jedis.zadd(key, score, member, params);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public Long zadd(String key, Map<String, Double> scoreMembers) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        Long result;
        try {
            result = jedis.zadd(key, scoreMembers);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public Long zadd(String key, Map<String, Double> scoreMembers, ZAddParams params) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        Long result;
        try {
            result = jedis.zadd(key, scoreMembers, params);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public Double zaddIncr(String key, double score, String member, ZAddParams params) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        Double result;
        try {
            result = jedis.zaddIncr(key, score, member, params);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public Set<String> zdiff(String... keys) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        Set<String> result;
        try {
            result = jedis.zdiff(keys);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public Set<Tuple> zdiffWithScores(String... keys) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        Set<Tuple> result;
        try {
            result = jedis.zdiffWithScores(keys);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public Long zdiffStore(String dstkey, String... keys) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        Long result;
        try {
            result = jedis.zdiffStore(dstkey, keys);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public Long bitcount(String key) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;

        long var4;
        try {
            var4 = jedis.bitcount(key);
        } catch (Throwable var14) {
            var3 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var3.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var4;
    }

    @Override
    public Long bitcount(String key, long start, long end) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var7 = null;

        long var8;
        try {
            var8 = jedis.bitcount(key, start, end);
        } catch (Throwable var18) {
            var7 = var18;
            throw var18;
        } finally {
            if (jedis != null) {
                if (var7 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var17) {
                        var7.addSuppressed(var17);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var8;
    }

    @Override
    public Set<String> keys(String pattern) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        Set<String> result;
        try {
            result = jedis.keys(pattern);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public List<Long> bitfield(String key, String... arguments) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var4 = null;

        List var5;
        try {
            var5 = jedis.bitfield(key, arguments);
        } catch (Throwable var14) {
            var4 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var4.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var5;
    }

    @Override
    public List<Long> bitfieldReadonly(String key, String... arguments) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var4 = null;

        List var5;
        try {
            var5 = jedis.bitfieldReadonly(key, arguments);
        } catch (Throwable var14) {
            var4 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var4.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var5;
    }

    @Override
    public Long bitop(BitOP op, String destKey, String... srcKeys) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var5 = null;

        long var6;
        try {
            var6 = jedis.bitop(op, destKey, srcKeys);
        } catch (Throwable var16) {
            var5 = var16;
            throw var16;
        } finally {
            if (jedis != null) {
                if (var5 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var15) {
                        var5.addSuppressed(var15);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var6;
    }

    @Override
    public Long geoadd(String key, double longitude, double latitude, String member) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var8 = null;

        long var9;
        try {
            var9 = jedis.geoadd(key, longitude, latitude, member);
        } catch (Throwable var19) {
            var8 = var19;
            throw var19;
        } finally {
            if (jedis != null) {
                if (var8 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var18) {
                        var8.addSuppressed(var18);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var9;
    }

    @Override
    public Long geoadd(String key, Map<String, GeoCoordinate> memberCoordinateMap) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var4 = null;

        long var5;
        try {
            var5 = jedis.geoadd(key, memberCoordinateMap);
        } catch (Throwable var15) {
            var4 = var15;
            throw var15;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var14) {
                        var4.addSuppressed(var14);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var5;
    }

    @Override
    public Long geoadd(String key, GeoAddParams params, Map<String, GeoCoordinate> memberCoordinateMap) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var5 = null;

        long var6;
        try {
            var6 = jedis.geoadd(key, params, memberCoordinateMap);
        } catch (Throwable var16) {
            var5 = var16;
            throw var16;
        } finally {
            if (jedis != null) {
                if (var5 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var15) {
                        var5.addSuppressed(var15);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var6;
    }

    @Override
    public Double geodist(String key, String member1, String member2) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var5 = null;

        Double var6;
        try {
            var6 = jedis.geodist(key, member1, member2);
        } catch (Throwable var15) {
            var5 = var15;
            throw var15;
        } finally {
            if (jedis != null) {
                if (var5 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var14) {
                        var5.addSuppressed(var14);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var6;
    }

    @Override
    public Double geodist(String key, String member1, String member2, GeoUnit unit) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var6 = null;

        Double var7;
        try {
            var7 = jedis.geodist(key, member1, member2, unit);
        } catch (Throwable var16) {
            var6 = var16;
            throw var16;
        } finally {
            if (jedis != null) {
                if (var6 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var15) {
                        var6.addSuppressed(var15);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var7;
    }

    @Override
    public List<String> geohash(String key, String... members) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var4 = null;

        List var5;
        try {
            var5 = jedis.geohash(key, members);
        } catch (Throwable var14) {
            var4 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var4.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var5;
    }

    @Override
    public List<GeoCoordinate> geopos(String key, String... members) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var4 = null;

        List var5;
        try {
            var5 = jedis.geopos(key, members);
        } catch (Throwable var14) {
            var4 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var4.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var5;
    }

    @Override
    public List<GeoRadiusResponse> georadius(String key, double longitude, double latitude, double radius, GeoUnit unit) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var10 = null;

        List var11;
        try {
            var11 = jedis.georadius(key, longitude, latitude, radius, unit);
        } catch (Throwable var20) {
            var10 = var20;
            throw var20;
        } finally {
            if (jedis != null) {
                if (var10 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var19) {
                        var10.addSuppressed(var19);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var11;
    }

    @Override
    public List<GeoRadiusResponse> georadiusReadonly(String key, double longitude, double latitude, double radius, GeoUnit unit) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var10 = null;

        List var11;
        try {
            var11 = jedis.georadiusReadonly(key, longitude, latitude, radius, unit);
        } catch (Throwable var20) {
            var10 = var20;
            throw var20;
        } finally {
            if (jedis != null) {
                if (var10 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var19) {
                        var10.addSuppressed(var19);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var11;
    }

    @Override
    public List<GeoRadiusResponse> georadius(String key, double longitude, double latitude, double radius, GeoUnit unit, GeoRadiusParam param) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var11 = null;

        List var12;
        try {
            var12 = jedis.georadius(key, longitude, latitude, radius, unit);
        } catch (Throwable var21) {
            var11 = var21;
            throw var21;
        } finally {
            if (jedis != null) {
                if (var11 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var20) {
                        var11.addSuppressed(var20);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var12;
    }

    @Override
    public List<GeoRadiusResponse> georadiusReadonly(String key, double longitude, double latitude, double radius, GeoUnit unit, GeoRadiusParam param) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var11 = null;

        List var12;
        try {
            var12 = jedis.georadiusReadonly(key, longitude, latitude, radius, unit);
        } catch (Throwable var21) {
            var11 = var21;
            throw var21;
        } finally {
            if (jedis != null) {
                if (var11 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var20) {
                        var11.addSuppressed(var20);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var12;
    }

    @Override
    public List<GeoRadiusResponse> georadiusByMember(String key, String member, double radius, GeoUnit unit) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var7 = null;

        List var8;
        try {
            var8 = jedis.georadiusByMember(key, member, radius, unit);
        } catch (Throwable var17) {
            var7 = var17;
            throw var17;
        } finally {
            if (jedis != null) {
                if (var7 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var16) {
                        var7.addSuppressed(var16);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var8;
    }

    @Override
    public List<GeoRadiusResponse> georadiusByMemberReadonly(String key, String member, double radius, GeoUnit unit) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var7 = null;

        List var8;
        try {
            var8 = jedis.georadiusByMemberReadonly(key, member, radius, unit);
        } catch (Throwable var17) {
            var7 = var17;
            throw var17;
        } finally {
            if (jedis != null) {
                if (var7 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var16) {
                        var7.addSuppressed(var16);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var8;
    }

    @Override
    public List<GeoRadiusResponse> georadiusByMember(String key, String member, double radius, GeoUnit unit, GeoRadiusParam param) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var8 = null;

        List var9;
        try {
            var9 = jedis.georadiusByMember(key, member, radius, unit, param);
        } catch (Throwable var18) {
            var8 = var18;
            throw var18;
        } finally {
            if (jedis != null) {
                if (var8 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var17) {
                        var8.addSuppressed(var17);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var9;
    }

    @Override
    public List<GeoRadiusResponse> georadiusByMemberReadonly(String key, String member, double radius, GeoUnit unit, GeoRadiusParam param) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var8 = null;

        List var9;
        try {
            var9 = jedis.georadiusByMemberReadonly(key, member, radius, unit, param);
        } catch (Throwable var18) {
            var8 = var18;
            throw var18;
        } finally {
            if (jedis != null) {
                if (var8 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var17) {
                        var8.addSuppressed(var17);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var9;
    }

    @Override
    public Long georadiusStore(String key, double longitude, double latitude, double radius, GeoUnit unit, GeoRadiusParam param, GeoRadiusStoreParam storeParam) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var12 = null;

        long var13;
        try {
            var13 = jedis.georadiusStore(key, longitude, latitude, radius, unit, param, storeParam);
        } catch (Throwable var23) {
            var12 = var23;
            throw var23;
        } finally {
            if (jedis != null) {
                if (var12 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var22) {
                        var12.addSuppressed(var22);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var13;
    }

    @Override
    public Long georadiusByMemberStore(String key, String member, double radius, GeoUnit unit, GeoRadiusParam param, GeoRadiusStoreParam storeParam) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var9 = null;

        long var10;
        try {
            var10 = jedis.georadiusByMemberStore(key, member, radius, unit, param, storeParam);
        } catch (Throwable var20) {
            var9 = var20;
            throw var20;
        } finally {
            if (jedis != null) {
                if (var9 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var19) {
                        var9.addSuppressed(var19);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var10;
    }

    @Override
    public long hset(final String key, final long expireSeconds, final String field, final String value) {
        if (expireSeconds < 1L) {
            Jedis jedis = (Jedis) this.pool.getResource();
            Throwable var7 = null;

            long var8;
            try {
                var8 = jedis.hset(key, field, value);
            } catch (Throwable var18) {
                var7 = var18;
                throw var18;
            } finally {
                if (jedis != null) {
                    if (var7 != null) {
                        try {
                            jedis.close();
                        } catch (Throwable var17) {
                            var7.addSuppressed(var17);
                        }
                    } else {
                        jedis.close();
                    }
                }

            }

            return var8;
        } else {
            return (Long) this.pipeline(new BasePipelineTaskWithResult<Long>() {
                public Response<Long> exec(Pipeline p) {
                    Response<Long> incr = p.hset(key, field, value);
                    p.expire(key, expireSeconds);
                    return incr;
                }
            });
        }
    }

    @Override
    public long hset(final String key, final long expireSeconds, final Map<String, String> hash) {
        if (expireSeconds < 1L) {
            Jedis jedis = (Jedis) this.pool.getResource();
            Throwable var6 = null;

            long var7;
            try {
                var7 = jedis.hset(key, hash);
            } catch (Throwable var17) {
                var6 = var17;
                throw var17;
            } finally {
                if (jedis != null) {
                    if (var6 != null) {
                        try {
                            jedis.close();
                        } catch (Throwable var16) {
                            var6.addSuppressed(var16);
                        }
                    } else {
                        jedis.close();
                    }
                }

            }

            return var7;
        } else {
            return (Long) this.pipeline(new BasePipelineTaskWithResult<Long>() {
                public Response<Long> exec(Pipeline p) {
                    Response<Long> incr = p.hset(key, hash);
                    p.expire(key, expireSeconds);
                    return incr;
                }
            });
        }
    }

    @Override
    public String hget(String key, String field) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var4 = null;

        String var5;
        try {
            var5 = jedis.hget(key, field);
        } catch (Throwable var14) {
            var4 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var4.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var5;
    }

    @Override
    public Long hsetnx(String key, String field, String value) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        Long result;
        try {
            result = jedis.hsetnx(key, field, value);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public String hmset(String key, Map<String, String> hash) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        String result;
        try {
            result = jedis.hmset(key, hash);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public long hsetnx(final String key, final long expireSeconds, final String field, final String value) {
        if (expireSeconds < 1L) {
            Jedis jedis = (Jedis) this.pool.getResource();
            Throwable var7 = null;

            long var8;
            try {
                var8 = jedis.hsetnx(key, field, value);
            } catch (Throwable var18) {
                var7 = var18;
                throw var18;
            } finally {
                if (jedis != null) {
                    if (var7 != null) {
                        try {
                            jedis.close();
                        } catch (Throwable var17) {
                            var7.addSuppressed(var17);
                        }
                    } else {
                        jedis.close();
                    }
                }

            }

            return var8;
        } else {
            return (Long) this.pipeline(new BasePipelineTaskWithResult<Long>() {
                public Response<Long> exec(Pipeline p) {
                    Response<Long> incr = p.hsetnx(key, field, value);
                    p.expire(key, expireSeconds);
                    return incr;
                }
            });
        }
    }

    @Override
    public String hmset(final String key, final long expireSeconds, final Map<String, String> hash) {
        if (expireSeconds < 1L) {
            Jedis jedis = (Jedis) this.pool.getResource();
            Throwable var6 = null;

            String var7;
            try {
                var7 = jedis.hmset(key, hash);
            } catch (Throwable var16) {
                var6 = var16;
                throw var16;
            } finally {
                if (jedis != null) {
                    if (var6 != null) {
                        try {
                            jedis.close();
                        } catch (Throwable var15) {
                            var6.addSuppressed(var15);
                        }
                    } else {
                        jedis.close();
                    }
                }

            }

            return var7;
        } else {
            return (String) this.pipeline(new BasePipelineTaskWithResult<String>() {
                public Response<String> exec(Pipeline p) {
                    Response<String> incr = p.hmset(key, hash);
                    p.expire(key, expireSeconds);
                    return incr;
                }
            });
        }
    }

    @Override
    public List<String> hmget(String key, String... fields) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var4 = null;

        List var5;
        try {
            var5 = jedis.hmget(key, fields);
        } catch (Throwable var14) {
            var4 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var4.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var5;
    }

    @Override
    public Long hincrBy(String key, String field, long value) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        Long result;
        try {
            result = jedis.hincrBy(key, field, value);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public Double hincrByFloat(String key, String field, double value) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        Double result;
        try {
            result = jedis.hincrByFloat(key, field, value);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    public long hincrBy(final String key, final long expireSeconds, final String field, final long value) {
        if (expireSeconds < 1L) {
            Jedis jedis = (Jedis) this.pool.getResource();
            Throwable var8 = null;

            long var9;
            try {
                var9 = jedis.hincrBy(key, field, value);
            } catch (Throwable var19) {
                var8 = var19;
                throw var19;
            } finally {
                if (jedis != null) {
                    if (var8 != null) {
                        try {
                            jedis.close();
                        } catch (Throwable var18) {
                            var8.addSuppressed(var18);
                        }
                    } else {
                        jedis.close();
                    }
                }

            }

            return var9;
        } else {
            return (Long) this.pipeline(new BasePipelineTaskWithResult<Long>() {
                public Response<Long> exec(Pipeline p) {
                    Response<Long> incr = p.hincrBy(key, field, value);
                    p.expire(key, expireSeconds);
                    return incr;
                }
            });
        }
    }

    @Override
    public double hincrByFloat(final String key, final long expireSeconds, final String field, final double value) {
        if (expireSeconds < 1L) {
            Jedis jedis = (Jedis) this.pool.getResource();
            Throwable var8 = null;

            double var9;
            try {
                var9 = jedis.hincrByFloat(key, field, value);
            } catch (Throwable var19) {
                var8 = var19;
                throw var19;
            } finally {
                if (jedis != null) {
                    if (var8 != null) {
                        try {
                            jedis.close();
                        } catch (Throwable var18) {
                            var8.addSuppressed(var18);
                        }
                    } else {
                        jedis.close();
                    }
                }

            }

            return var9;
        } else {
            return (Double) this.pipeline(new BasePipelineTaskWithResult<Double>() {
                public Response<Double> exec(Pipeline p) {
                    Response<Double> incr = p.hincrByFloat(key, field, value);
                    p.expire(key, expireSeconds);
                    return incr;
                }
            });
        }
    }

    @Override
    public Boolean hexists(String key, String field) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var4 = null;

        boolean var5;
        try {
            var5 = jedis.hexists(key, field);
        } catch (Throwable var14) {
            var4 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var4.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var5;
    }

    @Override
    public Long hdel(String key, String... field) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var4 = null;

        long var5;
        try {
            var5 = jedis.hdel(key, field);
        } catch (Throwable var15) {
            var4 = var15;
            throw var15;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var14) {
                        var4.addSuppressed(var14);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var5;
    }

    @Override
    public Long hlen(String key) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;

        long var4;
        try {
            var4 = jedis.hlen(key);
        } catch (Throwable var14) {
            var3 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var3.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var4;
    }

    @Override
    public Set<String> hkeys(String key) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;

        Set var4;
        try {
            var4 = jedis.hkeys(key);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var4;
    }

    @Override
    public List<String> hvals(String key) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;

        List var4;
        try {
            var4 = jedis.hvals(key);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var4;
    }

    @Override
    public Map<String, String> hgetAll(String key) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;

        Map var4;
        try {
            var4 = jedis.hgetAll(key);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var4;
    }

    @Override
    public String hrandfield(String key) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        String result;
        try {
            result = jedis.hrandfield(key);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public List<String> hrandfield(String key, long count) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        List<String> result;
        try {
            result = jedis.hrandfield(key, count);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public Map<String, String> hrandfieldWithValues(String key, long count) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var5 = null;

        Map var6;
        try {
            var6 = jedis.hrandfieldWithValues(key, count);
        } catch (Throwable var15) {
            var5 = var15;
            throw var15;
        } finally {
            if (jedis != null) {
                if (var5 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var14) {
                        var5.addSuppressed(var14);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var6;
    }

    @Override
    public Long rpush(String key, String... string) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        Long result;
        try {
            result = jedis.rpush(key, string);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public Long lpush(String key, String... string) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        Long result;
        try {
            result = jedis.lpush(key, string);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public ScanResult<Map.Entry<String, String>> hscan(String key, String cursor) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var4 = null;

        ScanResult var5;
        try {
            var5 = jedis.hscan(key, cursor);
        } catch (Throwable var14) {
            var4 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var4.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var5;
    }

    @Override
    public ScanResult<Map.Entry<String, String>> hscan(String key, String cursor, ScanParams params) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var4 = null;
        ScanResult var5;
        try {
            var5 = jedis.hscan(key, cursor, params);
        } catch (Throwable var14) {
            var4 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var4.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var5;
    }

    @Override
    public Long hstrlen(String key, String field) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var4 = null;

        long var5;
        try {
            var5 = jedis.hstrlen(key, field);
        } catch (Throwable var15) {
            var4 = var15;
            throw var15;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var14) {
                        var4.addSuppressed(var14);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var5;
    }

    @Override
    public Long pfadd(String key, String... elements) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var4 = null;

        long var5;
        try {
            var5 = jedis.pfadd(key, elements);
        } catch (Throwable var15) {
            var4 = var15;
            throw var15;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var14) {
                        var4.addSuppressed(var14);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var5;
    }

    @Override
    public String pfmerge(String destkey, String... sourcekeys) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var4 = null;

        String var5;
        try {
            var5 = jedis.pfmerge(destkey, sourcekeys);
        } catch (Throwable var14) {
            var4 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var4.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var5;
    }

    @Override
    public long pfcount(String key) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;

        long var4;
        try {
            var4 = jedis.pfcount(key);
        } catch (Throwable var14) {
            var3 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var3.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var4;
    }

    @Override
    public long pfcount(String... keys) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;

        long var4;
        try {
            var4 = jedis.pfcount(keys);
        } catch (Throwable var14) {
            var3 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var3.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var4;
    }

    @Override
    public Object eval(String script, int keyCount, String... params) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        Object result;
        try {
            result = jedis.eval(script, keyCount, params);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public Object eval(String script, String sampleKey) {
        throw new RuntimeException("standLone no such method");
    }

    @Override
    public Object eval(String script, List<String> keys, List<String> args) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        Object result;
        try {
            result = jedis.eval(script, keys, args);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public Boolean exists(String key) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        boolean var4;
        try {
            var4 = jedis.exists(key);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return var4;
    }

    @Override
    public Long exists(String... keys) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;

        long var4;
        try {
            var4 = jedis.exists(keys);
        } catch (Throwable var14) {
            var3 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var3.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var4;
    }

    @Override
    public Long persist(String key) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;

        long var4;
        try {
            var4 = jedis.persist(key);
        } catch (Throwable var14) {
            var3 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var3.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var4;
    }

    @Override
    public String type(String key) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;

        String var4;
        try {
            var4 = jedis.type(key);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var4;
    }

    @Override
    public byte[] dump(String key) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;

        byte[] var4;
        try {
            var4 = jedis.dump(key);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var4;
    }

    @Override
    public String restore(String key, long ttl, byte[] serializedValue) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var6 = null;

        String var7;
        try {
            var7 = jedis.restore(key, ttl, serializedValue);
        } catch (Throwable var16) {
            var6 = var16;
            throw var16;
        } finally {
            if (jedis != null) {
                if (var6 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var15) {
                        var6.addSuppressed(var15);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var7;
    }

    @Override
    public String restore(String key, long ttl, byte[] serializedValue, RestoreParams params) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var7 = null;

        String var8;
        try {
            var8 = jedis.restore(key, ttl, serializedValue, params);
        } catch (Throwable var17) {
            var7 = var17;
            throw var17;
        } finally {
            if (jedis != null) {
                if (var7 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var16) {
                        var7.addSuppressed(var16);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var8;
    }

    @Override
    public Long expire(String key, long seconds) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var5 = null;

        long var6;
        try {
            var6 = jedis.expire(key, seconds);
        } catch (Throwable var16) {
            var5 = var16;
            throw var16;
        } finally {
            if (jedis != null) {
                if (var5 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var15) {
                        var5.addSuppressed(var15);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var6;
    }

    @Override
    public Long pexpire(String key, long milliseconds) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var5 = null;

        long var6;
        try {
            var6 = jedis.pexpire(key, milliseconds);
        } catch (Throwable var16) {
            var5 = var16;
            throw var16;
        } finally {
            if (jedis != null) {
                if (var5 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var15) {
                        var5.addSuppressed(var15);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var6;
    }

    @Override
    public Long expireAt(String key, long unixTime) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var5 = null;

        long var6;
        try {
            var6 = jedis.expireAt(key, unixTime);
        } catch (Throwable var16) {
            var5 = var16;
            throw var16;
        } finally {
            if (jedis != null) {
                if (var5 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var15) {
                        var5.addSuppressed(var15);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var6;
    }

    @Override
    public Long pexpireAt(String key, long millisecondsTimestamp) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var5 = null;

        long var6;
        try {
            var6 = jedis.pexpireAt(key, millisecondsTimestamp);
        } catch (Throwable var16) {
            var5 = var16;
            throw var16;
        } finally {
            if (jedis != null) {
                if (var5 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var15) {
                        var5.addSuppressed(var15);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var6;
    }

    @Override
    public Long ttl(String key) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;

        long var4;
        try {
            var4 = jedis.ttl(key);
        } catch (Throwable var14) {
            var3 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var3.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var4;
    }

    @Override
    public Long pttl(String key) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;

        long var4;
        try {
            var4 = jedis.pttl(key);
        } catch (Throwable var14) {
            var3 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var3.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var4;
    }

    @Override
    public Long touch(String key) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;

        long var4;
        try {
            var4 = jedis.touch(key);
        } catch (Throwable var14) {
            var3 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var3.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var4;
    }

    @Override
    public Long touch(String... keys) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;

        long var4;
        try {
            var4 = jedis.touch(keys);
        } catch (Throwable var14) {
            var3 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var3.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var4;
    }

    @Override
    public Long del(String key) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;

        long var4;
        try {
            var4 = jedis.del(key);
        } catch (Throwable var14) {
            var3 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var3.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var4;
    }

    @Override
    public Long del(String... keys) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;

        long var4;
        try {
            var4 = jedis.del(keys);
        } catch (Throwable var14) {
            var3 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var3.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var4;
    }

    @Override
    public String lmove(String srcKey, String dstKey, ListDirection from, ListDirection to) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        String result;
        try {
            result = jedis.lmove(srcKey, dstKey, from, to);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public String blmove(String srcKey, String dstKey, ListDirection from, ListDirection to, double timeout) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        String result;
        try {
            result = jedis.blmove(srcKey, dstKey, from, to, timeout);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public Long unlink(String key) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;

        long var4;
        try {
            var4 = jedis.unlink(key);
        } catch (Throwable var14) {
            var3 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var3.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var4;
    }

    @Override
    public Long unlink(String... keys) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;

        long var4;
        try {
            var4 = jedis.unlink(keys);
        } catch (Throwable var14) {
            var3 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var3.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var4;
    }

    @Override
    public String echo(String string) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        String result;
        try {
            result = jedis.echo(string);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public Long memoryUsage(String key) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;

        Long var4;
        try {
            var4 = jedis.memoryUsage(key);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var4;
    }

    @Override
    public Long memoryUsage(String key, int samples) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var4 = null;

        Long var5;
        try {
            var5 = jedis.memoryUsage(key, samples);
        } catch (Throwable var14) {
            var4 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var4.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var5;
    }

    @Override
    public StreamEntryID xadd(String key, StreamEntryID id, Map<String, String> hash) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        StreamEntryID result;
        try {
            result = jedis.xadd(key, id, hash);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public StreamEntryID xadd(String key, StreamEntryID id, Map<String, String> hash, long maxLen, boolean approximateLength) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        StreamEntryID result;
        try {
            result = jedis.xadd(key, id, hash, maxLen, approximateLength);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public StreamEntryID xadd(String key, Map<String, String> hash, XAddParams params) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        StreamEntryID result;
        try {
            result = jedis.xadd(key, hash, params);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public Long xlen(String key) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        Long result;
        try {
            result = jedis.xlen(key);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public List<StreamEntry> xrange(String key, StreamEntryID start, StreamEntryID end) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        List<StreamEntry> result;
        try {
            result = jedis.xrange(key, start, end);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public List<StreamEntry> xrange(String key, StreamEntryID start, StreamEntryID end, int count) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        List<StreamEntry> result;
        try {
            result = jedis.xrange(key, start, end, count);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public List<StreamEntry> xrevrange(String key, StreamEntryID end, StreamEntryID start) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        List<StreamEntry> result;
        try {
            result = jedis.xrevrange(key, start, end);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public List<StreamEntry> xrevrange(String key, StreamEntryID end, StreamEntryID start, int count) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        List<StreamEntry> result;
        try {
            result = jedis.xrevrange(key, start, end, count);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public List<Map.Entry<String, List<StreamEntry>>> xread(int count, long block, Map.Entry<String, StreamEntryID>... streams) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        List<Map.Entry<String, List<StreamEntry>>> result;
        try {
            result = jedis.xread(count, block, streams);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public List<Map.Entry<String, List<StreamEntry>>> xread(XReadParams xReadParams, Map<String, StreamEntryID> streams) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        List<Map.Entry<String, List<StreamEntry>>> result;
        try {
            result = jedis.xread(xReadParams, streams);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public Long xack(String key, String group, StreamEntryID... ids) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        Long result;
        try {
            result = jedis.xack(key, group, ids);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public String xgroupCreate(String key, String groupname, StreamEntryID id, boolean makeStream) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        String result;
        try {
            result = jedis.xgroupCreate(key, groupname, id, makeStream);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public String xgroupSetID(String key, String groupname, StreamEntryID id) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        String result;
        try {
            result = jedis.xgroupSetID(key, groupname, id);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public Long xgroupDestroy(String key, String groupname) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        Long result;
        try {
            result = jedis.xgroupDestroy(key, groupname);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public Long xgroupDelConsumer(String key, String groupname, String consumername) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        Long result;
        try {
            result = jedis.xgroupDelConsumer(key, groupname, consumername);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public List<Map.Entry<String, List<StreamEntry>>> xreadGroup(String groupname, String consumer, int count, long block, boolean noAck, Map.Entry<String, StreamEntryID>... streams) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        List<Map.Entry<String, List<StreamEntry>>> result;
        try {
            result = jedis.xreadGroup(groupname, consumer, count, block, noAck, streams);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public List<Map.Entry<String, List<StreamEntry>>> xreadGroup(String groupname, String consumer, XReadGroupParams xReadGroupParams, Map<String, StreamEntryID> streams) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        List<Map.Entry<String, List<StreamEntry>>> result;
        try {
            result = jedis.xreadGroup(groupname, consumer, xReadGroupParams, streams);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public StreamPendingSummary xpending(String key, String groupname) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        StreamPendingSummary result;
        try {
            result = jedis.xpending(key, groupname);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public List<StreamPendingEntry> xpending(String key, String groupname, StreamEntryID start, StreamEntryID end, int count, String consumername) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        List<StreamPendingEntry> result;
        try {
            result = jedis.xpending(key, groupname, start, end, count, consumername);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public List<StreamPendingEntry> xpending(String key, String groupname, XPendingParams params) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        List<StreamPendingEntry> result;
        try {
            result = jedis.xpending(key, groupname, params);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public Long xdel(String key, StreamEntryID... ids) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        Long result;
        try {
            result = jedis.xdel(key, ids);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public Long xtrim(String key, long maxLen, boolean approximateLength) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        Long result;
        try {
            result = jedis.xtrim(key, maxLen, approximateLength);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public Long xtrim(String key, XTrimParams params) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        Long result;
        try {
            result = jedis.xtrim(key, params);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public List<StreamEntry> xclaim(String key, String group, String consumername, long minIdleTime, long newIdleTime, int retries, boolean force, StreamEntryID... ids) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        List<StreamEntry> result;
        try {
            result = jedis.xclaim(key, group, consumername, minIdleTime, newIdleTime, retries, force, ids);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public List<StreamEntry> xclaim(String key, String group, String consumername, long minIdleTime, XClaimParams params, StreamEntryID... ids) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        List<StreamEntry> result;
        try {
            result = jedis.xclaim(key, group, consumername, minIdleTime, params, ids);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public List<StreamEntryID> xclaimJustId(String key, String group, String consumername, long minIdleTime, XClaimParams params, StreamEntryID... ids) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        List<StreamEntryID> result;
        try {
            result = jedis.xclaimJustId(key, group, consumername, minIdleTime, params, ids);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public Map.Entry<StreamEntryID, List<StreamEntry>> xautoclaim(String key, String group, String consumerName, long minIdleTime, StreamEntryID start, XAutoClaimParams params) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        Map.Entry<StreamEntryID, List<StreamEntry>> result;
        try {
            result = jedis.xautoclaim(key, group, consumerName, minIdleTime, start, params);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public Map.Entry<StreamEntryID, List<StreamEntryID>> xautoclaimJustId(String key, String group, String consumerName, long minIdleTime, StreamEntryID start, XAutoClaimParams params) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        Map.Entry<StreamEntryID, List<StreamEntryID>> result;
        try {
            result = jedis.xautoclaimJustId(key, group, consumerName, minIdleTime, start, params);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public Long waitReplicas(String key, int replicas, long timeout) {
        throw new RuntimeException("standLone no such method");
    }

    @Override
    public Object sendCommand(String sampleKey, ProtocolCommand cmd, String... args) {
        throw new RuntimeException("standLone no such method");
    }

    @Override
    public Object sendBlockingCommand(String sampleKey, ProtocolCommand cmd, String... args) {
        throw new RuntimeException("standLone no such method");
    }

    @Override
    public ScanResult<String> scan(String cursor, ScanParams params) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var4 = null;

        ScanResult var5;
        try {
            var5 = jedis.scan(cursor, params);
        } catch (Throwable var14) {
            var4 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var4.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var5;
    }

    @Override
    public ScanResult<String> scan(String cursor, ScanParams params, String type) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var5 = null;

        ScanResult var6;
        try {
            var6 = jedis.scan(cursor, params, type);
        } catch (Throwable var15) {
            var5 = var15;
            throw var15;
        } finally {
            if (jedis != null) {
                if (var5 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var14) {
                        var5.addSuppressed(var14);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var6;
    }

    @Override
    public long rpush(final String key, final long expireSeconds, final String... strings) {
        if (expireSeconds < 1L) {
            Jedis jedis = (Jedis) this.pool.getResource();
            Throwable var6 = null;

            long var7;
            try {
                var7 = jedis.rpush(key, strings);
            } catch (Throwable var17) {
                var6 = var17;
                throw var17;
            } finally {
                if (jedis != null) {
                    if (var6 != null) {
                        try {
                            jedis.close();
                        } catch (Throwable var16) {
                            var6.addSuppressed(var16);
                        }
                    } else {
                        jedis.close();
                    }
                }

            }

            return var7;
        } else {
            return (Long) this.pipeline(new BasePipelineTaskWithResult<Long>() {
                public Response<Long> exec(Pipeline p) {
                    Response<Long> incr = p.rpush(key, strings);
                    p.expire(key, expireSeconds);
                    return incr;
                }
            });
        }
    }

    @Override
    public long lpush(final String key, final long expireSeconds, final String... strings) {
        if (expireSeconds < 1L) {
            Jedis jedis = (Jedis) this.pool.getResource();
            Throwable var6 = null;

            long var7;
            try {
                var7 = jedis.lpush(key, strings);
            } catch (Throwable var17) {
                var6 = var17;
                throw var17;
            } finally {
                if (jedis != null) {
                    if (var6 != null) {
                        try {
                            jedis.close();
                        } catch (Throwable var16) {
                            var6.addSuppressed(var16);
                        }
                    } else {
                        jedis.close();
                    }
                }

            }

            return var7;
        } else {
            return (Long) this.pipeline(new BasePipelineTaskWithResult<Long>() {
                public Response<Long> exec(Pipeline p) {
                    Response<Long> incr = p.lpush(key, strings);
                    p.expire(key, expireSeconds);
                    return incr;
                }
            });
        }
    }

    @Override
    public Long llen(String key) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;

        long var4;
        try {
            var4 = jedis.llen(key);
        } catch (Throwable var14) {
            var3 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var3.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var4;
    }

    @Override
    public List<String> lrange(String key, long start, long stop) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var7 = null;

        List var8;
        try {
            var8 = jedis.lrange(key, start, stop);
        } catch (Throwable var17) {
            var7 = var17;
            throw var17;
        } finally {
            if (jedis != null) {
                if (var7 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var16) {
                        var7.addSuppressed(var16);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var8;
    }

    @Override
    public String ltrim(String key, long start, long stop) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var7 = null;

        String var8;
        try {
            var8 = jedis.ltrim(key, start, stop);
        } catch (Throwable var17) {
            var7 = var17;
            throw var17;
        } finally {
            if (jedis != null) {
                if (var7 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var16) {
                        var7.addSuppressed(var16);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var8;
    }

    @Override
    public String lindex(String key, long index) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var5 = null;

        String var6;
        try {
            var6 = jedis.lindex(key, index);
        } catch (Throwable var15) {
            var5 = var15;
            throw var15;
        } finally {
            if (jedis != null) {
                if (var5 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var14) {
                        var5.addSuppressed(var14);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var6;
    }

    @Override
    public String lset(String key, long index, String value) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var6 = null;

        String var7;
        try {
            var7 = jedis.lset(key, index, value);
        } catch (Throwable var16) {
            var6 = var16;
            throw var16;
        } finally {
            if (jedis != null) {
                if (var6 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var15) {
                        var6.addSuppressed(var15);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var7;
    }

    @Override
    public Long lrem(String key, long count, String value) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var6 = null;

        long var7;
        try {
            var7 = jedis.lrem(key, count, value);
        } catch (Throwable var17) {
            var6 = var17;
            throw var17;
        } finally {
            if (jedis != null) {
                if (var6 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var16) {
                        var6.addSuppressed(var16);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var7;
    }

    @Override
    public String lpop(String key) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;

        String var4;
        try {
            var4 = jedis.lpop(key);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var4;
    }

    @Override
    public List<String> lpop(final String key, final int count) {
        return this.pipeline(new BasePipelineTaskWithResultList<String>() {
            public List<Response<String>> exec(Pipeline p) {
                List<Response<String>> list = new ArrayList(count);

                for (int i = 0; i < count; ++i) {
                    list.add(p.lpop(key));
                }

                return list;
            }
        });
    }

    @Override
    public Long lpos(String key, String element) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        Long result;
        try {
            result = jedis.lpos(key, element);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public Long lpos(String key, String element, LPosParams params) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        Long result;
        try {
            result = jedis.lpos(key, element, params);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public List<Long> lpos(String key, String element, LPosParams params, long count) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        List<Long> result;
        try {
            result = jedis.lpos(key, element, params, count);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public String rpop(String key) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;

        String var4;
        try {
            var4 = jedis.rpop(key);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var4;
    }

    @Override
    public List<String> rpop(final String key, final int count) {
        return this.pipeline(new BasePipelineTaskWithResultList<String>() {
            public List<Response<String>> exec(Pipeline p) {
                List<Response<String>> list = new ArrayList(count);

                for (int i = 0; i < count; ++i) {
                    list.add(p.rpop(key));
                }

                return list;
            }
        });
    }

    @Override
    public Long sadd(String key, String... member) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        Long result;
        try {
            result = jedis.sadd(key, member);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public Long linsert(String key, ListPosition where, String pivot, String value) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var6 = null;

        long var7;
        try {
            var7 = jedis.linsert(key, where, pivot, value);
        } catch (Throwable var17) {
            var6 = var17;
            throw var17;
        } finally {
            if (jedis != null) {
                if (var6 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var16) {
                        var6.addSuppressed(var16);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var7;
    }

    @Override
    public Long lpushx(String key, String... string) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        Long result;
        try {
            result = jedis.lpushx(key, string);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public Long rpushx(String key, String... string) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        Long result;
        try {
            result = jedis.rpushx(key, string);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public long lpushx(final String key, final long expireSeconds, final String... strings) {
        if (expireSeconds < 1L) {
            Jedis jedis = (Jedis) this.pool.getResource();
            Throwable var6 = null;

            long var7;
            try {
                var7 = jedis.lpushx(key, strings);
            } catch (Throwable var17) {
                var6 = var17;
                throw var17;
            } finally {
                if (jedis != null) {
                    if (var6 != null) {
                        try {
                            jedis.close();
                        } catch (Throwable var16) {
                            var6.addSuppressed(var16);
                        }
                    } else {
                        jedis.close();
                    }
                }

            }

            return var7;
        } else {
            return (Long) this.pipeline(new BasePipelineTaskWithResult<Long>() {
                public Response<Long> exec(Pipeline p) {
                    Response<Long> lpushx = p.lpushx(key, strings);
                    p.expire(key, expireSeconds);
                    return lpushx;
                }
            });
        }
    }

    @Override
    public long rpushx(final String key, final long expireSeconds, final String... strings) {
        if (expireSeconds < 1L) {
            Jedis jedis = (Jedis) this.pool.getResource();
            Throwable var6 = null;

            long var7;
            try {
                var7 = jedis.rpushx(key, strings);
            } catch (Throwable var17) {
                var6 = var17;
                throw var17;
            } finally {
                if (jedis != null) {
                    if (var6 != null) {
                        try {
                            jedis.close();
                        } catch (Throwable var16) {
                            var6.addSuppressed(var16);
                        }
                    } else {
                        jedis.close();
                    }
                }

            }

            return var7;
        } else {
            return (Long) this.pipeline(new BasePipelineTaskWithResult<Long>() {
                public Response<Long> exec(Pipeline p) {
                    Response<Long> rpushx = p.rpushx(key, strings);
                    p.expire(key, expireSeconds);
                    return rpushx;
                }
            });
        }
    }

    @Override
    public List<String> blpop(int timeout, String... keys) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var4 = null;

        List var5;
        try {
            var5 = jedis.blpop(timeout, keys);
        } catch (Throwable var14) {
            var4 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var4.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var5;
    }

    @Override
    public KeyedListElement blpop(double timeout, String... keys) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        KeyedListElement result;
        try {
            result = jedis.blpop(timeout, keys);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public List<String> blpop(int timeout, String key) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var4 = null;

        List var5;
        try {
            var5 = jedis.blpop(timeout, key);
        } catch (Throwable var14) {
            var4 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var4.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var5;
    }

    @Override
    public KeyedListElement blpop(double timeout, String key) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        KeyedListElement result;
        try {
            result = jedis.blpop(timeout, key);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public List<String> brpop(int timeout, String... keys) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var4 = null;

        List var5;
        try {
            var5 = jedis.brpop(timeout, keys);
        } catch (Throwable var14) {
            var4 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var4.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var5;
    }

    @Override
    public KeyedListElement brpop(double timeout, String... keys) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        KeyedListElement result;
        try {
            result = jedis.brpop(timeout, keys);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public KeyedZSetElement bzpopmax(double timeout, String... keys) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        KeyedZSetElement result;
        try {
            result = jedis.bzpopmax(timeout, keys);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public KeyedZSetElement bzpopmin(double timeout, String... keys) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        KeyedZSetElement result;
        try {
            result = jedis.bzpopmin(timeout, keys);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public List<String> brpop(int timeout, String key) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var4 = null;

        List var5;
        try {
            var5 = jedis.brpop(timeout, key);
        } catch (Throwable var14) {
            var4 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var4.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var5;
    }

    @Override
    public KeyedListElement brpop(double timeout, String key) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        KeyedListElement result;
        try {
            result = jedis.brpop(timeout, key);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public boolean scriptExists(String sha1) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;

        boolean var4;
        try {
            var4 = jedis.scriptExists(sha1);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var4;
    }

    @Override
    public long sadd(final String key, final long expireSeconds, final String... members) {
        if (expireSeconds < 1L) {
            Jedis jedis = (Jedis) this.pool.getResource();
            Throwable var6 = null;

            long var7;
            try {
                var7 = jedis.sadd(key, members);
            } catch (Throwable var17) {
                var6 = var17;
                throw var17;
            } finally {
                if (jedis != null) {
                    if (var6 != null) {
                        try {
                            jedis.close();
                        } catch (Throwable var16) {
                            var6.addSuppressed(var16);
                        }
                    } else {
                        jedis.close();
                    }
                }

            }

            return var7;
        } else {
            return (Long) this.pipeline(new BasePipelineTaskWithResult<Long>() {
                public Response<Long> exec(Pipeline p) {
                    Response<Long> lpushx = p.sadd(key, members);
                    p.expire(key, expireSeconds);
                    return lpushx;
                }
            });
        }
    }

    @Override
    public Set<String> smembers(String key) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;

        Set var4;
        try {
            var4 = jedis.smembers(key);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var4;
    }

    @Override
    public Long srem(String key, String... members) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var4 = null;

        long var5;
        try {
            var5 = jedis.srem(key, members);
        } catch (Throwable var15) {
            var4 = var15;
            throw var15;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var14) {
                        var4.addSuppressed(var14);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var5;
    }

    @Override
    public String spop(String key) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;

        String var4;
        try {
            var4 = jedis.spop(key);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var4;
    }

    @Override
    public Set<String> spop(String key, long count) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var5 = null;

        Set var6;
        try {
            var6 = jedis.spop(key, count);
        } catch (Throwable var15) {
            var5 = var15;
            throw var15;
        } finally {
            if (jedis != null) {
                if (var5 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var14) {
                        var5.addSuppressed(var14);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var6;
    }

    @Override
    public Long scard(String key) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;

        long var4;
        try {
            var4 = jedis.scard(key);
        } catch (Throwable var14) {
            var3 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var3.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var4;
    }

    @Override
    public Boolean sismember(String key, String member) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var4 = null;

        boolean var5;
        try {
            var5 = jedis.sismember(key, member);
        } catch (Throwable var14) {
            var4 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var4.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var5;
    }

    @Override
    public List<Boolean> smismember(String key, String... members) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var4 = null;

        List var5;
        try {
            var5 = jedis.smismember(key, members);
        } catch (Throwable var14) {
            var4 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var4.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var5;
    }

    @Override
    public String srandmember(String key) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;

        String var4;
        try {
            var4 = jedis.srandmember(key);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var4;
    }

    @Override
    public List<String> srandmember(String key, int count) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var4 = null;

        List var5;
        try {
            var5 = jedis.srandmember(key, count);
        } catch (Throwable var14) {
            var4 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var4.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var5;
    }

    @Override
    public ScanResult<String> sscan(String key, String cursor) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var4 = null;

        ScanResult var5;
        try {
            var5 = jedis.sscan(key, cursor);
        } catch (Throwable var14) {
            var4 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var4.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var5;
    }

    @Override
    public ScanResult<String> sscan(String key, String cursor, ScanParams params) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var4 = null;

        ScanResult var5;
        try {
            var5 = jedis.sscan(key, cursor, params);
        } catch (Throwable var14) {
            var4 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var4.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var5;
    }

    @Override
    public long zadd(final String key, final long expireSeconds, final double score, final String member) {
        if (expireSeconds < 1L) {
            Jedis jedis = (Jedis) this.pool.getResource();
            Throwable var8 = null;

            long var9;
            try {
                var9 = jedis.zadd(key, score, member);
            } catch (Throwable var19) {
                var8 = var19;
                throw var19;
            } finally {
                if (jedis != null) {
                    if (var8 != null) {
                        try {
                            jedis.close();
                        } catch (Throwable var18) {
                            var8.addSuppressed(var18);
                        }
                    } else {
                        jedis.close();
                    }
                }

            }

            return var9;
        } else {
            return (Long) this.pipeline(new BasePipelineTaskWithResult<Long>() {
                public Response<Long> exec(Pipeline p) {
                    Response<Long> ret = p.zadd(key, score, member);
                    p.expire(key, expireSeconds);
                    return ret;
                }
            });
        }
    }

    @Override
    public long zadd(final String key, final long expireSeconds, final double score, final String member, final ZAddParams params) {
        if (expireSeconds < 1L) {
            Jedis jedis = (Jedis) this.pool.getResource();
            Throwable var9 = null;

            long var10;
            try {
                var10 = jedis.zadd(key, score, member, params);
            } catch (Throwable var20) {
                var9 = var20;
                throw var20;
            } finally {
                if (jedis != null) {
                    if (var9 != null) {
                        try {
                            jedis.close();
                        } catch (Throwable var19) {
                            var9.addSuppressed(var19);
                        }
                    } else {
                        jedis.close();
                    }
                }

            }

            return var10;
        } else {
            return (Long) this.pipeline(new BasePipelineTaskWithResult<Long>() {
                public Response<Long> exec(Pipeline p) {
                    Response<Long> ret = p.zadd(key, score, member, params);
                    p.expire(key, expireSeconds);
                    return ret;
                }
            });
        }
    }

    @Override
    public long zadd(final String key, final long expireSeconds, final Map<String, Double> scoreMembers) {
        if (expireSeconds < 1L) {
            Jedis jedis = (Jedis) this.pool.getResource();
            Throwable var6 = null;

            long var7;
            try {
                var7 = jedis.zadd(key, scoreMembers);
            } catch (Throwable var17) {
                var6 = var17;
                throw var17;
            } finally {
                if (jedis != null) {
                    if (var6 != null) {
                        try {
                            jedis.close();
                        } catch (Throwable var16) {
                            var6.addSuppressed(var16);
                        }
                    } else {
                        jedis.close();
                    }
                }

            }

            return var7;
        } else {
            return (Long) this.pipeline(new BasePipelineTaskWithResult<Long>() {
                public Response<Long> exec(Pipeline p) {
                    Response<Long> ret = p.zadd(key, scoreMembers);
                    p.expire(key, expireSeconds);
                    return ret;
                }
            });
        }
    }

    @Override
    public long zadd(final String key, final long expireSeconds, final Map<String, Double> scoreMembers, final ZAddParams params) {
        if (expireSeconds < 1L) {
            Jedis jedis = (Jedis) this.pool.getResource();
            Throwable var7 = null;

            long var8;
            try {
                var8 = jedis.zadd(key, scoreMembers, params);
            } catch (Throwable var18) {
                var7 = var18;
                throw var18;
            } finally {
                if (jedis != null) {
                    if (var7 != null) {
                        try {
                            jedis.close();
                        } catch (Throwable var17) {
                            var7.addSuppressed(var17);
                        }
                    } else {
                        jedis.close();
                    }
                }

            }

            return var8;
        } else {
            return (Long) this.pipeline(new BasePipelineTaskWithResult<Long>() {
                public Response<Long> exec(Pipeline p) {
                    Response<Long> ret = p.zadd(key, scoreMembers, params);
                    p.expire(key, expireSeconds);
                    return ret;
                }
            });
        }
    }

    @Override
    public Long zrem(String key, String... members) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var4 = null;

        long var5;
        try {
            var5 = jedis.zrem(key, members);
        } catch (Throwable var15) {
            var4 = var15;
            throw var15;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var14) {
                        var4.addSuppressed(var14);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var5;
    }

    @Override
    public Double zincrby(String key, double increment, String member) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var6 = null;

        double var7;
        try {
            var7 = jedis.zincrby(key, increment, member);
        } catch (Throwable var17) {
            var6 = var17;
            throw var17;
        } finally {
            if (jedis != null) {
                if (var6 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var16) {
                        var6.addSuppressed(var16);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var7;
    }

    @Override
    public Double zincrby(String key, double increment, String member, ZIncrByParams params) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var7 = null;

        Double var8;
        try {
            var8 = jedis.zincrby(key, increment, member, params);
        } catch (Throwable var17) {
            var7 = var17;
            throw var17;
        } finally {
            if (jedis != null) {
                if (var7 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var16) {
                        var7.addSuppressed(var16);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var8;
    }

    @Override
    public Long zrank(String key, String member) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var4 = null;

        Long var5;
        try {
            var5 = jedis.zrank(key, member);
        } catch (Throwable var14) {
            var4 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var4.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var5;
    }

    @Override
    public Long zrevrank(String key, String member) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var4 = null;

        Long var5;
        try {
            var5 = jedis.zrevrank(key, member);
        } catch (Throwable var14) {
            var4 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var4.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var5;
    }

    @Override
    public Set<String> zrange(String key, long start, long stop) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var7 = null;

        Set var8;
        try {
            var8 = jedis.zrange(key, start, stop);
        } catch (Throwable var17) {
            var7 = var17;
            throw var17;
        } finally {
            if (jedis != null) {
                if (var7 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var16) {
                        var7.addSuppressed(var16);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var8;
    }

    @Override
    public Set<String> zrevrange(String key, long start, long stop) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var7 = null;

        Set var8;
        try {
            var8 = jedis.zrevrange(key, start, stop);
        } catch (Throwable var17) {
            var7 = var17;
            throw var17;
        } finally {
            if (jedis != null) {
                if (var7 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var16) {
                        var7.addSuppressed(var16);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var8;
    }

    @Override
    public Set<Tuple> zrangeWithScores(String key, long start, long stop) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var7 = null;

        Set var8;
        try {
            var8 = jedis.zrangeWithScores(key, start, stop);
        } catch (Throwable var17) {
            var7 = var17;
            throw var17;
        } finally {
            if (jedis != null) {
                if (var7 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var16) {
                        var7.addSuppressed(var16);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var8;
    }

    @Override
    public Set<Tuple> zrevrangeWithScores(String key, long start, long stop) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var7 = null;

        Set var8;
        try {
            var8 = jedis.zrevrangeWithScores(key, start, stop);
        } catch (Throwable var17) {
            var7 = var17;
            throw var17;
        } finally {
            if (jedis != null) {
                if (var7 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var16) {
                        var7.addSuppressed(var16);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var8;
    }

    @Override
    public String zrandmember(String key) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;

        String var4;
        try {
            var4 = jedis.zrandmember(key);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var4;
    }

    @Override
    public Set<String> zrandmember(String key, long count) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var5 = null;

        Set var6;
        try {
            var6 = jedis.zrandmember(key, count);
        } catch (Throwable var15) {
            var5 = var15;
            throw var15;
        } finally {
            if (jedis != null) {
                if (var5 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var14) {
                        var5.addSuppressed(var14);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var6;
    }

    @Override
    public Set<Tuple> zrandmemberWithScores(String key, long count) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var5 = null;

        Set var6;
        try {
            var6 = jedis.zrandmemberWithScores(key, count);
        } catch (Throwable var15) {
            var5 = var15;
            throw var15;
        } finally {
            if (jedis != null) {
                if (var5 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var14) {
                        var5.addSuppressed(var14);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var6;
    }

    @Override
    public Long zcard(String key) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;

        long var4;
        try {
            var4 = jedis.zcard(key);
        } catch (Throwable var14) {
            var3 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var3.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var4;
    }

    @Override
    public Double zscore(String key, String member) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var4 = null;

        Double var5;
        try {
            var5 = jedis.zscore(key, member);
        } catch (Throwable var14) {
            var4 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var4.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var5;
    }

    @Override
    public List<Double> zmscore(String key, String... members) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        List<Double> result;
        try {
            result = jedis.zmscore(key, members);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public Tuple zpopmax(String key) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        Tuple result;
        try {
            result = jedis.zpopmax(key);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public Set<Tuple> zpopmax(String key, int count) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        Set<Tuple> result;
        try {
            result = jedis.zpopmax(key, count);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public Tuple zpopmin(String key) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        Tuple result;
        try {
            result = jedis.zpopmin(key);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public Set<Tuple> zpopmin(String key, int count) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        Set<Tuple> result;
        try {
            result = jedis.zpopmin(key, count);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public List<String> sort(String key) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        List<String> result;
        try {
            result = jedis.sort(key);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public List<String> sort(String key, SortingParams sortingParameters) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        List<String> result;
        try {
            result = jedis.sort(key, sortingParameters);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (jedis != null) {
                if (var3 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    jedis.close();
                }
            }
        }
        return result;
    }

    @Override
    public Long zcount(String key, double min, double max) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var7 = null;

        long var8;
        try {
            var8 = jedis.zcount(key, min, max);
        } catch (Throwable var18) {
            var7 = var18;
            throw var18;
        } finally {
            if (jedis != null) {
                if (var7 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var17) {
                        var7.addSuppressed(var17);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var8;
    }

    @Override
    public Long zcount(String key, String min, String max) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var5 = null;

        long var6;
        try {
            var6 = jedis.zcount(key, min, max);
        } catch (Throwable var16) {
            var5 = var16;
            throw var16;
        } finally {
            if (jedis != null) {
                if (var5 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var15) {
                        var5.addSuppressed(var15);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var6;

    }

    @Override
    public Set<String> zrangeByScore(String key, double min, double max) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var7 = null;

        Set var8;
        try {
            var8 = jedis.zrangeByScore(key, min, max);
        } catch (Throwable var17) {
            var7 = var17;
            throw var17;
        } finally {
            if (jedis != null) {
                if (var7 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var16) {
                        var7.addSuppressed(var16);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var8;
    }

    @Override
    public Set<String> zrangeByScore(String key, String min, String max) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var5 = null;

        Set var6;
        try {
            var6 = jedis.zrangeByScore(key, min, max);
        } catch (Throwable var15) {
            var5 = var15;
            throw var15;
        } finally {
            if (jedis != null) {
                if (var5 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var14) {
                        var5.addSuppressed(var14);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var6;
    }

    @Override
    public Set<String> zrevrangeByScore(String key, double max, double min) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var7 = null;

        Set var8;
        try {
            var8 = jedis.zrangeByScore(key, max, min);
        } catch (Throwable var17) {
            var7 = var17;
            throw var17;
        } finally {
            if (jedis != null) {
                if (var7 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var16) {
                        var7.addSuppressed(var16);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var8;
    }

    @Override
    public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var9 = null;

        Set var10;
        try {
            var10 = jedis.zrangeByScore(key, max, min, offset, count);
        } catch (Throwable var19) {
            var9 = var19;
            throw var19;
        } finally {
            if (jedis != null) {
                if (var9 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var18) {
                        var9.addSuppressed(var18);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var10;
    }

    @Override
    public Set<String> zrevrangeByScore(String key, String max, String min) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var5 = null;

        Set var6;
        try {
            var6 = jedis.zrevrangeByScore(key, max, min);
        } catch (Throwable var15) {
            var5 = var15;
            throw var15;
        } finally {
            if (jedis != null) {
                if (var5 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var14) {
                        var5.addSuppressed(var14);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var6;
    }

    @Override
    public Set<String> zrangeByScore(String key, String min, String max, int offset, int count) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var7 = null;

        Set var8;
        try {
            var8 = jedis.zrevrangeByScore(key, min, max, offset, count);
        } catch (Throwable var17) {
            var7 = var17;
            throw var17;
        } finally {
            if (jedis != null) {
                if (var7 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var16) {
                        var7.addSuppressed(var16);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var8;
    }

    @Override
    public Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var9 = null;

        Set var10;
        try {
            var10 = jedis.zrevrangeByScore(key, max, min, offset, count);
        } catch (Throwable var19) {
            var9 = var19;
            throw var19;
        } finally {
            if (jedis != null) {
                if (var9 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var18) {
                        var9.addSuppressed(var18);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var10;
    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var7 = null;

        Set var8;
        try {
            var8 = jedis.zrangeByScoreWithScores(key, min, max);
        } catch (Throwable var17) {
            var7 = var17;
            throw var17;
        } finally {
            if (jedis != null) {
                if (var7 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var16) {
                        var7.addSuppressed(var16);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var8;
    }

    @Override
    public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var7 = null;

        Set var8;
        try {
            var8 = jedis.zrevrangeByScoreWithScores(key, max, min);
        } catch (Throwable var17) {
            var7 = var17;
            throw var17;
        } finally {
            if (jedis != null) {
                if (var7 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var16) {
                        var7.addSuppressed(var16);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var8;
    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var9 = null;

        Set var10;
        try {
            var10 = jedis.zrangeByScoreWithScores(key, min, max, offset, count);
        } catch (Throwable var19) {
            var9 = var19;
            throw var19;
        } finally {
            if (jedis != null) {
                if (var9 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var18) {
                        var9.addSuppressed(var18);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var10;
    }

    @Override
    public Set<String> zrevrangeByScore(String key, String max, String min, int offset, int count) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var7 = null;

        Set var8;
        try {
            var8 = jedis.zrevrangeByScore(key, min, max, offset, count);
        } catch (Throwable var17) {
            var7 = var17;
            throw var17;
        } finally {
            if (jedis != null) {
                if (var7 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var16) {
                        var7.addSuppressed(var16);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var8;
    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var5 = null;

        Set var6;
        try {
            var6 = jedis.zrangeByScoreWithScores(key, min, max);
        } catch (Throwable var15) {
            var5 = var15;
            throw var15;
        } finally {
            if (jedis != null) {
                if (var5 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var14) {
                        var5.addSuppressed(var14);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var6;
    }

    @Override
    public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var5 = null;

        Set var6;
        try {
            var6 = jedis.zrangeByScoreWithScores(key, max, min);
        } catch (Throwable var15) {
            var5 = var15;
            throw var15;
        } finally {
            if (jedis != null) {
                if (var5 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var14) {
                        var5.addSuppressed(var14);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var6;
    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max, int offset, int count) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var7 = null;

        Set var8;
        try {
            var8 = jedis.zrangeByScoreWithScores(key, max, min, offset, count);
        } catch (Throwable var17) {
            var7 = var17;
            throw var17;
        } finally {
            if (jedis != null) {
                if (var7 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var16) {
                        var7.addSuppressed(var16);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var8;
    }

    @Override
    public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var9 = null;

        Set var10;
        try {
            var10 = jedis.zrevrangeByScoreWithScores(key, max, min, offset, count);
        } catch (Throwable var19) {
            var9 = var19;
            throw var19;
        } finally {
            if (jedis != null) {
                if (var9 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var18) {
                        var9.addSuppressed(var18);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var10;
    }

    @Override
    public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min, int offset, int count) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var7 = null;

        Set var8;
        try {
            var8 = jedis.zrevrangeByScoreWithScores(key, max, min, offset, count);
        } catch (Throwable var17) {
            var7 = var17;
            throw var17;
        } finally {
            if (jedis != null) {
                if (var7 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var16) {
                        var7.addSuppressed(var16);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var8;
    }

    @Override
    public Long zremrangeByRank(String key, long start, long stop) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var7 = null;

        long var8;
        try {
            var8 = jedis.zremrangeByRank(key, start, stop);
        } catch (Throwable var18) {
            var7 = var18;
            throw var18;
        } finally {
            if (jedis != null) {
                if (var7 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var17) {
                        var7.addSuppressed(var17);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var8;
    }

    @Override
    public Long zremrangeByScore(String key, double min, double max) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var7 = null;

        long var8;
        try {
            var8 = jedis.zremrangeByScore(key, min, max);
        } catch (Throwable var18) {
            var7 = var18;
            throw var18;
        } finally {
            if (jedis != null) {
                if (var7 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var17) {
                        var7.addSuppressed(var17);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var8;
    }

    @Override
    public Long zremrangeByScore(String key, String min, String max) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var5 = null;

        long var6;
        try {
            var6 = jedis.zremrangeByScore(key, min, max);
        } catch (Throwable var16) {
            var5 = var16;
            throw var16;
        } finally {
            if (jedis != null) {
                if (var5 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var15) {
                        var5.addSuppressed(var15);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var6;
    }

    @Override
    public Long zlexcount(String key, String min, String max) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var5 = null;

        long var6;
        try {
            var6 = jedis.zlexcount(key, min, max);
        } catch (Throwable var16) {
            var5 = var16;
            throw var16;
        } finally {
            if (jedis != null) {
                if (var5 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var15) {
                        var5.addSuppressed(var15);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var6;
    }

    @Override
    public Set<String> zrangeByLex(String key, String min, String max) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var5 = null;

        Set var6;
        try {
            var6 = jedis.zrangeByLex(key, min, max);
        } catch (Throwable var15) {
            var5 = var15;
            throw var15;
        } finally {
            if (jedis != null) {
                if (var5 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var14) {
                        var5.addSuppressed(var14);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var6;
    }

    @Override
    public Set<String> zrangeByLex(String key, String min, String max, int offset, int count) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var7 = null;

        Set var8;
        try {
            var8 = jedis.zrangeByLex(key, min, max, offset, count);
        } catch (Throwable var17) {
            var7 = var17;
            throw var17;
        } finally {
            if (jedis != null) {
                if (var7 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var16) {
                        var7.addSuppressed(var16);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var8;
    }

    @Override
    public Set<String> zrevrangeByLex(String key, String max, String min) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var5 = null;

        Set var6;
        try {
            var6 = jedis.zrevrangeByLex(key, max, min);
        } catch (Throwable var15) {
            var5 = var15;
            throw var15;
        } finally {
            if (jedis != null) {
                if (var5 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var14) {
                        var5.addSuppressed(var14);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var6;
    }

    @Override
    public Set<String> zrevrangeByLex(String key, String max, String min, int offset, int count) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var7 = null;

        Set var8;
        try {
            var8 = jedis.zrevrangeByLex(key, max, min, offset, count);
        } catch (Throwable var17) {
            var7 = var17;
            throw var17;
        } finally {
            if (jedis != null) {
                if (var7 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var16) {
                        var7.addSuppressed(var16);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var8;
    }

    @Override
    public Long zremrangeByLex(String key, String min, String max) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var5 = null;

        long var6;
        try {
            var6 = jedis.zremrangeByLex(key, max, min);
        } catch (Throwable var16) {
            var5 = var16;
            throw var16;
        } finally {
            if (jedis != null) {
                if (var5 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var15) {
                        var5.addSuppressed(var15);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var6;
    }

    @Override
    public ScanResult<Tuple> zscan(String key, String cursor) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var4 = null;

        ScanResult var5;
        try {
            var5 = jedis.zscan(key, cursor);
        } catch (Throwable var14) {
            var4 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var4.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var5;
    }

    @Override
    public ScanResult<Tuple> zscan(String key, String cursor, ScanParams params) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var4 = null;

        ScanResult var5;
        try {
            var5 = jedis.zscan(key, cursor, params);
        } catch (Throwable var14) {
            var4 = var14;
            throw var14;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var13) {
                        var4.addSuppressed(var13);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var5;
    }

    @Override
    public <T> List<T> pipeline(String sampleKey, BasePipelineTaskWithResultList<T> p) {
        return this.pipeline(p);
    }

    @Override
    public <T> Map<String, T> pipeline(String sampleKey, BasePipelineTaskWithResultMap<T> p) {
        return this.pipeline(p);
    }

    @Override
    public <T> T pipeline(String sampleKey, BasePipelineTaskWithResult<T> p) {
        return this.pipeline(p);
    }

    @Override
    public void pipeline(String sampleKey, BasePipelineTaskWithoutResult p) {
        this.pipeline(p);
    }

    @Override
    public <T> List<T> pipeline(BasePipelineTaskWithResultList<T> p) {
        Jedis connection = (Jedis) this.pool.getResource();
        Throwable var3 = null;

        List var4;
        try {
            var4 = p.run(connection.pipelined());
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (connection != null) {
                if (var3 != null) {
                    try {
                        connection.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    connection.close();
                }
            }

        }

        return var4;
    }

    @Override
    public <T> Map<String, T> pipeline(BasePipelineTaskWithResultMap<T> p) {
        Jedis connection = (Jedis) this.pool.getResource();
        Throwable var3 = null;

        Map var4;
        try {
            var4 = p.run(connection.pipelined());
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (connection != null) {
                if (var3 != null) {
                    try {
                        connection.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    connection.close();
                }
            }

        }

        return var4;
    }

    @Override
    public <T> T pipeline(BasePipelineTaskWithResult<T> p) {
        Jedis connection = (Jedis) this.pool.getResource();
        Throwable var3 = null;

        T var4;
        try {
            var4 = p.run(connection.pipelined());
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (connection != null) {
                if (var3 != null) {
                    try {
                        connection.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    connection.close();
                }
            }

        }
        return var4;
    }

    @Override
    public void pipeline(BasePipelineTaskWithoutResult p) {
        Jedis connection = (Jedis) this.pool.getResource();
        Throwable var3 = null;

        try {
            p.run(connection.pipelined());
        } catch (Throwable var12) {
            var3 = var12;
            throw var12;
        } finally {
            if (connection != null) {
                if (var3 != null) {
                    try {
                        connection.close();
                    } catch (Throwable var11) {
                        var3.addSuppressed(var11);
                    }
                } else {
                    connection.close();
                }
            }

        }

    }

    @Override
    public Boolean copy(String srcKey, String dstKey, boolean replace) {
        Jedis connection = (Jedis) this.pool.getResource();
        Throwable var3 = null;
        try {
            return connection.copy(srcKey, dstKey, replace);
        } catch (Throwable var12) {
            var3 = var12;
            throw var12;
        } finally {
            if (connection != null) {
                if (var3 != null) {
                    try {
                        connection.close();
                    } catch (Throwable var11) {
                        var3.addSuppressed(var11);
                    }
                } else {
                    connection.close();
                }
            }
        }
    }

    @Override
    public String set(String key, String value) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var5 = null;
        String var6;
        try {
            var6 = jedis.set(key, value);
        } catch (Throwable var15) {
            var5 = var15;
            throw var15;
        } finally {
            if (jedis != null) {
                if (var5 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var14) {
                        var5.addSuppressed(var14);
                    }
                } else {
                    jedis.close();
                }
            }

        }
        return var6;
    }

    @Override
    public <T extends Pool<Jedis>> Map<String, Pool<Jedis>> getNodes() {
        return ImmutableMap.of("single", this.pool);
    }

    @Override
    public Object evalsha(String sha1) {
        Jedis connection = (Jedis) this.pool.getResource();
        Throwable var3 = null;

        Object var4;
        try {
            var4 = connection.evalsha(sha1);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (connection != null) {
                if (var3 != null) {
                    try {
                        connection.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    connection.close();
                }
            }

        }

        return var4;
    }

    @Override
    public Object evalsha(String sha1, int keyCount, String... params) {
        Jedis connection = (Jedis) this.pool.getResource();
        Throwable var5 = null;

        Object var6;
        try {
            var6 = connection.evalsha(sha1, keyCount, params);
        } catch (Throwable var15) {
            var5 = var15;
            throw var15;
        } finally {
            if (connection != null) {
                if (var5 != null) {
                    try {
                        connection.close();
                    } catch (Throwable var14) {
                        var5.addSuppressed(var14);
                    }
                } else {
                    connection.close();
                }
            }

        }

        return var6;
    }

    @Override
    public Object evalsha(String sha1, List<String> keys, List<String> args) {
        Jedis connection = (Jedis) this.pool.getResource();
        Throwable var5 = null;

        Object var6;
        try {
            var6 = connection.evalsha(sha1, keys, args);
        } catch (Throwable var15) {
            var5 = var15;
            throw var15;
        } finally {
            if (connection != null) {
                if (var5 != null) {
                    try {
                        connection.close();
                    } catch (Throwable var14) {
                        var5.addSuppressed(var14);
                    }
                } else {
                    connection.close();
                }
            }

        }

        return var6;
    }

    @Override
    public Object evalsha(String sha1, String sampleKey) {
        throw new RuntimeException("standLone no such method");
    }

    @Override
    public Boolean scriptExists(String sha1, String sampleKey) {
        throw new RuntimeException("standLone no such method");
    }

    @Override
    public List<Boolean> scriptExists(String sampleKey, String... sha1) {
        throw new RuntimeException("standLone no such method");
    }

    @Override
    public String scriptLoad(String script, String sampleKey) {
        throw new RuntimeException("standLone no such method");
    }

    @Override
    public String scriptFlush(String sampleKey) {
        throw new RuntimeException("standLone no such method");
    }

    @Override
    public String scriptKill(String sampleKey) {
        throw new RuntimeException("standLone no such method");
    }


    @Override
    public String getWithDefault(String key, String defaultValue) {
        String ret = null;

        try {
            ret = this.get(key);
            if (ret == null) {
                ret = defaultValue;
            }

            return ret;
        } catch (Exception var5) {
            return defaultValue;
        }
    }

    @Override
    public <T> T getObjectWithDefault(String key, Class<T> clazz, T defaultValue) {
        T ret = null;

        try {
            String json = this.get(key);
            if (json != null) {
                ret = this.deserialize(json, clazz);
            } else {
                ret = defaultValue;
            }

            return ret;
        } catch (Exception var6) {
            return defaultValue;
        }
    }

    @Override
    public String hgetWithDefault(String key, String field, String defaultValue) {
        String ret = null;

        try {
            ret = this.hget(key, field);
            if (ret == null) {
                ret = defaultValue;
            }

            return ret;
        } catch (Exception var6) {
            return defaultValue;
        }
    }

    @Override
    public <T> T hgetObjectWithDefault(String key, String field, Class<T> clazz, T defaultValue) {
        T ret = null;

        try {
            String json = this.hget(key, field);
            if (json != null) {
                ret = this.deserialize(json, clazz);
            } else {
                ret = defaultValue;
            }

            return ret;
        } catch (Exception var7) {
            return defaultValue;
        }
    }

    @Override
    public String setObject(String key, Serializable objValue, SetParams params) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var5 = null;

        String var7;
        try {
            String value = this.serialize(objValue);
            var7 = jedis.set(key, value, params);
        } catch (Throwable var16) {
            var5 = var16;
            throw var16;
        } finally {
            if (jedis != null) {
                if (var5 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var15) {
                        var5.addSuppressed(var15);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var7;
    }

    @Override
    public <T> T getObject(String key, Class<T> clazz) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var4 = null;

        T var6;
        try {
            String json = jedis.get(key);
            var6 = this.deserialize(json, clazz);
        } catch (Throwable var15) {
            var4 = var15;
            throw var15;
        } finally {
            if (jedis != null) {
                if (var4 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var14) {
                        var4.addSuppressed(var14);
                    }
                } else {
                    jedis.close();
                }
            }

        }
        return var6;
    }

    @Override
    public long setnxObject(final String key, final long second, final Serializable objValue) {
        return (Long) this.pipeline(new BasePipelineTaskWithResult<Long>() {
            public Response<Long> exec(Pipeline p) {
                String value = RedisClientImpl.this.serialize(objValue);
                Response<Long> setnx = p.setnx(key, value);
                p.expire(key, second);
                return setnx;
            }
        });
    }

    @Override
    public String setexObject(String key, long seconds, Serializable objValue) {
        Jedis jedis;
        Throwable var6;
        String value;
        String var8;
        if (seconds < 1L) {
            jedis = (Jedis) this.pool.getResource();
            var6 = null;

            try {
                value = this.serialize(objValue);
                var8 = jedis.set(key, value);
            } catch (Throwable var31) {
                var6 = var31;
                throw var31;
            } finally {
                if (jedis != null) {
                    if (var6 != null) {
                        try {
                            jedis.close();
                        } catch (Throwable var30) {
                            var6.addSuppressed(var30);
                        }
                    } else {
                        jedis.close();
                    }
                }

            }

            return var8;
        } else {
            jedis = (Jedis) this.pool.getResource();
            var6 = null;

            try {
                value = this.serialize(objValue);
                var8 = jedis.setex(key, seconds, value);
            } catch (Throwable var32) {
                var6 = var32;
                throw var32;
            } finally {
                if (jedis != null) {
                    if (var6 != null) {
                        try {
                            jedis.close();
                        } catch (Throwable var29) {
                            var6.addSuppressed(var29);
                        }
                    } else {
                        jedis.close();
                    }
                }

            }

            return var8;
        }
    }

    @Override
    public String psetexObject(String key, long milliseconds, Serializable objValue) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var6 = null;

        String var8;
        try {
            String value = this.serialize(objValue);
            var8 = jedis.psetex(key, milliseconds, value);
        } catch (Throwable var17) {
            var6 = var17;
            throw var17;
        } finally {
            if (jedis != null) {
                if (var6 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var16) {
                        var6.addSuppressed(var16);
                    }
                } else {
                    jedis.close();
                }
            }

        }

        return var8;
    }

    @Override
    public long hsetObject(final String key, final long expireSeconds, final String field, final Serializable objValue) {
        if (expireSeconds < 1L) {
            Jedis jedis = (Jedis) this.pool.getResource();
            Throwable var7 = null;

            long var9;
            try {
                String value = this.serialize(objValue);
                var9 = jedis.hset(key, field, value);
            } catch (Throwable var19) {
                var7 = var19;
                throw var19;
            } finally {
                if (jedis != null) {
                    if (var7 != null) {
                        try {
                            jedis.close();
                        } catch (Throwable var18) {
                            var7.addSuppressed(var18);
                        }
                    } else {
                        jedis.close();
                    }
                }

            }

            return var9;
        } else {
            return (Long) this.pipeline(new BasePipelineTaskWithResult<Long>() {
                public Response<Long> exec(Pipeline p) {
                    String value = RedisClientImpl.this.serialize(objValue);
                    Response<Long> incr = p.hset(key, field, value);
                    p.expire(key, expireSeconds);
                    return incr;
                }
            });
        }
    }

    @Override
    public <T> T hgetObject(String key, String field, Class<T> clazz) {
        Jedis jedis = (Jedis) this.pool.getResource();
        Throwable var5 = null;

        T var7;
        try {
            String json = jedis.hget(key, field);
            var7 = this.deserialize(json, clazz);
        } catch (Throwable var16) {
            var5 = var16;
            throw var16;
        } finally {
            if (jedis != null) {
                if (var5 != null) {
                    try {
                        jedis.close();
                    } catch (Throwable var15) {
                        var5.addSuppressed(var15);
                    }
                } else {
                    jedis.close();
                }
            }

        }
        return var7;
    }

    @Override
    public long hsetnxObject(final String key, final long expireSeconds, final String field, final Serializable objValue) {
        if (expireSeconds < 1L) {
            Jedis jedis = (Jedis) this.pool.getResource();
            Throwable var7 = null;

            long var9;
            try {
                String value = this.serialize(objValue);
                var9 = jedis.hsetnx(key, field, value);
            } catch (Throwable var19) {
                var7 = var19;
                throw var19;
            } finally {
                if (jedis != null) {
                    if (var7 != null) {
                        try {
                            jedis.close();
                        } catch (Throwable var18) {
                            var7.addSuppressed(var18);
                        }
                    } else {
                        jedis.close();
                    }
                }

            }

            return var9;
        } else {
            return (Long) this.pipeline(new BasePipelineTaskWithResult<Long>() {
                public Response<Long> exec(Pipeline p) {
                    String value = RedisClientImpl.this.serialize(objValue);
                    Response<Long> incr = p.hsetnx(key, field, value);
                    p.expire(key, expireSeconds);
                    return incr;
                }
            });
        }
    }

    @Override
    public Map<String, JedisPool> getClusterNodes() {
        throw new RuntimeException("standalone use getNodes()");
    }


    protected String serialize(Serializable obj) {
        return JSON.toJSONString(obj);
    }

    protected <T> T deserialize(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }
}
