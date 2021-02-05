package com.cdqckj.gmis.invoice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.domain.sts.InvoiceDayStsVo;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.QueryWrap;
import com.cdqckj.gmis.invoice.dto.*;
import com.cdqckj.gmis.invoice.dto.rhapi.ResponseData;
import com.cdqckj.gmis.invoice.dto.rhapi.einvoice.CxParam;
import com.cdqckj.gmis.invoice.entity.Invoice;
import com.cdqckj.gmis.invoice.service.InvoiceService;
import com.cdqckj.gmis.invoice.vo.InvoiceResponseData;
import com.cdqckj.gmis.security.annotation.PreAuth;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * <p>
 * 前端控制器
 *
 * </p>
 *
 * @author songyz
 * @date 2020-09-07
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/invoice")
@Api(value = "Invoice", tags = "发票记录")
@PreAuth(replace = "invoice:")
public class InvoiceController extends SuperController<InvoiceService, Long, Invoice, InvoicePageDTO, InvoiceSaveDTO, InvoiceUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list) {
        List<Invoice> invoiceList = list.stream().map((map) -> {
            Invoice invoice = Invoice.builder().build();
            //TODO 请在这里完成转换
            return invoice;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(invoiceList));
    }

    @ApiOperation(value = "根据某个字段批量查询发票")
    @PostMapping("/listByMap")
    public R<List<Invoice>> listByMap(@RequestBody Map<String, Object> columnMap) {
        return success(baseService.listByMap(columnMap));
    }

    @ApiOperation(value = "查询一段时间内的发票")
    @PostMapping("/queryInvoice")
    public R<List<Invoice>> queryInvoice(@RequestParam(value = "startTime") LocalDateTime startTime,
                                         @RequestParam(value = "endTime") LocalDateTime endTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        QueryWrapper<Invoice> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .and(dateSql -> dateSql.ge(Invoice::getCreateTime, startTime.format(dateTimeFormatter))
                        .le(Invoice::getCreateTime, endTime.format(dateTimeFormatter)));
        return success(getBaseService().list(wrapper));
    }

    @ApiOperation(value = "校验发票编号是否存在")
    @GetMapping("/checkInvoiceNumber")
    public R<Integer> checkInvoiceNumber(@RequestParam(value = "invoiceNumber") String invoiceNumber) {
        Integer count = baseService.checkInvoiceNumber(invoiceNumber);
        return R.success(count);
    }

    /**
     * 分页查询条件
     *
     * @param params
     * @param page
     * @param defSize
     */
    @Override
    public void query(PageParams<InvoicePageDTO> params, IPage<Invoice> page, Long defSize) {
        handlerQueryParams(params);

        if (defSize != null) {
            page.setSize(defSize);
        }

        QueryWrap<Invoice> wrapper = Wraps.q();

        handlerWrapper(wrapper, params);
        baseService.findPage(params, page);

        // 处理结果
        handlerResult(page);
    }


    @ApiOperation(value = "通过缴费订单生成发票")
    @PostMapping("/addByCharge")
    public R<InvoiceResponseData> createInvoiceByChargeNo(@RequestBody InvoiceAddDTO invoiceAddDTO) {

        return R.success(baseService.createInvoiceByChargeNo(invoiceAddDTO));
    }

    @ApiOperation(value = "瑞宏电子发票服务平台冲红发票")
    @PostMapping("/rhInvoiceCh")
    public R<ResponseData> rhInvoiceCh(@RequestBody InvoiceChDTO invoiceChDTO) {
        return R.success(baseService.invoiceCh(invoiceChDTO));
    }


    @ApiOperation(value = "查询电子发票服务平台生成的发票")
    @PostMapping("/invoiceCx")
    public R<ResponseData> invoiceCx(@RequestBody CxParam cxParam) {
        return R.success(baseService.invoiceCx(cxParam));
    }


    @ApiOperation(value = "电子发票平台开票结果回调接口")
    @PostMapping("/rhKpCallback")
    public R<Boolean> rhKpCallback(@RequestBody InvoiceCallbackDTO callback) {
        //更新开票结果、 记录回调日志
        log.info("瑞宏回调接口：" + callback.getResult());
        log.info("回调接口租户：" + callback.getTenant());
        //切换租户数据库
        BaseContextHandler.setTenant(callback.getTenant());
        baseService.rhKpCallback(callback);
        return R.success();
    }

