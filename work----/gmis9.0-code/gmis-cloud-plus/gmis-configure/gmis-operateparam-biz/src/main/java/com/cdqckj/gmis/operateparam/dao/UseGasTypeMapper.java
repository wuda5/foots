package com.cdqckj.gmis.operateparam.dao;

import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.operateparam.entity.UseGasType;

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
public interface UseGasTypeMapper extends SuperMapper<UseGasType> {
    /**
     * 查询最新一条用气类型数据
     * @return
     */
    UseGasType queryRentUseGasType();
}
