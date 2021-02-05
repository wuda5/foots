package com.cdqckj.gmis.bizcenter.temp.counter.validation;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cdqckj.gmis.archive.CustomerGasMeterRelatedBizApi;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.common.domain.excell.ValidResult;
import com.cdqckj.gmis.common.domain.excell.rule.ValidRule;
import com.cdqckj.gmis.devicearchive.dto.CustomerGasMeterRelatedUpdateDTO;
import com.cdqckj.gmis.exception.BizException;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author songyz
 */
public class CustomerChargeNoVaildRule implements ValidRule {


    @ApiModelProperty(value = "验证的类型")
    private CustomerGasMeterRelatedBizApi customerGasMeterRelatedBizApi;

    @ApiModelProperty(value = "失败的说明")
    private String validDesc;

    public CustomerChargeNoVaildRule(CustomerGasMeterRelatedBizApi customerGasMeterRelatedBizApi, String validDesc) {
        this.customerGasMeterRelatedBizApi = customerGasMeterRelatedBizApi;
        this.validDesc = validDesc;
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
    public String validEmptyFailDesc() {
        return null;
    }

    @Override
    public Integer validColStart() {
        return 7;
    }

    @Override
    public Integer validColSum() {
        return 1;
    }

    @Override
    public void validProcess(ValidResult validResult) {
        String data = validResult.getColDataList().get(0);
        if (StringUtils.isNotBlank(data)) {
            new CustomerGasMeterRelatedUpdateDTO();
            CustomerGasMeterRelatedUpdateDTO customerGasMeterRelatedUpdateDTO = CustomerGasMeterRelatedUpdateDTO
                    .builder()
                    .customerChargeNo(data)
                    .build();
            R<Boolean> checkChargeNoR = customerGasMeterRelatedBizApi.checkChargeNo(customerGasMeterRelatedUpdateDTO);
            if (checkChargeNoR.getIsError()) {
                throw new BizException("校验缴费编号异常");
            }
            if (checkChargeNoR.getData()) {
                validResult.setStatus(false);
                validResult.setInvalidDesc(validDesc);
            }
        }
    }

}
