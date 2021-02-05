package com.cdqckj.gmis.charges.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.charges.dao.SummaryBillMapper;
import com.cdqckj.gmis.charges.entity.ChargeRecord;
import com.cdqckj.gmis.charges.entity.SummaryBill;
import com.cdqckj.gmis.charges.service.SummaryBillDetailService;
import com.cdqckj.gmis.charges.service.SummaryBillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 业务实现类
 * 
 * </p>
 *
 * @author songyz
 * @date 2020-09-16
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class SummaryBillServiceImpl extends SuperServiceImpl<SummaryBillMapper, SummaryBill> implements SummaryBillService {

    @Resource
    private SummaryBillDetailService summaryBillDetailService;
    @Override
    public R<Page<ChargeRecord>> pageSummaryChargeRecord(long current, long size, Long summaryId) {
        Page<ChargeRecord> page = new Page<>(current,size);
//        Map<String, Object> columnMap = new HashMap<>(2);
//        columnMap.put("summary_bill_id", params.getId());
//        List<SummaryBillDetail>  summaryBillDetailList = summaryBillDetailService.listByMap(columnMap);
//        List<Long> chargeIdList = summaryBillDetailList.stream()
//                .filter(summaryBillDetail->summaryBillDetail.equals(SummaryBillSourceEnum.CHARGE.getCode()))
//                .map(SummaryBillDetail::getSourceId).collect(Collectors.toList());
        return R.success(baseMapper.queryChargeRecordList(page,summaryId));
    }
}
