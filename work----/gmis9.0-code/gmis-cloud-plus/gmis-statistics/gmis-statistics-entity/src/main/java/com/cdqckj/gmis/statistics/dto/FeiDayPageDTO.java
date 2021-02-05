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
 * 整个系统的费用的统计历史数据按照天来统计，实时统计。
 * 数据统计的维度到项目和收费员这个最小的维度，每个项目
 * </p>
 *
 * @author gmis
 * @since 2020-11-20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "FeiDayPageDTO", description = "整个系统的费用的统计历史数据按照天来统计，实时统计。 数据统计的维度到项目和收费员这个最小的维度，每个项目")
public class FeiDayPageDTO implements Serializable {

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
     * 收费员ID
     */
    @ApiModelProperty(value = "收费员ID")
    private Long createUserId;
    /**
     * 总的数量
     */
    @ApiModelProperty(value = "总的数量")
    @NotNull(message = "总的数量不能为空")
    private Integer totalNumber;
    /**
     * 1收费 2退费
     */
    @ApiModelProperty(value = "1收费 2退费")
    @NotNull(message = "1收费 2退费不能为空")
    private Integer type;
    /**
     * 1线上 2线下
     */
    @ApiModelProperty(value = "1线上 2线下")
    @NotNull(message = "1线上 2线下不能为空")
    private Integer online;
    /**
     * 1系统配置项目  2充值赠送
     */
    @ApiModelProperty(value = "1系统配置项目  2充值赠送")
    @NotNull(message = "1系统配置项目  2充值赠送不能为空")
    private Integer chargeType;
    /**
     * 收费类型
     */
    @ApiModelProperty(value = "收费类型")
    @NotEmpty(message = "收费类型不能为空")
    @Length(max = 11, message = "收费类型长度不能超过11")
    private String chargeItemSourceCode;
    /**
     * 支付宝 现金 微信
     */
    @ApiModelProperty(value = "支付宝 现金 微信")
    @NotEmpty(message = "支付宝 现金 微信不能为空")
    @Length(max = 32, message = "支付宝 现金 微信长度不能超过32")
    private String chargeMethodCode;
    /**
     * 收费项金额
     */
    @ApiModelProperty(value = "收费项金额")
    @NotNull(message = "收费项金额不能为空")
    private BigDecimal chargeItemMoney;
    /**
     * 数目
     */
    @ApiModelProperty(value = "数目")
    @NotNull(message = "数目不能为空")
    private Integer amount;
    /**
     * 统计的是哪一天的数据
     */
    @ApiModelProperty(value = "统计的是哪一天的数据")
    @NotNull(message = "统计的是哪一天的数据不能为空")
    private LocalDateTime stsDay;

}
