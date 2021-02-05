package com.cdqckj.gmis.charges.dao;

import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.charges.entity.GasmeterSettlementDetail;

import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * <p>
 * Mapper 接口
 * 气表结算明细
 * </p>
 *
 * @author tp
 * @date 2020-09-15
 */
@Repository
public interface GasmeterSettlementDetailMapper extends SuperMapper<GasmeterSettlementDetail> {

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/29 10:57
    * @remark 用气量统计
    */
    BigDecimal stsGeneralGasMeterUseGas(@Param("stsSearchParam") StsSearchParam stsSearchParam, @Param("dataScope") String dataScope);
}
