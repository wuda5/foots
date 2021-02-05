package com.cdqckj.gmis.executor.datasource;


import com.cdqckj.gmis.database.datasource.BaseMybatisConfiguration;
import com.cdqckj.gmis.database.properties.DatabaseProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 配置一些拦截器
 *
 * @author gmis
 * @createTime 2017-11-18 0:34
 */
@Configuration
@Slf4j
@EnableConfigurationProperties({DatabaseProperties.class})
public class ExecutorMybatisAutoConfiguration extends BaseMybatisConfiguration {


    public ExecutorMybatisAutoConfiguration(DatabaseProperties databaseProperties) {
        super(databaseProperties);

    }

//    /**
//     * 数据权限插件
//     *
//     * @return DataScopeInterceptor
//     */
//    @Bean
//    @ConditionalOnProperty(name = "gmis.database.isDataScope", havingValue = "true", matchIfMissing = true)
//    public DataScopeInterceptor dataScopeInterceptor() {
//        return new DataScopeInterceptor((userId) -> SpringUtils.getBean(UserService.class).getDataScopeById(userId));
//    }

}
