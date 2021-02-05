package com.cdqckj.gmis.bizcenter.device.archives.service;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.gasmeter.dto.GasMeterVersionSaveDTO;
import com.cdqckj.gmis.gasmeter.dto.GasMeterVersionUpdateDTO;
import com.cdqckj.gmis.gasmeter.entity.GasMeterVersion;

import java.util.List;

public interface GasMeterVersionService {
    R<GasMeterVersion>saveGasMeterVersion(GasMeterVersionSaveDTO gasMeterVersionSaveDTO);

    R<GasMeterVersion>updateGasMeterVersion(GasMeterVersionUpdateDTO gasMeterVersionUpdateDTO);

    R<Boolean> updateBatchGasMeter(List<GasMeterVersionUpdateDTO> gasMeterVersionUpdateList);
}
