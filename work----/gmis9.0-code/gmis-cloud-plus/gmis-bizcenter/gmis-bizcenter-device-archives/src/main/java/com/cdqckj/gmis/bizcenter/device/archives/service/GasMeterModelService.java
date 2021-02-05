package com.cdqckj.gmis.bizcenter.device.archives.service;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.gasmeter.dto.GasMeterModelSaveDTO;
import com.cdqckj.gmis.gasmeter.dto.GasMeterModelUpdateDTO;
import com.cdqckj.gmis.gasmeter.entity.GasMeterModel;

import java.util.List;

public interface GasMeterModelService {
    //新增校验
    R<GasMeterModel>saveGasMeterModel(GasMeterModelSaveDTO gasMeterModelSaveDTO);
    //修改校验
    R<GasMeterModel>updateGasMeterModel(GasMeterModelUpdateDTO gasMeterModelUpdateDTO);
    //批量修改校验
    R<Boolean> updateBatchModel(List<GasMeterModelUpdateDTO> gasMeterModelUpdateDTOList);
}
