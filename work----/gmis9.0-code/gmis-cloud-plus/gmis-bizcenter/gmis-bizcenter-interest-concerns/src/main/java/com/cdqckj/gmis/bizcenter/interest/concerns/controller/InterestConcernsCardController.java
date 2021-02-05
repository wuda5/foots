package com.cdqckj.gmis.bizcenter.interest.concerns.controller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.card.api.CardInfoBizApi;
import com.cdqckj.gmis.card.entity.ConcernsCardInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author songyz
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/interestConcernsCard")
@Api(value = "interestConcernsCard", tags = "兴趣关注点-卡")
public class InterestConcernsCardController {
    @Resource
    private CardInfoBizApi cardInfoBizApi;

    /**
     * 获取兴趣关注点卡信息
     * @param gasMeterCode
     * @return
     */
    @ApiOperation(value = "获取兴趣关注点卡信息")
    @GetMapping("/getConcernsCardInfo")
    public R<ConcernsCardInfo> getConcernsCardInfo(@RequestParam("gasMeterCode") String gasMeterCode,
                                                   @RequestParam("customerCode") String customerCode
    ){
        return cardInfoBizApi.getConcernsCardInfo(gasMeterCode,customerCode);
    }
}
