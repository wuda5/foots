package com.cdqckj.gmis.devicearchive.config.datasource;

import cn.hutool.core.util.ArrayUtil;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusPropertiesCustomizer;
import com.cdqckj.gmis.database.datasource.BaseDatabaseConfiguration;
import com.cdqckj.gmis.database.properties.DatabaseProperties;
import com.p6spy.engine.spy.P6DataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

/**
 * gmis.database.multiTenantType != DATASOURCE 时，该类启用.
 * 此时，项目的多租户模式切换成：${gmis.database.multiTenantType}。
 * <p>
 * NONE("非租户模式"): 不存在租户的概念
 * COLUMN("字段模式"): 在sql中拼接 tenant_code 字段
 * SCHEMA("独立schema模式"): 在sql中拼接 数据库 schema
 * <p>
 * COLUMN和SCHEMA模式的实现 参考下面的 @see 中的3个类
 *
 * @author gmis
 * @date 2020-07-02
 * 断点查看原理：👇👇👇
 * @see com.cdqckj.gmis.database.datasource.BaseMybatisConfiguration#paginationInterceptor()
 * @see com.cdqckj.gmis.database.servlet.TenantContextHandlerInterceptor
 * @see com.cdqckj.gmis.database.parsers.DynamicTableNameParser
*/
@Configuration
@Slf4j
@MapperScan(
        basePackages = { "com.cdqckj.gmis", }, annotationClass = Repository.class,
        sqlSessionFactoryRef = DeviceArchiveDatabaseAutoConfiguration.DATABASE_PREFIX + "SqlSessionFactory")
@EnableConfigurationProperties({MybatisPlusProperties.class})
@ConditionalOnExpression("!'DATASOURCE'.equals('${gmis.database.multiTenantType}')")
public class DeviceArchiveDatabaseAutoConfiguration extends BaseDatabaseConfiguration {
    /**
     * 每个数据源配置不同即可
     */
    final static String DATABASE_PREFIX = "master";

    public DeviceArchiveDatabaseAutoConfiguration(MybatisPlusProperties properties,
                                                  DatabaseProperties databaseProperties,
                                                  ObjectProvider<Interceptor[]> interceptorsProvider,
                                                  ObjectProvider<TypeHandler[]> typeHandlersProvider,
                                                  ObjectProvider<LanguageDriver[]> languageDriversProvider,
                                                  ResourceLoader resourceLoader,
                                                  ObjectProvider<DatabaseIdProvider> databaseIdProvider,
                                                  ObjectProvider<List<ConfigurationCustomizer>> configurationCustomizersProvider,
                                                  ObjectProvider<List<MybatisPlusPropertiesCustomizer>> mybatisPlusPropertiesCustomizerProvider,
                                                  ApplicationContext applicationContext) {
        super(properties, databaseProperties, interceptorsProvider, typeHandlersProvider,
                languageDriversProvider, resourceLoader, databaseIdProvider,
                configurationCustomizersProvider, mybatisPlusPropertiesCustomizerProvider, applicationContext);
        log.debug("检测到 gmis.database.multiTenantType!=DATASOURCE，加载了 DeviceArchiveDatabaseAutoConfiguration");
    }

    @Bean(DATABASE_PREFIX + "SqlSessionTemplate")
    public SqlSessionTemplate getSqlSessionTemplate(@Qualifier(DATABASE_PREFIX + "SqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        ExecutorType executorType = this.properties.getExecutorType();
        if (executorType != null) {
            return new SqlSessionTemplate(sqlSessionFactory, executorType);
        } else {
            return new SqlSessionTemplate(sqlSessionFactory);
        }
    }

    /**
     * 数据源信息
     *
     * @return
     */
    @Primary
    @Bean(name = DATABASE_PREFIX + "DruidDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid")
    public DataSource druidDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = DATABASE_PREFIX + "DataSource")
    public DataSource dataSource(@Qualifier(DATABASE_PREFIX + "DruidDataSource") DataSource dataSource) {
        if (ArrayUtil.contains(DEV_PROFILES, this.profiles)) {
            return new P6DataSource(dataSource);
        } else {
            return dataSource;
        }
    }

    /**
     * mybatis Sql Session 工厂
     *
     * @return
     * @throws Exception
     */
    @Bean(DATABASE_PREFIX + "SqlSessionFactory")
    public SqlSessionFactory getSqlSessionFactory(@Qualifier(DATABASE_PREFIX + "DataSource") DataSource dataSource) throws Exception {
        return super.sqlSessionFactory(dataSource);
    }

}
