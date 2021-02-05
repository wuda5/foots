package com.cdqckj.gmis.charges.service;

import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.charges.entity.GasmeterArrearsDetail;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 业务接口
 * 气表欠费明细
 * </p>
 *
 * @author tp
 * @date 2020-09-07
 */
public interface GasmeterArrearsDetailService extends SuperService<GasmeterArrearsDetail> {
    int updateChargeStatusComplete(List<Long> ids);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/7 11:09
    * @remark 统计欠费
     * @return
    */
    BigDecimal stsArrearsFee(StsSearchParam stsSearchParam);

    boolean checkExits(GasmeterArrearsDetail gasmeterArrearsDetail);

    boolean checkNB21Exits(GasmeterArrearsDetail gasmeterArrearsDetail);
    /**
     * 检测账单月份
     * @return
     */
    GasmeterArrearsDetail checkArrearsMonth(GasmeterArrearsDetail gasmeterArrearsDetail);

    Boolean updateBathArrears(List<Long> idsList);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/21 16:35
    * @remark 统计后费表
     * @return
    */
    BigDecimal stsAfterGasMeter(StsSearchParam stsSearchParam, String type);
}
