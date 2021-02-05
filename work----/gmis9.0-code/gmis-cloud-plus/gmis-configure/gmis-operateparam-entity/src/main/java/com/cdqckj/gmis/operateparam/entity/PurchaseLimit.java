package com.cdqckj.gmis.operateparam.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cdqckj.gmis.base.entity.Entity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import static com.cdqckj.gmis.utils.DateUtils.DEFAULT_DATE_TIME_FORMAT;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

/**
 * <p>
 * 实体类
 * 
 * </p>
 *
 * @author gmis
 * @since 2020-07-06
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("pz_purchase_limit")
@ApiModel(value = "PurchaseLimit", description = "")
@AllArgsConstructor
public class PurchaseLimit extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 公司编码
     */
    @ApiModelProperty(value = "公司编码")
    @Length(max = 32, message = "公司编码长度不能超过32")
    @TableField(value = "company_code", condition = LIKE)
    @Excel(name = "公司编码")
    private String companyCode;

    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    @Length(max = 100, message = "公司名称长度不能超过100")
    @TableField(value = "company_name", condition = LIKE)
    @Excel(name = "公司名称")
    private String companyName;

    /**
     * 组织ID
     */
    @ApiModelProperty(value = "组织ID")
    @TableField("org_id")
    @Excel(name = "组织ID")
    private Long orgId;

    /**
     * 限制名称
     */
    @ApiModelProperty(value = "限制名称")
    @Length(max = 50, message = "限制名称长度不能超过50")
    @TableField(value = "limit_name", condition = LIKE)
    @Excel(name = "限制名称")
    private String limitName;

    /**
     * 用气类型ID
     */
    @ApiModelProperty(value = "用气类型ID")
    @Length(max = 200, message = "用气类型名称长度不能超过200")
    @TableField("use_gas_type_id")
    @Excel(name = "用气类型ID")
    private String useGasTypeId;

    /**
     * 用气类型名称
     */
    @ApiModelProperty(value = "用气类型名称")
    @Length(max = 200, message = "用气类型名称长度不能超过200")
    @TableField(value = "use_gas_type_name", condition = LIKE)
    @Excel(name = "用气类型名称")
    private String useGasTypeName;

    /**
     * 1-个人  0-所有
     */
    @ApiModelProperty(value = "1-个人  0-所有")
    @TableField("limit_type")
    @Excel(name = "1-个人  0-所有")
    private Integer limitType;

    /**
     * 启用时间
     */
    @ApiModelProperty(value = "启用时间")
    @TableField("start_time")
    @Excel(name = "启用时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDate startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间")
    @TableField("end_time")
    @Excel(name = "结束时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDate endTime;

    /**
     * 周期
     */
    @ApiModelProperty(value = "周期")
    @TableField("cycle")
    @Excel(name = "周期")
    private Integer cycle;

    /**
     * 最大充值气量
     */
    @ApiModelProperty(value = "最大充值气量")
    @TableField("max_charge_gas")
    @Excel(name = "最大充值气量")
    private BigDecimal maxChargeGas;

    /**
     * 最大充值金额
     */
    @ApiModelProperty(value = "最大充值金额")
    @TableField("max_charge_money")
    @Excel(name = "最大充值金额")
    private BigDecimal maxChargeMoney;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    @TableField("data_status")
    @Excel(name = "状态")
    private Integer dataStatus;

    /**
     * 周期重复次数
     */
    @ApiModelProperty(value = "周期重复次数")
    @TableField("cycle_num")
    @Excel(name = "周期重复次数")
    private Integer cycleNum;

    @Builder
    public PurchaseLimit(Long id, LocalDateTime createTime, Long createUser, LocalDateTime updateTime, Long updateUser, 
                    String companyCode, String companyName, Long orgId, String limitName, String useGasTypeId,
                    String useGasTypeName, Integer limitType, LocalDate startTime, LocalDate endTime, Integer cycle, BigDecimal maxChargeGas,
                         BigDecimal maxChargeMoney, Integer dataStatus,Integer cycleNum) {
        this.id = id;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.orgId = orgId;
        this.limitName = limitName;
        this.useGasTypeId = useGasTypeId;
        this.useGasTypeName = useGasTypeName;
        this.limitType = limitType;
        this.startTime = startTime;
        this.endTime = endTime;
        this.cycle = cycle;
        this.maxChargeGas = maxChargeGas;
        this.maxChargeMoney = maxChargeMoney;
        this.dataStatus = dataStatus;
        this.cycleNum = cycleNum;
    }

}
