package com.cdqckj.gmis.systemparam.dto;

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
 * 开票参数配置
 * </p>
 *
 * @author gmis
 * @since 2020-07-07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "InvoiceParamUpdateDTO", description = "开票参数配置")
public class InvoiceParamUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @NotNull(message = "id不能为空", groups = SuperEntity.Update.class)
    private Long id;

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
     * 组织id
     */
    @ApiModelProperty(value = "组织id")
    private Long orgId;
    /**
     * 组织名称
     */
    @ApiModelProperty(value = "组织名称")
    @Length(max = 100, message = "组织名称长度不能超过100")
    private String orgName;
    /**
     * 账号等级（0-总公司，1-分公司）
     */
    @ApiModelProperty(value = "账号等级（0-总公司，1-分公司）")
    private Integer accountLevel;
    /**
     * 销方名称
     */
    @ApiModelProperty(value = "销方名称")
    @Length(max = 100, message = "销方名称长度不能超过100")
    private String name;
    /**
     * 销方纳税人编号
     */
    @ApiModelProperty(value = "销方纳税人编号")
    @Length(max = 100, message = "销方纳税人编号长度不能超过100")
    private String taxpayerNo;
    /**
     * 销方地址
     */
    @ApiModelProperty(value = "销方地址")
    @Length(max = 300, message = "销方地址长度不能超过300")
    private String address;
    /**
     * 销方电话
     */
    @ApiModelProperty(value = "销方电话")
    @Length(max = 20, message = "销方电话长度不能超过20")
    private String telephone;
    /**
     * 销方开户行名称
     */
    @ApiModelProperty(value = "销方开户行名称")
    @Length(max = 100, message = "销方开户行名称长度不能超过100")
    private String bankName;
    /**
     * 销方银行账户
     */
    @ApiModelProperty(value = "销方银行账户")
    @Length(max = 60, message = "销方银行账户长度不能超过60")
    private String bankAccount;
    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Integer invoiceStatus;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @Length(max = 300, message = "备注长度不能超过300")
    private String remark;

    /**
     * 删除标识1：存在 0：删除
     */
    @ApiModelProperty(value = "删除标识1：存在 0：删除")
    private Integer deleteStatus;
}
