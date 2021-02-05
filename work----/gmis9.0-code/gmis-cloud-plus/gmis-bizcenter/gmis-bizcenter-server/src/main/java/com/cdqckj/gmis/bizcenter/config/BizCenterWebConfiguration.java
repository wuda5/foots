package com.cdqckj.gmis.bizcenter.config;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.cdqckj.gmis.boot.config.BaseConfig;
import com.cdqckj.gmis.common.domain.tenant.MulTenantData;
import com.cdqckj.gmis.common.domain.tenant.MulTenantProcess;
import com.cdqckj.gmis.common.domain.tenant.MulTenantProcessDefault;
import com.cdqckj.gmis.common.domain.tenant.TenantRestAspect;
import com.cdqckj.gmis.log.event.SysLogListener;
import com.cdqckj.gmis.oauth.api.LogApi;
import lombok.extern.log4j.Log4j2;
import net.oschina.j2cache.CacheChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.support.TransactionTemplate;

@Log4j2
@Configuration
public class BizCenterWebConfiguration extends BaseConfig {
    @Bean
    @ConditionalOnExpression("${gmis.log.enabled:true} && 'DB'.equals('${gmis.log.type:LOGGER}')")
    public SysLogListener sysLogListener(LogApi logApi) {
        return new SysLogListener((log) -> logApi.save(log));
    }

    /**
     * @auth lijianguo
     * @date 2020/10/20 11:25
     * @remark 这里可能不能注入数据所以要检查一下
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
