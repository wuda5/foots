package com.cdqckj.gmis.biztemporary.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

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
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "ChangeMeterRecordSaveDTO", description = "气表换表记录")
public class ChangeMeterRecordSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 公司编号
     */
    @ApiModelProperty(value = "公司编号", hidden = true)
    @Length(max = 32, message = "公司编号长度不能超过32")
    private String companyCode;
    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称", hidden = true)
    @Length(max = 100, message = "公司名称长度不能超过100")
    private String companyName;
    /**
     * 组织ID
     */
    @ApiModelProperty(value = "组织ID", hidden = true)
    private Long orgId;
    /**
     * 组织名称
     */
    @ApiModelProperty(value = "组织名称", hidden = true)
    @Length(max = 100, message = "组织名称长度不能超过100")
    private String orgName;
    /**
     * 营业厅ID
     */
    @ApiModelProperty(value = "营业厅ID", hidden = true)
    private Long businessHallId;
    /**
     * 营业厅名称
     */
    @ApiModelProperty(value = "营业厅名称", hidden = true)
    @Length(max = 100, message = "营业厅名称长度不能超过100")
    private String businessHallName;
    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    @Length(max = 30, message = "客户编号长度不能超过30")
    @NotNull
    private String customerCode;
    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    @Length(max = 100, message = "客户名称长度不能超过100")
    @NotNull
    private String customerName;
    /**
     * 缴费编号
     */
    @ApiModelProperty(value = "缴费编号")
    @Length(max = 50, message = "缴费编号长度不能超过50")
    private String chargeNo;
    /**
     * 换表记录编号
     */
    @ApiModelProperty(value = "换表记录编号", hidden = true)
    @Length(max = 50, message = "换表记录编号长度不能超过50")
    private String changeMeterNo;
    /**
     * 旧气表表号
     */
    @ApiModelProperty(value = "旧气表表号")
    @Length(max = 32, message = "旧气表表号长度不能超过32")
    @NotNull
    private String oldMeterCode;
    /**
     * 旧表的表身号
     */
    @ApiModelProperty(value = "旧表的表身号")
    @Length(max = 30, message = "旧表的表身号长度不能超过30")
    private String oldMeterNumber;
    /**
     * 旧表类型
     */
    @ApiModelProperty(value = "旧表类型")
    @Length(max = 30, message = "旧表类型长度不能超过30")
    private String oldMeterType;
    /**
     * 新气表表号
     */
    @ApiModelProperty(value = "新气表表号")
    @Length(max = 32, message = "新气表表号长度不能超过32")
    private String newMeterCode;
    /**
     * 新表的表身号
     */
    @ApiModelProperty(value = "新表的表身号")
    @Length(max = 30, message = "新表的表身号长度不能超过30")
    private String newMeterNumber;
    /**
     * 新表类型
     */
    @ApiModelProperty(value = "新表类型", hidden = true)
    @Length(max = 30, message = "新表类型长度不能超过30")
    private String newMeterType;
    /**
     * 新表厂家ID
     */
    @ApiModelProperty(value = "新表厂家ID")
    private Long newMeterFactoryId;
    /**
     * 新表厂家名称
     */
    @ApiModelProperty(value = "新表厂家名称")
    @Length(max = 100, message = "新表厂家名称长度不能超过100")
    private String newMeterFactoryName;
    /**
     * 新表气表型号id
     */
    @ApiModelProperty(value = "新表气表型号id")
    private Long newMeterModelId;
    /**
     * 新表气表型号名称
     */
    @ApiModelProperty(value = "新表气表型号名称")
    @Length(max = 30, message = "新表气表型号名称长度不能超过30")
    private String newMeterModelName;
    /**
     * 新表版号ID
     */
    @ApiModelProperty(value = "新表版号ID")
    private Long newMeterVersionId;
    /**
     * 新表版号名称
     */
    @ApiModelProperty(value = "新表版号名称")
    @Length(max = 30, message = "新表版号名称长度不能超过30")
    private String newMeterVersionName;
    /**
     * 换表方式：CHANGE_NEW、换新表；CHANGE_ZERO、气表清零
     */
    @ApiModelProperty(value = "换表方式：CHANGE_NEW、换新表；CHANGE_ZERO、气表清零")
    @Length(max = 30, message = "换表方式：CHANGE_NEW、换新表；CHANGE_ZERO、气表清零长度不能超过30")
    private String changeMode;
    /**
     * 换表类型：GAS_TO_GAS、气量表换气表量；GAS_TO_MONEY、气量表换金额表；MONEY_TO_GAS、金额表换金额表；MONEY_TO_MONEY、金额表换气量表
     */
    @ApiModelProperty(value = "换表类型：GAS_TO_GAS、气量表换气表量；GAS_TO_MONEY、气量表换金额表；MONEY_TO_GAS、金额表换金额表；MONEY_TO_MONEY、金额表换气量表 ", hidden = true)
    @Length(max = 30, message = "换表类型：GAS_TO_GAS、气量表换气表量；GAS_TO_MONEY、气量表换金额表；MONEY_TO_GAS、金额表换金额表；MONEY_TO_MONEY、金额表换气量表 长度不能超过30")
    private String changeType;
    /**
     * 换表场景：ChangeSceneEnum
     */
    @ApiModelProperty(value = "换表场景：ChangeSceneEnum", hidden = true)
    @Length(max = 100, message = "换表场景：ChangeSceneEnum长度不能超过100")
    private String changeScene;
    /**
     * 换表原因
     */
    @ApiModelProperty(value = "换表原因")
    @Length(max = 300, message = "换表原因长度不能超过300")
    @NotNull
    private String changeReason;
    /**
     * 换表收费金额
     */
    @ApiModelProperty(value = "换表收费金额", hidden = true)
    private BigDecimal chargeAmount;
    /**
     * 旧表止数
     */
    @ApiModelProperty(value = "旧表止数")
    private BigDecimal oldMeterEndGas;
    /**
     * 新表底数
     */
    @ApiModelProperty(value = "新表底数")
    private BigDecimal newMeterStartGas;
    /**
     * 补充气量
     */
    @ApiModelProperty(value = "补充气量")
    private BigDecimal supplementGas;
    /**
     * 补充金额
     */
    @ApiModelProperty(value = "补充金额")
    private BigDecimal supplementAmount;
    /**
     * 周期累计使用量
     */
    @ApiModelProperty(value = "周期累计使用量")
    private BigDecimal cycleUseGas;
    /**
     * 生成的抄表数据Id
     */
    @ApiModelProperty(value = "生成的抄表数据Id")
    private Long readMeterId;
    /**
     * 结算产生的欠费记录id
     */
    @ApiModelProperty(value = "结算产生的欠费记录id")
    private Long arrearsDetailId;
    /**
     * 结算产生的多条欠费记录id
     */
    @ApiModelProperty(value = "结算产生的多条欠费记录id")
    @Length(max = 300, message = "结算产生的多条欠费记录id长度不能超过300")
    private String arrearsDetailIdList;
    /**
     * 换表日期
     */
    @ApiModelProperty(value = "换表日期", hidden = true)
    private LocalDate changeDate;
    /**
     * 换卡表时的读写卡操作结果
     */
    @ApiModelProperty(value = "换卡表时的读写卡操作结果")
    @Length(max = 30, message = "换卡表时的读写卡操作结果长度不能超过30")
    private String cardOperate;
    /**
     * 业务状态：0取消；1完成；2待缴费；3处理中
     */
    @ApiModelProperty(value = "业务状态：0取消；1完成；2待缴费；3处理中", hidden = true)
    private Integer status;
    /**
     * 删除状态
     */
    @ApiModelProperty(value = "删除状态", hidden = true)
    private Integer deleteStatus;
    /**
     * 旧表金额标识字段  是气量表，还是金额表
     */
    private String oldAmountMark;
    /**
     * 旧表金额标识字段  是气量表，还是金额表
     */
    private String newAmountMark;

}
