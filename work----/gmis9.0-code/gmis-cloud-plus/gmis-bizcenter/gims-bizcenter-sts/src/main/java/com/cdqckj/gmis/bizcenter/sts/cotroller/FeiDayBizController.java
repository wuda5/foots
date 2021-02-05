package com.cdqckj.gmis.bizcenter.sts.cotroller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.charges.enums.ChargePayMethodEnum;
import com.cdqckj.gmis.common.domain.Separate.SeparateListData;
import com.cdqckj.gmis.common.domain.search.InitParamUtil;
import com.cdqckj.gmis.common.utils.LocalDateUtil;
import com.cdqckj.gmis.statistics.FeiDayApi;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.statistics.enums.StsAllChargeItemEnum;
import com.cdqckj.gmis.statistics.vo.FeiDayByPayTypeVo;
import com.cdqckj.gmis.statistics.vo.PanelTimeGroupVo;
import com.cdqckj.gmis.common.domain.sts.StsInfoBaseVo;
import com.cdqckj.gmis.statistics.vo.StsTypeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
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
@RequestMapping("biz/sts/feiDay")
@Api(value = "FeiDay", tags = "费用")
public class FeiDayBizController {

    @Autowired
    FeiDayApi feiDayApi;

    /**
     * @auth lijianguo
     * @date 2020/11/11 14:26
     * @remark 柜台日常综合_收费统计
     */
    @ApiOperation(value = "柜台日常综合_收费统计")
    @PostMapping("/chargeFeiByPayType")
    public R chargeFeiByPayType(@RequestBody StsSearchParam stsSearchParam){
        InitParamUtil.setNowDayAndMonth(stsSearchParam);
        List<StsInfoBaseVo> feiByPayTypeVos = feiDayApi.chargeFeiByPayType(stsSearchParam).getData();
        List<StsInfoBaseVo> allPayInfoList = getFeiByPayTypeVos(feiByPayTypeVos);
        return R.success(allPayInfoList);
    }

    /**
     * @auth lijianguo
     * @date 2020/11/11 14:26
     * @remark 柜台日常综合_退费的统计
     */
    @ApiOperation(value = "柜台日常综合_退费统计")
    @PostMapping("/refundFeiByPayType")
    public R refundFeiByPayType(@RequestBody StsSearchParam stsSearchParam){
        InitParamUtil.setNowDayAndMonth(stsSearchParam);
        List<StsInfoBaseVo> feiByPayTypeVos = feiDayApi.refundFeiByPayType(stsSearchParam).getData();
        List<StsInfoBaseVo> allPayInfoList = getFeiByPayTypeVos(feiByPayTypeVos);
        return R.success(allPayInfoList);
    }

    /**
     * @auth lijianguo
     * @date 2020/11/11 14:31
     * @remark 重新格式化数据
     */
    private List<StsInfoBaseVo> getFeiByPayTypeVos(List<StsInfoBaseVo> feiByPayTypeVos) {
        SeparateListData<StsInfoBaseVo> sepPayType = new SeparateListData<>(feiByPayTypeVos);

        List<StsInfoBaseVo> allPayInfoList = new ArrayList<>(ChargePayMethodEnum.values().length);
        for (ChargePayMethodEnum e : ChargePayMethodEnum.values()) {
            StsInfoBaseVo vo = sepPayType.getTheDataByKey(e.getCode());
            if (vo == null) {
                vo = new StsInfoBaseVo();
                vo.setAmount(BigDecimal.ZERO);
            }
            vo.setType(e.getCode());
            vo.setTypeName(e.getDesc());
            allPayInfoList.add(vo);
        }
        return allPayInfoList;
    }

