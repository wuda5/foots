package com.cdqckj.gmis.entity.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 表具详情VO
 */
@Data
@Accessors(chain = true)
public class FeesGasMeterInfoVO {

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
}
