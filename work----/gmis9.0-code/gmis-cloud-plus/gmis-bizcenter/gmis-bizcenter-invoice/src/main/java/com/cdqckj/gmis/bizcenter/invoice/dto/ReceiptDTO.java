package com.cdqckj.gmis.bizcenter.invoice.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 实体类
 *
 * </p>
 *
 * @author gmis
 * @since 2020-10-14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "ReceiptDTO", description = "根据票据生成发票参数上传DTO")
public class ReceiptDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "票据ID")
    @NotNull(message = "不能为空")
    private Long id;

    /**
     * 票据编号
     */
    @ApiModelProperty(value = "票据编号")
    @Length(max = 32, message = "票据编号长度不能超过32")
    private String receiptNo;

    @ApiModelProperty(value = "发票类型")
    @Length(max = 30, message = "发票类型长度不能超过30")
    private String invoiceType;

    /**
     * 发票编号
     */
    @ApiModelProperty(value = "发票编号")
    @Length(max = 30, message = "发票编号长度不能超过30")
    private String invoiceNumber;

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
    @Length(max = 30, message = "购买方纳税人识别号长度不能超过30")
    private String buyerTinNo;
    /**
     * 购买方地址
     */
    @ApiModelProperty(value = "购买方地址")
    @Length(max = 100, message = "购买方地址长度不能超过100")
    private String buyerAddress;
    /**
     * 购买方联系电话
     */
    @ApiModelProperty(value = "购买方联系电话")
    @Length(max = 100, message = "购买方联系电话长度不能超过100")
    private String buyerPhone;
    /**
     * 购买方开户行名称
     */
    @ApiModelProperty(value = "购买方开户行名称")
    @Length(max = 100, message = "购买方开户行名称长度不能超过100")
    private String buyerBankName;
    /**
     * 购买方开户行账号
     */
    @ApiModelProperty(value = "购买方开户行账号")
    @Length(max = 100, message = "购买方开户行账号长度不能超过100")
    private String buyerBankAccount;


}
