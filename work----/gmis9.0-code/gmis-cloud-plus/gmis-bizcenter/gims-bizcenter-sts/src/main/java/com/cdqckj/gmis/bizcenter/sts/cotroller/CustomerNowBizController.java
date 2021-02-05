package com.cdqckj.gmis.bizcenter.sts.cotroller;

import com.cdqckj.gmis.authority.api.CommonConfigurationApi;
import com.cdqckj.gmis.authority.entity.common.DictionaryItem;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.common.domain.Separate.SeparateListData;
import com.cdqckj.gmis.common.domain.search.InitParamUtil;
import com.cdqckj.gmis.common.domain.sts.StsInfoBaseVo;
import com.cdqckj.gmis.common.utils.LocalDateUtil;
import com.cdqckj.gmis.security.annotation.PreAuth;
import com.cdqckj.gmis.statistics.CustomerNowApi;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.statistics.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
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
@RequestMapping("biz/sts/customerNow")
@Api(value = "CustomerNow", tags = "客户档案")
@PreAuth(replace = "customerNow:")
public class CustomerNowBizController {

    @Autowired
    CustomerNowApi customerNowApi;

    @Autowired
    CommonConfigurationApi commonConfigurationApi;

    /**
     * @auth lijianguo
     * @date 2020/11/16 13:57
     * @remark 客户档案的统计信息
     */
    @ApiOperation(value = "客户档案")
    @PostMapping("/stsCustom")
    public R<CustomerNowStsVo> stsCustom(@RequestBody StsSearchParam stsSearchParam){
        InitParamUtil.setNowDayAndMonth(stsSearchParam);
        CustomerNowStsVo vo = customerNowApi.stsCustom(stsSearchParam).getData();
        return R.success(vo);
    }

    /**
     * @auth lijianguo
     * @date 2020/11/17 9:57
     * @remark 客户信息看板-客户分类统计-时间比较
     */
    @ApiOperation(value = "客户信息看板-客户分类统计-时间比较")
    @PostMapping("/panel/stsCustomTypeCompare")
    public R<PanelTimeGroupVo> stsCustomTypeCompare(@RequestBody StsSearchParam stsSearchParam){
        if(stsSearchParam.getStartDay() == null) {
            stsSearchParam.setStartDay(LocalDateUtil.yearBegin(LocalDate.now()));
        }
        List<DictionaryItem> userTypeList = commonConfigurationApi.findCommonConfigbytype("USER_TYPE").getData();
        List<StsInfoBaseVo<String, Long>> searchData = getCustomTypeStsInfoBaseVos(stsSearchParam, userTypeList);

        LocalDate now = LocalDate.now();
        stsSearchParam.setStartDay(LocalDateUtil.monthBegin(now));
        stsSearchParam.setEndDay(LocalDateUtil.nextMonthBegin(now));
        List<StsInfoBaseVo<String, Long>> thisMothData = getCustomTypeStsInfoBaseVos(stsSearchParam, userTypeList);

        stsSearchParam.setStartDay(LocalDateUtil.monthBeginChangeNum(now, -1L));
        stsSearchParam.setEndDay(LocalDateUtil.monthBeginChangeNum(now, 0L));
        List<StsInfoBaseVo<String, Long>> lastMothData = getCustomTypeStsInfoBaseVos(stsSearchParam, userTypeList);

        stsSearchParam.setStartDay(LocalDateUtil.yearChangeNum(LocalDateUtil.monthBegin(now), -1L));
        stsSearchParam.setEndDay(LocalDateUtil.yearChangeNum(LocalDateUtil.nextMonthBegin(now), -1L));
        List<StsInfoBaseVo<String, Long>> lastYearData = getCustomTypeStsInfoBaseVos(stsSearchParam, userTypeList);

        PanelTimeGroupVo panelTimeGroupVo = new PanelTimeGroupVo<>();
        panelTimeGroupVo.setSearchDate(searchData);
        panelTimeGroupVo.setNowDate(thisMothData);
        panelTimeGroupVo.setLastMonthDate(lastMothData);
        panelTimeGroupVo.setLastYearDate(lastYearData);
        return R.success(panelTimeGroupVo);
    }

