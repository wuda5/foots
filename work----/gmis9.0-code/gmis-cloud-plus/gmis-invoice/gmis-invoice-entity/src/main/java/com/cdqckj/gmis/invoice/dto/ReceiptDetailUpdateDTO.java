package com.cdqckj.gmis.invoice.dto;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cdqckj.gmis.base.entity.Entity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import com.cdqckj.gmis.base.entity.SuperEntity;
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
 * @author songyz
 * @since 2020-09-08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "ReceiptDetailUpdateDTO", description = "")
public class ReceiptDetailUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @NotNull(message = "id不能为空", groups = SuperEntity.Update.class)
    private Long id;

    /**
     * 票据ID
     */
    @ApiModelProperty(value = "票据ID")
    @Length(max = 32, message = "票据ID长度不能超过32")
    private String receiptId;
    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    @Length(max = 32, message = "商品名称长度不能超过32")
    private String goodsId;
    /**
     * 商品代码
     *             0充值
     *             1燃气收费
     *             2商品销售
     *             3场景收费
     *             4附加费
     *             5保险费
     *             
     */
    @ApiModelProperty(value = "商品代码")
    @Length(max = 100, message = "商品代码长度不能超过100")
    private String goodsName;
    /**
     * 规格型号
     */
    @ApiModelProperty(value = "规格型号")
    @Length(max = 20, message = "规格型号长度不能超过20")
    private String specificationModel;
    /**
     * 单位
     */
    @ApiModelProperty(value = "单位")
    @Length(max = 20, message = "单位长度不能超过20")
    private String unit;
    /**
     * 数量
     */
    @ApiModelProperty(value = "数量")
    @Length(max = 32, message = "数量长度不能超过32")
    private String number;
    /**
     * 单价
     */
    @ApiModelProperty(value = "单价")
    private BigDecimal price;
    /**
     * 金额
     */
    @ApiModelProperty(value = "金额")
    private BigDecimal money;
    /**
     * 税率
     */
    @ApiModelProperty(value = "税率")
    private BigDecimal taxRate;
    /**
     * 税额
     */
    @ApiModelProperty(value = "税额")
    private BigDecimal taxMoney;
}
