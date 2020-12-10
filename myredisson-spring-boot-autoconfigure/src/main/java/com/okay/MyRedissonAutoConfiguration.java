package com.okay;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * <h2>redisson自动装配<h2>
 * @author okay
 * @create 2020-07-08 09:59
 */
@Configuration
@EnableConfigurationProperties(RedissonProperties.class)
public class MyRedissonAutoConfiguration {

    private final Logger logger = LoggerFactory.getLogger(MyRedissonAutoConfiguration.class);
    @Autowired
    private RedissonProperties redissonProperties;

    public Config jsonConfig() throws IOException {
        File file = ResourceUtils.getFile(redissonProperties.getConfigFile().getJson());
        return Config.fromJSON(file);
    }

    public Config yamlConfig() throws IOException {
        File file = ResourceUtils.getFile(redissonProperties.getConfigFile().getYaml());
        return Config.fromYAML(file);
    }

    @Bean
    @ConditionalOnMissingBean
    public Config config() throws IOException {
        if (!StringUtils.isEmpty(redissonProperties.getConfigFile().getJson())) {
            return jsonConfig();
        } else if (!StringUtils.isEmpty(redissonProperties.getConfigFile().getYaml())) {
            return yamlConfig();
        } else {
            throw new RuntimeException("please offer the config file by json/yaml");
        }
    }

    @Bean(destroyMethod = "shutdown")
    @ConditionalOnMissingBean
    public RedissonClient redissonClient(Config config) throws IOException {
        logger.info("create redissonClient, config is : {}", config.toJSON());
        return Redisson.create(config);
    }
}
