package com.cdqckj.gmis.common.domain.tenant;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @author: lijianguo
 * @time: 2020/10/20 11:07
 * @remark: 请输入类说明-- 这里不需要了
 */
@Log4j2
public class MulTenantConfig {

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

        MulTenantProcess process = new MulTenantProcessDefault(mulTenantData, redisTemplate, transactionTemplate, dataSource);
        log.info("MulTenantProcess init suc.....");
        return process;
    }

    @Bean
    public TenantRestAspect initTenantRestInterceptor(MulTenantProcess mulTenantProcess){
        return new TenantRestAspect(mulTenantProcess);
    }
}
