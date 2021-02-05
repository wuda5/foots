package com.cdqckj.gmis.bizcenter.invoice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.base.service.SuperCenterService;
import com.cdqckj.gmis.bizcenter.invoice.dto.ReceiptDTO;
import com.cdqckj.gmis.invoice.dto.*;
import com.cdqckj.gmis.invoice.entity.*;
import com.cdqckj.gmis.invoice.vo.InvoiceResponseData;

import java.util.List;

public interface BillService extends SuperCenterService {
    public R<Boolean> printInvoiceBasedOnReceipt(ReceiptDTO receiptDTO);

    public R<List<Receipt>> query(Receipt data);

    public R<Receipt> save(ReceiptSaveDTO saveDTO);

    public R<Receipt> update(ReceiptUpdateDTO updateDTO);

    public R<List<ReceiptDetail>> query(ReceiptDetail data);

    public R<ReceiptDetail> save(ReceiptDetailSaveDTO saveDTO);

    public R<ReceiptDetail> update(ReceiptDetailUpdateDTO updateDTO);

    public R<List<Invoice>> query(Invoice data);

    public R<Invoice> update(InvoiceUpdateDTO updateDTO);

    public R<Invoice> save(InvoiceSaveDTO saveDTO);

    public R<List<InvoiceDetails>> query(InvoiceDetails data);

    public R<InvoiceDetails> update(InvoiceDetailsUpdateDTO updateDTO);

    public R<InvoiceDetails> save(InvoiceDetailsSaveDTO saveDTO);

    public R<Receipt> makeOutReceipt(Bill bill);

    public R<Invoice> makeOutInvoice(Bill bill);

    /**
     * 生成发票编号
     */
    public String generateInvoiceNo();

    /**
     * 生成票据编号
     */
    public String generateReceiptNo();

    public R<Page<Invoice>> pageInvoice(PageParams<InvoicePageDTO> pageParams);

    public R<Page<Receipt>> pageReceipt(PageParams<ReceiptPageDTO> pageParams);

    /**
     * 通过票据ID查询票据详情
     *
     * @param receiptId 票据ID
     * @return
     */
    public R<List<ReceiptDetail>> queryReceiptDetail(String receiptId);

    /**
     * 通过发票ID查询发票详情
     *
     * @param invoiceId 发票ID
     * @return
     */
    public R<List<InvoiceDetails>> queryInvoiceDetails(String invoiceId);

    /**
     * 校验发票编号是否存在
     *
     * @param invoiceNumber
     * @return
     */
    public R<Boolean> checkInvoiceNumber(String invoiceNumber);

    /**
     * 通过缴费订单打印发票
     *
     * @param invoiceAddDTO
     * @return
     */
    public R<InvoiceResponseData> printInvoiceByCharge(InvoiceAddDTO invoiceAddDTO);

    /**
     * 瑞宏开票回调接口
     *
     * @param result
     * @return
     */
    public R<Boolean> rhKpCallback(String result, String tenant);
}
