package com.cdqckj.gmis.statistics.dto;

import java.time.LocalDateTime;
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
import lombok.Data;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import java.io.Serializable;

/**
 * <p>
 * 实体类
 * 在线,报警两个指标
 * </p>
 *
 * @author gmis
 * @since 2020-11-13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "MeterUploadStsNowPageDTO", description = "在线,报警两个指标")
public class MeterUploadStsNowPageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 租户CODE
     */
    @ApiModelProperty(value = "租户CODE")
    @NotEmpty(message = "租户CODE不能为空")
    @Length(max = 32, message = "租户CODE长度不能超过32")
    private String tCode;
    /**
     * 公司CODE
     */
    @ApiModelProperty(value = "公司CODE")
    @NotEmpty(message = "公司CODE不能为空")
    @Length(max = 32, message = "公司CODE长度不能超过32")
    private String companyCode;
    /**
     * 组织ID
     */
    @ApiModelProperty(value = "组织ID")
    @NotNull(message = "组织ID不能为空")
    private Long orgId;
    /**
     * 营业厅ID
     */
    @ApiModelProperty(value = "营业厅ID")
    private Long businessHallId;
    /**
     * 创建计划的用户的id
     */
    @ApiModelProperty(value = "创建计划的用户的id")
    @NotNull(message = "创建计划的用户的id不能为空")
    private Long createUserId;
    /**
     * 市id
     */
    @ApiModelProperty(value = "市id")
    @Length(max = 50, message = "市id长度不能超过50")
    private String cityCode;
    /**
     * 区id
     */
    @ApiModelProperty(value = "区id")
    @Length(max = 32, message = "区id长度不能超过32")
    private String areaCode;
    /**
     * 街道id
     */
    @ApiModelProperty(value = "街道id")
    @Length(max = 32, message = "街道id长度不能超过32")
    private String streetCode;
    /**
     * 小区id
     */
    @ApiModelProperty(value = "小区id")
    @Length(max = 32, message = "小区id长度不能超过32")
    private String communityCode;
    /**
     * 1在线 2报警
     */
    @ApiModelProperty(value = "1在线 2报警")
    private Integer type;
    /**
     * 报警类型
     */
    @ApiModelProperty(value = "报警类型")
    @Length(max = 255, message = "报警类型长度不能超过255")
    private String alarmType;
    /**
     * 数量
     */
    @ApiModelProperty(value = "数量")
    @NotNull(message = "数量不能为空")
    private Integer amount;
    /**
     * 统计的时间
     */
    @ApiModelProperty(value = "统计的时间")
    @NotNull(message = "统计的时间不能为空")
    private LocalDateTime stsDay;

}
