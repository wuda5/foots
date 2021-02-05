package com.cdqckj.gmis.charges.dao;

import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.charges.entity.GasmeterArrearsDetail;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * 气表欠费明细
 * </p>
 *
 * @author tp
 * @date 2020-09-07
 */
@Repository
public interface GasmeterArrearsDetailMapper extends SuperMapper<GasmeterArrearsDetail> {
    int updateChargeStatusComplete(List<Long> ids);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/7 11:12
    * @remark 统计费用
    */
    BigDecimal stsArrearsFee(@Param("stsSearchParam") StsSearchParam stsSearchParam);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/21 16:45
    * @remark 物联网后付费表 普表后付费表
    */
    BigDecimal stsAfterGasMeter(@Param("stsSearchParam") StsSearchParam stsSearchParam, @Param("type") String type);
}
