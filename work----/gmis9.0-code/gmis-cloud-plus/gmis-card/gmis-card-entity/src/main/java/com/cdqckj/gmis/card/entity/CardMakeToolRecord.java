package com.cdqckj.gmis.card.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
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
 * 制工具卡记录
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
@TableName("gt_card_make_tool_record")
@ApiModel(value = "CardMakeToolRecord", description = "制工具卡记录")
@AllArgsConstructor
public class CardMakeToolRecord extends Entity<Long> {

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
     * 制卡气量
     */
    @ApiModelProperty(value = "制卡气量")
    @TableField("write_gas")
    @Excel(name = "制卡气量")
    private BigDecimal writeGas;

    /**
     * 制卡金额
     */
    @ApiModelProperty(value = "制卡金额")
    @TableField("write_money")
    @Excel(name = "制卡金额")
    private BigDecimal writeMoney;

    /**
     * 制卡次数
     */
    @ApiModelProperty(value = "制卡次数")
    @TableField("write_count")
    @Excel(name = "制卡次数")
    private Integer writeCount;

    /**
     * 制卡费用
     */
    @ApiModelProperty(value = "制卡费用")
    @TableField("write_fee")
    @Excel(name = "制卡费用")
    private BigDecimal writeFee;

    /**
     * 制卡人名称
     */
    @ApiModelProperty(value = "制卡人名称")
    @Length(max = 100, message = "制卡人名称长度不能超过100")
    @TableField(value = "create_user_name", condition = LIKE)
    @Excel(name = "制卡人名称")
    private String createUserName;

    /**
     * 制卡原因
     */
    @ApiModelProperty(value = "制卡原因")
    @Length(max = 100, message = "制卡原因长度不能超过100")
    @TableField(value = "remark", condition = LIKE)
    @Excel(name = "制卡原因")
    private String remark;

    /**
     * 写卡记录ID
     */
    @ApiModelProperty(value = "写卡记录ID")
    @TableField("write_card_id")
    @Excel(name = "写卡记录ID")
    private Long writeCardId;


    @Builder
    public CardMakeToolRecord(Long id, LocalDateTime createTime, Long createUser, Long updateUser, LocalDateTime updateTime, 
                    String companyCode, String companyName, Long orgId, String orgName, String businessHallId, 
                    String businessHallName, BigDecimal writeGas, BigDecimal writeMoney, Integer writeCount, BigDecimal writeFee, String createUserName, 
                    String remark, Long writeCardId) {
        this.id = id;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.orgId = orgId;
        this.orgName = orgName;
        this.businessHallId = businessHallId;
        this.businessHallName = businessHallName;
        this.writeGas = writeGas;
        this.writeMoney = writeMoney;
        this.writeCount = writeCount;
        this.writeFee = writeFee;
        this.createUserName = createUserName;
        this.remark = remark;
        this.writeCardId = writeCardId;
    }

}
