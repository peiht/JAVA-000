package io.kimmking.rpcfx.demo.provider.zookeeper;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author hitopei
 *
 * zookeeper配置信息
 */
@Configuration
@ConfigurationProperties(prefix = "zookeeper")
@Data
public class ZookeeperProperties {

    private String host;

    private Integer port;

    private String namespace;
}
