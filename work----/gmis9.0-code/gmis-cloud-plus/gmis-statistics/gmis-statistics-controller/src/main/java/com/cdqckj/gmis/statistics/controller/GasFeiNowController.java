package com.cdqckj.gmis.statistics.controller;

import com.cdqckj.gmis.charges.api.ChargeItemRecordBizApi;
import com.cdqckj.gmis.charges.api.ChargeRecordBizApi;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.domain.sts.StsInfoBasePlusVo;
import com.cdqckj.gmis.statistics.service.GasFeiNowService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.statistics.vo.GasFeiNowTypeVo;
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
 * 燃气费
 * </p>
 *
 * @author gmis
 * @date 2020-11-19
 */
@Slf4j
@Validated
@RestController
@RequestMapping("sts/gasFeiNow")
@Api(value = "GasFeiNow", tags = "燃气费")
@PreAuth(replace = "gasFeiNow:")
public class GasFeiNowController extends BaseController{

    @Autowired
    GasFeiNowService gasFeiNowService;

    @Autowired
    ChargeItemRecordBizApi chargeItemRecordBizApi;


    /**
     * @auth lijianguo
     * @date 2020/11/17 14:41
     * @remark 保险购买分类统计
     */
    @ApiOperation(value = "售气收费看板-用气类型统计")
    @PostMapping("/panel/gasFeiType")
    public R<List<GasFeiNowTypeVo>> gasFeiType(@RequestBody StsSearchParam stsSearchParam){

        List<GasFeiNowTypeVo> feiNowTypeVos;
        if (stsRawDataType()){
            feiNowTypeVos = new ArrayList<>();
            List<StsInfoBasePlusVo<String, Long>> oriDataList = chargeItemRecordBizApi.stsGasFeeAndType(stsSearchParam).getData();
            for (StsInfoBasePlusVo<String, Long> data : oriDataList){
                GasFeiNowTypeVo vo = new GasFeiNowTypeVo();
                vo.setType(data.getType());
                vo.setFeiAmount(BigDecimal.valueOf(data.getAmount1()));
                vo.setGasAmount(BigDecimal.valueOf(data.getAmount2()));
                feiNowTypeVos.add(vo);
            }
        }else {
            feiNowTypeVos = gasFeiNowService.gasFeiType(stsSearchParam);
        }
        return R.success(feiNowTypeVos);
    }


}
