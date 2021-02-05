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
 * @since 2020-09-08
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("gt_receipt_detail")
@ApiModel(value = "ReceiptDetail", description = "")
@AllArgsConstructor
public class ReceiptDetail extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 票据ID
     */
    @ApiModelProperty(value = "票据ID")
    @Length(max = 32, message = "票据ID长度不能超过32")
    @TableField(value = "receipt_id", condition = LIKE)
    @Excel(name = "票据ID")
    private String receiptId;

    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    @Length(max = 32, message = "商品名称长度不能超过32")
    @TableField(value = "goods_id", condition = LIKE)
    @Excel(name = "商品名称")
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
    @TableField(value = "goods_name", condition = LIKE)
    @Excel(name = "商品代码")
    private String goodsName;

    /**
     * 规格型号
     */
    @ApiModelProperty(value = "规格型号")
    @Length(max = 20, message = "规格型号长度不能超过20")
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
    public ReceiptDetail(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime, 
                    String receiptId, String goodsId, String goodsName, String specificationModel, String unit, 
                    String number, BigDecimal price, BigDecimal money, BigDecimal taxRate, BigDecimal taxMoney) {
        this.id = id;
        this.createUser = createUser;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.receiptId = receiptId;
        this.goodsId = goodsId;
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
