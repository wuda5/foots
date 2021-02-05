package com.cdqckj.gmis.statistics.dto;

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
 * 表具的厂家，类型的两个维度
 * </p>
 *
 * @author gmis
 * @since 2020-11-12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "MeterNowSaveDTO", description = "表具的厂家，类型的两个维度")
public class MeterNowSaveDTO implements Serializable {

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
     * 操作员的ID
     */
    @ApiModelProperty(value = "操作员的ID")
    @NotNull(message = "操作员的ID不能为空")
    private Long createUserId;
    /**
     * 表类型ID
     */
    @ApiModelProperty(value = "表类型ID")
    @NotEmpty(message = "表类型ID不能为空")
    @Length(max = 32, message = "表类型ID长度不能超过32")
    private String gasMeterTypeCode;
    /**
     * 厂家ID
     */
    @ApiModelProperty(value = "厂家ID")
    @NotNull(message = "厂家ID不能为空")
    private Long gasMeterFactoryId;
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
