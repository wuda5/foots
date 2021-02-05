package com.cdqckj.gmis.bizcenter.read.meter.controller;

import cn.afterturn.easypoi.entity.vo.NormalExcelConstants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.archive.CustomerBizApi;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.bizcenter.read.meter.service.ReadMeterPlanBizService;
import com.cdqckj.gmis.common.utils.ExportUtil;
import com.cdqckj.gmis.readmeter.*;
import com.cdqckj.gmis.readmeter.dto.*;
import com.cdqckj.gmis.readmeter.entity.ReadMeterBook;
import com.cdqckj.gmis.readmeter.entity.ReadMeterPlan;
import com.cdqckj.gmis.readmeter.entity.ReadMeterPlanScope;
import com.cdqckj.gmis.readmeter.vo.ReadMeterPlanScopeParam;
import feign.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 * <p>
 * 抄表计划前端控制器
 * </p>
 * @author gmis
 * @date 2020-07-01
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/readmeter/register")
@Api(value = "register", tags = "抄表计划")
//@PreAuth(replace = "readMeterPlan:")
public class ReadMeterPlanController {

    @Autowired
    public ReadMeterPlanApi readMeterPlanApi;
    @Autowired
    public ReadMeterPlanScopeApi readMeterPlanScopeApi;
    @Autowired
    public ReadMeterDataApi readMeterDataApi;
    @Autowired
    public ReadMeterBookApi readMeterBookApi;
    @Autowired
    public CustomerBizApi customerBizApi;
    @Autowired
    public GasMeterBookRecordApi gasMeterBookRecordApi;
    @Autowired
    public ReadMeterMonthGasApi readMeterMonthGasApi;
    @Autowired
    public ReadMeterPlanBizService readMeterPlanService;

    @ApiOperation(value = "新增抄表计划信息")
    @PostMapping("/readMeterPlan/add")
    public R<ReadMeterPlan> saveReadMeterPlan(@RequestBody ReadMeterPlanSaveDTO readMeterPlanSaveDTO){
        return readMeterPlanApi.save(readMeterPlanSaveDTO);
    }

    @ApiOperation(value = "更新抄表计划信息")
    @PutMapping("/readMeterPlan/update")
    public R<ReadMeterPlan> updateReadMeterPlan(@RequestBody ReadMeterPlanUpdateDTO readMeterPlanUpdateDTO){
        return readMeterPlanApi.update(readMeterPlanUpdateDTO);
    }

    @ApiOperation(value = "修改计划状态（-1：未开启；1：执行中；2-暂停；0-执行完成）")
    @PutMapping("/readMeterPlan/updateSatus")
    public R<ReadMeterPlan> updateSatus(@RequestBody ReadMeterPlanUpdateDTO readMeterPlanUpdateDTO){
        return readMeterPlanService.updateSatus(readMeterPlanUpdateDTO);
    }

    @ApiOperation(value = "（单个）删除抄表计划信息")
    @DeleteMapping("/readMeterPlan/delete")
    public R<Boolean> deleteReadMeterPlan(@RequestParam("PlanIds") List<Long> ids){
        List<ReadMeterPlan> planList = readMeterPlanApi.queryList(ids).getData();
        if(planList.size()>0){
            return R.fail("已关联抄表册，无法删除");
        }
        return readMeterPlanApi.logicalDelete(ids);
    }

    @ApiOperation(value = "分页查询抄表计划信息")
    @PostMapping("/readMeterPlan/page")
    public R<Page<ReadMeterPlan>> pageReadMeterPlan(@RequestBody PageParams<ReadMeterPlanPageDTO> params){
        return readMeterPlanApi.page(params);
    }

    @ApiOperation(value = "新增抄表任务分配信息(之前接口，支持单个新增)")
    @PostMapping("/planScope/add")
    public R<ReadMeterPlanScope> saveReadMeterPlanScope(@RequestBody ReadMeterPlanScopeSaveDTO readMeterPlanScopeSaveDTO){
        return readMeterPlanService.saveReadMeterPlanScope(readMeterPlanScopeSaveDTO);
    }

