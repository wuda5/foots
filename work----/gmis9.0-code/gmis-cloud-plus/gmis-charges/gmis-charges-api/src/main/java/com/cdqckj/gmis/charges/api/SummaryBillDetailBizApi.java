package com.cdqckj.gmis.charges.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.entity.SuperEntity;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.charges.dto.SummaryBillDetailPageDTO;
import com.cdqckj.gmis.charges.dto.SummaryBillDetailSaveDTO;
import com.cdqckj.gmis.charges.dto.SummaryBillDetailUpdateDTO;
import com.cdqckj.gmis.charges.entity.SummaryBillDetail;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

/**
 * @author songyz
 */
@FeignClient(name = "${gmis.feign.gmis-charges-server:gmis-charges-server}",fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/summaryBillDetail", qualifier = "SummaryBillDetailBizApi")
public interface SummaryBillDetailBizApi {

    /**
     * 新增
     *
     * @param saveDTO 保存参数
     * @return 实体
     */
    @ApiOperation(value = "新增")
    @PostMapping
    public R<SummaryBillDetail> save(@RequestBody @Valid SummaryBillDetailSaveDTO saveDTO);

    /**
     * 修改
     *
     * @param updateDTO
     * @return
     */
    @ApiOperation(value = "修改")
    @PutMapping
    public R<SummaryBillDetail> update(@RequestBody @Validated(SuperEntity.Update.class) SummaryBillDetailUpdateDTO updateDTO);

    /**
     * 批量查询
     *
     * @param data 批量查询
     * @return 查询结果
     */
    @ApiOperation(value = "批量查询", notes = "批量查询")
    @PostMapping("/query")
    public R<List<SummaryBillDetail>> query(@RequestBody SummaryBillDetail data);

    /**
     * 分页查询
     *
     * @param params
     * @return
     */
    @ApiOperation(value = "分页列表查询")
    @PostMapping(value = "/page")
    public R<IPage<SummaryBillDetail>> page(@RequestBody @Validated PageParams<SummaryBillDetailPageDTO> params);
    /**
     * 新增
     *
     * @param list 保存参数
     * @return 实体
     */
    @ApiOperation(value = "批量新增")
    @PostMapping(value = "/saveList")
    R<SummaryBillDetail> saveList(@RequestBody List<SummaryBillDetailSaveDTO> list);

}
