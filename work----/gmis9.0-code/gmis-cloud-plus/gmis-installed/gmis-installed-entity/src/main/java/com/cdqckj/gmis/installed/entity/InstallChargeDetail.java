package com.cdqckj.gmis.installed.entity;

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
 * 报装费用清单
 * </p>
 *
 * @author tp
 * @since 2020-11-05
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("gc_install_charge_detail")
@ApiModel(value = "InstallChargeDetail", description = "报装费用清单")
@AllArgsConstructor
public class InstallChargeDetail extends Entity<Long> {

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
     * 报装ID
     */
    @ApiModelProperty(value = "报装ID")
    @TableField("install_id")
    @Excel(name = "报装ID")
    private Long installId;

    /**
     * 收费项目ID
     */
    @ApiModelProperty(value = "收费项目ID")
    @NotNull(message = "收费项目ID不能为空")
    @TableField("charge_item_id")
    @Excel(name = "收费项目ID")
    private Long chargeItemId;

    /**
     * 收费项目名称
     */
    @ApiModelProperty(value = "收费项目名称")
    @Length(max = 100, message = "收费项目名称长度不能超过100")
    @TableField(value = "charge_item_name", condition = LIKE)
    @Excel(name = "收费项目名称")
    private String chargeItemName;

    /**
     * 收费状态
     *             WAIT_CHARGE: 等待收费
     *             CHARGED: 已收费
     *             FREE_CHARGE: 免收费
     *             
     */
    @ApiModelProperty(value = "收费状态")
    @Length(max = 32, message = "收费状态长度不能超过32")
    @TableField(value = "charge_state", condition = LIKE)
    @Excel(name = "收费状态")
    private String chargeState;

    /**
     * 金额
     */
    @ApiModelProperty(value = "金额")
    @TableField("amount")
    @Excel(name = "金额")
    private BigDecimal amount;

    /**
     * 删除状态
     */
    @ApiModelProperty(value = "删除状态")
    @TableField("delete_status")
    @Excel(name = "删除状态")
    private Integer deleteStatus;


    @Builder
    public InstallChargeDetail(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime, 
                    String companyCode, String companyName, Long orgId, String orgName, Long installId, 
                    Long chargeItemId, String chargeItemName, String chargeState, BigDecimal amount, Integer deleteStatus) {
        this.id = id;
        this.createUser = createUser;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.orgId = orgId;
        this.orgName = orgName;
        this.installId = installId;
        this.chargeItemId = chargeItemId;
        this.chargeItemName = chargeItemName;
        this.chargeState = chargeState;
        this.amount = amount;
        this.deleteStatus = deleteStatus;
    }

}
