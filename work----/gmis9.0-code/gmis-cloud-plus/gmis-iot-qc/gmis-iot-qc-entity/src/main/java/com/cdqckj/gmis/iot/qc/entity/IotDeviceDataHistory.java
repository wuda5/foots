package com.cdqckj.gmis.iot.qc.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cdqckj.gmis.base.entity.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

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
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("qw_iot_device_data_history")
@ApiModel(value = "IotDeviceDataHistory", description = "")
@AllArgsConstructor
public class IotDeviceDataHistory extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 设备出厂编号
     */
    @ApiModelProperty(value = "设备出厂编号")
    @Length(max = 255, message = "设备出厂编号长度不能超过255")
    @TableField(value = "device_id", condition = LIKE)
    @Excel(name = "设备出厂编号")
    private String deviceId;

    /**
     * 数据类型
     */
    @ApiModelProperty(value = "数据类型")
    @Length(max = 255, message = "数据类型长度不能超过255")
    @TableField(value = "data_type", condition = LIKE)
    @Excel(name = "数据类型")
    private String dataType;

    /**
     * 上报数据
     */
    @ApiModelProperty(value = "上报数据")
    @Length(max = 65535, message = "上报数据长度不能超过65535")
    @TableField("device_data")
    @Excel(name = "上报数据")
    private String deviceData;


    @Builder
    public IotDeviceDataHistory(Long id, LocalDateTime createTime, Long createUser, LocalDateTime updateTime, Long updateUser,
                                String deviceId, String dataType, String deviceData) {
        this.id = id;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.deviceId = deviceId;
        this.dataType = dataType;
        this.deviceData = deviceData;
    }

}
