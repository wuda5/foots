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

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;
import static com.cdqckj.gmis.utils.DateUtils.DEFAULT_DATE_TIME_FORMAT;


/**
 * <p>
 * 实体类
 * 物联网气表指令数据
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
@TableName("qw_iot_gas_meter_command")
@ApiModel(value = "IotGasMeterCommand", description = "物联网气表指令数据")
@AllArgsConstructor
public class IotGasMeterCommand extends Entity<Long> {

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
     * 厂家id
     */
    @ApiModelProperty(value = "厂家id")
    @TableField("gas_meter_factory_id")
    @Excel(name = "厂家id")
    private Long gasMeterFactoryId;

    /**
     * 厂家名称
     */
    @ApiModelProperty(value = "厂家名称")
    @Length(max = 100, message = "厂家名称长度不能超过100")
    @TableField(value = "gas_meter_factory_name", condition = LIKE)
    @Excel(name = "厂家名称")
    private String gasMeterFactoryName;

    /**
     * 版号id
     */
    @ApiModelProperty(value = "版号id")
    @TableField("gas_meter_version_id")
    @Excel(name = "版号id")
    private Long gasMeterVersionId;

    /**
     * 版号名称
     */
    @ApiModelProperty(value = "版号名称")
    @Length(max = 30, message = "版号名称长度不能超过30")
    @TableField(value = "gas_meter_version_name", condition = LIKE)
    @Excel(name = "版号名称")
    private String gasMeterVersionName;

    /**
     * 气表类型id
     */
    @ApiModelProperty(value = "气表类型id")
    @TableField("gas_meter_type_id")
    @Excel(name = "气表类型id")
    private Long gasMeterTypeId;

    /**
     * 普表
     *             卡表
     *             物联网表
     *             流量计(普表)
     *             流量计(卡表）
     *             流量计(物联网表）
     */
    @ApiModelProperty(value = "普表")
    @Length(max = 30, message = "普表长度不能超过30")
    @TableField(value = "gas_meter_type_name", condition = LIKE)
    @Excel(name = "普表")
    private String gasMeterTypeName;

    /**
     * 气表编号
     */
    @ApiModelProperty(value = "气表编号")
    @Length(max = 50, message = "气表编号长度不能超过50")
    @TableField(value = "gas_meter_code", condition = LIKE)
    @Excel(name = "气表编号")
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
     * 表身号
     */
    @ApiModelProperty(value = "表身号")
    @Length(max = 30, message = "表身号长度不能超过30")
    @TableField(value = "meter_number", condition = LIKE)
    @Excel(name = "表身号")
    private String meterNumber;

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
     * 用气类型id
     */
    @ApiModelProperty(value = "用气类型id")
    @TableField("use_gas_type_id")
    @Excel(name = "用气类型id")
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
     * 价格方案id
     */
    @ApiModelProperty(value = "价格方案id")
    @TableField("price_id")
    @Excel(name = "价格方案id")
    private Long priceId;

    /**
     * 充值金额
     */
    @ApiModelProperty(value = "充值金额")
    @TableField("recharge_money")
    @Excel(name = "充值金额")
    private BigDecimal rechargeMoney;

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
     * 其他参数，建议存Json
     */
    @ApiModelProperty(value = "其他参数，建议存Json")
    @TableField(value = "command_parameter", condition = LIKE)
    @Excel(name = "其他参数，建议存Json")
    private String commandParameter;


    @Builder
    public IotGasMeterCommand(Long id, LocalDateTime createTime, Long createUser, Long updateUser, LocalDateTime updateTime, 
                    String companyCode, String companyName, Long orgId, String orgName, Long gasMeterFactoryId, 
                    String gasMeterFactoryName, Long gasMeterVersionId, String gasMeterVersionName, Long gasMeterTypeId, String gasMeterTypeName, String gasMeterCode, 
                    String gasMeterName, String meterNumber, CommandType commandType, String transactionNo, LocalDateTime to3Time, Long useGasTypeId, 
                    String useGasTypeName, Long priceId, BigDecimal rechargeMoney,Integer executeResult,String commandParameter) {
        this.id = id;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.orgId = orgId;
        this.orgName = orgName;
        this.gasMeterFactoryId = gasMeterFactoryId;
        this.gasMeterFactoryName = gasMeterFactoryName;
        this.gasMeterVersionId = gasMeterVersionId;
        this.gasMeterVersionName = gasMeterVersionName;
        this.gasMeterTypeId = gasMeterTypeId;
        this.gasMeterTypeName = gasMeterTypeName;
        this.gasMeterCode = gasMeterCode;
        this.gasMeterName = gasMeterName;
        this.meterNumber = meterNumber;
        this.commandType = commandType;
        this.transactionNo = transactionNo;
        this.to3Time = to3Time;
        this.useGasTypeId = useGasTypeId;
        this.useGasTypeName = useGasTypeName;
        this.priceId = priceId;
        this.rechargeMoney = rechargeMoney;
        this.executeResult = executeResult;
        this.commandParameter = commandParameter;
    }

}
