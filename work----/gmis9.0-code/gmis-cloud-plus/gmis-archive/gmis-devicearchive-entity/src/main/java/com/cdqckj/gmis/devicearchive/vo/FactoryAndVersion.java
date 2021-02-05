package com.cdqckj.gmis.devicearchive.vo;

import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "FactoryAndVersion", description = "")
public class FactoryAndVersion {
    private Long gasMeterFactoryId;
    private String gasMeterNumber;
}
