package com.cdqckj.gmis.gasmeter.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cdqckj.gmis.base.entity.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

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
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("pt_gas_meter_version")
@ApiModel(value = "GasMeterVersion", description = "气表版本")
@AllArgsConstructor
public class GasMeterVersion extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 版号名称
     */
    @ApiModelProperty(value = "版号名称")
    @Length(max = 30, message = "版号名称长度不能超过30")
    @TableField(value = "gas_meter_version_name", condition = LIKE)
    @Excel(name = "版号名称")
    private String gasMeterVersionName;

    /**
     * 版号描述
     */
    @ApiModelProperty(value = "版号描述")
    @Length(max = 30, message = "版号描述长度不能超过30")
    @TableField(value = "gas_meter_describe", condition = LIKE)
    @Excel(name = "版号描述")
    private String gasMeterDescribe;

    /**
     * 生产厂家ID
     */
    @ApiModelProperty(value = "生产厂家ID")
    @TableField("company_id")
    @Excel(name = "生产厂家ID")
    private Long companyId;

    /**
     * 生产厂家编号
     */
    @ApiModelProperty(value = "生产厂家编号")
    @Length(max = 10, message = "生产厂家编号长度不能超过10")
    @TableField(value = "company_code", condition = LIKE)
    @Excel(name = "生产厂家编号")
    private String companyCode;

    /**
     * 生产厂家名称
     */
    @ApiModelProperty(value = "生产厂家名称")
    @Length(max = 100, message = "生产厂家名称长度不能超过100")
    @TableField(value = "company_name", condition = LIKE)
    @Excel(name = "生产厂家名称")
    private String companyName;

    /**
     * 无线通信设备厂家名称
     */
    @ApiModelProperty(value = "无线通信设备厂家名称")
    @Length(max = 100, message = "无线通信设备厂家名称长度不能超过100")
    @TableField(value = "equipment_vendors_name", condition = LIKE)
    @Excel(name = "无线通信设备厂家名称")
    private String equipmentVendorsName;

    /**
     * 无线通信版本
     */
    @ApiModelProperty(value = "无线通信版本")
    @Length(max = 10, message = "无线通信版本长度不能超过10")
    @TableField(value = "wireless_version", condition = LIKE)
    @Excel(name = "无线通信版本")
    private String wirelessVersion;

    /**
     * 订单来源名称
     */
    @ApiModelProperty(value = "订单来源名称")
    @NotEmpty(message = "订单来源名称不能为空")
    @Length(max = 20, message = "订单来源名称长度不能超过20")
    @TableField(value = "order_source_name", condition = LIKE)
    @Excel(name = "订单来源名称")
    private String orderSourceName;

    /**
     * 金额标志
     */
    @ApiModelProperty(value = "金额标志")
    @Length(max = 200, message = "金额标志长度不能超过200")
    @TableField(value = "amount_mark", condition = LIKE)
    @Excel(name = "金额标志")
    private String amountMark;

    /**
     * 远程业务标志
     */
    @ApiModelProperty(value = "远程业务标志")
    @Length(max = 20, message = "远程业务标志长度不能超过20")
    @TableField(value = "remote_service_flag", condition = LIKE)
    @Excel(name = "远程业务标志")
    private String remoteServiceFlag;

    /**
     * 气量表金额位数
     */
    @ApiModelProperty(value = "气量表金额位数")
    @TableField("amount_digits")
    @Excel(name = "气量表金额位数")
    private Integer amountDigits;

    /**
     * 是否单阶金额表：1:是；0-否
     */
    @ApiModelProperty(value = "是否单阶金额表：1:是；0-否")
    @TableField("single_level_amount")
    @Excel(name = "是否单阶金额表：1:是；0-否")
    private Integer singleLevelAmount;

    /**
     * IC卡内核标识
     */
    @ApiModelProperty(value = "IC卡内核标识")
    @Length(max = 30, message = "IC卡内核标识长度不能超过30")
    @TableField(value = "ic_card_core_mark", condition = LIKE)
    @Excel(name = "IC卡内核标识")
    private String icCardCoreMark;

    /**
     * 计费精度（小数位数）
     */
    @ApiModelProperty(value = "计费精度（小数位数）")
    @TableField("measurement_accuracy")
    @Excel(name = "计费精度（小数位数）")
    private Integer measurementAccuracy;

    /**
     * 是否选择地址：1:是；0-否
     */
    @ApiModelProperty(value = "是否选择地址：1:是；0-否")
    @TableField("select_address")
    @Excel(name = "是否选择地址：1:是；0-否")
    private Integer selectAddress;

    /**
     * 开启
     * 		禁用
     */
    @ApiModelProperty(value = "开启")
    @TableField("version_state")
    @Excel(name = "开启")
    private Integer versionState;

    /**
     * 补气是否累加金额
     */
    @ApiModelProperty(value = "补气是否累加金额")
    @TableField("accumulated_amount")
    @Excel(name = "补气是否累加金额")
    private Integer accumulatedAmount;

    /**
     * 补气是否累加次数
     */
    @ApiModelProperty(value = "补气是否累加次数")
    @TableField("accumulated_count")
    @Excel(name = "补气是否累加次数")
    private Integer accumulatedCount;

    /**
     * 卡号类型
     */
    @ApiModelProperty(value = "卡号类型")
    @Length(max = 30, message = "卡号类型长度不能超过30")
    @TableField(value = "card_type", condition = LIKE)
    @Excel(name = "卡号类型")
    private String cardType;

    /**
     * 卡号前缀
     */
    @ApiModelProperty(value = "卡号前缀")
    @Length(max = 10, message = "卡号前缀长度不能超过10")
    @TableField(value = "card_number_prefix", condition = LIKE)
    @Excel(name = "卡号前缀")
    private String cardNumberPrefix;

    /**
     * 卡号长度
     */
    @ApiModelProperty(value = "卡号长度")
    @TableField("card_number_length")
    @Excel(name = "卡号长度")
    private Integer cardNumberLength;

    /**
     * 开户录入表号
     */
    @ApiModelProperty(value = "开户录入表号")
    @TableField("open_in_meter_number")
    @Excel(name = "开户录入表号")
    private Integer openInMeterNumber;

    /**
     * 是否发卡
     */
    @ApiModelProperty(value = "是否发卡")
    @TableField("issuing_cards")
    @Excel(name = "是否发卡")
    private Integer issuingCards;

    /**
     * 是否验证表号
     */
    @ApiModelProperty(value = "是否验证表号")
    @TableField("verification_table_no")
    @Excel(name = "是否验证表号")
    private Integer verificationTableNo;


    /**
     * 版号说明(备注)
     */
    @ApiModelProperty(value = "版号说明(备注)")
    @Length(max = 100, message = "版号说明(备注)长度不能超过100")
    @TableField(value = "remark", condition = LIKE)
    @Excel(name = "版号说明(备注)")
    private String remark;

    /**
     * 删除标识（0：存在；1-删除）
     */
    @ApiModelProperty(value = "删除标识（0：存在；1-删除）")
    @TableField("delete_status")
    @Excel(name = "删除标识（0：存在；1-删除）")
    private Integer deleteStatus;

    @ApiModelProperty(value = "是否录入底数：默认0;1、是;0、否")
    @TableField("radix")
    @Excel(name = "是否录入底数")
    private Integer radix;

    @ApiModelProperty(value = "是否开阀：默认0;1、是;0、否")
    @TableField("openValve")
    @Excel(name = "是否开阀")
    private Integer openValve;


    @Builder
    public GasMeterVersion(Long id, LocalDateTime createTime, Long createUser, LocalDateTime updateTime, Long updateUser, 
                    String gasMeterVersionName, String gasMeterDescribe, Long companyId, String companyCode, String companyName,
                    String equipmentVendorsName, String wirelessVersion, String orderSourceName, String amountMark, String remoteServiceFlag, Integer amountDigits, 
                    Integer singleLevelAmount, String icCardCoreMark, Integer measurementAccuracy, Integer selectAddress, Integer versionState, Integer accumulatedAmount, 
                    Integer accumulatedCount, String cardType, String cardNumberPrefix, Integer cardNumberLength, Integer openInMeterNumber, Integer issuingCards, 
                    Integer verificationTableNo, String remark, Integer deleteStatus) {
        this.id = id;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.gasMeterVersionName = gasMeterVersionName;
        this.gasMeterDescribe = gasMeterDescribe;
        this.companyId = companyId;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.equipmentVendorsName = equipmentVendorsName;
        this.wirelessVersion = wirelessVersion;
        this.orderSourceName = orderSourceName;
        this.amountMark = amountMark;
        this.remoteServiceFlag = remoteServiceFlag;
        this.amountDigits = amountDigits;
        this.singleLevelAmount = singleLevelAmount;
        this.icCardCoreMark = icCardCoreMark;
        this.measurementAccuracy = measurementAccuracy;
        this.selectAddress = selectAddress;
        this.versionState = versionState;
        this.accumulatedAmount = accumulatedAmount;
        this.accumulatedCount = accumulatedCount;
        this.cardType = cardType;
        this.cardNumberPrefix = cardNumberPrefix;
        this.cardNumberLength = cardNumberLength;
        this.openInMeterNumber = openInMeterNumber;
        this.issuingCards = issuingCards;
        this.verificationTableNo = verificationTableNo;
        this.remark = remark;
        this.deleteStatus = deleteStatus;
    }

}
