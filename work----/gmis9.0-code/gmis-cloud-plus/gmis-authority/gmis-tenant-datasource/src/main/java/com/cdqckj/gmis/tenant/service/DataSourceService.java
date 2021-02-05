package com.cdqckj.gmis.tenant.service;

import com.cdqckj.gmis.tenant.dto.DataSourceSaveDTO;

import java.util.Set;

/**
 * 数据源管理
 *
 * @author gmis
 * @date 2020年03月15日11:31:57
 */
public interface DataSourceService {
    /**
     * 查询所有的数据源
     *
     * @return
     */
    Set<String> all();

    /**
     * 新增Druid数据源
     *
     * @param dto
     * @return
     */
    Set<String> saveDruid(DataSourceSaveDTO dto);

    /**
     * 删除指定的数据源
     *
     * @param name
     * @return
     */
    Set<String> remove(String name);

    /**
     * 初始化行数据源
     *
     * @param tenant
     */
    void initDataSource(String tenant);
}
