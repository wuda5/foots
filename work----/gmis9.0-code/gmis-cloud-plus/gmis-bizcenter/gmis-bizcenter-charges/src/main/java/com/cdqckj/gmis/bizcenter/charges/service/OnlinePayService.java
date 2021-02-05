package com.cdqckj.gmis.bizcenter.charges.service;

import com.cdqckj.gmis.base.service.SuperCenterService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface OnlinePayService extends SuperCenterService {
    /**
     * 订单支付后回调函数
     * @param result
     * @return
     * @throws Exception
     */
    String weixinNotify(String result) throws Exception;

    /**
     * 退款申请后的回调函数
     * @param result
     * @return
     * @throws Exception
     */
    String refundNotify(String result) throws Exception;
}
