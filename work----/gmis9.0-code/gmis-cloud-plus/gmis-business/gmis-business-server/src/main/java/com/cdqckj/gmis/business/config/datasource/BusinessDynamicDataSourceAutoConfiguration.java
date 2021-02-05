package com.cdqckj.gmis.business.config.datasource;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceCreatorAutoConfiguration;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.druid.DruidDynamicDataSourceConfiguration;
import com.cdqckj.gmis.database.datasource.DynamicDataSourceAutoConfiguration;
import com.cdqckj.gmis.database.properties.DatabaseProperties;
import com.cdqckj.gmis.tenant.context.InitDatabaseOnStarted;
import com.cdqckj.gmis.tenant.strategy.impl.DatasourceInitSystemStrategy;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Repository;

/**
 * gmis.database.multiTenantType = DATASOURCE 时，该类启用.
 * 此时，项目的多租户模式切换成：动态切换数据源模式。
 * <p>
 * 即：每个租户链接独立的一个数据源，每个请求的请求头中需要携带的租户编码，在每个服务的拦截器(TenantContextHandlerInterceptor)中,将租户编码封装到 当前线程变量（ThreadLocal），
 * 在mybatis 执行sql前，通过 DsThreadProcessor 类获取到ThreadLocal中的租户编码，动态切换数据源
 * <p>
 * 下面的每个注解讲解：
 * <p>
 * ConditionalOnProperty:  gmis.database.multiTenantType=DATASOURCE 时，加载这个类，并执行下面的注解
 * Configuration：标记为配置类
 * EnableConfigurationProperties： 使 DynamicDataSourceProperties 类注入Spring。
 * AutoConfigureBefore： AuthorityDynamicDataSourceAutoConfiguration 将会在 DataSourceAutoConfiguration 类之前加载
 * Import：加载 DruidDynamicDataSourceConfiguration、DynamicDataSourceCreatorAutoConfiguration
 * EnableAutoConfiguration：排除 DruidDataSourceAutoConfigure
 * MapperScan：扫描 com.cdqckj.gmis 包下标记了Repository 注解的类为 Mybatis 的代理接口
 * <p>
 *
 * @author gmis
 * @date 2020年04月01日14:50:55
 * 断点查看原理：👇👇👇
 * @see com.baomidou.dynamic.datasource.provider.DynamicDataSourceProvider
 * @see com.baomidou.dynamic.datasource.strategy.DynamicDataSourceStrategy
 * @see com.baomidou.dynamic.datasource.DynamicRoutingDataSource
 */
@ConditionalOnProperty(prefix = DatabaseProperties.PREFIX, name = "multiTenantType", havingValue = "DATASOURCE")
@Configuration
@EnableConfigurationProperties(DynamicDataSourceProperties.class)
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
@Import(value = {DruidDynamicDataSourceConfiguration.class, DynamicDataSourceCreatorAutoConfiguration.class})
@EnableAutoConfiguration(exclude = {DruidDataSourceAutoConfigure.class})
@MapperScan(basePackages = {"com.cdqckj.gmis",}, annotationClass = Repository.class)
public class BusinessDynamicDataSourceAutoConfiguration extends DynamicDataSourceAutoConfiguration {

    public BusinessDynamicDataSourceAutoConfiguration(DynamicDataSourceProperties properties) {
        super(properties);
    }

    @Bean
    public InitDatabaseOnStarted getInitDatabaseOnStarted(DatasourceInitSystemStrategy initSystemContext) {
        return new InitDatabaseOnStarted(initSystemContext);
    }
}
