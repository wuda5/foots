package com.cdqckj.gmis.authority.config.mq;

import cn.afterturn.easypoi.cache.manager.IFileLoader;
import com.alibaba.fastjson.JSONObject;
import com.cdqckj.gmis.common.constant.BizMqQueue;
import com.cdqckj.gmis.common.domain.tenant.MulTenant;
import com.cdqckj.gmis.common.domain.tenant.MulTenantProcess;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.database.properties.DatabaseProperties;
import com.cdqckj.gmis.mq.constant.QueueConstants;
import com.cdqckj.gmis.mq.properties.MqProperties;
import com.cdqckj.gmis.tenant.entity.Tenant;
import com.cdqckj.gmis.tenant.service.DataSourceService;
import com.cdqckj.gmis.tenant.service.TenantService;
import com.cdqckj.gmis.tenant.service.impl.TenantServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.Arrays;
import java.util.HashMap;

/**
 * @author: lijianguo
 * @time: 2020/12/09 14:06
 * @remark: 新建租户的状态的队列
 */
@Configuration
@AllArgsConstructor
@Slf4j
@ConditionalOnProperty(prefix = MqProperties.PREFIX, name = "enabled", havingValue = "true")
@Import({TenantServiceImpl.class})
public class NewTentMq {

    @Autowired
    private TenantService tenantService;

    private final DataSourceService dataSourceService;

    private final MulTenantProcess mulTenantProcess;

    @Bean
    public Queue newTenantQueue() {
        return new Queue(BizMqQueue.NEW_TENANT_QUEUE);
    }

    @Bean
    public Binding newTenantBinding() {
        return new Binding(BizMqQueue.NEW_TENANT_QUEUE, Binding.DestinationType.QUEUE, BizMqQueue.NEW_TENANT_FANOUT_EXCHANGE, "", new HashMap(1));
    }

    @Bean
    public DirectExchange newTenantExchange() {
        return new DirectExchange(BizMqQueue.NEW_TENANT_FANOUT_EXCHANGE, true, false);
    }

    @RabbitListener(queues = BizMqQueue.NEW_TENANT_QUEUE)
    public void newTenantListener(@Payload String param) {
        log.info("新建租户的数据获取 {}", param);

        JSONObject map = JSONObject.parseObject(param);
        String tenant = map.getString("tenant");
        if (StringUtils.isBlank(tenant)){
            return;
        }
        BaseContextHandler.setTenantId(mulTenantProcess.getAdminTenant().getCode());
        BaseContextHandler.setTenant(mulTenantProcess.getAdminTenant().getCode());
        Tenant tenantInfo = tenantService.getByCode(tenant);
        if (tenantInfo == null) {
            return;
        }
        Integer initStatus = map.getInteger("initStatus");
        String initFailMsg = map.getString("initFailMsg");
        dataSourceService.initDataSource(tenant);
        tenantInfo.setInitStatus(initStatus);
        tenantInfo.setInitFailMsg(initFailMsg);
        tenantService.updateById(tenantInfo);
    }
}
