package com.cdqckj.gmis.bizcenter.summary.bill.service.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.archive.CustomerGasMeterRelatedBizApi;
import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.MessageConstants;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.base.service.SuperCenterServiceImpl;
import com.cdqckj.gmis.bizcenter.summary.bill.entity.ChargeRecordDetail;
import com.cdqckj.gmis.bizcenter.summary.bill.entity.ReceiptInvoiceRecordDetail;
import com.cdqckj.gmis.bizcenter.summary.bill.service.SummaryBillService;
import com.cdqckj.gmis.bizcenter.summary.bill.util.ExcelUtil;
import com.cdqckj.gmis.charges.api.*;
import com.cdqckj.gmis.charges.dto.*;
import com.cdqckj.gmis.charges.entity.ChargeRecord;
import com.cdqckj.gmis.charges.entity.SummaryBill;
import com.cdqckj.gmis.charges.entity.SummaryBillDetail;
import com.cdqckj.gmis.charges.enums.ChargePayMethodEnum;
import com.cdqckj.gmis.charges.enums.SummaryBillSourceEnum;
import com.cdqckj.gmis.charges.enums.SummaryBillStatusEnum;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.devicearchive.entity.CustomerGasMeterRelated;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.invoice.InvoiceBizApi;
import com.cdqckj.gmis.invoice.dto.InvoicePageDTO;
import com.cdqckj.gmis.invoice.entity.Invoice;
import com.cdqckj.gmis.invoice.entity.Receipt;
import com.cdqckj.gmis.invoice.enumeration.InvoiceKindEnum;
import com.cdqckj.gmis.invoice.enumeration.InvoiceTypeEnum;
import com.cdqckj.gmis.invoice.enumeration.ReceiptStateEnum;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author songyz
 * @author hp
 */
@Slf4j
@Service("summaryBillService")
public class SummaryBillServiceImpl extends SuperCenterServiceImpl implements SummaryBillService {

    @Autowired
    private ChargeRecordBizApi chargeRecordBizApi;
    @Autowired
    private InvoiceBizApi invoiceBizApi;
    @Autowired
    private SummaryBillBizApi summaryBillBizApi;
    @Autowired
    private SummaryBillDetailBizApi summaryBillDetailBizApi;
    @Autowired
    private ChargeRefundBizApi chargeRefundBizApi;
    @Autowired
    private CustomerGasMeterRelatedBizApi customerGasMeterRelatedBizApi;
    @Autowired
    private AccountRefundBizApi accountRefundBizApi;

