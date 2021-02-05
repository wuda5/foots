package com.cdqckj.gmis.pay.strategy;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.pay.dto.WxPaySaveDTO;
import com.cdqckj.gmis.pay.entity.WxPay;
import com.cdqckj.gmis.pay.entity.WxRefund;
import com.cdqckj.gmis.systemparam.entity.PayInfo;
import com.cdqckj.gmis.systemparam.enumeration.InterfaceModeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
@Component
public class PayContext {
    @Autowired
    private PayStrategy payStrategy;
    @Autowired
    private StrategyFactory factory;

    public PayStrategy getStrategy(PayInfo payInfo) {
        return factory.create(InterfaceModeEnum.getType(payInfo.getInterfaceMode()).getCode());
    }

    public Map<String, String> wechatScanCode(PayInfo payInfo, WxPay wxPaySaveDTO, HttpServletRequest request) throws Exception{
        return getStrategy(payInfo).wechatScanCode(payInfo, wxPaySaveDTO, request);
    }

    public Map<String, String> wechatMicropay(PayInfo payInfo, WxPay wxPaySaveDTO, HttpServletRequest request) throws Exception{
        return getStrategy(payInfo).wechatMicropay(payInfo, wxPaySaveDTO, request);
    }

    public Map<String, String> orderquery(PayInfo payInfo, WxPay wxPay) throws Exception{
        return getStrategy(payInfo).orderquery(payInfo, wxPay);
    }

    public Map<String, String> weixinRefund(PayInfo payInfo, WxPay wxPayInfo, WxRefund wxRefund) throws Exception{
        return getStrategy(payInfo).weixinRefund(payInfo, wxPayInfo, wxRefund);
    }

    public Map<String, String> downloadWeixinBill(PayInfo payInfo, String billDate) throws Exception{
        return getStrategy(payInfo).downloadWeixinBill(payInfo, billDate);
    }

    public Map<String, String> refundQuery(PayInfo payInfo, String refundNumber) throws Exception{
        return getStrategy(payInfo).refundQuery(payInfo, refundNumber);
    }
}
