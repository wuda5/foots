package com.cdqckj.gmis.devicearchive.dto;

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
 * 账户流水
 * </p>
 *
 * @author gmis
 * @since 2020-12-21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "GasMeterInfoSerialSaveDTO", description = "账户流水")
public class GasMeterInfoSerialSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

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
     * 账户号
     */
    @ApiModelProperty(value = "账户号")
    private Long gasMeterInfoId;
    /**
     * 气表编号
     */
    @ApiModelProperty(value = "气表编号")
    @Length(max = 32, message = "气表编号长度不能超过32")
    private String gasMeterCode;

    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    @Length(max = 32, message = "客户编号长度不能超过32")
    private String customerCode;

    /**
     * 流水号
     */
    @ApiModelProperty(value = "流水号")
    @Length(max = 32, message = "流水号长度不能超过32")
    private String serialNo;
    /**
     * 记费记录号( 根据各个场景记录不同的编号)
     *             例如：收费扣款或者收费预存存放 对应单号。
     */
    @ApiModelProperty(value = "记费记录号( 根据各个场景记录不同的编号)")
    @Length(max = 32, message = "记费记录号( 根据各个场景记录不同的编号)长度不能超过32")
    private String billNo;
    /**
     * 收入支出类型：
     *             预存抵扣
     *             预存
     *             抵扣退费
     *             预存退费
     *             
     */
    @ApiModelProperty(value = "收入支出类型：")
    @Length(max = 32, message = "收入支出类型：长度不能超过32")
    private String billType;
    /**
     * 记账金额
     */
    @ApiModelProperty(value = "记账金额")
    private BigDecimal bookMoney;
    /**
     * 记账前余额
     */
    @ApiModelProperty(value = "记账前余额")
    private BigDecimal bookPreMoney;
    /**
     * 记账后余额
     */
    @ApiModelProperty(value = "记账后余额")
    private BigDecimal bookAfterMoney;
    /**
     * 赠送记账金额
     */
    @ApiModelProperty(value = "赠送记账金额")
    private BigDecimal giveBookMoney;
    /**
     * 赠送记账前余额
     */
    @ApiModelProperty(value = "赠送记账前余额")
    private BigDecimal giveBookPreMoney;
    /**
     * 赠送记账后余额
     */
    @ApiModelProperty(value = "赠送记账后余额")
    private BigDecimal giveBookAfterMoney;
    /**
     * 流水说明
     */
    @ApiModelProperty(value = "流水说明")
    @Length(max = 400, message = "流水说明长度不能超过400")
    private String remark;

}
