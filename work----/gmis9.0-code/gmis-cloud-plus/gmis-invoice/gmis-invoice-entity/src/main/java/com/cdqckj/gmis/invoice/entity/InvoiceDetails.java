package com.cdqckj.gmis.invoice.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
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
 * @author songyz
 * @since 2020-09-07
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("gt_invoice_details")
@ApiModel(value = "InvoiceDetails", description = "")
@AllArgsConstructor
public class InvoiceDetails extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 发票ID
     */
    @ApiModelProperty(value = "发票ID")
    @Length(max = 32, message = "发票ID长度不能超过32")
    @TableField(value = "invoice_id", condition = LIKE)
    @Excel(name = "发票ID")
    private String invoiceId;

    /**
     * 发票行性质0正常行、1折扣行、2被折扣行
     */
    @ApiModelProperty(value = "发票行性质0正常行、1折扣行、2被折扣行")
    @Length(max = 1, message = "发票行性质0正常行、1折扣行、2被折扣行长度不能超过1")
    @TableField(value = "type", condition = LIKE)
    @Excel(name = "发票行性质0正常行、1折扣行、2被折扣行")
    private String type;

    /**
     * 自行编码(一般不建议使用自行编码)
     */
    @ApiModelProperty(value = "自行编码(一般不建议使用自行编码)")
    @Length(max = 50, message = "自行编码(一般不建议使用自行编码)长度不能超过50")
    @TableField(value = "code", condition = LIKE)
    @Excel(name = "自行编码(一般不建议使用自行编码)")
    private String code;

    /**
     * 优惠政策标识。0：不使用；1：使用
     */
    @ApiModelProperty(value = "优惠政策标识。0：不使用；1：使用")
    @Length(max = 1, message = "优惠政策标识。0：不使用；1：使用长度不能超过1")
    @TableField(value = "preferentialPolicyFlg", condition = LIKE)
    @Excel(name = "优惠政策标识。0：不使用；1：使用")
    private String preferentialPolicyFlg;

    /**
     * 增值税特殊管理（当优惠政策标识为1时必填）。
     */
    @ApiModelProperty(value = "增值税特殊管理（当优惠政策标识为1时必填）。")
    @Length(max = 50, message = "增值税特殊管理（当优惠政策标识为1时必填）。长度不能超过50")
    @TableField(value = "addedValueTaxFlg", condition = LIKE)
    @Excel(name = "增值税特殊管理（当优惠政策标识为1时必填）。")
    private String addedValueTaxFlg;

    /**
     * 零税率标识。1:免税,2:不征税,3:普通零税率。税率为零的情况下，如果不传，则默认为1:免税。
     */
    @ApiModelProperty(value = "零税率标识。1:免税,2:不征税,3:普通零税率。税率为零的情况下，如果不传，则默认为1:免税。")
    @Length(max = 1, message = "零税率标识。1:免税,2:不征税,3:普通零税率。税率为零的情况下，如果不传，则默认为1:免税。长度不能超过1")
    @TableField(value = "zeroTaxRateFlg", condition = LIKE)
    @Excel(name = "零税率标识。1:免税,2:不征税,3:普通零税率。税率为零的情况下，如果不传，则默认为1:免税。")
    private String zeroTaxRateFlg;

    /**
     * 商品代码
     */
    @ApiModelProperty(value = "商品代码")
    @Length(max = 32, message = "商品代码长度不能超过32")
    @TableField(value = "goods_code", condition = LIKE)
    @Excel(name = "商品代码")
    private String goodsCode;

    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    @Length(max = 100, message = "商品名称长度不能超过100")
    @TableField(value = "goods_name", condition = LIKE)
    @Excel(name = "商品名称")
    private String goodsName;

    /**
     * 规格型号
     */
    @ApiModelProperty(value = "规格型号")
    @Length(max = 32, message = "规格型号长度不能超过32")
    @TableField(value = "specification_model", condition = LIKE)
    @Excel(name = "规格型号")
    private String specificationModel;

    /**
     * 单位
     */
    @ApiModelProperty(value = "单位")
    @Length(max = 20, message = "单位长度不能超过20")
    @TableField(value = "unit", condition = LIKE)
    @Excel(name = "单位")
    private String unit;

    /**
     * 数量
     */
    @ApiModelProperty(value = "数量")
    @Length(max = 32, message = "数量长度不能超过32")
    @TableField(value = "number", condition = LIKE)
    @Excel(name = "数量")
    private String number;

    /**
     * 单价
     */
    @ApiModelProperty(value = "单价")
    @TableField("price")
    @Excel(name = "单价")
    private BigDecimal price;

    /**
     * 金额
     */
    @ApiModelProperty(value = "金额")
    @TableField("money")
    @Excel(name = "金额")
    private BigDecimal money;

    /**
     * 税率
     */
    @ApiModelProperty(value = "税率")
    @TableField("tax_rate")
    @Excel(name = "税率")
    private BigDecimal taxRate;

    /**
     * 税额
     */
    @ApiModelProperty(value = "税额")
    @TableField("tax_money")
    @Excel(name = "税额")
    private BigDecimal taxMoney;


    @Builder
    public InvoiceDetails(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime, 
                    String invoiceId, String type, String code, String preferentialPolicyFlg, String addedValueTaxFlg, 
                    String zeroTaxRateFlg, String goodsCode, String goodsName, String specificationModel, String unit, String number, 
                    BigDecimal price, BigDecimal money, BigDecimal taxRate, BigDecimal taxMoney) {
        this.id = id;
        this.createUser = createUser;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.invoiceId = invoiceId;
        this.type = type;
        this.code = code;
        this.preferentialPolicyFlg = preferentialPolicyFlg;
        this.addedValueTaxFlg = addedValueTaxFlg;
        this.zeroTaxRateFlg = zeroTaxRateFlg;
        this.goodsCode = goodsCode;
        this.goodsName = goodsName;
        this.specificationModel = specificationModel;
        this.unit = unit;
        this.number = number;
        this.price = price;
        this.money = money;
        this.taxRate = taxRate;
        this.taxMoney = taxMoney;
    }

}
