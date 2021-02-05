package com.cdqckj.gmis.bizcenter.securitycheckrecord.controller;

import com.cdqckj.gmis.operation.dto.OrderJobFileSaveDTO;
import com.cdqckj.gmis.securityed.dto.SecurityCheckItemsSaveDTO;
import com.cdqckj.gmis.securityed.dto.SecurityCheckResultSaveDTO;
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
@ApiModel(value = "ResultVo", description = "录入安检接果参数")
public class ResultVo {
    SecurityCheckResultSaveDTO params;

    List<SecurityCheckItemsSaveDTO> securityCheckItemsSaveDTOS;
}
