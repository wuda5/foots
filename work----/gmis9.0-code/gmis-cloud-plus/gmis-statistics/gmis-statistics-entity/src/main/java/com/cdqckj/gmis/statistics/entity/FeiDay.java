package com.cdqckj.gmis.statistics.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
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
 * 整个系统的费用的统计历史数据按照天来统计，实时统计。
 * 数据统计的维度到项目和收费员这个最小的维度，每个项目
 * </p>
 *
 * @author gmis
 * @since 2020-11-20
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sts_fei_day")
@ApiModel(value = "FeiDay", description = "整个系统的费用的统计历史数据按照天来统计，实时统计。")
@AllArgsConstructor
public class FeiDay extends Entity<Long> {

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
     * 收费员ID
     */
    @ApiModelProperty(value = "收费员ID")
    @TableField("create_user_id")
    @Excel(name = "收费员ID")
    private Long createUserId;

    /**
     * 总的数量
     */
    @ApiModelProperty(value = "总的数量")
    @NotNull(message = "总的数量不能为空")
    @TableField("total_number")
    @Excel(name = "总的数量")
    private Integer totalNumber;

    /**
     * 1收费 2退费
     */
    @ApiModelProperty(value = "1收费 2退费")
    @NotNull(message = "1收费 2退费不能为空")
    @TableField("type")
    @Excel(name = "1收费 2退费")
    private Integer type;

    /**
     * 1线上 2线下
     */
    @ApiModelProperty(value = "1线上 2线下")
    @NotNull(message = "1线上 2线下不能为空")
    @TableField("online")
    @Excel(name = "1线上 2线下")
    private Integer online;

    /**
     * 收费类型
     */
    @ApiModelProperty(value = "收费类型")
    @NotEmpty(message = "收费类型不能为空")
    @Length(max = 11, message = "收费类型长度不能超过11")
    @TableField(value = "charge_item_source_code", condition = LIKE)
    @Excel(name = "收费类型")
    private String chargeItemSourceCode;

    /**
     * 支付宝 现金 微信
     */
    @ApiModelProperty(value = "支付宝 现金 微信")
    @NotEmpty(message = "支付宝 现金 微信不能为空")
    @Length(max = 32, message = "支付宝 现金 微信长度不能超过32")
    @TableField(value = "charge_method_code", condition = LIKE)
    @Excel(name = "支付宝 现金 微信")
    private String chargeMethodCode;

    /**
     * 收费项金额
     */
    @ApiModelProperty(value = "收费项金额")
    @NotNull(message = "收费项金额不能为空")
    @TableField("charge_item_money")
    @Excel(name = "收费项金额")
    private BigDecimal chargeItemMoney;

    /**
     * 数目
     */
    @ApiModelProperty(value = "数目")
    @NotNull(message = "数目不能为空")
    @TableField("amount")
    @Excel(name = "数目")
    private Integer amount;

    /**
     * 统计的是哪一天的数据
     */
    @ApiModelProperty(value = "统计的是哪一天的数据")
    @NotNull(message = "统计的是哪一天的数据不能为空")
    @TableField("sts_day")
    @Excel(name = "统计的是哪一天的数据", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime stsDay;


    @Builder
    public FeiDay(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime, 
                    String tCode, String companyCode, Long orgId, Long businessHallId, Long createUserId, 
                    Integer totalNumber, Integer type, Integer online, String chargeItemSourceCode, String chargeMethodCode,
                    BigDecimal chargeItemMoney, Integer amount, LocalDateTime stsDay) {
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
        this.totalNumber = totalNumber;
        this.type = type;
        this.online = online;
        this.chargeItemSourceCode = chargeItemSourceCode;
        this.chargeMethodCode = chargeMethodCode;
        this.chargeItemMoney = chargeItemMoney;
        this.amount = amount;
        this.stsDay = stsDay;
    }

}
