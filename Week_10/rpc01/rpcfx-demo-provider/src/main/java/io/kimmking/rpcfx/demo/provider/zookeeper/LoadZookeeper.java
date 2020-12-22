package io.kimmking.rpcfx.demo.provider.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author hitopei
 *
 * spring启动加载zookeeper
 */
@Configuration
//@Import(ZookeeperProperties.class)
public class LoadZookeeper  {


    @Autowired
    private ZookeeperProperties properties;


    //@Bean(name = "zookeeperClient")
    //@ConditionalOnMissingBean
    public CuratorFramework getClient(){
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder().
                connectString(properties.getHost() + ":" + properties.getPort().toString()).
                namespace(properties.getNamespace()).retryPolicy(retryPolicy).build();
        return client;
    }


}
