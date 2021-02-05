package com.cdqckj.gmis.tenant.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cdqckj.gmis.tenant.service.InitDsService;
import com.cdqckj.gmis.common.constant.BizMqQueue;
import com.cdqckj.gmis.mq.properties.MqProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;


/**
 * 开启消息队列就广播
 *
 * @author gmis
 * @date 2020年04月05日16:27:03
 */
@Slf4j
@Service("mqInitDsServiceImpl")
@ConditionalOnProperty(prefix = MqProperties.PREFIX, name = "enabled", havingValue = "true")
public class MqInitDsServiceImpl implements InitDsService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Bean
    public FanoutExchange getFanoutExchange() {
        FanoutExchange queue = new FanoutExchange(BizMqQueue.TENANT_DS_FANOUT_EXCHANGE);
        log.info("Query {} [{}]", BizMqQueue.TENANT_DS_FANOUT_EXCHANGE, queue);
        return queue;
    }

    @Override
    public boolean initDataSource(String tenant) {
        JSONObject param = new JSONObject();
        param.put("tenant", tenant);
        param.put("method", "init");
        rabbitTemplate.convertAndSend(BizMqQueue.TENANT_DS_FANOUT_EXCHANGE, null, param.toString());
        return true;
    }

    @Override
    public boolean removeDataSource(String tenant) {
        JSONObject param = new JSONObject();
        param.put("tenant", tenant);
        param.put("method", "remove");
        rabbitTemplate.convertAndSend(BizMqQueue.TENANT_DS_FANOUT_EXCHANGE, null, param.toString());
        return true;
    }

}
