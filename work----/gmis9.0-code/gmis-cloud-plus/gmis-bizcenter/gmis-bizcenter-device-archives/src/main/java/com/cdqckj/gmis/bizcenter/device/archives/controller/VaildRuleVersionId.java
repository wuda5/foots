package com.cdqckj.gmis.bizcenter.device.archives.controller;

import com.cdqckj.gmis.cache.repository.RedisService;
import com.cdqckj.gmis.common.domain.excell.ValidResult;
import com.cdqckj.gmis.common.domain.excell.rule.ValidRule;
import com.cdqckj.gmis.common.utils.CacheKeyUtil;
import com.cdqckj.gmis.device.GasMeterFactoryBizApi;
import com.cdqckj.gmis.device.GasMeterVersionBizApi;
import com.cdqckj.gmis.gasmeter.entity.GasMeterFactory;
import com.cdqckj.gmis.gasmeter.entity.GasMeterVersion;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@Slf4j
public class VaildRuleVersionId implements ValidRule {

    @ApiModelProperty(value = "厂家")
    private GasMeterFactoryBizApi gasMeterFactoryBizApi;

    @Autowired
    private GasMeterVersionBizApi gasMeterVersionBizApi;

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
        return 2;
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

    @Override
    public void validProcess(ValidResult validResult) {
        String factoryName = (String) validResult.getColDataList().get(0);
        String versionName = (String) validResult.getColDataList().get(1);
        String key= CacheKeyUtil.createTenantKey("Ua","Ve", factoryName, versionName);
        Object status = redisService.get(key);
        if (status == null) {
            List<GasMeterFactory> data = gasMeterFactoryBizApi.query(new GasMeterFactory().setGasMeterFactoryName(factoryName)).getData();
            GasMeterFactory gasMeterFactory = data.get(0);
            List<GasMeterVersion> gasMeterVersions = gasMeterVersionBizApi.query(new GasMeterVersion().setCompanyId(gasMeterFactory.getId()).setGasMeterVersionName(versionName)).getData();
            if (gasMeterVersions.size() == 0){
                status = false;
                redisService.set(key, status,1800L);
            }else {
                status = true;
                redisService.set(key, status,1800L);
            }
        }
        log.info("factoryName:{} versionName={} ",factoryName,versionName);
        if((Boolean) status==false){
            validResult.setStatus(false);
            validResult.setInvalidDesc(validDesc);
        }
    }

    public VaildRuleVersionId( GasMeterFactoryBizApi gasMeterFactoryBizApi, GasMeterVersionBizApi gasMeterVersionBizApi,RedisService redisService,String validDesc,String emptyDesc){
        this.gasMeterFactoryBizApi=gasMeterFactoryBizApi;
        this.gasMeterVersionBizApi=gasMeterVersionBizApi;
        this.validDesc=validDesc;
        this.emptyDesc=emptyDesc;
        this.redisService=redisService;
    }
}
