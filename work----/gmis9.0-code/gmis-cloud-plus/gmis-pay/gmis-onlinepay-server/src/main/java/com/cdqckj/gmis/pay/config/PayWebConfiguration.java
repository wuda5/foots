package com.cdqckj.gmis.pay.config;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.cdqckj.gmis.boot.config.BaseConfig;
import com.cdqckj.gmis.common.domain.tenant.MulTenantData;
import com.cdqckj.gmis.common.domain.tenant.MulTenantProcess;
import com.cdqckj.gmis.common.domain.tenant.MulTenantProcessDefault;
import com.cdqckj.gmis.common.domain.tenant.TenantRestAspect;
import com.cdqckj.gmis.log.event.SysLogListener;
import com.cdqckj.gmis.oauth.api.LogApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
public class PayWebConfiguration extends BaseConfig {
    @Bean
    @ConditionalOnExpression("${gmis.log.enabled:true} && 'DB'.equals('${gmis.log.type:LOGGER}')")
    public SysLogListener sysLogListener(LogApi logApi) {
        return new SysLogListener((log) -> logApi.save(log));
    }

    /**
     * @auth lijianguo
     * @date 2020/10/20 11:25
     * @remark 注入
     */
    @Bean
    public MulTenantProcess initMulTenantProcess(@Autowired(required=false) MulTenantData mulTenantData,
                                                 @Autowired(required=false) RedisTemplate redisTemplate,
                                                 @Autowired(required=false) TransactionTemplate transactionTemplate,
                                                 @Autowired(required=false) DynamicRoutingDataSource dataSource){
        return new MulTenantProcessDefault(mulTenantData, redisTemplate, transactionTemplate, dataSource);
    }

    @Bean
    public TenantRestAspect initTenantRestInterceptor(MulTenantProcess mulTenantProcess){
        return new TenantRestAspect(mulTenantProcess);
    }
}
