package com.cdqckj.gmis.entity.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class GasCardMeterInfoVO {

    private String nCardCode;
    //表身号
    private String niMeterID;
    //购气量
    private BigDecimal niBuyGas;
    //购气次数
    private Integer niBuyTimes;
    //气表类型
    private Integer niMeterType;

}
