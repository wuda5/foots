package com.cdqckj.gmis.charges.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 实体类
 * 账户表余额更新参数对象
 * </p>
 *
 * @author hp
 * @since 2020-11-24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "UpdateAccountParamDTO", description = "账户表余额更新参数对象")
public class UpdateAccountParamDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    @Length(max = 32, message = "客户编号长度不能超过32")
    @NonNull
    private String customerCode;
    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    @Length(max = 32, message = "客户名称长度不能超过32")
    private String customerName;
    /**
     * 账户金额
     */
    @ApiModelProperty(value = "账户金额")
    @NonNull
    private BigDecimal accountMoney;
    /**
     * 业务id
     */
    @ApiModelProperty(value = "业务编号")
    @NonNull
    private String billNo;

    /**
     * 业务id
     */
    @ApiModelProperty(value = "业务类型")
    @NonNull
    private String billType;
    /**
     * 营业厅ID
     */
    @ApiModelProperty(value = "营业厅ID")
    private Long businessHallId;
    /**
     * 营业厅名称
     */
    @ApiModelProperty(value = "营业厅名称")
    @Length(max = 100, message = "营业厅名称长度不能超过100")
    private String businessHallName;

    /**
     * 气表编号
     */
    @ApiModelProperty(value = "气表编号")
    @Length(max = 32, message = "气表编号长度不能超过32")
    private String gasMeterCode;

}
