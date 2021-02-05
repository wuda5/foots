package com.cdqckj.gmis.devicearchive.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 厂家版号型号配置信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel("厂家版号型号配置信息")
public class GasMeterConfDTO {

    /**
     * 气表编号
     */
    @ApiModelProperty(value = "气表编号")
    private String gasMeterCode;

    /**
     * 用气类型ID
     */
    @ApiModelProperty(value = "用气类型ID")
    private Long useGasTypeId;

    /**
     * 生产厂家ID
     */
    @ApiModelProperty(value = "生产厂家ID")
    private Long factoryId;
    /**
     * 生产厂家编号
     */
    @ApiModelProperty(value = "生产厂家编号")
    private String gasMeterFactoryCode;

    /**
     * 厂家标识码
     */
    @ApiModelProperty(value = "厂家标识码:2、秦川")
    private String gasMeterFactoryMarkCode;
    /**
     * 生产厂家名称
     */
    @ApiModelProperty(value = "生产厂家名称")
    private String gasMeterFactoryName;

    /**
     * 版号ID
     */
    @ApiModelProperty(value = "版号ID")
    private Long versionId;

    /**
     * 版号名称
     */
    @ApiModelProperty(value = "版号名称")
    private String gasMeterVersionName;



    /**
     * 型号ID
     */
    @ApiModelProperty(value = "型号ID")
    private Long modelId;

    /**
     * 型号名称
     */
    @ApiModelProperty(value = "型号名称")
    private String modelName;

    /**
     * 型号规格
     */
    @ApiModelProperty(value = "型号规格")
    private String specification;


    /**
     * 无线通信设备厂家名称
     */
    @ApiModelProperty(value = "无线通信设备厂家名称")
    private String equipmentVendorsName;
    /**
     * 无线通信版本
     */
    @ApiModelProperty(value = "无线通信版本")
    private String wirelessVersion;
    /**
     * 订单来源名称
     */
    @ApiModelProperty(value = "订单来源名称")
    private String orderSourceName;
    /**
     * 金额标志
     */
    @ApiModelProperty(value = "金额标志")
    private String amountMark;

    /**
     * 气量表金额位数
     */
    @ApiModelProperty(value = "气量表金额位数")
    private Integer amountDigits;
    /**
     * 是否单阶金额表：1:是；0-否
     */
    @ApiModelProperty(value = "是否单阶金额表：1:是；0-否")
    private Integer singleLevelAmount;
    /**
     * IC卡内核标识
     */
    @ApiModelProperty(value = "IC卡内核标识")
    private String icCardCoreMark;

    /**
     * 补气是否累加金额
     */
    @ApiModelProperty(value = "补气是否累加金额")
    private Integer accumulatedAmount;
    /**
     * 补气是否累加次数
     */
    @ApiModelProperty(value = "补气是否累加次数")
    private Integer accumulatedCount;
    /**
     * 卡号类型
     */
    @ApiModelProperty(value = "卡号类型")
    private String cardType;
    /**
     * 卡号前缀
     */
    @ApiModelProperty(value = "卡号前缀")
    private String cardNumberPrefix;
    /**
     * 卡号长度
     */
    @ApiModelProperty(value = "卡号长度")
    private Integer cardNumberLength;
    /**
     * 开户录入表号
     */
    @ApiModelProperty(value = "开户录入表号")
    private Integer openInMeterNumber;
    /**
     * 是否发卡
     */
    @ApiModelProperty(value = "是否发卡")
    private Integer issuingCards;
    /**
     * 是否验证表号
     */
    @ApiModelProperty(value = "是否验证表号")
    private Integer verificationTableNo;
}
