package com.cdqckj.gmis.installed.dto;

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
import com.cdqckj.gmis.base.entity.SuperEntity;
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
 * 报装费用清单
 * </p>
 *
 * @author tp
 * @since 2020-11-05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "InstallChargeDetailUpdateDTO", description = "报装费用清单")
public class InstallChargeDetailUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @NotNull(message = "id不能为空", groups = SuperEntity.Update.class)
    private Long id;

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
     * 报装ID
     */
    @ApiModelProperty(value = "报装ID")
    private Long installId;
    /**
     * 收费项目ID
     */
    @ApiModelProperty(value = "收费项目ID")
    @NotNull(message = "收费项目ID不能为空")
    private Long chargeItemId;
    /**
     * 收费项目名称
     */
    @ApiModelProperty(value = "收费项目名称")
    @Length(max = 100, message = "收费项目名称长度不能超过100")
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
    private String chargeState;
    /**
     * 金额
     */
    @ApiModelProperty(value = "金额")
    private BigDecimal amount;
    /**
     * 删除状态
     */
    @ApiModelProperty(value = "删除状态")
    private Integer deleteStatus;
}
