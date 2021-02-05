package com.cdqckj.gmis.invoice.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

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
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "BuyerTaxpayerInfoPageDTO", description = "购买方税务相关信息")
public class BuyerTaxpayerInfoPageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 公司编码
     */
    @ApiModelProperty(value = "公司编码")
    @Length(max = 32, message = "公司编码长度不能超过32")
    private String companyCode;
    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    @Length(max = 100, message = "公司名称长度不能超过100")
    private String companyName;
    /**
     * 组织ID
     */
    @ApiModelProperty(value = "组织ID")
    private Long orgId;
    /**
     * 组织名称
     */
    @ApiModelProperty(value = "组织名称")
    @Length(max = 100, message = "组织名称长度不能超过100")
    private String orgName;
    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    @Length(max = 32, message = "客户编号长度不能超过32")
    private String customerCode;
    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    @Length(max = 100, message = "客户名称长度不能超过100")
    private String customerName;
    /**
     * 购买方名称
     */
    @ApiModelProperty(value = "购买方名称")
    @Length(max = 100, message = "购买方名称长度不能超过100")
    private String buyerName;
    /**
     * 购买方纳税人识别号
     */
    @ApiModelProperty(value = "购买方纳税人识别号")
    @Length(max = 20, message = "购买方纳税人识别号长度不能超过20")
    private String buyerTinNo;
    /**
     * 购买方地址
     */
    @ApiModelProperty(value = "购买方地址")
    @Length(max = 100, message = "购买方地址长度不能超过100")
    private String buyerAddress;
    /**
     * 购买方电话
     */
    @ApiModelProperty(value = "购买方电话")
    @Length(max = 100, message = "购买方电话长度不能超过100")
    private String buyerPhone;
    /**
     * 购买方开户行
     */
    @ApiModelProperty(value = "购买方开户行")
    @Length(max = 100, message = "购买方开户行长度不能超过100")
    private String buyerBankName;
    /**
     * 购买方账户
     */
    @ApiModelProperty(value = "购买方账户")
    @Length(max = 100, message = "购买方账户长度不能超过100")
    private String buyerBankAccount;
    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Integer dataStatus;

}
