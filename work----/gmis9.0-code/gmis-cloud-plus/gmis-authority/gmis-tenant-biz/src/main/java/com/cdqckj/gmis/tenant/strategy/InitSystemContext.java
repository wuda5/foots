package com.cdqckj.gmis.tenant.strategy;

import com.cdqckj.gmis.base.MessageConstants;
import com.cdqckj.gmis.cache.repository.RedisService;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.database.properties.DatabaseProperties;
import com.cdqckj.gmis.lang.L18nEnum;
import com.cdqckj.gmis.lang.L18nMenuContainer;
import com.cdqckj.gmis.utils.BizAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 初始化系统上下文
 *
 * @author gmis
 * @date 2020年03月15日11:58:47
 */
@Component
public class InitSystemContext {
    private final Map<String, InitSystemStrategy> initSystemStrategyMap = new ConcurrentHashMap<>();
    private final DatabaseProperties databaseProperties;

    @Autowired
    private RedisService redisService;

    @Autowired
    public InitSystemContext(Map<String, InitSystemStrategy> strategyMap, DatabaseProperties databaseProperties) {
        strategyMap.forEach(this.initSystemStrategyMap::put);
        this.databaseProperties = databaseProperties;
    }

    /**
     * 初始化系统
     *
     * @param tenant
     */
    public boolean init(String tenant) {
        InitSystemStrategy initSystemStrategy = initSystemStrategyMap.get(databaseProperties.getMultiTenantType().name());
        BizAssert.notNull(initSystemStrategy,String.format(getLangMessage(MessageConstants.TENANT_VERIFY_MODE),
                databaseProperties.getMultiTenantType().name()));

        return initSystemStrategy.init(tenant);
    }

    /**
     * 重置系统
     *
     * @param tenant
     */
    public boolean reset(String tenant) {
        InitSystemStrategy initSystemStrategy = initSystemStrategyMap.get(databaseProperties.getMultiTenantType().name());
        BizAssert.notNull(initSystemStrategy,String.format(getLangMessage(MessageConstants.TENANT_VERIFY_MODE),
                databaseProperties.getMultiTenantType().name()));
        return initSystemStrategy.reset(tenant);
    }

    /**
     * 删除租户数据
     *
     * @param tenantCodeList
     */
    public boolean delete(List<String> tenantCodeList) {
        InitSystemStrategy initSystemStrategy = initSystemStrategyMap.get(databaseProperties.getMultiTenantType().name());
        BizAssert.notNull(initSystemStrategy,String.format(getLangMessage(MessageConstants.TENANT_VERIFY_MODE),
                databaseProperties.getMultiTenantType().name()));
        return initSystemStrategy.delete(tenantCodeList);
    }

    public String getLangMessage(String key) {
        String message = null;
        String tenant = BaseContextHandler.getTenant();
        Integer langType = (Integer) redisService.get("lang"+tenant);
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

}
