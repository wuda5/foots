package com.cdqckj.gmis.bizcenter.device.archives.controller;

import com.cdqckj.gmis.cache.repository.RedisService;
import com.cdqckj.gmis.common.domain.excell.ValidResult;
import com.cdqckj.gmis.common.domain.excell.rule.ValidRule;
import com.cdqckj.gmis.common.utils.CacheKeyUtil;
import com.cdqckj.gmis.device.GasMeterFactoryBizApi;
import com.cdqckj.gmis.gasmeter.entity.GasMeterFactory;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class VaildRuleFactoryId implements ValidRule {
    @ApiModelProperty(value = "厂家")
    private GasMeterFactoryBizApi gasMeterFactoryBizApi;


    @ApiModelProperty(value = "失败的说明")
    private String validDesc;

    @ApiModelProperty(value = "空的说明")
    private String emptyDesc;


    private RedisService redisService;

    @Override
    public Integer validColStart() {
        return 0;
    }

    @Override
    public Integer validColSum() {
        return 1;
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
        return emptyDesc;
    }

    public VaildRuleFactoryId(GasMeterFactoryBizApi gasMeterFactoryBizApi,RedisService redisService, String validDesc, String emptyDesc ) {

        this.gasMeterFactoryBizApi=gasMeterFactoryBizApi;
        this.validDesc = validDesc;
        this.emptyDesc = emptyDesc;
        this.redisService=redisService;
    }

    @Override
    public void validProcess(ValidResult validResult) {
        String data1 = (String) validResult.getColDataList().get(0);
        String key= CacheKeyUtil.createTenantKey("Ua","Ft", data1);
        Object status = redisService.get(key);
        if (status == null) {
            List<GasMeterFactory> data = gasMeterFactoryBizApi.query(new GasMeterFactory().setGasMeterFactoryName(data1)).getData();
            if (data.size() == 0) {
                status = false;
                redisService.set(key, status);
            }else {
                status = true;
                redisService.set(key, status);
            }
        }

        if((Boolean) status==false){
            validResult.setStatus(false);
            validResult.setInvalidDesc(validDesc);
        }
    }
}
