package com.cdqckj.gmis.charges.dto;

import com.cdqckj.gmis.charges.entity.ChargeRecord;
import com.cdqckj.gmis.devicearchive.entity.GasMeterInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 实体类
 * 缴费结果业务数据封装
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
@ApiModel(value = "ChargeDTO", description = "缴费")
public class ChargeResultDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "是否轮询请求")
    private  Boolean isLoopRequest;

    @ApiModelProperty(value = "是否等待用户支付")
    private  Boolean isWaiteUserPay;

    @ApiModelProperty(value = "支付失败原因")
    private  String payErrReason;

    @ApiModelProperty(value = "支付限时（秒）")
    private Long expireSecend;

    @ApiModelProperty(value = "缴费信息")
    private ChargeRecord chargeRecord;

    @ApiModelProperty(value = "场景收费记录IDS")
    private List<Long> sceneIds;

    @ApiModelProperty(value="抄表收费数据IDS")
    private List<Long> arrearIds;

    @ApiModelProperty(value="调价补差数据IDS")
    private List<Long> adjustPriceIds;

    @ApiModelProperty(value = "气表使用信息")
    private GasMeterInfo gasMeterInfo;
    @ApiModelProperty(value = "订单来源")
    private String orderSourceName;
    @ApiModelProperty(value="金额标识")
    private String amountMark;
    @ApiModelProperty(value = "第三方支付状态: SUCCESS FAIL UNKOWN")
    private  String thirdPayStatus;




//    @ApiModelProperty(value="收费项明细")
//    private List<ChargeItemRecord> chargeItemRecords;
//
//    @ApiModelProperty(value="收费项系统信息（开票配置等-待用）")
//    private List<TollItem> sysTollItems;

}
