package com.cdqckj.gmis.bizcenter.charges.controller;

import cn.afterturn.easypoi.entity.vo.NormalExcelConstants;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.bizcenter.charges.annotation.ParamValid;
import com.cdqckj.gmis.bizcenter.charges.service.ChargePayService;
import com.cdqckj.gmis.bizcenter.charges.service.ChargeService;
import com.cdqckj.gmis.bizcenter.charges.service.OnlinePayService;
import com.cdqckj.gmis.charges.api.ChargeRecordBizApi;
import com.cdqckj.gmis.charges.dto.ChargeCompleteDTO;
import com.cdqckj.gmis.common.utils.ExportUtil;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.log.annotation.SysLog;
import com.cdqckj.gmis.operateparam.PayInfoBizApi;
import com.cdqckj.gmis.pay.PayBizApi;
import com.cdqckj.gmis.pay.WxBillBizApi;
import com.cdqckj.gmis.pay.dto.WxBillPageDTO;
import com.cdqckj.gmis.pay.dto.WxPayPageDTO;
import com.cdqckj.gmis.pay.dto.WxPaySaveDTO;
import com.cdqckj.gmis.pay.dto.WxRefundPageDTO;
import com.cdqckj.gmis.pay.entity.WxBill;
import com.cdqckj.gmis.pay.entity.WxPay;
import com.cdqckj.gmis.pay.entity.WxRefund;
import com.cdqckj.gmis.readmeter.dto.ReadMeterDataPageDTO;
import com.cdqckj.gmis.readmeter.entity.ReadMeterData;
import com.cdqckj.gmis.systemparam.dto.PayInfoPageDTO;
import com.cdqckj.gmis.systemparam.entity.PayInfo;
import com.cdqckj.gmis.utils.RequestUtil;
import com.github.wxpay.sdk.WXPayUtil;
import feign.Response;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;


