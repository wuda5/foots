package com.cdqckj.gmis.bizcenter.sts.cotroller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.common.domain.search.InitParamUtil;
import com.cdqckj.gmis.statistics.MeterNowApi;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.statistics.vo.StsMeterInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 * 前端控制器
 * 表具的厂家，类型的两个维度
 * </p>
 *
 * @author gmis
 * @date 2020-11-12
 */
@Slf4j
@Validated
@RestController
@RequestMapping("biz/sts/meterNow")
@Api(value = "MeterNow", tags = "表具")
public class MeterNowBizController {

    @Autowired
    MeterNowApi meterNowApi;

    /**
     * @auth lijianguo
     * @date 2020/11/13 10:58
     * @remark 统计表具的信息
     */
    @ApiOperation(value = "表具档案管理—统计数据")
    @PostMapping("/stsInfo")
    public R<StsMeterInfoVo> stsInfo(@RequestBody StsSearchParam stsSearchParam){
        InitParamUtil.setNowDayAndMonth(stsSearchParam);
        StsMeterInfoVo stsInfoVo = meterNowApi.stsInfo(stsSearchParam).getData();
        return R.success(stsInfoVo);
    }

}
