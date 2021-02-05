package com.cdqckj.gmis.biztemporary.dto;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
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
 * @since 2021-01-22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "CustomerTempUpdateDTO", description = "")
public class CustomerTempUpdateDTO implements Serializable {

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
    @Length(max = 30, message = "客户编号长度不能超过30")
    private String customerCode;
    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    @Length(max = 100, message = "客户名称长度不能超过100")
    private String customerName;
    /**
     * 客户类型ID
     */
    @ApiModelProperty(value = "客户类型ID")
    @Length(max = 32, message = "客户类型ID长度不能超过32")
    private String customerTypeCode;
    /**
     * 居民 商福 工业 低保
     */
    @ApiModelProperty(value = "居民 商福 工业 低保")
    @Length(max = 100, message = "居民 商福 工业 低保长度不能超过100")
    private String customerTypeName;
    /**
     * 性别  MAN表示男 ，WOMEN表示女
     */
    @ApiModelProperty(value = "性别  MAN表示男 ，WOMEN表示女")
    @Length(max = 10, message = "性别  MAN表示男 ，WOMEN表示女长度不能超过10")
    private String sex;
    /**
     * 证件类型ID
     */
    @ApiModelProperty(value = "证件类型ID")
    @Length(max = 10, message = "证件类型ID长度不能超过10")
    private String idTypeCode;
    /**
     * 证件类型名称
     */
    @ApiModelProperty(value = "证件类型名称")
    @Length(max = 100, message = "证件类型名称长度不能超过100")
    private String idTypeName;
    /**
     * 证件号码
     */
    @ApiModelProperty(value = "证件号码")
    @Length(max = 30, message = "证件号码长度不能超过30")
    private String idNumber;
    /**
     * 身份证号码
     */
    @ApiModelProperty(value = "身份证号码")
    @Length(max = 20, message = "身份证号码长度不能超过20")
    private String idCard;
    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    @Length(max = 12, message = "联系电话长度不能超过12")
    private String telphone;
    /**
     * 家庭住址/单位地址
     */
    @ApiModelProperty(value = "家庭住址/单位地址")
    @Length(max = 100, message = "家庭住址/单位地址长度不能超过100")
    private String contactAddress;
    @ApiModelProperty(value = "")
    @Length(max = 100, message = "长度不能超过100")
    private String remark;
    /**
     * 客户状态 0.预建档  1. 在用   2.销户
     */
    @ApiModelProperty(value = "客户状态 0.预建档  1. 在用   2.销户")
    private Integer customerStatus;
    /**
     * 预存总额
     */
    @ApiModelProperty(value = "预存总额")
    private BigDecimal preStoreMoney;
    /**
     * 预存次数
     */
    @ApiModelProperty(value = "预存次数")
    private Integer preStoreCount;
    /**
     * 账户余额
     */
    @ApiModelProperty(value = "账户余额")
    private BigDecimal balance;
    /**
     * 代扣合同编号
     */
    @ApiModelProperty(value = "代扣合同编号")
    @Length(max = 30, message = "代扣合同编号长度不能超过30")
    private String contractNumber;
    /**
     * 代扣签约状态
     */
    @ApiModelProperty(value = "代扣签约状态")
    private Boolean contractStatus;
    /**
     * 开户行
     */
    @ApiModelProperty(value = "开户行")
    @Length(max = 50, message = "开户行长度不能超过50")
    private String bank;
    /**
     * 持卡人
     */
    @ApiModelProperty(value = "持卡人")
    @Length(max = 100, message = "持卡人长度不能超过100")
    private String cardholder;
    /**
     * 银行账号
     */
    @ApiModelProperty(value = "银行账号")
    @Length(max = 30, message = "银行账号长度不能超过30")
    private String bankAccount;
    /**
     * 保险编号
     */
    @ApiModelProperty(value = "保险编号")
    @Length(max = 60, message = "保险编号长度不能超过60")
    private String insuranceNo;
    /**
     * 保险到期时间
     */
    @ApiModelProperty(value = "保险到期时间")
    private LocalDateTime insuranceEndTime;
    /**
     * 发票抬头
     */
    @ApiModelProperty(value = "发票抬头")
    @Length(max = 100, message = "发票抬头长度不能超过100")
    private String invoiceTitle;
    /**
     * 发票纳税人识别
     */
    @ApiModelProperty(value = "发票纳税人识别")
    @Length(max = 30, message = "发票纳税人识别长度不能超过30")
    private String tin;
    /**
     * 发票开户行及账号
     */
    @ApiModelProperty(value = "发票开户行及账号")
    @Length(max = 30, message = "发票开户行及账号长度不能超过30")
    private String invoiceBankAccount;
    /**
     * 发票公司地址
     */
    @ApiModelProperty(value = "发票公司地址")
    @Length(max = 100, message = "发票公司地址长度不能超过100")
    private String invoiceAddress;
    /**
     * 发票电子邮箱
     */
    @ApiModelProperty(value = "发票电子邮箱")
    @Length(max = 50, message = "发票电子邮箱长度不能超过50")
    private String invoiceEmail;
    /**
     * 逻辑删除
     */
    @ApiModelProperty(value = "逻辑删除")
    private Integer deleteStatus;
    /**
     * 黑名单状态
     */
    @ApiModelProperty(value = "黑名单状态")
    private Integer blackStatus;
    /**
     * 删除人
     */
    @ApiModelProperty(value = "删除人")
    private Long deleteUser;
    /**
     * 删除时间
     */
    @ApiModelProperty(value = "删除时间")
    private LocalDateTime deleteTime;
}
