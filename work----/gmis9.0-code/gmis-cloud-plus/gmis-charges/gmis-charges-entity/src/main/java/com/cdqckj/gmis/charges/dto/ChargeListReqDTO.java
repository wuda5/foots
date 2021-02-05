package com.cdqckj.gmis.charges.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 * 实体类
 * 收费列表查询参数信息
 * </p>
 *
 * @author tp
 * @since 2020-08-25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "ChargeListRequestDTO", description = "收费列表查询参数信息")
public class ChargeListReqDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "客户缴费编号")
    private String customerChargeNo;
    @ApiModelProperty(value = "缴费单号")
    private String chargeNo;
    @ApiModelProperty(value = "客户编码")
    private String customerCode;

    @ApiModelProperty(value = "客户姓名")
    private String customerName;

    @ApiModelProperty(value = "表具编码")
    private String gasMeterCode;

    @ApiModelProperty(value = "收费开始日期")
    private LocalDate start;

    @ApiModelProperty(value = "收费截止日期")
    private LocalDate end;



}