    private static final String DATE_FORMAT = "yyyyMMdd";
    private static final String EXPOR_DATE_FORMAT = "YYYY-MM-dd-HH-mm";
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    /**
     * 一段时间内的扎帐信息
     * @param endTime
     * @return
     */
    @GlobalTransactional
    @Override
    public R<SummaryBill> querySummaryBill(LocalDateTime startTime, LocalDateTime endTime) {
        long chargeUserId = BaseContextHandler.getUserId();
        //缴费是否可扎帐
        R<Boolean> isChargeSummaryR = chargeRecordBizApi.isSummaryBill(chargeUserId, startTime, endTime);
        if(isChargeSummaryR.getIsError() || ObjectUtil.isNull(isChargeSummaryR.getData())){
            log.error("判断缴费记录是否可扎帐异常");
            throw new BizException("判断缴费记录是否可扎帐异常");
        }
        if(isChargeSummaryR.getData()){
            log.error("存在收费未完成、失败或者退费未完成的情况，不可扎帐");
            throw new BizException("存在收费未完成、失败或者退费未完成的情况，不可扎帐");
        }
        //退费是否可扎帐
        R<Boolean> isChargeRefundR = chargeRefundBizApi.isSummaryBill(chargeUserId, startTime, endTime);
        if(isChargeRefundR.getIsError() || ObjectUtil.isNull(isChargeRefundR.getData())){
            log.error("判退费是否可扎帐异常");
            throw new BizException("判退费是否可扎帐异常");
        }
        if(isChargeRefundR.getData()){
            log.error("存在退费未完成或者退费失败的情况，不可扎帐");
            return R.fail("存在退费未完成或者退费失败的情况，不可扎帐");
        }
        //账户退费是否可扎帐
        R<Boolean> isAccountRefundR = accountRefundBizApi.isSummaryBill(chargeUserId, startTime, endTime);
        if(isAccountRefundR.getIsError() || ObjectUtil.isNull(isAccountRefundR.getData())){
            log.error("判账户退费是否可扎帐异常");
            throw new BizException("判账户退费是否可扎帐异常");
        }
        if(isAccountRefundR.getData()){
            log.error("存在账户退费未完成或者账户退费失败的情况，不可扎帐");
            return R.fail("存在账户退费未完成或者账户退费失败的情况，不可扎帐");
        }
        //缴费扎帐数据
        List<ChargeRecord> chargeRecordList = getChargeRecords(startTime, endTime);
        if(ObjectUtil.isNull(chargeRecordList) || chargeRecordList.size() == 0){
            return R.fail("没有扎帐信息");
        }
        //合计金额
        BigDecimal totalAmount = calculateTotalAmount(chargeRecordList);
        //退费金额
        R<BigDecimal> result = chargeRefundBizApi.sumChargeRefundByTime(startTime, endTime);
        if (result.getIsError()) {
            log.error("查询退费总金额失败");
            throw new BizException("查询退费总金额失败");
        }
        BigDecimal refundAmount;
        if(ObjectUtil.isNull(result.getData())){
            log.error("没有可扎帐的退费金额");
            refundAmount = BigDecimal.ZERO;
        }
        else {
            refundAmount = result.getData();
        }
        //账户退费金额
        R<BigDecimal> accountRefundR = accountRefundBizApi.sumAccountRefundByTime(startTime, endTime);
        if (accountRefundR.getIsError()) {
            log.error("查询账户退费金额失败");
            throw new BizException("查询账户退费金额失败");
        }
        if(ObjectUtil.isNotNull(accountRefundR.getData())){
            refundAmount = refundAmount.add(accountRefundR.getData());
        }
        totalAmount = totalAmount.subtract(refundAmount)
                .setScale(4, RoundingMode.HALF_UP);
        //现金金额
        BigDecimal cashAmount = calculateFee(chargeRecordList, ChargePayMethodEnum.CASH.getCode());
        //银行转账金额
        BigDecimal bankTransferAmount = calculateFee(chargeRecordList, ChargePayMethodEnum.BANK_TRANSFER.getCode());
        //支付宝金额
        BigDecimal alipayAmount = calculateFee(chargeRecordList, ChargePayMethodEnum.ALIPAY.getCode());
        //微信金额
        BigDecimal wechatAmount = calculateFee(chargeRecordList, ChargePayMethodEnum.WECHATPAY.getCode());
        //抵扣金额
        BigDecimal preDepositDeduction = getPreDepositDeduction(chargeRecordList);
        //查询发票
        List<Invoice> invoiceList = getInvoices(startTime, endTime);
        //发票数
        Integer invoiceTotalNum;
        if(ObjectUtil.isNull(invoiceList) || invoiceList.size() == 0){
            invoiceTotalNum = 0;
        }
        else{
            invoiceTotalNum = invoiceList.size();
        }
        //查询票据
        List<ChargeRecord> receiptList = chargeRecordList.stream()
                .filter(chargeRecord -> ReceiptStateEnum.GIVE_RECEIPT_NOT_INVOICE.getCode().equals(chargeRecord.getReceiptStatus()))
                .collect(Collectors.toList());
        //票据数
        Integer receiptTotalNum;
        if(ObjectUtil.isNull(receiptList) || receiptList.size() == 0){
            receiptTotalNum = 0;
        }else {
            receiptTotalNum = receiptList.size();
        }
        //合计票据发票数
        Integer receiptInvoiceTotalNum = invoiceTotalNum + receiptTotalNum;
        //红票数
        long redInvoiceTotalNum ;
        //蓝票数
        long blueInvoiceTotalNum;
        //废票数
        long invalidInvoiceTotalNum;
        //发票金额
        BigDecimal invoiceAmount;
        BigDecimal invalidAmount;
        if(ObjectUtil.isNull(invoiceList) || invoiceList.size() == 0){
            redInvoiceTotalNum = 0;
            blueInvoiceTotalNum = 0;
            invalidInvoiceTotalNum = 0;
            invoiceAmount = BigDecimal.ZERO;
            invalidAmount = BigDecimal.ZERO;
        }
        else {
            //红票数
            redInvoiceTotalNum = caculateInvoiceNum(invoiceList, InvoiceKindEnum.RED_INVOICE.getCode());
            //蓝票数
            blueInvoiceTotalNum = caculateInvoiceNum(invoiceList, InvoiceKindEnum.BLUE_INVOICE.getCode());
            //废票数
            invalidInvoiceTotalNum = caculateInvoiceNum(invoiceList, InvoiceKindEnum.INVALID_INVOICE.getCode());
            //发票金额
            invoiceAmount = caculateInvoiceAmount(invoiceList);
            //废票金额
            invalidAmount = getInvalidAmount(invoiceList);
        }
        //票据金额
        BigDecimal receiptAmount;
        if(ObjectUtil.isNull(receiptList) || receiptList.size() == 0){
            receiptAmount = BigDecimal.ZERO;
        }else {
            receiptAmount = getTotalAmount(receiptList, BigDecimal.ZERO);
        }
        //合计票据发票金额
        BigDecimal receiptInvoiceTotalAmount = receiptAmount
                .add(invoiceAmount)
                .subtract(invalidAmount)
                .setScale(4, RoundingMode.HALF_UP);
        //扎帐信息
        SummaryBill summaryBillParam = SummaryBill.builder()
                .totalAmount(totalAmount)
                .cashAmount(cashAmount)
                .bankTransferAmount(bankTransferAmount)
                .alipayAmount(alipayAmount)
                .wechatAmount(wechatAmount)
                .refundAmount(refundAmount)
                .preDepositDeduction(preDepositDeduction)
                .receiptInvoiceTotalNum(receiptInvoiceTotalNum)
                .receiptTotalNum(receiptTotalNum)
                .blueInvoiceTotalNum((int) blueInvoiceTotalNum)
                .redInvoiceTotalNum((int) redInvoiceTotalNum)
                .invalidInvoiceTotalNum((int) invalidInvoiceTotalNum)
                .receiptInvoiceTotalAmount(receiptInvoiceTotalAmount)
                .operatorName(BaseContextHandler.getName())
                .operatorNo(String.valueOf(BaseContextHandler.getUserId()))
                .summaryStartDate(startTime)
                .summaryEndDate(endTime)
                .summaryBillStatus(SummaryBillStatusEnum.UNBILL.getCode())
                .build();
        return R.success(summaryBillParam);
    }

