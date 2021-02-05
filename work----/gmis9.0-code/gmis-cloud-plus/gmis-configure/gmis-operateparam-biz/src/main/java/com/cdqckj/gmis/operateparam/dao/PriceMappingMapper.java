package com.cdqckj.gmis.operateparam.dao;

import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.operateparam.entity.PriceMapping;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * 价格方案映射表
 * </p>
 *
 * @author gmis
 * @date 2020-12-03
 */
@Repository
public interface PriceMappingMapper extends SuperMapper<PriceMapping> {

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/3 14:32
    * @remark 价格方案映射
    */
    PriceMapping getGasMeterPriceMapping(@Param("gasCode") String gasCode);
}
