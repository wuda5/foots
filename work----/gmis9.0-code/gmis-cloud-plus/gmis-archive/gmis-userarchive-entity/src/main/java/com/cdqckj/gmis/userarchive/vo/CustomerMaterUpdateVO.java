package com.cdqckj.gmis.userarchive.vo;

import com.cdqckj.gmis.devicearchive.dto.GasMeterUpdateDTO;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.userarchive.dto.CustomerUpdateDTO;
import com.cdqckj.gmis.userarchive.entity.Customer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "CustomerMaterUpdateVO", description = "客户与表具参数")
public class CustomerMaterUpdateVO implements Serializable {
    @ApiModelProperty(value = "客户")
    private Customer customer;
    @ApiModelProperty(value = "气表信息")
    private GasMeter gasMeter;
}
