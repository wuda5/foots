package com.cdqckj.gmis.invoice.dto;

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
 * @author gmis
 * @since 2020-10-15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "SellerTaxpayerInfoUpdateDTO", description = "")
public class SellerTaxpayerInfoUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @NotNull(message = "id不能为空", groups = SuperEntity.Update.class)
    private Long id;

    /**
     * 公司CODE
     */
    @ApiModelProperty(value = "公司CODE")
    @Length(max = 32, message = "公司CODE长度不能超过32")
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
     * 销售方名称
     */
    @ApiModelProperty(value = "销售方名称")
    @Length(max = 100, message = "销售方名称长度不能超过100")
    private String sellerName;
    /**
     * 销售方纳税人识别号
     */
    @ApiModelProperty(value = "销售方纳税人识别号")
    @Length(max = 20, message = "销售方纳税人识别号长度不能超过20")
    private String sellerTinNo;
    /**
     * 销售方地址、电话
     */
    @ApiModelProperty(value = "销售方地址、电话")
    @Length(max = 100, message = "销售方地址、电话长度不能超过100")
    private String sellerAddress;
    /**
     * 售卖方电话
     */
    @ApiModelProperty(value = "售卖方电话")
    @Length(max = 100, message = "售卖方电话长度不能超过100")
    private String sellerPhone;
    /**
     * 售卖方开户行名称
     */
    @ApiModelProperty(value = "售卖方开户行名称")
    @Length(max = 100, message = "售卖方开户行名称长度不能超过100")
    private String sellerBankName;
    /**
     * 销售方开户行及账户
     */
    @ApiModelProperty(value = "销售方开户行及账户")
    @Length(max = 100, message = "销售方开户行及账户长度不能超过100")
    private String sellerBankAccount;
    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Integer dataStatus;
}
