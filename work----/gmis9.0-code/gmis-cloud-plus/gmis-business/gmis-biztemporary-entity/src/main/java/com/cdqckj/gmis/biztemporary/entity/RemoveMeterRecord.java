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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;
import static com.cdqckj.gmis.utils.DateUtils.DEFAULT_DATE_TIME_FORMAT;

/**
 * <p>
 * 实体类
 * 气表拆表记录
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
@TableName("gt_remove_meter_record")
@ApiModel(value = "RemoveMeterRecord", description = "气表拆表记录")
@AllArgsConstructor
public class RemoveMeterRecord extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 公司编号
     */
    @ApiModelProperty(value = "公司编号")
    @Length(max = 32, message = "公司编号长度不能超过32")
    @TableField(value = "company_code", condition = LIKE)
    @Excel(name = "公司编号")
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
     * 组织名称
     */
    @ApiModelProperty(value = "组织名称")
    @Length(max = 100, message = "组织名称长度不能超过100")
    @TableField(value = "org_name", condition = LIKE)
    @Excel(name = "组织名称")
    private String orgName;

    /**
     * 营业厅ID
     */
    @ApiModelProperty(value = "营业厅ID")
    @TableField("business_hall_id")
    @Excel(name = "营业厅ID")
    private Long businessHallId;

    /**
     * 营业厅名称
     */
    @ApiModelProperty(value = "营业厅名称")
    @Length(max = 100, message = "营业厅名称长度不能超过100")
    @TableField(value = "business_hall_name", condition = LIKE)
    @Excel(name = "营业厅名称")
    private String businessHallName;

    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    @Length(max = 30, message = "客户编号长度不能超过30")
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
     * 拆表记录编号
     */
    @ApiModelProperty(value = "拆表记录编号")
    @Length(max = 50, message = "拆表记录编号长度不能超过50")
    @TableField(value = "remove_meter_no", condition = LIKE)
    @Excel(name = "拆表记录编号")
    private String removeMeterNo;

    /**
     * 缴费编号
     */
    @ApiModelProperty(value = "缴费编号")
    @Length(max = 50, message = "缴费编号长度不能超过50")
    @TableField(value = "charge_no", condition = LIKE)
    @Excel(name = "缴费编号")
    private String chargeNo;

    /**
     * 气表编号
     */
    @ApiModelProperty(value = "气表编号")
    @Length(max = 32, message = "气表编号长度不能超过32")
    @TableField(value = "meter_code", condition = LIKE)
    @Excel(name = "气表编号")
    private String meterCode;

    /**
     * 厂家ID
     */
    @ApiModelProperty(value = "厂家ID")
    @TableField("meter_factory_id")
    @Excel(name = "厂家ID")
    private Long meterFactoryId;

    /**
     * 厂家名称
     */
    @ApiModelProperty(value = "厂家名称")
    @Length(max = 100, message = "厂家名称长度不能超过100")
    @TableField(value = "meter_factory_name", condition = LIKE)
    @Excel(name = "厂家名称")
    private String meterFactoryName;

    /**
     * 气表型号id
     */
    @ApiModelProperty(value = "气表型号id")
    @TableField("meter_model_id")
    @Excel(name = "气表型号id")
    private Long meterModelId;

    /**
     * 气表型号名称
     */
    @ApiModelProperty(value = "气表型号名称")
    @Length(max = 30, message = "气表型号名称长度不能超过30")
    @TableField(value = "meter_model_name", condition = LIKE)
    @Excel(name = "气表型号名称")
    private String meterModelName;

    /**
     * 版号ID
     */
    @ApiModelProperty(value = "版号ID")
    @TableField("meter_version_id")
    @Excel(name = "版号ID")
    private Long meterVersionId;

    /**
     * 版号名称
     */
    @ApiModelProperty(value = "版号名称")
    @Length(max = 30, message = "版号名称长度不能超过30")
    @TableField(value = "meter_version_name", condition = LIKE)
    @Excel(name = "版号名称")
    private String meterVersionName;

    /**
     * 旧表止数
     */
    @ApiModelProperty(value = "旧表止数")
    @TableField("meter_end_gas")
    @Excel(name = "旧表止数")
    private BigDecimal meterEndGas;

    /**
     * 表具余额
     */
    @ApiModelProperty(value = "表具余额")
    @TableField("meter_balance")
    @Excel(name = "表具余额")
    private BigDecimal meterBalance;

    /**
     * 生成的抄表数据Id
     */
    @ApiModelProperty(value = "生成的抄表数据Id")
    @TableField("read_meter_id")
    @Excel(name = "生成的抄表数据Id")
    private Long readMeterId;

    /**
     * 结算产生的欠费记录id
     */
    @ApiModelProperty(value = "结算产生的欠费记录id")
    @TableField("arrears_detail_id")
    @Excel(name = "结算产生的欠费记录id")
    private Long arrearsDetailId;

    /**
     * 结算产生的多条欠费记录id
     */
    @ApiModelProperty(value = "结算产生的多条欠费记录id")
    @Length(max = 300, message = "结算产生的多条欠费记录id长度不能超过300")
    @TableField(value = "arrears_detail_id_list", condition = LIKE)
    @Excel(name = "结算产生的多条欠费记录id")
    private String arrearsDetailIdList;

    /**
     * 拆表原因
     */
    @ApiModelProperty(value = "拆表原因")
    @Length(max = 300, message = "拆表原因长度不能超过300")
    @TableField(value = "remove_reason", condition = LIKE)
    @Excel(name = "拆表原因")
    private String removeReason;

    /**
     * 拆表日期
     */
    @ApiModelProperty(value = "拆表日期")
    @TableField("remove_date")
    @Excel(name = "拆表日期", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDate removeDate;

    /**
     * 业务状态：0取消；1完成；2待缴费；3处理中
     */
    @ApiModelProperty(value = "业务状态：0取消；1完成；2待缴费；3处理中")
    @TableField("status")
    @Excel(name = "业务状态：0取消；1完成；2待缴费；3处理中")
    private Integer status;

    /**
     * 删除状态
     */
    @ApiModelProperty(value = "删除状态")
    @TableField("delete_status")
    @Excel(name = "删除状态")
    private Integer deleteStatus;


    @Builder
    public RemoveMeterRecord(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime,
                             String companyCode, String companyName, Long orgId, String orgName, Long businessHallId,
                             String businessHallName, String customerCode, String customerName, String removeMeterNo, String chargeNo, String meterCode,
                             Long meterFactoryId, String meterFactoryName, Long meterModelId, String meterModelName, Long meterVersionId, String meterVersionName,
                             BigDecimal meterEndGas, BigDecimal meterBalance, Long readMeterId, Long arrearsDetailId, String arrearsDetailIdList, String removeReason,
                             LocalDate removeDate, Integer status, Integer deleteStatus) {
        this.id = id;
        this.createUser = createUser;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.orgId = orgId;
        this.orgName = orgName;
        this.businessHallId = businessHallId;
        this.businessHallName = businessHallName;
        this.customerCode = customerCode;
        this.customerName = customerName;
        this.removeMeterNo = removeMeterNo;
        this.chargeNo = chargeNo;
        this.meterCode = meterCode;
        this.meterFactoryId = meterFactoryId;
        this.meterFactoryName = meterFactoryName;
        this.meterModelId = meterModelId;
        this.meterModelName = meterModelName;
        this.meterVersionId = meterVersionId;
        this.meterVersionName = meterVersionName;
        this.meterEndGas = meterEndGas;
        this.meterBalance = meterBalance;
        this.readMeterId = readMeterId;
        this.arrearsDetailId = arrearsDetailId;
        this.arrearsDetailIdList = arrearsDetailIdList;
        this.removeReason = removeReason;
        this.removeDate = removeDate;
        this.status = status;
        this.deleteStatus = deleteStatus;
    }

}
