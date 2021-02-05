package com.cdqckj.gmis.devicearchive.vo;

import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "MeterInfoVO", description = "")
public class MeterInfoVO implements Serializable {
    /**
     * 新价格方案id
     */
    private Long priceSchemeNewId;
    /**
     * 旧价格方案id
     */
    private Long priceSchemeOldId;
}
