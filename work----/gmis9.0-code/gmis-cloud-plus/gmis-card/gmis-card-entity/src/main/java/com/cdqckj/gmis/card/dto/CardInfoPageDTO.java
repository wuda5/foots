package com.cdqckj.gmis.card.dto;

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
 * 气表卡信息
 * </p>
 *
 * @author tp
 * @since 2020-11-06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "CardInfoPageDTO", description = "气表卡信息")
public class CardInfoPageDTO implements Serializable {

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
    @Length(max = 32, message = "营业厅ID长度不能超过32")
    private String businessHallId;
    /**
     * 营业厅名称
     */
    @ApiModelProperty(value = "营业厅名称")
    @Length(max = 100, message = "营业厅名称长度不能超过100")
    private String businessHallName;
    /**
     * 卡号
     */
    @ApiModelProperty(value = "卡号")
    @Length(max = 32, message = "卡号长度不能超过32")
    private String cardNo;
    /**
     * 气表编码
     */
    @ApiModelProperty(value = "气表编码")
    @Length(max = 32, message = "气表编码长度不能超过32")
    private String gasMeterCode;


    /**
     * 客户编码
     */
    @ApiModelProperty(value = "客户编码")
    @Length(max = 32, message = "客户编码长度不能超过32")
    private String customerCode;


    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    @Length(max = 100, message = "客户名称长度不能超过100")
    private String customerName;

    /**
     * 卡类型
     *             IC卡
     *             ID卡
     */
    @ApiModelProperty(value = "卡类型")
    @Length(max = 32, message = "卡类型长度不能超过32")
    private String cardType;
    /**
     * 开卡状态
     *             待收费
     *             待写卡
     *             已发卡
     */
    @ApiModelProperty(value = "开卡状态")
    @Length(max = 32, message = "开卡状态长度不能超过32")
    private String cardStatus;

    /**
     * 缴费单号
     */
    @ApiModelProperty(value = "缴费单号")
    private String chargeNo;

    /**
     * 卡上合计量
     */
    @ApiModelProperty(value = "卡上合计量")
    private BigDecimal totalAmount;
    /**
     * 卡上余额
     */
    @ApiModelProperty(value = "卡上余额")
    private BigDecimal cardBalance;
    /**
     * 卡上充值次数
     */
    @ApiModelProperty(value = "卡上充值次数")
    private Integer cardChargeCount;
    /**
     * 数据状态:
     *             1: 正常
     *             0: 作废
     */
    @ApiModelProperty(value = "数据状态:")
    private Integer dataStatus;

}
