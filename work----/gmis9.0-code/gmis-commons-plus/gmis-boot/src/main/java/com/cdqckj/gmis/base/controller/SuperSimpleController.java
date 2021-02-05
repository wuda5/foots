package com.cdqckj.gmis.base.controller;

import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.cache.repository.RedisService;
import com.cdqckj.gmis.utils.BeanPlusUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

/**
 * 简单的实现了BaseController，为了获取注入 Service 和 实体类型
 *
 * @param <S>      Service
 * @param <Entity> 实体
 * @author gmis
 * @date 2020年03月07日22:08:27
 */
public class SuperSimpleController<S extends SuperService<Entity>, Entity> implements BaseController<Entity> {

    protected Class<Entity> entityClass = null;
    @Autowired
    protected S baseService;

    @Autowired
    protected RedisService redisService;

    @Override
    public Class<Entity> getEntityClass() {
        if (entityClass == null) {
            this.entityClass = (Class) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        }
        return this.entityClass;
    }

    @Override
    public SuperService<Entity> getBaseService() {
        return baseService;
    }

    @Override
    public RedisService getRedisService() {
        return redisService;
    }

    @Override
    public String getLangMessage(String key) {
        String message = null;
        Integer langType = (Integer) redisService.get("lang"+getTenant());
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
    @Override
    public Entity setCommonParams(Entity obj) {
        setParams(obj,"orgId","setOrgId",getOrgId(),Long.class);
        setParams(obj,"orgName","setOrgName",getOrgName(),String.class);
        setParams(obj,"companyCode","setCompanyCode",getCompanyCode(),String.class);
        setParams(obj,"companyName","setCompanyName",getCompanyName(),String.class);
        setParams(obj,"deleteStatus","setDeleteStatus",0,Integer.class);
        setParams(obj,"createUserName","setCreateUserName",getName(),String.class);
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
