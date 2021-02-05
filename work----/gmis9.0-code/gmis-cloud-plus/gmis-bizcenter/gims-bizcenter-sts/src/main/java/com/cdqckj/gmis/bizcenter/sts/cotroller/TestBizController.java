package com.cdqckj.gmis.bizcenter.sts.cotroller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.calculate.api.CalculateApi;
import com.cdqckj.gmis.common.domain.code.CodeNotLost;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @author: lijianguo
 * @time: 2020/12/08 11:57
 * @remark: 请添加类说明
 */
@RestController
@RequestMapping("sts/test")
@Api(value = "MeterPlanNow", tags = "测试")
public class TestBizController {

    @Autowired
    CalculateApi calculateApi;

    @GetMapping("/test1")
    @ApiOperation("test1")
    R<BigDecimal> test1(){
        calculateApi.test1();
        return R.success(null);
    }

    @GetMapping("/test2")
    @ApiOperation("test2")
    @CodeNotLost
    R test2(){
        calculateApi.test2();
        return R.success();
    }
}
