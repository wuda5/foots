package com.cdqckj.gmis.charges.dto;

import com.cdqckj.gmis.charges.entity.AccountRefund;
import com.cdqckj.gmis.charges.entity.ChargeRecord;
import com.cdqckj.gmis.charges.entity.ChargeRefund;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 实体类
 * 退费结果业务数据封装
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
@ApiModel(value = "RefundResultDTO", description = "退费")
public class AccountRefundResultDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "退费信息")
    private AccountRefund accountRefund;


}
