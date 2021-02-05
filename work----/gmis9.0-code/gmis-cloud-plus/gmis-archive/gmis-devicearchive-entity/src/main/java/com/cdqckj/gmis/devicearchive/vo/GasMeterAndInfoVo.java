package com.cdqckj.gmis.devicearchive.vo;

import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.devicearchive.entity.GasMeterInfo;
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
@ApiModel(value = "GasMeterAndInfoVo", description = "")
public class GasMeterAndInfoVo {
    private PageGasMeter pageGasMeter;
    private GasMeterInfo gasMeterInfo;
}
