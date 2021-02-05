package com.cdqckj.gmis.bizcenter.invoice.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.GeneralConstants;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.base.service.SuperCenterServiceImpl;
import com.cdqckj.gmis.bizcenter.invoice.dto.ReceiptDTO;
import com.cdqckj.gmis.bizcenter.invoice.service.BillService;
import com.cdqckj.gmis.bizcenter.invoice.util.MoneyUtil;
import com.cdqckj.gmis.common.utils.BizCodeNewUtil;
import com.cdqckj.gmis.common.utils.BizCodeUtil;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.invoice.*;
import com.cdqckj.gmis.invoice.dto.*;
import com.cdqckj.gmis.invoice.entity.*;
import com.cdqckj.gmis.invoice.enumeration.InvoiceKindEnum;
import com.cdqckj.gmis.invoice.enumeration.ReceiptStateEnum;
import com.cdqckj.gmis.invoice.vo.InvoiceResponseData;
import com.cdqckj.gmis.operateparam.FunctionSwitchBizApi;
import com.cdqckj.gmis.systemparam.entity.FunctionSwitch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BillServiceImpl extends SuperCenterServiceImpl implements BillService {

    @Autowired
    private InvoiceBizApi invoiceBizApi;
    @Autowired
    private InvoiceDetailsBizApi invoiceDetailsBizApi;
    @Autowired
    private ReceiptBizApi receiptBizApi;
    @Autowired
    private ReceiptDetailBizApi receiptDetailBizApi;
    @Autowired
    private FunctionSwitchBizApi functionSwitchBizApi;
    @Autowired
    private ReceiptInvoiceAssociationBizApi receiptInvoiceAssociationBizApi;
    @Autowired
    private SellerTaxpayerInfoBizApi sellerTaxpayerInfoBizApi;
    @Autowired
    private BuyerTaxpayerInfoBizApi buyerTaxpayerInfoBizApi;

    /**
     * 基于票据打印发票
     *
     * @param receiptDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public R<Boolean> printInvoiceBasedOnReceipt(ReceiptDTO receiptDTO) {
        //1、查询票据记录
        R<Receipt> receiptR = receiptBizApi.get(receiptDTO.getId());

        if (receiptR.getIsError()) {
            log.error("票据查询接口调用失败");
            throw BizException.wrap("票据查询接口调用失败");
        }
        Receipt receiptGet = receiptR.getData();

        //2、票据明细
        List<ReceiptDetail> receiptDetailList = getReceiptDetails(receiptGet);
        List<BigDecimal> moneyList = receiptDetailList.stream().map(receiptDetail -> receiptDetail.getMoney()).collect(Collectors.toList());

        //3、增值税计算
        Map<String, Object> map = calculateVAT(moneyList);

        //查询销售方税务信息
        SellerTaxpayerInfo sellerTaxpayerInfo = new SellerTaxpayerInfo();
        sellerTaxpayerInfo.setCompanyCode(BaseContextHandler.getTenantId());
        R<List<SellerTaxpayerInfo>> sellerTaxpayerInfoListR = sellerTaxpayerInfoBizApi.query(sellerTaxpayerInfo);
        if (sellerTaxpayerInfoListR.getIsError()) {
            log.error("销售方税务信息查询接口调用失败");
            throw BizException.wrap("销售方税务信息查询接口调用失败");
        }
        List<SellerTaxpayerInfo> sellerTaxpayerInfoList = sellerTaxpayerInfoListR.getData();
        SellerTaxpayerInfo sellerTaxpayerInfoGet = sellerTaxpayerInfoList.get(0);
        //4、保存发票记录
        R<Invoice> invoiceR = saveInvoice(receiptDTO, receiptGet, sellerTaxpayerInfoGet, map);
        //5、关联发票票据
        relateReceiptInvoice(receiptGet, invoiceR);

        String invoiceId = String.valueOf(invoiceR.getData().getId());
        receiptDetailList.stream().forEach(receiptDetail -> {
            //6、保存发票明细
            saveInvoiceDetail(receiptDetail, invoiceId);
        });

        //更新购买方税务信息表数据
        mergeBuyerTaxpayerInfo(invoiceR.getData());

        //更新票据表数据为已开票
        updateReceiptState(receiptDTO);

        return R.success(true);
    }

    /**
     * 增值税计算
     *
     * @param moneyList
     */
    private Map<String, Object> calculateVAT(List<BigDecimal> moneyList) {
        //税率
        FunctionSwitch functionSwitch = new FunctionSwitch();
        R<List<FunctionSwitch>> functionSwitchListR = null;
        if (functionSwitchListR.getIsError()) {
            log.error("税率查询接口调用失败!");
            throw BizException.wrap("税率查询接口调用失败!");
        }
        List<FunctionSwitch> functionSwitchList = functionSwitchListR.getData();
        FunctionSwitch functionSwitchGet = functionSwitchList.get(0);
        BigDecimal taxRate = functionSwitchGet.getTaxRate();
//        Integer rounding = functionSwitchGet.getRounding();
        //营业额
        BigDecimal turnover = new BigDecimal("0.00");
        //销售额
        BigDecimal salesValue = new BigDecimal("0.00");
        //销项税额
        BigDecimal outputTax = new BigDecimal("0.00");
        //合计税额
        BigDecimal totalTax = new BigDecimal("0.00");
        //合计金额
        BigDecimal totalAmount = new BigDecimal("0.00");
        //价税合计（小写）
        BigDecimal priceTaxTotalLower = new BigDecimal("0.00");
        for (BigDecimal money : moneyList) {
            turnover = turnover.add(money);
        }
        salesValue = turnover.divide(new BigDecimal("1").add(taxRate), 4, BigDecimal.ROUND_HALF_UP);
        outputTax = salesValue.multiply(taxRate);
        totalTax = outputTax;
        totalAmount = outputTax.multiply(new BigDecimal("1").subtract(taxRate));
        priceTaxTotalLower = totalTax.add(totalAmount);
        //价税合计（大写）
        String priceTaxTotalUpper = MoneyUtil.amountToChinese(priceTaxTotalLower.doubleValue());
        Map<String, Object> map = new HashMap<>();
        map.put(GeneralConstants.TOTAL_TAX, totalTax);
        map.put(GeneralConstants.TOTAL_AMOUNT, totalAmount);
        map.put(GeneralConstants.PRICE_TAX_TOTAL_LOWER, priceTaxTotalLower);
        map.put(GeneralConstants.PRICE_TAX_TOTAL_UPPER, priceTaxTotalUpper);
        return map;
    }

    /**
     * 查询票据明细
     *
     * @param receiptGet
     * @return
     */
    private List<ReceiptDetail> getReceiptDetails(Receipt receiptGet) {
        ReceiptDetail receiptDetail = ReceiptDetail
                .builder()
                .receiptId(String.valueOf(receiptGet.getId()))
                .build();
        R<List<ReceiptDetail>> receiptDetailListR = receiptDetailBizApi.query(receiptDetail);
        if (receiptDetailListR.getIsError()) {
            log.error("票据明细查询接口调用失败！");
            throw BizException.wrap("票据明细查询接口调用失败！");
        }
        return receiptDetailListR.getData();
    }

    /**
     * 保存发票明细
     *
     * @param receiptDetail
     * @param invoiceId
     */
    private void saveInvoiceDetail(ReceiptDetail receiptDetail, String invoiceId) {
        InvoiceDetailsSaveDTO invoiceDetailsSaveDTO = InvoiceDetailsSaveDTO
                .builder()
                .invoiceId(invoiceId)
                .goodsCode(receiptDetail.getGoodsId())
                .goodsName(receiptDetail.getGoodsName())
                .specificationModel(receiptDetail.getSpecificationModel())
                .unit(receiptDetail.getUnit())
                .number(receiptDetail.getNumber())
                .price(receiptDetail.getPrice())
                .money(receiptDetail.getMoney())
                .build();
        R<InvoiceDetails> result = invoiceDetailsBizApi.save(invoiceDetailsSaveDTO);
        if (result.getIsError()) {
            throw BizException.wrap("发票明细保存失败！");
        }
    }

    /**
     * 保存发票记录
     *
     * @param receiptDTO            前端上传的购买方纳税信息等数据
     * @param receiptGet            后台查询的收费编号等数据
     * @param sellerTaxpayerInfoGet 后台查询的销售方纳税信息等数据
     * @param map                   计算出的税额等信息
     * @return
     */
    private R<Invoice> saveInvoice(ReceiptDTO receiptDTO, Receipt receiptGet, SellerTaxpayerInfo sellerTaxpayerInfoGet, Map<String, Object> map) {
        InvoiceSaveDTO invoiceSaveDTO = InvoiceSaveDTO
                .builder()

                .invoiceNumber(receiptDTO.getInvoiceNumber())
                .invoiceType(receiptDTO.getInvoiceType())
                .buyerName(receiptDTO.getBuyerName())
                .buyerTinNo(receiptDTO.getBuyerTinNo())
                .buyerAddress(receiptDTO.getBuyerAddress())
                .buyerPhone(receiptDTO.getBuyerPhone())
                .buyerBankName(receiptDTO.getBuyerBankName())
                .buyerBankAccount(receiptDTO.getBuyerBankAccount())

                .totalAmount(receiptGet.getChargeAmountTotalLowercase())
                .payNo(receiptGet.getPayNo())
                .billingDate(LocalDateTime.now())
                .invoiceKind(InvoiceKindEnum.BLUE_INVOICE.getCode())
                .businessHallId(receiptGet.getBusinessHallId())
                .businessHallName(receiptGet.getBusinessHallName())

                .sellerName(sellerTaxpayerInfoGet.getSellerName())
                .sellerTinNo(sellerTaxpayerInfoGet.getSellerTinNo())
                .sellerAddress(sellerTaxpayerInfoGet.getSellerAddress())
                .sellerPhone(sellerTaxpayerInfoGet.getSellerPhone())
                .sellerBankName(sellerTaxpayerInfoGet.getSellerBankName())
                .sellerBankAccount(sellerTaxpayerInfoGet.getSellerBankAccount())

                .drawerId(BaseContextHandler.getUserId())
                .drawerName(BaseContextHandler.getName())

                .totalTax(new BigDecimal(String.valueOf(map.get(GeneralConstants.TOTAL_TAX))))
                .totalAmount(new BigDecimal(String.valueOf(map.get(GeneralConstants.TOTAL_AMOUNT))))
                .priceTaxTotalLower(new BigDecimal(String.valueOf(map.get(GeneralConstants.PRICE_TAX_TOTAL_LOWER))))
                .priceTaxTotalUpper(String.valueOf(map.get(GeneralConstants.PRICE_TAX_TOTAL_UPPER)))
                .build();
        R<Invoice> result = invoiceBizApi.save(invoiceSaveDTO);
        if (result.getIsError()) {
            log.error("发票保存接口调用失败!");
            throw BizException.wrap("发票保存接口调用失败!");
        }
        return result;
    }

    /**
     * 关联发票票据
     *
     * @param receiptGet
     * @param invoiceR
     */
    private void relateReceiptInvoice(Receipt receiptGet, R<Invoice> invoiceR) {
        ReceiptInvoiceAssociationSaveDTO receiptInvoiceAssociationSaveDTO = ReceiptInvoiceAssociationSaveDTO
                .builder()
                .receiptId(receiptGet.getId())
                .invoiceId(invoiceR.getData().getId())
                .build();
        R<ReceiptInvoiceAssociation> result = receiptInvoiceAssociationBizApi.save(receiptInvoiceAssociationSaveDTO);
        if (result.getIsError()) {
            log.error("发票保存接口调用失败!");
            throw BizException.wrap("发票保存接口调用失败!");
        }
    }

    /**
     * 更新票据的开票状态
     *
     * @param receiptDTO
     */
    private void updateReceiptState(ReceiptDTO receiptDTO) {
        ReceiptUpdateDTO updateDTO = ReceiptUpdateDTO.builder()
                .id(receiptDTO.getId())
                .buyerName(receiptDTO.getBuyerName())
                .buyerTinNo(receiptDTO.getBuyerTinNo())
                .buyerBankName(receiptDTO.getBuyerBankAccount())
                .buyerBankAccount(receiptDTO.getBuyerBankAccount())
                .buyerAddress(receiptDTO.getBuyerAddress())
                .buyerPhone(receiptDTO.getBuyerPhone())
                .buyerTinNo(receiptDTO.getBuyerTinNo())
                .receiptState(Integer.parseInt(ReceiptStateEnum.GIVE_RECEIPT_GIVE_INVOICE.getCode()))
                .billingDate(LocalDateTime.now())
                .build();
        R<Receipt> result = receiptBizApi.update(updateDTO);
        if (result.getIsError()) {
            log.error("票据状态更新接口调用失败!");
            throw BizException.wrap("票据状态更新接口调用失败!");
        }
    }

    @Override
    public R<List<Receipt>> query(Receipt data) {
        return receiptBizApi.query(data);
    }

    @Override
    public R<Receipt> save(ReceiptSaveDTO saveDTO) {
        return receiptBizApi.save(saveDTO);
    }

    @Override
    public R<Receipt> update(ReceiptUpdateDTO updateDTO) {
        return receiptBizApi.update(updateDTO);
    }

    @Override
    public R<List<ReceiptDetail>> query(ReceiptDetail data) {
        return receiptDetailBizApi.query(data);
    }

    @Override
    public R<ReceiptDetail> save(ReceiptDetailSaveDTO saveDTO) {
        return receiptDetailBizApi.save(saveDTO);
    }

    @Override
    public R<ReceiptDetail> update(ReceiptDetailUpdateDTO updateDTO) {
        return receiptDetailBizApi.update(updateDTO);
    }

    @Override
    public R<List<Invoice>> query(Invoice data) {
        return invoiceBizApi.query(data);
    }

    @Override
    public R<Invoice> update(InvoiceUpdateDTO updateDTO) {
        return invoiceBizApi.update(updateDTO);
    }

    @Override
    public R<Invoice> save(InvoiceSaveDTO saveDTO) {
        return invoiceBizApi.save(saveDTO);
    }

    @Override
    public R<List<InvoiceDetails>> query(InvoiceDetails data) {
        return invoiceDetailsBizApi.query(data);
    }

    @Override
    public R<InvoiceDetails> update(InvoiceDetailsUpdateDTO updateDTO) {
        return invoiceDetailsBizApi.update(updateDTO);
    }

    @Override
    public R<InvoiceDetails> save(InvoiceDetailsSaveDTO saveDTO) {
        return invoiceDetailsBizApi.save(saveDTO);
    }

    /**
     * 开票据
     *
     * @param bill
     * @return
     */
    @Transactional
    @Override
    public R<Receipt> makeOutReceipt(Bill bill) {

        //保存票据信息
        ReceiptSaveDTO receiptSaveDTO = ReceiptSaveDTO
                .builder()
                .receiptNo(bill.getBillNo())
                .payNo(bill.getPayNo())
                .payType(bill.getPayType())
                .payTime(LocalDateTime.now())
                .discountAmount(bill.getDiscountAmount())
                .rechargeGiveGas(bill.getRechargeGiveGas())
                .shouldPay(bill.getShouldPay())
                .actualCollection(bill.getActualCollection())
                .giveChange(bill.getGiveChange())
                .preDeposit(bill.getPreDeposit())
                .deductAmount(bill.getDeductAmount())
                .rechargeAmount(bill.getRechargeAmount())
                .rechargeGasVolume(bill.getRechargeGasVolume())
                .predepositAmount(bill.getPredepositAmount())
                .premium(bill.getPremium())
                .chargeAmountTotalLowercase(bill.getChargeAmountTotal())
                .chargeAmountTotalUppercase(MoneyUtil.amountToChinese(bill.getChargeAmountTotal().doubleValue()))
                .build();
        R<Receipt> receiptR = receiptBizApi.save(receiptSaveDTO);
        Receipt receiptGet = receiptR.getData();
        List<BillDetail> billDetailList = bill.getBillDetail();
        billDetailList.stream().forEach(billDetail -> {

            //保存票据明细
            ReceiptDetailSaveDTO receiptDetailSaveDTO = ReceiptDetailSaveDTO
                    .builder()
                    .receiptId(String.valueOf(receiptGet.getId()))
                    .goodsId(String.valueOf(billDetail.getTollItemId()))
                    .goodsName(billDetail.getTollItemName())
                    .number(billDetail.getGasVolume())
                    .money(billDetail.getMoney())
                    .build();
            receiptDetailBizApi.save(receiptDetailSaveDTO);
        });
        return R.success(receiptGet);
    }

    /**
     * 开发票
     *
     * @param bill
     * @return
     */
    @Transactional
    @Override
    public R<Invoice> makeOutInvoice(Bill bill) {
        //查询销售方税务信息
        SellerTaxpayerInfo sellerTaxpayerInfo = new SellerTaxpayerInfo();
        sellerTaxpayerInfo.setCompanyCode(BaseContextHandler.getTenantId());
        R<List<SellerTaxpayerInfo>> sellerTaxpayerInfoListR = sellerTaxpayerInfoBizApi.query(sellerTaxpayerInfo);
        List<SellerTaxpayerInfo> sellerTaxpayerInfoList = sellerTaxpayerInfoListR.getData();
        SellerTaxpayerInfo sellerTaxpayerInfoGet = sellerTaxpayerInfoList.get(0);
        //增值税计算
        List<BigDecimal> moneyList = bill.getBillDetail().stream().map(BillDetail::getMoney).collect(Collectors.toList());
        Map<String, Object> map = calculateVAT(moneyList);
        //保存发票信息
        InvoiceSaveDTO invoiceSaveDTO = InvoiceSaveDTO
                .builder()
                .invoiceNumber(bill.getBillNo())
                .payNo(bill.getPayNo())
                .invoiceType(bill.getInvoiceType())
                .invoiceKind(InvoiceKindEnum.BLUE_INVOICE.getCode())
                .billingDate(LocalDateTime.now())
                .buyerName(bill.getBuyerName())
                .buyerTinNo(bill.getBuyerTinNo())
                .buyerAddress(bill.getBuyerAddress())
                .buyerPhone(bill.getBuyerPhone())
                .buyerBankName(bill.getBuyerBankName())
                .buyerBankAccount(bill.getBuyerBankAccount())
                .sellerName(sellerTaxpayerInfoGet.getSellerName())
                .sellerTinNo(sellerTaxpayerInfoGet.getSellerTinNo())
                .sellerAddress(sellerTaxpayerInfoGet.getSellerAddress())
                .sellerPhone(sellerTaxpayerInfoGet.getSellerPhone())
                .sellerBankName(sellerTaxpayerInfoGet.getSellerBankName())
                .sellerBankAccount(sellerTaxpayerInfoGet.getSellerBankAccount())
                .totalTax(new BigDecimal(String.valueOf(map.get(GeneralConstants.TOTAL_TAX))))
                .totalAmount(new BigDecimal(String.valueOf(map.get(GeneralConstants.TOTAL_AMOUNT))))
                .priceTaxTotalLower(new BigDecimal(String.valueOf(map.get(GeneralConstants.PRICE_TAX_TOTAL_LOWER))))
                .priceTaxTotalUpper(String.valueOf(map.get(GeneralConstants.PRICE_TAX_TOTAL_UPPER)))
                .build();
        R<Invoice> invoiceR = invoiceBizApi.save(invoiceSaveDTO);
        Invoice invoiceGet = invoiceR.getData();
        List<BillDetail> billDetailList = bill.getBillDetail();

        billDetailList.stream().forEach(billDetail -> {
            //保存发票明细
            InvoiceDetailsSaveDTO invoiceDetailsSaveDTO = InvoiceDetailsSaveDTO
                    .builder()
                    .invoiceId(String.valueOf(invoiceGet.getId()))
                    .goodsCode(String.valueOf(billDetail.getTollItemId()))
                    .goodsName(billDetail.getTollItemName())
                    .number(billDetail.getGasVolume())
                    .money(billDetail.getMoney())
                    .build();
            invoiceDetailsBizApi.save(invoiceDetailsSaveDTO);
        });
        //更新购买方税务信息表数据
        mergeBuyerTaxpayerInfo(invoiceR.getData());

        return R.success(invoiceGet);
    }

    /**
     * 生成发票编号
     */
    @Override
    public String generateInvoiceNo() {

//        return BizCodeUtil.genInvoiceNoDataCode(BizCodeUtil.INVOICE_NO);
        return BizCodeNewUtil.genInvoiceNoDataCode();
    }

    /**
     * 生成票据编号
     */
    @Override
    public String generateReceiptNo() {

//        return BizCodeUtil.genReceiptNoDataCode(BizCodeUtil.BILL_NO);
        return BizCodeNewUtil.genReceiptNoDataCode();
    }

    @Override
    public R<Page<Invoice>> pageInvoice(PageParams<InvoicePageDTO> pageParams) {
        return invoiceBizApi.page(pageParams);
    }

    @Override
    public R<Page<Receipt>> pageReceipt(PageParams<ReceiptPageDTO> pageParams) {
        return receiptBizApi.page(pageParams);
    }

    @Override
    public R<List<ReceiptDetail>> queryReceiptDetail(String receiptId) {
        ReceiptDetail query = ReceiptDetail.builder().receiptId(receiptId).build();
        return receiptDetailBizApi.query(query);
    }

    @Override
    public R<List<InvoiceDetails>> queryInvoiceDetails(String invoiceId) {
        InvoiceDetails query = InvoiceDetails.builder().invoiceId(invoiceId).build();
        return invoiceDetailsBizApi.query(query);
    }

    @Override
    public R<Boolean> checkInvoiceNumber(String invoiceNumber) {
        R<Integer> result = invoiceBizApi.checkInvoiceNumber(invoiceNumber);
        if (result.getData() > 0) {
            return R.success(false);
        } else {
            return R.success(true);

        }
    }

    /**
     * 通过缴费订单打印发票
     *
     * @param invoiceAddDTO
     * @return
     */
    @Override
    public R<InvoiceResponseData> printInvoiceByCharge(InvoiceAddDTO invoiceAddDTO) {
        return invoiceBizApi.createInvoiceByChargeNo(invoiceAddDTO);
    }

    @Override
    public R<Boolean> rhKpCallback(String result, String tenant) {
        if (StringUtils.isEmpty(result) || StringUtils.isEmpty(tenant)) {
            log.error("回调参数不能为空");
            throw new BizException("参数不能为空");
        }
        InvoiceCallbackDTO callback = InvoiceCallbackDTO.builder()
                .result(result)
                .tenant(tenant)
                .build();
        return invoiceBizApi.rhKpCallback(callback);
    }


    /**
     * 更新购买方税务信息数据
     *
     * @param invoice
     */
    private void mergeBuyerTaxpayerInfo(Invoice invoice) {

        BuyerTaxpayerInfo buyerTaxpayerInfo = BuyerTaxpayerInfo.builder()
                .buyerTinNo(invoice.getBuyerTinNo())
                .buyerName(invoice.getBuyerName())
                .buyerBankAccount(invoice.getBuyerBankAccount())
                .build();
        R<BuyerTaxpayerInfo> checkResult = buyerTaxpayerInfoBizApi.queryBuyerTaxpayerInfo(buyerTaxpayerInfo);
        if (checkResult.getIsError()) {
            log.error("购买方税务信息查询接口调用失败!");
            throw BizException.wrap("购买方税务信息查询接口调用失败!");
        }
        if (Objects.isNull(checkResult.getData())) {
            BuyerTaxpayerInfoSaveDTO saveDTO = BeanUtil.toBean(buyerTaxpayerInfo, BuyerTaxpayerInfoSaveDTO.class);
            R<BuyerTaxpayerInfo> saveResult = buyerTaxpayerInfoBizApi.save(saveDTO);
            if (saveResult.getIsError()) {
                log.error("购买方税务信息新增接口调用失败!");
                throw BizException.wrap("购买方税务信息新增接口调用失败!");
            }
        }
    }

}
