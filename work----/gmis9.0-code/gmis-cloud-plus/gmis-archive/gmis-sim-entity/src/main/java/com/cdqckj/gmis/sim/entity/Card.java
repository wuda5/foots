package com.cdqckj.gmis.sim.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
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
 * @since 2020-07-09
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("da_card")
@ApiModel(value = "Card", description = "")
@AllArgsConstructor
public class Card extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 公司编码
     */
    @ApiModelProperty(value = "公司编码")
    @Length(max = 32, message = "公司编码长度不能超过32")
    @TableField(value = "company_code", condition = LIKE)
    @Excel(name = "公司编码")
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
     * 气表编号
     */
    @ApiModelProperty(value = "气表编号")
    @Length(max = 30, message = "气表编号长度不能超过30")
    @TableField(value = "gas_code", condition = LIKE)
    @Excel(name = "气表编号")
    private String gasCode;

    /**
     * 卡类型
     */
    @ApiModelProperty(value = "卡类型")
    @Length(max = 30, message = "卡类型长度不能超过30")
    @TableField(value = "card_type", condition = LIKE)
    @Excel(name = "卡类型")
    private String cardType;

    /**
     * 卡编号
     */
    @ApiModelProperty(value = "卡编号")
    @Length(max = 100, message = "卡编号长度不能超过100")
    @TableField(value = "card_code", condition = LIKE)
    @Excel(name = "卡编号")
    private String cardCode;

    /**
     * 卡名称
     */
    @ApiModelProperty(value = "卡名称")
    @Length(max = 60, message = "卡名称长度不能超过60")
    @TableField(value = "card_name", condition = LIKE)
    @Excel(name = "卡名称")
    private String cardName;

    /**
     * 卡上次数
     */
    @ApiModelProperty(value = "卡上次数")
    @TableField("card_count")
    @Excel(name = "卡上次数")
    private Integer cardCount;

    /**
     * 卡上气量
     */
    @ApiModelProperty(value = "卡上气量")
    @TableField("card_volume")
    @Excel(name = "卡上气量")
    private BigDecimal cardVolume;

    /**
     * 卡上金额
     */
    @ApiModelProperty(value = "卡上金额")
    @TableField("card_money")
    @Excel(name = "卡上金额")
    private BigDecimal cardMoney;

    /**
     * 报警气量
     */
    @ApiModelProperty(value = "报警气量")
    @TableField("alarm_volume")
    @Excel(name = "报警气量")
    private BigDecimal alarmVolume;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @Length(max = 20, message = "备注长度不能超过20")
    @TableField(value = "remark", condition = LIKE)
    @Excel(name = "备注")
    private String remark;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    @TableField("data_status")
    @Excel(name = "状态")
    private Integer dataStatus;

    @ApiModelProperty(value = "删除标识")
    @TableField("delete_status")
    @Excel(name = "状态")
    private Integer deleteStatus;



    @Builder
    public Card(Long id, LocalDateTime createTime, Long createUser, LocalDateTime updateTime, Long updateUser, 
                    String companyCode, String companyName, Long orgId, String orgName, String gasCode, 
                    String cardType, String cardCode, String cardName, Integer cardCount, BigDecimal cardVolume, BigDecimal cardMoney, 
                    BigDecimal alarmVolume, String remark, Integer dataStatus,Integer deleteStatus) {
        this.id = id;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.orgId = orgId;
        this.orgName = orgName;
        this.gasCode = gasCode;
        this.cardType = cardType;
        this.cardCode = cardCode;
        this.cardName = cardName;
        this.cardCount = cardCount;
        this.cardVolume = cardVolume;
        this.cardMoney = cardMoney;
        this.alarmVolume = alarmVolume;
        this.remark = remark;
        this.dataStatus = dataStatus;
        this.deleteStatus=deleteStatus;
    }

}
