package com.cdqckj.gmis.gasmeter.service;

import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.gasmeter.entity.GasMeterModel;

/**
 * <p>
 * 业务接口
 * 气表型号
 * </p>
 *
 * @author gmis
 * @date 2020-08-24
 */
public interface GasMeterModelService extends SuperService<GasMeterModel> {
    Boolean queryGasMeterModelCheck(GasMeterModel gasMeterModel);
    GasMeterModel queryModel(GasMeterModel gasMeterModel);
}
