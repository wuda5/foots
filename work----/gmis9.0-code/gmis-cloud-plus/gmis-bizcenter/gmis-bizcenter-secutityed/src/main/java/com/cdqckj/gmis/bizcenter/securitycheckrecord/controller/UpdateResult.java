package com.cdqckj.gmis.bizcenter.securitycheckrecord.controller;

import com.cdqckj.gmis.securityed.dto.SecurityCheckItemsUpdateDTO;
import com.cdqckj.gmis.securityed.dto.SecurityCheckResultUpdateDTO;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "UpdateResult", description = "修改安检接果参数")
public class UpdateResult {

    SecurityCheckResultUpdateDTO params;

    List<SecurityCheckItemsUpdateDTO> securityCheckItemsUpdateDTOS;
}
