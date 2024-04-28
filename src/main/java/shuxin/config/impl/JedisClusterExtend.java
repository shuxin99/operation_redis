package shuxin.config.impl;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.*;
import shuxin.config.pipeline.BasePipelineTaskWithResult;
import shuxin.config.pipeline.BasePipelineTaskWithResultList;
import shuxin.config.pipeline.BasePipelineTaskWithResultMap;
import shuxin.config.pipeline.BasePipelineTaskWithoutResult;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class JedisClusterExtend extends JedisCluster {
    private static final Logger log = LoggerFactory.getLogger(JedisClusterExtend.class);

    public JedisClusterExtend(Set<HostAndPort> clusterNodes, int connectionTimeout, int soTimeout, int maxAttempts, String password, GenericObjectPoolConfig poolConfig) {
        super(clusterNodes, connectionTimeout, soTimeout, maxAttempts, password, poolConfig);
        this.connectionHandler = new JedisSlotBasedConnectionHandlerExtend(clusterNodes, poolConfig, connectionTimeout, soTimeout, (String) null, password, (String) null);
    }

    public <T> List<T> pipeline(String sampleKey, final BasePipelineTaskWithResultList<T> p) {
        return (List) (new JedisClusterCommand<List<T>>(this.connectionHandler, this.maxAttempts) {
            public List<T> execute(Jedis connection) {
                return p.run(connection.pipelined());
            }
        }).run(sampleKey);
    }

    public <T> Map<String, T> pipeline(String sampleKey, final BasePipelineTaskWithResultMap<T> p) {
        return (Map) (new JedisClusterCommand<Map<String, T>>(this.connectionHandler, this.maxAttempts) {
            public Map<String, T> execute(Jedis connection) {
                return p.run(connection.pipelined());
            }
        }).run(sampleKey);
    }

    public <T> T pipeline(String sampleKey, final BasePipelineTaskWithResult<T> p) {
        return (new JedisClusterCommand<T>(this.connectionHandler, this.maxAttempts) {
            public T execute(Jedis connection) {
                return p.run(connection.pipelined());
            }
        }).run(sampleKey);
    }

    public void pipeline(String sampleKey, final BasePipelineTaskWithoutResult p) {
        (new JedisClusterCommand<Object>(this.connectionHandler, this.maxAttempts) {
            public Object execute(Jedis connection) {
                p.run(connection.pipelined());
                return null;
            }
        }).run(sampleKey);
    }

    public Map<String, JedisPool> getClusterNodes() {
        return this.connectionHandler.getNodes();
    }


    public ScanResult<Map.Entry<String, String>> hscan(final String key, final String cursor, ScanParams params) {
        return (ScanResult) (new JedisClusterCommand<ScanResult<Map.Entry<String, String>>>(this.connectionHandler, this.maxAttempts, this.maxTotalRetriesDuration) {
            public ScanResult<Map.Entry<String, String>> execute(Jedis connection) {
                return connection.hscan(key, cursor, params);
            }
        }).run(key);
    }


    public ScanResult<String> sscan(final String key, final String cursor, ScanParams params) {
        return (ScanResult) (new JedisClusterCommand<ScanResult<String>>(this.connectionHandler, this.maxAttempts, this.maxTotalRetriesDuration) {
            public ScanResult<String> execute(Jedis connection) {
                return connection.sscan(key, cursor, params);
            }
        }).run(key);
    }


    public ScanResult<Tuple> zscan(final String key, final String cursor, ScanParams params) {
        return (ScanResult) (new JedisClusterCommand<ScanResult<Tuple>>(this.connectionHandler, this.maxAttempts, this.maxTotalRetriesDuration) {
            public ScanResult<Tuple> execute(Jedis connection) {
                return connection.zscan(key, cursor, params);
            }
        }).run(key);
    }


}
