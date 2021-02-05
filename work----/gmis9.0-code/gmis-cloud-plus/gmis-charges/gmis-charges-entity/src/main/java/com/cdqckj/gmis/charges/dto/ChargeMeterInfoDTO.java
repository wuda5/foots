package com.cdqckj.gmis.charges.dto;

import com.cdqckj.gmis.charges.entity.ChargeItemRecord;
import com.cdqckj.gmis.charges.entity.ChargeRecord;
import com.cdqckj.gmis.charges.entity.CustomerEnjoyActivityRecord;
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
public class ChargeMeterInfoDTO implements Serializable {

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

    @ApiModelProperty(value = "是否发卡缴费")
    private Boolean isSendCard;

//    @ApiModelProperty(value = "场景类型")
//    private String sceneType;
//
//    @ApiModelProperty(value = "场景业务编号或ID")
//    private String bizNoOrId;

    @ApiModelProperty(value="选择的优惠活动")
    private List<CustomerEnjoyActivityRecord> activityList;

}
