package com.cdqckj.gmis.bizcenter.charges.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.charges.dto.*;
import com.cdqckj.gmis.pay.entity.WxPay;

/**
 * 收费支付封装
 * @author tp
 * @date 2020-09-04
 */
public interface ChargePayService {

    void pay(ChargeResultDTO resultDTO,ChargeDTO infoDTO);
    ChargeCompleteDTO wxQueryByLoad(String chargeNo);
    ChargeCompleteDTO wxQueryByWeb(WxPay wxPay);
}
