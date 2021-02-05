package com.cdqckj.gmis.charges.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cdqckj.gmis.base.entity.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;
import static com.cdqckj.gmis.utils.DateUtils.DEFAULT_DATE_TIME_FORMAT;

/**
 * <p>
 * 实体类
 * 
 * </p>
 *
 * @author gmis
 * @since 2020-12-29
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("gt_adjust_calculation_record")
@ApiModel(value = "AdjustCalculationRecord", description = "")
@AllArgsConstructor
public class AdjustCalculationRecord extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间")
    @TableField("start_time")
    @Excel(name = "开始时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间")
    @TableField("end_time")
    @Excel(name = "结束时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime endTime;

    /**
     * 用气类型ID
     */
    @ApiModelProperty(value = "用气类型ID")
    @TableField("use_gas_type_id")
    @Excel(name = "用气类型ID")
    private Long useGasTypeId;

    /**
     * 用气类型名称
     */
    @ApiModelProperty(value = "用气类型名称")
    @Length(max = 100, message = "用气类型名称长度不能超过100")
    @TableField(value = "use_gas_type_name", condition = LIKE)
    @Excel(name = "用气类型名称")
    private String useGasTypeName;

    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    @Length(max = 32, message = "客户编号长度不能超过32")
    @TableField(value = "customer_code", condition = LIKE)
    @Excel(name = "客户编号")
    private String customerCode;

    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    @Length(max = 100, message = "客户名称长度不能超过100")
    @TableField(value = "customer_name", condition = LIKE)
    @Excel(name = "客户名称")
    private String customerName;

    /**
     * 表身号
     */
    @ApiModelProperty(value = "表身号")
    @Length(max = 32, message = "表身号长度不能超过32")
    @TableField(value = "body_number", condition = LIKE)
    @Excel(name = "表身号")
    private String bodyNumber;

    /**
     * 安装地址
     */
    @ApiModelProperty(value = "安装地址")
    @Length(max = 100, message = "安装地址长度不能超过100")
    @TableField(value = "gas_meter_address", condition = LIKE)
    @Excel(name = "安装地址")
    private String gasMeterAddress;

    /**
     * 总条数
     */
    @ApiModelProperty(value = "总条数")
    @TableField("total_num")
    @Excel(name = "总条数")
    private Long totalNum;

    /**
     * 核算人ID
     */
    @ApiModelProperty(value = "核算人ID")
    @TableField("accounting_user_id")
    @Excel(name = "核算人ID")
    private Long accountingUserId;

    /**
     * 核算人名称
     */
    @ApiModelProperty(value = "核算人名称")
    @Length(max = 100, message = "核算人名称长度不能超过100")
    @TableField(value = "accounting_user_name", condition = LIKE)
    @Excel(name = "核算人名称")
    private String accountingUserName;

    /**
     * 核算时间
     */
    @ApiModelProperty(value = "核算时间")
    @TableField("accounting_time")
    @Excel(name = "核算时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime accountingTime;

    /**
     * 状态0核算中1核算完成2核算失败
     */
    @ApiModelProperty(value = "状态0核算中1核算完成2核算失败")
    @TableField("data_status")
    @Excel(name = "状态0核算中1核算完成2核算失败")
    private Integer dataStatus;


    @Builder
    public AdjustCalculationRecord(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime, 
                    LocalDateTime startTime, LocalDateTime endTime, Long useGasTypeId, String useGasTypeName, String customerCode, 
                    String customerName, String bodyNumber, String gasMeterAddress, Long totalNum, Long accountingUserId, String accountingUserName, 
                    LocalDateTime accountingTime, Integer dataStatus) {
        this.id = id;
        this.createUser = createUser;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.startTime = startTime;
        this.endTime = endTime;
        this.useGasTypeId = useGasTypeId;
        this.useGasTypeName = useGasTypeName;
        this.customerCode = customerCode;
        this.customerName = customerName;
        this.bodyNumber = bodyNumber;
        this.gasMeterAddress = gasMeterAddress;
        this.totalNum = totalNum;
        this.accountingUserId = accountingUserId;
        this.accountingUserName = accountingUserName;
        this.accountingTime = accountingTime;
        this.dataStatus = dataStatus;
    }

}
