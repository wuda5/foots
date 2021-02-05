package com.cdqckj.gmis.cache;

import com.cdqckj.gmis.cache.lock.CaffeineDistributedLock;
import com.cdqckj.gmis.cache.repository.CacheRepository;
import com.cdqckj.gmis.cache.repository.CaffeineRepositoryImpl;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.cdqckj.gmis.cache.properties.CustomCacheProperties;
import com.cdqckj.gmis.lock.DistributedLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * 内存缓存配置
 *
 * @author gmis
 * @date 2019/08/07
 */
@Slf4j
@ConditionalOnProperty(name = "gmis.cache.type", havingValue = "CAFFEINE")
@EnableConfigurationProperties({CustomCacheProperties.class})
public class CaffeineAutoConfigure {

    @Autowired
    private CustomCacheProperties cacheProperties;

    /**
     * 为了解决演示环境启动报错而加的类
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public DistributedLock RedisDistributedLock() {
        return new CaffeineDistributedLock();
    }

    /**
     * caffeine 持久库
     *
     * @return the redis repository
     */
    @Bean
    @ConditionalOnMissingBean
    public CacheRepository redisRepository() {
        return new CaffeineRepositoryImpl();
    }

    @Bean
    @Primary
    public CacheManager caffeineCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();

        Caffeine caffeine = Caffeine.newBuilder()
                .recordStats()
                .initialCapacity(500)
                .expireAfterWrite(cacheProperties.getDef().getTimeToLive())
                .maximumSize(cacheProperties.getDef().getMaxSize());
        cacheManager.setAllowNullValues(true);
        cacheManager.setCaffeine(caffeine);

        //配置了这里，就必须实现在这里指定key 才能缓存
//        Map<String, CustomCacheProperties.Redis> configs = cacheProperties.getConfigs();
//        Optional.ofNullable(configs).ifPresent((config)->{
//            cacheManager.setCacheNames(config.keySet());
//        });
        return cacheManager;
    }

}

