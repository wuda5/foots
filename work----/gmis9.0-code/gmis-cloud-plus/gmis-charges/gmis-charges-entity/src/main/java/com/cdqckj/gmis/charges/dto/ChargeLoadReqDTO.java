package com.cdqckj.gmis.charges.dto;

import com.alibaba.fastjson.JSONObject;
import com.cdqckj.gmis.charges.entity.ChargeItemRecord;
import com.cdqckj.gmis.charges.entity.CustomerEnjoyActivityRecord;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 实体类
 * 缴费加载数据封装
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
@ApiModel(value = "ChargeLoadReqDTO", description = "缴费加载参数封装")
public class ChargeLoadReqDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "气表编号")
    @NotBlank
    private String gasMeterCode;
    @ApiModelProperty(value = "客户编号")
    @NotBlank
    private String customerCode;

    @ApiModelProperty(value = "读卡数据")
    private JSONObject readData;


}
