package com.cdqckj.gmis.statistics.controller;

import com.cdqckj.gmis.archive.CustomerBizApi;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.charges.api.ChargeRecordBizApi;
import com.cdqckj.gmis.common.domain.sts.StsInfoBaseVo;
import com.cdqckj.gmis.common.utils.LocalDateUtil;
import com.cdqckj.gmis.security.annotation.PreAuth;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.statistics.service.CustomerNowService;
import com.cdqckj.gmis.statistics.service.FeiDayService;
import com.cdqckj.gmis.statistics.service.GasFeiNowService;
import com.cdqckj.gmis.statistics.vo.GasFeiNowTypeVo;
import com.cdqckj.gmis.statistics.vo.StsFinanceVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: lijianguo
 * @time: 2020/12/26 10:22
 * @remark: 业绩看板
 */
@Slf4j
@Validated
@RestController
@RequestMapping("sts/performancePanel")
@Api(value = "PerformancePanel", tags = "业绩看板")
@PreAuth(replace = "PerformancePanel:")
public class PerformPanelController extends BaseController {

    @Autowired
    CustomerNowService customerNowService;

    @Autowired
    GasFeiNowService gasFeiNowService;

    @Autowired
    FeiDayService feiDayService;

    @Autowired
    CustomerBizApi customerBizApi;

    @Autowired
    ChargeRecordBizApi chargeRecordBizApi;

    /**
     * @Author: lijiangguo
     * @Date: 2020/12/3 18:28
     * @remark 业绩看板-财务数据统计
     */
    @ApiOperation(value = "业绩看板-财务数据统计")
    @PostMapping("/stsFinance")
    public R<StsFinanceVo> stsFinance(@RequestBody StsSearchParam stsSearchParam){

        List<LocalDate> splitMonth = monthSplit(stsSearchParam);
        StsFinanceVo stsFinanceVo = new StsFinanceVo();
        // 统计客户
        StsFinanceVo.StsMeasures cusMeasures = getCustomMeasures(stsSearchParam);
        stsFinanceVo.setCustomer(cusMeasures);
        List<StsFinanceVo.StsMonthCompare<Long>> cusCompareList =  getCustomMonthNum(stsSearchParam, splitMonth);
        stsFinanceVo.getCustomer().setCompareData(cusCompareList);
        // 统计气量
//        StsFinanceVo.StsMeasures gasMeasures = getGasMeasure(stsSearchParam);
//        stsFinanceVo.setGasAmount(gasMeasures);
//        List<StsFinanceVo.StsMonthCompare> gasCompare = getGasMonthCompare(stsSearchParam, splitMonth);
//        stsFinanceVo.getGasAmount().setCompareData(gasCompare);
        // 统计总费用
        StsFinanceVo.StsMeasures<BigDecimal> feeMeasures = getFeiMeasure(stsSearchParam);
        stsFinanceVo.setGasFei(feeMeasures);
        List<StsFinanceVo.StsMonthCompare<BigDecimal>> feeCompare = getFeeMonthCompare(stsSearchParam, splitMonth);
        stsFinanceVo.getGasFei().setCompareData(feeCompare);
        return R.success(stsFinanceVo);
    }

    /**
     * @Author: lijiangguo
     * @Date: 2020/12/3 20:16
     * @remark 统计客户的数据
     */
    private StsFinanceVo.StsMeasures<Long> getCustomMeasures(StsSearchParam stsSearchParam) {

        LocalDate now = LocalDate.now();
        stsSearchParam.setStartDay(now);
        stsSearchParam.setEndDay(LocalDateUtil.nextDay(now));
        Long cusTodayAdd = stsCustomNumBase(stsSearchParam);

        stsSearchParam.setStartDay(LocalDateUtil.monthBegin(now));
        stsSearchParam.setEndDay(LocalDateUtil.nextMonthBegin(now));
        Long cusMonthAdd = stsCustomNumBase(stsSearchParam);

        stsSearchParam.setStartDay(LocalDateUtil.yearBegin(now));
        stsSearchParam.setEndDay(LocalDateUtil.nextYearBegin(now));
        Long cusYearAdd = stsCustomNumBase(stsSearchParam);

        stsSearchParam.setStartDay(null);
        stsSearchParam.setEndDay(null);
        Long cusTotalAdd = stsCustomNumBase(stsSearchParam);

        StsFinanceVo.StsMeasures<Long> cusMeasures = new StsFinanceVo.StsMeasures();
        cusMeasures.setToday(cusTodayAdd);
        cusMeasures.setMonth(cusMonthAdd);
        cusMeasures.setYear(cusYearAdd);
        cusMeasures.setTotal(cusTotalAdd);
        return cusMeasures;
    }

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/20 15:42
    * @remark 统计客户的数据
    */
    private Long stsCustomNumBase(StsSearchParam stsSearchParam){

        Long num = 0L;
        if (stsRawDataType()){
            List<StsInfoBaseVo<Integer, Long>> dataList = customerBizApi.stsByCustomerStatus(stsSearchParam).getData();
            for (StsInfoBaseVo<Integer, Long> data: dataList){
                num += data.getAmount();
            }
        }else {
            num = customerNowService.stsCustomBlackNumNotNull(stsSearchParam).getAmount();
        }
        return num;
    }

