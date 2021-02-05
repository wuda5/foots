package com.cdqckj.gmis.charges.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.entity.SuperEntity;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.charges.dto.SummaryBillPageDTO;
import com.cdqckj.gmis.charges.dto.SummaryBillSaveDTO;
import com.cdqckj.gmis.charges.dto.SummaryBillUpdateDTO;
import com.cdqckj.gmis.charges.entity.ChargeRecord;
import com.cdqckj.gmis.charges.entity.SummaryBill;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@FeignClient(name = "${gmis.feign.gmis-charges-server:gmis-charges-server}",fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/summaryBill", qualifier = "SummaryBillBizApi")
public interface SummaryBillBizApi {

    /**
     * 新增
     *
     * @param saveDTO 保存参数
     * @return 实体
     */
    @ApiOperation(value = "新增")
    @PostMapping
    public R<SummaryBill> save(@RequestBody @Valid SummaryBillSaveDTO saveDTO);

    /**
     * 修改
     *
     * @param updateDTO
     * @return
     */
    @ApiOperation(value = "修改")
    @PutMapping
    public R<SummaryBill> update(@RequestBody @Validated(SuperEntity.Update.class) SummaryBillUpdateDTO updateDTO);

    /**
     * 批量查询
     *
     * @param data 批量查询
     * @return 查询结果
     */
    @ApiOperation(value = "批量查询", notes = "批量查询")
    @PostMapping("/query")
    public R<List<SummaryBill>> query(@RequestBody SummaryBill data);

    /**
     * 分页查询
     *
     * @param params
     * @return
     */
    @ApiOperation(value = "分页列表查询")
    @PostMapping(value = "/page")
    public R<Page<SummaryBill>> page(@RequestBody @Validated PageParams<SummaryBillPageDTO> params);

    /**
     * 获取最后扎帐日期
     * @return
     */
    @ApiOperation(value = "获取最后扎帐日期")
    @GetMapping("/lastSummaryBillDate")
    public R<SummaryBill> getLastSummaryBillDate();

    /**
     * 已扎帐收费明细记录查询
     * @param current
     * @param size
     * @param summaryId
     * @return
     */
    @ApiOperation(value = "已扎帐收费明细记录查询")
    @PostMapping("/pageSummaryChargeRecord")
    R<Page<ChargeRecord>> pageSummaryChargeRecord(@RequestParam("current") long current,
                                                  @RequestParam("size") long size, @RequestParam("summaryId") Long summaryId);
    /**
     * 查询扎帐信息
     * @param endTime
     * @return 查询结果
     */
    @ApiOperation(value = "查询扎帐信息", notes = "查询扎帐信息")
    @PostMapping("/getSummaryBills")
    public R<List<SummaryBill>> getSummaryBills(@RequestParam("endTime") LocalDateTime endTime);
}
