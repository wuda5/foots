package com.cdqckj.gmis.invoice;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.entity.SuperEntity;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.domain.sts.InvoiceDayStsVo;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.invoice.dto.*;
import com.cdqckj.gmis.invoice.dto.rhapi.ResponseData;
import com.cdqckj.gmis.invoice.entity.Invoice;
import com.cdqckj.gmis.invoice.vo.InvoiceResponseData;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@FeignClient(name = "${gmis.feign.gmis-invoice-server:gmis-invoice-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/invoice", qualifier = "InvoiceBizApi")
public interface InvoiceBizApi {

    /**
     * ID查询发票记录
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    R<Invoice> get(@PathVariable("id") Long id);

    /**
     * 新增
     *
     * @param saveDTO 保存参数
     * @return 实体
     */
    @ApiOperation(value = "新增")
    @PostMapping
    public R<Invoice> save(@RequestBody @Valid InvoiceSaveDTO saveDTO);

    /**
     * 修改
     *
     * @param updateDTO
     * @return
     */
    @ApiOperation(value = "修改")
    @PutMapping
    public R<Invoice> update(@RequestBody @Validated(SuperEntity.Update.class) InvoiceUpdateDTO updateDTO);

    /**
     * 批量查询
     *
     * @param data 批量查询
     * @return 查询结果
     */
    @ApiOperation(value = "批量查询", notes = "批量查询")
    @PostMapping("/query")
    public R<List<Invoice>> query(@RequestBody Invoice data);

    /**
     * 分页查询
     *
     * @param params
     * @return
     */
    @ApiOperation(value = "分页列表查询")
    @PostMapping(value = "/page")
    public R<Page<Invoice>> page(@RequestBody @Validated PageParams<InvoicePageDTO> params);

    /**
     * 查询一段时间内的发票
     *
     * @param startTime
     * @param endTime
     * @return
     */
    @PostMapping("/queryInvoice")
    R<List<Invoice>> queryInvoice(@RequestParam(value = "startTime") LocalDateTime startTime,
                                  @RequestParam(value = "endTime") LocalDateTime endTime);

    /**
     * 校验发票编号是否存在
     *
     * @param invoiceNumber
     * @return
     */
    @GetMapping("/checkInvoiceNumber")
    R<Integer> checkInvoiceNumber(@RequestParam(value = "invoiceNumber") String invoiceNumber);

    /**
     * 通过缴费订单生成发票
     *
     * @param invoiceAddDTO
     * @return
     */
    @PostMapping("/addByCharge")
    R<InvoiceResponseData> createInvoiceByChargeNo(@RequestBody InvoiceAddDTO invoiceAddDTO);

    /**
     * 瑞宏开票回调
     *
     * @param callback
     * @return
     */
    @PostMapping("/rhKpCallback")
    R<Boolean> rhKpCallback(@RequestBody InvoiceCallbackDTO callback);

    /**
     * 重新推送发票到服务平台开票
     *
     * @param invoiceAddDTO 发票id
     * @return 推送结果
     */
    @PostMapping("/retryPrint")
    public R<InvoiceResponseData> retryPrint(@RequestBody InvoiceAddDTO invoiceAddDTO);

    /**
     * 重新开票
     *
     * @param invoiceAddDTO 重新开票参数
     * @return 重新开票结果
     */
    @PostMapping("/retryKp")
    public R<InvoiceResponseData> retryKp(@RequestBody InvoiceAddDTO invoiceAddDTO);

    /**
     * 电子发票冲红
     *
     * @param invoiceChDTO 电子发票冲红
     * @return 电子发票冲红结果
     */
    @PostMapping("/invoiceCh")
    R<ResponseData> invoiceCh(@RequestBody InvoiceChDTO invoiceChDTO);

    /**
     * 通过缴费编号查询未冲红、作废的且开票中或开票成功的发票
     *
     * @param chargeNo 缴费编号
     * @return 发票数据
     */
    @GetMapping("/getEffectiveInvoice")
    public R<Invoice> getEffectiveInvoice(@RequestParam(value = "chargeNo") String chargeNo);

    /**
     * 根据某个字段批量查询发票
     * @param columnMap
     * @return
     */
    @ApiOperation(value = "根据某个字段批量查询发票")
    @PostMapping("/listByMap")
    R<List<Invoice>> listByMap(@RequestBody Map<String, Object> columnMap);
    /**
     * 已扎帐发票明细查询
     * @param current
     * @param size
     * @param summaryId
     * @return
     */
    @ApiOperation(value = "已扎帐发票明细查询")
    @PostMapping("/pageInvoice")
    R<Page<Invoice>> pageInvoice(@RequestParam("current") Integer current,
                                               @RequestParam("size") Integer size, @RequestParam("summaryId") Long summaryId);
    /**
     * 关联缴费数据查询发票接口
     * @param startTime
     * @param endTime
     * @return
     */
    @ApiOperation("关联缴费数据查询发票接口")
    @GetMapping("/queryInvoicesByTimeFrame")
    R<List<Invoice>> queryInvoicesByTimeFrame(@RequestParam(value = "startTime", required = false) LocalDateTime startTime,@RequestParam(value = "endTime", required = false) LocalDateTime endTime);
    /**
     * 根据扎帐ID查询发票信息
     * @param summaryId
     * @return
     */
    @ApiOperation("根据扎帐ID查询发票信息")
    @GetMapping("/queryBySummaryId")
    R<List<Invoice>> queryInvoicesBySummaryId(@RequestParam("summaryId") Long summaryId);
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
    R<Page<Invoice>> pageInvoicesByTimeFrame(@RequestParam("current") long current, @RequestParam("size") long size,
                                                          @RequestParam(value = "startTime", required = false) LocalDateTime startTime, @RequestParam(value = "endTime", required = false) LocalDateTime endTime);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/15 16:35
    * @remark 统计一个类型的发票
    */
    @PostMapping("/sts/invoiceStsByType")
    R<List<InvoiceDayStsVo>> invoiceStsByType(@RequestBody StsSearchParam stsSearchParam);
}
