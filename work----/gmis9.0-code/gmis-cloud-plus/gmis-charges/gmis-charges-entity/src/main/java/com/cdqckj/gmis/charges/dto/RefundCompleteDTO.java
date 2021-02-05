package com.cdqckj.gmis.charges.dto;

import com.cdqckj.gmis.charges.entity.ChargeRecord;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.devicearchive.entity.GasMeterInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;

/**
 * <p>
 * 第三方支付退费封装
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
@ApiModel(value = "ThirdPayRefundDTO", description = "第三方支付退费封装")
public class RefundCompleteDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "收费编号")
    private String  chargeNo;

    @ApiModelProperty(value = "退费单号")
    private String  refundNo;

    /**
     * 返回微信的信息
     */
    private Map<String,String> returnXmlData;
    //当前租户
    private String code;
    /**
     * 第三方退款状态
     */
    private Boolean refundStatus;
    /**
     * 第三方退款描述
     */
    private String refundRemark;

    @ApiModelProperty(value = "是否轮询请求")
    private  Boolean isLoopRequest;

    @ApiModelProperty(value = "支付失败原因")
    private  String payErrReason;

    @ApiModelProperty(value = "是否需要卡退费")
    private Boolean isCardRefund;

}
