package com.cdqckj.gmis.bizcenter.device.archives.service.impl;

import com.cdqckj.gmis.base.MessageConstants;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperCenterServiceImpl;
import com.cdqckj.gmis.bizcenter.device.archives.service.GasMeterVersionService;
import com.cdqckj.gmis.device.GasMeterFactoryBizApi;
import com.cdqckj.gmis.device.GasMeterModelBizApi;
import com.cdqckj.gmis.device.GasMeterVersionBizApi;
import com.cdqckj.gmis.gasmeter.dto.GasMeterModelUpdateDTO;
import com.cdqckj.gmis.gasmeter.dto.GasMeterVersionSaveDTO;
import com.cdqckj.gmis.gasmeter.dto.GasMeterVersionUpdateDTO;
import com.cdqckj.gmis.gasmeter.entity.GasMeterFactory;
import com.cdqckj.gmis.gasmeter.entity.GasMeterModel;
import com.cdqckj.gmis.gasmeter.entity.GasMeterVersion;
import com.cdqckj.gmis.utils.BeanPlusUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("gasMeterVersionService")
public class GasMeterVersionServiceImpl extends SuperCenterServiceImpl implements GasMeterVersionService {

    @Autowired
    private GasMeterFactoryBizApi gasMeterFactoryBizApi;
    @Autowired
    private GasMeterVersionBizApi gasMeterVersionBizApi;
    @Autowired
    private GasMeterModelBizApi gasMeterModelBizApi;
    @Override
    public R<GasMeterVersion> saveGasMeterVersion(GasMeterVersionSaveDTO gasMeterVersionSaveDTO) {
        GasMeterVersion gasMeterVersion = BeanPlusUtil.toBean(gasMeterVersionSaveDTO,GasMeterVersion.class);
        Boolean isRepeat = gasMeterVersionBizApi.queryGasMeter(gasMeterVersion).getData();
        if(!isRepeat) {
            return gasMeterVersionBizApi.save(gasMeterVersionSaveDTO);
        }
        return R.fail(getLangMessage(MessageConstants.REPEAT_GAS_VERSION_NAME));
    }

    @Override
    public R<GasMeterVersion> updateGasMeterVersion(GasMeterVersionUpdateDTO gasMeterVersionUpdateDTO) {
        GasMeterVersion gasMeterVersion = BeanPlusUtil.toBean(gasMeterVersionUpdateDTO, GasMeterVersion.class);
        Boolean isRepeat = gasMeterVersionBizApi.queryGasMeter(gasMeterVersion).getData();
        //判断版号名称是否存在，若不存在
        if (!isRepeat) {
//            gasMeterVersionUpdateDTO.setVersionState(1);
            R<GasMeterVersion> x = updateCheck(gasMeterVersionUpdateDTO);
            if (x != null) return x;
            return gasMeterVersionBizApi.update(gasMeterVersionUpdateDTO);
        }
        return R.fail(getLangMessage(MessageConstants.REPEAT_GAS_VERSION_NAME));
    }

    private R<GasMeterVersion> updateCheck(GasMeterVersionUpdateDTO gasMeterVersionUpdateDTO) {
        GasMeterFactory gasMeterFactory=new GasMeterFactory();
        gasMeterFactory.setGasMeterFactoryStatus(1);
        gasMeterFactory.setId(gasMeterVersionUpdateDTO.getCompanyId());
        R<List<GasMeterFactory>> factoryList=gasMeterFactoryBizApi.query(gasMeterFactory);
        if (factoryList.getIsError()) {
            return R.fail(getLangMessage(MessageConstants.SELECT_FAIL));
        }
        List<GasMeterFactory> gasMeterFactoryList=factoryList.getData();
        if (gasMeterFactoryList==null||gasMeterFactoryList.size()==0){
            return R.fail(getLangMessage(MessageConstants.INVALID_GAS_METER_FACTORY));
        }
        GasMeterModel gasMeterModel=new GasMeterModel();
        gasMeterModel.setGasMeterVersionId(gasMeterVersionUpdateDTO.getId());
        R<List<GasMeterModel>> modelresult = gasMeterModelBizApi.query(gasMeterModel);
        if (modelresult.getIsError()) {
            return R.fail(getLangMessage(MessageConstants.SELECT_FAIL));
        }
        List<GasMeterModel> list = modelresult.getData();
        //该版号是否已经被使用，若被使用
        if (list != null||list.size() != 0) {
            if (gasMeterVersionUpdateDTO.getVersionState() == null) {
                return R.fail(getLangMessage(MessageConstants.SELECT_FAIL));
            }else{
                for (GasMeterModel gasMeter : list) {
                    if(!gasMeter.getCompanyName().equals(gasMeterVersionUpdateDTO.getCompanyName())){
                        gasMeter.setCompanyName(gasMeterVersionUpdateDTO.getCompanyName());
                    }
                    if(!gasMeter.getGasMeterVersionName().equals(gasMeterVersionUpdateDTO
                            .getGasMeterVersionName())){
                        gasMeter.setGasMeterVersionName(gasMeterVersionUpdateDTO.getGasMeterVersionName());
                    }
                    if(gasMeterVersionUpdateDTO.getVersionState()==0){ gasMeter.setDataStatus(0);
                    }
                }
            }
            List<GasMeterModelUpdateDTO> gasMeterList = BeanPlusUtil.toBeanList(list, GasMeterModelUpdateDTO.class);
            gasMeterModelBizApi.updateBatchById(gasMeterList);
        }
        return null;
    }


    @Override
    public R<Boolean> updateBatchGasMeter(List<GasMeterVersionUpdateDTO> gasMeterVersionUpdateList) {
        if (gasMeterVersionUpdateList.get(0).getVersionState()==1){
            GasMeterFactory gasMeterFactory=new GasMeterFactory();
            gasMeterFactory.setGasMeterFactoryStatus(0);
            List<GasMeterFactory> factoryList=null;
            for (GasMeterVersionUpdateDTO gasMeterVersionUpdateDTO:gasMeterVersionUpdateList) {
                gasMeterFactory.setId(gasMeterVersionUpdateDTO.getCompanyId());
                factoryList=gasMeterFactoryBizApi.query(gasMeterFactory).getData();
            }
            if (factoryList.size()==0){
                return gasMeterVersionBizApi.updateBatchById(gasMeterVersionUpdateList);
            }
            return R.fail(getLangMessage(MessageConstants.INVALID_GAS_METER_FACTORY));
        }else{// 禁用
            GasMeterModel gasMeterModel=new GasMeterModel();
            List<GasMeterModel> modelList=null;
            for (GasMeterVersionUpdateDTO gasMeterVersionUpdateDTO:gasMeterVersionUpdateList) {
                gasMeterModel.setGasMeterVersionId(gasMeterVersionUpdateDTO.getId());
                modelList=gasMeterModelBizApi.query(gasMeterModel).getData();
            }
            if (modelList.size()==0){
                List<GasMeterModelUpdateDTO>modelUpdateDTOList=BeanPlusUtil.toBeanList(modelList,GasMeterModelUpdateDTO.class);
                gasMeterModelBizApi.updateBatchById(modelUpdateDTOList);
            }else return R.fail(getLangMessage(MessageConstants.INVALID_GAS_METER_FACTORY));
        }
        return gasMeterVersionBizApi.updateBatchById(gasMeterVersionUpdateList);
    }
}