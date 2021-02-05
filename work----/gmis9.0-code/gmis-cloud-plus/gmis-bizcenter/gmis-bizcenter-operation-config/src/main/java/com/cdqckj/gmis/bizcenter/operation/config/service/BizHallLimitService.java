package com.cdqckj.gmis.bizcenter.operation.config.service;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperCenterService;
import com.cdqckj.gmis.operateparam.dto.BusinessHallQuotaRecordSaveDTO;
import com.cdqckj.gmis.operateparam.entity.BusinessHallQuotaRecord;

public interface BizHallLimitService extends SuperCenterService {
    R<BusinessHallQuotaRecord> saveBusinessHallQuotaRecord(BusinessHallQuotaRecordSaveDTO saveDTO);
}
