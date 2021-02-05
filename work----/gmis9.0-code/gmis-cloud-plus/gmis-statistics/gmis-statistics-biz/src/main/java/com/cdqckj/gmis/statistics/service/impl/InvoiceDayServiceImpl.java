package com.cdqckj.gmis.statistics.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.statistics.dao.InvoiceDayMapper;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.statistics.entity.InvoiceDay;
import com.cdqckj.gmis.statistics.service.InvoiceDayService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import com.cdqckj.gmis.statistics.vo.InvoiceDayKindVo;
import com.cdqckj.gmis.common.domain.sts.InvoiceDayStsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 业务实现类
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-11-02
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class InvoiceDayServiceImpl extends SuperServiceImpl<InvoiceDayMapper, InvoiceDay> implements InvoiceDayService {

    @Override
    public InvoiceDay getTheDayRecord(String searchStr) {
        return this.baseMapper.getTheDayRecord(searchStr);
    }

    @Override
    public List<InvoiceDayKindVo> stsKindNow(Long uId, Integer kind, LocalDateTime stsDay) {
        return this.baseMapper.stsKindNow(uId, kind, stsDay);
    }

    @Override
    public List<InvoiceDayKindVo> panelInvoiceKind(StsSearchParam stsSearchParam) {
        return this.baseMapper.panelInvoiceKind(stsSearchParam, stsSearchParam.createSearchSql());
    }

    @Override
    public List<InvoiceDayStsVo> invoiceTypeGroupByKind(StsSearchParam stsSearchParam, String typeCode) {
        return this.baseMapper.getInvoiceTypeGroupByKind(stsSearchParam, stsSearchParam.createSearchSql(), typeCode);
    }
}
