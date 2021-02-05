package com.cdqckj.gmis.operateparam.service;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.operateparam.entity.CompanyUserQuotaRecord;
import com.cdqckj.gmis.operateparam.vo.BusinessHallVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 业务接口
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-07-02
 */
public interface CompanyUserQuotaRecordService extends SuperService<CompanyUserQuotaRecord> {
    R<Map<String, List<BusinessHallVO>>> getUserBusinessHall();

    /**
     * 查询最近一条配额记录
     * @return
     */
    CompanyUserQuotaRecord queryRecentRecord();
}
