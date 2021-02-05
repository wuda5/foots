package com.cdqckj.gmis.pay.storage;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.common.utils.StringUtil;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.pay.dto.WxPaySaveDTO;
import com.cdqckj.gmis.pay.elias.util.HttpUrlUtil;
import com.cdqckj.gmis.pay.elias.util.JsonUtils;
import com.cdqckj.gmis.pay.entity.WxBill;
import com.cdqckj.gmis.pay.entity.WxPay;
import com.cdqckj.gmis.pay.entity.WxRefund;
import com.cdqckj.gmis.pay.enumeration.TradeTypeEnum;
import com.cdqckj.gmis.pay.strategy.PayStrategy;
import com.cdqckj.gmis.pay.util.WxPayAppConfig;
import com.cdqckj.gmis.systemparam.entity.PayInfo;
import com.cdqckj.gmis.utils.DateUtils;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.cdqckj.gmis.pay.enumeration.TradeTypeEnum.JSAPI;
import static com.github.wxpay.sdk.WXPayUtil.MD5;

/**
 * 本地支付
 */
@Slf4j
@Component("nativePay")
@Primary
public class NativePayStrategy implements PayStrategy {
    @Autowired
    @Qualifier(value = "wxPayAppConfig")
    private WxPayAppConfig wxPayAppConfig;
    private static final String[] WEIXINWAY = {"SCANCODE","MICROPAY"};
    private WXPay wxpay = null;

    @Autowired
    public void getWxpay(){
        wxpay = new WXPay(wxPayAppConfig);
    }
    @Override
    public Map<String, String> wechatScanCode(PayInfo payInfo, WxPay wxPaySaveDTO, HttpServletRequest request) {
        return weixinPay(wxPaySaveDTO,payInfo, WEIXINWAY[0], request);
    }

    @Override
    public Map<String, String> wechatMicropay(PayInfo payInfo, WxPay wxPayDTO, HttpServletRequest request) {
        return weixinPay(wxPayDTO,payInfo,WEIXINWAY[1], request);
    }

