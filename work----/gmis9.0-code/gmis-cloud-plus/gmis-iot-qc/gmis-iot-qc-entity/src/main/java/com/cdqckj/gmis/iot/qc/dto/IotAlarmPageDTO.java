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
 * 报警信息
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
@ApiModel(value = "IotAlarmPageDTO", description = "报警信息")
public class IotAlarmPageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 报警状态
     */
    @ApiModelProperty(value = "报警状态")
    private Integer alarmStatus;
    /**
     * 报警类型
     */
    @ApiModelProperty(value = "报警类型")
    @Length(max = 255, message = "报警类型长度不能超过255")
    private String alarmType;
    /**
     * 报警信息
     */
    @ApiModelProperty(value = "报警信息")
    @Length(max = 65535, message = "报警信息长度不能超过65,535")
    private String alarmContent;
    /**
     * 设备id
     */
    @ApiModelProperty(value = "设备id")
    @Length(max = 255, message = "设备id长度不能超过255")
    private String deviceId;

}
