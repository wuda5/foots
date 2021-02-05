package com.cdqckj.gmis.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @auther hc
 */
@Data
@ApiModel(value = "GasMeterBindDTO",description = "代缴气表绑定实体")
public class GasMeterBindDTO {

    @ApiModelProperty(value = "开放密匙",required = true)
    @NotNull
    @NotEmpty
    private String open_token;


    @ApiModelProperty(value = "燃气缴费编号集:有数据及同步新增,无就是获取",required = true)
    private List<String> meterChargeNos;

}