    @Override
    public Map<String, String> orderquery(PayInfo payInfo, WxPay wxPay) {
        Map<String, String> data = new HashMap<>();
        data.put("out_trade_no", wxPay.getOrderNumber());
        data.put("sub_mch_id", payInfo.getNativeMerchantNo());
        try {
            return wxpay.orderQuery(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, String> weixinRefund(PayInfo payInfo, WxPay wxPayInfo, WxRefund wxRefund) throws Exception {
        Map<String, String> resultMap = new HashMap<>(16);
        String orderNo = wxRefund.getOrderNumber();
        String refundFee = getMoneyStr(wxRefund.getRefundFee());
        String orderPrice = getMoneyStr(wxPayInfo.getPay());
        String refundDesc = wxRefund.getRefundDesc();
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("out_trade_no", orderNo);
        requestMap.put("out_refund_no", wxRefund.getOutRefundNo());
        requestMap.put("total_fee", orderPrice);
        requestMap.put("refund_fee", refundFee);
        requestMap.put("sub_mch_id", payInfo.getNativeMerchantNo());
        requestMap.put("sub_appid", payInfo.getAppId());
        requestMap.put("notify_url", wxPayAppConfig.getRefundNotifyUrl());
        if(StringUtil.isNotEmpty(refundDesc)){
            requestMap.put("refund_desc", refundDesc);
        }
        try {
            resultMap = wxpay.refund(requestMap);
        } catch (Exception e) {
//            e.printStackTrace();
            log.error("发起微信退费失败",e);
            throw BizException.wrap("发起微信退费失败");
        }
        return resultMap;
    }

    public String getMoneyStr(BigDecimal pay) {
        BigDecimal order_price = pay.multiply(new BigDecimal("100"));
        return order_price.stripTrailingZeros().toPlainString();
    }

    @Override
    public Map<String, String> downloadWeixinBill(PayInfo payInfo, String billDate) throws Exception {
        SortedMap<String, String> params = new TreeMap<>();
        params.put("bill_date", billDate);
        params.put("bill_type", "ALL");
        Map<String, String> resultMap = wxpay.downloadBill(params);
        return resultMap;
    }

    @Override
    public Map<String, String> refundQuery(PayInfo payInfo, String refundNumber) throws Exception {
        Map<String, String> data = new HashMap<>();
        data.put("out_refund_no", refundNumber);
        data.put("sub_mch_id", payInfo.getNativeMerchantNo());
        try {
            return wxpay.refundQuery(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 微信扫码支付：放入参数，调用微信接口
     * @param wxPaySaveDTO
     * @param payInfo
     * @return
     */
    private Map<String, String> weixinPay(WxPay wxPaySaveDTO, PayInfo payInfo,String weixinPayWay, HttpServletRequest request){
        try{
            Map<String, String> resultMap = new HashMap<>(16);
            String orderNumner = wxPaySaveDTO.getOrderNumber();
            String orderPrice = getMoneyStr(wxPaySaveDTO.getPay());
            // 微信支付显示标题
            String body = wxPaySaveDTO.getOrderTitle();
            // 微信支交易订单号，不能重复
            SortedMap<String, String> params = new TreeMap<>();
            params.put("fee_type", "CNY");

            //params.put("nonce_str", UUID.randomUUID().toString());
            params.put("out_trade_no", orderNumner);
            params.put("body", wxPaySaveDTO.getBody());
            params.put("detail", wxPaySaveDTO.getDetail());
            params.put("time_start", wxPaySaveDTO.getTimeStart());
            params.put("time_expire", wxPaySaveDTO.getTimeExpire());
            params.put("spbill_create_ip", HttpUrlUtil.getIpAddr(request));
            params.put("total_fee", orderPrice);
            params.put("attach", BaseContextHandler.getTenant());
            params.put("sub_appid", payInfo.getAppId());
            params.put("sub_mch_id", payInfo.getNativeMerchantNo());
            TradeTypeEnum type = TradeTypeEnum.get(wxPaySaveDTO.getTradeType());
            switch (type){
                case JSAPI:
                    params.put("sub_openid", wxPaySaveDTO.getPayOpenid());
                    break;
                case NATIVE:
                    params.put("product_id", wxPaySaveDTO.getProductId());
                    break;
                default:
                    break;
            }
            if(weixinPayWay.equals(WEIXINWAY[1])){
                params.put("auth_code",wxPaySaveDTO.getAuthCode());
                resultMap = wxpay.microPay(params);
            }else{
                params.put("trade_type", wxPaySaveDTO.getTradeType());
                params.put("notify_url", Optional.ofNullable(wxPaySaveDTO.getNotifyUrl()).orElse(wxPayAppConfig.getDefaultUrl()));
                resultMap = wxpay.unifiedOrder(params);
            }
            System.out.println(resultMap.get("return_code")+"     "+resultMap.get("return_msg"));
            if(resultMap != null){
                Boolean bool = "SUCCESS".equals(resultMap.get("return_code"));
                if(bool && type.eq(JSAPI)){
                    SortedMap<String, String> map = new TreeMap<>();
                    map.put("appId", payInfo.getAppId());
                    String time = JsonUtils.getSecondTimestampTwo(new Date());
                    String nonceStr = UUID.randomUUID().toString().replaceAll("-","");
                    map.put("timeStamp", time);
                    map.put("nonceStr", nonceStr);
                    map.put("package", "prepay_id="+resultMap.get("prepay_id"));
                    map.put("signType", "MD5");
                    String appletsSign = WXPayUtil.generateSignature(map, wxPayAppConfig.getKey(), WXPayConstants.SignType.MD5);
                    resultMap.put("appletsSign", appletsSign);
                    resultMap.put("timeStamp", time);
                    resultMap.put("nonceStr", nonceStr);
                }
                // 前台添加定时器，进行轮询操作，直到支付完毕
                return resultMap;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成微信支付sign
     */
    public static String createSign(SortedMap<String, String> params, String key) throws Exception {
        StringBuilder sb = new StringBuilder();
        Set<Map.Entry<String, String>> es = params.entrySet();
        Iterator<Map.Entry<String, String>> it = es.iterator();
        while(it.hasNext()){
            Map.Entry<String, String> entry = it.next();
            String k = entry.getKey();
            String v = entry.getValue();
            if(null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)){
                sb.append(k + "=" + v + "&");
            }
        }
        if(null!=key){
            sb.append("key=").append(key);
        }
        String sign = MD5(sb.toString()).toUpperCase();

        return sign;
    }
}
