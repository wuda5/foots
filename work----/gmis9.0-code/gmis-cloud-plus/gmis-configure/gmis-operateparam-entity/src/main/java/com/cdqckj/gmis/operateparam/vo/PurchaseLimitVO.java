package com.cdqckj.gmis.operateparam.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author ASUS
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "限购信息查询对象", description = "限购信息查询对象")
public class PurchaseLimitVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户编号")
    @NonNull
    private String customerCode;

    @ApiModelProperty(value = "气表编号")
    private String meterCode;
}
