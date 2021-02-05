package com.cdqckj.gmis.charges.dto;

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
public class RefundResultDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "退费信息")
    private ChargeRefund chargeRefund;

    @ApiModelProperty(value = "退费信息")
    private ChargeRecord chargeRecord;

    @ApiModelProperty(value = "场景收费记录IDS")
    private List<Long> sceneIds;

    @ApiModelProperty(value="抄表收费数据IDS")
    private List<Long> arrearIds;

    @ApiModelProperty(value="调价补差数据IDS")
    private List<Long> adjIds;


    @ApiModelProperty(value = "补卡记录ID")
    private Long repCardId;


    @ApiModelProperty(value = "是否卡退气")
    private Boolean isCardRefund;

    @ApiModelProperty(value = "是否轮询请求")
    private  Boolean isLoopRequest;

    @ApiModelProperty(value = "支付失败原因")
    private  String payErrReason;

    @ApiModelProperty(value = "轮询间隔时长（秒）")
    private Long loopSecend=5000L;

}
