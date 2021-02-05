package com.cdqckj.gmis.iot.qc.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cdqckj.gmis.base.entity.Entity;
import com.cdqckj.gmis.iot.qc.enumeration.CommandType;
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
 * 物联网气表指令明细数据
 * </p>
 *
 * @author gmis
 * @since 2020-10-15
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("qw_iot_gas_meter_command_detail")
@ApiModel(value = "IotGasMeterCommandDetail", description = "物联网气表指令明细数据")
@AllArgsConstructor
public class IotGasMeterCommandDetail extends Entity<Long> {

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
     * 指令id
     */
    @ApiModelProperty(value = "指令id")
    @TableField("command_id")
    @Excel(name = "指令id")
    private Long commandId;

    /**
     * 表身号
     */
    @ApiModelProperty(value = "表身号")
    @Length(max = 30, message = "表身号长度不能超过30")
    @TableField(value = "meter_number", condition = LIKE)
    @Excel(name = "表身号")
    private String meterNumber;

    /**
     * 气表编号
     */
    @ApiModelProperty(value = "气表编号")
    @Length(max = 50, message = "气表编号长度不能超过50")
    @TableField(value = "gas_meter_code", condition = LIKE)
    @Excel(name = "气表编号")
    private String gasMeterCode;

    /**
     * 指令类型
     * #CommandType{CHANGE_METER:换表;RECHARGE:充值;OPEN_VALVE:开阀;CLOSE_VALVE:关阀;ADJ_PRICE:调价;}
     */
    @ApiModelProperty(value = "指令类型")
    @TableField("command_type")
    @Excel(name = "指令类型", replace = {"换表_CHANGE_METER", "充值_RECHARGE", "开阀_OPEN_VALVE", "关阀_CLOSE_VALVE", "调价_ADJ_PRICE",  "_null"})
    private CommandType commandType;

    /**
     * QNMS3.0流水号
     */
    @ApiModelProperty(value = "QNMS3.0流水号")
    @Length(max = 50, message = "QNMS3.0流水号长度不能超过50")
    @TableField(value = "transaction_no", condition = LIKE)
    @Excel(name = "QNMS3.0流水号")
    private String transactionNo;

    /**
     * 到达3.0时间
     */
    @ApiModelProperty(value = "到达3.0时间")
    @TableField("to_3_time")
    @Excel(name = "到达3.0时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime to3Time;

    /**
     * 执行时间
     */
    @ApiModelProperty(value = "执行时间")
    @TableField("execute_time")
    @Excel(name = "执行时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime executeTime;

    /**
     * 0:未执行
     *             1:执行成功
     *             其他自编：失败
     */
    @ApiModelProperty(value = "0:未执行")
    @TableField("execute_result")
    @Excel(name = "0:未执行")
    private Integer executeResult;

    /**
     * 0:待发送
     *             1:已发至3.0
     *             2:已执行
     *             10：重试
     *             100：取消执行
     */
    @ApiModelProperty(value = "0:待发送")
    @TableField("command_stage")
    @Excel(name = "0:待发送")
    private Integer commandStage;

    /**
     * 0：无效
     *             1：有效
     */
    @ApiModelProperty(value = "0：无效")
    @TableField("command_status")
    @Excel(name = "0：无效")
    private Integer commandStatus;

    /**
     * 其他参数，建议存Json
     */
    @ApiModelProperty(value = "其他参数，建议存Json")
    @Length(max = 2000, message = "其他参数，建议存Json长度不能超过2000")
    @TableField(value = "command_parameter", condition = LIKE)
    @Excel(name = "其他参数，建议存Json")
    private String commandParameter;

    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    @TableField("customer_code")
    @Excel(name = "客户编号")
    private String customerCode;

    /**
     * 错误描述
     */
    @ApiModelProperty(value = "错误描述")
    @TableField("error_desc")
    private String errorDesc;

    @Builder
    public IotGasMeterCommandDetail(Long id, LocalDateTime createTime, Long createUser, Long updateUser, LocalDateTime updateTime, 
                    String companyCode, String companyName, Long orgId, String orgName, Long commandId, 
                    String meterNumber, String gasMeterCode, CommandType commandType, String transactionNo, LocalDateTime to3Time, LocalDateTime executeTime, 
                    Integer executeResult, Integer commandStage, Integer commandStatus, String commandParameter,String errorDesc) {
        this.id = id;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.orgId = orgId;
        this.orgName = orgName;
        this.commandId = commandId;
        this.meterNumber = meterNumber;
        this.gasMeterCode = gasMeterCode;
        this.commandType = commandType;
        this.transactionNo = transactionNo;
        this.to3Time = to3Time;
        this.executeTime = executeTime;
        this.executeResult = executeResult;
        this.commandStage = commandStage;
        this.commandStatus = commandStatus;
        this.commandParameter = commandParameter;
        this.errorDesc = errorDesc;
    }

}