    /**
     * @auth lijianguo
     * @date 2020/11/18 14:46
     * @remark 统计一个时间段的信息
     */
    private List<StsInfoBaseVo<String, Long>> getCustomTypeStsInfoBaseVos(@RequestBody StsSearchParam stsSearchParam, List<DictionaryItem> userTypeList) {
        List<StsInfoBaseVo<String, Long>> baseVoList = customerNowApi.stsCustomType(stsSearchParam).getData();
        SeparateListData<StsInfoBaseVo<String,Long>> sepData = new SeparateListData<>(baseVoList);
        List<StsInfoBaseVo<String,Long>> resultList = new ArrayList<>(userTypeList.size());
        for (DictionaryItem item: userTypeList){

            StsInfoBaseVo<String,Long> vo = sepData.getTheDataByKey(item.getCode());
            if (vo == null){
                vo = new StsInfoBaseVo<>();
                vo.setAmount(0L);
            }
            vo.setType(item.getCode());
            vo.setTypeName(item.getName());
            resultList.add(vo);
        }
        return resultList;
    }

    /**
     * @auth lijianguo
     * @date 2020/11/17 14:41
     * @remark 客户信息看板-客户增量统计-时间比较
     */
    @ApiOperation(value = "客户信息看板-客户增量统计-时间比较")
    @PostMapping("/panel/stsCustomAddTypeCompare")
    public R<PanelTimeGroupVo> stsCustomAddTypeCompare(@RequestBody StsSearchParam stsSearchParam){
        if(stsSearchParam.getStartDay() == null) {
            stsSearchParam.setStartDay(LocalDateUtil.yearBegin(LocalDate.now()));
        }
        List<DictionaryItem> userTypeList = commonConfigurationApi.findCommonConfigbytype("USER_TYPE").getData();
        CustomerNowTypeKindVo searchData = stsCustomAddType(stsSearchParam, userTypeList);

        LocalDate now = LocalDate.now();
        stsSearchParam.setStartDay(LocalDateUtil.monthBegin(now));
        stsSearchParam.setEndDay(LocalDateUtil.nextMonthBegin(now));
        CustomerNowTypeKindVo thisMothData = stsCustomAddType(stsSearchParam, userTypeList);

        stsSearchParam.setStartDay(LocalDateUtil.monthBeginChangeNum(now, -1L));
        stsSearchParam.setEndDay(LocalDateUtil.monthBeginChangeNum(now, 0L));
        CustomerNowTypeKindVo lastMothData = stsCustomAddType(stsSearchParam, userTypeList);

        stsSearchParam.setStartDay(LocalDateUtil.yearChangeNum(LocalDateUtil.monthBegin(now), -1L));
        stsSearchParam.setEndDay(LocalDateUtil.yearChangeNum(LocalDateUtil.nextMonthBegin(now), -1L));
        CustomerNowTypeKindVo lastYearData = stsCustomAddType(stsSearchParam, userTypeList);

        PanelTimeGroupVo panelTimeGroupVo = new PanelTimeGroupVo<>();
        panelTimeGroupVo.setSearchDate(searchData);
        panelTimeGroupVo.setNowDate(thisMothData);
        panelTimeGroupVo.setLastMonthDate(lastMothData);
        panelTimeGroupVo.setLastYearDate(lastYearData);

        for (DictionaryItem dictionaryItem: userTypeList){
            StsTypeVo typeVo = new StsTypeVo(dictionaryItem.getCode(), dictionaryItem.getName());
            panelTimeGroupVo.addStsType(typeVo);
        }
        return R.success(panelTimeGroupVo);
    }

