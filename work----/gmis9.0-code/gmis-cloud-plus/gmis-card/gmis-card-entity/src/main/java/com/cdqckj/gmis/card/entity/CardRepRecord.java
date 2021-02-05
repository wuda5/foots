package com.cdqckj.gmis.card.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cdqckj.gmis.base.entity.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

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
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("gt_card_rep_record")
@ApiModel(value = "CardRepRecord", description = "补换卡记录")
@AllArgsConstructor
public class CardRepRecord extends Entity<Long> {

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
     * 营业厅ID
     */
    @ApiModelProperty(value = "营业厅ID")
    @Length(max = 32, message = "营业厅ID长度不能超过32")
    @TableField(value = "business_hall_id", condition = LIKE)
    @Excel(name = "营业厅ID")
    private String businessHallId;

    /**
     * 营业厅名称
     */
    @ApiModelProperty(value = "营业厅名称")
    @Length(max = 100, message = "营业厅名称长度不能超过100")
    @TableField(value = "business_hall_name", condition = LIKE)
    @Excel(name = "营业厅名称")
    private String businessHallName;

    /**
     * 卡号
     */
    @ApiModelProperty(value = "卡号")
    @Length(max = 32, message = "卡号长度不能超过32")
    @TableField(value = "card_no", condition = LIKE)
    @Excel(name = "卡号")
    private String cardNo;

    /**
     * 气表编码
     */
    @ApiModelProperty(value = "气表编码")
    @Length(max = 32, message = "气表编码长度不能超过32")
    @TableField(value = "gas_meter_code", condition = LIKE)
    @Excel(name = "气表编码")
    private String gasMeterCode;

    /**
     * 客户编码
     */
    @ApiModelProperty(value = "客户编码")
    @Length(max = 32, message = "客户编码长度不能超过32")
    @TableField(value = "customer_code", condition = SqlCondition.EQUAL)
    @Excel(name = "客户编码")
    private String customerCode;


    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    @Length(max = 100, message = "客户名称长度不能超过100")
    @TableField(value = "customer_name", condition = SqlCondition.EQUAL)
    @Excel(name = "客户名称")
    private String customerName;

    /**
     * 卡类型
     *             IC卡
     *             ID卡
     */
    @ApiModelProperty(value = "卡类型")
    @Length(max = 32, message = "卡类型长度不能超过32")
    @TableField(value = "card_type", condition = LIKE)
    @Excel(name = "卡类型")
    private String cardType;

    /**
     * 是否回收老卡
     */
    @ApiModelProperty(value = "是否回收老卡")
    @TableField("is_recovery_old_card")
    @Excel(name = "是否回收老卡", replace = {"是_true", "否_false", "_null"})
    private Boolean isRecoveryOldCard;

    /**
     * 补卡类型
     *             新用户卡
     *             购气卡
     */
    @ApiModelProperty(value = "补卡类型")
    @Length(max = 30, message = "补卡类型长度不能超过30")
    @TableField(value = "rep_card_type", condition = LIKE)
    @Excel(name = "补卡类型")
    private String repCardType;

    /**
     * 补卡方式
     *             补上次充值
     *             无充值
     */
    @ApiModelProperty(value = "补卡方式")
    @Length(max = 30, message = "补卡方式长度不能超过30")
    @TableField(value = "rep_card_method", condition = LIKE)
    @Excel(name = "补卡方式")
    private String repCardMethod;

    /**
     * 补卡气量
     */
    @ApiModelProperty(value = "补卡气量")
    @TableField("rep_card_gas")
    @Excel(name = "补卡气量")
    private BigDecimal repCardGas;

    /**
     * 补卡金额
     */
    @ApiModelProperty(value = "补卡金额")
    @TableField("rep_card_money")
    @Excel(name = "补卡金额")
    private BigDecimal repCardMoney;

    /**
     * 补卡原因
     */
    @ApiModelProperty(value = "补卡原因")
    @Length(max = 1000, message = "补卡原因长度不能超过1000")
    @TableField(value = "rep_card_desc", condition = LIKE)
    @Excel(name = "补卡原因")
    private String repCardDesc;

    /**
     * 补卡状态
     *             待收费
     *             待写卡
     *             已补卡
     */
    @ApiModelProperty(value = "补卡状态")
    @Length(max = 32, message = "补卡状态长度不能超过32")
    @TableField(value = "rep_card_status", condition = LIKE)
    @Excel(name = "补卡状态")
    private String repCardStatus;

    /**
     * 数据状态:
     *             1: 正常
     *             0: 作废
     */
    @ApiModelProperty(value = "数据状态:")
    @TableField("data_status")
    @Excel(name = "数据状态:")
    private Integer dataStatus;


    @Builder
    public CardRepRecord(Long id, LocalDateTime createTime, Long createUser, LocalDateTime updateTime, Long updateUser, 
                    String companyCode, String companyName, Long orgId, String orgName, String businessHallId, 
                    String businessHallName, String cardNo, String gasMeterCode, String cardType, Boolean isRecoveryOldCard, String repCardType, 
                    String repCardMethod, BigDecimal repCardGas, BigDecimal repCardMoney, String repCardDesc, String repCardStatus, Integer dataStatus) {
        this.id = id;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.orgId = orgId;
        this.orgName = orgName;
        this.businessHallId = businessHallId;
        this.businessHallName = businessHallName;
        this.cardNo = cardNo;
        this.gasMeterCode = gasMeterCode;
        this.cardType = cardType;
        this.isRecoveryOldCard = isRecoveryOldCard;
        this.repCardType = repCardType;
        this.repCardMethod = repCardMethod;
        this.repCardGas = repCardGas;
        this.repCardMoney = repCardMoney;
        this.repCardDesc = repCardDesc;
        this.repCardStatus = repCardStatus;
        this.dataStatus = dataStatus;
    }

}
