package com.cdqckj.gmis.bizcenter.interest.concerns.controller;

import com.cdqckj.gmis.archive.GasMeterBizApi;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author songyz
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/interestConcernsGasMeter")
@Api(value = "interestConcernsGasMeter", tags = "兴趣关注点-表具")
public class InterestConcernsGasMeterController {

    @Resource
    private GasMeterBizApi gasMeterBizApi;

    @ApiOperation(value = "查询表具信息")
    @GetMapping("/findGasMeterByCode")
    R<GasMeter> findGasMeterByCode(@RequestParam(value = "gascode")  String gascode){
        return gasMeterBizApi.findGasMeterByCode(gascode);
    }
}
