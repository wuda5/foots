package com.cdqckj.gmis.iot.qc.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * <p>
 * 实体类
 * 
 * </p>
 *
 * @author gmis
 * @since 2020-10-14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "IotDeviceDataHistorySaveDTO", description = "")
public class IotDeviceDataHistorySaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 设备出厂编号
     */
    @ApiModelProperty(value = "设备出厂编号")
    @Length(max = 255, message = "设备出厂编号长度不能超过255")
    private String deviceId;
    /**
     * 数据类型
     */
    @ApiModelProperty(value = "数据类型")
    @Length(max = 255, message = "数据类型长度不能超过255")
    private String dataType;
    /**
     * 上报数据
     */
    @ApiModelProperty(value = "上报数据")
    @Length(max = 65535, message = "上报数据长度不能超过65,535")
    private String deviceData;

}
