package com.cdqckj.gmis.readmeter.controller;

import com.cdqckj.gmis.common.domain.excell.ValidResult;
import com.cdqckj.gmis.common.domain.excell.rule.ValidRule;
import com.cdqckj.gmis.common.utils.BigDecimalUtils;
import com.cdqckj.gmis.readmeter.entity.ReadMeterLatestRecord;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Map;

public class ReadDataValueVaildRule implements ValidRule {

    @ApiModelProperty(value = "抄表时间")
    private String readTime;

    @ApiModelProperty(value = "气表最新止数")
    private Map<String, ReadMeterLatestRecord> LatestMap;

    @ApiModelProperty(value = "失败的说明")
    private String validDesc = "null";

    @ApiModelProperty(value = "空的说明")
    private String emptyDesc;

    public ReadDataValueVaildRule(String readTime, Map<String, ReadMeterLatestRecord> LatestMap) {
        this.readTime = readTime;
        this.LatestMap = LatestMap;
    }

    @Override
    public Integer validColStart() {
        return 0;
    }

    @Override
    public Integer validColSum() {
        return 7;
    }

    @Override
    public String validEmptyFailDesc() {
        return emptyDesc;
    }

    @Override
    public void validProcess(ValidResult validResult) {
        String val= (String) validResult.getColDataList().get(6);
        if(null!=val && !"".equals(val)){
            String code = (String) validResult.getColDataList().get(2);
            ReadMeterLatestRecord latestRecord = LatestMap.get(code);
            BigDecimal currentTotalGas = new BigDecimal(val);
            BigDecimal lastTotalGas = latestRecord.getCurrentTotalGas();
            Boolean currentTotalGasBool = currentTotalGas == null;
            if (!currentTotalGasBool && BigDecimalUtils.lessThan(currentTotalGas, lastTotalGas)) {
                validResult.setStatus(false);
                validResult.setInvalidDesc("本期止数不能小于上期止数");
            }
        }
    }

    @Override
    public int validType() {
        return 0;
    }

    @Override
    public String validFailDesc() {
        return validDesc;
    }

}
