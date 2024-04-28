package shuxin.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisSentinelPool;
import shuxin.config.impl.JedisClusterExtend;
import shuxin.config.impl.RedisClientClusterImpl;
import shuxin.config.impl.RedisClientImpl;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Component
public class RedisClientGenerate {
    private final RedisConfig config;

    @Autowired
    public RedisClientGenerate(RedisConfig config) {
        this.config = config;
    }

    @Bean
    @PostConstruct
    public RedisClient generateRedisClient() {
        RedisType redisType = RedisType.parse(config.getType());
        RedisPoolConfig p = config.getPool();
        if (redisType != RedisType.Cluster) {
            if (redisType == RedisType.Sentinel) {
                return new RedisClientImpl(new JedisSentinelPool(null, new HashSet<>(config.getNodes()), this.createPoolConfig(p), p.getConnectionTimeout(), p.getSoTimeout(), config.getPassword(), p.getDatabase()), redisType);
            } else if (redisType == RedisType.Standalone) {
                HostAndPort hostAndPort = HostAndPort.from(config.getNodes().get(0));
                return new RedisClientImpl(new JedisPool(this.createPoolConfig(p), hostAndPort.getHost(), hostAndPort.getPort(), p.getConnectionTimeout(), p.getSoTimeout(), config.getPassword(), p.getDatabase(), null), redisType);
            } else {
                throw new RuntimeException("init with wrong type: " + config.getType());
            }
        } else {
            Set<HostAndPort> clusterNodes = new HashSet<>(config.getNodes().size());
            Iterator<String> var6 = config.getNodes().iterator();

            while (var6.hasNext()) {
                String node = var6.next();
                clusterNodes.add(HostAndPort.from(node));
            }
            return new RedisClientClusterImpl(new JedisClusterExtend(clusterNodes, p.getConnectionTimeout(), p.getSoTimeout(), 5, config.getPassword(), this.createPoolConfig(p)), redisType);
        }
    }

    private GenericObjectPoolConfig createPoolConfig(RedisPoolConfig p) {
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxIdle(p.getMaxIdle() == null ? p.getMaxTotal() : p.getMaxIdle());
        poolConfig.setMaxTotal(p.getMaxTotal());
        poolConfig.setMinIdle(p.getMinIdle());
        poolConfig.setMaxWaitMillis(p.getMaxWaitMillis());
        poolConfig.setTestOnBorrow(p.isTestOnBorrow());
        poolConfig.setTestOnReturn(p.isTestOnReturn());
        poolConfig.setTestWhileIdle(p.isTestWhileIdle());
        poolConfig.setTimeBetweenEvictionRunsMillis(p.getTimeBetweenEvictionRunsMillis());
        poolConfig.setNumTestsPerEvictionRun(p.getNumTestsPerEvictionRun() == null ? p.getMaxTotal() : p.getNumTestsPerEvictionRun());
        poolConfig.setMinEvictableIdleTimeMillis(p.getMinEvictableIdleTimeMillis() == null ? (long) p.getTimeBetweenEvictionRunsMillis() : (long) p.getMinEvictableIdleTimeMillis());
        return poolConfig;
    }

}
