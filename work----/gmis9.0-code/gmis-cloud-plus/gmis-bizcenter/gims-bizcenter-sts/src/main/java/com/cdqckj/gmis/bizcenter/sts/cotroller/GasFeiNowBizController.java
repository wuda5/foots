package com.cdqckj.gmis.bizcenter.sts.cotroller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.common.domain.Separate.SeparateListData;
import com.cdqckj.gmis.common.utils.LocalDateUtil;
import com.cdqckj.gmis.security.annotation.PreAuth;
import com.cdqckj.gmis.statistics.GasFeiNowApi;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.statistics.vo.GasFeiNowTypeVo;
import com.cdqckj.gmis.statistics.vo.PanelTimeGroupVo;
import com.cdqckj.gmis.statistics.vo.PurchaseLimitVo;
import com.cdqckj.gmis.statistics.vo.StsTypeVo;
import com.cdqckj.gmis.userarchive.enumeration.CustomerTypeEnum;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


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
@RequestMapping("biz/sts/gasFeiNow")
@Api(value = "GasFeiNow", tags = "燃气费")
@PreAuth(replace = "gasFeiNow:")
public class GasFeiNowBizController {

    @Autowired
    GasFeiNowApi gasFeiNowApi;

    /**
     * @auth lijianguo
     * @date 2020/11/17 14:41
     * @remark 售气收费看板-用气类型统计-时间比较
     */
    @ApiOperation(value = "售气收费看板-用气类型统计-时间比较")
    @PostMapping("/panel/gasFeiTypeCompare")
    public R<PanelTimeGroupVo> gasFeiTypeCompare(@RequestBody StsSearchParam stsSearchParam){

        List<GasFeiNowTypeVo> searchData = gasFeiType(stsSearchParam);

        LocalDate now = LocalDate.now();
        stsSearchParam.setStartDay(LocalDateUtil.monthBegin(now));
        stsSearchParam.setEndDay(LocalDateUtil.nextMonthBegin(now));
        List<GasFeiNowTypeVo> thisMothData = gasFeiType(stsSearchParam);

        stsSearchParam.setStartDay(LocalDateUtil.monthBeginChangeNum(now, -1L));
        stsSearchParam.setEndDay(LocalDateUtil.monthBeginChangeNum(now, 0L));
        List<GasFeiNowTypeVo> lastMothData = gasFeiType(stsSearchParam);

        stsSearchParam.setStartDay(LocalDateUtil.yearChangeNum(LocalDateUtil.monthBegin(now), -1L));
        stsSearchParam.setEndDay(LocalDateUtil.yearChangeNum(LocalDateUtil.nextMonthBegin(now), -1L));
        List<GasFeiNowTypeVo> lastYearData = gasFeiType(stsSearchParam);

        PanelTimeGroupVo panelTimeGroupVo = new PanelTimeGroupVo<>();
        panelTimeGroupVo.setSearchDate(searchData);
        panelTimeGroupVo.setNowDate(thisMothData);
        panelTimeGroupVo.setLastMonthDate(lastMothData);
        panelTimeGroupVo.setLastYearDate(lastYearData);
        for (CustomerTypeEnum e: CustomerTypeEnum.values()){
            StsTypeVo typeVo = new StsTypeVo(e.getCode(), e.getDesc());
            panelTimeGroupVo.addStsType(typeVo);
        }
        return R.success(panelTimeGroupVo);
    }

    /**
     * @auth lijianguo
     * @date 2020/11/17 14:41
     * @remark 用气类型统计
     */
    private List<GasFeiNowTypeVo> gasFeiType(StsSearchParam stsSearchParam){
        List<GasFeiNowTypeVo> feiNowTypeVos = gasFeiNowApi.gasFeiType(stsSearchParam).getData();
        SeparateListData<GasFeiNowTypeVo> seepData = new SeparateListData<>(feiNowTypeVos);
        List<GasFeiNowTypeVo> result = new ArrayList<>();
        for (CustomerTypeEnum e: CustomerTypeEnum.values()){

            GasFeiNowTypeVo vo = seepData.getTheDataByKey(e.getCode());
            if (vo == null){
                vo = new GasFeiNowTypeVo();
                vo.setFeiAmount(BigDecimal.ZERO);
                vo.setGasAmount(BigDecimal.ZERO);
            }
            vo.setType(e.getCode());
            vo.setTypeName(e.getDesc());
            result.add(vo);
        }
        return result;
    }
}
