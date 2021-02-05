
package com.cdqckj.gmis.invoice.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.DeleteStatusEnum;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.charges.entity.ChargeItemRecord;
import com.cdqckj.gmis.charges.entity.ChargeRecord;
import com.cdqckj.gmis.charges.enums.ChargeItemEnum;
import com.cdqckj.gmis.charges.service.ChargeItemRecordService;
import com.cdqckj.gmis.charges.service.ChargeRecordService;
import com.cdqckj.gmis.charges.service.SummaryBillDetailService;
import com.cdqckj.gmis.common.domain.code.CodeNotLost;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.domain.sts.InvoiceDayStsVo;
import com.cdqckj.gmis.common.utils.BizCodeNewUtil;
import com.cdqckj.gmis.common.utils.UserOrgIdUtil;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.database.mybatis.auth.DataScope;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.exception.code.ExceptionCode;
import com.cdqckj.gmis.invoice.dao.InvoiceMapper;
import com.cdqckj.gmis.invoice.dto.InvoiceAddDTO;
import com.cdqckj.gmis.invoice.dto.InvoiceCallbackDTO;
import com.cdqckj.gmis.invoice.dto.InvoiceChDTO;
import com.cdqckj.gmis.invoice.dto.InvoicePageDTO;
import com.cdqckj.gmis.invoice.dto.rhapi.KeyStoreInfo;
import com.cdqckj.gmis.invoice.dto.rhapi.ResponseData;
import com.cdqckj.gmis.invoice.dto.rhapi.einvoice.*;
import com.cdqckj.gmis.invoice.entity.*;
import com.cdqckj.gmis.invoice.enumeration.*;
import com.cdqckj.gmis.invoice.service.*;
import com.cdqckj.gmis.invoice.util.MoneyUtil;
import com.cdqckj.gmis.invoice.vo.InvoiceResponseData;
import com.cdqckj.gmis.systemparam.entity.InvoiceParam;
import com.cdqckj.gmis.systemparam.entity.InvoicePlatConfig;
import com.cdqckj.gmis.systemparam.entity.TollItem;
import com.cdqckj.gmis.systemparam.service.InvoiceParamService;
import com.cdqckj.gmis.systemparam.service.InvoicePlatConfigService;
import com.cdqckj.gmis.systemparam.service.TollItemService;
import com.cdqckj.gmis.utils.StrPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


