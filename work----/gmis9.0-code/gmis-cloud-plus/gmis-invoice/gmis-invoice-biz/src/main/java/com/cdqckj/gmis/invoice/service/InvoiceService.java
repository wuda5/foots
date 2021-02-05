package com.cdqckj.gmis.invoice.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.domain.sts.InvoiceDayStsVo;
import com.cdqckj.gmis.invoice.dto.InvoiceAddDTO;
import com.cdqckj.gmis.invoice.dto.InvoiceCallbackDTO;
import com.cdqckj.gmis.invoice.dto.InvoiceChDTO;
import com.cdqckj.gmis.invoice.dto.InvoicePageDTO;
import com.cdqckj.gmis.invoice.dto.rhapi.ResponseData;
import com.cdqckj.gmis.invoice.dto.rhapi.einvoice.CxParam;
import com.cdqckj.gmis.invoice.entity.Invoice;
import com.cdqckj.gmis.invoice.vo.InvoiceResponseData;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 业务接口
 *
 * </p>
 *
 * @author songyz
 * @date 2020-09-07
 */
public interface InvoiceService extends SuperService<Invoice> {

    /**
     * 模糊查询发票数据
     * 匹配字段：客户编号、客户姓名、订单编号、发票编号、发票抬头、联系电话
     *
     * @param pageParams
     * @param page
     * @return
     */
    IPage<Invoice> findPage(PageParams<InvoicePageDTO> pageParams, IPage<Invoice> page);

    /**
     * 校验发票编号是已存在
     *
     * @param invoiceNumber
     * @return
     */
    Integer checkInvoiceNumber(String invoiceNumber);

    /**
     * 通过缴费单创建发票
     *
     * @param invoiceAddDTO
     * @return
     */
    InvoiceResponseData createInvoiceByChargeNo(InvoiceAddDTO invoiceAddDTO);


    /**
     * 在电子发票服务平台查询发票
     *
     * @param cxParam
     * @return
     */
    ResponseData invoiceCx(CxParam cxParam);

    /**
     * 冲红发票
     *
     * @param invoiceChDTO
     * @return
     */
    ResponseData invoiceCh(InvoiceChDTO invoiceChDTO);

    /**
     * 重新开票
     *
     * @param invoiceAddDTO 发票id
     * @return 推送结果
     */
    InvoiceResponseData retryPrint(InvoiceAddDTO invoiceAddDTO);


    /**
     * 重新开票
     *
     * @param invoiceAddDTO
     * @return
     */
    InvoiceResponseData retryKp(InvoiceAddDTO invoiceAddDTO);


    /**
     * 瑞宏网电子发票回调
     *
     * @param callback
     * @return
     */
    void rhKpCallback(InvoiceCallbackDTO callback);

    /**
     * 通过缴费编号查询对应的有效发票
     *
     * @param chargeNo 缴费编号
     * @return 发票数据
     */
    Invoice getEffectiveInvoice(String chargeNo);

    /**
     * 已扎帐发票明细查询
     * @param current
     * @param size
     * @param summaryId
     * @return
     */
    R<Page<Invoice>> pageInvoice(Integer current,
                                        Integer size, Long summaryId);

    /**
     * 关联缴费数据查询发票接口
     * @param startTime
     * @param endTime
     * @return
     */
    List<Invoice> queryInvoicesByTimeFrame(LocalDateTime startTime,LocalDateTime endTime);
    /**
     * 根据扎帐ID查询发票信息
     * @param summaryId
     * @return
     */
    List<Invoice> queryInvoicesBySummaryId(Long summaryId);
    /**
     * 根据时间范围分页查询缴费记录的发票信息
     * @param current
     * @param size
     * @param startTime
     * @param endTime
     * @return
     */
    Page<Invoice> pageInvoicesByTimeFrame(long current, long size, LocalDateTime startTime, LocalDateTime endTime);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/15 16:43
    * @remark 统计类型
    */
    List<InvoiceDayStsVo> invoiceStsByType(StsSearchParam stsSearchParam);
}
