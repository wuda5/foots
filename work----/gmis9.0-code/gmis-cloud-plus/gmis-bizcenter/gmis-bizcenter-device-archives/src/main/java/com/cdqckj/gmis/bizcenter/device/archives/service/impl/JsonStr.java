package com.cdqckj.gmis.bizcenter.device.archives.service.impl;

import lombok.*;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
public class JsonStr {
    private String code;
    private String name;

}
