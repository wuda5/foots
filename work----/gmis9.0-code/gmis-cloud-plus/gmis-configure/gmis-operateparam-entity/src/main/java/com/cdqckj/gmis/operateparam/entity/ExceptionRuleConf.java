package com.cdqckj.gmis.operateparam.entity;

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
 * 抄表异常规则配置
 * </p>
 *
 * @author gmis
 * @since 2020-09-07
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("pz_exception_rule_conf")
@ApiModel(value = "ExceptionRuleConf", description = "抄表异常规则配置")
@AllArgsConstructor
public class ExceptionRuleConf extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 公司code
     */
    @ApiModelProperty(value = "公司code")
    @Length(max = 32, message = "公司code长度不能超过32")
    @TableField(value = "company_code", condition = LIKE)
    @Excel(name = "公司code")
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
     * 用气类型id
     */
    @ApiModelProperty(value = "用气类型id")
    @TableField("use_gas_type_id")
    @Excel(name = "用气类型id")
    private Long useGasTypeId;

    /**
     * 暴增倍数（大于1）
     */
    @ApiModelProperty(value = "暴增倍数（大于1）")
    @TableField("surge_multiple")
    @Excel(name = "暴增倍数（大于1）")
    private BigDecimal surgeMultiple;

    /**
     * 锐减比例（0~1）
     */
    @ApiModelProperty(value = "锐减比例（0~1）")
    @TableField("sharp_decrease")
    @Excel(name = "锐减比例（0~1）")
    private BigDecimal sharpDecrease;

    /**
     * 创建人名称
     */
    @ApiModelProperty(value = "创建人名称")
    @Length(max = 100, message = "创建人名称长度不能超过100")
    @TableField(value = "create_user_name", condition = LIKE)
    @Excel(name = "创建人名称")
    private String createUserName;


    @Builder
    public ExceptionRuleConf(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime, 
                    String companyCode, String companyName, Long orgId, String orgName, Long useGasTypeId, 
                    BigDecimal surgeMultiple, BigDecimal sharpDecrease, String createUserName) {
        this.id = id;
        this.createUser = createUser;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.orgId = orgId;
        this.orgName = orgName;
        this.useGasTypeId = useGasTypeId;
        this.surgeMultiple = surgeMultiple;
        this.sharpDecrease = sharpDecrease;
        this.createUserName = createUserName;
    }

}
