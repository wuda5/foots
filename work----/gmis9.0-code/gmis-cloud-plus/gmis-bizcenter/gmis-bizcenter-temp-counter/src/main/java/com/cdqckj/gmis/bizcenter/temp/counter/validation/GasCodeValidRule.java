package com.cdqckj.gmis.bizcenter.temp.counter.validation;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cdqckj.gmis.archive.CustomerGasMeterRelatedBizApi;
import com.cdqckj.gmis.archive.GasMeterBizApi;
import com.cdqckj.gmis.common.domain.excell.ValidResult;
import com.cdqckj.gmis.common.domain.excell.rule.ValidRule;
import com.cdqckj.gmis.devicearchive.dto.GasMeterUpdateDTO;
import io.swagger.annotations.ApiModelProperty;

public class GasCodeValidRule implements ValidRule {

    @ApiModelProperty(value = "验证的类型")
    private GasMeterBizApi gasMeterBizApi;
    @ApiModelProperty(value = "验证的类型")
    private CustomerGasMeterRelatedBizApi customerGasMeterRelatedBizApi;

    @ApiModelProperty(value = "失败的说明")
    private String validDesc;

    @ApiModelProperty(value = "空的说明")
    private String emptyDesc;

    public GasCodeValidRule(GasMeterBizApi gasMeterBizApi, CustomerGasMeterRelatedBizApi customerGasMeterRelatedBizApi, String validDesc, String emptyDesc) {
        this.gasMeterBizApi = gasMeterBizApi;
        this.customerGasMeterRelatedBizApi = customerGasMeterRelatedBizApi;
        this.validDesc = validDesc;
        this.emptyDesc = emptyDesc;
    }
    public GasCodeValidRule(GasMeterBizApi gasMeterBizApi, String validDesc) {
        this.gasMeterBizApi = gasMeterBizApi;
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
    public Integer validColStart() {
        return 6;
    }

    @Override
    public Integer validColSum() {
        return 1;
    }

    @Override
    public String validEmptyFailDesc() {
        return validDesc;
    }

    @Override
    public void validProcess(ValidResult validResult) {

        String data = validResult.getColDataList().get(0);
        if(StringUtils.isNotBlank(data)) {
            new GasMeterUpdateDTO();
            GasMeterUpdateDTO gasMeterUpdateDTO = GasMeterUpdateDTO
                    .builder()
                    .gasCode(data)
                    .build();
            Boolean existsGasMeter = gasMeterBizApi.checkByGasCode(gasMeterUpdateDTO).getData();
//            new CustomerGasMeterRelatedUpdateDTO();
//            CustomerGasMeterRelatedUpdateDTO customerGasMeterRelatedUpdateDTO = CustomerGasMeterRelatedUpdateDTO
//                    .builder()
//                    .gasMeterCode(data)
//                    .build();
//            Boolean existsRelation = customerGasMeterRelatedBizApi.check(customerGasMeterRelatedUpdateDTO).getData();
            if (!existsGasMeter) {
                validResult.setStatus(false);
                validResult.setInvalidDesc(validDesc);
            }
        }
    }
}
