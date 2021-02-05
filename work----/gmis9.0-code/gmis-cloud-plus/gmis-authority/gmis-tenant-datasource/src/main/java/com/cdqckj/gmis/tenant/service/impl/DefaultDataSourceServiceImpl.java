package com.cdqckj.gmis.tenant.service.impl;

import com.cdqckj.gmis.tenant.service.DataSourceService;
import com.cdqckj.gmis.database.properties.DatabaseProperties;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.tenant.dto.DataSourceSaveDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Service;

import java.util.Set;


/**
 * 数据源管理
 *
 * @author gmis
 * @date 2020年03月15日11:35:08
 */
@Service
@ConditionalOnExpression("!'DATASOURCE'.equals('${gmis.database.multiTenantType}')")
@Slf4j
public class DefaultDataSourceServiceImpl implements DataSourceService {

    @Autowired
    private DatabaseProperties databaseProperties;

    @Override
    public Set<String> all() {
        throw BizException.wrap("%s(%s)模式不允许该操作", databaseProperties.getMultiTenantType().name(), databaseProperties.getMultiTenantType().getDescribe());
    }

    @Override
    public Set<String> saveDruid(DataSourceSaveDTO dto) {
        throw BizException.wrap("%s(%s)模式不允许该操作", databaseProperties.getMultiTenantType().name(), databaseProperties.getMultiTenantType().getDescribe());
    }

    @Override
    public Set<String> remove(String name) {
        throw BizException.wrap("%s(%s)模式不允许该操作", databaseProperties.getMultiTenantType().name(), databaseProperties.getMultiTenantType().getDescribe());
    }

    @Override
    public void initDataSource(String tenant) {
        throw BizException.wrap("%s(%s)模式不允许该操作", databaseProperties.getMultiTenantType().name(), databaseProperties.getMultiTenantType().getDescribe());
    }
}
