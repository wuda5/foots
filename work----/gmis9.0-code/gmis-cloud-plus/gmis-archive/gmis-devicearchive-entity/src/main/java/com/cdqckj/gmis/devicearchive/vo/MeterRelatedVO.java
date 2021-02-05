package com.cdqckj.gmis.devicearchive.vo;

import com.cdqckj.gmis.devicearchive.enumeration.RelateLevelType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 客户和表具关联关系对象
 *
 * @author hp
 * @since 2020-12-14
 */
@Data
public class MeterRelatedVO {

    private static final long serialVersionUID = 1L;

    /**
     * 客户档案ID
     */
    @ApiModelProperty(value = "客户档案编号")
    private String customerCode;
    /**
     * 气表档案ID
     */
    @ApiModelProperty(value = "气表档案编号")
    private String gasMeterCode;
    /**
     * 气表状态
     */
    @ApiModelProperty(value = "气表状态")
    private Integer gasMeterStatus;
    /**
     * 客户缴费编号
     */
    @ApiModelProperty(value = "客户缴费编号")
    private String customerChargeNo;

    /**
     * #RelateLevelType{MAIN_HOUSEHOLD:主户;QUOTE:引用;TENANT:租户;}
     */
    @ApiModelProperty(value = "主户_MAIN_HOUSEHOLD,引用_QUOTE,租户_TENANT,default_null")
    private RelateLevelType relatedLevel;

    /**
     * 抄表抵扣或银行代扣
     */
    @ApiModelProperty(value = "抄表抵扣或银行代扣")
    private Integer relatedDeductions;


    /**
     * 客户表具关系操作类型
     * 开户: OPEN_ACCOUNT
     * 过户: TRANS_ACCOUNT
     * 销户: DIS_ACCOUNT
     * 换表: CHANGE_METER
     */
    @ApiModelProperty(value = "客户表具关系操作类型")
    private String operType;

    /**
     * 数据状态 1 有效 0无效
     */
    @ApiModelProperty(value = "数据状态 1 有效 0无效")
    private Integer dataStatus;


}
