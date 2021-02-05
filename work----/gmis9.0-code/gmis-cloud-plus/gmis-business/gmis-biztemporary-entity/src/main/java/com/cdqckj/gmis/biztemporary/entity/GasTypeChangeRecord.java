package com.cdqckj.gmis.biztemporary.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cdqckj.gmis.base.entity.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;
import static com.cdqckj.gmis.utils.DateUtils.DEFAULT_DATE_TIME_FORMAT;

/**
 * <p>
 * 实体类
 * 客户用气类型变更记录
 * </p>
 *
 * @author gmis
 * @since 2020-11-16
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("gt_gas_type_change_record")
@ApiModel(value = "GasTypeChangeRecord", description = "客户用气类型变更记录")
@AllArgsConstructor
public class GasTypeChangeRecord extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 公司CODE
     */
    @ApiModelProperty(value = "公司CODE")
    @Length(max = 32, message = "公司CODE长度不能超过32")
    @TableField(value = "company_code", condition = LIKE)
    @Excel(name = "公司CODE")
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
     * 组织id
     */
    @ApiModelProperty(value = "组织id")
    @TableField("org_id")
    @Excel(name = "组织id")
    private Long orgId;

    /**
     * 组织名称
     */
    @ApiModelProperty(value = "组织名称")
    @Length(max = 100, message = "组织名称长度不能超过100")
    @TableField(value = "org_name", condition = LIKE)
    @Excel(name = "组织名称")
    private String orgName;

    /**
     * 营业厅id
     */
    @ApiModelProperty(value = "营业厅id")
    @Length(max = 32, message = "营业厅id长度不能超过32")
    @TableField(value = "business_ball_id", condition = LIKE)
    @Excel(name = "营业厅id")
    private String businessBallId;

    /**
     * 营业厅名称
     */
    @ApiModelProperty(value = "营业厅名称")
    @Length(max = 100, message = "营业厅名称长度不能超过100")
    @TableField(value = "business_ball_name", condition = LIKE)
    @Excel(name = "营业厅名称")
    private String businessBallName;

    /**
     * 客户编码
     */
    @ApiModelProperty(value = "客户编码")
    @Length(max = 50, message = "客户编码长度不能超过50")
    @TableField(value = "customer_code", condition = LIKE)
    @Excel(name = "客户编码")
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
     * 气表编码
     */
    @ApiModelProperty(value = "气表编码")
    @Length(max = 50, message = "气表编码长度不能超过50")
    @TableField(value = "gas_meter_code", condition = LIKE)
    @Excel(name = "气表编码")
    private String gasMeterCode;

    /**
     * 气表名称
     */
    @ApiModelProperty(value = "气表名称")
    @Length(max = 100, message = "气表名称长度不能超过100")
    @TableField(value = "gas_meter_name", condition = LIKE)
    @Excel(name = "气表名称")
    private String gasMeterName;

    /**
     * 变更时间
     */
    @ApiModelProperty(value = "变更时间")
    @TableField("change_time")
    @Excel(name = "变更时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime changeTime;

    /**
     * 变更前用气类型名称
     */
    @ApiModelProperty(value = "变更前用气类型名称")
    @Length(max = 60, message = "变更前用气类型名称长度不能超过60")
    @TableField(value = "old_gas_type_name", condition = LIKE)
    @Excel(name = "变更前用气类型名称")
    private String oldGasTypeName;

    /**
     * 变更前用气类型id
     */
    @ApiModelProperty(value = "变更前用气类型id")
    @TableField("old_gas_type_id")
    @Excel(name = "变更前用气类型id")
    private Long oldGasTypeId;

    /**
     * 变更后用气类型名称
     */
    @ApiModelProperty(value = "变更后用气类型名称")
    @Length(max = 60, message = "变更后用气类型名称长度不能超过60")
    @TableField(value = "new_gas_type_name", condition = LIKE)
    @Excel(name = "变更后用气类型名称")
    private String newGasTypeName;

    /**
     * 变更后用气类型id
     */
    @ApiModelProperty(value = "变更后用气类型id")
    @TableField("new_gas_type_id")
    @Excel(name = "变更后用气类型id")
    private Long newGasTypeId;

    /**
     * 变更前价格方案id
     */
    @ApiModelProperty(value = "变更后价格方案开始时间")
    private LocalDate startTimeNew;
    /**
     * 变更后价格方案id
     */
    @ApiModelProperty(value = "变更后价格方案结束时间")
    private LocalDate endTimeNew;


    @ApiModelProperty(value = "变更前价格方案开始时间")
    private LocalDate startTimeOld;
    /**
     * 变更后价格方案id
     */
    @ApiModelProperty(value = "变更前价格方案结束时间")
    private LocalDate endTimeOld;

    /**
     * 变更前嘉庚方案号
     */
    @ApiModelProperty(value = "变更前嘉庚方案号")
    @TableField("old_price_num")
    @Excel(name = "变更前嘉庚方案号")
    private Integer oldPriceNum;

    /**
     * 变更后价格方案号
     */
    @ApiModelProperty(value = "变更后价格方案号")
    @TableField("new_price_num")
    @Excel(name = "变更后价格方案号")
    private Integer newPriceNum;

    /**
     * 周期量控制（是否清零1-清零，0-不清零）
     */
    @ApiModelProperty(value = "周期量控制（是否清零1-清零，0-不清零）")
    @TableField("cycle_sum_control")
    @Excel(name = "周期量控制（是否清零1-清零，0-不清零）")
    private Integer cycleSumControl;

    /**
     * 变更前价格方案id
     */
    @ApiModelProperty(value = "变更前价格方案id")
    @TableField("old_price_id")
    @Excel(name = "变更前价格方案id")
    private Long oldPriceId;

    /**
     * 变更后价格方案id
     */
    @ApiModelProperty(value = "变更后价格方案id")
    @TableField("new_price_id")
    @Excel(name = "变更后价格方案id")
    private Long newPriceId;


    @Builder
    public GasTypeChangeRecord(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime,
                    String companyCode, String companyName, Long orgId, String orgName, String businessBallId,
                    String businessBallName, String customerCode, String customerName, String gasMeterCode, String gasMeterName, LocalDateTime changeTime,
                    String oldGasTypeName, Long oldGasTypeId, String newGasTypeName, Long newGasTypeId, Long oldPriceId, Long newPriceId,
                    Integer oldPriceNum, Integer newPriceNum, Integer cycleSumControl) {
        this.id = id;
        this.createUser = createUser;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.orgId = orgId;
        this.orgName = orgName;
        this.businessBallId = businessBallId;
        this.businessBallName = businessBallName;
        this.customerCode = customerCode;
        this.customerName = customerName;
        this.gasMeterCode = gasMeterCode;
        this.gasMeterName = gasMeterName;
        this.changeTime = changeTime;
        this.oldGasTypeName = oldGasTypeName;
        this.oldGasTypeId = oldGasTypeId;
        this.newGasTypeName = newGasTypeName;
        this.newGasTypeId = newGasTypeId;
        this.oldPriceNum = oldPriceNum;
        this.newPriceNum = newPriceNum;
        this.cycleSumControl = cycleSumControl;
        this.newPriceId = newPriceId;
        this.oldPriceId = oldPriceId;
    }

}
