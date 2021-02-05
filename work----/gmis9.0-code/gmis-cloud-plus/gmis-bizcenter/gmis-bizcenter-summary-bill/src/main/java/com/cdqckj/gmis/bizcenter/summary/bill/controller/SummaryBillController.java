package com.cdqckj.gmis.bizcenter.summary.bill.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.bizcenter.summary.bill.service.SummaryBillService;
import com.cdqckj.gmis.charges.api.SummaryBillBizApi;
import com.cdqckj.gmis.charges.dto.ChargeRecordPageDTO;
import com.cdqckj.gmis.charges.dto.SummaryBillPageDTO;
import com.cdqckj.gmis.charges.entity.ChargeRecord;
import com.cdqckj.gmis.charges.entity.SummaryBill;
import com.cdqckj.gmis.charges.enums.SummaryBillStatusEnum;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.invoice.dto.InvoicePageDTO;
import com.cdqckj.gmis.invoice.entity.Invoice;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author songyz
 * @author hp
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/summaryBill")
@Api(value = "summaryBill", tags = "扎帐")
public class SummaryBillController {

    @Autowired
    private SummaryBillService summaryBillService;
    @Autowired
    private SummaryBillBizApi summaryBillBizApi;

    /**
     * 查询扎帐信息
     * @param endTime
     * @return
     */
    @ApiOperation(value = "查询扎帐信息")
    @PostMapping(value = "/query")
    public R<SummaryBill> querySummaryBill(@RequestParam(value = "startTime",required = false) LocalDateTime startTime, @RequestParam(value = "endTime", required = false) LocalDateTime endTime) {
        return summaryBillService.querySummaryBill(startTime, endTime);
    }

    /**
     * 扎帐
     * @param summaryBill
     * @return
     */
    @ApiOperation(value = "扎帐")
    @PostMapping(value = "/summaryBill")
    public R<SummaryBill> summaryBill(@RequestBody SummaryBill summaryBill) {
        return summaryBillService.summaryBill(summaryBill);
    }

    /**
     * 反扎帐
     * @param summaryBills
     * @return
     */
    @ApiOperation(value = "反扎帐")
    @PostMapping(value = "/reverse")
    public R<List<SummaryBill>> reverseSummaryBill(@RequestBody List<SummaryBill> summaryBills) {
        return summaryBillService.reverseSummaryBill(summaryBills);
    }

    /**
     * 导出扎帐信息
     * @return
     */
    @ApiOperation(value = "导出扎帐信息")
    @PostMapping(value = "/export")
    public void exportSummaryBill(HttpServletRequest request, HttpServletResponse response,
                                  @RequestBody SummaryBill summaryBill) {
        try {
            summaryBillService.exportSummaryBill(request, response, summaryBill);
        } catch (Exception e) {
            log.error("下载扎帐信息异常，{}", e.getMessage(), e);
        }
    }

    /**
     * 待扎帐收费明细记录查询
     * @param params
     * @return
     */
    @ApiOperation(value = "待扎帐收费明细记录查询")
    @PostMapping(value = "/queryChargeRecordList")
    public R<Page<ChargeRecord>> queryChargeRecordList(@RequestBody @Validated PageParams<ChargeRecordPageDTO> params) {
        return summaryBillService.pageChargeRecordList(params);
    }

    /**
     * 待扎帐发票明细查询
     * @param params
     * @return
     */
    @ApiOperation(value = "待扎帐发票明细查询")
    @PostMapping(value = "/queryInvoiceList")
    public R<Page<Invoice>> queryInvoiceList(@RequestBody @Validated PageParams<InvoicePageDTO> params) {
        return summaryBillService.pageInvoiceList(params);
    }

    /**
     * 获取最后扎帐日期
     * @return
     */
    @ApiOperation(value = "获取最后扎帐日期")
    @GetMapping("/lastSummaryBillDate")
    public R<SummaryBill> getLastSummaryBillDate(){
        return summaryBillBizApi.getLastSummaryBillDate();
    }

    /**
     * 扎帐记录分页查询
     * @param params
     * @return
     */
    @ApiOperation(value = "扎帐记录分页查询")
    @PostMapping(value = "/querySummaryBillRecord")
    public R<Page<SummaryBill>> page(@RequestBody @Validated PageParams<SummaryBillPageDTO> params){
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
        SummaryBillPageDTO model = SummaryBillPageDTO
                .builder()
                .operatorNo(String.valueOf(BaseContextHandler.getUserId()))
                .dataStatus(DataStatusEnum.NORMAL.getValue())
                .summaryBillStatus(SummaryBillStatusEnum.BILLED.getCode())
                .build();
        params.setModel(model);
        return summaryBillBizApi.page(params);
    }

    /**
     * 已扎帐收费明细记录查询
     * @param params
     * @return
     */
    @ApiOperation(value = "已扎帐收费明细记录查询")
    @PostMapping(value = "/querySummaryedCharges")
    public R<Page<ChargeRecord>> querySummaryedCharges(@RequestBody @Validated PageParams<SummaryBill> params) {
        SummaryBill summaryBill = params.getModel();
        return summaryBillService.queryChargeRecordList(params.getCurrent(), params.getSize(), summaryBill);
    }

    /**
     * 已扎帐发票明细查询
     * @param params
     * @return
     */
    @ApiOperation(value = "已扎帐发票明细查询")
    @PostMapping(value = "/querySummaryedInvoices")
    public R<Page<Invoice>> querySummaryedInvoices(@RequestBody @Validated PageParams<SummaryBill> params) {
        SummaryBill summaryBill = params.getModel();
        Long current = params.getCurrent();
        Long size = params.getSize();
        return summaryBillService.queryInvoiceList(current.intValue(), size.intValue(), summaryBill);
    }


}
