package com.cdqckj.gmis.invoice.hystrix;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.invoice.InvoiceBizApi;
import com.cdqckj.gmis.invoice.dto.*;
import com.cdqckj.gmis.invoice.dto.rhapi.ResponseData;
import com.cdqckj.gmis.invoice.entity.Invoice;
import com.cdqckj.gmis.invoice.vo.InvoiceResponseData;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Component
public abstract class InvoiceBizApiFallback implements InvoiceBizApi {
    /**
     * ID查询发票记录
     *
     * @param id
     * @return
     */
    @Override
    public R<Invoice> get(Long id) {
        return R.timeout();
    }

    @Override
    public R<Invoice> save(@Valid InvoiceSaveDTO saveDTO) {
        return R.timeout();
    }

    @Override
    public R<Invoice> update(InvoiceUpdateDTO updateDTO) {
        return R.timeout();
    }

    @Override
    public R<List<Invoice>> query(Invoice data) {
        return R.timeout();
    }

    @Override
    public R<Page<Invoice>> page(PageParams<InvoicePageDTO> params) {
        return R.timeout();
    }

    @Override
    public R<List<Invoice>> queryInvoice(LocalDateTime startTime, LocalDateTime endTime) {
        return R.timeout();
    }

    @Override
    public R<Integer> checkInvoiceNumber(String invoiceNumber) {
        return R.timeout();
    }

    /**
     * 通过缴费订单生成发票
     *
     * @param invoiceAddDTO
     * @return
     */
    @Override
    public R<InvoiceResponseData> createInvoiceByChargeNo(InvoiceAddDTO invoiceAddDTO) {
        return R.timeout();
    }

    /**
     * 瑞宏开票回调
     *
     * @param callback
     * @return
     */
    @Override
    public R<Boolean> rhKpCallback(InvoiceCallbackDTO callback) {
        return R.timeout();
    }

    /**
     * 重新推送发票到服务平台开票
     *
     * @param invoiceAddDTO 发票id
     * @return 推送结果
     */
    @Override
    public R<InvoiceResponseData> retryPrint(InvoiceAddDTO invoiceAddDTO) {
        return R.timeout();
    }

    /**
     * 重新开票
     *
     * @param invoiceAddDTO 重新开票参数
     * @return 重新开票结果
     */
    @Override
    public R<InvoiceResponseData> retryKp(InvoiceAddDTO invoiceAddDTO) {
        return R.timeout();
    }

    /**
     * 电子发票冲红
     *
     * @param invoiceChDTO 电子发票冲红
     * @return 电子发票冲红结果
     */
    @Override
    public R<ResponseData> invoiceCh(InvoiceChDTO invoiceChDTO) {
        return R.timeout();
    }

    /**
     * 通过缴费编号查询未冲红、作废的且开票中或开票成功的发票
     *
     * @param chargeNo 缴费编号
     * @return 发票数据
     */
    @Override
    public R<Invoice> getEffectiveInvoice(String chargeNo) {
        return R.timeout();
    }

    @Override
    public R<List<Invoice>> listByMap(Map<String, Object> columnMap) {
        return R.timeout();
    }

    @Override
    public R<Page<Invoice>> pageInvoice(Integer current, Integer size, Long summaryId) {
        return R.timeout();
    }

    @Override
    public R<List<Invoice>> queryInvoicesByTimeFrame(LocalDateTime startTime, LocalDateTime endTime) {
        return R.timeout();
    }

    @Override
    public R<List<Invoice>> queryInvoicesBySummaryId(Long summaryId) {
        return R.timeout();
    }

    @Override
    public R<Page<Invoice>> pageInvoicesByTimeFrame(long current, long size, LocalDateTime startTime, LocalDateTime endTime) {
        return R.timeout();
    }

}
