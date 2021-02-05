package com.cdqckj.gmis.iot.qc.dto;

import com.cdqckj.gmis.base.entity.SuperEntity;
import com.cdqckj.gmis.iot.qc.enumeration.CommandType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

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
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "IotGasMeterCommandDetailUpdateDTO", description = "物联网气表指令明细数据")
public class IotGasMeterCommandDetailUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @NotNull(message = "id不能为空", groups = SuperEntity.Update.class)
    private Long id;

    /**
     * 公司编码
     */
    @ApiModelProperty(value = "公司编码")
    @Length(max = 32, message = "公司编码长度不能超过32")
    private String companyCode;
    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    @Length(max = 100, message = "公司名称长度不能超过100")
    private String companyName;
    /**
     * 组织id
     */
    @ApiModelProperty(value = "组织id")
    private Long orgId;
    /**
     * 组织名称
     */
    @ApiModelProperty(value = "组织名称")
    @Length(max = 100, message = "组织名称长度不能超过100")
    private String orgName;
    /**
     * 指令id
     */
    @ApiModelProperty(value = "指令id")
    private Long commandId;
    /**
     * 表身号
     */
    @ApiModelProperty(value = "表身号")
    @Length(max = 30, message = "表身号长度不能超过30")
    private String meterNumber;
    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    @Length(max = 30, message = "客户编号长度不能超过30")
    private String customerCode;
    /**
     * 气表编号
     */
    @ApiModelProperty(value = "气表编号")
    @Length(max = 50, message = "气表编号长度不能超过50")
    private String gasMeterCode;
    /**
     * 指令类型
     * #CommandType{CHANGE_METER:换表;RECHARGE:充值;OPEN_VALVE:开阀;CLOSE_VALVE:关阀;ADJ_PRICE:调价;}
     */
    @ApiModelProperty(value = "指令类型")
    private CommandType commandType;
    /**
     * QNMS3.0流水号
     */
    @ApiModelProperty(value = "QNMS3.0流水号")
    @Length(max = 50, message = "QNMS3.0流水号长度不能超过50")
    private String transactionNo;
    /**
     * 到达3.0时间
     */
    @ApiModelProperty(value = "到达3.0时间")
    private LocalDateTime to3Time;
    /**
     * 执行时间
     */
    @ApiModelProperty(value = "执行时间")
    private LocalDateTime executeTime;
    /**
     * 0:未执行
     *             1:执行成功
     *             其他自编：失败
     */
    @ApiModelProperty(value = "0:未执行")
    private Integer executeResult;
    /**
     * 0:待发送
     *             1:已发至3.0
     *             2:已执行
     *             10：重试
     *             100：取消执行
     */
    @ApiModelProperty(value = "0:待发送")
    private Integer commandStage;
    /**
     * 0：无效
     * 1：有效
     */
    @ApiModelProperty(value = "0：无效")
    private Integer commandStatus;
    /**
     * 其他参数，建议存Json
     */
    @ApiModelProperty(value = "其他参数，建议存Json")
    @Length(max = 2000, message = "其他参数，建议存Json长度不能超过2000")
    private String commandParameter;
    /**
     * 错误描述
     */
    @ApiModelProperty(value = "错误描述")
    private String errorDesc;
}
