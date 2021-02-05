package com.cdqckj.gmis.scan.service;

import com.cdqckj.gmis.scan.model.SystemApiScanSaveDTO;

/**
 * This is a Description
 *
 * @author gmis
 * @date 2019/12/17
 */

public interface SystemApiScanService {
    /**
     * 批量保存
     *
     * @param data
     * @return
     */
    Boolean batchSave(SystemApiScanSaveDTO data);
}
