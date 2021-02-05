package com.cdqckj.gmis.authority.config;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.cdqckj.gmis.authority.service.common.OptLogService;
import com.cdqckj.gmis.authority.service.common.impl.OptLogServiceImpl;
import com.cdqckj.gmis.boot.config.BaseConfig;
import com.cdqckj.gmis.common.domain.tenant.*;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.log.event.SysLogListener;
import com.cdqckj.gmis.tenant.service.impl.MulTenantDataImpl;
import io.seata.core.context.RootContext;
import io.seata.tm.api.GlobalTransaction;
import io.seata.tm.api.GlobalTransactionContext;
import lombok.extern.log4j.Log4j2;
import net.oschina.j2cache.CacheChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.interceptor.TransactionAttributeSource;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author gmis
 * @createTime 2017-12-15 14:42
 */
@Log4j2
@Configuration
@Import({MulTenantDataImpl.class, OptLogServiceImpl.class})
public class AuthorityWebConfiguration extends BaseConfig {

    /**
     * gmis.log.enabled = true 并且 gmis.log.type=DB时实例该类
     *
     * @param optLogService
     * @return
     */

    @Bean
    @ConditionalOnExpression("${gmis.log.enabled:true} && 'DB'.equals('${gmis.log.type:LOGGER}')")
    public SysLogListener sysLogListener(OptLogService optLogService) {
        return new SysLogListener((log) -> optLogService.save(log));
    }

    /**
     * @auth lijianguo
     * @date 2020/10/20 11:25
     * @remark 这里可能不能注入数据所以要检查一下
     */
    @Bean
    public MulTenantProcess initMulTenantProcess(@Autowired(required=false)MulTenantData mulTenantData,
                                                 @Autowired(required=false) RedisTemplate redisTemplate,
                                                 @Autowired(required=false)TransactionTemplate transactionTemplate,
                                                 @Autowired(required=false)DynamicRoutingDataSource dataSource){
        return new MulTenantProcessDefault(mulTenantData, redisTemplate, transactionTemplate, dataSource);
    }

    @Bean
    public TenantRestAspect initTenantRestInterceptor(MulTenantProcess mulTenantProcess){
        return new TenantRestAspect(mulTenantProcess);
    }

}
