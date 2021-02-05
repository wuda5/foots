package com.cdqckj.gmis.statistics.controller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.charges.api.ChargeRecordBizApi;
import com.cdqckj.gmis.common.domain.Separate.SeparateListData;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.statistics.enums.StsAllChargeItemEnum;
import com.cdqckj.gmis.statistics.service.FeiDayService;
import com.cdqckj.gmis.statistics.vo.FeiDayByPayTypeVo;
import com.cdqckj.gmis.common.domain.sts.StsInfoBaseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * 前端控制器
 * 整个系统的费用的统计历史数据按照天来统计，实时统计。
 * 数据统计的维度到项目和收费员这个最小的维度，每个项目
 * </p>
 *
 * @author gmis
 * @date 2020-11-09
 */
@Slf4j
@Validated
@RestController
@RequestMapping("sts/feiDay")
@Api(value = "FeiDay", tags = "费用的表")
//@PreAuth(replace = "feiDay:")
public class FeiDayController extends BaseController{

    @Autowired
    FeiDayService feiDayService;

    @Autowired
    ChargeRecordBizApi chargeRecordBizApi;

    /**
     * @auth lijianguo
     * @date 2020/11/9 16:44*
     * @remark 收费的统计
     */
    @ApiOperation(value = "柜台日常综合_收费的统计")
    @PostMapping("/chargeFeiByPayType")
    public R<List<StsInfoBaseVo<String,BigDecimal>>> chargeFeiByPayType(@RequestBody StsSearchParam stsSearchParam){

        List<StsInfoBaseVo<String,BigDecimal>> feiByPayTypeVoList;
        if (stsRawDataType()){
            feiByPayTypeVoList = chargeRecordBizApi.stsFeeByChargeMethod(stsSearchParam).getData();
        }else {
            feiByPayTypeVoList = feiDayService.stsNow(stsSearchParam, 1);
        }
        return R.success(feiByPayTypeVoList);
    }

    /**
     * @auth lijianguo
     * @date 2020/11/10 19:23
     * @remark 退费的统计
     */
    @ApiOperation(value = "柜台日常综合_退费的统计")
    @PostMapping("/refundFeiByPayType")
    public R<List<StsInfoBaseVo<String,BigDecimal>>> refundFeiByPayType(@RequestBody StsSearchParam stsSearchParam){

        List<StsInfoBaseVo<String,BigDecimal>> feiByPayTypeVoList;
        stsSearchParam.addSearchKey("refundStatus", "REFUNDED");
        if (stsRawDataType()){
            feiByPayTypeVoList = chargeRecordBizApi.stsFeeByChargeMethod(stsSearchParam).getData();
        }else {
            feiByPayTypeVoList = feiDayService.stsNow(stsSearchParam, 2);
        }
        return R.success(feiByPayTypeVoList);
    }

    /**
     * @auth lijianguo
     * @date 2020/11/20 8:49
     * @remark 售气收费看板-收费的统计
     */
    @ApiOperation(value = "售气收费看板-收费的统计")
    @PostMapping("/panel/chargeFeeByType")
    public R<List<StsInfoBaseVo<String, BigDecimal>>> chargeFeeByType(@RequestBody StsSearchParam stsSearchParam){

        List<StsInfoBaseVo<String, BigDecimal>> chargeSysItemList;
        if (stsRawDataType()){
            chargeSysItemList = chargeRecordBizApi.stsChargeFeeItemType(stsSearchParam).getData();
        }else {
            chargeSysItemList = feiDayService.chargeFeeType(stsSearchParam, 1);
        }
        List<StsInfoBaseVo<String, BigDecimal>> result = new ArrayList<>();
        SeparateListData<StsInfoBaseVo<String, BigDecimal>> sepData = new SeparateListData<>(chargeSysItemList);
        for (StsAllChargeItemEnum e : StsAllChargeItemEnum.values()) {

            StsInfoBaseVo<String, BigDecimal> vo = sepData.getTheDataByKey(e.getCode());
            if (vo == null) {
                vo = new StsInfoBaseVo<>();
                vo.setAmount(BigDecimal.ZERO);
            }
            vo.setType(e.getCode());
            vo.setTypeName(e.getDesc());
            result.add(vo);
        }
        return R.success(result);
    }

    /**
     * @auth lijianguo
     * @date 2020/11/20 8:49
     * @remark 售气收费看板-支付方式统计
     */
    @ApiOperation(value = "售气收费看板-支付方式统计")
    @PostMapping("/panel/chargeFeeByPayType")
    public R<List<FeiDayByPayTypeVo>> chargeFeeByPayType(@RequestBody StsSearchParam stsSearchParam){

        List<StsInfoBaseVo<String, BigDecimal>> chargeFeeList;
        List<StsInfoBaseVo<String, BigDecimal>> refundFeeList;
        if (stsRawDataType()){
            chargeFeeList = chargeRecordBizApi.stsFeeByChargeMethod(stsSearchParam).getData();
            stsSearchParam.addSearchKey("refundStatus", "REFUNDED");
            refundFeeList = chargeRecordBizApi.stsChargeFeeItemType(stsSearchParam).getData();
        }else {
            chargeFeeList = feiDayService.chargeFeeByPayType(stsSearchParam, 1);
            refundFeeList = feiDayService.chargeFeeByPayType(stsSearchParam, 2);
        }

        SeparateListData<FeiDayByPayTypeVo> sepData = new SeparateListData<>(new ArrayList<>());
        produceData(chargeFeeList, sepData, 1);
        produceData(refundFeeList, sepData, 2);

        return R.success(sepData.getAllData());
    }

    /**
     * @auth lijianguo
     * @date 2020/11/20 14:45
     * @remark 生成数据
     */
    private void produceData(List<StsInfoBaseVo<String, BigDecimal>> dataList, SeparateListData<FeiDayByPayTypeVo> sepData, Integer type) {
        for (StsInfoBaseVo<String, BigDecimal> vo: dataList){

            FeiDayByPayTypeVo payTypeVo = sepData.getTheDataByKey(vo.indexKey());
            if (payTypeVo == null){
                payTypeVo = new FeiDayByPayTypeVo();
                payTypeVo.setChargeMethodCode(vo.indexKey());
                sepData.addData(payTypeVo);
            }
            if (type == 1) {
                payTypeVo.setChargeFee(payTypeVo.getChargeFee().add(vo.getAmount()));
            }else {
                payTypeVo.setRefundFee(payTypeVo.getRefundFee().add(vo.getAmount()));
            }
        }
    }

}
