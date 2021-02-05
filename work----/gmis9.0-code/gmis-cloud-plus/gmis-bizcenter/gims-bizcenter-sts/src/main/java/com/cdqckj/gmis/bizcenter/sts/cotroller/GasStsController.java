package com.cdqckj.gmis.bizcenter.sts.cotroller;

import com.cdqckj.gmis.authority.entity.auth.Resource;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.charges.api.ChargeRecordBizApi;
import com.cdqckj.gmis.charges.vo.StsGasLadderDetailFeeVo;
import com.cdqckj.gmis.charges.vo.StsGasLadderFeeVo;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.utils.LocalDateUtil;
import com.cdqckj.gmis.operateparam.PriceSchemeBizApi;
import com.cdqckj.gmis.operateparam.entity.PriceScheme;
import com.cdqckj.gmis.operateparam.service.PriceSchemeService;
import com.cdqckj.gmis.security.annotation.PreAuth;
import com.cdqckj.gmis.statistics.vo.GasFeiNowTypeVo;
import com.cdqckj.gmis.statistics.vo.InsureNowTypeStsVo;
import com.cdqckj.gmis.statistics.vo.PanelTimeGroupVo;
import com.cdqckj.gmis.statistics.vo.StsTypeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: lijianguo
 * @time: 2021/01/08 16:18
 * @remark: 请添加类说明
 */
@Slf4j
@Validated
@RestController
@RequestMapping("biz/sts/gas")
@Api(value = "stsGas", tags = "用气统计")
@PreAuth(replace = "stsGas:")
public class GasStsController {

    @Autowired
    PriceSchemeBizApi priceSchemeBizApi;

    @Autowired
    ChargeRecordBizApi chargeRecordBizApi;

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/8 16:23
    * @remark 统计分析报表-售气收费看板-阶梯方案统计
    * 用气类型-用气方案-用气的几个阶段
    * 查找所有的用气方案-- 1）这个用气方案要在这个时间段之类有效 2）这个方案所有的收费数据
    */
//    @ApiOperation(value = "售气收费看板-阶梯方案统计")
//    @PostMapping("/totalSts")
//    public R<List<StsGasLadderFeeVo>> totalSts(@RequestBody StsSearchParam stsSearchParam){
//
//        List<StsGasLadderFeeVo> feeVoList = getStsGasLadderFeeVos(stsSearchParam);
//        return R.success(feeVoList);
//    }

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/13 13:23
    * @remark 获取统计的数据
    */
    private List<StsGasLadderFeeVo> getStsGasLadderFeeVos(@RequestBody StsSearchParam stsSearchParam) {

        List<Long> schemeIdList = getPriceSchemeId(stsSearchParam);
        HashMap hashMap = new HashMap();
        hashMap.put("idList", schemeIdList);
        stsSearchParam.setDataMap(hashMap);
        List<StsGasLadderFeeVo> voList = chargeRecordBizApi.stsGasLadderFee(stsSearchParam).getData();
        HashMap<Long, Integer> nameCount = new HashMap<>();
        for (StsGasLadderFeeVo vo: voList){
            Integer num = nameCount.get(vo.getGasTypeId());
            if (num == null){
                num = 1;
            }else {
                num ++;
            }
            nameCount.put(vo.getGasTypeId(), num);
            String realName = vo.getGasTypeName() + num;
            vo.setGasLadderName(realName);
        }
        return voList;
    }

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/13 14:14
    * @remark 获取方案的id
    */
    private List<Long> getPriceSchemeId(StsSearchParam stsSearchParam) {

        List<PriceScheme> priceSchemeList = priceSchemeBizApi.getPriceSchemeDuring(stsSearchParam).getData();
        List<Long> schemeIdList = new ArrayList<>();
        for (PriceScheme priceScheme: priceSchemeList) {
            if (stsSearchParam.getStartDay() == null && stsSearchParam.getEndDay() == null) {
                schemeIdList.add(priceScheme.getId());
            } else if (stsSearchParam.getStartDay() != null && stsSearchParam.getEndDay() == null) {
                if (priceScheme.getEndTime() == null) {
                    if (stsSearchParam.getStartDay().isAfter(priceScheme.getStartTime())) {
                        schemeIdList.add(priceScheme.getId());
                    }
                } else {
                    if (stsSearchParam.getStartDay().isAfter(priceScheme.getStartTime()) && stsSearchParam.getStartDay().isBefore(priceScheme.getEndTime())) {
                        schemeIdList.add(priceScheme.getId());
                    }
                }
            } else if (stsSearchParam.getStartDay() == null && stsSearchParam.getEndDay() != null) {
                if (priceScheme.getEndTime() == null) {
                    if (stsSearchParam.getEndDay().isAfter(priceScheme.getStartTime())) {
                        schemeIdList.add(priceScheme.getId());
                    }
                } else {
                    if (stsSearchParam.getEndDay().isAfter(priceScheme.getStartTime()) && stsSearchParam.getEndDay().isBefore(priceScheme.getEndTime())) {
                        schemeIdList.add(priceScheme.getId());
                    }
                }
            } else {
                if (priceScheme.getEndTime() == null) {
                    if (stsSearchParam.getEndDay().isAfter(priceScheme.getStartTime())) {
                        schemeIdList.add(priceScheme.getId());
                    }
                } else {
                    if (stsSearchParam.getEndDay().isAfter(priceScheme.getEndTime())) {

                    }else if(stsSearchParam.getStartDay().isBefore(priceScheme.getStartTime())) {

                    }else {
                        schemeIdList.add(priceScheme.getId());
                    }
                }
            }
        }
        return schemeIdList;
    }

