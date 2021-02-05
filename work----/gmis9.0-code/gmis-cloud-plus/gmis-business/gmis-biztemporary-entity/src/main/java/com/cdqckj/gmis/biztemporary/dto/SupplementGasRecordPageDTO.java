package com.cdqckj.gmis.biztemporary.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 实体类
 * 补气记录
 * </p>
 *
 * @author gmis
 * @since 2020-12-10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "SupplementGasRecordPageDTO", description = "补气记录")
public class SupplementGasRecordPageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 公司编号
     */
    @ApiModelProperty(value = "公司编号")
    @Length(max = 32, message = "公司编号长度不能超过32")
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
     * 营业厅ID
     */
    @ApiModelProperty(value = "营业厅ID")
    private Long businessHallId;
    /**
     * 营业厅名称
     */
    @ApiModelProperty(value = "营业厅名称")
    @Length(max = 100, message = "营业厅名称长度不能超过100")
    private String businessHallName;
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
     * 气表编号
     */
    @ApiModelProperty(value = "气表编号")
    @Length(max = 32, message = "气表编号长度不能超过32")
    private String gasMeterCode;
    /**
     * 补充方式：补上次充值、按需补充
     */
    @ApiModelProperty(value = "补充方式：补上次充值、按需补充")
    @Length(max = 30, message = "补充方式：补上次充值、按需补充长度不能超过30")
    private String repGasMethod;
    /**
     * 补气原因
     */
    @ApiModelProperty(value = "补气原因")
    @Length(max = 300, message = "补气原因长度不能超过300")
    private String repGasDesc;
    /**
     * 补充气量
     */
    @ApiModelProperty(value = "补充气量")
    private BigDecimal repGas;
    /**
     * 补充金额
     */
    @ApiModelProperty(value = "补充金额")
    private BigDecimal repMoney;
    /**
     * 补气状态
     */
    @ApiModelProperty(value = "补气状态")
    @Length(max = 30, message = "补气状态长度不能超过30")
    private String repGasStatus;
    /**
     * 删除状态
     */
    @ApiModelProperty(value = "删除状态")
    private Integer dataStatus;

}
