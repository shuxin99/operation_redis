package shuxin.config.impl;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisClientConfig;
import redis.clients.jedis.JedisSlotBasedConnectionHandler;

import java.util.Set;

public class JedisSlotBasedConnectionHandlerExtend extends JedisSlotBasedConnectionHandler {
    public JedisSlotBasedConnectionHandlerExtend(Set<HostAndPort> nodes, GenericObjectPoolConfig<Jedis> poolConfig, int timeout) {
        super(nodes, poolConfig, timeout);
    }

    public JedisSlotBasedConnectionHandlerExtend(Set<HostAndPort> nodes, GenericObjectPoolConfig<Jedis> poolConfig, int connectionTimeout, int soTimeout) {
        super(nodes, poolConfig, connectionTimeout, soTimeout);
    }

    public JedisSlotBasedConnectionHandlerExtend(Set<HostAndPort> nodes, GenericObjectPoolConfig<Jedis> poolConfig, int connectionTimeout, int soTimeout, String password) {
        super(nodes, poolConfig, connectionTimeout, soTimeout, password);
    }

    public JedisSlotBasedConnectionHandlerExtend(Set<HostAndPort> nodes, GenericObjectPoolConfig<Jedis> poolConfig, int connectionTimeout, int soTimeout, String password, String clientName) {
        super(nodes, poolConfig, connectionTimeout, soTimeout, password, clientName);
    }

    public JedisSlotBasedConnectionHandlerExtend(Set<HostAndPort> nodes, GenericObjectPoolConfig<Jedis> poolConfig, int connectionTimeout, int soTimeout, String user, String password, String clientName) {
        super(nodes, poolConfig, connectionTimeout, soTimeout, user, password, clientName);
    }

    public JedisSlotBasedConnectionHandlerExtend(Set<HostAndPort> nodes, GenericObjectPoolConfig<Jedis> poolConfig, int connectionTimeout, int soTimeout, int infiniteSoTimeout, String user, String password, String clientName) {
        super(nodes, poolConfig, connectionTimeout, soTimeout, infiniteSoTimeout, user, password, clientName);
    }

    public JedisSlotBasedConnectionHandlerExtend(Set<HostAndPort> nodes, GenericObjectPoolConfig<Jedis> poolConfig, JedisClientConfig clientConfig) {
        super(nodes, poolConfig, clientConfig);
    }
}
