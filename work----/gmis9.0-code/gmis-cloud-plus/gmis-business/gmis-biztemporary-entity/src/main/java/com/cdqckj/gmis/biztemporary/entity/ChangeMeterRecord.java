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
 * 气表换表记录
 * </p>
 *
 * @author gmis
 * @since 2020-11-30
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("gt_change_meter_record")
@ApiModel(value = "ChangeMeterRecord", description = "气表换表记录")
@AllArgsConstructor
public class ChangeMeterRecord extends Entity<Long> {

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
     * 缴费编号
     */
    @ApiModelProperty(value = "缴费编号")
    @Length(max = 50, message = "缴费编号长度不能超过50")
    @TableField(value = "charge_no", condition = LIKE)
    @Excel(name = "缴费编号")
    private String chargeNo;

    /**
     * 换表记录编号
     */
    @ApiModelProperty(value = "换表记录编号")
    @Length(max = 50, message = "换表记录编号长度不能超过50")
    @TableField(value = "change_meter_no", condition = LIKE)
    @Excel(name = "换表记录编号")
    private String changeMeterNo;

    /**
     * 旧气表表号
     */
    @ApiModelProperty(value = "旧气表表号")
    @Length(max = 32, message = "旧气表表号长度不能超过32")
    @TableField(value = "old_meter_code", condition = LIKE)
    @Excel(name = "旧气表表号")
    private String oldMeterCode;

    /**
     * 旧表的表身号
     */
    @ApiModelProperty(value = "旧表的表身号")
    @Length(max = 30, message = "旧表的表身号长度不能超过30")
    @TableField(value = "old_meter_number", condition = LIKE)
    @Excel(name = "旧表的表身号")
    private String oldMeterNumber;

    /**
     * 旧表类型
     */
    @ApiModelProperty(value = "旧表类型")
    @Length(max = 30, message = "旧表类型长度不能超过30")
    @TableField(value = "old_meter_type", condition = LIKE)
    @Excel(name = "旧表类型")
    private String oldMeterType;

    /**
     * 新气表表号
     */
    @ApiModelProperty(value = "新气表表号")
    @Length(max = 32, message = "新气表表号长度不能超过32")
    @TableField(value = "new_meter_code", condition = LIKE)
    @Excel(name = "新气表表号")
    private String newMeterCode;

    /**
     * 新表的表身号
     */
    @ApiModelProperty(value = "新表的表身号")
    @Length(max = 30, message = "新表的表身号长度不能超过30")
    @TableField(value = "new_meter_number", condition = LIKE)
    @Excel(name = "新表的表身号")
    private String newMeterNumber;

    /**
     * 新表类型
     */
    @ApiModelProperty(value = "新表类型")
    @Length(max = 30, message = "新表类型长度不能超过30")
    @TableField(value = "new_meter_type", condition = LIKE)
    @Excel(name = "新表类型")
    private String newMeterType;

    /**
     * 新表厂家ID
     */
    @ApiModelProperty(value = "新表厂家ID")
    @TableField("new_meter_factory_id")
    @Excel(name = "新表厂家ID")
    private Long newMeterFactoryId;

    /**
     * 新表厂家名称
     */
    @ApiModelProperty(value = "新表厂家名称")
    @Length(max = 100, message = "新表厂家名称长度不能超过100")
    @TableField(value = "new_meter_factory_name", condition = LIKE)
    @Excel(name = "新表厂家名称")
    private String newMeterFactoryName;

    /**
     * 新表气表型号id
     */
    @ApiModelProperty(value = "新表气表型号id")
    @TableField("new_meter_model_id")
    @Excel(name = "新表气表型号id")
    private Long newMeterModelId;

    /**
     * 新表气表型号名称
     */
    @ApiModelProperty(value = "新表气表型号名称")
    @Length(max = 30, message = "新表气表型号名称长度不能超过30")
    @TableField(value = "new_meter_model_name", condition = LIKE)
    @Excel(name = "新表气表型号名称")
    private String newMeterModelName;

    /**
     * 新表版号ID
     */
    @ApiModelProperty(value = "新表版号ID")
    @TableField("new_meter_version_id")
    @Excel(name = "新表版号ID")
    private Long newMeterVersionId;

    /**
     * 新表版号名称
     */
    @ApiModelProperty(value = "新表版号名称")
    @Length(max = 30, message = "新表版号名称长度不能超过30")
    @TableField(value = "new_meter_version_name", condition = LIKE)
    @Excel(name = "新表版号名称")
    private String newMeterVersionName;

    /**
     * 换表方式：CHANGE_NEW、换新表；CHANGE_ZERO、气表清零
     */
    @ApiModelProperty(value = "换表方式：CHANGE_NEW、换新表；CHANGE_ZERO、气表清零")
    @Length(max = 30, message = "换表方式：CHANGE_NEW、换新表；CHANGE_ZERO、气表清零长度不能超过30")
    @TableField(value = "change_mode", condition = LIKE)
    @Excel(name = "换表方式：CHANGE_NEW、换新表；CHANGE_ZERO、气表清零")
    private String changeMode;

