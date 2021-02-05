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

import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.SqlCondition.EQUAL;
import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

/**
 * <p>
 * 实体类
 * 柜台综合：过户业务表，业务状态未完成之前不会更改主表数据
 * </p>
 *
 * @author gmis
 * @since 2020-11-06
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("gt_gasmeter_transfer_account")
@ApiModel(value = "GasmeterTransferAccount", description = "柜台综合：过户业务表，业务状态未完成之前不会更改主表数据")
@AllArgsConstructor
public class GasmeterTransferAccount extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 表具编号
     */
    @ApiModelProperty(value = "表具编号")
    @Length(max = 32, message = "表具编号长度不能超过32")
    @TableField(value = "gas_meter_code", condition = LIKE)
    @Excel(name = "表具编号")
    private String gasMeterCode;

    /**
     * 原客户编号
     */
    @ApiModelProperty(value = "原客户编号")
    @Length(max = 32, message = "原客户编号长度不能超过32")
    @TableField(value = "old_customer_code", condition = LIKE)
    @Excel(name = "原客户编号")
    private String oldCustomerCode;

    /**
     * 新客户code
     */
    @ApiModelProperty(value = "新客户code:没有就表示新增")
    @Length(max = 32, message = "新客户code")
    @TableField(value = "customer_code", condition = LIKE)
    @Excel(name = "新客户code")
    private String customerCode;

    /**
     * 缴费编号
     */
    @ApiModelProperty(value = "缴费编号")
    @TableField(value = "customer_charge_no", condition = LIKE)
    @Excel(name = "缴费编号")
    private String customerChargeNo;


    @ApiModelProperty(value = "使用人数")
    @TableField(value = "population", condition = EQUAL)
    @Excel(name = "使用人数")
    private Integer population;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @Length(max = 100, message = "备注长度不能超过100")
    @TableField(value = "remark", condition = LIKE)
    @Excel(name = "备注")
    private String remark;

    /**
     * 过户业务状态：0’取消；1‘完成；2’待缴费；3‘处理中
     */
    @ApiModelProperty(value = "过户业务状态：0’取消；1‘完成；2’待缴费；3‘处理中")
    @TableField("status")
    @Excel(name = "过户业务状态：0’取消；1‘完成；2’待缴费；3‘处理中")
    private Integer status;

    /**
     * 组织id
     */
    @ApiModelProperty(value = "组织id")
    @TableField("org_id")
    @Excel(name = "组织id")
    private Long orgId;


    @Builder
    public GasmeterTransferAccount(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime,
                    String gasMeterCode, String oldCustomerCode,  String customerCode,String remark, Integer status,Long orgId) {
        this.id = id;
        this.createUser = createUser;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.gasMeterCode = gasMeterCode;
        this.oldCustomerCode = oldCustomerCode;
        this.customerCode = customerCode;
        this.remark = remark;
        this.status = status;
        this.orgId = orgId;
    }

}
