package com.cdqckj.gmis.bizcenter.temp.counter.entity;

import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.userarchive.entity.Customer;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstablishAccount {
    /**
     *客户信息
     */
    @ApiModelProperty(value = "客户信息")
    private Customer customer;
    /**
     *表具信息
     */
    @ApiModelProperty(value = "表具信息")
    private GasMeter gasMeter;
    /**
     * 缴费编号
     */
    @ApiModelProperty(value = "缴费编号")
    private String chargeNo;
}
