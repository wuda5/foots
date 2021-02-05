package com.cdqckj.gmis.scan;

import com.alibaba.fastjson.JSONObject;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.mq.constant.QueueConstants;
import com.cdqckj.gmis.scan.properties.ScanProperties;
import com.cdqckj.gmis.scan.service.SystemApiScanService;
import com.cdqckj.gmis.utils.SpringUtils;
import com.cdqckj.gmis.scan.model.SystemApiScanSaveDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 系统api扫描配置类
 *
 * @author gmis
 * @date 2019/12/16
 */
@ComponentScan(
        basePackages = {
                "com.cdqckj.gmis.scan.controller"
        }
)
@Import({ScanConfiguration.ScanRabbitConfiguration.class, ScanConfiguration.ScanFeignConfiguration.class})
@Slf4j
public class ScanConfiguration {
    @Bean
    @ConditionalOnMissingBean(SpringUtils.class)
    public SpringUtils getSpringUtils(ApplicationContext applicationContext) {
        SpringUtils.setApplicationContext(applicationContext);
        return new SpringUtils();
    }

    @Configuration
    @ConditionalOnProperty(name = "gmis.scan.type", havingValue = "FEIGN", matchIfMissing = true)
    @EnableFeignClients(basePackageClasses = ScanFeignConfiguration.SystemApiApi.class)
    public static class ScanFeignConfiguration {

        @Bean("systemApiScanService")
        @ConditionalOnMissingBean(SystemApiScanService.class)
        public SystemApiScanService getSystemApiService(SystemApiApi systemApiApi) {
            return new SystemApiFeignServiceImpl(systemApiApi);
        }

        /**
         * 系统接口
         *
         * @author gmis
         * @date 2019/12/16
         */
        @FeignClient(name = "${gmis.feign.oauth-server:gmis-oauth-server}", path = "/systemApi", fallback = SystemApiApiFallback.class)
        public interface SystemApiApi {
            /**
             * 批量保存
             *
             * @param data
             * @return
             */
            @PostMapping("/batch")
            R<Boolean> batchSave(@RequestBody SystemApiScanSaveDTO data);

        }

        public class SystemApiFeignServiceImpl implements SystemApiScanService {

            private SystemApiApi systemApiApi;

            public SystemApiFeignServiceImpl(SystemApiApi systemApiApi) {
                this.systemApiApi = systemApiApi;
            }

            @Override
            public Boolean batchSave(SystemApiScanSaveDTO data) {
                R<Boolean> result = systemApiApi.batchSave(data);
                return result.getIsSuccess() ? result.getData() : false;
            }
        }

        /**
         * 熔断
         *
         * @author gmis
         * @date 2019/12/16
         */
        @Component
        public class SystemApiApiFallback implements SystemApiApi {

            @Override
            public R<Boolean> batchSave(SystemApiScanSaveDTO data) {
                return R.timeout();
            }
        }

    }

    /**
     * 使用队列时，消费者需要自行实现
     */
    @ConditionalOnProperty(prefix = ScanProperties.PREFIX, name = "type", havingValue = "RABBIT")
    public static class ScanRabbitConfiguration {

        @Bean("systemApiScanService")
        @ConditionalOnMissingBean(SystemApiScanService.class)
        public SystemApiScanService getSystemApiService() {
            return new SystemApiRabbitServiceImpl();
        }

        public class SystemApiRabbitServiceImpl implements SystemApiScanService {

            @Autowired
            private RabbitTemplate rabbitTemplate;

            @Override
            public Boolean batchSave(SystemApiScanSaveDTO data) {
                try {
                    rabbitTemplate.convertAndSend(QueueConstants.QUEUE_SCAN_API_RESOURCE, JSONObject.toJSONString(data));
                    return true;
                } catch (Exception e) {
                    log.warn("发送消息失败", e);
                    return false;
                }
            }
        }
    }

}
