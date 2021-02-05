package com.cdqckj.gmis.charges.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.charges.dto.SummaryBillPageDTO;
import com.cdqckj.gmis.charges.dto.SummaryBillSaveDTO;
import com.cdqckj.gmis.charges.dto.SummaryBillUpdateDTO;
import com.cdqckj.gmis.charges.entity.ChargeRecord;
import com.cdqckj.gmis.charges.entity.SummaryBill;
import com.cdqckj.gmis.charges.service.SummaryBillService;
import com.cdqckj.gmis.context.BaseContextHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <p>
 * 前端控制器
 * 
 * </p>
 *
 * @author songyz
 * @date 2020-09-16
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/summaryBill")
@Api(value = "SummaryBill", tags = "扎帐信息")
//@PreAuth(replace = "summaryBill:")
public class SummaryBillController extends SuperController<SummaryBillService, Long, SummaryBill, SummaryBillPageDTO, SummaryBillSaveDTO, SummaryBillUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<SummaryBill> summaryBillList = list.stream().map((map) -> {
            SummaryBill summaryBill = SummaryBill.builder().build();
            //TODO 请在这里完成转换
            return summaryBill;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(summaryBillList));
    }

    @Override
    public R<SummaryBill> handlerUpdate(SummaryBillUpdateDTO model) {
        UpdateWrapper<SummaryBill> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
                .eq(SummaryBill::getId, model.getId());
        SummaryBill summaryBill = BeanUtil.toBean(model, getEntityClass());
        getBaseService().update(summaryBill, updateWrapper);
        return success(summaryBill);
    }

    /**
     * 获取最后扎帐日期
     * @return
     */
    @ApiOperation(value = "获取最后扎帐日期")
    @GetMapping("/lastSummaryBillDate")
    public R<SummaryBill> getLastSummaryBillDate(){
        QueryWrapper<SummaryBill> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("ifnull(max(summary_end_date), null) as summaryEndDate");
        queryWrapper.eq("operator_no", String.valueOf(BaseContextHandler.getUserId()));
        SummaryBill summaryBill;
        try {
            summaryBill = baseService.getOne(queryWrapper);
        } catch (Exception e) {
            log.error("获取最后扎帐日期,{}", e.getMessage(), e);
            return R.fail("获取最后扎帐日期异常数据大于两条");
        } finally {
        }
        return R.success(summaryBill);
    }
    /**
     * 已扎帐收费明细记录查询
     * @param current
     * @param size
     * @param summaryId
     * @return
     */
    @ApiOperation(value = "已扎帐收费明细记录查询")
    @PostMapping("/pageSummaryChargeRecord")
    public R<Page<ChargeRecord>> pageSummaryChargeRecord(@RequestParam("current") long current,
                                                         @RequestParam("size") long size, @RequestParam("summaryId") Long summaryId){
        return baseService.pageSummaryChargeRecord(current, size, summaryId);
    }
}
