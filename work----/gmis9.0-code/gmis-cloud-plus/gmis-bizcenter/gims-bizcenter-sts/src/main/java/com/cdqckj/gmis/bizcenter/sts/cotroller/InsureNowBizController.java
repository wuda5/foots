package com.cdqckj.gmis.bizcenter.sts.cotroller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.common.utils.LocalDateUtil;
import com.cdqckj.gmis.security.annotation.PreAuth;
import com.cdqckj.gmis.statistics.InsureNowApi;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.statistics.vo.InsureNowTypeStsVo;
import com.cdqckj.gmis.statistics.vo.PanelTimeGroupVo;
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
import java.time.LocalDateTime;


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
@RequestMapping("biz/sts/insureNow")
@Api(value = "InsureNow", tags = "保险")
@PreAuth(replace = "insureNow:")
public class InsureNowBizController {

    @Autowired
    InsureNowApi insureNowApi;

    /**
     * @auth lijianguo
     * @date 2020/11/19 10:22
     * @remark 客户信息看板-保险购买分类统计-时间比较
     */
    @ApiOperation(value = "客户信息看板-保险购买分类统计-时间比较")
    @PostMapping("/panel/insureNowByTypeCompare")
    public R<PanelTimeGroupVo> insureNowByTypeCompare(@RequestBody StsSearchParam stsSearchParam){

        InsureNowTypeStsVo searchData = insureNowApi.insureNowByType(stsSearchParam).getData();

        LocalDate now = LocalDate.now();
        stsSearchParam.setStartDay(LocalDateUtil.monthBegin(now));
        stsSearchParam.setEndDay(LocalDateUtil.nextMonthBegin(now));
        InsureNowTypeStsVo thisMothData = insureNowApi.insureNowByType(stsSearchParam).getData();

        stsSearchParam.setStartDay(LocalDateUtil.monthBeginChangeNum(now, -1L));
        stsSearchParam.setEndDay(LocalDateUtil.monthBeginChangeNum(now, 0L));
        InsureNowTypeStsVo lastMothData = insureNowApi.insureNowByType(stsSearchParam).getData();

        stsSearchParam.setStartDay(LocalDateUtil.yearChangeNum(LocalDateUtil.monthBegin(now), -1L));
        stsSearchParam.setEndDay(LocalDateUtil.yearChangeNum(LocalDateUtil.nextMonthBegin(now), -1L));
        InsureNowTypeStsVo lastYearData = insureNowApi.insureNowByType(stsSearchParam).getData();

        PanelTimeGroupVo panelTimeGroupVo = new PanelTimeGroupVo<>();
        panelTimeGroupVo.setSearchDate(searchData);
        panelTimeGroupVo.setNowDate(thisMothData);
        panelTimeGroupVo.setLastMonthDate(lastMothData);
        panelTimeGroupVo.setLastYearDate(lastYearData);
        return R.success(panelTimeGroupVo);
    }

}
