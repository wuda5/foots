package com.cdqckj.gmis.appmanager.dto;

import com.cdqckj.gmis.validator.annotations.NotLessThanZero;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 应用设置DTO
 */
@Data
@ApiModel(value = "AppSettingDTO", description = "应用设置")
public class AppSettingDTO implements Serializable {

    @NotNull(message = "主键不能为空")
    @ApiModelProperty(value = "每分钟访问次数限制")
    private Long id;

    /**
     * 每分钟访问次数限制
     */
    @ApiModelProperty(value = "每分钟访问次数限制")
    @NotLessThanZero(message = "每分钟访问次数限制大于零")
    private Integer visitsTimes;
    /**
     * 访问时间间隔,单位ms
     */
    @ApiModelProperty(value = "访问时间间隔,单位ms")
    @NotLessThanZero(message = "访问时间间隔大于零")
    private Integer visitsInterval;
    /**
     * 应用有效开始时间
     */
    @ApiModelProperty(value = "应用有效开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date validTimeStart;
    /**
     * 应用有效结束时间
     */
    @ApiModelProperty(value = "应用有效结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date validTimeEnd;

}
