package com.cdqckj.gmis.charges.dto;

import com.cdqckj.gmis.charges.entity.ChargeItemRecord;
import com.cdqckj.gmis.charges.entity.ChargeRecord;
import com.cdqckj.gmis.charges.entity.CustomerEnjoyActivityRecord;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.devicearchive.entity.GasMeterInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 实体类
 * 缴费数据封装
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
public class ChargeDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "收费项")
    private List<ChargeItemRecord> itemList;

    @ApiModelProperty(value = "表具编码")
    @NotBlank
    private String  gasMeterCode;

    @ApiModelProperty(value = "客户编码")
    @NotBlank
    private String  customerCode;

    @ApiModelProperty(value = "是否使用抵扣")
    private Boolean useAccountDec;

    @ApiModelProperty(value = "缴费对象")
    @Valid
    private ChargeRecord chargeRecord;

/*    @ApiModelProperty(value = "是否发卡缴费")
    private Boolean isSendCard;*/


    @ApiModelProperty(value = "场景类型")
    private String scene;


    @ApiModelProperty(value = "换算类型 MONEY GAS ")
    private String ctype;

    @ApiModelProperty(value = "biz ID")
    private Long bizNoOrId;

    @ApiModelProperty(value = "换表老表编码")
    private Long oldGasMeterCode;

//    @ApiModelProperty(value = "场景类型")
//    private String sceneType;
//
//    @ApiModelProperty(value = "场景业务编号或ID")
//    private String bizNoOrId;

    @ApiModelProperty(value="选择的优惠活动")
    private List<CustomerEnjoyActivityRecord> activityList;


    @ApiModelProperty(value = "欠费ID")
    private List<Long>  arrearsDetailIds;

    @ApiModelProperty(value = "气表使用信息，前端不用传")
    private GasMeter meter;

    @ApiModelProperty(value = "气表使用信息，前端不用传")
    private GasMeterInfo gasMeterInfo;

    @ApiModelProperty(value = "扫码支付付款码")
    private String authCode;

    @ApiModelProperty(value = "测试超时")
    private Integer testExpireSecend;

//    /**
//     * 终端支付开始时间， 如果终端通过获取到响应开始轮询，服务器返回应该轮询几分钟，几分钟后就终止轮询 。如果设置5分钟过期，前端会轮询5分钟
//     */
//    private String timeStart;
//    /**
//     * 支付过期时间 如果设置5分钟过期，前端会轮询5分钟
//     */
//    private String timeExpire;

//    {
//        "authCode": "134736873380268569",
//            "productId": "66666666",
//            "body": "吃啥",
//            "detail": "订单详情",
//            "orderNumber": "66555441258",
//            "pay": 0.01,
//            "timeStart":"20210108161311",
//            "timeExpire":"20210108161411"
//    }

}
