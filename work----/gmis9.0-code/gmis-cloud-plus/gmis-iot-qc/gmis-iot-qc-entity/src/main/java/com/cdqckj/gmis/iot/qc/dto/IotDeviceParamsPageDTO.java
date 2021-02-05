package com.cdqckj.gmis.iot.qc.dto;


import java.time.LocalDateTime;
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
import lombok.Data;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import java.io.Serializable;

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
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "IotDeviceParamsPageDTO", description = "物联网设备参数设置记录表")
public class IotDeviceParamsPageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 表身号
     */
    @ApiModelProperty(value = "表身号")
    @Length(max = 50, message = "表身号长度不能超过50")
    private String gasMeterNumber;
    /**
     * 多天未上报权限关阀：0--禁止；1 - 255 表示天数。 默认：5天
     */
    @ApiModelProperty(value = "多天未上报权限关阀：0--禁止；1 - 255 表示天数。 默认：5天")
    private String unUploadClose;
    /**
     * 多天不上传普通关阀控制参数：0--禁止；1 - 255 表示天数
     */
    @ApiModelProperty(value = "多天不上传普通关阀控制参数：0--禁止；1 - 255 表示天数")
    private String valveNoUpload;
    /**
     * 多天不用气关阀控制参数：  0--禁止；1 - 255 表示天数
     */
    @ApiModelProperty(value = "多天不用气关阀控制参数：  0--禁止；1 - 255 表示天数")
    private String valveCloseNouse;
    /**
     * 设置错峰时间间隔,单位秒，范围15 - 43。默认15
     */
    @ApiModelProperty(value = "设置错峰时间间隔,单位秒，范围15 - 43。默认15")
    private String timeInterva;
    /**
     * 余额不足报警值：值：数字
     */
    @ApiModelProperty(value = "余额不足报警值：值：数字")
    private String balanceAlarmValueOne;
    /**
     * 过流报警使能(默认关闭),0：禁止；1：使能
     */
    @ApiModelProperty(value = "过流报警使能(默认关闭),0：禁止；1：使能")
    private String flowoverEnable;
    /**
     * 定时上传参数 （标志/周期/小时/分钟)
     */
    @ApiModelProperty(value = "定时上传参数 （标志/周期/小时/分钟)")
    @Length(max = 100, message = "定时上传参数 （标志/周期/小时/分钟)长度不能超过100")
    private String timedParamMeter;

}
