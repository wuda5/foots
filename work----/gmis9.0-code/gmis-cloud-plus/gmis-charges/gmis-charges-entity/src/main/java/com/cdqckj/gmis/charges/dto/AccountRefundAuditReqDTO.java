package com.cdqckj.gmis.charges.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 实体类
 * 退费审核请求参数信息
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
@ApiModel(value = "RefundAuditSaveReqDTO", description = "退费审核请求参数信息")
public class AccountRefundAuditReqDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "退费ID")
    @NotNull
    private Long refundId;
    @ApiModelProperty(value = "审核状态")
    private Boolean status;
    @ApiModelProperty(value = "审核意见")
    @Length(max = 300)
    private String  auditOpinion;

}
