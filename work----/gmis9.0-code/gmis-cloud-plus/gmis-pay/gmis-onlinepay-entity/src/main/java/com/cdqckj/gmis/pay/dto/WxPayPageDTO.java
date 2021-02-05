package com.cdqckj.gmis.pay.dto;

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
 * 
 * </p>
 *
 * @author gmis
 * @since 2020-06-04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "WxPayPageDTO", description = "")
public class WxPayPageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 账单日期
     */
    private String billDate;

    /**
     * 支付备注
     */
    @ApiModelProperty(value = "支付备注")
    @Length(max = 50, message = "支付备注长度不能超过50")
    private String payNotes;
    /**
     * 支付金额
     */
    @ApiModelProperty(value = "支付金额")
    private Double pay;
    /**
     * 支付人唯一标识
     */
    @ApiModelProperty(value = "支付人唯一标识")
    @Length(max = 50, message = "支付人唯一标识长度不能超过50")
    private String payOpenid;
    /**
     * 支付状态（1：已支付，0：待支付）
     */
    @ApiModelProperty(value = "支付状态（1：已支付，0：待支付）")
    private Integer payState;
    /**
     * 支付时间
     */
    @ApiModelProperty(value = "支付时间")
    private LocalDateTime payTime;
    /**
     * 通知(1:已通知,0:未通知)
     */
    @ApiModelProperty(value = "通知(1:已通知,0:未通知)")
    private Integer notify;
    /**
     * 通知内容
     */
    @ApiModelProperty(value = "通知内容")
    @Length(max = 500, message = "通知内容长度不能超过500")
    private String notifyConten;
    /**
     * 用户付款码
     */
    @Length(max = 128, message = "用户付款码长度不能超过500")
    @ApiModelProperty(value = "用户付款码")
    private String authCode;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
}
