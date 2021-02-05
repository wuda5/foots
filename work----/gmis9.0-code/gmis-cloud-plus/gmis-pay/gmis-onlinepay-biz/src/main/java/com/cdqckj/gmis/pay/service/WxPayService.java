package com.cdqckj.gmis.pay.service;

import com.alipay.api.AlipayApiException;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.charges.dto.ChargeCompleteDTO;
import com.cdqckj.gmis.charges.dto.ChargeRecordResDTO;
import com.cdqckj.gmis.charges.dto.ChargeRefundResDTO;
import com.cdqckj.gmis.charges.dto.RefundCompleteDTO;
import com.cdqckj.gmis.pay.dto.WxBillPageDTO;
import com.cdqckj.gmis.pay.dto.WxPayDTO;
import com.cdqckj.gmis.pay.dto.WxPayPageDTO;
import com.cdqckj.gmis.pay.dto.WxPaySaveDTO;
import com.cdqckj.gmis.pay.entity.WxBill;
import com.cdqckj.gmis.pay.entity.WxPay;
import com.cdqckj.gmis.pay.entity.WxRefund;
import com.cdqckj.gmis.systemparam.entity.PayInfo;
import com.github.wxpay.sdk.WXPay;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 业务接口
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-06-04
 */
public interface WxPayService extends SuperService<WxPay> {
    /**
     * 微信扫码支付：生成订单二维码
     * @param wxPayDTO
     * @return
     */
    R<Map<String, String>> wechatScanCode(WxPay wxPayDTO, HttpServletRequest request) throws Exception;

    /**
     * 微信付款码支付
     * @param wxPayDTO
     * @return
     */
    R<Map<String, String>> wechatMicropay(WxPay wxPayDTO, HttpServletRequest request) throws Exception;
    /**
     * 微信app支付
     * @param wxPayDTO
     * @param request
     * @return
     * @throws Exception
     */
    R<Map<String, String>> wechatAppPay(@RequestBody WxPay wxPayDTO, HttpServletRequest request)throws Exception ;
    /**
     * 支付宝扫码支付：生成订单二维码
     * @param wxPayDTO
     * @return
     * @throws Exception
     */
    R<String> alipayScanCode(@RequestBody WxPayDTO wxPayDTO) throws Exception;

    /**
     * 微信退款
     * @param wxRefund
     * @return
     */
    R<Map<String, String>> weixinRefund(WxRefund wxRefund) throws Exception;

    /**
     * 微信查询交易账单
     * @param params
     * @return
     */
    R<IPage<WxBill>> queryWeixinBill(@RequestBody PageParams<WxBillPageDTO> params) throws Exception;

    public R<Boolean> queryAndSaveBill(@RequestBody String billDate) throws Exception;

    /**
     * 支付宝退款
     * @param wxPayDTO
     * @return
     * @throws AlipayApiException
     */
    R<String> alipayRefund(@RequestBody WxPayDTO wxPayDTO) throws AlipayApiException;

    /**
     * 订单支付后回调函数
     * @param data
     * @return
     * @throws Exception
     */
    R<ChargeCompleteDTO> weixinNotify(Map<String, String> data) throws Exception;

    R<RefundCompleteDTO> refundNotify(Map<String, String> data) throws Exception;

    /**
     * 微信查询订单
   * @param wxPay
     * @return
     */
    R<Map<String, String>> orderquery(@RequestBody WxPay wxPay) throws Exception;

    /**
     * 付款码支付：撤销订单
     * @param wxPay
     * @return
     */
    R<Map<String, String>> reverse(@RequestBody WxPay wxPay);

    //对return、notify参数进行验签
    boolean check(PayInfo payInfo, Map<String,String[]> requestParams) throws Exception;

    R<Map<String, String>> refundQuery(String wxPay) throws Exception;
}
