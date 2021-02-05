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
 * 报警信息
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
@TableName("qw_iot_alarm")
@ApiModel(value = "IotAlarm", description = "报警信息")
@AllArgsConstructor
public class IotAlarm extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 报警状态
     */
    @ApiModelProperty(value = "报警状态")
    @TableField("alarm_status")
    @Excel(name = "报警状态")
    private Integer alarmStatus;

    /**
     * 报警类型
     */
    @ApiModelProperty(value = "报警类型")
    @Length(max = 255, message = "报警类型长度不能超过255")
    @TableField(value = "alarm_type", condition = LIKE)
    @Excel(name = "报警类型")
    private String alarmType;

    /**
     * 报警信息
     */
    @ApiModelProperty(value = "报警信息")
    @Length(max = 65535, message = "报警信息长度不能超过65535")
    @TableField("alarm_content")
    @Excel(name = "报警信息")
    private String alarmContent;

    /**
     * 设备id
     */
    @ApiModelProperty(value = "设备id")
    @Length(max = 255, message = "设备id长度不能超过255")
    @TableField(value = "device_id", condition = LIKE)
    @Excel(name = "设备id")
    private String deviceId;


    @Builder
    public IotAlarm(Long id, LocalDateTime createTime, Long createUser, LocalDateTime updateTime, Long updateUser, 
                    Integer alarmStatus, String alarmType, String alarmContent, String deviceId) {
        this.id = id;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.alarmStatus = alarmStatus;
        this.alarmType = alarmType;
        this.alarmContent = alarmContent;
        this.deviceId = deviceId;
    }

}
