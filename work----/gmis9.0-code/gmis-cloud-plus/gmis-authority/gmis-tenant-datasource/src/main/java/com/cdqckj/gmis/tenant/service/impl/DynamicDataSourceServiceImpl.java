package com.cdqckj.gmis.tenant.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.DruidDataSourceCreator;
import com.baomidou.dynamic.datasource.exception.ErrorCreateDataSourceException;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.cdqckj.gmis.tenant.service.DataSourceService;
import com.cdqckj.gmis.database.properties.DatabaseProperties;
import com.cdqckj.gmis.tenant.dto.DataSourceSaveDTO;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.Set;


/**
 * 数据源管理
 *
 * @author gmis
 * @date 2020年03月15日11:35:08
 */
@Service
@ConditionalOnProperty(prefix = DatabaseProperties.PREFIX, name = "multiTenantType", havingValue = "DATASOURCE")
@Log4j2
public class DynamicDataSourceServiceImpl implements DataSourceService {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private DruidDataSourceCreator druidDataSourceCreator;

    @Value("${gmis.mysql.database}")
    private String defaultDatabase;
    @Value("${gmis.mysql.driverClassName}")
    private String driverClassName;
    @Value("${gmis.mysql.username}")
    private String username;
    @Value("${gmis.mysql.password}")
    private String password;
    @Value("${gmis.mysql.url}")
    private String url;

    @Autowired
    private DatabaseProperties databaseProperties;

    @Override
    public void initDataSource(String tenant) {

        Set<String> all = all();
        // 如果已经加载这个数据库了，就不用了初始化数据了
        if (all.contains(tenant)){
            return;
        }
        DataSourceSaveDTO dto = new DataSourceSaveDTO();
        dto.setUsername(username);
        dto.setPassword(password);
        dto.setUrl(StrUtil.replace(url, defaultDatabase, databaseProperties.getTenantDatabasePrefix() + StrUtil.UNDERLINE + tenant));
        dto.setDriverClassName(driverClassName);
        dto.setPollName(tenant);
        this.saveDruid(dto);
    }

    @Override
    public Set<String> all() {
        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
        return ds.getCurrentDataSources().keySet();
    }

    @Override
    public Set<String> saveDruid(DataSourceSaveDTO dto) {
        DataSourceProperty dataSourceProperty = new DataSourceProperty();
        BeanUtils.copyProperties(dto, dataSourceProperty);
        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
        try {
            DataSource dataSource = druidDataSourceCreator.createDataSource(dataSourceProperty);
            ds.addDataSource(dto.getPollName(), dataSource);
        }catch (ErrorCreateDataSourceException e){
            log.error("数据库不存在,所以放弃加载数据库");
            log.error(e);
        }
        return ds.getCurrentDataSources().keySet();
    }

    @Override
    public Set<String> remove(String name) {
        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
        ds.removeDataSource(name);
        return ds.getCurrentDataSources().keySet();
    }

}
