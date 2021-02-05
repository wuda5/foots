package com.cdqckj.gmis.bizcenter.charges.service.impl;

import com.cdqckj.gmis.base.service.SuperCenterServiceImpl;
import com.cdqckj.gmis.bizcenter.charges.service.ChargeService;
import com.cdqckj.gmis.bizcenter.charges.service.OnlinePayService;
import com.cdqckj.gmis.bizcenter.charges.service.RefundService;
import com.cdqckj.gmis.charges.dto.ChargeCompleteDTO;
import com.cdqckj.gmis.charges.dto.RefundCompleteDTO;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.pay.PayBizApi;
import com.github.wxpay.sdk.WXPayUtil;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@Service
@Log4j2
public class OnlinePayServiceImpl extends SuperCenterServiceImpl implements OnlinePayService {

    @Autowired
    public PayBizApi payBizApi;
    @Autowired
    public ChargeService chargeService;
    @Autowired
    public RefundService refundService;
    @Override
    public String weixinNotify(String result) throws Exception {
        log.info("支付收费回调"+result);
        Map<String, String> wxMap=WXPayUtil.xmlToMap(result);
        ChargeCompleteDTO dto =  payBizApi.weixinNotify(result).getData();

        new Thread(new weixinNotifyCall(dto)).start();
//        chargeService.chargeComplete(dto);
        return dto.getReturnXmlData();
    }

    /**
     * 以下只是付款码退费回调业务逻辑处理。
     * @param result
     * @return
     * @throws Exception
     */
    @Override
    public String refundNotify(String result) throws Exception {
        log.info("退费回调"+result);

        RefundCompleteDTO dto =  payBizApi.refundNotify(result).getData();
        Map<String, String> resultData = new HashMap<>();
        //这里捕获，防止通知数据被回滚，如果要回滚，就全局捕获，微信支持如下返回信息：
//        refund_status
//        SUCCESS-退款成功
//        CHANGE-退款异常
//        REFUNDCLOSE—退款关闭
        Map<String,String> wxData=dto.getReturnXmlData();
        try {
            String refundStatus=wxData.getOrDefault("refund_status",null);
            if("SUCCESS".equals(refundStatus)){
                dto.setRefundStatus(true);
                dto.setRefundNo(wxData.getOrDefault("out_refund_no",null));
                refundService.refundComplete(dto);
            }else if("CHANGE".equals(refundStatus)){
                dto.setRefundStatus(false);
                dto.setRefundNo(wxData.getOrDefault("out_refund_no",null));
                refundService.refundComplete(dto);
            }
            resultData.put("return_msg", "ok");
            resultData.put("return_code", "SUCCESS");
        }catch (Exception e){
            log.error("业务处理异常",e);
            resultData.put("return_code", "FAIL");
            resultData.put("return_msg", "业务处理异常");
        }
        return  WXPayUtil.mapToXml(resultData);
    }


    class weixinNotifyCall implements Runnable{

        private ChargeCompleteDTO dto;

        weixinNotifyCall(ChargeCompleteDTO dto){
            this.dto = dto;
        }

        @Override
        @GlobalTransactional
        public void run() {
            BaseContextHandler.setTenant(dto.getCode());
            chargeService.chargeComplete(dto);
        }
    }

}
