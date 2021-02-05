package com.cdqckj.gmis.bizcenter.device.archives.service.impl;

import com.cdqckj.gmis.base.MessageConstants;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperCenterServiceImpl;
import com.cdqckj.gmis.bizcenter.device.archives.service.GasMeterFactoryService;
import com.cdqckj.gmis.device.GasMeterFactoryBizApi;
import com.cdqckj.gmis.device.GasMeterModelBizApi;
import com.cdqckj.gmis.device.GasMeterVersionBizApi;
import com.cdqckj.gmis.gasmeter.dto.GasMeterFactorySaveDTO;
import com.cdqckj.gmis.gasmeter.dto.GasMeterFactoryUpdateDTO;
import com.cdqckj.gmis.gasmeter.dto.GasMeterModelUpdateDTO;
import com.cdqckj.gmis.gasmeter.dto.GasMeterVersionUpdateDTO;
import com.cdqckj.gmis.gasmeter.entity.GasMeterFactory;
import com.cdqckj.gmis.gasmeter.entity.GasMeterModel;
import com.cdqckj.gmis.gasmeter.entity.GasMeterVersion;
import com.cdqckj.gmis.utils.BeanPlusUtil;
import com.cdqckj.gmis.utils.I18nUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("gasMeterFactoryService")
public class GasMeterFactoryServiceImpl extends SuperCenterServiceImpl implements GasMeterFactoryService {

    @Autowired
    private GasMeterFactoryBizApi gasMeterFactoryBizApi;
    @Autowired
    private GasMeterVersionBizApi gasMeterVersionBizApi;
    @Autowired
    private GasMeterModelBizApi gasMeterModelBizApi;

    @Autowired
    I18nUtil i18nUtil;
    @Override
    public R<GasMeterFactory> saveGasMeter(GasMeterFactorySaveDTO gasMeterFactorySaveDTO) {
        GasMeterFactory gasMeterFactory = BeanPlusUtil.toBean(gasMeterFactorySaveDTO,GasMeterFactory.class);
        Boolean isRepeat = gasMeterFactoryBizApi.queryGasMeter(gasMeterFactory).getData();
        if(!isRepeat)  return gasMeterFactoryBizApi.save(gasMeterFactorySaveDTO);
        return R.fail(i18nUtil.getMessage(MessageConstants.REPEAT_GAS_FACTORY_INFO));
    }
    @Override
    public R<GasMeterFactory> updateGasMeter(GasMeterFactoryUpdateDTO gasMeterFactoryUpdateDTO) {
        GasMeterFactory gasMeterFactory = BeanPlusUtil.toBean(gasMeterFactoryUpdateDTO,GasMeterFactory.class);
        Boolean isRepeat =gasMeterFactoryBizApi.queryGasMeter(gasMeterFactory).getData();
        //校验厂家名称、编码和地址
        if(!isRepeat){
            //版号联级
            if (versionCheck(gasMeterFactory))
                return R.fail(getLangMessage(MessageConstants.SELECT_FAIL));

            //型号联级
            modelCheck(gasMeterFactory);
            return gasMeterFactoryBizApi.update(gasMeterFactoryUpdateDTO);
        }

        return R.fail(i18nUtil.getMessage(MessageConstants.REPEAT_GAS_FACTORY_INFO));
    }

    // 批量禁用或 启用 新气表厂家信息
    @Override
    public R<Boolean> updateBatchGasMeter(List<GasMeterFactoryUpdateDTO> gasMeterFactoryUpdateDTOList) {
        List<GasMeterFactory> gasMeterFactoryList = BeanPlusUtil
                .toBeanList(gasMeterFactoryUpdateDTOList, GasMeterFactory.class);
        // 如果是禁用，还需要 禁用此厂商下面的版号 和 型号xxx
        if (gasMeterFactoryUpdateDTOList.get(0).getGasMeterFactoryStatus() == 0) {
            List<Long> companyIds = gasMeterFactoryList.stream().map(h -> h.getId()).collect(Collectors.toList());
            gasMeterVersionBizApi.updateVersionStatusByFactoryIds(companyIds, 0);
            gasMeterModelBizApi.updateModelStatusByFactoryIds(companyIds, 0);
        }
//        for (GasMeterFactory gasMeter:gasMeterFactoryList) {
//            if (gasMeter.getGasMeterFactoryStatus()==0){
//                versionCheck(gasMeter);
//                modelCheck(gasMeter);
//            }
//        }
        return gasMeterFactoryBizApi.updateBatchById(gasMeterFactoryUpdateDTOList);
    }

    @Override
    public Boolean gasMeterFactoryCheck(GasMeterFactory gasMeterFactory) {
        return gasMeterFactoryBizApi.queryGasMeter(gasMeterFactory).getData();
    }

    private boolean versionCheck(GasMeterFactory gasMeterFactory) {
        GasMeterVersion gasMeterVersion=new GasMeterVersion();
        gasMeterVersion.setCompanyId(gasMeterFactory.getId());
        List<GasMeterVersion> versionList= gasMeterVersionBizApi.query(gasMeterVersion).getData();
        if (versionList!=null||versionList.size()!=0) {
            for (GasMeterVersion gasMeter : versionList) {
                if (!gasMeter.getCompanyName().equals(gasMeterFactory.getGasMeterFactoryName())) {
                    gasMeter.setCompanyName(gasMeterFactory.getGasMeterFactoryName());
                }
                if (gasMeterFactory.getGasMeterFactoryStatus() == 0) {
                    gasMeter.setVersionState(0);
                }
            }
            List<GasMeterVersionUpdateDTO> versionUpdateDTOList= BeanPlusUtil
                    .toBeanList(versionList,GasMeterVersionUpdateDTO.class);
            gasMeterVersionBizApi.updateBatchById(versionUpdateDTOList);
        }
        return false;
    }

    private void modelCheck(GasMeterFactory gasMeterFactory) {
        GasMeterModel gasMeterModel=new GasMeterModel();
        gasMeterModel.setCompanyId(gasMeterFactory.getId());
        List<GasMeterModel> modelList=gasMeterModelBizApi.query(gasMeterModel).getData();
        if (modelList!=null||modelList.size()!=0){
            for (GasMeterModel model:modelList) {
                if (!model.getCompanyName().equals(gasMeterFactory.getGasMeterFactoryName())){
                    model.setCompanyName(gasMeterFactory.getGasMeterFactoryName());
                }
                if (gasMeterFactory.getGasMeterFactoryStatus()==0){
                    model.setDataStatus(0);
                }
            }
            List<GasMeterModelUpdateDTO> modelUpdateDTOList= BeanPlusUtil.toBeanList(modelList,GasMeterModelUpdateDTO.class);
            gasMeterModelBizApi.updateBatchById(modelUpdateDTOList);
        }
    }


}