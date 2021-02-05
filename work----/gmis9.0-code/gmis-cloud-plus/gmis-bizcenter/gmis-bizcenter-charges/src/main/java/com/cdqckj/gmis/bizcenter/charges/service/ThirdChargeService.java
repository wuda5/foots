package com.cdqckj.gmis.bizcenter.charges.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.bizcenter.charges.dto.AlipayBody;
import com.cdqckj.gmis.bizcenter.charges.dto.AlipayParam;
import com.cdqckj.gmis.bizcenter.charges.enumeration.ThirdChargeChannelEnum;
import com.cdqckj.gmis.charges.dto.*;

/**
 * 收费相关业务数据计算和校验以及数据存储
 * @author tp
 * @date 2020-09-04
 */
public interface ThirdChargeService {
    /**
     * 支付宝生活缴费-欠费查询
     * @return
     */
    R<AlipayBody> alipayChargeLoad(String customerChargeNo,String acctOrgNo);

    /**
     * 支付宝生活缴费-缴费销账
     * @param alipayParam
     * @return
     */
    R<AlipayBody>  alipayCharge(AlipayParam alipayParam) ;


}
