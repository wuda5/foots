package com.cdqckj.gmis.bizcenter.device.archives.service;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.gasmeter.dto.GasMeterFactorySaveDTO;
import com.cdqckj.gmis.gasmeter.dto.GasMeterFactoryUpdateDTO;
import com.cdqckj.gmis.gasmeter.entity.GasMeterFactory;

import java.util.List;

public interface GasMeterFactoryService {

    R<GasMeterFactory> saveGasMeter(GasMeterFactorySaveDTO gasMeterFactorySaveDTO);

    R<GasMeterFactory> updateGasMeter(GasMeterFactoryUpdateDTO gasMeterFactoryUpdateDTO);

    R<Boolean> updateBatchGasMeter(List<GasMeterFactoryUpdateDTO> gasMeterFactoryList);

    Boolean gasMeterFactoryCheck(GasMeterFactory gasMeterFactory);
}
