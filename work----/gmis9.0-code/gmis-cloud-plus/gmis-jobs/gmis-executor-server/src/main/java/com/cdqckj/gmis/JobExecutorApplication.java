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

/**
 * @author gmis 2020年04月10日21:34:04
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, SeataFeignClientAutoConfiguration.class})
@EnableDiscoveryClient
@EnableHystrix
@EnableFeignClients(value = {
		"com.cdqckj.gmis",
})
@Slf4j
@EnableLoginArgResolver
public class JobExecutorApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobExecutorApplication.class, args);
	}

}
