package com.cdqckj.gmis.pay.controller;

import com.alipay.api.AlipayApiException;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.charges.dto.ChargeCompleteDTO;
import com.cdqckj.gmis.charges.dto.ChargeRefundResDTO;
import com.cdqckj.gmis.charges.dto.RefundCompleteDTO;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.log.annotation.SysLog;
import com.cdqckj.gmis.pay.dto.*;
import com.cdqckj.gmis.pay.entity.WxBill;
import com.cdqckj.gmis.pay.entity.WxPay;
import com.cdqckj.gmis.pay.entity.WxRefund;
import com.cdqckj.gmis.pay.enumeration.TradeTypeEnum;
import com.cdqckj.gmis.pay.service.WxBillService;
import com.cdqckj.gmis.pay.service.WxPayService;
import com.cdqckj.gmis.systemparam.entity.PayInfo;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 * 前端控制器
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-06-04
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/wxPay")
@Api(value = "WxPay", tags = "支付")
//@PreAuth(replace = "wxPay:")
public class WxPayController extends SuperController<WxPayService, String, WxPay, WxPayPageDTO, WxPaySaveDTO, WxPayUpdateDTO> {

    @Value("${server.port}")
    String port;
    @Autowired
    private WxPayService wxPayService;
    @Autowired
    private WxBillService wxBillService;
    @Autowired(required=false)
    private WXPay wxpay;

    private static final String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    @ApiOperation(value = "查询所有微信交易记录", notes = "查询所有微信交易记录")
    @GetMapping("/all")
    @SysLog("查询所有微信交易记录")
    public R<Map> findAllUserId() {
        log.info("Server port is "+port);
        Map map = new HashMap();
        map.put("payList",baseService.list());
        map.put("Server port",port);
        return success(map);
    }

    /**
     * 微信扫码支付、微信小程序支付、微信APP支付
     * 终端生成二维码接口（AppID-商户下的appid，MchID-服务商分配的商户号，Key-api密钥）
     * @param wxPayDTO
     * @return
     */
    @ApiOperation(value = "微信扫码支付：生成订单二维码", notes = "微信扫码支付：生成订单二维码")
    @PostMapping("/wechatScanCode")
    @SysLog("微信扫码支付：生成订单二维码")
    public R<Map<String, String>> wechatScanCode(@RequestBody WxPay wxPayDTO, HttpServletRequest request) throws Exception {
        wxPayDTO.setTradeType(TradeTypeEnum.NATIVE.getCode());
        return wxPayService.wechatScanCode(wxPayDTO, request);
    }

    @ApiOperation(value = "微信付款码支付", notes = "微信付款码支付")
    @PostMapping("/wechatMicropay")
    @SysLog("微信付款码支付")
    @GlobalTransactional
    public R<Map<String, String>> wechatAppletspay(@RequestBody WxPay wxPayDTO, HttpServletRequest request) throws Exception {
        wxPayDTO.setTradeType(TradeTypeEnum.MICROPAY.getCode());
        return wxPayService.wechatMicropay(wxPayDTO, request);
    }

    @ApiOperation(value = "微信小程序支付", notes = "微信小程序支付")
    @PostMapping("/wechatApplets")
    @SysLog("微信小程序支付")
    public R<Map<String, String>> wechatApplets(@RequestBody WxPay wxPayDTO, HttpServletRequest request) throws Exception {
        wxPayDTO.setTradeType(TradeTypeEnum.JSAPI.getCode());
        return wxPayService.wechatScanCode(wxPayDTO, request);
    }

    @ApiOperation(value = "微信APP支付", notes = "微信APP支付")
    @PostMapping("/wechatAppPay")
    @SysLog("微信APP支付")
    public R<Map<String, String>> wechatAppPay(@RequestBody WxPay wxPayDTO, HttpServletRequest request) throws Exception {
        wxPayDTO.setTradeType(TradeTypeEnum.APP.getCode());
        return wxPayService.wechatAppPay(wxPayDTO, request);
    }