    /**
     * @Author: lijiangguo
     * @Date: 2020/12/3 20:53
     * @remark 统计gas费用
     */
    private StsFinanceVo.StsMeasures getGasMeasure(StsSearchParam stsSearchParam) {

        StsFinanceVo.StsMeasures gasMeasures  = new StsFinanceVo.StsMeasures();
        LocalDate now = LocalDate.now();
        stsSearchParam.setStartDay(now);
        stsSearchParam.setEndDay(LocalDateUtil.nextDay(now));
        GasFeiNowTypeVo gasToday = gasFeiNowService.stsGasFei(stsSearchParam);

        stsSearchParam.setStartDay(LocalDateUtil.monthBegin(now));
        stsSearchParam.setEndDay(LocalDateUtil.nextMonthBegin(now));
        GasFeiNowTypeVo gasMonth = gasFeiNowService.stsGasFei(stsSearchParam);

        stsSearchParam.setStartDay(LocalDateUtil.yearBegin(now));
        stsSearchParam.setEndDay(LocalDateUtil.nextYearBegin(now));
        GasFeiNowTypeVo gasYear = gasFeiNowService.stsGasFei(stsSearchParam);

        stsSearchParam.setStartDay(null);
        stsSearchParam.setEndDay(null);
        GasFeiNowTypeVo gasTotal = gasFeiNowService.stsGasFei(stsSearchParam);

        gasMeasures.setToday(gasToday.getGasAmount());
        gasMeasures.setMonth(gasMonth.getGasAmount());
        gasMeasures.setYear(gasYear.getGasAmount());
        gasMeasures.setTotal(gasTotal.getGasAmount());
        return gasMeasures;
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/26 10:30
    * @remark 分割为月份的数据
    */
    private List<LocalDate> monthSplit(StsSearchParam stsSearchParam){

        StsSearchParam copyParam = new StsSearchParam(stsSearchParam);
        if (copyParam.getStartDay() == null){
            copyParam.setStartDay(LocalDateUtil.yearBegin(LocalDate.now()));
            copyParam.setEndDay(LocalDateUtil.nextMonthBegin(LocalDate.now()));
        }
        List<LocalDate> splitMonth = LocalDateUtil.splitDateTimeByMonth(copyParam.getStartDay(), copyParam.getEndDay());
        return splitMonth;
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/28 11:41
    * @remark 统计客户的每个月的数据
    */
    private List<StsFinanceVo.StsMonthCompare<Long>> getCustomMonthNum(StsSearchParam stsSearchParam, List<LocalDate> splitMonthList){

        StsSearchParam copyParam = new StsSearchParam(stsSearchParam);
        List<StsFinanceVo.StsMonthCompare<Long>> result = new ArrayList<>();
        for(int i = 1; i < splitMonthList.size(); i++){
            StsFinanceVo.StsMonthCompare<Long> vo = new StsFinanceVo.StsMonthCompare();
            LocalDate start = splitMonthList.get(i - 1);
            LocalDate end = splitMonthList.get(i);
            copyParam.setStartDay(start);
            copyParam.setEndDay(end);
            Long yearNum = stsCustomNumBase(copyParam);
            vo.setMonStr(LocalDateUtil.getMonthNum(start));
            vo.setThisYearNum(yearNum);

            copyParam.setStartDay(LocalDateUtil.yearChangeNum(start, -1L));
            copyParam.setEndDay(LocalDateUtil.yearChangeNum(end, -1L));
            Long lastYearNum = stsCustomNumBase(copyParam);
            vo.setLastYearNum(lastYearNum);
            result.add(vo);
        }
        return result;
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/28 13:48
    * @remark 统计每每个月的气费，气量的对比
    */
    private List<StsFinanceVo.StsMonthCompare> getGasMonthCompare(StsSearchParam stsSearchParam, List<LocalDate> splitMonthList){

        StsSearchParam copyParam = new StsSearchParam(stsSearchParam);
        List<StsFinanceVo.StsMonthCompare> result = new ArrayList<>();
        for(int i = 1; i < splitMonthList.size(); i++) {

            StsFinanceVo.StsMonthCompare gasAmountVo = new StsFinanceVo.StsMonthCompare();
            LocalDate start = splitMonthList.get(i - 1);
            LocalDate end = splitMonthList.get(i);
            copyParam.setStartDay(start);
            copyParam.setEndDay(end);
            GasFeiNowTypeVo gasToday = gasFeiNowService.stsGasFei(copyParam);

            gasAmountVo.setMonStr(LocalDateUtil.getMonthNum(start));
            gasAmountVo.setThisYearNum(gasToday.getGasAmount().longValue());

            copyParam.setStartDay(LocalDateUtil.yearChangeNum(start, -1L));
            copyParam.setEndDay(LocalDateUtil.yearChangeNum(end, -1L));
            GasFeiNowTypeVo gasLastYear = gasFeiNowService.stsGasFei(copyParam);

            gasAmountVo.setLastYearNum(gasLastYear.getGasAmount().longValue());
            result.add(gasAmountVo);
        }
        return result;
    }

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/7 8:58
    * @remark 统计费用
    */
    private StsFinanceVo.StsMeasures<BigDecimal> getFeiMeasure(StsSearchParam stsSearchParam){

        LocalDate now = LocalDate.now();
        stsSearchParam.setStartDay(now);
        stsSearchParam.setEndDay(LocalDateUtil.nextDay(now));
        BigDecimal feeToday = chargeFeiMeasureBase(stsSearchParam);

        stsSearchParam.setStartDay(LocalDateUtil.monthBegin(now));
        stsSearchParam.setEndDay(LocalDateUtil.nextMonthBegin(now));
        BigDecimal feeMonth = chargeFeiMeasureBase(stsSearchParam);

        stsSearchParam.setStartDay(LocalDateUtil.yearBegin(now));
        stsSearchParam.setEndDay(LocalDateUtil.nextYearBegin(now));
        BigDecimal feeYear = chargeFeiMeasureBase(stsSearchParam);

        stsSearchParam.setStartDay(null);
        stsSearchParam.setEndDay(null);
        BigDecimal feeTotal = chargeFeiMeasureBase(stsSearchParam);

        StsFinanceVo.StsMeasures<BigDecimal> feiMeasures  = new StsFinanceVo.StsMeasures();
        feiMeasures.setToday(feeToday);
        feiMeasures.setMonth(feeMonth);
        feiMeasures.setYear(feeYear);
        feiMeasures.setTotal(feeTotal);
        return feiMeasures;
    }

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/20 16:11
    * @remark 统计费用
    */
    private BigDecimal chargeFeiMeasureBase(StsSearchParam stsSearchParam){
        BigDecimal num = BigDecimal.ZERO;
        if (stsRawDataType()){
            List<StsInfoBaseVo<String, BigDecimal>> dataList = chargeRecordBizApi.stsFeeByChargeMethod(stsSearchParam).getData();
            for (StsInfoBaseVo<String, BigDecimal> data: dataList){
                num = num.add(data.getAmount());
            }
        }else {
            num = feiDayService.changeFeeTotal(stsSearchParam);
        }
        return num;
    }

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/7 9:22
    * @remark 每个月的费用的统计
    */
    private List<StsFinanceVo.StsMonthCompare<BigDecimal>> getFeeMonthCompare(StsSearchParam stsSearchParam, List<LocalDate> splitMonthList){
        StsSearchParam copyParam = new StsSearchParam(stsSearchParam);
        List<StsFinanceVo.StsMonthCompare<BigDecimal>> result = new ArrayList<>();
        for(int i = 1; i < splitMonthList.size(); i++) {

            StsFinanceVo.StsMonthCompare feeMonthCompare = new StsFinanceVo.StsMonthCompare();
            LocalDate start = splitMonthList.get(i - 1);
            LocalDate end = splitMonthList.get(i);
            copyParam.setStartDay(start);
            copyParam.setEndDay(end);
            BigDecimal feeToday = chargeFeiMeasureBase(copyParam);

            feeMonthCompare.setMonStr(LocalDateUtil.getMonthNum(start));
            feeMonthCompare.setThisYearNum(feeToday);

            copyParam.setStartDay(LocalDateUtil.yearChangeNum(start, -1L));
            copyParam.setEndDay(LocalDateUtil.yearChangeNum(end, -1L));
            BigDecimal feeLastYear = chargeFeiMeasureBase(copyParam);

            feeMonthCompare.setLastYearNum(feeLastYear);
            result.add(feeMonthCompare);
        }
        return result;
    }

}
