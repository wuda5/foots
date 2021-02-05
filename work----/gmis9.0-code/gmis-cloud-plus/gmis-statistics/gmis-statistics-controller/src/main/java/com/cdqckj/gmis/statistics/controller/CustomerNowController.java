package com.cdqckj.gmis.statistics.controller;

import com.cdqckj.gmis.archive.CustomerBizApi;
import com.cdqckj.gmis.archive.MeterAccountCancelBizApi;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.biztemporary.OpenAccountRecordBizApi;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.domain.sts.StsInfoBaseVo;
import com.cdqckj.gmis.common.utils.LocalDateUtil;
import com.cdqckj.gmis.security.annotation.PreAuth;
import com.cdqckj.gmis.statistics.domain.log.util.DateStsUtil;
import com.cdqckj.gmis.statistics.service.AccountNowService;
import com.cdqckj.gmis.statistics.service.CustomerNowService;
import com.cdqckj.gmis.statistics.service.GasFeiNowService;
import com.cdqckj.gmis.statistics.service.InsureNowService;
import com.cdqckj.gmis.statistics.vo.*;
import com.cdqckj.gmis.userarchive.enumeration.CustomerStatusEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;


/**
 * <p>
 * 前端控制器
 * 用户的客户档案的统计
 * </p>
 *
 * @author gmis
 * @date 2020-11-07
 */
@Slf4j
@Validated
@RestController
@RequestMapping("sts/customerNow")
@Api(value = "CustomerNow", tags = "客户档案")
@PreAuth(replace = "customerNow:")
public class CustomerNowController extends BaseController {

    @Autowired
    CustomerNowService customerNowService;

    @Autowired
    InsureNowService insureNowService;

    @Autowired
    GasFeiNowService gasFeiNowService;

    @Autowired
    AccountNowService accountNowService;

    @Autowired
    CustomerBizApi customerBizApi;

    @Autowired
    OpenAccountRecordBizApi openAccountRecordBizApi;

    @Autowired
    MeterAccountCancelBizApi meterAccountCancelBizApi;

