package com.cdqckj.gmis.invoice.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
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
 * @author gmis
 * @since 2020-10-15
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("gt_seller_taxpayer_info")
@ApiModel(value = "SellerTaxpayerInfo", description = "")
@AllArgsConstructor
public class SellerTaxpayerInfo extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 公司CODE
     */
    @ApiModelProperty(value = "公司CODE")
    @Length(max = 32, message = "公司CODE长度不能超过32")
    @TableField(value = "company_code", condition = LIKE)
    @Excel(name = "公司CODE")
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
     * 销售方名称
     */
    @ApiModelProperty(value = "销售方名称")
    @Length(max = 100, message = "销售方名称长度不能超过100")
    @TableField(value = "seller_name", condition = LIKE)
    @Excel(name = "销售方名称")
    private String sellerName;

    /**
     * 销售方纳税人识别号
     */
    @ApiModelProperty(value = "销售方纳税人识别号")
    @Length(max = 20, message = "销售方纳税人识别号长度不能超过20")
    @TableField(value = "seller_tin_no", condition = LIKE)
    @Excel(name = "销售方纳税人识别号")
    private String sellerTinNo;

    /**
     * 销售方地址、电话
     */
    @ApiModelProperty(value = "销售方地址、电话")
    @Length(max = 100, message = "销售方地址、电话长度不能超过100")
    @TableField(value = "seller_address", condition = LIKE)
    @Excel(name = "销售方地址、电话")
    private String sellerAddress;

    /**
     * 售卖方电话
     */
    @ApiModelProperty(value = "售卖方电话")
    @Length(max = 100, message = "售卖方电话长度不能超过100")
    @TableField(value = "seller_phone", condition = LIKE)
    @Excel(name = "售卖方电话")
    private String sellerPhone;

    /**
     * 售卖方开户行名称
     */
    @ApiModelProperty(value = "售卖方开户行名称")
    @Length(max = 100, message = "售卖方开户行名称长度不能超过100")
    @TableField(value = "seller_bank_name", condition = LIKE)
    @Excel(name = "售卖方开户行名称")
    private String sellerBankName;

    /**
     * 销售方开户行及账户
     */
    @ApiModelProperty(value = "销售方开户行及账户")
    @Length(max = 100, message = "销售方开户行及账户长度不能超过100")
    @TableField(value = "seller_bank_account", condition = LIKE)
    @Excel(name = "销售方开户行及账户")
    private String sellerBankAccount;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    @TableField("data_status")
    @Excel(name = "状态")
    private Integer dataStatus;


    @Builder
    public SellerTaxpayerInfo(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime, 
                    String companyCode, String companyName, Long orgId, String orgName, String sellerName, 
                    String sellerTinNo, String sellerAddress, String sellerPhone, String sellerBankName, String sellerBankAccount, Integer dataStatus) {
        this.id = id;
        this.createUser = createUser;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.orgId = orgId;
        this.orgName = orgName;
        this.sellerName = sellerName;
        this.sellerTinNo = sellerTinNo;
        this.sellerAddress = sellerAddress;
        this.sellerPhone = sellerPhone;
        this.sellerBankName = sellerBankName;
        this.sellerBankAccount = sellerBankAccount;
        this.dataStatus = dataStatus;
    }

}
