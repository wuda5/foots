package com.cdqckj.gmis.bizcenter.sts.cotroller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.charges.api.ChargeRecordBizApi;
import com.cdqckj.gmis.charges.api.GasmeterArrearsDetailBizApi;
import com.cdqckj.gmis.charges.vo.StsCounterStaffVo;
import com.cdqckj.gmis.common.utils.BigDecimalUtils;
import com.cdqckj.gmis.common.utils.LocalDateUtil;
import com.cdqckj.gmis.readmeter.ReadMeterDataApi;
import com.cdqckj.gmis.security.annotation.PreAuth;
import com.cdqckj.gmis.statistics.PerformPanelApi;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.statistics.domain.log.util.DateStsUtil;
import com.cdqckj.gmis.statistics.vo.MeterPlanNowStsVo;
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
@RequestMapping("biz/sts/performancePanel")
@Api(value = "PerformancePanel", tags = "业绩看板")
@PreAuth(replace = "PerformancePanel:")
public class PerformPanelController {

    @Autowired
    PerformPanelApi performPanelApi;

    @Autowired
    ChargeRecordBizApi chargeRecordBizApi;

    @Autowired
    ReadMeterDataApi readMeterDataApi;

    @Autowired
    GasmeterArrearsDetailBizApi gasmeterArrearsDetailBizApi;

    /**
     * @Author: lijiangguo
     * @Date: 2020/12/3 18:28
     * @remark 业绩看板-财务数据统计
     */
    @ApiOperation(value = "业绩看板-财务数据统计")
    @PostMapping("/stsAllFinance")
    public R<StsFinanceVo> stsFinance(@RequestBody StsSearchParam stsSearchParam) {
        if (stsSearchParam.getEndDay() != null){
            stsSearchParam.setEndDay(LocalDateUtil.nextMonthBegin(stsSearchParam.getEndDay()));
        }
        // 客户 售气 收费
        StsFinanceVo vo = performPanelApi.stsFinance(stsSearchParam).getData();
        StsFinanceVo.StsMeasures<BigDecimal> gasFei = vo.getGasFei();
        gasFei.setToday(BigDecimalUtils.left2(gasFei.getToday()));
        gasFei.setMonth(BigDecimalUtils.left2(gasFei.getMonth()));
        gasFei.setYear(BigDecimalUtils.left2(gasFei.getYear()));
        gasFei.setTotal(BigDecimalUtils.left2(gasFei.getTotal()));
        // 统计欠费
        List<LocalDate> monthSplitList = monthSplit(stsSearchParam);
        StsFinanceVo.StsMeasures feeMeasures = getArrearsFee(stsSearchParam);
        vo.setOverdraftFei(feeMeasures);
        List<StsFinanceVo.StsMonthCompare<BigDecimal>> feiCompareList =  getArrearsMonthNum(stsSearchParam, monthSplitList);
        vo.getOverdraftFei().setCompareData(feiCompareList);
        return R.success(vo);
    }

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/7 11:03
    * @remark 统计欠费
    */
    private StsFinanceVo.StsMeasures getArrearsFee(StsSearchParam stsSearchParam){

        LocalDate now = LocalDate.now();
        stsSearchParam.setStartDay(now);
        stsSearchParam.setEndDay(LocalDateUtil.nextDay(now));
        BigDecimal feeDay = gasmeterArrearsDetailBizApi.stsArrearsFee(stsSearchParam).getData();

        stsSearchParam.setStartDay(LocalDateUtil.monthBegin(now));
        stsSearchParam.setEndDay(LocalDateUtil.nextMonthBegin(now));
        BigDecimal feeMonth = gasmeterArrearsDetailBizApi.stsArrearsFee(stsSearchParam).getData();

        stsSearchParam.setStartDay(LocalDateUtil.yearBegin(now));
        stsSearchParam.setEndDay(LocalDateUtil.nextYearBegin(now));
        BigDecimal feeYear = gasmeterArrearsDetailBizApi.stsArrearsFee(stsSearchParam).getData();

        stsSearchParam.setStartDay(null);
        stsSearchParam.setEndDay(null);
        BigDecimal feeAll = gasmeterArrearsDetailBizApi.stsArrearsFee(stsSearchParam).getData();

        StsFinanceVo.StsMeasures<BigDecimal> feeMeasures = new StsFinanceVo.StsMeasures();
        feeMeasures.setToday(BigDecimalUtils.left2(feeDay));
        feeMeasures.setMonth(BigDecimalUtils.left2(feeMonth));
        feeMeasures.setYear(BigDecimalUtils.left2(feeYear));
        feeMeasures.setTotal(BigDecimalUtils.left2(feeAll));
        return feeMeasures;
    }

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/7 11:51
    * @remark 统计月份的数据
    */
    private List<StsFinanceVo.StsMonthCompare<BigDecimal>> getArrearsMonthNum(StsSearchParam stsSearchParam, List<LocalDate> splitMonthList){

        StsSearchParam copyParam = new StsSearchParam(stsSearchParam);
        List<StsFinanceVo.StsMonthCompare<BigDecimal>> result = new ArrayList<>();
        for(int i = 1; i < splitMonthList.size(); i++){
            StsFinanceVo.StsMonthCompare<BigDecimal> vo = new StsFinanceVo.StsMonthCompare();
            LocalDate start = splitMonthList.get(i - 1);
            LocalDate end = splitMonthList.get(i);
            copyParam.setStartDay(start);
            copyParam.setEndDay(end);
            BigDecimal yearNum = gasmeterArrearsDetailBizApi.stsArrearsFee(copyParam).getData();
            vo.setMonStr(LocalDateUtil.getMonthNum(start));
            vo.setThisYearNum(BigDecimalUtils.left2(yearNum));

            copyParam.setStartDay(LocalDateUtil.yearChangeNum(start, -1L));
            copyParam.setEndDay(LocalDateUtil.yearChangeNum(end, -1L));
            BigDecimal lastYearNum = gasmeterArrearsDetailBizApi.stsArrearsFee(copyParam).getData();
            vo.setLastYearNum(BigDecimalUtils.left2(lastYearNum));
            result.add(vo);
        }
        return result;
    }

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/7 10:47
    * @remark 请添加函数说明
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
    * @Date: 2020/12/29 9:04
    * @remark 业绩看板-柜台人员统计
    */
    @ApiOperation(value = "业绩看板-柜台人员统计")
    @PostMapping("/counterStaff")
    public R<Page<StsCounterStaffVo>> counterStaff(@RequestBody StsSearchParam stsSearchParam) {
        if (stsSearchParam.getEndDay() != null){
            stsSearchParam.setEndDay(LocalDateUtil.nextDay(stsSearchParam.getEndDay()));
        }
        if(stsSearchParam.getStartDay() == null) {
            stsSearchParam.setStartDay(LocalDateUtil.yearBegin(LocalDate.now()));
        }
        Page<StsCounterStaffVo> page =  chargeRecordBizApi.counterStaff(stsSearchParam).getData();
        return R.success(page);
    }

    @ApiOperation(value = "业绩看板-抄表完成统计")
    @PostMapping("/stsReadMeter")
    public R<Page<MeterPlanNowStsVo>> stsReadMeter(@RequestBody StsSearchParam stsSearchParam) {
        if (stsSearchParam.getStsDay() == null){
            stsSearchParam.setStsDay(LocalDate.now());
        }
        if(stsSearchParam.getStartDay() == null) {
            stsSearchParam.setStartDay(LocalDateUtil.yearBegin(LocalDate.now()));
        }
        return readMeterDataApi.stsReadMeter(stsSearchParam);
    }
}

