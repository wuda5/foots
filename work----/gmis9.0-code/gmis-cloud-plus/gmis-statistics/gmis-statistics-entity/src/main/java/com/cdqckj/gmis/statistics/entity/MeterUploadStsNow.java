package com.cdqckj.gmis.statistics.entity;
import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
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
 * 在线 报警的表具数量
 * </p>
 *
 * @author ljg
 * @since 2021-01-06
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sts_meter_upload_sts_now")
@ApiModel(value = "MeterUploadStsNow", description = "在线 报警的表具数量")
@AllArgsConstructor
public class MeterUploadStsNow extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 租户CODE
     */
    @ApiModelProperty(value = "租户CODE")
    @NotEmpty(message = "租户CODE不能为空")
    @Length(max = 32, message = "租户CODE长度不能超过32")
    @TableField(value = "t_code", condition = LIKE)
    @Excel(name = "租户CODE")
    private String tCode;

    /**
     * 公司CODE
     */
    @ApiModelProperty(value = "公司CODE")
    @NotEmpty(message = "公司CODE不能为空")
    @Length(max = 32, message = "公司CODE长度不能超过32")
    @TableField(value = "company_code", condition = LIKE)
    @Excel(name = "公司CODE")
    private String companyCode;

    /**
     * 组织ID
     */
    @ApiModelProperty(value = "组织ID")
    @NotNull(message = "组织ID不能为空")
    @TableField("org_id")
    @Excel(name = "组织ID")
    private Long orgId;

    /**
     * 营业厅ID
     */
    @ApiModelProperty(value = "营业厅ID")
    @TableField("business_hall_id")
    @Excel(name = "营业厅ID")
    private Long businessHallId;

    /**
     * 用户的ID
     */
    @ApiModelProperty(value = "用户的ID")
    @NotNull(message = "用户的ID不能为空")
    @TableField("create_user_id")
    @Excel(name = "用户的ID")
    private Long createUserId;

    /**
     * 设备id
     */
    @ApiModelProperty(value = "设备id")
    @Length(max = 255, message = "设备id长度不能超过255")
    @TableField(value = "device_id", condition = LIKE)
    @Excel(name = "设备id")
    private String deviceId;

    /**
     * 1在线 2报警
     */
    @ApiModelProperty(value = "1在线 2报警")
    @TableField("type")
    @Excel(name = "1在线 2报警")
    private Integer type;

    /**
     * 报警类型
     */
    @ApiModelProperty(value = "报警类型")
    @Length(max = 255, message = "报警类型长度不能超过255")
    @TableField(value = "alarm_type", condition = LIKE)
    @Excel(name = "报警类型")
    private String alarmType;

    /**
     * 次数
     */
    @ApiModelProperty(value = "次数")
    @NotNull(message = "次数不能为空")
    @TableField("upload_times")
    @Excel(name = "次数")
    private Integer uploadTimes;

    /**
     * 统计的时间
     */
    @ApiModelProperty(value = "统计的时间")
    @NotNull(message = "统计的时间不能为空")
    @TableField("sts_day")
    @Excel(name = "统计的时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime stsDay;


    @Builder
    public MeterUploadStsNow(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime,
                             String tCode, String companyCode, Long orgId, Long businessHallId, Long createUserId,
                             String deviceId, Integer type, String alarmType, Integer uploadTimes, LocalDateTime stsDay) {
        this.id = id;
        this.createUser = createUser;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.tCode = tCode;
        this.companyCode = companyCode;
        this.orgId = orgId;
        this.businessHallId = businessHallId;
        this.createUserId = createUserId;
        this.deviceId = deviceId;
        this.type = type;
        this.alarmType = alarmType;
        this.uploadTimes = uploadTimes;
        this.stsDay = stsDay;
    }

}