    /**
     * 扎帐
     * @param summaryBillParam
     * @return
     */
    @GlobalTransactional
    @Override
    public R<SummaryBill> summaryBill(SummaryBill summaryBillParam){
        if(ObjectUtil.isNull(summaryBillParam)){
            return R.success(summaryBillParam);
        }
        //保存扎帐信息
        SummaryBillSaveDTO summaryBillSaveDTO = BeanUtil.toBean(summaryBillParam, SummaryBillSaveDTO.class);
        summaryBillSaveDTO.setDataStatus(DataStatusEnum.NORMAL.getValue());
        summaryBillSaveDTO.setSummaryBillStatus(SummaryBillStatusEnum.BILLED.getCode());
        R<SummaryBill> saveResult = summaryBillBizApi.save(summaryBillSaveDTO);
        if (saveResult.getIsError() || ObjectUtil.isNull(saveResult.getData())) {
            log.error("新增扎帐数据失败");
            throw new BizException("新增扎帐数据失败");
        }
        SummaryBill summaryBill = saveResult.getData();
        List<ChargeRecord> chargeRecordList = getChargeRecords(summaryBillParam.getSummaryStartDate(), summaryBillParam.getSummaryEndDate());
        //保存扎帐明细
        List<SummaryBillDetailSaveDTO> summaryBillDetailSaveList = new ArrayList<>();
        chargeRecordList.stream().forEach(chargeRecord->{
            SummaryBillDetailSaveDTO summaryBillDetailSaveDTO = SummaryBillDetailSaveDTO
                    .builder()
                    .sourceType(SummaryBillSourceEnum.CHARGE.getCode())
                    .sourceId(chargeRecord.getId())
                    .summaryBillId(summaryBill.getId())
                    .operatorNo(String.valueOf(BaseContextHandler.getUserId()))
                    .summaryBillDate(dateTimeFormatter.format(summaryBillParam.getSummaryEndDate()))
                    .dataStatus(DataStatusEnum.NORMAL.getValue())
                    .build();
            summaryBillDetailSaveList.add(summaryBillDetailSaveDTO);
        });
        summaryBillDetailBizApi.saveList(summaryBillDetailSaveList);
        //修改缴费记录扎帐状态
        List<ChargeRecordUpdateDTO> chargeRecordUpdateList = new ArrayList<>();
        if(ObjectUtil.isNotNull(chargeRecordList) && chargeRecordList.size() > 0){
            chargeRecordList.stream().forEach(chargeRecord->{
                ChargeRecordUpdateDTO updateDTO = ChargeRecordUpdateDTO
                        .builder()
                        .id(chargeRecord.getId())
                        .summaryBillStatus(SummaryBillStatusEnum.BILLED.getCode())
                        .build();
                chargeRecordUpdateList.add(updateDTO);
            });
            chargeRecordBizApi.updateBatchById(chargeRecordUpdateList);
        }
        return R.success(summaryBill);
    }
    @GlobalTransactional
    @Override
    public R<List<SummaryBill>> reverseSummaryBill(List<SummaryBill> summaryBills) {
        summaryBills.stream().forEach(summaryBill->{
            //查询缴费相关扎帐记录
            R<List<SummaryBillDetail>> chargeSummaryDetailListR = getChargeSummaryDetailList(summaryBill);
            //回滚缴费记录状态
            List<SummaryBillDetail> summaryBillDetailList;
            if(ObjectUtil.isNotNull(chargeSummaryDetailListR.getData()) && chargeSummaryDetailListR.getData().size() > 0){
                summaryBillDetailList = chargeSummaryDetailListR.getData();
                summaryBillDetailList.stream().forEach(summaryDetail->{
                    ChargeRecordUpdateDTO updateDTO = ChargeRecordUpdateDTO
                            .builder()
                            .id(summaryDetail.getSourceId())
                            .summaryBillStatus(SummaryBillStatusEnum.UNBILL.getCode())
                            .build();
                    chargeRecordBizApi.update(updateDTO);
                });
            }
            //更新扎帐信息
            SummaryBill summaryBillParam = SummaryBill
                    .builder()
                    .id(summaryBill.getId())
                    .dataStatus(DataStatusEnum.NOT_AVAILABLE.getValue())
                    .summaryBillStatus(SummaryBillStatusEnum.UNBILL.getCode())
                    .build();
            R<SummaryBill> updateResult = summaryBillBizApi.update(BeanUtil.toBean(summaryBillParam, SummaryBillUpdateDTO.class));
            if (updateResult.getIsError()) {
                log.error("更新扎帐数据失败");
                throw new BizException("更新扎帐数据失败");
            }
        });
        return R.success(summaryBills);
    }

