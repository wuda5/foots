package com.cdqckj.gmis.statistics.controller;

import com.cdqckj.gmis.common.key.RedisStsKey;
import com.cdqckj.gmis.common.utils.CacheKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author: lijianguo
 * @time: 2021/01/15 08:36
 * @remark: 重新设置统计的方式
 */
public class BaseController {

    @Autowired
    RedisTemplate redisTemplate;

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/15 8:38
    * @remark 用原始数据统计
    */
    protected Boolean stsRawDataType(){

        String key = CacheKeyUtil.createTenantKey(RedisStsKey.STS_COMBINE_DATA);
        if (redisTemplate.hasKey(key)){
            return false;
        }else {
            return true;
        }
    }
}
