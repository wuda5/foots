package com.cdqckj.gmis.iot.qc.dto;

import com.cdqckj.gmis.base.entity.SuperEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
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
@ApiModel(value = "IotAlarmUpdateDTO", description = "报警信息")
public class IotAlarmUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @NotNull(message = "id不能为空", groups = SuperEntity.Update.class)
    private Long id;

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
