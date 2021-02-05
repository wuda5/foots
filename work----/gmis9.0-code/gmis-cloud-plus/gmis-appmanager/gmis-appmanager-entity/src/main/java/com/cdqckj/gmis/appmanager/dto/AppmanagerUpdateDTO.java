package com.cdqckj.gmis.appmanager.dto;

import com.cdqckj.gmis.appmanager.enumeration.AppmanagerAppTypeEnum;
import com.cdqckj.gmis.base.entity.SuperEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

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
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "AppmanagerUpdateDTO", description = "应用管理表")
public class AppmanagerUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @NotNull(message = "id不能为空", groups = SuperEntity.Update.class)
    private Long id;

    /**
     * 租户id
     */
    @ApiModelProperty(value = "租户id")
    private Long tenantId;

    @ApiModelProperty(value = "租户code")
    private String tenantCode;
    /**
     * 租户名字
     */
    @ApiModelProperty(value = "租户名字")
    @Length(max = 255, message = "租户名字长度不能超过255")
    private String tenantName;
    /**
     * 应用类型
     * AppType#{APPLET:applet,小程序;APP:app,app;KAKU:kaku,卡库;OTHER:other,其他}
     */
    @ApiModelProperty(value = "应用类型")
    private AppmanagerAppTypeEnum appType;
    /**
     * 应用名称
     */
    @ApiModelProperty(value = "应用名称")
    @Length(max = 255, message = "应用名称长度不能超过255")
    private String appName;
    /**
     * 应用唯一标识
     */
    @ApiModelProperty(value = "应用唯一标识")
    @Length(max = 0, message = "应用唯一标识长度不能超过0")
    private String appId;
    /**
     * 应用密匙
     */
    @ApiModelProperty(value = "应用密匙")
    @Length(max = 255, message = "应用密匙长度不能超过255")
    private String appSecret;
    /**
     * 每分钟访问次数限制
     */
    @ApiModelProperty(value = "每分钟访问次数限制")
    private Integer visitsTimes;
    /**
     * 访问时间间隔,单位ms
     */
    @ApiModelProperty(value = "访问时间间隔,单位ms")
    private Integer visitsInterval;
    /**
     * 应用有效开始时间
     */
    @ApiModelProperty(value = "应用有效开始时间")
    private LocalDateTime validTimeStart;
    /**
     * 应用有效结束时间
     */
    @ApiModelProperty(value = "应用有效结束时间")
    private LocalDateTime validTimeEnd;
    /**
     * 扩展字段1
     */
    @ApiModelProperty(value = "扩展字段1")
    @Length(max = 255, message = "扩展字段1长度不能超过255")
    private String attr1;
    /**
     * 扩展字段2
     */
    @ApiModelProperty(value = "扩展字段2")
    @Length(max = 255, message = "扩展字段2长度不能超过255")
    private String attr2;
    /**
     * 扩展字段3
     */
    @ApiModelProperty(value = "扩展字段3")
    @Length(max = 255, message = "扩展字段3长度不能超过255")
    private String attr3;

    @ApiModelProperty(value = "应用状态")
    private Boolean status;
}
