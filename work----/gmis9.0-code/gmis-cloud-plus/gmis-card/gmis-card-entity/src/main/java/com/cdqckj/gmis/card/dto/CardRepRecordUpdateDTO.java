package com.cdqckj.gmis.card.dto;

import com.cdqckj.gmis.base.entity.SuperEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 实体类
 * 补换卡记录
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
@ApiModel(value = "CardRepRecordUpdateDTO", description = "补换卡记录")
public class CardRepRecordUpdateDTO implements Serializable {

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
     * 是否回收老卡
     */
    @ApiModelProperty(value = "是否回收老卡")
    private Boolean isRecoveryOldCard;
    /**
     * 补卡类型
     *             新用户卡
     *             购气卡
     */
    @ApiModelProperty(value = "补卡类型")
    @Length(max = 30, message = "补卡类型长度不能超过30")
    private String repCardType;
    /**
     * 补卡方式
     *             补上次充值
     *             无充值
     */
    @ApiModelProperty(value = "补卡方式")
    @Length(max = 30, message = "补卡方式长度不能超过30")
    private String repCardMethod;
    /**
     * 补卡气量
     */
    @ApiModelProperty(value = "补卡气量")
    private BigDecimal repCardGas;
    /**
     * 补卡金额
     */
    @ApiModelProperty(value = "补卡金额")
    private BigDecimal repCardMoney;
    /**
     * 补卡原因
     */
    @ApiModelProperty(value = "补卡原因")
    @Length(max = 1000, message = "补卡原因长度不能超过1000")
    private String repCardDesc;
    /**
     * 补卡状态
     *             待收费
     *             待写卡
     *             已补卡
     */
    @ApiModelProperty(value = "补卡状态")
    @Length(max = 32, message = "补卡状态长度不能超过32")
    private String repCardStatus;
    /**
     * 数据状态:
     *             1: 正常
     *             0: 作废
     */
    @ApiModelProperty(value = "数据状态:")
    private Integer dataStatus;
}
