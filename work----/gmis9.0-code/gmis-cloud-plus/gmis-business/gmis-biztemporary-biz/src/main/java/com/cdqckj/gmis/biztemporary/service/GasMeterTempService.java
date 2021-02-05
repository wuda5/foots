package com.cdqckj.gmis.biztemporary.service;

import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.biztemporary.entity.GasMeterTemp;

/**
 * <p>
 * 业务接口
 * 气表档案临时表
 * </p>
 *
 * @author songyz
 * @date 2021-01-04
 */
public interface GasMeterTempService extends SuperService<GasMeterTemp> {
    Boolean check(GasMeterTemp params);
}
