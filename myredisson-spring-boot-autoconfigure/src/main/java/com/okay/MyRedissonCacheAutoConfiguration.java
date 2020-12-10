package com.okay;

import com.okay.aspect.MyCacheAspect;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * <h2>springcache管理<h2>
 * @author okay
 * @create 2020-07-08 11:10
 */
@Configuration
@AutoConfigureAfter(MyRedissonAutoConfiguration.class)
@ConditionalOnBean(RedissonClient.class)
public class MyRedissonCacheAutoConfiguration {

    private final Logger logger = LoggerFactory.getLogger(MyRedissonCacheAutoConfiguration.class);

    @Autowired
    private RedissonProperties redissonProperties;

    @Bean
    @ConditionalOnMissingBean(CacheManager.class)
    public CacheManager cacheManager(RedissonClient redissonClient) {
        RedissonSpringCacheManager cacheManager = new RedissonSpringCacheManager(redissonClient,
                redissonProperties.getCacheFile().getYaml());
        logger.info("create cacheManager, configCacheFile is : {}", redissonProperties.getCacheFile().getYaml());
        cacheManager.setCodec(new JsonJacksonCodec());
        return cacheManager;
    }

    @Bean
    public MyCacheAspect cacheAspect() {
        return new MyCacheAspect();
    }
}
