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
 * 账户退费列表请求实体
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
@ApiModel(value = "AccountRefundListReqDTO", description = "账户退费列表请求实体")
public class AccountRefundListReqDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "电话")
    private String phone;
    @ApiModelProperty(value = "证件号")
    private String identityCardNo;
    @ApiModelProperty(value = "客户名称")
    private String customerName;
    @ApiModelProperty(value = "审核状态: WAITE_AUDIT待审核，AUDITED 已审核 ，REFUNDABLE 待退费，" +
            " REFUND_ERR 退费失败, AUDITED 已审核 ，REFUNDED 退费完成")
    private String auditStatus;
//    @ApiModelProperty(value = "客户编码，前端不用传")
//    private List<String> customerCodes;


    @ApiModelProperty(value = "申请开始日期")
    private LocalDate start;

    @ApiModelProperty(value = "申请截止日期")
    private LocalDate end;


    private List<Long> orgIds;



}
