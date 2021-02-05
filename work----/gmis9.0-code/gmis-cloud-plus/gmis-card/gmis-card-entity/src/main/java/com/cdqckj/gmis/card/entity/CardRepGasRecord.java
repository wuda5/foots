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
 * 卡补气记录
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
@TableName("gt_card_rep_gas_record")
@ApiModel(value = "CardRepGasRecord", description = "卡补气记录")
@AllArgsConstructor
public class CardRepGasRecord extends Entity<Long> {

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
     * 补气方式
     *             补上次充值
     *             按需补充
     */
    @ApiModelProperty(value = "补气方式")
    @Length(max = 30, message = "补气方式长度不能超过30")
    @TableField(value = "rep_gas_method", condition = LIKE)
    @Excel(name = "补气方式")
    private String repGasMethod;

    /**
     * 补充气量
     */
    @ApiModelProperty(value = "补充气量")
    @TableField("rep_gas")
    @Excel(name = "补充气量")
    private BigDecimal repGas;

    /**
     * 补充金额
     */
    @ApiModelProperty(value = "补充金额")
    @TableField("rep_money")
    @Excel(name = "补充金额")
    private BigDecimal repMoney;

    /**
     * 补气原因
     */
    @ApiModelProperty(value = "补气原因")
    @Length(max = 1000, message = "补气原因长度不能超过1000")
    @TableField(value = "rep_gas_desc", condition = LIKE)
    @Excel(name = "补气原因")
    private String repGasDesc;

    /**
     * 补气状态
     *             待写卡
     *             待上表
     *             补气完成
     */
    @ApiModelProperty(value = "补气状态")
    @Length(max = 32, message = "补气状态长度不能超过32")
    @TableField(value = "rep_gas_status", condition = LIKE)
    @Excel(name = "补气状态")
    private String repGasStatus;

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
    public CardRepGasRecord(Long id, LocalDateTime createTime, Long createUser, LocalDateTime updateTime, Long updateUser, 
                    String companyCode, String companyName, Long orgId, String orgName, String businessHallId, 
                    String businessHallName, String cardNo, String gasMeterCode, String repGasMethod, BigDecimal repGas, BigDecimal repMoney, 
                    String repGasDesc, String repGasStatus, Integer dataStatus) {
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
        this.repGasMethod = repGasMethod;
        this.repGas = repGas;
        this.repMoney = repMoney;
        this.repGasDesc = repGasDesc;
        this.repGasStatus = repGasStatus;
        this.dataStatus = dataStatus;
    }

}
