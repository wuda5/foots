package com.cdqckj.gmis.sim.dto;

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
 * 
 * </p>
 *
 * @author gmis
 * @since 2020-07-09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "CardSaveDTO", description = "")
public class CardSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 公司编码
     */
    @ApiModelProperty(value = "公司编码")
    @Length(max = 32, message = "公司编码长度不能超过32")
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
     * 气表编号
     */
    @ApiModelProperty(value = "气表编号")
    @Length(max = 30, message = "气表编号长度不能超过30")
    private String gasCode;
    /**
     * 卡类型
     */
    @ApiModelProperty(value = "卡类型")
    @Length(max = 30, message = "卡类型长度不能超过30")
    private String cardType;
    /**
     * 卡编号
     */
    @ApiModelProperty(value = "卡编号")
    @Length(max = 100, message = "卡编号长度不能超过100")
    private String cardCode;
    /**
     * 卡名称
     */
    @ApiModelProperty(value = "卡名称")
    @Length(max = 60, message = "卡名称长度不能超过60")
    private String cardName;
    /**
     * 卡上次数
     */
    @ApiModelProperty(value = "卡上次数")
    private Integer cardCount;
    /**
     * 卡上气量
     */
    @ApiModelProperty(value = "卡上气量")
    private BigDecimal cardVolume;
    /**
     * 卡上金额
     */
    @ApiModelProperty(value = "卡上金额")
    private BigDecimal cardMoney;
    /**
     * 报警气量
     */
    @ApiModelProperty(value = "报警气量")
    private BigDecimal alarmVolume;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @Length(max = 20, message = "备注长度不能超过20")
    private String remark;
    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Integer dataStatus;

    @ApiModelProperty(value = "删除标识")
    private Integer deleteStatus;

}
