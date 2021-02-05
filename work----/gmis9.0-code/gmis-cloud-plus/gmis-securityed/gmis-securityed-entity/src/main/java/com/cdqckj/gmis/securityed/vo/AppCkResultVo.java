package com.cdqckj.gmis.securityed.vo;

import com.cdqckj.gmis.securityed.dto.SecurityCheckItemsSaveDTO;
import com.cdqckj.gmis.securityed.dto.SecurityCheckResultSaveDTO;
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
@ApiModel(value = "AppCkResultVo", description = "app录入安检接果参数")
public class AppCkResultVo {
    SecurityCheckResultUpdateDTO scResultDto;

    List<SecurityCheckItemsSaveDTO> scItemsSaveDtoS;
}
