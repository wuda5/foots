package com.cdqckj.gmis.bizcenter.operation.config.service;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperCenterService;
import com.cdqckj.gmis.operateparam.dto.CompanyUserQuotaRecordSaveDTO;
import com.cdqckj.gmis.operateparam.entity.CompanyUserQuotaRecord;

public interface OperatorLimitService extends SuperCenterService {
    R<CompanyUserQuotaRecord> saveCompanyUserQuotaRecord(CompanyUserQuotaRecordSaveDTO saveDTO);
}
