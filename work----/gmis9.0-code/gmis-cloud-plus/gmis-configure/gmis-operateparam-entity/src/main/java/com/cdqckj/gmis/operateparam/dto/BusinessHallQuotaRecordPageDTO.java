package com.cdqckj.gmis.operateparam.dto;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cdqckj.gmis.base.entity.Entity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.cdqckj.gmis.operateparam.enumeration.QuotaType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import lombok.Data;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import java.io.Serializable;

/**
 * <p>
 * 实体类
 * 
 * </p>
 *
 * @author gmis
 * @since 2020-06-30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "BusinessHallQuotaRecordPageDTO", description = "")
public class BusinessHallQuotaRecordPageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 公司ID
     */
    @ApiModelProperty(value = "公司ID")
    @Length(max = 32, message = "公司ID长度不能超过32")
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
     * 营业厅ID
     */
    @ApiModelProperty(value = "营业厅ID")
    private Long businessHallId;
    /**
     * 0：不限制
     *             1：限制
     */
    @ApiModelProperty(value = "0：不限制")
    private Integer dataStatus;
    /**
     * 营业厅名称
     */
    @ApiModelProperty(value = "营业厅名称")
    @Length(max = 100, message = "营业厅名称长度不能超过100")
    private String businessHallName;
    /**
     * 
     * #QuotaType{CUMULATIVE:累加;COVER:覆盖;}
     */
    @ApiModelProperty(value = "")
    private QuotaType quotaType;
    /**
     * 配额时间
     */
    @ApiModelProperty(value = "配额时间")
    private LocalDateTime quotaTime;
    /**
     * 金额
     */
    @ApiModelProperty(value = "金额")
    private BigDecimal money;
    /**
     * 当前余额
     */
    @ApiModelProperty(value = "当前余额")
    private BigDecimal balance;
    /**
     * 配后金额
     */
    @ApiModelProperty(value = "配后金额")
    private BigDecimal totalMoney;
    /**
     * 单笔限额
     */
    @ApiModelProperty(value = "单笔限额")
    private BigDecimal singleBusinessRestrictionMoney;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @Length(max = 100, message = "备注长度不能超过100")
    private String remark;
    /**
     * 创建人名称
     */
    @ApiModelProperty(value = "创建人名称")
    @Length(max = 100, message = "创建人名称长度不能超过100")
    private String createUserName;

}
