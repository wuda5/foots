package com.cdqckj.gmis.mq.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/**
 * 操作日志配置类
 *
 * @author gmis
 * @date 2020年03月09日15:00:47
 */
@Data
@NoArgsConstructor
@ConfigurationProperties(prefix = MqProperties.PREFIX)
@Configuration
public class MqProperties {
    public static final String PREFIX = "gmis.rabbitmq";

    /**
     * 是否启用
     */
    private Boolean enabled = true;

    /**
     * ip
     */
    private String ip;

    /**
     * 端口
     */
    @Value("${gmis.rabbitmq.port}")
    private Integer port;

    /**
     * 用户名
     */
    @Value("${gmis.rabbitmq.username}")
    private String username;

    /**
     * 密码
     */
    @Value("${gmis.rabbitmq.password}")
    private String password;

}
