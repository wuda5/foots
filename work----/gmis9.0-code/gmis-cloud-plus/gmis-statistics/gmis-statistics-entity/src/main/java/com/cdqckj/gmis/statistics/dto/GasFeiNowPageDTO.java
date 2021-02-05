package com.cdqckj.gmis.statistics.dto;

import java.time.LocalDateTime;
import java.math.BigDecimal;
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
 * 燃气费
 * </p>
 *
 * @author gmis
 * @since 2020-11-19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "GasFeiNowPageDTO", description = "燃气费")
public class GasFeiNowPageDTO implements Serializable {

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
     * 总的数量
     */
    @ApiModelProperty(value = "总的数量")
    @NotNull(message = "总的数量不能为空")
    private Integer totalNumber;
    /**
     * 客户类型ID
     */
    @ApiModelProperty(value = "客户类型ID")
    @Length(max = 32, message = "客户类型ID长度不能超过32")
    private String customerTypeCode;
    /**
     * 气量
     */
    @ApiModelProperty(value = "气量")
    @NotNull(message = "气量不能为空")
    private BigDecimal gasAmount;
    /**
     * 金额
     */
    @ApiModelProperty(value = "金额")
    @NotNull(message = "金额不能为空")
    private BigDecimal feiAmount;
    /**
     * 统计的是哪一天的数据
     */
    @ApiModelProperty(value = "统计的是哪一天的数据")
    @NotNull(message = "统计的是哪一天的数据不能为空")
    private LocalDateTime stsDay;

}
