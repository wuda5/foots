package com.cdqckj.gmis.tenant.lineEvent;

import com.cdqckj.gmis.cache.repository.RedisService;
import com.cdqckj.gmis.common.constant.CacheKey;
import com.cdqckj.gmis.lang.L18nMenuContainer;
import com.cdqckj.gmis.tenant.entity.Lang;
import com.cdqckj.gmis.tenant.entity.Tenant;
import com.cdqckj.gmis.tenant.service.LangService;
import com.cdqckj.gmis.tenant.service.TenantService;
import net.oschina.j2cache.CacheChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 语言包初始化
 * Date:2020.06.09 17:12:23
 * Author:Mr.Yang
 */
@Component
public class LangCommandLineRunner implements CommandLineRunner {
    @Autowired
    private LangService langService;
    @Autowired
    protected CacheChannel cacheChannel;
    @Autowired
    protected TenantService tenantService;
    @Autowired
    protected RedisService redisService;
    @Override
    public void run(String... args) throws Exception {
        System.out.println("-----------------------语言参数初始化------------------");
        List<Lang> list = langService.initLang();
        if(list == null || list.size() <= 0){ return;}
        for(Lang lang : list) {
            if (lang.getLangType() == 1) {L18nMenuContainer.getContainerByType(1).
                    put(lang.getLangKey(),lang.getLangValue());}
            if (lang.getLangType() == 2) {L18nMenuContainer.getContainerByType(2).
                    put(lang.getLangKey(),lang.getLangValue());}
        }
        redisService.hmset(CacheKey.LANG_EN, L18nMenuContainer.L18N_EN_MAP);
        redisService.hmset(CacheKey.LANG_ZH, L18nMenuContainer.L18N_ZH_MAP);
        // 获取所有租户
        List<Tenant> listTent = tenantService.getAllTenant();
        if (listTent == null || list.size() <= 0) {return;}
        for(Tenant tenant : listTent) {
            //将租户语言类型放入缓存
            L18nMenuContainer.LANG_MAP.put(CacheKey.LANG+tenant.getCode(),tenant.getLangType());
            redisService.set(CacheKey.LANG+tenant.getCode(),tenant.getLangType());
        }
    }
}
