package com.cdqckj.gmis.charges.dto;

import com.cdqckj.gmis.charges.entity.ChargeRecord;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.devicearchive.entity.GasMeterInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Map;

/**
 * <p>
 * 实体类
 * 缴费完成回调数据封装
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
@ApiModel(value = "ChargeCompleteDTO", description = "缴费完成回调")
public class ChargeCompleteDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "收费编号")
    @NotBlank
    private String  chargeNo;

    @ApiModelProperty(value = "收费状态")
    private Boolean  chargeStatus;

    @ApiModelProperty(value = "备注描述")
    private String remark;

    @ApiModelProperty(value = "收费记录,前端不用传")
    private ChargeRecord record;

    @ApiModelProperty(value = "气表使用信息，前端不用传")
    private GasMeterInfo gasMeterInfo;
    @ApiModelProperty(value = "气表使用信息，前端不用传")
    private GasMeter meter;
    /**
     * 返回微信的信息
     */
    private String returnXmlData;
    //当前租户
    private String code;

    private Map<String,String> wxQueryData;

}
