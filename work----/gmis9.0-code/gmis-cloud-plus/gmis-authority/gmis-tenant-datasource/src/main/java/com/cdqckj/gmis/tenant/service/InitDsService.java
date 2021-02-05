package com.cdqckj.gmis.tenant.service;


/**
 * 广播初始化数据源
 *
 * @author gmis
 * @date 2020年04月05日16:27:03
 */
public interface InitDsService {
    /**
     * 初始化 数据源
     *
     * @param tenant 租户
     * @return 是否成功
     */
    boolean initDataSource(String tenant);

    /**
     * 删除数据源
     *
     * @param tenant
     * @return
     */
    boolean removeDataSource(String tenant);
}
