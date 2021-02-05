package com.cdqckj.gmis.operateparam.dto;

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
 * 优惠活动表
 * </p>
 *
 * @author gmis
 * @since 2020-08-27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "PreferentialPageDTO", description = "优惠活动表")
public class PreferentialPageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用气类_ID
     */
    @ApiModelProperty(value = "用气类_ID")
    @Length(max = 1000, message = "用气类_ID长度不能超过1000")
    private String pzId;
    /**
     * 用气类型code
     */
    @ApiModelProperty(value = "用气类型code")
    @Length(max = 32, message = "用气类型code长度不能超过32")
    private String useGasTypeCode;
    /**
     * 用气类型名称
     */
    @ApiModelProperty(value = "用气类型名称")
    @Length(max = 50, message = "用气类型名称长度不能超过50")
    private String useGasTypeName;
    /**
     * 优惠活动名称
     */
    @ApiModelProperty(value = "优惠活动名称")
    @Length(max = 100, message = "优惠活动名称长度不能超过100")
    private String preferentialName;
    /**
     * 活动有效期
     */
    @ApiModelProperty(value = "活动有效期")
    private Integer preferentialActivate;
    /**
     * 持续优惠
     */
    @ApiModelProperty(value = "持续优惠")
    private Integer isAlwaysPreferential;
    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间")
    private LocalDateTime startTime;
    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间")
    private LocalDateTime endTime;
    /**
     * 是否启用
     */
    @ApiModelProperty(value = "是否启用")
    private Integer dataStatus;
    /**
     * 优惠气量
     */
    @ApiModelProperty(value = "优惠气量")
    private BigDecimal preferentialGas;
    /**
     * 优惠方式
     */
    @ApiModelProperty(value = "优惠方式")
    private Integer preferentialType;
    /**
     * 优惠金额
     */
    @ApiModelProperty(value = "优惠金额")
    private BigDecimal preferentialMoney;
    /**
     * 优惠比例
     */
    @ApiModelProperty(value = "优惠比例")
    private BigDecimal preferentialPercent;
    /**
     * 删除状态
     */
    @ApiModelProperty(value = "删除状态")
    private Integer deleteStatus;

}
