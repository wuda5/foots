package com.cdqckj.gmis.operationcenter.readmeter.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.operationcenter.readmeter.dto.AppCustomerMeterPageDTO;
import com.cdqckj.gmis.operationcenter.readmeter.dto.AppReadBookDTO;
import com.cdqckj.gmis.readmeter.GasMeterBookRecordApi;
import com.cdqckj.gmis.readmeter.ReadMeterBookApi;
import com.cdqckj.gmis.readmeter.ReadMeterPlanApi;
import com.cdqckj.gmis.readmeter.dto.GasMeterBookRecordPageDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterDataPageDTO;
import com.cdqckj.gmis.readmeter.entity.GasMeterBookRecord;
import com.cdqckj.gmis.readmeter.entity.ReadMeterBook;
import com.cdqckj.gmis.readmeter.entity.ReadMeterPlan;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/appReadmeter/readBook")
@Api(value = "/appReadmeter/readBook", tags = "app抄表册列表")
public class AppReadBookController {

    @Autowired
    public ReadMeterPlanApi readMeterPlanApi;
    @Autowired
    public ReadMeterBookApi ReadMeterBookApi;
    @Autowired
    public GasMeterBookRecordApi gasMeterBookRecordApi;

    @ApiOperation(value = "查询抄表员对应的抄表册列表", notes = "查询抄表员对应的抄表册列表")
    @PostMapping("/query")
    R<List<ReadMeterBook>> query(@RequestBody AppReadBookDTO data){

        List<ReadMeterBook> re = ReadMeterBookApi.query(new ReadMeterBook().setReadMeterUser(data.getReadMeterUserId())).getData();
        return R.success(re);
    }

    /**
     * 查询抄表册对应关联的抄表客户表具列表--类似含客户表具列表
     * @param params
     * @return
     */
    @ApiOperation(value = "查询抄表册对应关联的抄表客户表具列表，抄表册id必传 ", notes = "查询抄表册对应关联的抄表客户表具列表 抄表册id必传")
    @PostMapping(value = "/page/customerRelate")
    R<Page<GasMeterBookRecord>> page(@RequestBody AppCustomerMeterPageDTO params){
        PageParams<GasMeterBookRecordPageDTO> pa = new PageParams<>();
        pa.setCurrent(params.getCurrent());
        pa.setSize(params.getSize());

        pa.setModel(new GasMeterBookRecordPageDTO().setReadMeterBook(params.getReadMeterBook()));
        return gasMeterBookRecordApi.page(pa);
    }
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
