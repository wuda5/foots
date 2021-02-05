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
@ApiModel(value = "IotDeviceDataHistoryUpdateDTO", description = "")
public class IotDeviceDataHistoryUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @NotNull(message = "id不能为空", groups = SuperEntity.Update.class)
    private Long id;

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
