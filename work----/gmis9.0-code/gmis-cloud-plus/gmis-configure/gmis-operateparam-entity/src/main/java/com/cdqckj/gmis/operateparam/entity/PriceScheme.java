package com.cdqckj.gmis.operateparam.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cdqckj.gmis.base.entity.Entity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.cdqckj.gmis.scale.annotation.FieldNoScale;
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
 * 
 * </p>
 *
 * @author gmis
 * @since 2020-07-03
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("pz_price_scheme")
@ApiModel(value = "PriceScheme", description = "")
@AllArgsConstructor
public class PriceScheme extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 公司id
     */
    @ApiModelProperty(value = "公司id")
    @Length(max = 32, message = "公司id长度不能超过32")
    @TableField(value = "company_code", condition = LIKE)
    @Excel(name = "公司id")
    private String companyCode;

    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    @Length(max = 100, message = "公司名称长度不能超过100")
    @TableField(value = "company_name", condition = LIKE)
    @Excel(name = "公司名称")
    private String companyName;

    /**
     * 组织id
     */
    @ApiModelProperty(value = "组织id")
    @TableField("org_id")
    @Excel(name = "组织id")
    private Long orgId;

    /**
     * 用气类型ID
     */
    @ApiModelProperty(value = "用气类型ID")
    @TableField(value = "use_gas_type_id", condition = LIKE)
    @Excel(name = "用气类型ID")
    private Long useGasTypeId;

    /**
     * 补差价格
     */
    @ApiModelProperty(value = "补差价格")
    @TableField("compensation_price")
    @Excel(name = "补差价格")
    private BigDecimal compensationPrice;

    /**
     * 一阶气量
     */
    @ApiModelProperty(value = "一阶气量")
    @TableField("gas_1")
    @Excel(name = "一阶气量")
    private BigDecimal gas1;

    /**
     * 一阶价格
     */
    @ApiModelProperty(value = "一阶价格")
    @TableField("price_1")
    @Excel(name = "一阶价格")
    @FieldNoScale
    private BigDecimal price1;

    /**
     * 二阶气量
     */
    @ApiModelProperty(value = "二阶气量")
    @TableField("gas_2")
    @Excel(name = "二阶气量")
    private BigDecimal gas2;

    /**
     * 二阶价格
     */
    @ApiModelProperty(value = "二阶价格")
    @TableField("price_2")
    @Excel(name = "二阶价格")
    @FieldNoScale
    private BigDecimal price2;

    /**
     * 三阶气量
     */
    @ApiModelProperty(value = "三阶气量")
    @TableField("gas_3")
    @Excel(name = "三阶气量")
    private BigDecimal gas3;

    /**
     * 三阶价格
     */
    @ApiModelProperty(value = "三阶价格")
    @TableField("price_3")
    @Excel(name = "三阶价格")
    @FieldNoScale
    private BigDecimal price3;

    /**
     * 四阶气量
     */
    @ApiModelProperty(value = "四阶气量")
    @TableField("gas_4")
    @Excel(name = "四阶气量")
    private BigDecimal gas4;

    /**
     * 四阶价格
     */
    @ApiModelProperty(value = "四阶价格")
    @TableField("price_4")
    @Excel(name = "四阶价格")
    @FieldNoScale
    private BigDecimal price4;

    /**
     * 五阶气量
     */
    @ApiModelProperty(value = "五阶气量id")
    @TableField("gas_5")
    @Excel(name = "五阶气量id")
    private BigDecimal gas5;

    /**
     * 五阶价格
     */
    @ApiModelProperty(value = "五阶价格")
    @TableField("price_5")
    @Excel(name = "五阶价格")
    @FieldNoScale
    private BigDecimal price5;

    /**
     * 六阶气量
     */
    @ApiModelProperty(value = "六阶气量")
    @TableField("gas_6")
    @Excel(name = "六阶气量")
    private BigDecimal gas6;

    /**
     * 六阶价格
     */
    @ApiModelProperty(value = "六阶价格")
    @TableField("price_6")
    @Excel(name = "六阶价格")
    @FieldNoScale
    private BigDecimal price6;

    /**
     * 七阶气量
     */
    @ApiModelProperty(value = "七阶气量")
    @TableField("gas_7")
    @Excel(name = "七阶气量")
    private BigDecimal gas7;

    /**
     * 七阶价格
     */
    @ApiModelProperty(value = "七阶价格")
    @TableField("price_7")
    @Excel(name = "七阶价格")
    @FieldNoScale
    private BigDecimal price7;

    /**
     * 固定价格
     */
    @ApiModelProperty(value = "固定价格")
    @TableField("fixed_price")
    @Excel(name = "固定价格")
    @FieldNoScale
    private BigDecimal fixedPrice;


    /**
     * 启用时间
     */
    @ApiModelProperty(value = "启用时间")
    @TableField("start_time")
    @Excel(name = "启用时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDate startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间")
    @TableField("end_time")
    @Excel(name = "结束时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDate endTime;

    /**
     * 切换时间
     */
    @ApiModelProperty(value = "切换时间")
    @TableField("cycle_start_time")
    @Excel(name = "切换时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDate cycleStartTime;

    /**
     * 起始月
     */
    @ApiModelProperty(value = "起始月")
    @TableField("start_month")
    @Excel(name = "起始月")
    private Integer startMonth;

    /**
     * 结算日
     */
    @ApiModelProperty(value = "结算日")
    @TableField("settlement_day")
    @Excel(name = "结算日")
    private Integer settlementDay;

    /**
     * 周期
     */
    @ApiModelProperty(value = "周期")
    @TableField("cycle")
    @Excel(name = "周期")
    private Integer cycle;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    @TableField("data_status")
    @Excel(name = "状态")
    private Integer dataStatus;

    /**
     * 调价是否清零
     */
    @ApiModelProperty(value = "调价是否清零（1-清零，0-不清零）")
    @TableField("price_change_is_clear")
    @Excel(name = "调价是否清零")
    private Integer priceChangeIsClear;
    /**
     * 是否采暖
     */
    @ApiModelProperty(value = "是否采暖（1-采暖，0-非采暖）")
    @TableField("is_heating")
    @Excel(name = "是否采暖")
    private Integer isHeating;

    @ApiModelProperty(value = "周期开始时间")
    @TableField("cycle_enable_date")
    @Excel(name = "周期开始时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDate cycleEnableDate;

    /**
     * 价格方案号
     */
    @ApiModelProperty(value = "价格方案号")
    @TableField("price_num")
    @Excel(name = "价格方案号")
    private Integer priceNum;


    @Builder
    public PriceScheme(Long id, LocalDateTime createTime, Long createUser, LocalDateTime updateTime, Long updateUser, 
                    String companyCode, String companyName, Long orgId, Long useGasTypeId, BigDecimal compensationPrice,
                       BigDecimal gas1, BigDecimal price1, BigDecimal gas2, BigDecimal price2, BigDecimal gas3, BigDecimal price3,
                       BigDecimal gas4, BigDecimal price4, BigDecimal gas5, BigDecimal price5, BigDecimal gas6, BigDecimal price6,
                       BigDecimal gas7, BigDecimal price7, LocalDate startTime, LocalDate endTime, LocalDate cycleStartTime, Integer startMonth,
                    Integer settlementDay, Integer cycle, Integer dataStatus, Integer priceChangeIsClear,BigDecimal fixedPrice,Integer isHeating,
                       LocalDate cycleEnableDate,Integer priceNum) {
        this.id = id;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.orgId = orgId;
        this.useGasTypeId = useGasTypeId;
        this.compensationPrice = compensationPrice;
        this.gas1 = gas1;
        this.price1 = price1;
        this.gas2 = gas2;
        this.price2 = price2;
        this.gas3 = gas3;
        this.price3 = price3;
        this.gas4 = gas4;
        this.price4 = price4;
        this.gas5 = gas5;
        this.price5 = price5;
        this.gas6 = gas6;
        this.price6 = price6;
        this.gas7 = gas7;
        this.price7 = price7;
        this.fixedPrice = fixedPrice;
        this.startTime = startTime;
        this.endTime = endTime;
        this.cycleStartTime = cycleStartTime;
        this.startMonth = startMonth;
        this.settlementDay = settlementDay;
        this.cycle = cycle;
        this.dataStatus = dataStatus;
        this.priceChangeIsClear = priceChangeIsClear;
        this.isHeating = isHeating;
        this.cycleEnableDate = cycleEnableDate;
        this.priceNum = priceNum;
    }

}
