package com.cdqckj.gmis.operateparam.entity;

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
 * 优惠活动表
 * </p>
 *
 * @author gmis
 * @since 2020-08-27
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("pz_preferential")
@ApiModel(value = "Preferential", description = "优惠活动表")
@AllArgsConstructor
public class Preferential extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 用气类_ID
     */
    @ApiModelProperty(value = "用气类_ID")
    @Length(max = 1000, message = "用气类_ID长度不能超过1000")
    @TableField(value = "PZ__id", condition = LIKE)
    @Excel(name = "用气类_ID")
    private String pzId;

    /**
     * 用气类型code
     */
    @ApiModelProperty(value = "用气类型code")
    @Length(max = 32, message = "用气类型code长度不能超过32")
    @TableField(value = "use_gas_type_code", condition = LIKE)
    @Excel(name = "用气类型code")
    private String useGasTypeCode;

    /**
     * 用气类型名称
     */
    @ApiModelProperty(value = "用气类型名称")
    @Length(max = 50, message = "用气类型名称长度不能超过50")
    @TableField(value = "use_gas_type_name", condition = LIKE)
    @Excel(name = "用气类型名称")
    private String useGasTypeName;

    /**
     * 优惠活动名称
     */
    @ApiModelProperty(value = "优惠活动名称")
    @Length(max = 100, message = "优惠活动名称长度不能超过100")
    @TableField(value = "preferential_name", condition = LIKE)
    @Excel(name = "优惠活动名称")
    private String preferentialName;

    /**
     * 活动有效期
     */
    @ApiModelProperty(value = "活动有效期")
    @TableField("preferential_activate")
    @Excel(name = "活动有效期")
    private Integer preferentialActivate;

    /**
     * 持续优惠
     */
    @ApiModelProperty(value = "持续优惠")
    @TableField("is_always_preferential")
    @Excel(name = "持续优惠")
    private Integer isAlwaysPreferential;

    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间")
    @TableField("start_time")
    @Excel(name = "开始时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间")
    @TableField("end_time")
    @Excel(name = "结束时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime endTime;

    /**
     * 是否启用
     */
    @ApiModelProperty(value = "是否启用")
    @TableField("data_status")
    @Excel(name = "是否启用")
    private Integer dataStatus;

    /**
     * 优惠气量
     */
    @ApiModelProperty(value = "优惠气量")
    @TableField("preferential_gas")
    @Excel(name = "优惠气量")
    private BigDecimal preferentialGas;

    /**
     * 优惠方式
     */
    @ApiModelProperty(value = "优惠方式")
    @TableField("preferential_type")
    @Excel(name = "优惠方式")
    private Integer preferentialType;

    /**
     * 优惠金额
     */
    @ApiModelProperty(value = "优惠金额")
    @TableField("preferential_money")
    @Excel(name = "优惠金额")
    private BigDecimal preferentialMoney;

    /**
     * 优惠比例
     */
    @ApiModelProperty(value = "优惠比例")
    @TableField("preferential_percent")
    @Excel(name = "优惠比例")
    private BigDecimal preferentialPercent;

    /**
     * 删除状态
     */
    @ApiModelProperty(value = "删除状态")
    @TableField("delete_status")
    @Excel(name = "删除状态")
    private Integer deleteStatus;


    @Builder
    public Preferential(Long id, LocalDateTime createTime, Long createUser, LocalDateTime updateTime, Long updateUser, 
                    String pzId, String useGasTypeCode, String useGasTypeName, String preferentialName, Integer preferentialActivate, 
                    Integer isAlwaysPreferential, LocalDateTime startTime, LocalDateTime endTime, Integer dataStatus, BigDecimal preferentialGas, Integer preferentialType, 
                    BigDecimal preferentialMoney, BigDecimal preferentialPercent, Integer deleteStatus) {
        this.id = id;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.pzId = pzId;
        this.useGasTypeCode = useGasTypeCode;
        this.useGasTypeName = useGasTypeName;
        this.preferentialName = preferentialName;
        this.preferentialActivate = preferentialActivate;
        this.isAlwaysPreferential = isAlwaysPreferential;
        this.startTime = startTime;
        this.endTime = endTime;
        this.dataStatus = dataStatus;
        this.preferentialGas = preferentialGas;
        this.preferentialType = preferentialType;
        this.preferentialMoney = preferentialMoney;
        this.preferentialPercent = preferentialPercent;
        this.deleteStatus = deleteStatus;
    }

}
