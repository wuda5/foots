package com.cdqckj.gmis.authority.service.auth;

import com.cdqckj.gmis.authority.dto.auth.SystemApiScanSaveDTO;
import com.cdqckj.gmis.authority.entity.auth.SystemApi;
import com.cdqckj.gmis.base.service.SuperCacheService;

/**
 * <p>
 * 业务接口
 * API接口
 * </p>
 *
 * @author gmis
 * @date 2019-12-15
 */
public interface SystemApiService extends SuperCacheService<SystemApi> {
    /**
     * 批量保存
     *
     * @param data
     * @return
     */
    Boolean batchSave(SystemApiScanSaveDTO data);
}
