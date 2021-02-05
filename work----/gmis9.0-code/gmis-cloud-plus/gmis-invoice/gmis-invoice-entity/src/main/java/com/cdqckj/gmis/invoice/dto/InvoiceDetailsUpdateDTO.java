package com.cdqckj.gmis.invoice.dto;

import com.cdqckj.gmis.base.entity.SuperEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 实体类
 *
 * </p>
 *
 * @author gmis
 * @since 2020-12-07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "InvoiceDetailsUpdateDTO", description = "")
public class InvoiceDetailsUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @NotNull(message = "id不能为空", groups = SuperEntity.Update.class)
    private Long id;

    /**
     * 发票ID
     */
    @ApiModelProperty(value = "发票ID")
    @Length(max = 32, message = "发票ID长度不能超过32")
    private String invoiceId;
    /**
     * 发票行性质0正常行、1折扣行、2被折扣行
     */
    @ApiModelProperty(value = "发票行性质0正常行、1折扣行、2被折扣行")
    @Length(max = 1, message = "发票行性质0正常行、1折扣行、2被折扣行长度不能超过1")
    private String type;
    /**
     * 自行编码(一般不建议使用自行编码)
     */
    @ApiModelProperty(value = "自行编码(一般不建议使用自行编码)")
    @Length(max = 50, message = "自行编码(一般不建议使用自行编码)长度不能超过50")
    private String code;
    /**
     * 优惠政策标识。0：不使用；1：使用
     */
    @ApiModelProperty(value = "优惠政策标识。0：不使用；1：使用")
    @Length(max = 1, message = "优惠政策标识。0：不使用；1：使用长度不能超过1")
    private String preferentialPolicyFlg;
    /**
     * 增值税特殊管理（当优惠政策标识为1时必填）。
     */
    @ApiModelProperty(value = "增值税特殊管理（当优惠政策标识为1时必填）。")
    @Length(max = 50, message = "增值税特殊管理（当优惠政策标识为1时必填）。长度不能超过50")
    private String addedValueTaxFlg;
    /**
     * 零税率标识。1:免税,2:不征税,3:普通零税率。税率为零的情况下，如果不传，则默认为1:免税。
     */
    @ApiModelProperty(value = "零税率标识。1:免税,2:不征税,3:普通零税率。税率为零的情况下，如果不传，则默认为1:免税。")
    @Length(max = 1, message = "零税率标识。1:免税,2:不征税,3:普通零税率。税率为零的情况下，如果不传，则默认为1:免税。长度不能超过1")
    private String zeroTaxRateFlg;
    /**
     * 商品代码
     */
    @ApiModelProperty(value = "商品代码")
    @Length(max = 32, message = "商品代码长度不能超过32")
    private String goodsCode;
    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    @Length(max = 100, message = "商品名称长度不能超过100")
    private String goodsName;
    /**
     * 规格型号
     */
    @ApiModelProperty(value = "规格型号")
    @Length(max = 32, message = "规格型号长度不能超过32")
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
