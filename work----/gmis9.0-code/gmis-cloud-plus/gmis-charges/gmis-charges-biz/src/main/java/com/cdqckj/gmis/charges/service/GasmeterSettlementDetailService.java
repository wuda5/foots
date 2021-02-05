package com.cdqckj.gmis.charges.service;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.charges.entity.GasmeterSettlementDetail;
import com.cdqckj.gmis.charges.vo.SettlementArrearsVO;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;

import java.math.BigDecimal;

/**
 * <p>
 * 业务接口
 * 气表结算明细
 * </p>
 *
 * @author tp
 * @date 2020-09-15
 */
public interface GasmeterSettlementDetailService extends SuperService<GasmeterSettlementDetail> {
    /**
     * 根据时间区间获取结算数据的欠费金额
     * @param arrearsVO
     * @return
     */
     R<BigDecimal> getSettlementArrears(SettlementArrearsVO arrearsVO);

     /**
     * @Author: lijiangguo
     * @Date: 2021/1/29 10:55
     * @remark 统计普表的用气量
      * @return
     */
    BigDecimal stsGeneralGasMeterUseGas(StsSearchParam stsSearchParam);
}
