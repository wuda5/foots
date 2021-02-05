package com.cdqckj.gmis.charges.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.charges.entity.ChargeRecord;
import com.cdqckj.gmis.charges.entity.SummaryBill;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * 
 * </p>
 *
 * @author songyz
 * @date 2020-09-16
 */
@Repository
public interface SummaryBillMapper extends SuperMapper<SummaryBill> {
    /**
     * 已扎帐收费明细记录查询
     * @param page
     * @param summaryId
     * @return
     */
    Page<ChargeRecord> queryChargeRecordList(IPage<ChargeRecord> page, @Param("summaryId") Long summaryId);
}
