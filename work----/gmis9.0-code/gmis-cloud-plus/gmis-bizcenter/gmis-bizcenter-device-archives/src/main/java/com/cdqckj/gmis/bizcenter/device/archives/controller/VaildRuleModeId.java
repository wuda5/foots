package com.cdqckj.gmis.bizcenter.device.archives.controller;

import com.cdqckj.gmis.cache.repository.RedisService;
import com.cdqckj.gmis.common.domain.excell.ValidResult;
import com.cdqckj.gmis.common.domain.excell.rule.ValidRule;
import com.cdqckj.gmis.common.utils.CacheKeyUtil;
import com.cdqckj.gmis.device.GasMeterModelBizApi;
import com.cdqckj.gmis.device.GasMeterVersionBizApi;
import com.cdqckj.gmis.gasmeter.entity.GasMeterModel;
import com.cdqckj.gmis.gasmeter.entity.GasMeterVersion;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class VaildRuleModeId implements ValidRule {

    @Autowired
    private GasMeterVersionBizApi gasMeterVersionBizApi;

    @Autowired
    private GasMeterModelBizApi gasMeterModelBizApi;


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
        return 3;
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
        String versionNmae = (String) validResult.getColDataList().get(1);
        String modeName = (String) validResult.getColDataList().get(2);
        String key= CacheKeyUtil.createTenantKey("Ua","Md", versionNmae, modeName);
        Object status = redisService.get(key);
        if(status==null){
            GasMeterVersion gasMeterVersion=new GasMeterVersion();
            gasMeterVersion.setGasMeterVersionName(versionNmae);
            List<GasMeterVersion> data = gasMeterVersionBizApi.query(gasMeterVersion).getData();
            gasMeterVersion = data.get(0);
            GasMeterModel gasMeterModel=new GasMeterModel();
            gasMeterModel.setModelName(modeName);
            gasMeterModel.setGasMeterVersionId(gasMeterVersion.getId());
            List<GasMeterModel> gasMeterModels = gasMeterModelBizApi.query(gasMeterModel).getData();
            if(gasMeterModels.size()==0){
                status = false;
                redisService.set(key, status,1800L);
            }else {
                status = true;
                redisService.set(key, status,1800L);
            }
        }
        if((Boolean) status==false){
            validResult.setStatus(false);
            validResult.setInvalidDesc(validDesc);
        }
    }

    public VaildRuleModeId(GasMeterVersionBizApi gasMeterVersionBizApi, GasMeterModelBizApi gasMeterModelBizApi, RedisService redisService,String validDesc, String emptyDesc){
        this.gasMeterModelBizApi=gasMeterModelBizApi;
        this.gasMeterVersionBizApi=gasMeterVersionBizApi;
        this.validDesc=validDesc;
        this.emptyDesc=emptyDesc;
        this.redisService=redisService;
    }
}
