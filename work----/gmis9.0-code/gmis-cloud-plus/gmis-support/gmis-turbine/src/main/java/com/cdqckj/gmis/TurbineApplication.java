package com.cdqckj.gmis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

/**
 * @author gmis
 * @createTime 2020-06-03
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrixDashboard
@Slf4j
@EnableTurbine
public class TurbineApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext application  = SpringApplication.run(TurbineApplication.class, args);

        Environment env = application.getEnvironment();
        log.info("\n----------------------------------------------------------\n\t" +
                        "应用 '{}' 运行成功! \n\t" +
                        "----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                //InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port"),
                env.getProperty("server.servlet.context-path", ""),
                env.getProperty("spring.mvc.servlet.path", "")
        );
    }
}
