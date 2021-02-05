package com.cdqckj.gmis.charges.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 实体类
 * 客户账户列表请求实体
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
@ApiModel(value = "CustomerAccountListReqDTO", description = "客户账户列表请求实体")
public class CustomerAccountListReqDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "电话")
    private String phone;
    @ApiModelProperty(value = "证件号")
    private String identityCardNo;
    @ApiModelProperty(value = "客户名称")
    private String customerName;

    private List<Long> orgIds;
//
//    @ApiModelProperty(value = "客户编码（前端不用传）")
//    private List<String> customerCodes;



}
