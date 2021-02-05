package com.cdqckj.gmis.pay.strategy;

import com.cdqckj.gmis.pay.dto.WxPaySaveDTO;
import com.cdqckj.gmis.pay.entity.WxPay;
import com.cdqckj.gmis.pay.entity.WxRefund;
import com.cdqckj.gmis.systemparam.entity.PayInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface PayStrategy {
    /**
     * 微信扫码支付：生成订单二维码
     * @param wxPayDTO
     * @return
     */
    Map<String, String> wechatScanCode(PayInfo payInfo, WxPay wxPayDTO, HttpServletRequest request) throws Exception;
    /**
     * 微信付款码支付
     * @param wxPayDTO
     * @return
     */
    Map<String, String> wechatMicropay(PayInfo payInfo, WxPay wxPayDTO, HttpServletRequest request) throws Exception;
    /**
     * 微信查询订单
     * @param wxPay
     * @return
     */
    Map<String, String> orderquery(PayInfo payInfo, WxPay wxPay) throws Exception;

    Map<String, String> weixinRefund(PayInfo payInfo, WxPay wxPayInfo, WxRefund wxRefund) throws Exception;

    /**
     * 微信下载交易账单
     * @param payInfo
     * @param billDate
     * @return
     */
    Map<String, String> downloadWeixinBill(PayInfo payInfo, String billDate) throws Exception;

    Map<String, String> refundQuery(PayInfo payInfo, String refundNumber)  throws Exception;
}