    /**
     * @Author: lijiangguo
     * @Date: 2021/1/8 16:23
     * @remark 统计分析报表-售气收费看板-阶梯方案统计
     * 用气类型-用气方案-用气的几个阶段
     * 查找所有的用气方案-- 1）这个用气方案要在这个时间段之类有效 2）这个方案所有的收费数据
     */
    @ApiOperation(value = "售气收费看板-阶梯方案统计-时间比较")
    @PostMapping("/totalStsMonthCompare")
    public R<PanelTimeGroupVo> totalStsMonthCompare(@RequestBody StsSearchParam stsSearchParam){
        if(stsSearchParam.getStartDay() == null) {
            stsSearchParam.setStartDay(LocalDateUtil.yearBegin(LocalDate.now()));
        }
        List<StsGasLadderFeeVo> searchData = getStsGasLadderFeeVos(stsSearchParam);

        LocalDate now = LocalDate.now();
        stsSearchParam.setStartDay(LocalDateUtil.monthBegin(now));
        stsSearchParam.setEndDay(LocalDateUtil.nextMonthBegin(now));
        List<StsGasLadderFeeVo> thisMothData = getStsGasLadderFeeVos(stsSearchParam);

        stsSearchParam.setStartDay(LocalDateUtil.monthBeginChangeNum(now, -1L));
        stsSearchParam.setEndDay(LocalDateUtil.monthBeginChangeNum(now, 0L));
        List<StsGasLadderFeeVo> lastMothData = getStsGasLadderFeeVos(stsSearchParam);

        stsSearchParam.setStartDay(LocalDateUtil.yearChangeNum(LocalDateUtil.monthBegin(now), -1L));
        stsSearchParam.setEndDay(LocalDateUtil.yearChangeNum(LocalDateUtil.nextMonthBegin(now), -1L));
        List<StsGasLadderFeeVo> lastYearData = getStsGasLadderFeeVos(stsSearchParam);

        PanelTimeGroupVo panelTimeGroupVo = new PanelTimeGroupVo<>();
        panelTimeGroupVo.setSearchDate(searchData);
        panelTimeGroupVo.setNowDate(thisMothData);
        panelTimeGroupVo.setLastMonthDate(lastMothData);
        panelTimeGroupVo.setLastYearDate(lastYearData);

        setGasType(searchData, panelTimeGroupVo);
        setGasType(thisMothData, panelTimeGroupVo);
        setGasType(lastMothData, panelTimeGroupVo);
        setGasType(lastYearData, panelTimeGroupVo);
        return R.success(panelTimeGroupVo);
    }

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/14 11:56
    * @remark 添加类型
    */
    private void setGasType(List<StsGasLadderFeeVo> searchData, PanelTimeGroupVo panelTimeGroupVo) {
        for (StsGasLadderFeeVo vo : searchData) {
            StsTypeVo typeVo = new StsTypeVo(String.valueOf(vo.getGasLadderId()), vo.getGasLadderName());
            panelTimeGroupVo.addStsType(typeVo);
        }
    }