    private CustomerNowTypeKindVo stsCustomAddType(StsSearchParam stsSearchParam, List<DictionaryItem> userTypeList){

        CustomerNowTypeKindVo ctk = customerNowApi.stsCustomAddType(stsSearchParam).getData();
        ctk.setOpenAccountSts(setType(userTypeList, ctk.getOpenAccountSts()));
        ctk.setCloseAccountSts(setType(userTypeList, ctk.getCloseAccountSts()));
        return ctk;
    }

    /**
     * @auth lijianguo
     * @date 2020/11/19 9:23
     * @remark 设置类型
     */
    private List<StsInfoBaseVo<String,Long>> setType(List<DictionaryItem> userTypeList, List<StsInfoBaseVo<String, Long>> infoBaseVos) {

        SeparateListData<StsInfoBaseVo> sepData = new SeparateListData(infoBaseVos);
        List<StsInfoBaseVo<String,Long>> result = new ArrayList<>(userTypeList.size());
        for (DictionaryItem item: userTypeList){

            StsInfoBaseVo<String,Long> open = sepData.getTheDataByKey(item.getCode());
            if (open == null){
                open = new StsInfoBaseVo();
                open.setAmount(0L);
            }
            open.setType(item.getCode());
            open.setTypeName(item.getName());
            result.add(open);
        }
        return result;
    }

    /**
     * @auth lijianguo
     * @date 2020/11/18 8:35
     * @remark 限购统计  Purchase limit
     */
    @ApiOperation(value = "客户信息看板-限购统计-时间比较")
    @PostMapping("/panel/stsCustomPurchaseLimitCompare")
    public R<PanelTimeGroupVo> stsCustomPurchaseLimitCompare(@RequestBody StsSearchParam stsSearchParam){
        if(stsSearchParam.getStartDay() == null) {
            stsSearchParam.setStartDay(LocalDateUtil.yearBegin(LocalDate.now()));
        }
        PurchaseLimitVo searchData = customerNowApi.stsCustomPurchaseLimit(stsSearchParam).getData();

        LocalDate now = LocalDate.now();
        stsSearchParam.setStartDay(LocalDateUtil.monthBegin(now));
        stsSearchParam.setEndDay(LocalDateUtil.nextMonthBegin(now));
        PurchaseLimitVo thisMothData = customerNowApi.stsCustomPurchaseLimit(stsSearchParam).getData();

        stsSearchParam.setStartDay(LocalDateUtil.monthBeginChangeNum(now, -1L));
        stsSearchParam.setEndDay(LocalDateUtil.monthBeginChangeNum(now, 0L));
        PurchaseLimitVo lastMothData = customerNowApi.stsCustomPurchaseLimit(stsSearchParam).getData();

        stsSearchParam.setStartDay(LocalDateUtil.yearChangeNum(LocalDateUtil.monthBegin(now), -1L));
        stsSearchParam.setEndDay(LocalDateUtil.yearChangeNum(LocalDateUtil.nextMonthBegin(now), -1L));
        PurchaseLimitVo lastYearData = customerNowApi.stsCustomPurchaseLimit(stsSearchParam).getData();

        PanelTimeGroupVo panelTimeGroupVo = new PanelTimeGroupVo<>();
        panelTimeGroupVo.setSearchDate(searchData);
        panelTimeGroupVo.setNowDate(thisMothData);
        panelTimeGroupVo.setLastMonthDate(lastMothData);
        panelTimeGroupVo.setLastYearDate(lastYearData);
        return R.success(panelTimeGroupVo);
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/24 16:00
    * @remark 业绩看板-财务数据统计
    */
    @ApiOperation(value = "业绩看板-财务数据统计")
    @PostMapping("/panel/stsFinance")
    R<StsFinanceVo> stsFinance(@RequestBody StsSearchParam stsSearchParam){
        return customerNowApi.stsFinance(stsSearchParam);
    }

}
