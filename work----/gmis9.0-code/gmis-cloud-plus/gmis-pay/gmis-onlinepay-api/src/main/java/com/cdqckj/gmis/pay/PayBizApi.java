package com.cdqckj.gmis.pay;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.charges.dto.ChargeCompleteDTO;
import com.cdqckj.gmis.charges.dto.ChargeRefundResDTO;
import com.cdqckj.gmis.charges.dto.RefundCompleteDTO;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.pay.dto.WxBillPageDTO;
import com.cdqckj.gmis.pay.dto.WxPayPageDTO;
import com.cdqckj.gmis.pay.dto.WxPaySaveDTO;
import com.cdqckj.gmis.pay.dto.WxRefundPageDTO;
import com.cdqckj.gmis.pay.entity.WxBill;
import com.cdqckj.gmis.pay.entity.WxPay;
import com.cdqckj.gmis.pay.entity.WxRefund;
import com.cdqckj.gmis.pay.hystrix.PayBizApiFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient(name = "${gmis.feign.onlinepay-server:gmis-onlinepay-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/wxPay", qualifier = "payBizApi")
public interface PayBizApi {

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    R<Map> getAllWxChangeDetial();

    /**
     * 微信扫码支付(返回支付二维码
     * @param wxPay
     * @return
     */
    @RequestMapping(value = "/wechatScanCode", method = RequestMethod.POST)
    R<Map<String, String>> wechatScanCode(WxPay wxPay);

    /**
     * 微信小程序支付
     * @param wxPay
     * @return
     */
    @RequestMapping(value = "/wechatApplets", method = RequestMethod.POST)
    R<Map<String, String>> wechatApplets(@RequestBody WxPay wxPay);

    /**
     * 微信APP支付
     * @param wxPay
     * @return
     */
    @RequestMapping(value = "/wechatAppPay", method = RequestMethod.POST)
    R<Map<String, String>> wechatAppPay(WxPay wxPay);

    /**
     * 微信付款码支付
     * @param wxPay
     * @return
     */
    @RequestMapping(value = "/wechatMicropay", method = RequestMethod.POST)
    R<Map<String, String>> wechatMicropay(WxPay wxPay);

    /**
     * 微信订单支付后回调函数
     * @return
     */
    @RequestMapping(value = "/weixinNotify", method = RequestMethod.POST)
    R<ChargeCompleteDTO> weixinNotify(String result);

    /**
     * orderquery
     * @param wxPay
     * @return
     */
    @RequestMapping(value = "/orderquery", method = RequestMethod.POST)
    R<Map<String, String>> orderquery(@RequestBody WxPay wxPay);

    /**
     * 微信查询交易账单
     * @param params
     * @return
     */
    @RequestMapping(value = "/queryWeixinBill", method = RequestMethod.POST)
    R<Page<WxBill>> queryWeixinBill(@RequestBody PageParams<WxBillPageDTO> params) throws Exception;

    /**
     * 微信查询交易账单
     * @param billDate
     * @return
     */
    @RequestMapping(value = "/queryAndSaveBill", method = RequestMethod.POST)
    R<Boolean> queryAndSaveBill(@RequestBody String billDate) throws Exception;

    /**
     * 微信申请退款
     * @param wxRefund
     * @return
     */
    @RequestMapping(value = "/weixinRefund", method = RequestMethod.POST)
    R<Map<String, String>> weixinRefund(@RequestBody WxRefund wxRefund);

    /**
     * 微信订单支付后回调函数
     * @return
     */
    @RequestMapping(value = "/refundNotify", method = RequestMethod.POST)
    R<RefundCompleteDTO> refundNotify(String result);

    /**
     * 微信退款查询
     * @param refund
     * @return
     */
    @RequestMapping(value = "/refundQuery", method = RequestMethod.POST)
    R<Map<String, String>> refundQuery(@RequestBody WxRefundPageDTO refund);

    /**
     * 支付宝扫码支付(返回支付二维码)
     * @param wxPayDTO
     * @return
     */
    @RequestMapping(value = "/alipayScanCode", method = RequestMethod.POST)
    R<String> alipayScanCode(WxPaySaveDTO wxPayDTO);
}