    /**
     * 查询缴费相关扎帐记录
     * @param summaryBill
     * @return
     */
    private R<List<SummaryBillDetail>> getChargeSummaryDetailList(SummaryBill summaryBill) {
        //查询缴费相关扎帐记录
        SummaryBillDetail chargeSummaryDetailParam = SummaryBillDetail
                .builder()
                .summaryBillId(summaryBill.getId())
                .sourceType(SummaryBillSourceEnum.CHARGE.getCode())
                .operatorNo(String.valueOf(BaseContextHandler.getUserId()))
                .build();
        R<List<SummaryBillDetail>> chargeSummaryDetailListR = summaryBillDetailBizApi.query(chargeSummaryDetailParam);
        if(chargeSummaryDetailListR.getIsError()){
            log.error("查询缴费相关扎帐明细数据失败");
            throw new BizException("查询缴费相关扎帐明细数据失败");
        }
        return chargeSummaryDetailListR;
    }

    /**
     * 获取废票金额
     *
     * @param invoiceList
     * @return
     */
    private BigDecimal getInvalidAmount(List<Invoice> invoiceList) {
        BigDecimal invalidAmount = BigDecimal.ZERO;
        invoiceList.stream().filter(invoice -> invoice.getInvoiceKind().equals(InvoiceKindEnum.INVALID_INVOICE.getCode())).collect(Collectors.toList());
        Iterator iter = invoiceList.iterator();
        while (iter.hasNext()) {
            Invoice invoice = (Invoice) iter.next();
            invalidAmount = invalidAmount.add(invoice.getPriceTaxTotalLower());
        }
        invalidAmount = invalidAmount.setScale(4, RoundingMode.HALF_UP);
        return invalidAmount;
    }

    /**
     * 查询扎帐信息
     *
     * @param summaryEndDate
     * @return
     */
    private List<SummaryBill> getSummaryBills(LocalDateTime summaryEndDate) {
        SummaryBill summaryBillGet = SummaryBill.builder()
                .summaryEndDate(summaryEndDate)
                .operatorNo(String.valueOf(BaseContextHandler.getUserId()))
                .dataStatus(DataStatusEnum.NORMAL.getValue())
                .build();
        R<List<SummaryBill>> summaryBillListR = summaryBillBizApi.query(summaryBillGet);
        if (summaryBillListR.getIsError()) {
            log.error("查询扎帐信息失败");
            throw new BizException("查询扎帐信息失败");
        }
        return (ObjectUtil.isNull(summaryBillListR.getData()) || summaryBillListR.getData().size() == 0) ? null : summaryBillListR.getData();
    }

