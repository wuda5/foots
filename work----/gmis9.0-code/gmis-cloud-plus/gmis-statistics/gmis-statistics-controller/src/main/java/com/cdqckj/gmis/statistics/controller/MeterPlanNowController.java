package com.cdqckj.gmis.statistics.controller;

import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.statistics.service.MeterPlanNowService;

import java.util.List;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.statistics.vo.MeterPlanNowStsVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.cdqckj.gmis.security.annotation.PreAuth;


/**
 * <p>
 * 前端控制器
 * 对每一个抄表计划统计
 * </p>
 *
 * @author gmis
 * @date 2020-11-04
 */
@Slf4j
@Validated
@RestController
@RequestMapping("sts/meterPlanNow")
@Api(value = "MeterPlanNow", tags = "对每一个抄表计划统计")
@PreAuth(replace = "meterPlanNow:")
public class MeterPlanNowController {

    @Autowired
    MeterPlanNowService meterPlanNowService;

    /**
     * @auth lijianguo
     * @date 2020/11/12 15:26
     * @remark 用户抄表的计划统计
     */
    @ApiOperation(value = "用户抄表的计划统计")
    @GetMapping("/stsUserPlan")
    public R<List<MeterPlanNowStsVo>> stsUserPlan(Long uId){

        List<MeterPlanNowStsVo> meterPlanNowStsVoList = meterPlanNowService.stsUserPlan(uId, 1);
        return R.success(meterPlanNowStsVoList);
    }

    /**
     * @auth lijianguo
     * @date 2020/11/12 15:26
     * @remark 抄表业务管理-普表抄表-抄表统计
     */
    @ApiOperation(value = "抄表业务管理-普表抄表-抄表统计")
    @PostMapping("/generalGasMeter")
    public R<List<MeterPlanNowStsVo>> generalGasMeter(@RequestBody StsSearchParam stsSearchParam){

        List<MeterPlanNowStsVo> meterPlanNowStsVoList = meterPlanNowService.generalGasMeter(stsSearchParam);
        return R.success(meterPlanNowStsVoList);
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/19 13:52
    * @remark 物联网抄表的统计i
    */
    @ApiOperation(value = "抄表业务管理- 物联网抄表-抄表统计")
    @PostMapping("/internetGasMeter")
    public R<List<MeterPlanNowStsVo>> internetGasMeter(@RequestBody StsSearchParam stsSearchParam){

        List<MeterPlanNowStsVo> meterPlanNowStsVoList = meterPlanNowService.generalGasMeter(stsSearchParam);
        return R.success(meterPlanNowStsVoList);
    }



}