    /**
     * @Author: lijiangguo
     * @Date: 2021/1/8 16:23
     * @remark 统计分析报表-售气收费看板-阶梯方案统计
     * 用气类型-用气方案-用气的几个阶段
     * 查找所有的用气方案-- 1）这个用气方案要在这个时间段之类有效 2）这个方案所有的收费数据
     */
    @ApiOperation(value = "售气收费看板-用气阶梯统计-时间比较")
    @PostMapping("/totalLadderStsMonthCompare")
    public R<PanelTimeGroupVo> totalLadderStsMonthCompare(@RequestBody StsSearchParam stsSearchParam){
        if(stsSearchParam.getStartDay() == null) {
            stsSearchParam.setStartDay(LocalDateUtil.yearBegin(LocalDate.now()));
        }
        List<StsGasLadderDetailFeeVo> searchData = getStsGasLadderDetailFeeVos(stsSearchParam);

        LocalDate now = LocalDate.now();
        stsSearchParam.setStartDay(LocalDateUtil.monthBegin(now));
        stsSearchParam.setEndDay(LocalDateUtil.nextMonthBegin(now));
        List<StsGasLadderDetailFeeVo> thisMothData = getStsGasLadderDetailFeeVos(stsSearchParam);

        stsSearchParam.setStartDay(LocalDateUtil.monthBeginChangeNum(now, -1L));
        stsSearchParam.setEndDay(LocalDateUtil.monthBeginChangeNum(now, 0L));
        List<StsGasLadderDetailFeeVo> lastMothData = getStsGasLadderDetailFeeVos(stsSearchParam);

        stsSearchParam.setStartDay(LocalDateUtil.yearChangeNum(LocalDateUtil.monthBegin(now), -1L));
        stsSearchParam.setEndDay(LocalDateUtil.yearChangeNum(LocalDateUtil.nextMonthBegin(now), -1L));
        List<StsGasLadderDetailFeeVo> lastYearData = getStsGasLadderDetailFeeVos(stsSearchParam);

        PanelTimeGroupVo panelTimeGroupVo = new PanelTimeGroupVo<>();
        panelTimeGroupVo.setSearchDate(searchData);
        panelTimeGroupVo.setNowDate(thisMothData);
        panelTimeGroupVo.setLastMonthDate(lastMothData);
        panelTimeGroupVo.setLastYearDate(lastYearData);

        setLadderGasType(searchData, panelTimeGroupVo);
        setLadderGasType(thisMothData, panelTimeGroupVo);
        setLadderGasType(lastMothData, panelTimeGroupVo);
        setLadderGasType(lastYearData, panelTimeGroupVo);
        return R.success(panelTimeGroupVo);
    }

    /**
     * @Author: lijiangguo
     * @Date: 2021/1/14 11:56
     * @remark 梯度的分类
     */
    private void setLadderGasType(List<StsGasLadderDetailFeeVo> searchData, PanelTimeGroupVo panelTimeGroupVo) {
        for (StsGasLadderDetailFeeVo vo : searchData) {
            StsTypeVo typeVo = new StsTypeVo(String.valueOf(vo.getGasLadderId()), vo.getGasLadderName());
            panelTimeGroupVo.addStsType(typeVo);
        }
    }

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/13 16:36
    * @remark 统计阶梯信息
    */
    private List<StsGasLadderDetailFeeVo> getStsGasLadderDetailFeeVos(@RequestBody StsSearchParam stsSearchParam) {
        HashMap hashMap = new HashMap();
//        hashMap.put("priceType", "LADDER_PRICE");
        stsSearchParam.setDataMap(hashMap);
        List<Long> schemeIdList = getPriceSchemeId(stsSearchParam);
        hashMap.put("idList", schemeIdList);
        stsSearchParam.setDataMap(hashMap);
        List<StsGasLadderDetailFeeVo> voList = chargeRecordBizApi.stsGasLadderDetailFee(stsSearchParam).getData();
        HashMap<Long, Integer> nameCount = new HashMap<>();
        for (StsGasLadderDetailFeeVo vo: voList){
            Integer num = nameCount.get(vo.getGasTypeId());
            if (num == null){
                num = 1;
            }else {
                num ++;
            }
            nameCount.put(vo.getGasTypeId(), num);
            String realName = vo.getGasTypeName() + num;
            vo.setGasLadderName(realName);
        }
        return voList;
    }

}
