package com.cdqckj.gmis.readmeter.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.log.annotation.SysLog;
import com.cdqckj.gmis.readmeter.entity.ReadMeterPlan;
import com.cdqckj.gmis.readmeter.dto.ReadMeterPlanSaveDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterPlanUpdateDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterPlanPageDTO;
import com.cdqckj.gmis.readmeter.service.ReadMeterPlanService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cdqckj.gmis.security.annotation.PreAuth;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 * 前端控制器
 * 抄表计划
 * </p>
 *
 * @author gmis
 * @date 2020-07-13
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/readMeterPlan")
@Api(value = "ReadMeterPlan", tags = "抄表计划")
@PreAuth(replace = "readMeterPlan:")
public class ReadMeterPlanController extends SuperController<ReadMeterPlanService, Long, ReadMeterPlan, ReadMeterPlanPageDTO, ReadMeterPlanSaveDTO, ReadMeterPlanUpdateDTO> {


    @ApiOperation(value = "抄表app根据抄表人查询对应的抄表列表", notes = "抄表app 根据抄表人查询对应的抄表列表")
    @PostMapping("/planPageByReadmeterUser")
    @SysLog("抄表app根据抄表人查询对应的抄表列表")
    R<IPage<ReadMeterPlan>> planPageByReadmeterUser(@RequestBody PageParams<Long> params){
        IPage<ReadMeterPlan> r=baseService.planPageByReadmeterUser(params);
        return success(r);
    }
}