    /**
     * 微信回调接口
     * @param result
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "订单支付后回调函数", notes = "订单支付后回调函数")
    @PostMapping("/weixinNotify")
    @SysLog("订单支付后回调函数")
    public R<ChargeCompleteDTO> weixinNotify(@RequestBody String result) throws Exception {
        Map<String, String> data = WXPayUtil.xmlToMap(result);
        String code = data.get("attach");
        BaseContextHandler.setTenant(code);
        return wxPayService.weixinNotify(data);
    }

    @ApiOperation(value = "订单查询接口", notes = "订单查询接口")
    @PostMapping("/orderquery")
    @SysLog("订单查询接口")
    public R<Map<String, String>> orderquery(@RequestBody WxPay wxPay) throws Exception {
        return wxPayService.orderquery(wxPay);
    }

    @ApiOperation(value = "付款码支付：撤销订单", notes = "付款码支付：撤销订单")
    @PostMapping("/reverse")
    @SysLog("付款码支付：撤销订单")
    public R<Map<String, String>> reverse(@RequestBody WxPay wxPay) throws Exception {
        return wxPayService.reverse(wxPay);
    }

    /**
     * 微信退款
     * @param wxRefund
     * @return
     */
    @ApiOperation(value = "微信退款", notes = "微信退款")
    @PostMapping("/weixinRefund")
    @SysLog("微信退款")
    public R<Map<String, String>> weixinRefund(@RequestBody @Valid WxRefund wxRefund) throws Exception {
        return wxPayService.weixinRefund(wxRefund);
    }

    /**
     * 订单支退款回调函数
     * @param result
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "订单支退款回调函数", notes = "订单支退款回调函数")
    @PostMapping("/refundNotify")
    @SysLog("订单支退款回调函数")
    public R<RefundCompleteDTO> refundNotify(@RequestBody String result) throws Exception {
        Map<String, String> data = WXPayUtil.xmlToMap(result);
        String code = data.get("attach");
        BaseContextHandler.setTenant(code);
        return wxPayService.refundNotify(data);
    }

    @ApiOperation(value = "微信支付查询退款", notes = "微信支付查询退款")
    @PostMapping("/refundQuery")
    @SysLog("微信支付查询退款")
    public R<Map<String, String>> refundQuery(@RequestBody WxRefundPageDTO refund) throws Exception {
        return wxPayService.refundQuery(refund.getOutRefundNo());
    }

    /**
     * 微信查询交易账单
     * @param params
     * @return
     */
    @ApiOperation(value = "微信查询交易账单", notes = "微信查询交易账单")
    @PostMapping("/queryWeixinBill")
    @SysLog("微信查询交易账单")
    public R<IPage<WxBill>> queryWeixinBill(@RequestBody PageParams<WxBillPageDTO> params) throws Exception{
        return wxPayService.queryWeixinBill(params);
    }

    /**
     * 微账单查询并保存
     * @param billDate
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "微账单查询并保存", notes = "微账单查询并保存")
    @PostMapping("/queryAndSaveBill")
    @SysLog("微账单查询并保存")
    public R<Boolean> queryAndSaveBill(@RequestBody String billDate) throws Exception{
        return wxPayService.queryAndSaveBill(billDate);
    }


    @ApiOperation(value = "支付宝扫码支付：生成订单二维码", notes = "支付宝扫码支付：生成订单二维码")
    @PostMapping("/alipayScanCode")
    @SysLog("支付宝扫码支付：生成订单二维码")
    public R<String> alipayScanCode(@RequestBody WxPayDTO wxPayDTO, HttpServletResponse response) throws Exception {
        return wxPayService.alipayScanCode(wxPayDTO);
    }

    /**
     * 支付宝退款
     * @return
     * @throws AlipayApiException
     */
    @ApiOperation(value = "支付宝退款", notes = "支付宝退款")
    @RequestMapping(value = "/alipayRefund")
    @SysLog("支付宝退款")
    //@ResponseBody
    public R<String> alipayRefund(@RequestBody WxPayDTO wxPayDTO) throws AlipayApiException {
        return wxPayService.alipayRefund(wxPayDTO);
    }

    //处理用户付款成功后的异步回调业务代码
    @ApiOperation(value = "异步回调", notes = "异步回调")
    @RequestMapping(value = "/alipayNotify")
    @SysLog("异步回调")
    public void notify(PayInfo payInfo,HttpServletRequest request) throws Exception{   //trade_success状态下异步通知接口
        if (wxPayService.check(payInfo,request.getParameterMap())){
            System.out.println(request.getParameter("trade_status"));
            System.out.println("异步通知 "+ Instant.now());
        }else {
            System.out.println("验签失败");
        }
    }

    //处理用户支付成功后的同步回调业务代码，用于给用户展示支付后的信息
  @ApiOperation(value = "同步回调", notes = "同步回调")
    @RequestMapping(value = "/alipayReturn")
    @SysLog("同步回调")
    public String returnUrl(PayInfo payInfo,HttpServletRequest request) throws Exception{  //订单支付成功后同步返回地址
        if (wxPayService.check(payInfo,request.getParameterMap())){
            return "success";
        }else {
            return "false";
        }
    }

}