    @ApiOperation(value = "重新推送发票服务平台开票")
    @PostMapping("/retryPrint")
    @GlobalTransactional
    public R<InvoiceResponseData> retryPrint(@RequestBody InvoiceAddDTO invoiceAddDTO) {
        return R.success(baseService.retryPrint(invoiceAddDTO));
    }

    @ApiOperation(value = "重新开票")
    @PostMapping("/retryKp")
    @GlobalTransactional
    public R<InvoiceResponseData> retryKp(@RequestBody InvoiceAddDTO invoiceAddDTO) {
        if (Objects.isNull(invoiceAddDTO.getInvoiceId())) {
            return R.fail("发票id不能为空");
        }
        return R.success(baseService.retryKp(invoiceAddDTO));
    }

    @ApiOperation(value = "发票冲红")
    @PostMapping("/invoiceCh")
    R<ResponseData> invoiceCh(@RequestBody InvoiceChDTO invoiceChDTO) {
        return R.success(baseService.invoiceCh(invoiceChDTO));
    }

    /**
     * 通过缴费编号查询未冲红、作废的且开票中或开票成功的发票
     *
     * @param chargeNo 缴费编号
     * @return 发票数据
     */
    @GetMapping("/getEffectiveInvoice")
    public R<Invoice> getEffectiveInvoice(@RequestParam(value = "chargeNo") String chargeNo) {
        return R.success(baseService.getEffectiveInvoice(chargeNo));
    }

    /**
     * 已扎帐发票明细查询
     *
     * @param current
     * @param size
     * @param summaryId
     * @return
     */
    @ApiOperation(value = "已扎帐发票明细查询")
    @PostMapping("/pageInvoice")
    public R<Page<Invoice>> pageInvoice(@RequestParam("current") Integer current,
                                               @RequestParam("size") Integer size, @RequestParam("summaryId") Long summaryId) {
        return baseService.pageInvoice(current, size, summaryId);
    }

    /**
     * 关联缴费数据查询发票接口
     * @param startTime
     * @param endTime
     * @return
     */
    @ApiOperation("关联缴费数据查询发票接口")
    @GetMapping("/queryInvoicesByTimeFrame")
    public R<List<Invoice>> queryInvoicesByTimeFrame(@RequestParam(value = "startTime", required = false) LocalDateTime startTime,@RequestParam(value = "endTime", required = false) LocalDateTime endTime){
        return success(baseService.queryInvoicesByTimeFrame(startTime, endTime));
    }
    /**
     * 根据扎帐ID查询发票信息
     * @param summaryId
     * @return
     */
    @ApiOperation("根据扎帐ID查询发票信息")
    @GetMapping("/queryBySummaryId")
    public R<List<Invoice>> queryInvoicesBySummaryId(@RequestParam("summaryId") Long summaryId){
        return success(baseService.queryInvoicesBySummaryId(summaryId));
    }
    /**
     * 根据时间范围分页查询缴费记录的发票信息
     * @param current
     * @param size
     * @param startTime
     * @param endTime
     * @return
     */
    @ApiOperation("根据时间范围分页查询缴费记录的发票信息")
    @GetMapping("/pageInvoicesByTimeFrame")
    public R<Page<Invoice>> pageInvoicesByTimeFrame(@RequestParam("current") long current, @RequestParam("size") long size,
                                                          @RequestParam(value = "startTime", required = false) LocalDateTime startTime, @RequestParam(value = "endTime", required = false) LocalDateTime endTime){
        return success(baseService.pageInvoicesByTimeFrame(current, size, startTime, endTime));
    }

    /**
     * @Author: lijiangguo
     * @Date: 2021/1/15 16:35
     * @remark 统计一个类型的发票
     */
    @PostMapping("/sts/invoiceStsByType")
    R<List<InvoiceDayStsVo>> invoiceStsByType(@RequestBody StsSearchParam stsSearchParam){
        List<InvoiceDayStsVo> voList = this.baseService.invoiceStsByType(stsSearchParam);
        return R.success(voList);
    }
}
