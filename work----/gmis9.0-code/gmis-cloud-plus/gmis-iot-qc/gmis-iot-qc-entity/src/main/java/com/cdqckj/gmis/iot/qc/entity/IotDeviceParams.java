package com.cdqckj.gmis.iot.qc.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cdqckj.gmis.base.entity.Entity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import static com.cdqckj.gmis.utils.DateUtils.DEFAULT_DATE_TIME_FORMAT;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

/**
 * <p>
 * 实体类
 * 物联网设备参数设置记录表
 * </p>
 *
 * @author gmis
 * @since 2020-12-08
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("qw_iot_device_params")
@ApiModel(value = "IotDeviceParams", description = "物联网设备参数设置记录表")
@AllArgsConstructor
public class IotDeviceParams extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 表身号
     */
    @ApiModelProperty(value = "表身号")
    @Length(max = 50, message = "表身号长度不能超过50")
    @TableField(value = "gas_meter_number", condition = LIKE)
    @Excel(name = "表身号")
    private String gasMeterNumber;

    /**
     * 多天未上报权限关阀：0--禁止；1 - 255 表示天数。 默认：5天
     */
    @ApiModelProperty(value = "多天未上报权限关阀：0--禁止；1 - 255 表示天数。 默认：5天")
    @TableField("un_upLoad_close")
    @Excel(name = "多天未上报权限关阀：0--禁止；1 - 255 表示天数。 默认：5天")
    private String unUploadClose;

    /**
     * 多天不上传普通关阀控制参数：0--禁止；1 - 255 表示天数
     */
    @ApiModelProperty(value = "多天不上传普通关阀控制参数：0--禁止；1 - 255 表示天数")
    @TableField("valve_no_upLoad")
    @Excel(name = "多天不上传普通关阀控制参数：0--禁止；1 - 255 表示天数")
    private String valveNoUpload;

    /**
     * 多天不用气关阀控制参数：  0--禁止；1 - 255 表示天数
     */
    @ApiModelProperty(value = "多天不用气关阀控制参数：  0--禁止；1 - 255 表示天数")
    @TableField("valve_close_nouse")
    @Excel(name = "多天不用气关阀控制参数：  0--禁止；1 - 255 表示天数")
    private String valveCloseNouse;

    /**
     * 设置错峰时间间隔,单位秒，范围15 - 43。默认15
     */
    @ApiModelProperty(value = "设置错峰时间间隔,单位秒，范围15 - 43。默认15")
    @TableField("time_interva")
    @Excel(name = "设置错峰时间间隔,单位秒，范围15 - 43。默认15")
    private String timeInterva;

    /**
     * 余额不足报警值：值：数字
     */
    @ApiModelProperty(value = "余额不足报警值：值：数字")
    @TableField("balance_alarm_value_one")
    @Excel(name = "余额不足报警值：值：数字")
    private BigDecimal balanceAlarmValueOne;

    /**
     * 过流报警使能(默认关闭),0：禁止；1：使能
     */
    @ApiModelProperty(value = "过流报警使能(默认关闭),0：禁止；1：使能")
    @TableField("flowover_enable")
    @Excel(name = "过流报警使能(默认关闭),0：禁止；1：使能")
    private String flowoverEnable;

    /**
     * 定时上传参数 （标志/周期/小时/分钟)
     */
    @ApiModelProperty(value = "定时上传参数 （标志/周期/小时/分钟)")
    @Length(max = 100, message = "定时上传参数 （标志/周期/小时/分钟)长度不能超过100")
    @TableField(value = "timed_param_meter", condition = LIKE)
    @Excel(name = "定时上传参数 （标志/周期/小时/分钟)")
    private String timedParamMeter;

    @TableField(exist = false)
    private String domainId;

    @Builder
    public IotDeviceParams(Long id, 
                    String gasMeterNumber, String unUploadClose, String valveNoUpload, String valveCloseNouse, String timeInterva,
                    BigDecimal balanceAlarmValueOne, String flowoverEnable, String timedParamMeter) {
        this.id = id;
        this.gasMeterNumber = gasMeterNumber;
        this.unUploadClose = unUploadClose;
        this.valveNoUpload = valveNoUpload;
        this.valveCloseNouse = valveCloseNouse;
        this.timeInterva = timeInterva;
        this.balanceAlarmValueOne = balanceAlarmValueOne;
        this.flowoverEnable = flowoverEnable;
        this.timedParamMeter = timedParamMeter;
    }

}
