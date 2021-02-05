package com.cdqckj.gmis.pay.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.math.BigDecimal;
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

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

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
@ApiModel(value = "WxPaySaveDTO", description = "")
public class WxPaySaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 回调地址
     */
    @ApiModelProperty(value = "回调地址")
    private String notifyUrl;

    /**
     * 交易类型
     */
    @ApiModelProperty(value = "交易类型")
    private String tradeType;
    /**
     * 订单号
     */
    @ApiModelProperty(value = "订单号")
    @Length(max = 32, message = "订单号")
    private String orderNumber;
    /**
     * 产品id
     */
    @ApiModelProperty(value = "产品id")
    @Length(max = 32, message = "产品id")
    private String productId;
    /**
     * 订单标题
     */
    @ApiModelProperty(value = "订单标题")
    @Length(max = 50, message = "订单标题")
    private String orderTitle;
    /**
     * 商品描述
     */
    @ApiModelProperty(value = "商品描述")
    @Length(max = 50, message = "商品描述")
    private String body;
    /**
     * 商品详情
     */
    @ApiModelProperty(value = "商品详情")
    @Length(max = 50, message = "商品详情")
    private String detail;

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
    private BigDecimal pay;
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
     *
     */
    @ApiModelProperty(value = "用户付款码")
    private String authCode;
    /**
     * 通知内容
     */
    @ApiModelProperty(value = "通知内容")
    @Length(max = 500, message = "通知内容长度不能超过500")
    private String notifyConten;

    @ApiModelProperty(value = "名称")
    @Length(max = 255, message = "名称长度不能超过255")
    protected String label;

    @ApiModelProperty(value = "父ID")
    protected String parentId;

    @ApiModelProperty(value = "排序号")
    protected Integer sortValue;

    /**
     * 是否沙箱环境
     */
    @ApiModelProperty(value = "是否沙箱环境")
    private Integer isSandbox;

    /**
     * 交易起始时间
     */
    @ApiModelProperty(value = "交易起始时间")
    private String timeStart;

    /**
     * 交易结束时间
     */
    @ApiModelProperty(value = "交易结束时间")
    private String timeExpire;
}
