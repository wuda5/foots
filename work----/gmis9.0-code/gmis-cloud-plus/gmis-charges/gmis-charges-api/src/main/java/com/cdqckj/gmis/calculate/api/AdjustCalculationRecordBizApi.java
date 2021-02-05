package com.cdqckj.gmis.calculate.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.charges.dto.AdjustCalculationRecordPageDTO;
import com.cdqckj.gmis.charges.entity.AdjustCalculationRecord;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author songyz
 * 核算任务
 */
@FeignClient(name = "${gmis.feign.gmis-charges-server:gmis-charges-server}", fallbackFactory  = HystrixTimeoutFallbackFactory.class
        , path = "/adjustCalculationRecord", qualifier = "AdjustCalculationRecordBizApi")
public interface AdjustCalculationRecordBizApi {
    /**
     * 分页查询
     * @param params
     * @return
     */
    @ApiOperation(value = "分页列表查询")
    @PostMapping(value = "/page")
    public R<Page<AdjustCalculationRecord>> page(@RequestBody @Validated PageParams<AdjustCalculationRecordPageDTO> params);

    /**
     * 查询核算任务统计数
     * @param startTime
     * @param endTime
     * @return
     */
    @ApiOperation(value = "查询核算任务统计数", notes = "查询核算任务统计数")
    @GetMapping("/caculationStatistics")
    public Map getCaculationTaskInfo(@RequestParam(value = "startTime") LocalDateTime startTime,
                                     @RequestParam(value = "endTime") LocalDateTime endTime);
    /**
     * 生成核算数据重试
     * @return
     */
    @ApiOperation(value = "生成核算数据重试")
    @PostMapping(value = "/retry")
    public R<Boolean> retry(@RequestBody AdjustCalculationRecord adjustCalculationRecord);
}
