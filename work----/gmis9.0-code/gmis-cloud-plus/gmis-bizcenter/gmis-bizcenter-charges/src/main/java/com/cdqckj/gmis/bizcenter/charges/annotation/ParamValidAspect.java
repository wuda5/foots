package com.cdqckj.gmis.bizcenter.charges.annotation;

import com.cdqckj.gmis.charges.api.ChargeRecordBizApi;
import com.cdqckj.gmis.charges.entity.ChargeRecord;
import com.cdqckj.gmis.pay.dto.WxPaySaveDTO;
import com.cdqckj.gmis.pay.entity.WxPay;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ParamValidAspect {

    @Autowired
    private ChargeRecordBizApi recordBizApi;

    @Before("@annotation(paramValid)")
    public void paramValid(JoinPoint point, ParamValid paramValid) throws Exception {
        Object[] args = point.getArgs();
        WxPay wxPaySaveDTO = (WxPay) args[0];
        String chargeNo = wxPaySaveDTO.getOrderNumber();
        ChargeRecord chargeRecord = recordBizApi.getChargeRecordByNo(chargeNo).getData();
        if(null!=chargeRecord){
            wxPaySaveDTO.setPay(chargeRecord.getActualIncomeMoney());
        }else{
            throw new RuntimeException("订单不存在");
        }
    }

}