    @ApiOperation(value = "批量新增抄表任务分配信息")
    @PostMapping("/planScope/addList")
    public R<Boolean> savePlanScopeList(@RequestBody ReadMeterPlanScopeParam param){
        return readMeterPlanService.savePlanScopeList(param);
    }

    @ApiOperation(value = "更新抄表任务分配信息")
    @PutMapping("/planScope/update")
    public R<ReadMeterPlanScope> updateReadMeterPlanScope(@RequestBody ReadMeterPlanScopeUpdateDTO readMeterPlanScopeUpdateDTO){
        return readMeterPlanScopeApi.update(readMeterPlanScopeUpdateDTO);
    }

    @ApiOperation(value = "删除抄表任务分配信息")
    @PostMapping("/planScope/delete")
    public R<Boolean> deleteReadMeterPlanScope(@RequestBody List<Long> ids){
        return readMeterPlanScopeApi.deleteReadMeterPlanScope(ids);
    }

    @ApiOperation(value = "分页查询抄表任务分配信息")
    @PostMapping("/planScope/page")
    public R<Page<ReadMeterPlanScope>> pageReadMeterPlanScope(@RequestBody PageParams<ReadMeterPlanScopePageDTO> params){
        return readMeterPlanScopeApi.page(params);
    }

    @ApiOperation(value = "根据id批量获取抄表任务分配信息")
    @PostMapping("/planScope/queryList")
    public R<List<ReadMeterPlanScope>> listPlanScope(@RequestParam("ids[]") List<Long> ids){
        return readMeterPlanScopeApi.queryList(ids);
    };

    /**
     * 下拉框可通过枚举在实体类中设置，或者手动添加，如下：
     * String[] str = ChargeEnum.getDescStr();
     * ExcelSelectortDTO selectortDTO = new ExcelSelectortDTO(11,11,str);
     * List<ExcelSelectortDTO> list = Lists.newArrayList(selectortDTO);
     * params.setComboboxList(list);
     * @param params
     * @param request
     * @param httpResponse
     * @throws IOException
     */
    @Deprecated
    @ApiOperation(value = "导出抄表计划的抄表名单")
    @PostMapping("/readMeterBook/exportMeterData")
    public void exportMeterData(@RequestBody @Validated PageParams<ReadMeterDataPageDTO> params, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
        // feign文件下载
        Response response = readMeterDataApi.exportCombobox(params);
        String fileName = params.getMap().getOrDefault(NormalExcelConstants.FILE_NAME, "临时文件");
        ExportUtil.exportExcel(response,httpResponse,fileName);
    }

    @ApiOperation(value = "导出抄表计划的抄表名单")
    @PostMapping("/readMeterBook/exportReadMeterList")
    public void exportReadMeterList(@RequestBody List<Long> ids, HttpServletResponse httpResponse) throws IOException {
        List<ReadMeterPlanScope> list = readMeterPlanScopeApi.queryList(ids).getData();
        // feign文件下载
        Response response = readMeterDataApi.exportReadMeterList(list);
        ExportUtil.exportExcel(response,httpResponse,"抄表名单");
    }

    @ApiOperation(value = "分页查询没有绑定的抄表册")
    @PostMapping("/planScope/pageUnboundBook")
    public R<Page<ReadMeterBook>> pageUnboundBook(@RequestBody PageParams<ReadMeterPlan> params){
        return readMeterPlanService.pageUnboundBook(params);
    }

    /*@ApiOperation(value = "导出抄表数据")
    @PostMapping("/readMeterBook/exportData")
    public void export(@RequestBody @Validated PageParams<ReadMeterDataPageDTO> params, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
        // feign文件下载
        Response response = readMeterDataApi.export(params);
        String fileName = params.getMap().getOrDefault(NormalExcelConstants.FILE_NAME, "临时文件");
        ExportUtil.exportExcel(response,httpResponse,fileName);
    }*/
}
