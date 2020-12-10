package com.okay.config;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @create 2020-07-28 14:18
 */
@Configuration
public class ZookeeperConfig {
    @Value("${zk.url}")
    private String zkUrl;

    @Bean
    public CuratorFramework getCuratorFramework() {
        // 用于重连策略，1000毫秒是初始化的间隔时间，3代表尝试重连次数
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000,3);

        CuratorFramework client = CuratorFrameworkFactory.newClient(zkUrl, retryPolicy);

        client.start();
        return client;
    }
}
