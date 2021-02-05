package com.cdqckj.gmis.readmeter.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cdqckj.gmis.base.DeleteStatusEnum;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.utils.ExecuteStatus;
import com.cdqckj.gmis.readmeter.dto.ReadMeterPlanScopePageDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterPlanScopeSaveDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterPlanScopeUpdateDTO;
import com.cdqckj.gmis.readmeter.entity.ReadMeterData;
import com.cdqckj.gmis.readmeter.entity.ReadMeterPlan;
import com.cdqckj.gmis.readmeter.entity.ReadMeterPlanScope;
import com.cdqckj.gmis.readmeter.service.ReadMeterDataService;
import com.cdqckj.gmis.readmeter.service.ReadMeterPlanScopeService;
import com.cdqckj.gmis.readmeter.service.ReadMeterPlanService;
import com.cdqckj.gmis.security.annotation.PreAuth;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


/**
 * <p>
 * 前端控制器
 * 抄表任务分配
 * </p>
 *
 * @author gmis
 * @date 2020-07-13
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/readMeterPlanScope")
@Api(value = "ReadMeterPlanScope", tags = "抄表任务分配")
@PreAuth(replace = "readMeterPlanScope:")
public class ReadMeterPlanScopeController extends SuperController<ReadMeterPlanScopeService, Long, ReadMeterPlanScope, ReadMeterPlanScopePageDTO, ReadMeterPlanScopeSaveDTO, ReadMeterPlanScopeUpdateDTO> {

    @Autowired
    private ReadMeterDataService readMeterDataService;
    @Autowired
    private ReadMeterPlanService readMeterPlanService;

    @PostMapping(value = "/updateByPlanId")
    public R<List<ReadMeterPlanScope>> updateByPlanId(@RequestParam("planId") Long planId, @RequestParam("status") ExecuteStatus status) {
        ReadMeterPlanScope readMeterPlanScope = new ReadMeterPlanScope();
        readMeterPlanScope.setReadmeterPlanId(planId);
        if(readMeterPlanScope.getDeleteStatus()==null){
            readMeterPlanScope.setDeleteStatus(DeleteStatusEnum.NORMAL.getValue());
        }
        List<ReadMeterPlanScope> list = query(readMeterPlanScope).getData();
        list.forEach(data ->{
            if(data.getTotalReadMeterCount()>0){
                data.setDataStatus(status.getCode());
                getBaseService().updateBatchById(list);
            }
        });
        return R.success(list);
    }

    @PostMapping(value = "/queryByWrap")
    R<List<ReadMeterPlanScope>> queryByWrap(@RequestParam(value = "planIds",required = false) List<Long> list, @RequestParam(value = "bookId",required = false) Long bookId) {
        LambdaQueryWrapper<ReadMeterPlanScope> query = new LambdaQueryWrapper();
        query.eq(ReadMeterPlanScope::getDeleteStatus,0);
        if(list!=null && list.size()>0){
            query.in(ReadMeterPlanScope::getReadmeterPlanId,list);
        }
        if(bookId!=null){
            query.eq(ReadMeterPlanScope::getBookId,bookId);
        }
        return success(getBaseService().list(query));
    }

    @PostMapping(value = "/queryByBookId")
    public R<List<ReadMeterPlanScope>> queryByBookId(@RequestBody List<Long> bookIds) {
        LambdaQueryWrapper<ReadMeterPlanScope> query = new LambdaQueryWrapper();
        query.in(ReadMeterPlanScope::getBookId,bookIds);
        query.eq(ReadMeterPlanScope::getDeleteStatus,0);
        //抄表完成的不返回
        query.ne(ReadMeterPlanScope::getDataStatus,0);
        return success(getBaseService().list(query));
    }

    @PostMapping(value = "/queryByBookIdAndPlan")
    public R<List<ReadMeterPlanScope>> queryByBookIdAndPlan(@RequestParam("bookId") Long id, @RequestParam("planIds") List<Long> planIds) {
        LambdaQueryWrapper<ReadMeterPlanScope> query = new LambdaQueryWrapper();
        query.in(ReadMeterPlanScope::getReadmeterPlanId,planIds);
        query.eq(ReadMeterPlanScope::getBookId,id);
        //抄表完成的不返回
        query.ne(ReadMeterPlanScope::getDataStatus,0);
        return success(getBaseService().list(query));
    }
    
    @PostMapping(value = "/deleteReadMeterPlanScope")
    R<Boolean> deleteReadMeterPlanScope(@RequestBody List<Long> ids){
        try {
            List<ReadMeterPlanScope> deleteList = queryList(ids).getData();
            //没有抄表的才可以删除
            List<Long> deleteIds = deleteList.stream().filter(item-> item.getReadMeterCount()==0).map(ReadMeterPlanScope::getId).collect(Collectors.toList());
            Integer deleteCount = deleteIds.size();
            if(deleteCount == 0){
                return R.fail("只有未开始抄表的抄表册可以移除");
            }
            List<ReadMeterData> li = deleteList.stream().map(dto ->{
                ReadMeterData data = new ReadMeterData();
                data.setPlanId(dto.getReadmeterPlanId());
                data.setBookId(dto.getBookId());
                return data;
            }).collect(Collectors.toList());
            //删除数据
            readMeterDataService.deleteData(li);
            delete(deleteIds);
            int a = 1/0;
            ReadMeterPlan readMeterPlan = readMeterPlanService.getById(deleteList.get(0).getReadmeterPlanId());
            Integer totalCount = readMeterPlan.getTotalReadMeterCount()-deleteCount;
            readMeterPlan.setTotalReadMeterCount(totalCount);
            if(totalCount==0 && readMeterPlan.getDataStatus().equals(ExecuteStatus.EXECUTING.getCode())){
                readMeterPlan.setDataStatus(ExecuteStatus.SUSPEND.getCode());
            }
            readMeterPlanService.updateById(readMeterPlan);
            return R.success();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.fail("");
    }
}
