package com.okay;

import com.okay.config.CacheFile;
import com.okay.config.ConfigFile;
import org.redisson.spring.cache.CacheConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * <h2>redisson参数配置<h2>
 * @author okay
 * @create 2020-07-08 09:49
 */
@ConfigurationProperties(prefix = "spring.redisson")
public class RedissonProperties {

    private ConfigFile configFile = new ConfigFile();
    private CacheFile  cacheFile = new CacheFile();

    public CacheFile getCacheFile() {
        return cacheFile;
    }

    public void setCacheFile(CacheFile cacheFile) {
        this.cacheFile = cacheFile;
    }

    public ConfigFile getConfigFile() {
        return configFile;
    }

    public void setConfigFile(ConfigFile configFile) {
        this.configFile = configFile;
    }
}
