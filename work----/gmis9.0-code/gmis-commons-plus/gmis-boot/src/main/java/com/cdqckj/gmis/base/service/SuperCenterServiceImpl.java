package com.cdqckj.gmis.base.service;

import com.cdqckj.gmis.base.entity.Entity;
import com.cdqckj.gmis.cache.repository.RedisService;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.utils.BeanPlusUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;

public class SuperCenterServiceImpl implements SuperCenterService {
    @Autowired
    RedisService redisService;
    @Override
    public String getLangMessage(String key) {
        String message = null;
        Integer langType = (Integer) redisService.get("lang"+ BaseContextHandler.getTenant());
        if(langType==null){langType=1;}
        switch(langType){
            case 1:
                message = (String) redisService.hmget("langZh").get(key);
                break;
            case 2:
                message = (String) redisService.hmget("langEn").get(key);
                break;
            default:
                message = null;
        }
        return message;
    }

    @SneakyThrows
    public Entity setCommonParams(Entity obj) {
        setParams(obj,"orgId","setOrgId",BaseContextHandler.getOrgId(),Long.class);
        setParams(obj,"orgName","setOrgName",BaseContextHandler.getOrgName(),String.class);
        setParams(obj,"companyCode","setCompanyCode",BaseContextHandler.getTenant(),String.class);
        setParams(obj,"companyName","setCompanyName",BaseContextHandler.getTenantName(),String.class);
        setParams(obj,"deleteStatus","setDeleteStatus",0,Integer.class);
        setParams(obj,"createUserName","setCreateUserName",BaseContextHandler.getName(),String.class);
        return obj;
    }

    @SneakyThrows
    private void setParams(Entity obj, String key, String methodName, Object value, Class tclass){
        Boolean isSet = BeanPlusUtil.isNullField(key,obj);
        if(isSet){
            Method setMethod = obj.getClass().getMethod(methodName,tclass);
            setMethod.invoke(obj, value);
        }
    }
}
