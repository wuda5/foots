package com.cdqckj.gmis;

import com.alibaba.cloud.seata.feign.SeataFeignClientAutoConfiguration;
import com.cdqckj.gmis.security.annotation.EnableLoginArgResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

import java.net.InetAddress;

/**
 * 消息服务
 *
 * @author gmis
 * @createTime 2018-01-25 10:13
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, SeataFeignClientAutoConfiguration.class})
@ComponentScan({
        "com.cdqckj.gmis",
        "com.xxl.job.admin",
})
@Slf4j
@EnableDiscoveryClient
@EnableHystrix
@EnableFeignClients(value = {
        "com.cdqckj.gmis",
})
@EnableLoginArgResolver
public class JobsServerApplication {
    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext application = SpringApplication.run(JobsServerApplication.class, args);
        Environment env = application.getEnvironment();
        log.info("\n----------------------------------------------------------\n\t" +
                        "应用 '{}' 运行成功! 访问连接:\n\t" +
                        "首页: \t\thttp://{}:{}/{}\n" +
                        "----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port"),
                env.getProperty("spring.application.name")
        );
    }
}