    /**
     * 换表类型：GAS_TO_GAS、气量表换气表量；GAS_TO_MONEY、气量表换金额表；MONEY_TO_GAS、金额表换金额表；MONEY_TO_MONEY、金额表换气量表
     */
    @ApiModelProperty(value = "换表类型：GAS_TO_GAS、气量表换气表量；GAS_TO_MONEY、气量表换金额表；MONEY_TO_GAS、金额表换金额表；MONEY_TO_MONEY、金额表换气量表 ")
    @Length(max = 30, message = "换表类型：GAS_TO_GAS、气量表换气表量；GAS_TO_MONEY、气量表换金额表；MONEY_TO_GAS、金额表换金额表；MONEY_TO_MONEY、金额表换气量表 长度不能超过30")
    @TableField(value = "change_type", condition = LIKE)
    @Excel(name = "换表类型：GAS_TO_GAS、气量表换气表量；GAS_TO_MONEY、气量表换金额表；MONEY_TO_GAS、金额表换金额表；MONEY_TO_MONEY、金额表换气量表 ")
    private String changeType;

    /**
     * 换表场景：ChangeSceneEnum
     */
    @ApiModelProperty(value = "换表场景：ChangeSceneEnum")
    @Length(max = 100, message = "换表场景：ChangeSceneEnum长度不能超过100")
    @TableField(value = "change_scene", condition = LIKE)
    @Excel(name = "换表场景：ChangeSceneEnum")
    private String changeScene;

    /**
     * 换表原因
     */
    @ApiModelProperty(value = "换表原因")
    @Length(max = 300, message = "换表原因长度不能超过300")
    @TableField(value = "change_reason", condition = LIKE)
    @Excel(name = "换表原因")
    private String changeReason;

    /**
     * 换表收费金额
     */
    @ApiModelProperty(value = "换表收费金额")
    @TableField("charge_amount")
    @Excel(name = "换表收费金额")
    private BigDecimal chargeAmount;

    /**
     * 旧表止数
     */
    @ApiModelProperty(value = "旧表止数")
    @TableField("old_meter_end_gas")
    @Excel(name = "旧表止数")
    private BigDecimal oldMeterEndGas;

    /**
     * 新表底数
     */
    @ApiModelProperty(value = "新表底数")
    @TableField("new_meter_start_gas")
    @Excel(name = "新表底数")
    private BigDecimal newMeterStartGas;

    /**
     * 补充气量
     */
    @ApiModelProperty(value = "补充气量")
    @TableField("supplement_gas")
    @Excel(name = "补充气量")
    private BigDecimal supplementGas;

    /**
     * 补充金额
     */
    @ApiModelProperty(value = "补充金额")
    @TableField("supplement_amount")
    @Excel(name = "补充金额")
    private BigDecimal supplementAmount;

    /**
     * 周期累计使用量
     */
    @ApiModelProperty(value = "周期累计使用量")
    @TableField("cycle_use_gas")
    @Excel(name = "周期累计使用量")
    private BigDecimal cycleUseGas;

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
     * 换表日期
     */
    @ApiModelProperty(value = "换表日期")
    @TableField("change_date")
    @Excel(name = "换表日期", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDate changeDate;

    /**
     * 换卡表时的读写卡操作结果
     */
    @ApiModelProperty(value = "换卡表时的读写卡操作结果")
    @Length(max = 30, message = "换卡表时的读写卡操作结果长度不能超过30")
    @TableField(value = "card_operate", condition = LIKE)
    @Excel(name = "换卡表时的读写卡操作结果")
    private String cardOperate;

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
    public ChangeMeterRecord(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime,
                             String companyCode, String companyName, Long orgId, String orgName, Long businessHallId,
                             String businessHallName, String customerCode, String customerName, String chargeNo, String changeMeterNo, String oldMeterCode,
                             String oldMeterNumber, String oldMeterType, String newMeterCode, String newMeterNumber, String newMeterType, Long newMeterFactoryId,
                             String newMeterFactoryName, Long newMeterModelId, String newMeterModelName, Long newMeterVersionId, String newMeterVersionName, String changeMode,
                             String changeType, String changeScene, String changeReason, BigDecimal chargeAmount, BigDecimal oldMeterEndGas, BigDecimal newMeterStartGas,
                             BigDecimal supplementGas, BigDecimal supplementAmount, BigDecimal cycleUseGas, Long readMeterId, Long arrearsDetailId, String arrearsDetailIdList,
                             LocalDate changeDate, String cardOperate, Integer status, Integer deleteStatus) {
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
        this.chargeNo = chargeNo;
        this.changeMeterNo = changeMeterNo;
        this.oldMeterCode = oldMeterCode;
        this.oldMeterNumber = oldMeterNumber;
        this.oldMeterType = oldMeterType;
        this.newMeterCode = newMeterCode;
        this.newMeterNumber = newMeterNumber;
        this.newMeterType = newMeterType;
        this.newMeterFactoryId = newMeterFactoryId;
        this.newMeterFactoryName = newMeterFactoryName;
        this.newMeterModelId = newMeterModelId;
        this.newMeterModelName = newMeterModelName;
        this.newMeterVersionId = newMeterVersionId;
        this.newMeterVersionName = newMeterVersionName;
        this.changeMode = changeMode;
        this.changeType = changeType;
        this.changeScene = changeScene;
        this.changeReason = changeReason;
        this.chargeAmount = chargeAmount;
        this.oldMeterEndGas = oldMeterEndGas;
        this.newMeterStartGas = newMeterStartGas;
        this.supplementGas = supplementGas;
        this.supplementAmount = supplementAmount;
        this.cycleUseGas = cycleUseGas;
        this.readMeterId = readMeterId;
        this.arrearsDetailId = arrearsDetailId;
        this.arrearsDetailIdList = arrearsDetailIdList;
        this.changeDate = changeDate;
        this.cardOperate = cardOperate;
        this.status = status;
        this.deleteStatus = deleteStatus;
    }

}