    /**
     * 计算合计金额
     *
     * @param chargeRecordList
     * @return
     */
    private BigDecimal calculateTotalAmount(List<ChargeRecord> chargeRecordList) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        //（实收金额–找零金额 + 预存抵扣）累加
        totalAmount = getTotalAmount(chargeRecordList, totalAmount);
        return totalAmount;
    }

    /**
     * 获取合计金额
     *
     * @param chargeRecordList
     * @param totalAmount
     * @return
     */
    private BigDecimal getTotalAmount(List<ChargeRecord> chargeRecordList, BigDecimal totalAmount) {
        Iterator iter = chargeRecordList.iterator();
        while (iter.hasNext()) {
            ChargeRecord chargeRecord = (ChargeRecord) iter.next();
            BigDecimal actualIncomeMoney = chargeRecord.getActualIncomeMoney();
            totalAmount = totalAmount.add(actualIncomeMoney)
                    .subtract(chargeRecord.getGiveChange())
                    .add(chargeRecord.getPrechargeDeductionMoney());
        }
        totalAmount = totalAmount.setScale(4, RoundingMode.HALF_UP);
        return totalAmount;
    }

    /**
     * 计算抵扣金额
     *
     * @param chargeRecordList
     * @return
     */
    private BigDecimal getPreDepositDeduction(List<ChargeRecord> chargeRecordList) {
        BigDecimal preDepositDeduction = BigDecimal.ZERO;
        Iterator iter = chargeRecordList.iterator();
        while (iter.hasNext()) {
            ChargeRecord chargeRecord = (ChargeRecord) iter.next();
            preDepositDeduction = preDepositDeduction.add(chargeRecord.getPrechargeDeductionMoney());
        }
        preDepositDeduction = preDepositDeduction.setScale(4, RoundingMode.HALF_UP);
        return preDepositDeduction;
    }

    @Override
    public void exportSummaryBill(HttpServletRequest request, HttpServletResponse response, SummaryBill summaryBill)
            throws Exception {
        List<Map<String, Object>> sheetsList = getSheetsList(summaryBill);
        // 执行方法
        Workbook workbook = ExcelExportUtil.exportExcel(sheetsList, ExcelType.HSSF);
        ExcelUtil.downLoadExcel("扎帐信息" + DateUtil.format(LocalDateTime.now(), EXPOR_DATE_FORMAT) + ".xls", response, workbook);
    }

    @Override
    public R<Page<ChargeRecord>> pageChargeRecordList(PageParams<ChargeRecordPageDTO> params) {
        LocalDateTime createTimeSt = params.getModel().getCreateTimeSt();
        LocalDateTime createTimeEd = params.getModel().getCreateTimeEd();
        Map<String, String> map = new HashMap<>(8);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if(ObjectUtil.isNotNull(createTimeSt) && ObjectUtil.isNotNull(createTimeEd)) {
            map.put("createTime_st", dateTimeFormatter.format(createTimeSt));
            map.put("createTime_ed", dateTimeFormatter.format(createTimeEd));
            params.setMap(map);
        }
        else if(ObjectUtil.isNull(createTimeSt) && ObjectUtil.isNotNull(createTimeEd)){
            map.put("createTime_ed", dateTimeFormatter.format(createTimeEd));
            params.setMap(map);
        }
        else if(ObjectUtil.isNotNull(createTimeSt) && ObjectUtil.isNull(createTimeEd)){
            map.put("createTime_st", dateTimeFormatter.format(createTimeSt));
            params.setMap(map);
        }
        ChargeRecordPageDTO model = ChargeRecordPageDTO
                .builder()
                .createUserId(BaseContextHandler.getUserId())
                .summaryBillStatus(SummaryBillStatusEnum.UNBILL.getCode())
                .build();
        params.setModel(model);
        return chargeRecordBizApi.page(params);
    }

    @Override
    public R<Page<Invoice>> pageInvoiceList(PageParams<InvoicePageDTO> params) {
        return invoiceBizApi.pageInvoicesByTimeFrame(params.getCurrent(), params.getSize(), params.getModel().getCreateTimeSt(), params.getModel().getCreateTimeEd());
    }

    @Override
    public R<Page<ChargeRecord>> queryChargeRecordList(long current,
                                                              long size, SummaryBill summaryBill) {
        return summaryBillBizApi.pageSummaryChargeRecord(current, size, summaryBill.getId());
    }

    @Override
    public R<Page<Invoice>> queryInvoiceList(Integer current,
                                                    Integer size, SummaryBill summaryBill) {

        return invoiceBizApi.pageInvoice(current, size, summaryBill.getId());
    }

    private List<Map<String, Object>> getSheetsList(SummaryBill summaryBill) throws Exception {
        List<Map<String, Object>> lists = new ArrayList<>();
        //查询扎帐信息
        SummaryBill summaryBillClone = ObjectUtil.cloneByStream(summaryBill);
        String summaryBillStatus = "";
        switch (summaryBillClone.getSummaryBillStatus()){
            case "UNBILL":
                summaryBillStatus = "未扎帐";
                break;
            case "BILLED":
                summaryBillStatus = "已扎帐";
                break;
            default:
                break;
        }
        summaryBillClone.setSummaryBillStatus(summaryBillStatus);
        List<SummaryBill> summaryBillGets = new ArrayList<>();
        summaryBillGets.add(summaryBillClone);
        //扎帐信息map
        Map<String, Object> summaryBillMap = summaryBillSheet(lists, summaryBillGets);
        //查询收费信息
        R<List<ChargeRecord>> chargeRecordListR = chargeRecordBizApi.queryChargesRecord(summaryBill.getSummaryStartDate(), summaryBill.getSummaryEndDate());
        if(chargeRecordListR.getIsError()){
            log.error("查询一段时间内的缴费记录异常");
            throw new BizException("查询一段时间内的缴费记录异常");
        }
        List<ChargeRecord> chargeRecordListGet = null;
        if(ObjectUtil.isNotNull(chargeRecordListR.getData()) && chargeRecordListR.getData().size() > 0) {
            chargeRecordListGet = chargeRecordListR.getData();
        }
        //收费明细map
        Map<String, Object> chargeRecordDetailMap = chargeDetailSheet(lists, chargeRecordListGet);
        //查询开票明细
        //发票明细
        R<List<Invoice>>  invoiceListR = invoiceBizApi.queryInvoicesByTimeFrame(summaryBill.getSummaryStartDate(), summaryBill.getSummaryEndDate());
        if(invoiceListR.getIsError()){
            log.error("关联缴费数据查询发票异常");
            throw new BizException("关联缴费数据查询发票异常");
        }
        List<Invoice> invoiceListGet = null;
        if(ObjectUtil.isNotNull(invoiceListR.getData()) && invoiceListR.getData().size() > 0) {
            invoiceListGet = invoiceListR.getData();
        }
        //票据明细
        List<ChargeRecord> receiptListGet = chargeRecordListGet.stream()
                .filter(chargeRecord -> ReceiptStateEnum.GIVE_RECEIPT_NOT_INVOICE.getCode().equals(chargeRecord.getReceiptStatus()))
                .collect(Collectors.toList());
        //开票明细map
        Map<String, Object> receiptInvoiceDetailMap = receiptInvoiceDetail(lists, invoiceListGet, receiptListGet);
        // 将sheet使用的map进行包装
        List<Map<String, Object>> sheetsList = new ArrayList<>();
        sheetsList.add(summaryBillMap);
        sheetsList.add(chargeRecordDetailMap);
        sheetsList.add(receiptInvoiceDetailMap);
        return sheetsList;
    }

    /**
     * 开票明细sheet
     *
     * @param lists
     * @param invoiceListGet
     * @param receiptListGet
     */
    private Map<String, Object> receiptInvoiceDetail(List<Map<String, Object>> lists, List<Invoice> invoiceListGet, List<ChargeRecord> receiptListGet) {
        List<ReceiptInvoiceRecordDetail> invoiceReceiptList = new ArrayList<>();
        // 创建参数对象
        ExportParams exportParams = new ExportParams();
        // 设置sheet得名称
        exportParams.setSheetName("开票明细");
        if(ObjectUtil.isNotNull(invoiceListGet) && invoiceListGet.size() > 0){
            invoiceListGet.stream().forEach(invoice -> {
                ReceiptInvoiceRecordDetail receiptInvoiceRecordDetail = ReceiptInvoiceRecordDetail.builder()
                        .invoiceReceiptNumber(invoice.getInvoiceNumber())
                        .invoiceReceiptType(invoice.getInvoiceType())
                        .invoiceReceiptKind(invoice.getInvoiceKind())
                        .invoiceReceiptStatus(invoice.getInvoiceStatus())
                        .name(invoice.getBuyerName())
                        .tinNo(invoice.getBuyerTinNo())
                        .addrPhone(invoice.getBuyerAddress() + invoice.getBuyerPhone())
                        .bankAccount(invoice.getBuyerBankName() + invoice.getBuyerBankAccount())
                        .telphone(invoice.getTelphone())
                        .receiptInvoiceAmount(invoice.getPriceTaxTotalLower())
                        .billingTime(invoice.getBillingDate())
                        .build();
    //            BeanUtil.copyProperties(receiptInvoiceRecordDetail, invoiceReceiptMap);
                invoiceReceiptList.add(receiptInvoiceRecordDetail);
            });
        }
        if(ObjectUtil.isNotNull(receiptListGet) && receiptListGet.size() > 0) {
            receiptListGet.stream().forEach(receipt -> {
                ReceiptInvoiceRecordDetail receiptInvoiceRecordDetail = ReceiptInvoiceRecordDetail.builder()
                        .invoiceReceiptNumber(receipt.getChargeNo())
                        .invoiceReceiptType(InvoiceTypeEnum.RECEIPT.getCode())
                        .invoiceReceiptStatus(receipt.getInvoiceStatus())
                        .receiptInvoiceAmount(receipt.getChargeMoney())
                        .billingTime(receipt.getCreateTime())
                        .build();
                invoiceReceiptList.add(receiptInvoiceRecordDetail);
            });
        }

        // 创建sheet使用的map
        Map<String, Object> receiptInvoiceRecordDetailMap = new HashMap<>(4);
        // title的参数为ExportParams类型
        receiptInvoiceRecordDetailMap.put("title", exportParams);
        // 模版导出对应得实体类型
        receiptInvoiceRecordDetailMap.put("entity", ReceiptInvoiceRecordDetail.class);
        // sheet中要填充得数据
        receiptInvoiceRecordDetailMap.put("data", invoiceReceiptList);
        return receiptInvoiceRecordDetailMap;
    }

    /**
     * 查询收费明细
     *
     * @param startTime
     * @param endTime
     * @return
     */
    private List<ChargeRecord> getChargeRecords(LocalDateTime startTime, LocalDateTime endTime) {
        R<List<ChargeRecord>> chargeRecordListR = chargeRecordBizApi.queryChargesRecord(startTime, endTime);
        if(chargeRecordListR.getIsError()){
            log.error("查询收费明细异常");
            throw new BizException("查询收费明细异常");
        }
        return (ObjectUtil.isNull(chargeRecordListR.getData()) || chargeRecordListR.getData().size() == 0) ? null : chargeRecordListR.getData();
    }

    /**
     * 查询收费明细
     * @param summaryBillGets
     * @return
     */
    private List<ChargeRecord> getChargeRecords(List<SummaryBill> summaryBillGets) {
        List<Long> ids = summaryBillGets.stream().map(SummaryBill::getId).collect(Collectors.toList());
        R<List<ChargeRecord>> chargeRecordListR = chargeRecordBizApi.queryList(ids);
        if(chargeRecordListR.getIsError()){
            log.error("查询收费明细异常");
            throw new BizException("查询收费明细异常");
        }
        return (ObjectUtil.isNull(chargeRecordListR.getData()) || chargeRecordListR.getData().size() == 0) ? null : chargeRecordListR.getData();
    }

    /**
     * 收费明细sheet
     *
     * @param lists
     * @param chargeRecordListGet
     */
    private Map<String, Object> chargeDetailSheet(List<Map<String, Object>> lists, List<ChargeRecord> chargeRecordListGet) {
        List<ChargeRecordDetail> chargeRecordDetailList = new ArrayList<>();
        // 创建参数对象
        ExportParams exportParams = new ExportParams();
        // 设置sheet得名称
        exportParams.setSheetName("收费明细");

        Iterator iter = chargeRecordListGet.iterator();
        while (iter.hasNext()) {
            //合计金额
            BigDecimal totalAmount = BigDecimal.ZERO;
            //合计气量
            BigDecimal gasTotal = BigDecimal.ZERO;
            ChargeRecord chargeRecord = (ChargeRecord) iter.next();
            totalAmount = totalAmount.add(chargeRecord.getActualIncomeMoney()).subtract(chargeRecord.getGiveChange())
                    .add(chargeRecord.getPrechargeDeductionMoney());
            totalAmount = totalAmount.setScale(4, RoundingMode.HALF_UP);
            gasTotal = gasTotal.add(chargeRecord.getRechargeGas());
            //转换发票状态
            String invoiceStatus = "";
            switch (chargeRecord.getInvoiceStatus()){
                case "OPENED":
                    invoiceStatus = "已开";
                    break;
                case "NOT_OPEN":
                    invoiceStatus = "未开";
                    break;
                default:
                    break;
            }
            //查询缴费编号
            R<List<CustomerGasMeterRelated>> customerGasMeterRelatedListR = customerGasMeterRelatedBizApi.query(new CustomerGasMeterRelated()
                    .setGasMeterCode(chargeRecord.getGasMeterCode()).setCustomerCode(chargeRecord.getCustomerCode()));
            if(customerGasMeterRelatedListR.getIsError() ||
                    ObjectUtil.isNull(customerGasMeterRelatedListR.getData()) ||
                    customerGasMeterRelatedListR.getData().size() == 0){
                throw new BizException("查询缴费编号异常或者查询记录为空");
            }
            List<CustomerGasMeterRelated> customerGasMeterRelatedList = customerGasMeterRelatedListR.getData();
            String customerChargeNo = null;
            if(customerGasMeterRelatedList.size() == 1){
                customerChargeNo = customerGasMeterRelatedList.get(0).getCustomerChargeNo();
            }
            else if(customerGasMeterRelatedList.size() > 1){
                customerGasMeterRelatedList = customerGasMeterRelatedList.stream().filter(item -> item.getDataStatus().equals(DataStatusEnum.NORMAL.getValue())).collect(Collectors.toList());
                customerChargeNo = customerGasMeterRelatedList.get(0).getCustomerChargeNo();
            }
            //组装excel数据
            ChargeRecordDetail chargeRecordDetail = ChargeRecordDetail.builder()
                    .chargeNo(chargeRecord.getChargeNo())
                    .customerChargeNo(customerChargeNo)
                    .chargeTime(chargeRecord.getCreateTime())
                    .chargeMethodCode(chargeRecord.getChargeMethodCode())
                    .chargeMethodName(chargeRecord.getChargeMethodName())
                    .totalAmount(totalAmount)
                    .rechargeGiveMoney(chargeRecord.getRechargeGiveMoney())
                    .rechargeGiveGas(chargeRecord.getRechargeGiveGas())
                    .gasTotal(gasTotal)
                    .customerCode(chargeRecord.getCustomerCode())
                    .customerName(chargeRecord.getCustomerName())
                    .invoceStatus(invoiceStatus)
                    .build();
//            BeanUtil.copyProperties(chargeRecordDetail, chargeRecordMap);
            chargeRecordDetailList.add(chargeRecordDetail);
        }
        // 创建sheet使用得map
        Map<String, Object> chargeRecordDetailMap = new HashMap<>(4);
        // title的参数为ExportParams类型
        chargeRecordDetailMap.put("title", exportParams);
        // 模版导出对应得实体类型
        chargeRecordDetailMap.put("entity", ChargeRecordDetail.class);
        // sheet中要填充得数据
        chargeRecordDetailMap.put("data", chargeRecordDetailList);
        return chargeRecordDetailMap;
    }

    /**
     * 扎帐信息sheet
     *
     * @param lists
     * @param summaryBillGets
     */
    private Map<String, Object> summaryBillSheet(List<Map<String, Object>> lists, List<SummaryBill> summaryBillGets) {
        // 创建参数对象
        ExportParams exportParams = new ExportParams();
        // 设置sheet得名称
        exportParams.setSheetName("扎帐信息");
        // 创建sheet1使用得map
        Map<String, Object> summaryBillMap = new HashMap<>(4);
        // title的参数为ExportParams类型
        summaryBillMap.put("title", exportParams);
        // 模版导出对应得实体类型
        summaryBillMap.put("entity", SummaryBill.class);
        // sheet中要填充得数据
        summaryBillMap.put("data", summaryBillGets);
        return summaryBillMap;
//        Map<String, Object> summaryBillMap = new HashMap<String, Object>();
//        BeanUtil.copyProperties(summaryBillGet, summaryBillMap);
//        List<Map<String, Object>> summaryBilList = new ArrayList<>();
//        summaryBilList.add(summaryBillMap);
//        Map<String, Object> tempSummaryBill = WorkBookUtils.createOneSheet("扎帐信息", "扎帐信息", SummaryBill.class, summaryBilList);
//        lists.add(tempSummaryBill);
    }

    /**
     * 获取当日操作员扎帐信息
     *
     * @param summaryBill
     * @return
     * @throws Exception
     */
    private List<SummaryBill> getSummaryBill(SummaryBill summaryBill) throws Exception {
        SummaryBill summaryBillParam = SummaryBill
                .builder()
                .id(summaryBill.getId())
                .build();
        R<List<SummaryBill>> summaryBillListR = summaryBillBizApi.query(summaryBillParam);
        if(summaryBillListR.getIsError()){
            log.error("查询扎帐信息异常");
            throw new BizException("查询扎帐信息异常");
        }
        List<SummaryBill> summaryBillList = (ObjectUtil.isNull(summaryBillListR.getData()) || summaryBillListR.getData().size() == 0) ? null : summaryBillListR.getData();
        if (summaryBillList.size() > 1) {
            throw new Exception(getLangMessage(MessageConstants.REDUNDANT_SUMMARY_BILL_DATA));
        }
        return summaryBillList;
    }

    /**
     * 查询发票
     * @param startTime
     * @param endTime
     * @return
     */
    private List<Invoice> getInvoices(LocalDateTime startTime, LocalDateTime endTime) {
        R<List<Invoice>> invoiceListR = invoiceBizApi.queryInvoicesByTimeFrame(startTime, endTime);
        if(invoiceListR.getIsError()){
            log.error("查询发票信息异常");
            throw new BizException("查询发票信息异常");
        }
        return (ObjectUtil.isNull(invoiceListR.getData()) || invoiceListR.getData().size() == 0) ? null : invoiceListR.getData();
    }
    /**
     * 根据扎帐ID查询发票
     * @param summaryId
     * @return
     */
    private List<Invoice> getInvoices(long summaryId) {
        R<List<Invoice>> invoiceListR = invoiceBizApi.queryInvoicesBySummaryId(summaryId);
        if(invoiceListR.getIsError()){
            log.error("查询发票信息异常");
            throw new BizException("查询发票信息异常");
        }
        return (ObjectUtil.isNull(invoiceListR.getData()) || invoiceListR.getData().size() == 0) ? null : invoiceListR.getData();
    }

    /**
     * 获取扎帐日期
     *
     * @return
     */
    private String getSummaryBillDate() {
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return localDate.format(dateTimeFormatter);
    }

    /**
     * 计算发票金额
     *
     * @param invoiceList
     * @return
     */
    private BigDecimal caculateInvoiceAmount(List<Invoice> invoiceList) {
        BigDecimal invoiceAmount = BigDecimal.ZERO;
        Iterator iter = invoiceList.iterator();
        while (iter.hasNext()) {
            Invoice invoice = (Invoice) iter.next();
            invoiceAmount = invoiceAmount.add(invoice.getPriceTaxTotalLower());
        }
        invoiceAmount = invoiceAmount.setScale(4, RoundingMode.HALF_UP);
        return invoiceAmount;
    }

    /**
     * 计算票据金额
     *
     * @param receiptList
     * @return
     */
    private BigDecimal caculateReceiptAmount(List<Receipt> receiptList) {
        BigDecimal receiptAmount = BigDecimal.ZERO;
        List<Receipt> receiptListFilter = receiptList.stream().filter(receipt ->
                receipt.getReceiptState().equals(Integer.parseInt(ReceiptStateEnum.NOT_GIVE_RECEIPT.getCode()))
                        || receipt.getReceiptState().equals(Integer.parseInt(ReceiptStateEnum.GIVE_RECEIPT_NOT_INVOICE.getCode()))).collect(Collectors.toList());
        Iterator iter = receiptListFilter.iterator();
        while (iter.hasNext()) {
            Receipt receipt = (Receipt) iter.next();
            receiptAmount = receiptAmount.add(receipt.getActualCollection())
                    .subtract(receipt.getGiveChange())
                    .add(receipt.getDeductAmount());
        }
        receiptAmount = receiptAmount.setScale(4, RoundingMode.HALF_UP);
        return receiptAmount;
    }

    /**
     * 计算票数
     *
     * @param invoiceList
     * @param invoiceKind
     * @return
     */
    private long caculateInvoiceNum(List<Invoice> invoiceList, String invoiceKind) {
        return invoiceList.stream()
                .filter(invoice -> invoice.getInvoiceKind().equals(invoiceKind)).count();
    }

    /**
     * 计算金额
     *
     * @param chargeRecordList
     * @param enumCode
     * @return
     */
    private BigDecimal calculateFee(List<ChargeRecord> chargeRecordList, String enumCode) {
        BigDecimal amount = BigDecimal.ZERO;
        List<ChargeRecord> amountList = chargeRecordList.stream()
                .filter(chargeRecord -> chargeRecord.getChargeMethodCode().equals(enumCode)).collect(Collectors.toList());
        amount = getTotalAmount(amountList, amount);
        return amount;
    }

}