    /**
     * @auth lijianguo
     * @date 2020/11/20 8:49
     * @remark 售气收费看板-收费的统计-时间比较
     */
    @ApiOperation(value = "售气收费看板-收费统计-时间比较")
    @PostMapping("/panel/chargeFeeByTypeCompare")
    public R<PanelTimeGroupVo> chargeFeeByTypeCompare(@RequestBody StsSearchParam stsSearchParam){
        if(stsSearchParam.getStartDay() == null) {
            stsSearchParam.setStartDay(LocalDateUtil.yearBegin(LocalDate.now()));
        }
        List<StsInfoBaseVo<String, BigDecimal>> searchData = feiDayApi.chargeFeeByType(stsSearchParam).getData();

        LocalDate now = LocalDate.now();
        stsSearchParam.setStartDay(LocalDateUtil.monthBegin(now));
        stsSearchParam.setEndDay(LocalDateUtil.nextMonthBegin(now));
        List<StsInfoBaseVo<String, BigDecimal>> thisMothData = feiDayApi.chargeFeeByType(stsSearchParam).getData();

        stsSearchParam.setStartDay(LocalDateUtil.monthBeginChangeNum(now, -1L));
        stsSearchParam.setEndDay(LocalDateUtil.monthBeginChangeNum(now, 0L));
        List<StsInfoBaseVo<String, BigDecimal>> lastMothData = feiDayApi.chargeFeeByType(stsSearchParam).getData();

        stsSearchParam.setStartDay(LocalDateUtil.yearChangeNum(LocalDateUtil.monthBegin(now), -1L));
        stsSearchParam.setEndDay(LocalDateUtil.yearChangeNum(LocalDateUtil.nextMonthBegin(now), -1L));
        List<StsInfoBaseVo<String, BigDecimal>> lastYearData = feiDayApi.chargeFeeByType(stsSearchParam).getData();

        PanelTimeGroupVo panelTimeGroupVo = new PanelTimeGroupVo<>();
        panelTimeGroupVo.setSearchDate(searchData);
        panelTimeGroupVo.setNowDate(thisMothData);
        panelTimeGroupVo.setLastMonthDate(lastMothData);
        panelTimeGroupVo.setLastYearDate(lastYearData);

        for (StsAllChargeItemEnum e: StsAllChargeItemEnum.values()){
            StsTypeVo typeVo = new StsTypeVo(e.getCode(), e.getDesc());
            panelTimeGroupVo.addStsType(typeVo);
        }
        return R.success(panelTimeGroupVo);
    }

    /**
     * @auth lijianguo
     * @date 2020/11/20 8:49
     * @remark 售气收费看板-支付方式统计-时间比较
     */
    @ApiOperation(value = "售气收费看板-支付方式统计-时间比较")
    @PostMapping("/panel/chargeFeeByPayTypeCompare")
    public R<PanelTimeGroupVo> chargeFeeByPayTypeCompare(@RequestBody StsSearchParam stsSearchParam){
        if(stsSearchParam.getStartDay() == null) {
            stsSearchParam.setStartDay(LocalDateUtil.yearBegin(LocalDate.now()));
        }
        List<FeiDayByPayTypeVo> searchData = chargeFeeByPayType(stsSearchParam);

        LocalDate now = LocalDate.now();
        stsSearchParam.setStartDay(LocalDateUtil.monthBegin(now));
        stsSearchParam.setEndDay(LocalDateUtil.nextMonthBegin(now));
        List<FeiDayByPayTypeVo> thisMothData = chargeFeeByPayType(stsSearchParam);

        stsSearchParam.setStartDay(LocalDateUtil.monthBeginChangeNum(now, -1L));
        stsSearchParam.setEndDay(LocalDateUtil.monthBeginChangeNum(now, 0L));
        List<FeiDayByPayTypeVo> lastMothData = chargeFeeByPayType(stsSearchParam);

        stsSearchParam.setStartDay(LocalDateUtil.yearChangeNum(LocalDateUtil.monthBegin(now), -1L));
        stsSearchParam.setEndDay(LocalDateUtil.yearChangeNum(LocalDateUtil.nextMonthBegin(now), -1L));
        List<FeiDayByPayTypeVo> lastYearData = chargeFeeByPayType(stsSearchParam);

        PanelTimeGroupVo panelTimeGroupVo = new PanelTimeGroupVo<>();
        panelTimeGroupVo.setSearchDate(searchData);
        panelTimeGroupVo.setNowDate(thisMothData);
        panelTimeGroupVo.setLastMonthDate(lastMothData);
        panelTimeGroupVo.setLastYearDate(lastYearData);

        for (ChargePayMethodEnum e: ChargePayMethodEnum.values()){
            StsTypeVo typeVo = new StsTypeVo(e.getCode(), e.getDesc());
            panelTimeGroupVo.addStsType(typeVo);
        }
        return R.success(panelTimeGroupVo);
    }

    /**
     * @Author: lijiangguo
     * @Date: 2021/1/14 13:20
     * @remark 售气收费看板-支付方式统计
     */
    List<FeiDayByPayTypeVo> chargeFeeByPayType(StsSearchParam stsSearchParam){

        List<FeiDayByPayTypeVo> dataList = feiDayApi.chargeFeeByPayType(stsSearchParam).getData();
        SeparateListData<FeiDayByPayTypeVo> sepData = new SeparateListData<>(dataList);

        List<FeiDayByPayTypeVo> resultList = new ArrayList<>();
        for (ChargePayMethodEnum e: ChargePayMethodEnum.values()){

            FeiDayByPayTypeVo vo = sepData.getTheDataByKey(e.getCode());
            if (vo == null){
                vo = new FeiDayByPayTypeVo();
            }
            vo.setChargeMethodCode(e.getCode());
            vo.setChargeMethodName(e.getDesc());
            resultList.add(vo);
        }
        return resultList;
    }
}
