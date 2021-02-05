package com.cdqckj.gmis.bizcenter.device.archives.service.impl;

import com.cdqckj.gmis.base.MessageConstants;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperCenterServiceImpl;
import com.cdqckj.gmis.bizcenter.device.archives.service.GasMeterModelService;
import com.cdqckj.gmis.device.GasMeterModelBizApi;
import com.cdqckj.gmis.device.GasMeterVersionBizApi;
import com.cdqckj.gmis.gasmeter.dto.GasMeterModelSaveDTO;
import com.cdqckj.gmis.gasmeter.dto.GasMeterModelUpdateDTO;
import com.cdqckj.gmis.gasmeter.entity.GasMeterModel;
import com.cdqckj.gmis.gasmeter.entity.GasMeterVersion;
import com.cdqckj.gmis.utils.BeanPlusUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("gasMeterModelService")
public class GasMeterModelServiceImpl extends SuperCenterServiceImpl implements GasMeterModelService {
    @Autowired
    private GasMeterModelBizApi gasMeterModelBizApi;
    @Autowired
    private GasMeterVersionBizApi gasMeterVersionBizApi;
    @Override
    public R<GasMeterModel> saveGasMeterModel(GasMeterModelSaveDTO gasMeterModelSaveDTO) {
        GasMeterModel gasMeterModel= BeanPlusUtil.toBean(gasMeterModelSaveDTO,GasMeterModel.class);
        Boolean isRepeat=gasMeterModelBizApi.queryGasMeter(gasMeterModel).getData();
        if (!isRepeat){
            //最大流量校验
            if (gasMeterModelSaveDTO.getMaxFlow().compareTo(gasMeterModelSaveDTO.getMinFlow())<=0){
                return R.fail(getLangMessage(MessageConstants.INVALID_FLOW));
            }
            return gasMeterModelBizApi.save(gasMeterModelSaveDTO);
        }
        return R.fail(getLangMessage(MessageConstants.REPEAT_GAS_MODEL_NAME));
    }

    @Override
    public R<GasMeterModel> updateGasMeterModel(GasMeterModelUpdateDTO gasMeterModelUpdateDTO) {
        GasMeterModel gasMeterModel= BeanPlusUtil.toBean(gasMeterModelUpdateDTO,GasMeterModel.class);
        Boolean isRepeat=gasMeterModelBizApi.queryGasMeter(gasMeterModel).getData();
        if (!isRepeat){
            //最大流量校验
            if (gasMeterModelUpdateDTO.getMaxFlow().compareTo(gasMeterModelUpdateDTO.getMinFlow())<=0){
                return R.fail(getLangMessage(MessageConstants.INVALID_FLOW));
            }
            if (gasMeterModelUpdateDTO.getDataStatus()==1){
                GasMeterVersion gasMeterVersion=new GasMeterVersion();
                gasMeterVersion.setId(gasMeterModelUpdateDTO.getGasMeterVersionId());
                gasMeterVersion.setVersionState(1);
                List<GasMeterVersion> versionList=gasMeterVersionBizApi.query(gasMeterVersion).getData();
                if (versionList==null||versionList.size()==0)
                    return R.fail(getLangMessage(MessageConstants.INVALID_GAS_METER_VERSION));
            }
            return gasMeterModelBizApi.update(gasMeterModelUpdateDTO);
        }
        return R.fail(getLangMessage(MessageConstants.REPEAT_GAS_MODEL_NAME));
    }

    @Override
    public R<Boolean> updateBatchModel(List<GasMeterModelUpdateDTO> gasMeterModelUpdateDTOList) {
        if (gasMeterModelUpdateDTOList.get(0).getDataStatus()==1){
            GasMeterVersion gasMeterVersion=new GasMeterVersion();
            gasMeterVersion.setVersionState(0);
            List<GasMeterVersion> versionList= null;
            for (GasMeterModelUpdateDTO gasMeterModelUpdateDTO:gasMeterModelUpdateDTOList) {
                gasMeterVersion.setId(gasMeterModelUpdateDTO.getGasMeterVersionId());
                versionList=gasMeterVersionBizApi.query(gasMeterVersion).getData();
            }
            if (versionList.size()==0){
                return gasMeterModelBizApi.updateBatchById(gasMeterModelUpdateDTOList);
            }else  return R.fail(getLangMessage(MessageConstants.INVALID_GAS_METER_VERSION));
        }
        return gasMeterModelBizApi.updateBatchById(gasMeterModelUpdateDTOList);
    }
}
