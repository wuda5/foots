package com.cdqckj.gmis.operationcenter.config.datasource;


import com.cdqckj.gmis.database.datasource.BaseMybatisConfiguration;
import com.cdqckj.gmis.database.mybatis.auth.DataScopeInterceptor;
import com.cdqckj.gmis.database.properties.DatabaseProperties;
import com.cdqckj.gmis.oauth.api.UserApi;
import com.cdqckj.gmis.utils.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * 工程服务API聚合服务-Mybatis 常用重用拦截器
 *
 * @author gmis
 * @date 2020-10-14
 */
@Configuration
@Slf4j
@EnableConfigurationProperties({DatabaseProperties.class})
public class OperationCenterMybatisAutoConfiguration extends BaseMybatisConfiguration {

    public OperationCenterMybatisAutoConfiguration(DatabaseProperties databaseProperties) {
        super(databaseProperties);
    }

    /**
     * 数据权限插件
     *
     * @return DataScopeInterceptor
     */
    @Order(10)
    @Bean
    @ConditionalOnProperty(prefix = DatabaseProperties.PREFIX, name = "isDataScope", havingValue = "true", matchIfMissing = true)
    public DataScopeInterceptor dataScopeInterceptor() {
        return new DataScopeInterceptor((userId) -> SpringUtils.getBean(UserApi.class).getDataScopeById(userId));
    }

}
