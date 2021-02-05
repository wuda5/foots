package com.cdqckj.gmis.common.utils;

import com.cdqckj.gmis.cache.repository.RedisService;
import com.cdqckj.gmis.common.constant.MeterRedisConstant;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.utils.StrPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MeterFactoryCacheConfig {
    @Autowired
    private RedisService redisService;
    public String getReceiveDomainId(String gasMeterNumber){
        if(!redisService.hasKey(MeterRedisConstant.IOT_METER+ StrPool.COLON+BaseContextHandler.getTenant()+StrPool.COLON+gasMeterNumber)){
            return null;
        }else{
            return (String)redisService.get(MeterRedisConstant.IOT_METER+ StrPool.COLON+BaseContextHandler.getTenant()+StrPool.COLON+gasMeterNumber);
        }
    }


}
