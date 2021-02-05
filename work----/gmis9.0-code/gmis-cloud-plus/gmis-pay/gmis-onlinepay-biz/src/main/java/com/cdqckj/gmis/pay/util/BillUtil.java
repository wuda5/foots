package com.cdqckj.gmis.pay.util;

import com.cdqckj.gmis.pay.enumeration.BillReturnCodeEnum;
import com.cdqckj.gmis.pay.enumeration.TradeTypeEnum;

public class BillUtil {
    public static Integer getType(String val){
        Integer type = BillReturnCodeEnum.match(val);
        if(null==type){
            type = TradeTypeEnum.getType(val);
        }
        return type;
    }
}
