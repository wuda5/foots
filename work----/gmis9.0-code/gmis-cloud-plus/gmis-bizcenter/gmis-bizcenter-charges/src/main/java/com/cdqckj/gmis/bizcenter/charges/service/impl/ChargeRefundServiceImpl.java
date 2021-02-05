package com.cdqckj.gmis.bizcenter.charges.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.bizcenter.charges.service.ChargePayService;
import com.cdqckj.gmis.bizcenter.charges.service.ChargeRefundService;
import com.cdqckj.gmis.charges.dto.ChargeDTO;
import com.cdqckj.gmis.charges.dto.ChargeResultDTO;
import com.cdqckj.gmis.charges.dto.RefundCompleteDTO;
import com.cdqckj.gmis.charges.entity.ChargeRecord;
import com.cdqckj.gmis.charges.entity.ChargeRefund;
import com.cdqckj.gmis.charges.enums.ChargePayMethodEnum;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.pay.PayBizApi;
import com.cdqckj.gmis.pay.dto.WxRefundPageDTO;
import com.cdqckj.gmis.pay.entity.WxPay;
import com.cdqckj.gmis.pay.entity.WxRefund;
import com.cdqckj.gmis.utils.DateUtils;
import io.seata.common.util.CollectionUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 三方支付退费封装
 * @author tp
 * @date 2020-09-04
 */
@Service
@Log4j2
public class ChargeRefundServiceImpl implements ChargeRefundService {


    @Autowired
    PayBizApi payBizApi;

    /**
     * 柜台收费加载
     * @return
     */
    public void refund(ChargeRecord record, ChargeRefund refund, RefundCompleteDTO completeDTO) {
        completeDTO.setRefundNo(refund.getRefundNo());
        if (ChargePayMethodEnum.WECHATPAY.eq(record.getChargeMethodCode())) {
            wechatRefund(record,refund,completeDTO);
        }
    }

    public void wxRefundQuery(String refundNo,RefundCompleteDTO completeDTO){
        completeDTO.setRefundNo(refundNo);
        Map<String,String> wxqData=  payBizApi.refundQuery(WxRefundPageDTO.builder().outRefundNo(refundNo).build()).getData();
//                退款状态	refund_status_$n	是	String(16)	SUCCESS
//                SUCCESS—退款成功
//                REFUNDCLOSE—退款关闭。
//                PROCESSING—退款处理中
//                CHANGE—退款异常，退款到银行发现用户的卡作废或者冻结了，导致原路退款银行卡失败，可前往商户平台（pay.weixin.qq.com）-交易中心，手动处理此笔退款。$n为下标，从0开始编号。
        String wxRefundStatus=wxqData.getOrDefault("refund_status_0",null);
        if("SUCCESS".equals(wxRefundStatus)){
            completeDTO.setRefundStatus(true);
        }else  if("CHANGE".equals(wxRefundStatus) ){
            completeDTO.setRefundStatus(false);
            String wxMsg=wxqData.getOrDefault("err_code_des","未知");
            if(wxMsg==null){
                completeDTO.setRefundRemark(wxMsg);
            }
        }
    }

    private void wechatRefund(ChargeRecord record,ChargeRefund refund, RefundCompleteDTO completeDTO){
        WxRefund wxRefund=new WxRefund();
        wxRefund.setBody(refund.getBackReason());
        wxRefund.setOrderNumber(record.getChargeNo());
        wxRefund.setOutRefundNo(refund.getRefundNo());
        wxRefund.setRefundFee(record.getActualIncomeMoney());
        R<Map<String, String>> r=payBizApi.weixinRefund(wxRefund);
        if(!r.getIsSuccess()){
            throw BizException.wrap("发起微信退费失败:"+r.getMsg());
        }

        Map<String,String> wxData=r.getData();
        if(wxData==null){
            log.error("0.发起微信退费失败");
            throw BizException.wrap("发起微信退费失败");
        }
        log.info("微信退费响应数据："+JSONObject.toJSONString(wxData));
        String returnCode=wxData.getOrDefault("return_code",null);
        String resultCode=wxData.getOrDefault("result_code",null);
        if("SUCCESS".equals(returnCode)){
            if("SUCCESS".equals(resultCode)){
                //等待3s后查询
                try {
                    wait(3000);
                }catch (Exception e){}
                log.info("退款3s后查询退款结果");
                //查询退费结果
                Map<String,String> wxqData=  payBizApi.refundQuery(WxRefundPageDTO.builder().outRefundNo(refund.getRefundNo()).build()).getData();
//                退款状态	refund_status_$n	是	String(16)	SUCCESS
//                SUCCESS—退款成功
//                REFUNDCLOSE—退款关闭。
//                PROCESSING—退款处理中
//                CHANGE—退款异常，退款到银行发现用户的卡作废或者冻结了，导致原路退款银行卡失败，可前往商户平台（pay.weixin.qq.com）-交易中心，手动处理此笔退款。$n为下标，从0开始编号。

                String wxRefundStatus = wxqData.getOrDefault("refund_status_0",null);
                if ("SUCCESS".equals(wxRefundStatus)) {
                    completeDTO.setRefundStatus(true);
                } else if ("REFUNDCLOSE".equals(wxRefundStatus)) {
                    throw BizException.wrap("当前退款渠道关闭");
                } else if ("CHANGE".equals(wxRefundStatus)) {
                    completeDTO.setRefundStatus(false);
                    String wxMsg = wxqData.getOrDefault("err_code_des","未知");
                    if (wxMsg == null) {
                        completeDTO.setRefundRemark(wxMsg);
                    }
                } else if ("PROCESSING".equals(wxRefundStatus)) {
                    //需要轮询
                    completeDTO.setIsLoopRequest(true);
                }
            }else{
                String wxMsg=wxData.getOrDefault("err_code_des",null);
                if(wxMsg==null){
                    log.error("3.发起微信退费失败");
                    throw BizException.wrap("发起微信退费失败");
                }
                log.error("4.发起微信退费失败："+wxMsg);
                throw BizException.wrap("发起微信退费失败："+wxMsg);
            }
        }else{
            String wxMsg=wxData.getOrDefault("return_msg",null);
            if(wxMsg==null){
                log.error("1.发起微信退费失败");
                throw BizException.wrap("发起微信退费失败");
            }
            log.error("2.发起微信退费失败："+wxMsg);
            throw BizException.wrap("发起微信退费失败："+wxMsg);
        }



    }


}
