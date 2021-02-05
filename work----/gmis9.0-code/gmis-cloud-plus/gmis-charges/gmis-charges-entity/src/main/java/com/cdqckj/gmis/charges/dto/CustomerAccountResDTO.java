package com.cdqckj.gmis.charges.dto;

import com.cdqckj.gmis.base.entity.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 实体类
 * 客户账户信息
 * </p>
 *
 * @author tp
 * @since 2020-09-24
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "CustomerAccountResDTO", description = "客户账户信息")
@AllArgsConstructor
public class CustomerAccountResDTO extends Entity<Long> {

    private static final long serialVersionUID = 1L;
    /**
     * 账户号
     */
    @ApiModelProperty(value = "账户号")
    private String accountCode;

    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    private String customerCode;

    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    private String customerName;

    /**
     * 客户电话
     */
    @ApiModelProperty(value = "客户电话")
    private String phone;


    /**
     * 身份证号
     */
    @ApiModelProperty(value = "身份证号")
    private String identityCardNo;


    /**
     * 家庭住址
     */
    @ApiModelProperty(value = "家庭住址")
    private String address;

    /**
     * 账户金额
     */
    @ApiModelProperty(value = "账户金额")
    private BigDecimal accountMoney;






}
