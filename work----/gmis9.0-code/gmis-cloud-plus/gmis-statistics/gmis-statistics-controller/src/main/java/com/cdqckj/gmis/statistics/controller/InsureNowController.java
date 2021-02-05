package com.cdqckj.gmis.statistics.controller;

import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.statistics.service.CustomerNowService;
import com.cdqckj.gmis.statistics.service.InsureNowService;

import java.math.BigInteger;
import java.util.List;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.statistics.vo.InsureNowTypeStsVo;
import com.cdqckj.gmis.common.domain.sts.StsInfoBaseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cdqckj.gmis.security.annotation.PreAuth;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 * 前端控制器
 * 保险的统计信息
 * </p>
 *
 * @author gmis
 * @date 2020-11-07
 */
@Slf4j
@Validated
@RestController
@RequestMapping("sts/insureNow")
@Api(value = "InsureNow", tags = "保险的统计信息")
@PreAuth(replace = "insureNow:")
public class InsureNowController{

    @Autowired
    InsureNowService insureNowService;

    @Autowired
    CustomerNowService customerNowService;

    /**
     * @auth lijianguo
     * @date 2020/11/17 14:41
     * @remark 保险购买分类统计
     */
    @ApiOperation(value = "客户信息看板-保险购买分类统计")
    @PostMapping("/panel/insureNowByType")
    public R<InsureNowTypeStsVo> insureNowByType(@RequestBody StsSearchParam stsSearchParam){

        List<StsInfoBaseVo<Integer,Long>> insureList = insureNowService.insureNowByType(stsSearchParam);
        List<StsInfoBaseVo<String, Long>> customList = customerNowService.stsCustomTypeWithCondition(stsSearchParam);

        InsureNowTypeStsVo stsVo = new InsureNowTypeStsVo();
        for (StsInfoBaseVo<Integer, Long> insure: insureList){
            // 新增
            if (insure.getType() == 1){
                Integer value = 0;
                if (insure.getAmount() != null){
                    value = insure.getAmount().intValue();
                }
                stsVo.setNewBuy(value);
            // 续保
            }else if(insure.getType() == 2){
                Integer value = 0;
                if (insure.getAmount() != null){
                    value = insure.getAmount().intValue();
                }
                stsVo.setReBuy(value);
            }else {

            }
        }
        Integer sum = customList.stream().mapToInt(s->s.getAmount().intValue()).sum();
        Integer noBuy = sum - stsVo.getNewBuy() - stsVo.getReBuy();
        if (noBuy < 0){
            noBuy = 0;
        }
        stsVo.setNoBuy(noBuy);
        return R.success(stsVo);
    }


}