/**
 * <p>
 * 线上支付前端控制器
 * </p>
 * @author gmis
 * @date 2020-07-28
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/pay/onlinepay")
@Api(value = "onlinepay", tags = "在线支付")
//@PreAuth(replace = "onlinepay:")
public class OnlinePayController {

    @Autowired
    public PayBizApi payBizApi;
    @Autowired
    public WxBillBizApi wxBillBizApi;
    @Autowired
    public PayInfoBizApi payInfoBizApi;
    @Autowired
    private OnlinePayService onlinePayService;
    @Autowired
    private ChargePayService chargePayService;
    @Autowired
    private ChargeService chargeService;

    private final String[] PAYWAY = {"WECHAT","ALIPAY"};

    /**
     * 微信扫码支付、微信小程序支付、微信APP支付
     * @param wxPay
     * @return
     */
    @ParamValid
    @ApiOperation(value = "微信扫码支付(返回支付二维码)")
    @PostMapping("/onlinepay/wechatScanCode")
    public R<Map<String, String>> wechatScanCode(@RequestBody @Valid WxPay wxPay){
        return payBizApi.wechatScanCode(wxPay);
    }

    @ParamValid
    @ApiOperation(value = "微信小程序支付")
    @PostMapping("/onlinepay/wechatApplets")
    public R<Map<String, String>> wechatApplets(@RequestBody @Valid WxPay wxPay){
        return payBizApi.wechatApplets(wxPay);
    }

    @ParamValid
    @ApiOperation(value = "微信APP支付")
    @PostMapping("/onlinepay/wechatAppPay")
    public R<Map<String, String>> wechatAppPay(@RequestBody @Valid WxPay wxPay){
        return payBizApi.wechatAppPay(wxPay);
    }

    @ParamValid
    @ApiOperation(value = "微信付款码支付")
    @PostMapping("/onlinepay/wechatMicropay")
    public R<Map<String, String>> wechatMicropay(@RequestBody @Valid WxPay wxPay){
        return payBizApi.wechatMicropay(wxPay);
    }

    /**
     * 微信订单支付后回调函数
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "微信订单支付后回调函数", notes = "微信订单支付后回调函数")
    @PostMapping("/notify")
    @SysLog("微信订单支付后回调函数")
    @GlobalTransactional
    public String notify(HttpServletRequest request) throws Exception {
        String result = RequestUtil.requestToString(request);
        return onlinePayService.weixinNotify(result);
    }

    /**
     * 订单查询接口
     * @param wxPay
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "订单查询接口", notes = "订单查询接口")
    @PostMapping("/orderquery")
    @SysLog("订单查询接口")
    public R<Map<String, String>> orderquery(@RequestBody @Valid WxPay wxPay) throws Exception {
//        R<Map<String, String>> r= payBizApi.orderquery(wxPay);
        ChargeCompleteDTO dto=chargePayService.wxQueryByWeb(wxPay);
        if(dto.getChargeStatus()!=null){
            chargeService.chargeComplete(dto);
        }
        return R.success(dto.getWxQueryData());
    }

    @ApiOperation(value = "微信申请退款", notes = "微信申请退款")
    @PostMapping("/weixinRefund")
    @SysLog("微信申请退款")
    public R<Map<String, String>> weixinRefund(@RequestBody @Valid WxRefund wxRefund){
        return payBizApi.weixinRefund(wxRefund);
    }

    /**
     * 退款申请后的回调函数
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "退款申请后的回调函数", notes = "退款申请后的回调函数")
    @PostMapping("/refundNotify")
    @SysLog("退款申请后的回调函数")
    @GlobalTransactional
    public String refundNotify(HttpServletRequest request) throws Exception {
        String result = RequestUtil.requestToString(request);
        return onlinePayService.refundNotify(result);
    }

    /**
     * 订单退款查询接口
     * @param refund
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "订单退款查询接口", notes = "订单退款查询接口")
    @PostMapping("/refundQuery")
    @SysLog("订单退款查询接口")
    public R<Map<String, String>> refundQuery(@RequestBody @Valid WxRefundPageDTO refund) throws Exception {
        return payBizApi.refundQuery(refund);
    }

    /**
     * 微信查询交易账单
     * @param params
     * @return
     */
    @ApiOperation(value = "微信查询交易账单", notes = "微信查询交易账单")
    @PostMapping("/queryWeixinBill")
    @SysLog("微信查询交易账单")
    public R<Page<WxBill>> queryWeixinBill(@RequestBody PageParams<WxBillPageDTO> params) throws Exception{
        return payBizApi.queryWeixinBill(params);
    }

    /**
     * 微信下载交易账单
     * @param params
     * @param request
     * @param httpResponse
     * @throws IOException
     */
    @ApiOperation(value = "微信下载交易账单")
    @PostMapping("/wxBill/export")
    public void export(@RequestBody @Validated PageParams<WxBillPageDTO> params, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
        // feign文件下载
        //Optional.ofNullable(params.getModel().getBillDate()).orElseThrow(() -> new BizException("请传入账单日期"));
        Response response = wxBillBizApi.exportBill(params);
        String fileName = params.getMap().getOrDefault(NormalExcelConstants.FILE_NAME, "微信账单");
        ExportUtil.exportExcel(response,httpResponse,fileName);
    }

    @ApiOperation(value = "支付宝扫码支付(返回支付二维码)")
    @PostMapping("/alipay/alipayScanCode")
    public R<String> alipayScanCode(@RequestBody WxPaySaveDTO wxPaySaveDTO){
        return payBizApi.alipayScanCode(wxPaySaveDTO);
    }


    /**
     * 获取当前登录租户的支付配置信息
     * @return
     */
    private PayInfo getTenant(String payWay) {
        //获取当前登录租户编号
        String tenant = BaseContextHandler.getTenant();
        //当前租户有效的微信支付信息
        PayInfoPageDTO pageDto = new PayInfoPageDTO();
        pageDto.setPayCode(payWay);
        pageDto.setCompanyCode(tenant);
        pageDto.setDeleteStatus(0);
        PageParams<PayInfoPageDTO> params = new PageParams<>();
        params.setModel(pageDto);
        R<Page<PayInfo>> payInfo = payInfoBizApi.page(params);
        if(payInfo.getIsSuccess() && payInfo.getData().getTotal()>0){
            return payInfo.getData().getRecords().get(0);
        }
        return null;
    }


}
