package com.cdqckj.gmis.bizcenter.temp.counter.validation;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cdqckj.gmis.archive.CustomerBizApi;
import com.cdqckj.gmis.common.domain.excell.ValidResult;
import com.cdqckj.gmis.common.domain.excell.rule.ValidRule;
import com.cdqckj.gmis.userarchive.dto.CustomerUpdateDTO;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author songyz
 */
public class CustomerCodeVaildRule implements ValidRule {


    @ApiModelProperty(value = "验证的类型")
    private CustomerBizApi customerBizApi;

    @ApiModelProperty(value = "失败的说明")
    private String validDesc;

    @ApiModelProperty(value = "空的说明")
    private String emptyDesc;

    public CustomerCodeVaildRule(CustomerBizApi customerBizApi, String validDesc, String emptyDesc) {
        this.customerBizApi = customerBizApi;
        this.validDesc = validDesc;
        this.emptyDesc = emptyDesc;
    }

    @Override
    public int validType() {
        return 0;
    }

    @Override
    public String validFailDesc() {
        return validDesc;
    }

    @Override
    public Integer validColStart() {
        return 0;
    }

    @Override
    public Integer validColSum() {
        return 1;
    }

    @Override
    public String validEmptyFailDesc() {
        return emptyDesc;
    }

    @Override
    public void validProcess(ValidResult validResult) {

        String data = validResult.getColDataList().get(0);
        if(StringUtils.isNotBlank(data)) {
            new CustomerUpdateDTO();
            CustomerUpdateDTO customerUpdateDTO = CustomerUpdateDTO
                    .builder()
                    .customerCode(data)
                    .build();
            Boolean o = customerBizApi.checkByCustomerCode(customerUpdateDTO).getData();
            if (o) {
                validResult.setStatus(false);
                validResult.setInvalidDesc(validDesc);
            }
        }

    }

}
