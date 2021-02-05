package com.cdqckj.gmis.readmeter.dto;

import java.time.LocalDateTime;
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
 * 银行代扣记录
 * </p>
 *
 * @author gmis
 * @since 2020-07-13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "BankWithholdRecordPageDTO", description = "银行代扣记录")
public class BankWithholdRecordPageDTO implements Serializable {

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
     * 气表编号
     */
    @ApiModelProperty(value = "气表编号")
    @Length(max = 32, message = "气表编号长度不能超过32")
    private String gasMeterCode;
    /**
     * 气表名称
     */
    @ApiModelProperty(value = "气表名称")
    @Length(max = 100, message = "气表名称长度不能超过100")
    private String gasMeterName;
    /**
     * 气表类型id
     */
    @ApiModelProperty(value = "气表类型id")
    @Length(max = 32, message = "气表类型id长度不能超过32")
    private String gasMeterTypeId;
    /**
     * 气表类型名称
     */
    @ApiModelProperty(value = "气表类型名称")
    @Length(max = 100, message = "气表类型名称长度不能超过100")
    private String gasMeterTypeName;
    /**
     * 气表安装地址
     */
    @ApiModelProperty(value = "气表安装地址")
    @Length(max = 200, message = "气表安装地址长度不能超过200")
    private String gasMeterAddress;
    /**
     * 持卡人
     */
    @ApiModelProperty(value = "持卡人")
    @Length(max = 30, message = "持卡人长度不能超过30")
    private String cardholder;
    /**
     * 银行账号
     */
    @ApiModelProperty(value = "银行账号")
    @Length(max = 100, message = "银行账号长度不能超过100")
    private String bankAccount;
    /**
     * 金额
     */
    @ApiModelProperty(value = "金额")
    private BigDecimal amount;
    /**
     * 导入状态
     */
    @ApiModelProperty(value = "导入状态")
    private Integer importStatus;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @Length(max = 100, message = "备注长度不能超过100")
    private String remark;

}
