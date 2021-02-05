package com.cdqckj.gmis.devicearchive.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.devicearchive.dto.GasMeterConfDTO;
import com.cdqckj.gmis.devicearchive.service.GasMeterConfService;
import org.springframework.stereotype.Service;

@Service
@DS("#thread.tenant")
public class GasMeterConfServiceImpl implements GasMeterConfService {

//    @Autowired
//    GasMeterVersionService gasMeterVersionService;
//
//
//    @Autowired
//    GasMeterService gasMeterService;
//
//    @Autowired
//    GasMeterFactoryService gasMeterFactoryService;
//
//    @Autowired
//    GasMeterModelService gasMeterModelService;
    /**
     * 根据档案号获取气表配置（厂家，版号，型号）信息
     *
     * @param gasMeterCode 档案号
     * @return 气表配置信息
     */
    public GasMeterConfDTO findGasMeterConfInfoByCode(String gasMeterCode){
//        GasMeterConfDTO result=new GasMeterConfDTO();
//        GasMeter meter=gasMeterService.findGasMeterByCode(gasMeterCode);
//        if(meter!=null){
//            result.setGasMeterCode(gasMeterCode);
//            result.setUseGasTypeId(meter.getUseGasTypeId());
//
//            GasMeterFactory factory=gasMeterFactoryService.getById(meter.getGasMeterFactoryId());
//
//            GasMeterVersion version=gasMeterVersionService.getById(meter.getGasMeterVersionId());
//            if(version!=null){
//                BeanPlusUtil.copyProperties(version,result);
//                result.setVersionId(version.getId());
//            }
//
//            GasMeterModel model=gasMeterModelService.getById(meter.getGasMeterModelId());
//            if(model!=null){
//                result.setModelId(model.getId());
//                result.setModelName(model.getModelName());
//                result.setSpecification(model.getSpecification());
//            }
//            if(factory!= null) {
//                result.setFactoryId(factory.getId());
//                result.setGasMeterFactoryCode(factory.getGasMeterFactoryCode());
//                result.setGasMeterFactoryName(factory.getGasMeterFactoryName());
//                result.setGasMeterFactoryMarkCode(factory.getGasMeterFactoryMarkCode());
//            }
//        }
//        return result;
        return null;
    }
}