    /**
     * @auth lijianguo
     * @date 2020/11/16 13:57
     * @remark 客户档案的统计信息
     */
    @ApiOperation(value = "客户档案_客户档案统计")
    @PostMapping("/stsCustom")
    public R<CustomerNowStsVo> stsCustom(@RequestBody StsSearchParam stsSearchParam){

        CustomerNowStsVo customerNowStsVo = new CustomerNowStsVo();
        stsSearchParam.setStartDay(null);
        stsSearchParam.setEndDay(null);
        List<StsInfoBaseVo<Integer, Long>> stsInfo = customerBizApi.stsByCustomerStatus(stsSearchParam).getData();
        for (StsInfoBaseVo<Integer, Long> info: stsInfo){
            if (CustomerStatusEnum.EFFECTIVE.getCode().equals(info.getType())){
                customerNowStsVo.setUserNum(info.getAmount().intValue());
            }
            if (CustomerStatusEnum.INVAID.getCode().equals(info.getType())){
                customerNowStsVo.setCloseNum(info.getAmount().intValue());
            }
        }
        customerNowStsVo.setTotalNum(customerNowStsVo.getUserNum() + customerNowStsVo.getCloseNum());
        stsSearchParam.setStartDay(LocalDate.now());
        stsSearchParam.setEndDay(LocalDateUtil.nextDay(LocalDate.now()));
        List<StsInfoBaseVo<Integer, Long>> stsDayInfo = customerBizApi.stsByCustomerStatus(stsSearchParam).getData();
        for (StsInfoBaseVo<Integer, Long> info: stsDayInfo){
            customerNowStsVo.setDayAddNum(customerNowStsVo.getDayAddNum() + info.getAmount().intValue());
        }
        customerNowStsVo.setTotalNum(customerNowStsVo.getUserNum() + customerNowStsVo.getCloseNum());

        stsSearchParam.setStartDay(LocalDateUtil.monthBegin(LocalDate.now()));
        stsSearchParam.setEndDay(LocalDateUtil.nextMonthBegin(LocalDate.now()));
        List<StsInfoBaseVo<Integer, Long>> stsMonthInfo = customerBizApi.stsByCustomerStatus(stsSearchParam).getData();
        for (StsInfoBaseVo<Integer, Long> info: stsMonthInfo){
            customerNowStsVo.setMonthAddNum(customerNowStsVo.getMonthAddNum() + info.getAmount().intValue());
        }

        // 预建档-所有
//        Integer totalNum = customerNowService.stsCustomType(Arrays.asList(0), stsSearchParam);
//        customerNowStsVo.setTotalNum(totalNum);
//        List<StsInfoBaseVo<Integer, Long>> accountNowTypeList = accountNowService.accountNowTypeFrom(stsSearchParam);
//        for (StsInfoBaseVo<Integer, Long> accountNowType: accountNowTypeList){
//            if (1 == accountNowType.getType()){
//                customerNowStsVo.setOpenAccountNum(accountNowType.getAmount().intValue());
//                continue;
//            }
//            if (3 == accountNowType.getType()){
//                customerNowStsVo.setCloseAccountNum(accountNowType.getAmount().intValue());
//                continue;
//            }
//        }
//        // 保险的统计 新增 续保 购买 = 新增 + 续保
//        Integer add = insureNowService.stsInsureNum(1, stsSearchParam);
//        customerNowStsVo.setAddInsuranceNum(add);
//        Integer reBuy = insureNowService.stsInsureNum(2, stsSearchParam);
//        customerNowStsVo.setRenewalNum(reBuy);
//        customerNowStsVo.setBuyInsuranceNum(add + reBuy);
//        // 黑名单 = 总的加入黑名单 - 总的解除黑名单
//        Integer blackIn = customerNowService.stsCustomType(Arrays.asList(3), stsSearchParam);
//        Integer blackOut = customerNowService.stsCustomType(Arrays.asList(4), stsSearchParam);
//        Integer blackTotal = blackIn - blackOut;
//        blackTotal = blackTotal < 0 ? 0 : blackTotal;
//        customerNowStsVo.setBlacklistNum(blackTotal);

        return R.success(customerNowStsVo);
    }

    /**
     * @auth lijianguo
     * @date 2020/11/17 9:57
     * @remark 客户信息看板-客户分类统计
     */
    @ApiOperation(value = "客户信息看板-客户分类统计")
    @PostMapping("/panel/stsCustomType")
    public R<List<StsInfoBaseVo<String, Long>>> stsCustomType(@RequestBody StsSearchParam stsSearchParam){

        List<StsInfoBaseVo<String, Long>> baseVoList;
        if(stsRawDataType()){
            baseVoList  = customerBizApi.stsCustomType(stsSearchParam).getData();
        }else {
            baseVoList = customerNowService.stsCustomTypeWithCondition(stsSearchParam);
        }
        return R.success(baseVoList);
    }

    /**
     * @auth lijianguo
     * @date 2020/11/17 14:41
     * @remark 客户增量统计
     */
    @ApiOperation(value = "客户信息看板-客户增量统计")
    @PostMapping("/panel/stsCustomAddType")
    public R<CustomerNowTypeKindVo> stsCustomAddType(@RequestBody StsSearchParam stsSearchParam){

        CustomerNowTypeKindVo ctk = new CustomerNowTypeKindVo();
        List<StsInfoBaseVo<String,Long>> openAccountSts;
        List<StsInfoBaseVo<String,Long>> closeAccountSts;
        if (stsRawDataType()){
            openAccountSts = openAccountRecordBizApi.stsOpenCustomerType(stsSearchParam).getData();
            closeAccountSts = meterAccountCancelBizApi.stsCancelCustomerType(stsSearchParam).getData();
        }else {
            openAccountSts = customerNowService.stsCustomAddType(stsSearchParam, 2);
            closeAccountSts = customerNowService.stsCustomAddType(stsSearchParam, 3);
        }
        ctk.setOpenAccountSts(openAccountSts);
        ctk.setCloseAccountSts(closeAccountSts);

        return R.success(ctk);
    }

