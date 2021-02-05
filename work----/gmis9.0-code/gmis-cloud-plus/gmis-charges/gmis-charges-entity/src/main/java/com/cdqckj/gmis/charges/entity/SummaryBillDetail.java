package com.cdqckj.gmis.charges.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
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
 * @author songyz
 * @since 2020-12-10
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("gt_summary_bill_detail")
@ApiModel(value = "SummaryBillDetail", description = "")
@AllArgsConstructor
public class SummaryBillDetail extends Entity<Long> {

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
     * 扎帐来源ID
     */
    @ApiModelProperty(value = "扎帐来源ID")
    @TableField("source_id")
    @Excel(name = "扎帐来源ID")
    private Long sourceId;

    /**
     * 扎帐来源类型
     */
    @ApiModelProperty(value = "扎帐来源类型")
    @TableField("source_type")
    @Excel(name = "扎帐来源类型")
    private Integer sourceType;

    /**
     * 银行扎帐ID转账金额
     */
    @ApiModelProperty(value = "银行扎帐ID转账金额")
    @TableField("summary_bill_id")
    @Excel(name = "银行扎帐ID转账金额")
    private Long summaryBillId;

    /**
     * 操作员编号
     */
    @ApiModelProperty(value = "操作员编号")
    @Length(max = 32, message = "操作员编号长度不能超过32")
    @TableField(value = "operator_no", condition = LIKE)
    @Excel(name = "操作员编号")
    private String operatorNo;

    /**
     * 扎帐日期
     */
    @ApiModelProperty(value = "扎帐日期")
    @Length(max = 8, message = "扎帐日期长度不能超过8")
    @TableField(value = "summary_bill_date", condition = LIKE)
    @Excel(name = "扎帐日期")
    private String summaryBillDate;

    /**
     * 数据状态
     */
    @ApiModelProperty(value = "数据状态")
    @TableField("data_status")
    @Excel(name = "数据状态")
    private Integer dataStatus;


    @Builder
    public SummaryBillDetail(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime, 
                    String companyCode, String companyName, Long orgId, String orgName, Long sourceId, 
                    Integer sourceType, Long summaryBillId, String operatorNo, String summaryBillDate, Integer dataStatus) {
        this.id = id;
        this.createUser = createUser;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.orgId = orgId;
        this.orgName = orgName;
        this.sourceId = sourceId;
        this.sourceType = sourceType;
        this.summaryBillId = summaryBillId;
        this.operatorNo = operatorNo;
        this.summaryBillDate = summaryBillDate;
        this.dataStatus = dataStatus;
    }

}
