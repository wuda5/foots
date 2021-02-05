package com.cdqckj.gmis.iot.qc.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.cdqckj.gmis.base.entity.SuperEntity;
import com.cdqckj.gmis.iot.qc.enumeration.CommandType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

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
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "IotGasMeterCommandUpdateDTO", description = "物联网气表指令数据")
public class IotGasMeterCommandUpdateDTO implements Serializable {

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
     * 厂家id
     */
    @ApiModelProperty(value = "厂家id")
    private Long gasMeterFactoryId;
    /**
     * 厂家名称
     */
    @ApiModelProperty(value = "厂家名称")
    @Length(max = 100, message = "厂家名称长度不能超过100")
    private String gasMeterFactoryName;
    /**
     * 版号id
     */
    @ApiModelProperty(value = "版号id")
    private Long gasMeterVersionId;
    /**
     * 版号名称
     */
    @ApiModelProperty(value = "版号名称")
    @Length(max = 30, message = "版号名称长度不能超过30")
    private String gasMeterVersionName;
    /**
     * 气表类型id
     */
    @ApiModelProperty(value = "气表类型id")
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
    private String gasMeterTypeName;
    /**
     * 气表编号
     */
    @ApiModelProperty(value = "气表编号")
    @Length(max = 50, message = "气表编号长度不能超过50")
    private String gasMeterCode;
    /**
     * 气表名称
     */
    @ApiModelProperty(value = "气表名称")
    @Length(max = 100, message = "气表名称长度不能超过100")
    private String gasMeterName;
    /**
     * 表身号
     */
    @ApiModelProperty(value = "表身号")
    @Length(max = 30, message = "表身号长度不能超过30")
    private String meterNumber;
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
     * 用气类型id
     */
    @ApiModelProperty(value = "用气类型id")
    private Long useGasTypeId;
    /**
     * 用气类型名称
     */
    @ApiModelProperty(value = "用气类型名称")
    @Length(max = 100, message = "用气类型名称长度不能超过100")
    private String useGasTypeName;
    /**
     * 价格方案id
     */
    @ApiModelProperty(value = "价格方案id")
    private Long priceId;
    /**
     * 充值金额
     */
    @ApiModelProperty(value = "充值金额")
    private BigDecimal rechargeMoney;
    /**
     * 0:未执行
     *             1:执行成功
     *             其他自编：失败
     */
    @ApiModelProperty(value = "0:未执行")
    private Integer executeResult;

    /**
     * 其他参数，建议存Json
     */
    @ApiModelProperty(value = "其他参数，建议存Json")
    private String commandParameter;
}
