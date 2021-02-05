package com.cdqckj.gmis.bizcenter.device.archives.controller;

import com.cdqckj.gmis.archive.GasMeterBizApi;
import com.cdqckj.gmis.cache.repository.RedisService;
import com.cdqckj.gmis.common.domain.excell.ValidResult;
import com.cdqckj.gmis.common.domain.excell.rule.ValidRule;
import com.cdqckj.gmis.common.utils.CacheKeyUtil;
import com.cdqckj.gmis.device.GasMeterFactoryBizApi;
import com.cdqckj.gmis.devicearchive.vo.FactoryAndVersion;
import com.cdqckj.gmis.gasmeter.entity.GasMeterFactory;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class VaildRuleGasId implements ValidRule {

    @ApiModelProperty(value = "验证的类型")
    private GasMeterBizApi gasMeterBizApi;


    @ApiModelProperty(value = "厂家")
    private GasMeterFactoryBizApi gasMeterFactoryBizApi;

    private RedisService redisService;

    @ApiModelProperty(value = "失败的说明")
    private String validDesc;

    @ApiModelProperty(value = "空的说明")
    private String emptyDesc;

    public VaildRuleGasId(GasMeterBizApi gasMeterBizApi,GasMeterFactoryBizApi gasMeterFactoryBizApi,RedisService redisService, String validDesc, String emptyDesc) {
        this.gasMeterBizApi = gasMeterBizApi;
        this.gasMeterFactoryBizApi=gasMeterFactoryBizApi;
        this.validDesc = validDesc;
        this.emptyDesc = emptyDesc;
        this.redisService=redisService;
    }

    @Override
    public Integer validColStart() {
        return 0;
    }

    @Override
    public Integer validColSum() {
        return 5;
    }

    @Override
    public String validEmptyFailDesc() {
        return emptyDesc;
    }

    @Override
    public void validProcess(ValidResult validResult) {
        String factoryName = validResult.getColDataList().get(0);
        String gasNumber = validResult.getColDataList().get(4);
        if(StringUtils.isEmpty(gasNumber)){
            return;
        }
        for (char c : gasNumber.toCharArray()) {
            if (c >= 0x4E00 &&  c <= 0x9FA5){
                validResult.setStatus(false);
                validResult.setInvalidDesc("表身号需要字母或数字！！！！！！！！");
                return;
            }
        }
        String key= CacheKeyUtil.createTenantKey("Ua","Gn", factoryName, gasNumber);
        Object status = redisService.get(key);
        if(status == null) {
            List<GasMeterFactory> factories = gasMeterFactoryBizApi.query(new GasMeterFactory().setGasMeterFactoryName(factoryName)).getData();
            FactoryAndVersion factoryAndVersion = new FactoryAndVersion(factories.get(0).getId(), gasNumber);
            Integer count = gasMeterBizApi.findGasMeterNumber(factoryAndVersion).getData();
            if (count > 0) {
                status = true;
            }else {
                status = false;
            }
        }
        if ((Boolean) status == true){
            validResult.setStatus(false);
            validResult.setInvalidDesc(validDesc);
        }
        redisService.set(key, true, 1800L);
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
