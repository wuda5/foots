package com.cdqckj.gmis.devicearchive.vo;

import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "BatchUserDeviceVO", description = "")
public class BatchUserDeviceVO implements Serializable {
    private String gasCode;
    private Integer dataStatus;
}
