package com.cdqckj.gmis.appmanager.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cdqckj.gmis.appmanager.enumeration.AppmanagerAppTypeEnum;
import com.cdqckj.gmis.base.entity.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;
import static com.cdqckj.gmis.utils.DateUtils.DEFAULT_DATE_TIME_FORMAT;

/**
 * <p>
 * 实体类
 * 应用管理表
 * </p>
 *
 * @author gmis
 * @since 2020-09-16
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("gl_appmanager")
@ApiModel(value = "Appmanager", description = "应用管理表")
@AllArgsConstructor
public class Appmanager extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 租户id
     */
    @ApiModelProperty(value = "租户id")
    @TableField("tenant_id")
    @Excel(name = "租户id")
    private Long tenantId;

    @ApiModelProperty(value = "租户code")
    @TableField(value = "tenant_code")
    @Excel(name = "租户code")
    private String tenantCode;

    /**
     * 租户名字
     */
    @ApiModelProperty(value = "租户名字")
    @Length(max = 255, message = "租户名字长度不能超过255")
    @TableField(value = "tenant_name", condition = LIKE)
    @Excel(name = "租户名字")
    private String tenantName;

    /**
     * 应用类型
     * AppType#{APPLET:applet,小程序;APP:app,app;KAKU:kaku,卡库;OTHER:other,其他}
     */
    @ApiModelProperty(value = "应用类型")
    @TableField("app_type")
    @Excel(name = "应用类型", replace = {"applet_APPLET", "app_APP", "kaku_KAKU", "other_OTHER",  "_null"})
    private AppmanagerAppTypeEnum appType;

    /**
     * 应用名称
     */
    @ApiModelProperty(value = "应用名称")
    @Length(max = 255, message = "应用名称长度不能超过255")
    @TableField(value = "app_name", condition = LIKE)
    @Excel(name = "应用名称")
    private String appName;

    /**
     * 应用唯一标识
     */
    @ApiModelProperty(value = "应用唯一标识")
    @Length(max = 36, message = "应用唯一标识长度不能超过36")
    @TableField(value = "app_id", condition = LIKE)
    @Excel(name = "应用唯一标识")
    private String appId;

    /**
     * 应用密匙
     */
    @ApiModelProperty(value = "应用密匙")
    @Length(max = 255, message = "应用密匙长度不能超过255")
    @TableField(value = "app_secret", condition = LIKE)
    @Excel(name = "应用密匙")
    private String appSecret;

    /**
     * 每分钟访问次数限制
     */
    @ApiModelProperty(value = "每分钟访问次数限制")
    @TableField("visits_times")
    @Excel(name = "每分钟访问次数限制")
    private Integer visitsTimes;

    /**
     * 访问时间间隔,单位ms
     */
    @ApiModelProperty(value = "访问时间间隔,单位ms")
    @TableField("visits_interval")
    @Excel(name = "访问时间间隔,单位ms")
    private Integer visitsInterval;

    /**
     * 应用有效开始时间
     */
    @ApiModelProperty(value = "应用有效开始时间")
    @TableField("valid_time_start")
    @Excel(name = "应用有效开始时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime validTimeStart;

    /**
     * 应用有效结束时间
     */
    @ApiModelProperty(value = "应用有效结束时间")
    @TableField("valid_time_end")
    @Excel(name = "应用有效结束时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime validTimeEnd;

    /**
     * 启用状态 0、禁用,1、启用
     */
    @ApiModelProperty(value = "应用有效结束时间")
    @TableField("status")
    @Excel(name = "启用状态", width = 10)
    private Boolean status;

    /**
     * 扩展字段1
     */
    @ApiModelProperty(value = "扩展字段1")
    @Length(max = 255, message = "扩展字段1长度不能超过255")
    @TableField(value = "attr1", condition = LIKE)
    @Excel(name = "扩展字段1")
    private String attr1;

    /**
     * 扩展字段2
     */
    @ApiModelProperty(value = "扩展字段2")
    @Length(max = 255, message = "扩展字段2长度不能超过255")
    @TableField(value = "attr2", condition = LIKE)
    @Excel(name = "扩展字段2")
    private String attr2;

    /**
     * 扩展字段3
     */
    @ApiModelProperty(value = "扩展字段3")
    @Length(max = 255, message = "扩展字段3长度不能超过255")
    @TableField(value = "attr3", condition = LIKE)
    @Excel(name = "扩展字段3")
    private String attr3;


    @Builder
    public Appmanager(Long id, LocalDateTime createTime, Long createUser, LocalDateTime updateTime, Long updateUser, 
                    Long tenantId, String tenantName, AppmanagerAppTypeEnum appType, String appName, String appId,
                    String appSecret, Integer visitsTimes, Integer visitsInterval, LocalDateTime validTimeStart, LocalDateTime validTimeEnd, String attr1, 
                    String attr2, String attr3) {
        this.id = id;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.tenantId = tenantId;
        this.tenantName = tenantName;
        this.appType = appType;
        this.appName = appName;
        this.appId = appId;
        this.appSecret = appSecret;
        this.visitsTimes = visitsTimes;
        this.visitsInterval = visitsInterval;
        this.validTimeStart = validTimeStart;
        this.validTimeEnd = validTimeEnd;
        this.attr1 = attr1;
        this.attr2 = attr2;
        this.attr3 = attr3;
    }

}
