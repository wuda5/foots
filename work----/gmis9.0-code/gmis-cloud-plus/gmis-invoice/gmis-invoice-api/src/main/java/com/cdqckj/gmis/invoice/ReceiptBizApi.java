package com.cdqckj.gmis.invoice;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.entity.SuperEntity;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.invoice.dto.ReceiptPageDTO;
import com.cdqckj.gmis.invoice.dto.ReceiptSaveDTO;
import com.cdqckj.gmis.invoice.dto.ReceiptUpdateDTO;
import com.cdqckj.gmis.invoice.entity.Receipt;
import com.cdqckj.gmis.invoice.hystrix.ReceiptBizApiFallback;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@FeignClient(name = "${gmis.feign.gmis-invoice-server:gmis-invoice-server}", fallback = ReceiptBizApiFallback.class
        , path = "/receipt", qualifier = "ReceiptBizApi")
public interface ReceiptBizApi {

    /**
     * 新增
     *
     * @param saveDTO 保存参数
     * @return 实体
     */
    @ApiOperation(value = "新增")
    @PostMapping
    public R<Receipt> save(@RequestBody @Valid ReceiptSaveDTO saveDTO);

    /**
     * 修改
     *
     * @param updateDTO
     * @return
     */
    @ApiOperation(value = "修改")
    @PutMapping
    public R<Receipt> update(@RequestBody @Validated(SuperEntity.Update.class) ReceiptUpdateDTO updateDTO);

    /**
     * 查询
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "查询", notes = "查询")
    @GetMapping("/{id}")
    public R<Receipt> get(@PathVariable(value = "id") Long id);

    /**
     * 批量查询
     *
     * @param data 批量查询
     * @return 查询结果
     */
    @ApiOperation(value = "批量查询", notes = "批量查询")
    @PostMapping("/query")
    public R<List<Receipt>> query(@RequestBody Receipt data);

    /**
     * 分页查询
     *
     * @param params
     * @return
     */
    @ApiOperation(value = "分页列表查询")
    @PostMapping(value = "/page")
    public R<Page<Receipt>> page(@RequestBody @Validated PageParams<ReceiptPageDTO> params);

    @PostMapping("/queryReceipt")
    public R<List<Receipt>> queryReceipt(@RequestParam(value = "startTime") LocalDateTime startTime,
                                         @RequestParam(value = "endTime") LocalDateTime endTime);


}
