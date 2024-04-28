package shuxin.config;

import com.sun.istack.internal.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
@ConfigurationProperties(prefix = "redis.config")
public class RedisConfig {
    private @NotNull String type;
    private List<String> nodes;
    private String password;
    private RedisPoolConfig pool;

    public void setType(String type) {
        this.type = type;
    }

    public @NotNull String getType() {
        return this.type;
    }

    public List<String> getNodes() {
        return this.nodes;
    }

    public String getPassword() {
        return this.password;
    }

    public RedisPoolConfig getPool() {
        return this.pool;
    }

    public void setNodes(List<String> nodes) {
        this.nodes = nodes;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPool(RedisPoolConfig pool) {
        this.pool = pool;
    }

    public RedisConfig() {
    }

    public RedisConfig(String type, List<String> nodes, String password, RedisPoolConfig pool) {
        this.type = type;
        this.nodes = nodes;
        this.password = password;
        this.pool = pool;
    }


}
