package com.cdqckj.gmis.operateparam.dao;

import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.operateparam.entity.CompanyUserQuotaRecord;

import com.cdqckj.gmis.operateparam.vo.BusinessHallVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-07-02
 */
@Repository
public interface CompanyUserQuotaRecordMapper extends SuperMapper<CompanyUserQuotaRecord> {
     List<BusinessHallVO> selectBusinessHallUser();
     CompanyUserQuotaRecord queryRecentRecord();
}
