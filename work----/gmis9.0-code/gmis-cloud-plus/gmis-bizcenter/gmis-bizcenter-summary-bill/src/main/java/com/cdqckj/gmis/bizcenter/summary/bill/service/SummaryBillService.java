package com.cdqckj.gmis.bizcenter.summary.bill.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.base.service.SuperCenterService;
import com.cdqckj.gmis.charges.dto.ChargeRecordPageDTO;
import com.cdqckj.gmis.charges.entity.ChargeRecord;
import com.cdqckj.gmis.charges.entity.SummaryBill;
import com.cdqckj.gmis.invoice.dto.InvoicePageDTO;
import com.cdqckj.gmis.invoice.entity.Invoice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author songyz
 * @author hp
 */
public interface SummaryBillService extends SuperCenterService {
    /**
     * 扎帐查询
     * @param endTime
     * @return
     */
    R<SummaryBill> querySummaryBill(LocalDateTime startTime, LocalDateTime endTime);
    /**
     * 扎帐
     * @param summaryBill
     * @return
     */
    R<SummaryBill> summaryBill(SummaryBill summaryBill);
    /**
     * 反扎帐
     * @param summaryBills
     * @return
     */
    R<List<SummaryBill>> reverseSummaryBill(List<SummaryBill> summaryBills);
    /**
     * 导出扎帐
     * @param summaryBill
     * @return
     */
    void exportSummaryBill(HttpServletRequest request, HttpServletResponse response,
                           SummaryBill summaryBill) throws Exception;
    /**
     * 待扎帐收费明细记录查询
     *
     * @param params
     * @return
     */
    R<Page<ChargeRecord>> pageChargeRecordList(PageParams<ChargeRecordPageDTO> params);
    /**
     * 待扎帐发票明细查询
     *
     * @param params
     * @return
     */
    R<Page<Invoice>> pageInvoiceList(PageParams<InvoicePageDTO> params);
    /**
     * 已扎帐收费明细记录查询
     * @param current
     * @param size
     * @param summaryBill
     * @return
     */
    R<Page<ChargeRecord>> queryChargeRecordList(long current,
                                                       long size, SummaryBill summaryBill);
    /**
     * 已扎帐发票明细查询
     * @param current
     * @param size
     * @param summaryBill
     * @return
     */
    R<Page<Invoice>> queryInvoiceList(Integer current,
                                             Integer size, SummaryBill summaryBill);


}
