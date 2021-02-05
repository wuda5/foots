package com.cdqckj.gmis.charges.service;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.charges.dto.ChargeCompleteDTO;
import com.cdqckj.gmis.charges.dto.ChargeDTO;
import com.cdqckj.gmis.charges.dto.ChargeResultDTO;

/**
 * 收费相关业务数据计算和校验以及数据存储
 * @author tp
 * @date 2020-09-04
 */
public interface ChargeService {
    /**
     * 缴费
     * @param infoDTO
     * @return
     */
    R<ChargeResultDTO> charge(ChargeDTO infoDTO) ;

    /**
     * 修改收费中状态
     * @param chargeNo
     * @return
     */
    R<Boolean> charging(String chargeNo);

    /**
     * 异步回调缴费完成动作
     * @param dto
     * @return
     */
    R<ChargeResultDTO> chargeComplete(ChargeCompleteDTO dto);

    /**
     * 处理未完成的充值记录
     * @param gasMeterCode
     * @return
     */
    R<Boolean> dealUnCompleteRecord(String gasMeterCode);
}
