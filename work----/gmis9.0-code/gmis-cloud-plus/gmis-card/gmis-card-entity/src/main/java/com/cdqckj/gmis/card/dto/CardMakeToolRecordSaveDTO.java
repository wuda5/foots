package com.cdqckj.gmis.card.dto;

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
 * 制工具卡记录
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
@ApiModel(value = "CardMakeToolRecordSaveDTO", description = "制工具卡记录")
public class CardMakeToolRecordSaveDTO implements Serializable {

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
     * 制卡气量
     */
    @ApiModelProperty(value = "制卡气量")
    private BigDecimal writeGas;
    /**
     * 制卡金额
     */
    @ApiModelProperty(value = "制卡金额")
    private BigDecimal writeMoney;
    /**
     * 制卡次数
     */
    @ApiModelProperty(value = "制卡次数")
    private Integer writeCount;
    /**
     * 制卡费用
     */
    @ApiModelProperty(value = "制卡费用")
    private BigDecimal writeFee;
    /**
     * 制卡人名称
     */
    @ApiModelProperty(value = "制卡人名称")
    @Length(max = 100, message = "制卡人名称长度不能超过100")
    private String createUserName;
    /**
     * 制卡原因
     */
    @ApiModelProperty(value = "制卡原因")
    @Length(max = 100, message = "制卡原因长度不能超过100")
    private String remark;
    /**
     * 写卡记录ID
     */
    @ApiModelProperty(value = "写卡记录ID")
    private Long writeCardId;

}