    /**
     * @auth lijianguo
     * @date 2020/11/18 8:35
     * @remark 限购统计  Purchase limit
     */
    @ApiOperation(value = "客户信息看板-限购统计")
    @PostMapping("/panel/stsCustomPurchaseLimit")
    public R<PurchaseLimitVo> stsCustomPurchaseLimit(@RequestBody StsSearchParam stsSearchParam){

        PurchaseLimitVo vo = new PurchaseLimitVo();
        Long addBlack = customerBizApi.stsCustomBlackNum(stsSearchParam).getData();

//        StsInfoBaseVo<Integer, Long> addBlack = customerNowService.stsCustomBlackNum(stsSearchParam, 4);
//        StsInfoBaseVo<Integer, Long> cancelBlack = customerNowService.stsCustomBlackNum(stsSearchParam, 5);
//        if (addBlack != null) {
//            vo.setBlackNum(addBlack.getAmount());
//        }else {
//            vo.setBlackNum(0L);
//        }
        vo.setBlackNum(addBlack);
        vo.setSecurityCheckNum(0L);
        return R.success(vo);
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/3 18:28
    * @remark 业绩看板-财务数据统计
    */
    @ApiOperation(value = "业绩看板-财务数据统计")
    @PostMapping("/panel/stsFinance")
    public R<StsFinanceVo> stsFinance(@RequestBody StsSearchParam stsSearchParam){

        StsFinanceVo stsFinanceVo = new StsFinanceVo();
        StsFinanceVo.StsMeasures cusMeasures = getCustomMeasures(stsSearchParam);
        stsFinanceVo.setCustomer(cusMeasures);
        setStsGas(stsSearchParam, stsFinanceVo);

        return R.success(stsFinanceVo);
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/3 20:53
    * @remark 统计gas费用
    */
    private void setStsGas(StsSearchParam stsSearchParam, StsFinanceVo stsFinanceVo) {
        StsFinanceVo.StsMeasures gasMeasures  = new StsFinanceVo.StsMeasures();
        StsFinanceVo.StsMeasures gasFeiMeasures  = new StsFinanceVo.StsMeasures();

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
        stsFinanceVo.setGasAmount(gasMeasures);

        gasFeiMeasures.setToday(gasToday.getFeiAmount());
        gasFeiMeasures.setMonth(gasMonth.getFeiAmount());
        gasFeiMeasures.setYear(gasYear.getFeiAmount());
        gasFeiMeasures.setTotal(gasTotal.getFeiAmount());
        stsFinanceVo.setGasFei(gasFeiMeasures);
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/3 20:16
    * @remark 统计客户的数据
    */
    private StsFinanceVo.StsMeasures getCustomMeasures(StsSearchParam stsSearchParam) {

        LocalDate now = LocalDate.now();
        stsSearchParam.setStartDay(now);
        stsSearchParam.setEndDay(LocalDateUtil.nextDay(now));
        Long cusTodayAdd = customerNowService.stsCustomBlackNumNotNull(stsSearchParam).getAmount();

        stsSearchParam.setStartDay(LocalDateUtil.monthBegin(now));
        stsSearchParam.setEndDay(LocalDateUtil.nextMonthBegin(now));
        Long cusMonthAdd = customerNowService.stsCustomBlackNumNotNull(stsSearchParam).getAmount();

        stsSearchParam.setStartDay(LocalDateUtil.yearBegin(now));
        stsSearchParam.setEndDay(LocalDateUtil.nextYearBegin(now));
        Long cusYearAdd = customerNowService.stsCustomBlackNumNotNull(stsSearchParam).getAmount();

        stsSearchParam.setStartDay(null);
        stsSearchParam.setEndDay(null);
        Long cusTotalAdd = customerNowService.stsCustomBlackNumNotNull(stsSearchParam).getAmount();

        StsFinanceVo.StsMeasures cusMeasures = new StsFinanceVo.StsMeasures();
        cusMeasures.setToday(cusTodayAdd);
        cusMeasures.setMonth(cusMonthAdd);
        cusMeasures.setYear(cusYearAdd);
        cusMeasures.setTotal(cusTotalAdd);
        return cusMeasures;
    }


}
