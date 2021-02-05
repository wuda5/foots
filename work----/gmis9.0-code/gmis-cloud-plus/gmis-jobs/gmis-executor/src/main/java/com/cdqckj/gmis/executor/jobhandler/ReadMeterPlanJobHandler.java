package com.cdqckj.gmis.executor.jobhandler;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.readmeter.entity.ReadMeterBook;
import com.cdqckj.gmis.readmeter.entity.ReadMeterPlan;
import com.cdqckj.gmis.readmeter.entity.ReadMeterPlanScope;
import com.cdqckj.gmis.readmeter.service.ReadMeterBookService;
import com.cdqckj.gmis.readmeter.service.ReadMeterPlanScopeService;
import com.cdqckj.gmis.readmeter.service.ReadMeterPlanService;
import com.cdqckj.gmis.tenant.entity.Tenant;
import com.cdqckj.gmis.tenant.service.TenantService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 抄表计划定时任务
 * 每天凌晨执行
 */
@JobHandler(value = "readMeterPlanJobHandler")
@Component
@Slf4j
public class ReadMeterPlanJobHandler extends IJobHandler {

    @Autowired
    private TenantService tenantService;
    @Autowired
    private ReadMeterPlanService planService;
    @Autowired
    private ReadMeterPlanScopeService planScopeService;
    @Autowired
    private ReadMeterBookService bookService;
    @Autowired
    private ReadMeterPlanScopeService scopeService;

    @Override
    public ReturnT<String> execute2(String param) {
        List<Tenant> tenantList = tenantService.getAllTenant();
        //多租户，过期的计划全部置为完结
        DateTime time = new DateTime();
        tenantList.stream().forEach(dto -> {
            String str = "";
            String code = dto.getCode();
            //切换数据库
            BaseContextHandler.setTenant(code);
            LambdaQueryWrapper<ReadMeterPlan> lambda = new LambdaQueryWrapper<>();
            lambda.lt(ReadMeterPlan::getPlanEndTime,time);
            lambda.ne(ReadMeterPlan::getDataStatus,0);
            List<ReadMeterPlan> list = planService.list(lambda);
            if(list.size()>0){
                LambdaQueryWrapper<ReadMeterPlanScope> lambda1 = new LambdaQueryWrapper<>();
                List<Long> ids = list.stream().map(ReadMeterPlan::getId).collect(Collectors.toList());
                lambda1.in(ReadMeterPlanScope::getReadmeterPlanId,ids);
                List<ReadMeterPlanScope> scopesList = planScopeService.list(lambda1);
                scopesList.stream().forEach(plan -> {
                    plan.setDataStatus(0);
                });
                list.stream().forEach(plan -> {
                    plan.setDataStatus(0);
                });
                Boolean bool = planService.updateBatchById(list);
                if(bool){
                    bool = planScopeService.updateBatchById(scopesList);
                }
                if(bool){
                    bool = renewReadMeterBook(list);
                    str = "成功";
                }else{
                    // todo失败还需要再次处理
                    str = "失败";
                }
            }
            XxlJobLogger.log("抄表计划过期筛查：编号为"+code+"的租户:"+dto.getName(), str);
        });
        return SUCCESS;
    }

    /**
     * 更新抄表册
     * @param list
     */
    public Boolean renewReadMeterBook(List<ReadMeterPlan> list) {
        List<ReadMeterBook> bookList = new ArrayList<>();
        List<Long> planIds = list.stream().map(ReadMeterPlan::getId).collect(Collectors.toList());
        LambdaQueryWrapper<ReadMeterPlanScope> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(ReadMeterPlanScope::getReadmeterPlanId,planIds);
        List<ReadMeterPlanScope> planScopeList = scopeService.list(queryWrapper);
        Map<Long, List<ReadMeterPlanScope>> targetMap = planScopeList.stream().collect(Collectors.groupingBy(ReadMeterPlanScope::getBookId));
        for(Map.Entry<Long, List<ReadMeterPlanScope>> m : targetMap.entrySet()){
            ReadMeterBook book = bookService.getById(m.getKey());
            Integer count = book.getCitedCount()>=m.getValue().size()?book.getCitedCount()-m.getValue().size():0;
            book.setCitedCount(count);
            if(count==0){
                book.setBookStatus(-1);//空闲
            }
            bookList.add(book);
        }
        return bookService.updateBatchById(bookList);
    }

}
