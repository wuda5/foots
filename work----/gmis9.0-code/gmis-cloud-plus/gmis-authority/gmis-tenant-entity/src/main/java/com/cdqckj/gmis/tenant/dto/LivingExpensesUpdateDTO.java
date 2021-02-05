package com.cdqckj.gmis.tenant.dto;

import com.cdqckj.gmis.base.entity.SuperEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 实体类
 * 支付宝微信-生活缴费关联租户
 * </p>
 *
 * @author gmis
 * @since 2021-01-22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "LivingExpensesUpdateDTO", description = "支付宝微信-生活缴费关联租户")
public class LivingExpensesUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @NotNull(message = "id不能为空", groups = SuperEntity.Update.class)
    private Long id;

    /**
     * 租户编码
     */
    @ApiModelProperty(value = "租户编码")
    @NotEmpty(message = "租户编码不能为空")
    @Length(max = 30, message = "租户编码长度不能超过30")
    private String companyCode;
    /**
     * 支付宝清算单位
     */
    @ApiModelProperty(value = "支付宝清算单位")
    @Length(max = 20, message = "支付宝清算单位长度不能超过20")
    private String alipayCode;
    /**
     * 微信清算单位
     */
    @ApiModelProperty(value = "微信清算单位")
    @Length(max = 100, message = "微信清算单位长度不能超过100")
    private String weixinCode;
    /**
     * 删除标识（0：存在；1：删除）
     */
    @ApiModelProperty(value = "删除标识（0：存在；1：删除）")
    private Integer deleteStatus;
}