/**
 * <p>
 * 业务实现类
 *
 * </p>
 *
 * @author songyz
 * @date 2020-09-07
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class InvoiceServiceImpl extends SuperServiceImpl<InvoiceMapper, Invoice> implements InvoiceService {

    private static String RETRY_KP = "重开发票";

    @Value("${gmis.e-invoice.api.rh.invoice.callbackUrl}")
    String callbackUrl;

    @Autowired
    private ChargeRecordService chargeRecordService;
    @Autowired
    private ChargeItemRecordService chargeItemRecordService;
    @Autowired
    private TollItemService tollItemService;
    @Autowired
    private InvoiceParamService invoiceParamService;
    @Autowired
    private InvoiceDetailsService invoiceDetailsService;
    @Autowired
    private BuyerTaxpayerInfoService buyerTaxpayerInfoService;
    @Autowired
    private InvoicePlatConfigService invoicePlatConfigService;
    @Autowired
    private InvoicePushRecordService invoicePushRecordService;
    @Autowired
    private RhInvoiceApiService rhInvoiceApiService;
    @Autowired
    InvoiceCallbackRecordService invoiceCallbackRecordService;
    @Autowired
    private SummaryBillDetailService summaryBillDetailService;

    @Override
    public IPage<Invoice> findPage(PageParams<InvoicePageDTO> pageParams, IPage<Invoice> page) {
        InvoicePageDTO pageDTO = pageParams.getModel();
        LbqWrapper<Invoice> wrapper = Wraps.lbQ();
        wrapper.in(Invoice::getInvoiceType, pageDTO.getInvoiceTypeList())
                .eq(pageDTO.getInvalidStatus() == 1, Invoice::getInvalidStatus, pageDTO.getInvalidStatus())
                .in(Invoice::getInvoiceKind, pageDTO.getInvoiceKindList())
                .in(Invoice::getInvoiceStatus, pageDTO.getInvoiceStatusList())
                .and(!StringUtils.isEmpty(pageDTO.getQueryString()),
                        wrapperSql -> wrapperSql.like(Invoice::getInvoiceNumber, pageDTO.getQueryString())
                                .or().like(Invoice::getPayNo, pageDTO.getQueryString())
                                .or().like(Invoice::getBuyerName, pageDTO.getQueryString())
                                .or().like(Invoice::getTelphone, pageDTO.getQueryString())
                                .or().like(Invoice::getCustomerName, pageDTO.getQueryString())
                                .or().like(Invoice::getGasmeterName, pageDTO.getQueryString())
                                .or().like(Invoice::getCustomerCode, pageDTO.getQueryString())
                                .or().like(Invoice::getGasmeterCode, pageDTO.getQueryString()))
                .orderByDesc(Invoice::getBillingDate);

        return baseMapper.findPage(page, wrapper, new DataScope());
    }

    @Override
    public Integer checkInvoiceNumber(String invoiceNumber) {
        LbqWrapper<Invoice> wrapper = Wraps.lbQ();
        wrapper.eq(Invoice::getInvoiceNumber, invoiceNumber);
        return super.count(wrapper);
    }

    /**
     * 通过缴费单创建发票
     *
     * @param invoiceAddDTO 页面填写数据
     * @return 创建的发票
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public InvoiceResponseData createInvoiceByChargeNo(InvoiceAddDTO invoiceAddDTO) {
        InvoiceResponseData invoiceResponseData = InvoiceResponseData.builder().build();
        //获取缴费数据及详情数据
        ChargeRecord chargeRecord = chargeRecordService.getById(invoiceAddDTO.getChargeId());
        if (Objects.isNull(chargeRecord)) {
            throw new BizException(ExceptionCode.NOT_FOUND.getCode(), "未查询到对应的缴费数据");
        }
        LbqWrapper<ChargeItemRecord> itemWrapper = Wraps.lbQ();
        itemWrapper.eq(ChargeItemRecord::getChargeNo, invoiceAddDTO.getChargeNo())
                .eq(ChargeItemRecord::getDataStatus, DataStatusEnum.NORMAL.getValue());
        List<ChargeItemRecord> chargeItemRecords = chargeItemRecordService.list(itemWrapper);
        //缴费明细转换为发票明细
        List<InvoiceDetails> details = convertInvoiceDetails(chargeItemRecords);

        //查询销售方税务信息
        InvoiceParam invoiceParam = getInvoiceParam(chargeRecord.getCompanyCode());
        //4、保存发票记录
        Invoice invoice = saveInvoice(invoiceAddDTO, chargeRecord, invoiceParam, details);
        //保存发票明细
        String invoiceId = String.valueOf(invoice.getId());
        saveInvoiceDetails(details, invoiceId);
        //更新购买方税务信息表数据
        mergeBuyerTaxpayerInfo(invoice);
        //收据发票状态修改
        chargeRecordService.updateReceiptOrInvoiceStatus(chargeRecord.getChargeNo(),
                invoiceAddDTO.getInvoiceType(), com.cdqckj.gmis.charges.enums.InvoiceStatusEnum.OPENED.name());

        if (InvoiceTypeEnum.ELECTRONIC_INVOICE.name().equals(invoiceAddDTO.getInvoiceType())) {
            //电子发票-推送发票平台
            ResponseData responseData = pushInvoicePlatKp(invoice, details);
            invoiceResponseData.setCode(responseData.getCode());
            invoiceResponseData.setMessage(responseData.getMessage());
            invoiceResponseData.setInvoice(invoice);
        }

        return invoiceResponseData;
    }

    /**
     * @param cxParam
     */
    @Override
    public ResponseData invoiceCx(CxParam cxParam) {
        ResponseData result = null;
        InvoicePlatConfig invoicePlatConfig = queryInvoicePlatConfig();
        if (InvoicePlatEnum.RUI_HONG.getDesc().equals(invoicePlatConfig.getPlatName())) {
            //瑞宏网平台推送
            KeyStoreInfo signParam = JSONUtil.toBean(invoicePlatConfig.getOtherParam(), KeyStoreInfo.class);
            signParam.setAppCode(invoicePlatConfig.getOpenId());
            try {
                result = rhInvoiceApiService.pushInvoiceCx(signParam, cxParam);
            } catch (Exception e) {
                log.error("查询失败：" + e.getMessage());
                e.printStackTrace();
                throw new BizException(e.getMessage());
            }
        }
        return result;
    }

    /**
     * 冲红发票
     *
     * @param invoiceChDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CodeNotLost
    public ResponseData invoiceCh(InvoiceChDTO invoiceChDTO) {

        Invoice invoice = getById(invoiceChDTO.getInvoiceId());
        ResponseData responseData = ResponseData.builder().build();
        if (Objects.isNull(invoice)) {
            responseData.setCode("0");
            responseData.setMessage("未开票");
            return responseData;
        }
        if (InvoiceStatusEnum.OPENING.getCode().equals(invoice.getInvoiceStatus())) {
            throw new BizException("开票未完成，待开票出结果后才能继续退费");
        }
        if (InvoiceStatusEnum.OPEN_FAIL.getCode().equals(invoice.getInvoiceStatus())) {
            responseData.setCode("0");
            responseData.setMessage("未开票");
            return responseData;
        }
        //新增一张红票
        Invoice redInvoice = convertToRedInvoice(invoiceChDTO, invoice);
        return pushInvoiceCh(invoice, redInvoice);
    }

    /**
     * 推送冲红
     *
     * @param blueInvoice
     * @param redInvoice
     * @return
     */
    private ResponseData pushInvoiceCh(Invoice blueInvoice, Invoice redInvoice) {
        InvoicePushRecord record = InvoicePushRecord.builder()
                .invoiceId(redInvoice.getId())
                .invoiceNumber(redInvoice.getInvoiceNumber())
                .pushDate(LocalDate.now())
                .build();
        ResponseData result;
        InvoicePlatConfig invoicePlatConfig = queryInvoicePlatConfig();
        if (InvoicePlatEnum.RUI_HONG.getDesc().equals(invoicePlatConfig.getPlatName())) {
            //冲红参数组装
            Map<String, String> extendedParams = new HashMap<>();
            extendedParams.put("invoiceId", redInvoice.getId().toString());
            String tenant = BaseContextHandler.getTenant();
            Map<String, String> dynamicParams = new HashMap<>();
            String backUrl = callbackUrl + "?tenantCode=" + tenant;
            dynamicParams.put("callbackUrl", backUrl);
            log.info("callbackUrl: " + backUrl);
            ChParams chParams = ChParams.builder()
                    .serialNo(UUID.randomUUID().toString().replace("-", ""))
                    .originalCode(blueInvoice.getInvoiceCodeNo())
                    .postTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                    .reason(redInvoice.getRemark())
                    .extendedParams(extendedParams)
                    .dynamicParams(dynamicParams)
                    .build();
            //瑞宏网平台推送
            KeyStoreInfo signParam = JSONUtil.toBean(invoicePlatConfig.getOtherParam(), KeyStoreInfo.class);
            signParam.setAppCode(invoicePlatConfig.getOpenId());
            Invoice update = Invoice.builder().id(redInvoice.getId()).build();
            try {
                result = rhInvoiceApiService.pushInvoiceCh(signParam, chParams);
                if (StrPool.ZERO.equals(result.getCode())) {
                    log.info("推送成功");
                    update.setInvoiceStatus(InvoiceStatusEnum.OPENING.getCode());
                    update.setInvoiceSerialNumber(result.getSerialNo());
                } else {
                    log.info("推送失败" + result.getMessage());
                    update.setInvoiceStatus(InvoiceStatusEnum.OPEN_FAIL.getCode());
                    update.setInvoiceSerialNumber(result.getSerialNo());
                    update.setInvoiceResult(result.getMessage());
                }
                record.setPushUrl(result.getApiUrl());
                record.setPushResult(result.toString());
                record.setPushResultCode(result.getCode());
                record.setSerialNo(result.getSerialNo());
            } catch (Exception e) {
                log.error("冲红失败：" + e.getMessage());
                throw new BizException(e.getMessage());
            }
            //更新发票状态
            updateById(update);
            //记录日志
            record.setPushCmdName(ApiCmdNameEnum.INVOICE_KP.getCode());
            record.setPushData(JSONUtil.toJsonStr(chParams));
            record.setPushPlat(InvoicePlatEnum.RUI_HONG.getDesc());
            invoicePushRecordService.save(record);
        } else {
            result = ResponseData.builder().build();
        }
        return result;
    }

    /**
     * 补打发票
     *
     * @param invoiceAddDTO 发票id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public InvoiceResponseData retryPrint(InvoiceAddDTO invoiceAddDTO) {
        Invoice invoice = getById(invoiceAddDTO.getInvoiceId());
        InvoiceResponseData response = InvoiceResponseData.builder().build();
        if (InvoiceTypeEnum.ELECTRONIC_INVOICE.eq(invoice.getInvoiceType())) {
            if (InvoiceKindEnum.BLUE_INVOICE.getCode().equals(invoice.getInvoiceKind())) {
                List<InvoiceDetails> details = invoiceDetailsService.getByInvoiceId(invoiceAddDTO.getInvoiceId());
                ResponseData responseData = pushInvoicePlatKp(invoice, details);
                response.setCode(responseData.getCode());
                response.setMessage(responseData.getMessage());
                response.setInvoice(invoice);
                return response;
            }
            if (InvoiceKindEnum.RED_INVOICE.getCode().equals(invoice.getInvoiceKind())) {
                LbqWrapper<Invoice> itemWrapper = Wraps.lbQ();
                itemWrapper.eq(Invoice::getBlueInvoiceNumber, invoice.getBlueInvoiceNumber())
                        .eq(Invoice::getBlueSerialNumber, invoice.getBlueSerialNumber());
                Invoice blueInvoice = baseMapper.selectOne(itemWrapper);
                if (null == blueInvoice) {
                    throw new BizException("没有找到对应蓝票");
                }
                ResponseData responseData = pushInvoiceCh(blueInvoice, invoice);
                response.setCode(responseData.getCode());
                response.setMessage(responseData.getMessage());
                response.setInvoice(invoice);
                return response;
            }
        }
        return response.setCode("0").setMessage("非电子发票，未处理");
    }

    /**
     * 重新开票
     *
     * @param invoiceAddDTO 重开发票参数
     * @return 重开结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public InvoiceResponseData retryKp(InvoiceAddDTO invoiceAddDTO) {
        Invoice invoice = super.getById(invoiceAddDTO.getInvoiceId());
        String invoiceStatus = invoice.getInvoiceStatus();
        InvoiceResponseData response = new InvoiceResponseData();
        if (!InvoiceKindEnum.BLUE_INVOICE.getCode().equals(invoice.getInvoiceKind())) {
            response.setCode("-1");
            response.setMessage("蓝票才能重开");
            return response;
        }

        if (InvoiceStatusEnum.OPEN_SUCCESS.getCode().equals(invoiceStatus)) {
            //如果开票成功，重开需要作废或冲红原发票
            if (InvoiceTypeEnum.ELECTRONIC_INVOICE.eq(invoice.getInvoiceType())) {
                log.info("电子发票蓝票重开先冲红发票");
                InvoiceChDTO ch = InvoiceChDTO.builder()
                        .invoiceId(invoice.getId())
                        .reason(RETRY_KP)
                        .build();
                ResponseData responseData = invoiceCh(ch);
                if (!"0".equals(responseData.getCode())) {
                    //冲红失败 直接返回 不重开蓝票
                    response.setCode(responseData.getCode());
                    response.setMessage(responseData.getMessage());
                    return response;
                }
            } else {
                //作废发票
                log.info("非电子发票蓝票重开先作废发票");
                Invoice update = Invoice.builder()
                        .id(invoice.getId())
                        .invalidStatus(1)
                        .remark(RETRY_KP)
                        .build();
                updateById(update);
            }

        } else if (InvoiceStatusEnum.OPEN_FAIL.getCode().equals(invoiceStatus)) {
            //如果开票失败  重开先逻辑删除原发票  防止补打发票造成重复开票
            log.info("开票失败  重开先逻辑删除原发票，防止补打发票造成重复开票");
            Invoice update = Invoice.builder()
                    .id(invoice.getId())
                    .dataStatus(DataStatusEnum.NOT_AVAILABLE.getValue())
                    .remark(RETRY_KP)
                    .build();
            updateById(update);
        } else {
            //其它情况 提示 不能重开
            response.setCode("-1");
            response.setMessage("请核对发票状态。");
            return response;
        }
        //查询原发票明细记录
        List<InvoiceDetails> details = invoiceDetailsService.getByInvoiceId(invoice.getId());
        //保存重开的发票数据及明细
        Invoice newInvoice = saveRetryKp(invoiceAddDTO, invoice, details);

        if (InvoiceTypeEnum.ELECTRONIC_INVOICE.name().equals(invoiceAddDTO.getInvoiceType())) {
            log.info("重开发票推送数据");
            ResponseData responseData = pushInvoicePlatKp(newInvoice, details);
            response.setCode(responseData.getCode());
            response.setMessage(responseData.getMessage());
            response.setInvoice(newInvoice);
        } else {
            response.setCode("0").setMessage("非电子发票，未处理");
        }
        return response;
    }

    /**
     * 重开发票时 保存新发票记录
     *
     * @param invoiceAddDTO 重开时新增参数
     * @param invoice       原发票数据
     * @param details       原发票明细
     * @return 保存结果
     */
    private Invoice saveRetryKp(InvoiceAddDTO invoiceAddDTO, Invoice invoice, List<InvoiceDetails> details) {
        //查询销售方税务信息
        InvoiceParam invoiceParam = getInvoiceParam(invoice.getCompanyCode());
        //计算发票金额、税额
        BigDecimal totalTax = BigDecimal.ZERO;
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (InvoiceDetails detail : details) {
            detail.setId(null);
            totalTax = totalTax.add(detail.getTaxMoney());
            totalAmount = totalAmount.add(detail.getMoney());
        }

        Invoice entity = Invoice.builder()
                .invoiceNumber(BizCodeNewUtil.genInvoiceNoDataCode())
                .invoiceType(invoiceAddDTO.getInvoiceType())
                .buyerName(invoiceAddDTO.getBuyerName())
                .buyerTinNo(invoiceAddDTO.getBuyerTinNo())
                .buyerAddress(invoiceAddDTO.getBuyerAddress())
                .buyerPhone(invoiceAddDTO.getBuyerPhone())
                .buyerBankName(invoiceAddDTO.getBuyerBankName())
                .buyerBankAccount(invoiceAddDTO.getBuyerBankAccount())

                .payNo(invoice.getPayNo())
                .billingDate(LocalDateTime.now())
                .invoiceKind(InvoiceKindEnum.BLUE_INVOICE.getCode())
                .businessHallId(String.valueOf(invoice.getBusinessHallId()))
                .businessHallName(invoice.getBusinessHallName())
                .customerCode(invoice.getCustomerCode())
                .customerName(invoice.getCustomerName())
                .gasmeterCode(invoice.getGasmeterCode())

                .sellerName(invoiceParam.getName())
                .sellerTinNo(invoiceParam.getTaxpayerNo())
                .sellerAddress(invoiceParam.getAddress())
                .sellerPhone(invoiceParam.getTelephone())
                .sellerBankName(invoiceParam.getBankName())
                .sellerBankAccount(invoiceParam.getBankAccount())

                .drawerId(BaseContextHandler.getUserId())
                .drawerName(BaseContextHandler.getName())
                //税额
                .totalTax(totalTax.setScale(2, RoundingMode.HALF_UP))
                //不含税金额
                .totalAmount(totalAmount.subtract(totalTax).setScale(2, RoundingMode.HALF_UP))
                //含税金额
                .priceTaxTotalLower(totalAmount.setScale(2, RoundingMode.HALF_UP))
                .priceTaxTotalUpper(MoneyUtil.amountToChinese(totalAmount.doubleValue()))

                .orgId(invoice.getOrgId())
                .orgName(invoice.getOrgName())
                .dataStatus(DataStatusEnum.NORMAL.getValue())
                .invoiceStatus(InvoiceStatusEnum.NOT_OPEN.getCode())
                .createTime(LocalDateTime.now())
                .build();
        save(entity);
        //保存发票明细
        String invoiceId = String.valueOf(entity.getId());
        saveInvoiceDetails(details, invoiceId);
        return entity;
    }

    /**
     * 查询配置的销售方开票参数
     *
     * @param companyCode 公司编码
     * @return 开票参数
     */
    private InvoiceParam getInvoiceParam(String companyCode) {
        LbqWrapper<InvoiceParam> paramWrapper = Wraps.lbQ();
        paramWrapper.eq(InvoiceParam::getCompanyCode, companyCode)
                .eq(InvoiceParam::getInvoiceStatus, DataStatusEnum.NORMAL.getValue())
                .eq(InvoiceParam::getDeleteStatus, DeleteStatusEnum.NORMAL.getValue());
        InvoiceParam invoiceParam = invoiceParamService.getOne(paramWrapper);
        if (Objects.isNull(invoiceParam)) {
            throw new BizException(ExceptionCode.NOT_FOUND.getCode(), "未查询到开票参数，请先配置开票参数");
        }
        return invoiceParam;
    }

    /**
     * 瑞宏网电子发票回调
     *
     * @param callback
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void rhKpCallback(InvoiceCallbackDTO callback) {
        InvoiceCallbackRecord entity = InvoiceCallbackRecord.builder()
                .tenant(callback.getTenant())
                .platCode(InvoicePlatEnum.RUI_HONG.name())
                .platName(InvoicePlatEnum.RUI_HONG.getDesc())
                .callbackDate(LocalDateTime.now())
                .callbackContent(callback.getResult())
                .build();
        invoiceCallbackRecordService.save(entity);
        ResponseData responseData = JSONUtil.toBean(callback.getResult(), ResponseData.class);
        Invoice invoice = Invoice.builder().build();
        if (StrPool.ZERO.equals(responseData.getCode())) {
            ResultInvoice resultInvoice = responseData.getInvoices().get(0);
            invoice.setId(Long.valueOf(responseData.getExtendedParams().get("invoiceId")));
            invoice.setInvoiceCodeNo(resultInvoice.getCode());
            invoice.setBillingDate(LocalDateTime.parse(resultInvoice.getGenerateTime(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            invoice.setInvoiceSerialNumber(responseData.getSerialNo());
            invoice.setPdfDownloadUrl(resultInvoice.getPdfUnsignedUrl());
            invoice.setInvoiceFileUrl(resultInvoice.getViewUrl());
            invoice.setInvoiceStatus(InvoiceStatusEnum.OPEN_SUCCESS.getCode());
            super.updateById(invoice);
        } else {
            //开票失败没有额外参数回来
            baseMapper.updateFailResultBySerialNo(responseData.getMessage(), responseData.getSerialNo());
        }
    }

    /**
     * 通过缴费编号查询对应的有效发票
     *
     * @param chargeNo 缴费编号
     * @return 发票数据
     */
    @Override
    public Invoice getEffectiveInvoice(String chargeNo) {
        return baseMapper.getEffectiveInvoice(chargeNo);
    }

    @Override
    public R<Page<Invoice>> pageInvoice(Integer current, Integer size, Long summaryId) {
        Page<Invoice> page = new Page<>(current, size);
//        Map<String, Object> columnMap = new HashMap<>(2);
//        columnMap.put("summary_bill_id", summaryBill.getId());
//        List<SummaryBillDetail>  summaryBillDetailList = summaryBillDetailService.listByMap(columnMap);
//        List<Long> chargeIdList = summaryBillDetailList.stream()
//                .filter(summaryBillDetail->summaryBillDetail.equals(SummaryBillSourceEnum.CHARGE.getCode()))
//                .map(SummaryBillDetail::getSourceId).collect(Collectors.toList());
        return R.success(baseMapper.queryInvoiceList(page, summaryId));
    }

    @Override
    public List<Invoice> queryInvoicesByTimeFrame(LocalDateTime startTime, LocalDateTime endTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String startTimeString = null;
        if(ObjectUtil.isNotNull(startTime)){
            startTimeString = startTime.format(dateTimeFormatter);
        }
        String endTimeString = null;
        if(ObjectUtil.isNotNull(endTime)){
            endTimeString = endTime.format(dateTimeFormatter);
        }
        return baseMapper.queryInvoicesByTimeFrame(startTimeString, endTimeString);
    }

    @Override
    public List<Invoice> queryInvoicesBySummaryId(Long summaryId) {
        return baseMapper.queryInvoicesBySummaryId(summaryId);
    }

    @Override
    public Page<Invoice> pageInvoicesByTimeFrame(long current, long size, LocalDateTime startTime, LocalDateTime endTime) {
        Page<Invoice> page = new Page<>(current, size);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String startTimeString = null;
        if(ObjectUtil.isNotNull(startTime)){
            startTimeString = startTime.format(dateTimeFormatter);
        }
        String endTimeString = null;
        if(ObjectUtil.isNotNull(startTime)){
            endTimeString = endTime.format(dateTimeFormatter);
        }
        return baseMapper.pageInvoicesByTimeFrame(page, startTimeString, endTimeString);
    }

    /**
     * 保存发票记录
     *
     * @param invoiceAddDTO 前端上传的购买方纳税信息等数据
     * @param chargeRecord  后台查询的缴费单数据
     * @param invoiceParam  后台查询的销售方纳税信息等数据
     * @param details       发票明细
     * @return
     */

    private Invoice saveInvoice(InvoiceAddDTO invoiceAddDTO, ChargeRecord chargeRecord, InvoiceParam invoiceParam, List<InvoiceDetails> details) {

        BigDecimal totalTax = BigDecimal.ZERO;
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (InvoiceDetails detail : details) {
            totalTax = totalTax.add(detail.getTaxMoney());
            totalAmount = totalAmount.add(detail.getMoney());
        }

        Invoice entity = Invoice.builder()
//                .invoiceNumber(BizCodeUtil.genInvoiceNoDataCode(BizCodeUtil.INVOICE_NO))
                .invoiceNumber(BizCodeNewUtil.genInvoiceNoDataCode())
                .invoiceType(invoiceAddDTO.getInvoiceType())
                .buyerName(invoiceAddDTO.getBuyerName())
                .buyerTinNo(invoiceAddDTO.getBuyerTinNo())
                .buyerAddress(invoiceAddDTO.getBuyerAddress())
                .buyerPhone(invoiceAddDTO.getBuyerPhone())
                .buyerBankName(invoiceAddDTO.getBuyerBankName())
                .buyerBankAccount(invoiceAddDTO.getBuyerBankAccount())

                .payNo(chargeRecord.getChargeNo())
                .billingDate(LocalDateTime.now())
                .invoiceKind(InvoiceKindEnum.BLUE_INVOICE.getCode())
                .businessHallId(String.valueOf(chargeRecord.getBusinessHallId()))
                .businessHallName(chargeRecord.getBusinessHallName())
                .customerCode(chargeRecord.getCustomerCode())
                .customerName(chargeRecord.getCustomerName())
                .gasmeterCode(chargeRecord.getGasMeterCode())

                .sellerName(invoiceParam.getName())
                .sellerTinNo(invoiceParam.getTaxpayerNo())
                .sellerAddress(invoiceParam.getAddress())
                .sellerPhone(invoiceParam.getTelephone())
                .sellerBankName(invoiceParam.getBankName())
                .sellerBankAccount(invoiceParam.getBankAccount())

                .drawerId(BaseContextHandler.getUserId())
                .drawerName(BaseContextHandler.getName())
                .payeeId(chargeRecord.getCreateUserId())
                .payeeName(chargeRecord.getCreateUserName())
                //税额
                .totalTax(totalTax.setScale(2, RoundingMode.HALF_UP))
                //不含税金额
                .totalAmount(totalAmount.subtract(totalTax).setScale(2, RoundingMode.HALF_UP))
                //含税金额
                .priceTaxTotalLower(totalAmount.setScale(2, RoundingMode.HALF_UP))
                .priceTaxTotalUpper(MoneyUtil.amountToChinese(totalAmount.doubleValue()))

                .orgId(chargeRecord.getOrgId())
                .orgName(chargeRecord.getOrgName())
                .dataStatus(DataStatusEnum.NORMAL.getValue())
                .invoiceStatus(InvoiceStatusEnum.NOT_OPEN.getCode())
                .createTime(LocalDateTime.now())
                .build();
        save(entity);
        return entity;
    }

    /**
     * 发票明细数据转换
     *
     * @param chargeItemRecords 收费项明细列表
     */
    private List<InvoiceDetails> convertInvoiceDetails(List<ChargeItemRecord> chargeItemRecords) {
        //减免优惠项
        List<ChargeItemRecord> normalList = new ArrayList<>();
        Map<String, ChargeItemRecord> reduceMap = new HashMap<>();
        BigDecimal deductionAmount = BigDecimal.ZERO;
        for (ChargeItemRecord item : chargeItemRecords) {
            if (ChargeItemEnum.ACCOUNT_DEC.eq(item.getChargeItemSourceCode())) {
                deductionAmount = deductionAmount.add(item.getChargeItemMoney());
            } else if (ChargeItemEnum.REDUCTION.eq(item.getChargeItemSourceCode())) {
                reduceMap.put(item.getTollItemScene(), item);
            } else {
                normalList.add(item);
            }
        }
        //计算减免项 和抵扣项
        for (ChargeItemRecord temp : normalList) {
            ChargeItemRecord reduce = reduceMap.get(temp.getTollItemScene());
            //该收费项有减免项 并且不是燃气费（燃气费不能减免，只能减免对应的滞纳金）
            if (Objects.nonNull(reduce) && reduce.getChargeItemMoney().compareTo(BigDecimal.ZERO) > 0
                    && !ChargeItemEnum.GASFEE.eq(temp.getChargeItemSourceCode())) {
                BigDecimal count = temp.getChargeItemMoney().subtract(reduce.getChargeItemMoney());
                temp.setChargeItemMoney(count);
                reduce.setChargeItemMoney(count);
                reduceMap.put(temp.getTollItemScene(), reduce);
            }
            if (deductionAmount.compareTo(BigDecimal.ZERO) > 0 && ChargeItemEnum.RECHARGE.eq(temp.getChargeItemSourceCode())) {
                BigDecimal itemMoney = temp.getChargeItemMoney();
                if (itemMoney.compareTo(BigDecimal.ZERO) > 0) {
                    temp.setChargeItemMoney(itemMoney.subtract(deductionAmount));
                }
            }
        }
        return chargeItemRecords.stream().filter(item -> item.getChargeItemMoney().compareTo(BigDecimal.ZERO) > 0)
                .map(record -> {
                    TollItem tollItem = tollItemService.getById(record.getTollItemId());
                    BigDecimal taxRate = tollItem.getVatGeneralRate();
                    if (Objects.isNull(taxRate) || Objects.isNull(tollItem.getTaxCategoryCode())) {
                        throw new BizException("收费项税率或税收分类编码为空，请进行配置。");
                    }
                    return InvoiceDetails.builder()
                            .type("0")
                            .taxRate(tollItem.getVatGeneralRate())
                            .goodsCode(tollItem.getTaxCategoryCode())
                            .goodsName(tollItem.getItemName())
                            .money(record.getChargeItemMoney().setScale(2, RoundingMode.HALF_UP))
                            .taxMoney(taxRate.multiply(record.getChargeItemMoney().divide(BigDecimal.ONE.add(taxRate), 4, RoundingMode.HALF_UP))
                                    .setScale(2, RoundingMode.HALF_UP))
                            .build();
                }).collect(Collectors.toList());
    }

    /**
     * 保存发票明细
     *
     * @param details   发票明细列表
     * @param invoiceId 发票id
     */
    private void saveInvoiceDetails(List<InvoiceDetails> details, String invoiceId) {
        details.forEach(detail -> detail.setInvoiceId(invoiceId));
        invoiceDetailsService.saveBatch(details);
    }

    /**
     * 更新购买方税务信息数据
     *
     * @param invoice 发票对象
     */
    private void mergeBuyerTaxpayerInfo(Invoice invoice) {

        LbqWrapper<BuyerTaxpayerInfo> wrapper = Wraps.lbQ();
        wrapper.eq(Objects.nonNull(invoice.getBuyerTinNo()), BuyerTaxpayerInfo::getBuyerTinNo, invoice.getBuyerTinNo())
                .eq(BuyerTaxpayerInfo::getBuyerName, invoice.getBuyerName())
                .eq(BuyerTaxpayerInfo::getCustomerCode, invoice.getCustomerCode())
                .eq(Objects.nonNull(invoice.getBuyerBankAccount()), BuyerTaxpayerInfo::getBuyerBankAccount, invoice.getBuyerBankAccount());
        BuyerTaxpayerInfo checkResult = buyerTaxpayerInfoService.getOne(wrapper);
        if (Objects.isNull(checkResult)) {
            BuyerTaxpayerInfo buyerTaxpayerInfo = BuyerTaxpayerInfo.builder()
                    .buyerTinNo(invoice.getBuyerTinNo())
                    .buyerName(invoice.getBuyerName())
                    .buyerBankAccount(invoice.getBuyerBankAccount())
                    .buyerBankName(invoice.getBuyerBankName())
                    .customerCode(invoice.getCustomerCode())
                    .customerName(invoice.getCustomerName())
                    .buyerAddress(invoice.getBuyerAddress())
                    .buyerPhone(invoice.getBuyerPhone())
                    .build();
            buyerTaxpayerInfoService.save(buyerTaxpayerInfo);
        }
    }

    /**
     * 获取发票平台配置参数
     *
     * @return
     */
    private InvoicePlatConfig queryInvoicePlatConfig() {

        LbqWrapper<InvoicePlatConfig> wrapper = Wraps.lbQ();
        wrapper.eq(InvoicePlatConfig::getDataStatus, DataStatusEnum.NORMAL.getValue())
                .eq(InvoicePlatConfig::getDeleteStatus, DeleteStatusEnum.NORMAL.getValue());
        return invoicePlatConfigService.getOne(wrapper);
    }

    /**
     * 推送数据到发票平台
     */
    private ResponseData pushInvoicePlatKp(Invoice invoice, List<InvoiceDetails> invoiceDetails) {
        InvoicePushRecord record = InvoicePushRecord.builder()
                .invoiceId(invoice.getId())
                .invoiceNumber(invoice.getInvoiceNumber())
                .pushDate(LocalDate.now())
                .build();
        ResponseData responseData = null;
        InvoicePlatConfig invoicePlatConfig = queryInvoicePlatConfig();
        if (InvoicePlatEnum.RUI_HONG.getDesc().equals(invoicePlatConfig.getPlatName())) {
            KeyStoreInfo keyStoreInfo = JSONUtil.toBean(invoicePlatConfig.getOtherParam(), KeyStoreInfo.class);
            keyStoreInfo.setAppCode(invoicePlatConfig.getOpenId());
            //瑞宏网平台推送
            KpParam kpParam = convertElectronicInvoice(invoice, invoiceDetails);
            Invoice update = Invoice.builder().id(invoice.getId()).build();
            try {
                responseData = rhInvoiceApiService.pushInvoiceKp(keyStoreInfo, kpParam);
                if (StrPool.ZERO.equals(responseData.getCode())) {
                    log.info("推送成功");
                    update.setInvoiceStatus(InvoiceStatusEnum.OPENING.getCode());
                    update.setInvoiceSerialNumber(responseData.getSerialNo());
                } else {
                    log.info("推送失败" + responseData.getMessage());
                    update.setInvoiceStatus(InvoiceStatusEnum.OPEN_FAIL.getCode());
                    update.setInvoiceSerialNumber(responseData.getSerialNo());
                    update.setInvoiceResult(responseData.getMessage());
                }
                record.setPushUrl(responseData.getApiUrl());
                record.setPushResult(responseData.toString());
                record.setPushResultCode(responseData.getCode());
                record.setSerialNo(responseData.getSerialNo());
            } catch (Exception e) {
                log.error("推送失败：" + e.getMessage());
                throw new BizException(e.getMessage());
            }
            //更新发票状态
            updateById(update);
            //记录日志
            record.setPushCmdName(ApiCmdNameEnum.INVOICE_KP.getCode());
            record.setPushData(JSONUtil.toJsonStr(kpParam));
            record.setPushPlat(InvoicePlatEnum.RUI_HONG.getDesc());

            invoicePushRecordService.save(record);
        } else {
            responseData = ResponseData.builder().code("0").build();
        }

        return responseData;
    }

    /**
     * 将发票数据转换为电子发票开票参数结构
     *
     * @param invoice        发票信息
     * @param invoiceDetails 发票明细列表
     */
    private KpParam convertElectronicInvoice(Invoice invoice, List<InvoiceDetails> invoiceDetails) {
        Order order = Order.builder()
                .orderNo(invoice.getPayNo())
                .account(invoice.getCustomerName())
                .build();
        List<KpInvoiceItem> itemList = invoiceDetails.stream().map(detail -> KpInvoiceItem.builder()
                //发票行性质 0 正常行、1 折扣行、2 被 折扣行。
                .type(detail.getType())
                //商品名称。可在每一行商品下加入折扣行,折扣行商品名称与被折扣商品名称一致,金额和税额栏以负数填写,税率与被折扣行商品税率相同,其它栏不填写。
                .name(detail.getGoodsName())
                //税率。只能为0、 0.03、0.04、0.06、0.10、0.11、0.16、0.17。
                .taxRate(detail.getTaxRate())
                //税价合计金额。
                .amount(detail.getMoney())
                //商品分类编码。目前的分类编码为19位，不足19位的在后面补0。
                .catalogCode(detail.getGoodsCode())
                .build()).collect(Collectors.toList());

        log.info(JSONUtil.toJsonStr(invoice));
        KpInvoice kpInvoice = KpInvoice.builder()
                .taxpayerCode(invoice.getSellerTinNo())
                .taxpayerName(invoice.getSellerName())
                .taxpayerAddress(invoice.getSellerAddress())
                .taxpayerTel(invoice.getSellerPhone())
                .taxpayerBankName(invoice.getSellerBankName())
                .taxpayerBankAccount(invoice.getSellerBankAccount())
                .customerName(invoice.getBuyerName())
                .customerCode(invoice.getBuyerTinNo())
                .customerAddress(invoice.getBuyerAddress())
                .customerTel(invoice.getBuyerPhone())
                .customerBankName(invoice.getBuyerBankName())
                .customerBankAccount(invoice.getBuyerBankAccount())
//                .invoiceType("开票类型。p:电子增值税普通发票（默认） ps:电子收购发票 py：成品油")
//                .shopCode(invoice.getBusinessHallId())
//                .shopName(invoice.getBusinessHallName())
//                .payType("支付方式")
//                .payBillNo(invoice.getPayNo())
                .drawer(invoice.getDrawerName())
                .payee(invoice.getPayeeName())
                .reviewer(invoice.getReviewerName())
                .totalAmount(invoice.getPriceTaxTotalLower())
                .remark(invoice.getRemark())
                .items(itemList)
                .build();
        Map<String, String> extendedParams = new HashMap<>();
        extendedParams.put("invoiceId", invoice.getId().toString());

        String tenant = BaseContextHandler.getTenant();
        Map<String, String> dynamicParams = new HashMap<>();
        String backUrl = callbackUrl + "?tenantCode=" + tenant;
        dynamicParams.put("callbackUrl", backUrl);
        log.info("callbackUrl: " + backUrl);

        String serialNo = UUID.randomUUID().toString().replace("-", "");
        return KpParam.builder()
                .serialNo(serialNo)
                .postTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .invoice(kpInvoice)
                .order(order)
                .extendedParams(extendedParams)
                .dynamicParams(dynamicParams)
                .build();
    }

    /**
     * 新增红票信息
     *
     * @param invoiceChDTO 冲红参数
     * @param invoice      冲红的蓝票
     * @return 红票信息
     */
    private Invoice convertToRedInvoice(InvoiceChDTO invoiceChDTO, Invoice invoice) {
        Invoice redInvoice = Invoice.builder().build();
        BeanUtil.copyProperties(invoice, redInvoice);
        redInvoice.setId(null);
        redInvoice.setInvoiceKind(InvoiceKindEnum.RED_INVOICE.getCode());
        redInvoice.setInvoiceNumber(BizCodeNewUtil.genInvoiceNoDataCode());
        redInvoice.setBillingDate(LocalDateTime.now());
        redInvoice.setInvoiceCode(null);
        redInvoice.setInvoiceNo(null);
        redInvoice.setInvoiceCodeNo(null);
        redInvoice.setRedUserId(String.valueOf(BaseContextHandler.getUserId()));
        redInvoice.setRedUserName(BaseContextHandler.getName());
        redInvoice.setInvoiceFileUrl(null);
        redInvoice.setPdfDownloadUrl(null);
        redInvoice.setInvoiceSerialNumber(null);
        redInvoice.setRedRequestNumber(null);
        redInvoice.setBlueSerialNumber(invoice.getInvoiceSerialNumber());
        redInvoice.setBlueInvoiceNumber(invoice.getInvoiceNumber());
        redInvoice.setInvoiceStatus(InvoiceStatusEnum.NOT_OPEN.getCode());
        redInvoice.setInvoiceResult(null);
        redInvoice.setInvalidStatus(0);
        redInvoice.setPrintTimes(null);
        redInvoice.setDataStatus(DataStatusEnum.NORMAL.getValue());
        redInvoice.setRemark(invoiceChDTO.getReason());
        redInvoice.setCreateTime(LocalDateTime.now());
        save(redInvoice);

        //保存明细
        List<InvoiceDetails> details = invoiceDetailsService.getByInvoiceId(invoice.getId());
        details.forEach(detail -> {
            detail.setInvoiceId(redInvoice.getId().toString());
            detail.setId(null);
        });
        invoiceDetailsService.saveBatch(details, details.size());

        return redInvoice;
    }

    @Override
    public List<InvoiceDayStsVo> invoiceStsByType(StsSearchParam stsSearchParam) {

        String type = stsSearchParam.getSearchKeyValue("type");
        String dataScope = UserOrgIdUtil.getUserDataScopeStr();
        return this.baseMapper.invoiceStsByType(stsSearchParam, type, null, dataScope);
    }
}
