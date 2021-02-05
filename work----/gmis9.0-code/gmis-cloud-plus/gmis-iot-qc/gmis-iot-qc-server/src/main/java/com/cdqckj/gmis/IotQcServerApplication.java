package com.cdqckj.gmis;

import com.alibaba.cloud.seata.feign.SeataFeignClientAutoConfiguration;
import com.cdqckj.gmis.iot.qc.qnms.utils.IotSpringUtils;
import com.cdqckj.gmis.security.annotation.EnableLoginArgResolver;
import com.cdqckj.gmis.utils.SpringUtils;
import com.cdqckj.gmis.validator.config.EnableFormValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 调度聚合服务启动类
 *
 * @author gmis
 * @date 2020-10-08
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, SeataFeignClientAutoConfiguration.class})
@EnableDiscoveryClient
@Configuration
@EnableFeignClients(value = { "com.cdqckj.gmis" })
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@Slf4j
@EnableLoginArgResolver
public class IotQcServerApplication {
    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(IotQcServerApplication.class, args);
        Environment env = application.getEnvironment();
        SpringUtils.setApplicationContext(application);
        log.info("\n----------------------------------------------------------\n\t" +
                        "应用 '{}' 启动成功! 访问连接:\n\t" +
                        "Swagger文档: \t\thttp://{}:{}/doc.html\n\t" +
                        "数据库监控: \t\thttp://{}:{}/druid\n" +
                        "----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port"),
                "127.0.0.1",
                env.getProperty("server.port"));
    }
}
