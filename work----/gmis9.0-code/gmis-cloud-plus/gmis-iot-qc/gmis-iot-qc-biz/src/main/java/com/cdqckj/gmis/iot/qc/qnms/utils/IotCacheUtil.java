package com.cdqckj.gmis.iot.qc.qnms.utils;

import com.cdqckj.gmis.authority.enumeration.auth.ApplicationAppTypeEnum;
import com.cdqckj.gmis.cache.repository.RedisService;
import com.cdqckj.gmis.common.utils.StringUtil;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.iot.qc.entity.IotSubscribe;
import com.cdqckj.gmis.iot.qc.qnms.constant.GMISHeaderConstant;
import com.cdqckj.gmis.iot.qc.qnms.constant.RedisConstant;
import com.cdqckj.gmis.iot.qc.qnms.exception.IotException;
import com.cdqckj.gmis.iot.qc.service.IotSubscribeService;
import com.cdqckj.gmis.utils.StrPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * All rights Reserved, Designed By www.cdqckj.com
 *
 * @version V1.0
 * @Description: 传感系统缓存工具
 * @author: 秦川物联网科技
 * @date: 2020/10/14 16:56
 * @author: yjb
 * @Copyright: 2020 www.cdqckj.com Inc. All rights reserved.
 * 注意：本内容仅限于秦川物联网科技公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Component
public class IotCacheUtil {
    @Autowired
    private RedisService redisService;
    @Autowired
    private IotSubscribeService iotSubscribeService;
    /**
     * 获取访问token
     * @return
     */
    public String getAccessToken(){
        String domainId = getDomainId();
        String tenant = BaseContextHandler.getTenant();
        if(StringUtil.isNullOrBlank(domainId)){
            throw new IotException(500,"未传递"+GMISHeaderConstant.DOMAIN_ID+"信息");
        }
        String accessToken = (String)redisService.get(RedisConstant.IOT_GMIS+ StrPool.COLON+tenant+StrPool.COLON+
                RedisConstant.IOT_TOKEN+StrPool.COLON+domainId);
        if(StringUtil.isNullOrBlank(accessToken)){
            throw new IotException(500,"accessToken不能为空");
        }
        return accessToken;
    }

    /**
     * 没有request请求时获取access_token
     * @param domainId
     * @return
     */
    public String getImplAccessToken(String domainId){
        String tenant = BaseContextHandler.getTenant();
        if(StringUtil.isNullOrBlank(domainId)){
            throw new IotException(500,"未传递"+GMISHeaderConstant.DOMAIN_ID+"信息");
        }
        String accessToken = (String)redisService.get(RedisConstant.IOT_GMIS+StrPool.COLON+tenant+StrPool.COLON+
                RedisConstant.IOT_TOKEN+StrPool.COLON+domainId);
        if(StringUtil.isNullOrBlank(accessToken)){
            throw new IotException(500,"accessToken不能为空");
        }
        return accessToken;
    }


    /**
     * 获取domainId
     * @return
     */
    public String getDomainId(){
        String domainId = HttpContextUtils.getHttpServletRequest().getHeader(GMISHeaderConstant.DOMAIN_ID);
        if(StringUtil.isNullOrBlank(domainId)){
            return null;
        }
        if(StringUtil.isNullOrBlank(domainId)){
            IotSubscribe iotSubscribe = iotSubscribeService.queryByTenant(BaseContextHandler.getTenant(),domainId);
            domainId = iotSubscribe.getDomainId();
        }
        return domainId;
    }
    /**
     * 获取厂家和物联网关联的信息
     */
    public IotSubscribe getFactoryEntity(){
        String domainId = HttpContextUtils.getHttpServletRequest().getHeader(GMISHeaderConstant.DOMAIN_ID);
        if(StringUtil.isNullOrBlank(domainId)){
            return null;
        }
        IotSubscribe iotSubscribe = iotSubscribeService.queryByTenant(BaseContextHandler.getTenant(),domainId);
        return iotSubscribe;
    }

    public String getReceiveDomainId(){
      return HttpContextUtils.getHttpServletRequest().getHeader(GMISHeaderConstant.DOMAIN_ID);
    }

    /**
     * 消息队列中获取全局配置
      * @return
     */
    public IotSubscribe getMessageFactoryEntity(String domainId){
        IotSubscribe iotSubscribe = null;
        if(redisService.hasKey(RedisConstant.IOT_GMIS+StrPool.COLON+BaseContextHandler.getTenant()+StrPool.COLON+
                RedisConstant.FACTORY_IOT_ENTITY+StrPool.COLON+domainId)){
                iotSubscribe = (IotSubscribe) redisService.get(RedisConstant.IOT_GMIS+StrPool.COLON+BaseContextHandler.getTenant()+StrPool.COLON+
                    RedisConstant.FACTORY_IOT_ENTITY+StrPool.COLON+domainId);
        }else{
                iotSubscribe = iotSubscribeService.queryByTenant(BaseContextHandler.getTenant(),domainId);
        }
        String url = iotSubscribe.getNoticeUrl().replace("http://","").replace(":8760/api/iotQc/receive/data/","")
                .replace("/","");
        iotSubscribe.setNoticeUrl("http://"+url+":8760/api/iotQc/receive/data/");
        return iotSubscribe;
    }

}
