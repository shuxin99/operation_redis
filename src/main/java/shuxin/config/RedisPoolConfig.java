package shuxin.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@ConfigurationProperties(prefix = "redis.config.pool")
public class RedisPoolConfig {
    private int maxTotal = 20;
    private Integer maxIdle;
    private int minIdle = 5;
    private int maxWaitMillis = 2000;
    private boolean testOnBorrow = false;
    private boolean testOnReturn = false;
    private boolean testWhileIdle = true;
    private int timeBetweenEvictionRunsMillis = 15000;
    private Integer minEvictableIdleTimeMillis;
    private Integer numTestsPerEvictionRun;
    private int connectionTimeout = 5000;
    private int soTimeout = 1000;
    private int database = 0;

    public int getMaxTotal() {
        return this.maxTotal;
    }

    public Integer getMaxIdle() {
        return this.maxIdle;
    }

    public int getMinIdle() {
        return this.minIdle;
    }

    public int getMaxWaitMillis() {
        return this.maxWaitMillis;
    }

    public boolean isTestOnBorrow() {
        return this.testOnBorrow;
    }

    public boolean isTestOnReturn() {
        return this.testOnReturn;
    }

    public boolean isTestWhileIdle() {
        return this.testWhileIdle;
    }

    public int getTimeBetweenEvictionRunsMillis() {
        return this.timeBetweenEvictionRunsMillis;
    }

    public Integer getMinEvictableIdleTimeMillis() {
        return this.minEvictableIdleTimeMillis;
    }

    public Integer getNumTestsPerEvictionRun() {
        return this.numTestsPerEvictionRun;
    }

    public int getConnectionTimeout() {
        return this.connectionTimeout;
    }

    public int getSoTimeout() {
        return this.soTimeout;
    }

    public int getDatabase() {
        return this.database;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    public void setMaxIdle(Integer maxIdle) {
        this.maxIdle = maxIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public void setMaxWaitMillis(int maxWaitMillis) {
        this.maxWaitMillis = maxWaitMillis;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public void setTestOnReturn(boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    public void setTestWhileIdle(boolean testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
    }

    public void setTimeBetweenEvictionRunsMillis(int timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }

    public void setMinEvictableIdleTimeMillis(Integer minEvictableIdleTimeMillis) {
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }

    public void setNumTestsPerEvictionRun(Integer numTestsPerEvictionRun) {
        this.numTestsPerEvictionRun = numTestsPerEvictionRun;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public void setSoTimeout(int soTimeout) {
        this.soTimeout = soTimeout;
    }

    public void setDatabase(int database) {
        this.database = database;
    }

    public RedisPoolConfig() {
    }
}
