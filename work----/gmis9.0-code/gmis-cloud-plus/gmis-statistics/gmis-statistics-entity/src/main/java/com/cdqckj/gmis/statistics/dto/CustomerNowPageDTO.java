package com.cdqckj.gmis.statistics.dto;

import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cdqckj.gmis.base.entity.TreeEntity;
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
 * 用户的客户档案的统计
 * </p>
 *
 * @author gmis
 * @since 2020-11-07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "CustomerNowPageDTO", description = "用户的客户档案的统计")
public class CustomerNowPageDTO implements Serializable {

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
    private Long orgId;
    /**
     * 营业厅ID
     */
    @ApiModelProperty(value = "营业厅ID")
    private Long businessHallId;
    /**
     * 创建档案的操作员的ID
     */
    @ApiModelProperty(value = "创建档案的操作员的ID")
    @NotNull(message = "创建档案的操作员的ID不能为空")
    private Long createUserId;
    /**
     * 1预建档  2有效 3销户 4添加黑名单 5移除黑名单
     */
    @ApiModelProperty(value = "1预建档  2有效 3销户 4添加黑名单 5移除黑名单")
    @NotNull(message = "1预建档  2有效 3销户 4添加黑名单 5移除黑名单不能为空")
    private Integer type;
    /**
     * 数量
     */
    @ApiModelProperty(value = "数量")
    @NotNull(message = "数量不能为空")
    private Integer amount;
    /**
     * 统计的是哪一天的数据
     */
    @ApiModelProperty(value = "统计的是哪一天的数据")
    @NotNull(message = "统计的是哪一天的数据不能为空")
    private LocalDateTime stsDay;

    @ApiModelProperty(value = "名称")
    @NotEmpty(message = "名称不能为空")
    @Length(max = 255, message = "名称长度不能超过255")
    protected String label;

    @ApiModelProperty(value = "父ID")
    protected Long parentId;

    @ApiModelProperty(value = "排序号")
    protected Integer sortValue;
}
