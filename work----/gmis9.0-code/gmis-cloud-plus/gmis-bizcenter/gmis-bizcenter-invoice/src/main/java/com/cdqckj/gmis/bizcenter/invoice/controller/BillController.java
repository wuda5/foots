package com.cdqckj.gmis.bizcenter.invoice.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.bizcenter.invoice.dto.ReceiptDTO;
import com.cdqckj.gmis.bizcenter.invoice.service.BillService;
import com.cdqckj.gmis.invoice.InvoiceBizApi;
import com.cdqckj.gmis.invoice.dto.*;
import com.cdqckj.gmis.invoice.dto.rhapi.ResponseData;
import com.cdqckj.gmis.invoice.entity.*;
import com.cdqckj.gmis.invoice.enumeration.InvoiceStatusEnum;
import com.cdqckj.gmis.invoice.vo.InvoiceResponseData;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@Slf4j
@Validated
@RestController
@RequestMapping("/bill")
@Api(value = "bill", tags = "票据发票")
public class BillController {
    @Autowired
    private BillService billService;
    @Autowired
    private InvoiceBizApi invoiceBizApi;


    /**
     * 基于票据打印发票
     *
     * @return
     */
    @ApiOperation(value = "基于票据打印发票")
    @PostMapping(value = "/printInvoiceBasedOnReceipt")
    @GlobalTransactional
    public R<Boolean> printInvoiceBasedOnReceipt(@RequestBody @Valid ReceiptDTO receiptDTO) {
        return billService.printInvoiceBasedOnReceipt(receiptDTO);
    }

    /**
     * 开票据
     *
     * @return
     */
    @ApiOperation(value = "开票据")
    @PostMapping(value = "/makeOutReceipt")
    public R<Receipt> makeOutReceipt(@RequestBody Bill bill) {
        return billService.makeOutReceipt(bill);
    }

    /**
     * 开发票
     *
     * @return
     */
    @ApiOperation(value = "开发票")
    @PostMapping(value = "/makeOutInvoice")
    public R<Invoice> makeOutInvoice(@RequestBody Bill bill) {
        return billService.makeOutInvoice(bill);
    }

    /**
     * 生成发票编号
     *
     * @return
     */
    @ApiOperation(value = "生成发票编号")
    @PostMapping(value = "/generateInvoiceNo")
    public String generateInvoiceNo() {
        return billService.generateInvoiceNo();
    }

    /**
     * 生成票据编号
     *
     * @return
     */
    @ApiOperation(value = "生成票据编号")
    @PostMapping(value = "/generateReceiptNo")
    public String generateReceiptNo() {
        return billService.generateReceiptNo();
    }

    /**
     * 查询票据
     *
     * @return
     */
    @ApiOperation(value = "查询票据")
    @PostMapping(value = "/queryReceipt")
    public R<List<Receipt>> query(@RequestBody Receipt data) {
        return billService.query(data);
    }

    /**
     * 修改票据
     *
     * @return
     */
    @ApiOperation(value = "修改票据")
    @PostMapping(value = "/updateReceipt")
    public R<Receipt> update(ReceiptUpdateDTO updateDTO) {
        return billService.update(updateDTO);
    }

    /**
     * 保存票据
     *
     * @return
     */
    @ApiOperation(value = "保存票据")
    @PostMapping(value = "/saveReceipt")
    public R<Receipt> save(@RequestBody @Valid ReceiptSaveDTO saveDTO) {
        return billService.save(saveDTO);
    }

    /**
     * 查询票据明细
     *
     * @return
     */
    @ApiOperation(value = "通过票据ID查询票据明细")
    @GetMapping(value = "/queryReceiptDetail")
    public R<List<ReceiptDetail>> queryReceiptDetail(@RequestParam("receiptId") String receiptId) {
        return billService.queryReceiptDetail(receiptId);
    }

    /**
     * 修改票据明细
     *
     * @return
     */
    @ApiOperation(value = "修改票据明细")
    @PostMapping(value = "/updateReceiptDetail")
    public R<ReceiptDetail> update(ReceiptDetailUpdateDTO updateDTO) {
        return billService.update(updateDTO);
    }

    /**
     * 保存票据明细
     *
     * @return
     */
    @ApiOperation(value = "保存票据明细")
    @PostMapping(value = "/saveReceiptDetail")
    public R<ReceiptDetail> save(@RequestBody @Valid ReceiptDetailSaveDTO saveDTO) {
        return billService.save(saveDTO);
    }

    /**
     * 查询发票
     *
     * @return
     */
    @ApiOperation(value = "查询发票")
    @PostMapping(value = "/queryInvoice")
    public R<List<Invoice>> query(@RequestBody Invoice data) {
        return billService.query(data);
    }

    /**
     * 修改发票
     *
     * @return
     */
    @ApiOperation(value = "修改发票")
    @PostMapping(value = "/updateInvoice")
    public R<Invoice> update(InvoiceUpdateDTO updateDTO) {
        return billService.update(updateDTO);
    }

