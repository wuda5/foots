package com.cdqckj.gmis.authority.dao.common;

import com.cdqckj.gmis.authority.entity.common.Area;
import com.cdqckj.gmis.base.mapper.SuperMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * 地区表
 * </p>
 *
 * @author gmis
 * @date 2019-07-02
 */
@Repository
public interface AreaMapper extends SuperMapper<Area> {

    /**
     * 获取具有详细地址的区域
     * @author hc
     * @return
     */
    List<Area> queryEx();
}
