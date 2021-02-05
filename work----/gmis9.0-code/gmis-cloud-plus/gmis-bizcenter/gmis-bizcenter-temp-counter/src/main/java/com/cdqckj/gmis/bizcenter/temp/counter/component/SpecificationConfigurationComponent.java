package com.cdqckj.gmis.bizcenter.temp.counter.component;

import cn.hutool.core.util.ObjectUtil;
import com.cdqckj.gmis.archive.GasMeterBizApi;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.device.GasMeterFactoryBizApi;
import com.cdqckj.gmis.device.GasMeterModelBizApi;
import com.cdqckj.gmis.device.GasMeterVersionBizApi;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.gasmeter.entity.GasMeterFactory;
import com.cdqckj.gmis.gasmeter.entity.GasMeterModel;
import com.cdqckj.gmis.gasmeter.entity.GasMeterVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author songyz
 */
@Component
public class SpecificationConfigurationComponent {
    @Autowired
    private GasMeterFactoryBizApi gasMeterFactoryBizApi;
    @Autowired
    private GasMeterVersionBizApi gasMeterVersionBizApi;
    @Autowired
    private GasMeterModelBizApi gasMeterModelBizApi;
    @Autowired
    private GasMeterBizApi gasMeterBizApi;

    /**
     * 通过厂家名称获取厂家信息
     * @param gasMeterFactoryName
     * @return
     */
    public GasMeterFactory getGasMeterFactoryByName(String gasMeterFactoryName){
        //厂家
        GasMeterFactory gasMeterFactoryParam = GasMeterFactory
                .builder()
                .gasMeterFactoryName(gasMeterFactoryName)
                .build();
        R<GasMeterFactory> gasMeterFactoryR = gasMeterFactoryBizApi.queryFactoryByName(gasMeterFactoryParam);
        if(gasMeterFactoryR.getIsError()){
            throw new BizException("查询厂家信息异常");
        }
        if(ObjectUtil.isNull(gasMeterFactoryR.getData())){
            throw new BizException("没有厂家信息");
        }
        return gasMeterFactoryR.getData();
    }
    /**
     * 通过版号名称获取版号信息
     * @param gasMeterVersionName
     * @param fachtoryId
     * @return
     */
    public GasMeterVersion getGasMeterVersionByName(Long fachtoryId, String gasMeterVersionName){
        GasMeterVersion gasMeterVersionParam = GasMeterVersion
                .builder()
                .companyId(fachtoryId)
                .gasMeterVersionName(gasMeterVersionName)
                .build();
        R<GasMeterVersion> gasMeterVersionR = gasMeterVersionBizApi.queryVersion(gasMeterVersionParam);
        if(gasMeterVersionR.getIsError()){
            throw new BizException("查询版号信息异常");
        }
        if(ObjectUtil.isNull(gasMeterVersionR.getData())){
            throw new BizException("没有版号信息");
        }
        return  gasMeterVersionR.getData();
    }
    /**
     * 通过型号名称获取型号信息
     * @param modelName
     * @param fachtoryId
     * @param versionId
     * @return
     */
    public GasMeterModel getGasMeterModelByName(Long fachtoryId, Long versionId, String modelName){
        GasMeterModel gasMeterModelParam = GasMeterModel
                .builder()
                .companyId(fachtoryId)
                .gasMeterVersionId(versionId)
                .modelName(modelName)
                .build();
        R<List<GasMeterModel>> gasMeterModelListR = gasMeterModelBizApi.query(gasMeterModelParam);
        if(gasMeterModelListR.getIsError()){
            throw new BizException("查询型号信息异常");
        }
        if(gasMeterModelListR.getData().size() == 0
                || ObjectUtil.isNull(gasMeterModelListR.getData())){
            throw new BizException("没有型号信息");
        }
        if (gasMeterModelListR.getData().size() > 1) {
            throw new BizException("同一厂家、版号下有重复型号信息");
        }
        return  gasMeterModelListR.getData().get(0);
    }
    /**
     * 通过表身号名称获取表具信息
     * @param bodyNumber
     * @param fachtoryId
     * @param versionId
     * @param modelId
     * @return
     */
    public GasMeter getGasMeterByBodyNo(Long fachtoryId, Long versionId, Long modelId, String bodyNumber){
        GasMeter gasMeterParam = GasMeter
                .builder()
                .gasMeterFactoryId(fachtoryId)
                .gasMeterVersionId(versionId)
                .gasMeterModelId(modelId)
                .gasMeterNumber(bodyNumber)
                .build();
        R<List<GasMeter>> gasMeterListR = gasMeterBizApi.query(gasMeterParam);
        if(gasMeterListR.getIsError()){
            throw new BizException("查询表具信息异常");
        }
        if(gasMeterListR.getData().size() == 0
                || ObjectUtil.isNull(gasMeterListR.getData())){
            throw new BizException("没有表具信息");
        }
        if (gasMeterListR.getData().size() > 1) {
            throw new BizException("同一厂家、版号、型号、表身号有重复表具信息");
        }
        return  gasMeterListR.getData().get(0);
    }
}
