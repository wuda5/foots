package com.cdqckj.gmis.operateparam.dao;

import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.operateparam.entity.Penalty;

import com.cdqckj.gmis.operateparam.entity.PriceScheme;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-06-29
 */
@Repository
public interface PenaltyMapper extends SuperMapper<Penalty> {
    /**
     * 查询最新一条滞纳金记录
     * @return
     */
    Penalty queryRecentRecord();
}
