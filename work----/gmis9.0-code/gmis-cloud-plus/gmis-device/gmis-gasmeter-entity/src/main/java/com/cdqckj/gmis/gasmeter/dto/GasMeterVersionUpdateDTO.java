package com.cdqckj.gmis.gasmeter.dto;

import com.cdqckj.gmis.base.entity.SuperEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 实体类
 * 气表版本
 * </p>
 *
 * @author gmis
 * @since 2020-08-24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "GasMeterVersionUpdateDTO", description = "气表版本")
public class GasMeterVersionUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @NotNull(message = "id不能为空", groups = SuperEntity.Update.class)
    private Long id;

    /**
     * 版号名称
     */
    @ApiModelProperty(value = "版号名称")
    @Length(max = 30, message = "版号名称长度不能超过30")
    private String gasMeterVersionName;
    /**
     * 版号描述
     */
    @ApiModelProperty(value = "版号描述")
    @Length(max = 30, message = "版号描述长度不能超过30")
    private String gasMeterDescribe;
    /**
     * 生产厂家ID
     */
    @ApiModelProperty(value = "生产厂家ID")
    private Long companyId;
    /**
     * 生产厂家编号
     */
    @ApiModelProperty(value = "生产厂家编号")
    @Length(max = 10, message = "生产厂家编号长度不能超过10")
    private String companyCode;
    /**
     * 生产厂家名称
     */
    @ApiModelProperty(value = "生产厂家名称")
    @Length(max = 100, message = "生产厂家名称长度不能超过100")
    private String companyName;
    /**
     * 无线通信设备厂家名称
     */
    @ApiModelProperty(value = "无线通信设备厂家名称")
    @Length(max = 100, message = "无线通信设备厂家名称长度不能超过100")
    private String equipmentVendorsName;
    /**
     * 无线通信版本
     */
    @ApiModelProperty(value = "无线通信版本")
    @Length(max = 10, message = "无线通信版本长度不能超过10")
    private String wirelessVersion;
    /**
     * 订单来源名称
     */
    @ApiModelProperty(value = "订单来源名称")
    @NotEmpty(message = "订单来源名称不能为空")
    @Length(max = 20, message = "订单来源名称长度不能超过20")
    private String orderSourceName;
    /**
     * 金额标志
     */
    @ApiModelProperty(value = "金额标志")
    @Length(max = 200, message = "金额标志长度不能超过200")
    private String amountMark;
    /**
     * 远程业务标志
     */
    @ApiModelProperty(value = "远程业务标志")
    @Length(max = 225, message = "远程业务标志长度不能超过20")
    private String remoteServiceFlag;
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
    @Length(max = 30, message = "IC卡内核标识长度不能超过30")
    private String icCardCoreMark;
    /**
     * 计费精度（小数位数）
     */
    @ApiModelProperty(value = "计费精度（小数位数）")
    private Integer measurementAccuracy;
    /**
     * 是否选择地址：1:是；0-否
     */
    @ApiModelProperty(value = "是否选择地址：1:是；0-否")
    private Integer selectAddress;
    /**
     * 开启
     * 		禁用
     */
    @ApiModelProperty(value = "开启")
    private Integer versionState;
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
    @Length(max = 30, message = "卡号类型长度不能超过30")
    private String cardType;
    /**
     * 卡号前缀
     */
    @ApiModelProperty(value = "卡号前缀")
    @Length(max = 10, message = "卡号前缀长度不能超过10")
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

    /**
     * 如气表功能控制、兼容标识、兼容参数等。
     *             建议JSON存储
     */
    @ApiModelProperty(value = "如气表功能控制、兼容标识、兼容参数等。")
    @Length(max = 1000, message = "如气表功能控制、兼容标识、兼容参数等。长度不能超过1000")
    private String compatibleParameter;
    /**
     * 版号说明(备注)
     */
    @ApiModelProperty(value = "版号说明(备注)")
    @Length(max = 100, message = "版号说明(备注)长度不能超过100")
    private String remark;
    /**
     * 删除标识（0：存在；1-删除）
     */
    @ApiModelProperty(value = "删除标识（0：存在；1-删除）")
    private Integer deleteStatus;

    @ApiModelProperty(value = "是否录入底数：默认0;1、是;0、否")
    private Integer radix;

    @ApiModelProperty(value = "是否开阀：默认0;1、是;0、否")
    private Integer openValve;
}