    /**
     * 保存发票
     *
     * @return
     */
    @ApiOperation(value = "保存发票")
    @PostMapping(value = "/saveInvoice")
    public R<Invoice> save(@RequestBody @Valid InvoiceSaveDTO saveDTO) {
        return billService.save(saveDTO);
    }

    /**
     * 查询发票明细
     *
     * @return
     */
    @ApiOperation(value = "通过发票ID查询发票明细")
    @GetMapping(value = "/queryInvoiceDetails")
    public R<List<InvoiceDetails>> queryInvoiceDetails(@RequestParam("invoiceId") String invoiceId) {
        return billService.queryInvoiceDetails(invoiceId);
    }

    /**
     * 修改发票明细
     *
     * @return
     */
    @ApiOperation(value = "修改发票明细")
    @PostMapping(value = "/updateInvoiceDetails")
    public R<InvoiceDetails> update(InvoiceDetailsUpdateDTO updateDTO) {
        return billService.update(updateDTO);
    }

    /**
     * 保存发票明细
     *
     * @return
     */
    @ApiOperation(value = "保存发票明细")
    @PostMapping(value = "/saveInvoiceDetails")
    public R<InvoiceDetails> save(@RequestBody @Valid InvoiceDetailsSaveDTO saveDTO) {
        return billService.save(saveDTO);
    }

    @ApiOperation(value = "根据条件分页模糊查询发票")
    @PostMapping("/fuzzySearch")
    R<Page<Invoice>> fuzzySearch(@RequestBody PageParams<InvoicePageDTO> queryDTO) {
        return billService.pageInvoice(queryDTO);
    }

    @ApiOperation(value = "根据条件分页模糊查询票据")
    @PostMapping("/pageReceipt")
    R<Page<Receipt>> pageReceipt(@RequestBody PageParams<ReceiptPageDTO> queryDTO) {
        return billService.pageReceipt(queryDTO);
    }

    @ApiOperation(value = "校验发票编号是否存在")
    @GetMapping("/checkInvoiceNumber")
    R<Boolean> checkInvoiceNumber(@RequestParam(value = "invoiceNumber") String invoiceNumber) {
        return billService.checkInvoiceNumber(invoiceNumber);
    }

    /**
     * 通过缴费订单生成发票
     *
     * @return
     */
    @ApiOperation(value = "通过缴费订单生成发票")
    @PostMapping(value = "/printInvoiceByCharge")
    @GlobalTransactional
    public R<InvoiceResponseData> printInvoiceByCharge(@RequestBody @Valid InvoiceAddDTO addDTO) {
        return billService.printInvoiceByCharge(addDTO);
    }


    /**
     * 瑞宏开票回调接口
     *
     * @return
     */
    @ApiOperation(value = "瑞宏开票回调接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tenantCode", value = "租户code", dataType = "string", paramType = "query"),
    })
    @PostMapping(value = "/rhKpCallback")
    @GlobalTransactional
    public R<Boolean> rhKpCallback(HttpServletRequest request, @RequestBody String result) {
        log.info("瑞宏回调接口：" + result);
        //参数tenant 改为 tenantCode 防止AccessFilter中做token验证 getHeader
        String tenant = request.getParameter("tenantCode");
        log.info("瑞宏回调租户：" + tenant);
        return billService.rhKpCallback(result, tenant);
    }

    /**
     * 重新推送数据到发票服务平台开票
     *
     * @param addDTO 发票id
     * @return 推送结果
     */
    @ApiOperation(value = "重新推送数据到发票服务平台开票")
    @PostMapping("/retryPrint")
    public R<InvoiceResponseData> retryPrint(@RequestBody InvoiceAddDTO addDTO) {
        return invoiceBizApi.retryPrint(addDTO);
    }

    /**
     * 重新开票
     *
     * @param addParam 重开参数
     * @return 开票结果
     */
    @ApiOperation(value = "重新开票")
    @PostMapping("/retryKp")
    @GlobalTransactional
    public R<InvoiceResponseData> retryKp(@RequestBody InvoiceAddDTO addParam) {
        return invoiceBizApi.retryKp(addParam);
    }

    @ApiOperation(value = "纸质发票打印成功回写状态")
    @PostMapping("/printCallBack")
    @GlobalTransactional
    public R<Invoice> printCallBack(@RequestBody Long invoiceId) {
        Invoice data = invoiceBizApi.get(invoiceId).getData();
        if (Objects.isNull(data)) {
            return R.fail("未查询到开票数据，请核对发票数据");
        }
        InvoiceUpdateDTO update = InvoiceUpdateDTO.builder()
                .id(invoiceId)
                .invoiceStatus(InvoiceStatusEnum.OPEN_SUCCESS.getCode())
                .printTimes(Objects.isNull(data.getPrintTimes()) ? 1 : data.getPrintTimes() + 1)
                .build();
        return invoiceBizApi.update(update);
    }

}
