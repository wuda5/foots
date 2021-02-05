package com.cdqckj.gmis.operateparam.config.mq;

import com.alibaba.fastjson.JSONObject;
import com.cdqckj.gmis.common.constant.BizMqQueue;
import com.cdqckj.gmis.common.domain.machine.MqQueueName;
import com.cdqckj.gmis.database.properties.DatabaseProperties;
import com.cdqckj.gmis.mq.properties.MqProperties;
import com.cdqckj.gmis.tenant.service.DataSourceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.HashMap;


/**
 * 消息队列开启时，启用
 *
 * @author gmis
 * @date 2020年04月05日19:22:02
 */
@Slf4j
@AllArgsConstructor
@Configuration
@ConditionalOnProperty(prefix = MqProperties.PREFIX, name = "enabled", havingValue = "true")
@Import({MqQueueName.class})
public class ConfigResetDataSource {

    private final DataSourceService dataSourceService;

    @Autowired
    MqQueueName mqQueueName;

    @Bean
    @ConditionalOnProperty(prefix = DatabaseProperties.PREFIX, name = "multiTenantType", havingValue = "DATASOURCE")
    public Queue dsQueue() {
        String queueName = mqQueueName.getQueueName(BizMqQueue.TENANT_DS_QUEUE_BY_CONFIG);
        return new Queue(queueName);
    }

    @Bean
    @ConditionalOnProperty(prefix = DatabaseProperties.PREFIX, name = "multiTenantType", havingValue = "DATASOURCE")
    public Binding dsQueueBinding() {
        String queueName = mqQueueName.getQueueName(BizMqQueue.TENANT_DS_QUEUE_BY_CONFIG);
        return new Binding(queueName, Binding.DestinationType.QUEUE, BizMqQueue.TENANT_DS_FANOUT_EXCHANGE, "", new HashMap(1));
    }

    @RabbitListener(queues = "#{dsQueue.name}")
    @ConditionalOnProperty(prefix = DatabaseProperties.PREFIX, name = "multiTenantType", havingValue = "DATASOURCE")
    public void dsRabbitListener(@Payload String param) {
        log.info("异步初始化数据源=={}", param);
        JSONObject map = JSONObject.parseObject(param);
        if ("init".equals(map.getString("method"))) {
            dataSourceService.initDataSource(map.getString("tenant"));
        } else {
            dataSourceService.remove(map.getString("tenant"));
        }
    }
}
