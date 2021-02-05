package com.cdqckj.gmis.readmeter.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.common.utils.UserOrgIdUtil;
import com.cdqckj.gmis.log.annotation.SysLog;
import com.cdqckj.gmis.readmeter.dto.ReadMeterBookPageDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterBookSaveDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterBookUpdateDTO;
import com.cdqckj.gmis.readmeter.entity.*;
import com.cdqckj.gmis.readmeter.service.ReadMeterBookService;
import com.cdqckj.gmis.readmeter.service.ReadMeterDataService;
import com.cdqckj.gmis.readmeter.service.ReadMeterPlanScopeService;
import com.cdqckj.gmis.readmeter.service.ReadMeterPlanService;
import com.cdqckj.gmis.security.annotation.PreAuth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


/**
 * <p>
 * 前端控制器
 * 抄表册
 * </p>
 *
 * @author gmis
 * @date 2020-07-13
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/readMeterBook")
@Api(value = "ReadMeterBook", tags = "抄表册")
@PreAuth(replace = "readMeterBook:")
public class ReadMeterBookController extends SuperController<ReadMeterBookService, Long, ReadMeterBook, ReadMeterBookPageDTO, ReadMeterBookSaveDTO, ReadMeterBookUpdateDTO> {

    @Autowired
    private ReadMeterPlanScopeService planScopeService;
    @Autowired
    private ReadMeterDataService dataService;
    @Autowired
    private ReadMeterPlanService planService;

    @PostMapping(value = "/queryByWrap")
    R<IPage<ReadMeterBook>> queryByWrap(@RequestBody PageParams<ReadMeterPlan> params) {

        ReadMeterPlan module = params.getModel();
        LambdaQueryWrapper<ReadMeterPlan> planquery = new LambdaQueryWrapper(module);
        List<ReadMeterPlan> list = planService.list(planquery);
        List<Long> planList = list.stream().map(ReadMeterPlan::getId).collect(Collectors.toList());
        LambdaQueryWrapper<ReadMeterPlanScope> scpoeQuery = new LambdaQueryWrapper();
        scpoeQuery.in(ReadMeterPlanScope::getReadmeterPlanId, planList);
        List<ReadMeterPlanScope> planScope = planScopeService.list(scpoeQuery);
        List<Long> bookIds = planScope.stream().map(ReadMeterPlanScope::getBookId).distinct().collect(Collectors.toList());
        Page page = new Page(params.getCurrent(), params.getSize());
        //readMeterBookApi.queryByWrap(bookIds.size()>0?bookIds:null,page);
        LambdaQueryWrapper<ReadMeterBook> query = new LambdaQueryWrapper();
        query.eq(ReadMeterBook::getDeleteStatus,0);
        if(bookIds!=null && bookIds.size()>0){
            query.notIn(ReadMeterBook::getId,bookIds);
        }
        getBaseService().page(page, query);
        return success(page);
    }

    /**
     * 新增抄表册信息
     * @param book
     * @return
     */
    @ApiOperation(value = "新增抄表册信息", notes = "新增抄表册信息")
    @PostMapping("/saveBook")
    @SysLog("新增抄表册信息")
    R<Boolean> saveBook(@RequestBody ReadMeterBook book){
        return baseService.saveBook(book);
    }

    /**
     * 跟新抄表册信息
     * @param book
     * @return
     */
    @ApiOperation(value = "跟新抄表册信息", notes = "跟新抄表册信息")
    @PostMapping("/updateBookDetail")
    @SysLog("跟新抄表册信息")
    R<Boolean> updateBookDetail(@RequestBody ReadMeterBook book){
        return baseService.updateBookDetail(book);
    };

    @Override
    public R<IPage<ReadMeterBook>> page(@RequestBody @Validated PageParams<ReadMeterBookPageDTO> params) {
        return baseService.page(params);
    }
}
