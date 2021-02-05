package com.cdqckj.gmis.biztemporary.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 气表详情
 */
@Data
public class GasMeterInfoVO {
    /**
     * 缴费 编号
     */
    private String customerChargeNo;

    /**
     * 表身号
     */
    private String gasMeterNumber;

    /**
     * 气表编号
     */
    private String gasCode;

    /**
     * 气表安装地址
     */
    private String gasMeterAddress;

    /**
     * 气表安装详细地址
     */
    private String moreGasMeterAddress;

    /**
     * 气表类型
     */
    private String gasMeterTypeCode;

    /**
     * 气表类型名
     */
    private String gasMeterTypeName;

    /**
     * 调压箱号
     */
    private String nodeNumber;

    /**
     * 开户时间
     */
    private String openAccountTime;

    /**
     * 客户名字
     */
    private String customerName;

    /**
     * 客户code
     */
    private String customerCode;


    /**
     * 客户id
     */
    private String customerId;

    /**
     * 电话号码
     */
    private String telphone;

    /**
     * 累计用气量
     */
    private Double totalUseGas;


    /**
     * 气表底数
     */
    private BigDecimal gasMeterBase;


    /**
     * 通气方向
     */
    private String direction;

    /**
     * 客户类型
     */
    private String customerTypeCode;

    /**
     * 通气时间
     */
    private Long ventilateTime;

    /**
     * 安装时间
     */
    private Long installTime;

    /**
     * 经度
     */
    private BigDecimal longitude;

    /**
     * 纬度
     */
    private BigDecimal latitude;
}
