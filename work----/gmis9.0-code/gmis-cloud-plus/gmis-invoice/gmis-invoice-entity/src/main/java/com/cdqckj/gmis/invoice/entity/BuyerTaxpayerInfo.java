package com.cdqckj.gmis.invoice.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cdqckj.gmis.base.entity.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

/**
 * <p>
 * 实体类
 * 购买方税务相关信息
 * </p>
 *
 * @author houp
 * @since 2020-10-14
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("gt_buyer_taxpayer_info")
@ApiModel(value = "BuyerTaxpayerInfo", description = "购买方税务相关信息")
@AllArgsConstructor
public class BuyerTaxpayerInfo extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 公司编码
     */
    @ApiModelProperty(value = "公司编码")
    @Length(max = 32, message = "公司编码长度不能超过32")
    @TableField(value = "company_code", condition = LIKE)
    @Excel(name = "公司编码")
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
     * 组织ID
     */
    @ApiModelProperty(value = "组织ID")
    @TableField("org_id")
    @Excel(name = "组织ID")
    private Long orgId;

    /**
     * 组织名称
     */
    @ApiModelProperty(value = "组织名称")
    @Length(max = 100, message = "组织名称长度不能超过100")
    @TableField(value = "org_name", condition = LIKE)
    @Excel(name = "组织名称")
    private String orgName;

    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    @Length(max = 32, message = "客户编号长度不能超过32")
    @TableField(value = "customer_code", condition = LIKE)
    @Excel(name = "客户编号")
    private String customerCode;

    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    @Length(max = 100, message = "客户名称长度不能超过100")
    @TableField(value = "customer_name", condition = LIKE)
    @Excel(name = "客户名称")
    private String customerName;

    /**
     * 购买方名称
     */
    @ApiModelProperty(value = "购买方名称")
    @Length(max = 100, message = "购买方名称长度不能超过100")
    @TableField(value = "buyer_name", condition = LIKE)
    @Excel(name = "购买方名称")
    private String buyerName;

    /**
     * 购买方纳税人识别号
     */
    @ApiModelProperty(value = "购买方纳税人识别号")
    @Length(max = 20, message = "购买方纳税人识别号长度不能超过20")
    @TableField(value = "buyer_tin_no", condition = LIKE)
    @Excel(name = "购买方纳税人识别号")
    private String buyerTinNo;

    /**
     * 购买方地址
     */
    @ApiModelProperty(value = "购买方地址")
    @Length(max = 100, message = "购买方地址长度不能超过100")
    @TableField(value = "buyer_address", condition = LIKE)
    @Excel(name = "购买方地址")
    private String buyerAddress;

    /**
     * 购买方电话
     */
    @ApiModelProperty(value = "购买方电话")
    @Length(max = 100, message = "购买方电话长度不能超过100")
    @TableField(value = "buyer_phone", condition = LIKE)
    @Excel(name = "购买方电话")
    private String buyerPhone;

    /**
     * 购买方开户行
     */
    @ApiModelProperty(value = "购买方开户行")
    @Length(max = 100, message = "购买方开户行长度不能超过100")
    @TableField(value = "buyer_bank_name", condition = LIKE)
    @Excel(name = "购买方开户行")
    private String buyerBankName;

    /**
     * 购买方账户
     */
    @ApiModelProperty(value = "购买方账户")
    @Length(max = 100, message = "购买方账户长度不能超过100")
    @TableField(value = "buyer_bank_account", condition = LIKE)
    @Excel(name = "购买方账户")
    private String buyerBankAccount;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    @TableField("data_status")
    @Excel(name = "状态")
    private Integer dataStatus;


    @Builder
    public BuyerTaxpayerInfo(Long id, LocalDateTime createTime, Long createUser, LocalDateTime updateTime, Long updateUser,
                             String companyCode, String companyName, Long orgId, String orgName, String customerCode,
                             String customerName, String buyerName, String buyerTinNo, String buyerAddress, String buyerPhone, String buyerBankName,
                             String buyerBankAccount, Integer dataStatus) {
        this.id = id;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.orgId = orgId;
        this.orgName = orgName;
        this.customerCode = customerCode;
        this.customerName = customerName;
        this.buyerName = buyerName;
        this.buyerTinNo = buyerTinNo;
        this.buyerAddress = buyerAddress;
        this.buyerPhone = buyerPhone;
        this.buyerBankName = buyerBankName;
        this.buyerBankAccount = buyerBankAccount;
        this.dataStatus = dataStatus;
    }

}
