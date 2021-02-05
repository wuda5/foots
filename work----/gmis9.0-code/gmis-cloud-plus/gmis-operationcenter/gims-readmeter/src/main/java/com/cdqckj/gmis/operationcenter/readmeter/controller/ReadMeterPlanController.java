package com.cdqckj.gmis.operationcenter.readmeter.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.readmeter.ReadMeterPlanApi;
import com.cdqckj.gmis.readmeter.entity.ReadMeterPlan;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 * 抄表计划前端控制器
 * </p>
 * @author gmis
 * @date 2020-07-01
 */
//@Slf4j
//@Validated
//@RestController
//@RequestMapping("/appReadmeter/readMeterPlan")
//@Api(value = "readMeterData", tags = "app抄表计划")
public class ReadMeterPlanController {

//    @Autowired
//    public ReadMeterPlanApi readMeterPlanApi;
//
//    /**
//     * @param params
//     * @return
//     */
//    @PostMapping(value = "/page")
//    public  R<Page<ReadMeterPlan>> planPageByReadmeterUser(@RequestBody PageParams<Long> params){
////
////        PageParams<ReadMeterPlanScopePageDTO> scopeParams =new PageParams<ReadMeterPlanScopePageDTO>();
////        scopeParams.setSize(params.getSize());
////        scopeParams.setCurrent(params.getCurrent());
////        scopeParams.setModel(new ReadMeterPlanScopePageDTO().setReadMeterUser(params.getModel()));
////
////        R<Page<ReadMeterPlanScope>> re = readMeterPlanScopeApi.page(scopeParams);
////        List<Long> planList = re.getData().getRecords().stream().map(ReadMeterPlanScope::getReadmeterPlanId).collect(Collectors.toList());
////
////        List<ReadMeterPlan> plans = readMeterPlanApi.queryList(planList).getData();
////
////        //
////
////        new Page<ReadMeterPlan>().setRecords(plans).set
////        R<Page<ReadMeterPlan>> pageR = new R<Page<ReadMeterPlan>>();
//
//        // 或者单独写到一个api 中 关联一次查出 readMeterPlanApi
//        R<Page<ReadMeterPlan>> re= readMeterPlanApi.planPageByReadmeterUser(params);
//        return re;
//    }


}
