package com.cdqckj.gmis.pay.storage;

import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.pay.dto.WxPaySaveDTO;
import com.cdqckj.gmis.pay.elias.util.JsonUtils;
import com.cdqckj.gmis.pay.entity.WxPay;
import com.cdqckj.gmis.pay.entity.WxRefund;
import com.cdqckj.gmis.pay.enumeration.BusinessTypeEnum;
import com.cdqckj.gmis.pay.enumeration.TradeTypeEnum;
import com.cdqckj.gmis.pay.strategy.PayStrategy;
import com.cdqckj.gmis.pay.util.WxPayAppConfig;
import com.cdqckj.gmis.systemparam.entity.PayInfo;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

import static com.cdqckj.gmis.utils.HttpClientUtils.doPostJson;

/**
 * 成都支付通支付
 */
@Slf4j
@Service
@Component("thiredPay")
public class ThiredPayStrategy implements PayStrategy {
    @Autowired
    private WxPayAppConfig config;
    @Override
    public Map<String, String> wechatScanCode(PayInfo payInfo, WxPay wxPaySaveDTO, HttpServletRequest request) throws Exception {
        //调用成都支付通
        String url = null;
        BigDecimal order_price = wxPaySaveDTO.getPay().multiply(new BigDecimal("100"));
        String orderPrice = order_price.stripTrailingZeros().toString();
        SortedMap<String, String> params = new TreeMap<>();
        params.put("txn_no", wxPaySaveDTO.getOrderNumber());
        params.put("mcht_no", payInfo.getThirdMerchantNo());
        params.put("txn_amt", orderPrice);
        params.put("sign_type", "MD5");
        params.put("attach", BaseContextHandler.getTenant());
        params.put("notify_url", Optional.ofNullable(wxPaySaveDTO.getNotifyUrl()).orElse(config.getDefaultUrl()));
        params.put("subject", wxPaySaveDTO.getOrderTitle());
        params.put("body", wxPaySaveDTO.getBody());
        params.put("nonce_str", UUID.randomUUID().toString());
        String sign = WXPayUtil.generateSignature(params, payInfo.getApiSecret(), WXPayConstants.SignType.MD5);
        params.put("sign", sign);
        TradeTypeEnum tradeType= TradeTypeEnum.get(wxPaySaveDTO.getTradeType());
        switch (tradeType){
            case JSAPI:
                params.put("business_type", BusinessTypeEnum.WECHAT_APPLET.getDesc());
                url = "https://qr.cdzft.cn/payfront/mobile/pay/wxMiniProgram";
                break;
            case APP:
                //支付通暂不支持app支付
                params.put("business_type", BusinessTypeEnum.WECHAT_APP.getDesc());
                url = "http://uchannel.payexpress.biz/payfront/json/trans/codeApp";
                break;
            case NATIVE:
                params.put("business_type", BusinessTypeEnum.AGGREGATE_SCANNING.getDesc());
                url = "http://uchannel.payexpress.biz/payfront/json/trans/codeIntegration";
                break;
            default:
                break;
        }
        JSONObject jsonObject = JSONObject.fromObject(params);
        String str = doPostJson(url,jsonObject.toString());
        return JsonUtils.stringToMap(str);
    }

    @Override
    public Map<String, String> wechatMicropay(PayInfo payInfo, WxPay wxPaySaveDTO, HttpServletRequest request) throws Exception {
        BigDecimal order_price = wxPaySaveDTO.getPay().multiply(new BigDecimal("100"));
        String orderPrice = order_price.stripTrailingZeros().toString();
        SortedMap<String, String> params = new TreeMap<>();
        params.put("txn_no", wxPaySaveDTO.getOrderNumber());
        params.put("mcht_no", payInfo.getThirdMerchantNo());
        params.put("business_type", BusinessTypeEnum.WECHAT_SCANNED.getDesc());
        params.put("txn_amt", orderPrice);
        params.put("subject", wxPaySaveDTO.getOrderTitle());
        params.put("body", wxPaySaveDTO.getBody());
        params.put("auth_code", wxPaySaveDTO.getAuthCode());
        params.put("nonce_str", UUID.randomUUID().toString());
        params.put("sign_type", "MD5");
        String sign = WXPayUtil.generateSignature(params, payInfo.getApiSecret(), WXPayConstants.SignType.MD5);
        params.put("sign", sign);
        JSONObject jsonObject = JSONObject.fromObject(params);
        String str = doPostJson("http://uchannel.payexpress.biz/payfront/json/trans/codeInitiative",jsonObject.toString());
        return JsonUtils.stringToMap(str);
    }

    @Override
    public Map<String, String> orderquery(PayInfo payInfo, WxPay wxPay) throws Exception {
        SortedMap<String, String> params = new TreeMap<>();
        params.put("txn_no", UUID.randomUUID().toString());
        params.put("pro_txn_no", wxPay.getOrderNumber());
        params.put("mcht_no", payInfo.getThirdMerchantNo());
        params.put("business_type", BusinessTypeEnum.ORDER_INQUIRY.getDesc());
        params.put("nonce_str", UUID.randomUUID().toString());
        params.put("sign_type", "MD5");
        String sign = WXPayUtil.generateSignature(params, payInfo.getApiSecret(), WXPayConstants.SignType.MD5);
        params.put("sign", sign);
        JSONObject jsonObject = JSONObject.fromObject(params);
        String str = doPostJson("http://uchannel.payexpress.biz/payfront/json/trans/orderDetailQuery",jsonObject.toString());
        return JsonUtils.stringToMap(str);
    }

    @Override
    public Map<String, String> weixinRefund(PayInfo payInfo, WxPay wxPayInfo, WxRefund wxRefund) throws Exception {
        return null;
    }

    @Override
    public Map<String, String> downloadWeixinBill(PayInfo payInfo, String billDate) {
        return null;
    }

    @Override
    public Map<String, String> refundQuery(PayInfo payInfo, String refundNumber) throws Exception {
        return null;
    }
}
