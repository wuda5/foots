package com.cdqckj.gmis.pay.annotation;

import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.pay.dto.WxPaySaveDTO;
import com.cdqckj.gmis.pay.entity.WxPay;
import com.cdqckj.gmis.pay.service.WxPayService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
@Slf4j
public class OrderNoValidAspect {

    @Autowired
    private WxPayService wxPayService;

    @Before("@annotation(orderNoValid)")
    public void paramValid(JoinPoint point, OrderNoValid orderNoValid) throws Exception {
        Object[] args = point.getArgs();
        WxPay wxPaySaveDTO = (WxPay) args[0];
        String orderNumber = wxPaySaveDTO.getOrderNumber();
        List<WxPay> list = wxPayService.list(Wraps.<WxPay>lbQ().eq(WxPay::getOrderNumber,orderNumber));
        if(list.size()>0){
            throw new RuntimeException("订单号重复");
        }
    }

}


