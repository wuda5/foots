package com.cdqckj.gmis.authority.service.common;

import com.cdqckj.gmis.authority.entity.common.Area;
import com.cdqckj.gmis.base.service.SuperCacheService;

import java.util.List;

/**
 * <p>
 * 业务接口
 * 地区表
 * </p>
 *
 * @author gmis
 * @date 2019-07-02
 */
public interface AreaService extends SuperCacheService<Area> {

    /**
     * 递归删除
     *
     * @param ids
     * @return
     */
    boolean recursively(List<Long> ids);


    List<Area> queryEx();
}
