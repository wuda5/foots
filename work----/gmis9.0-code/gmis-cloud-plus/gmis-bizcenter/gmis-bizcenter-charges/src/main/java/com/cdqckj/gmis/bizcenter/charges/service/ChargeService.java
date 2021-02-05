package com.cdqckj.gmis.bizcenter.charges.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.charges.dto.*;

/**
 * 收费相关业务数据计算和校验以及数据存储
 * @author tp
 * @date 2020-09-04
 */
public interface ChargeService {

    R<ChargeLoadDTO> chargeLoad(ChargeLoadReqDTO param);

    /**
     * 缴费
     * @param infoDTO
     * @return
     */
    R<ChargeResultDTO> charge(ChargeDTO infoDTO) ;

    /**
     * 三方支付回调
     * @param infoDTO
     * @return
     */
    R<ChargeResultDTO> chargeComplete(ChargeCompleteDTO infoDTO) ;


    R<Page<ChargeRecordResDTO>> pageList(PageParams<ChargeListReqDTO> params);
}
