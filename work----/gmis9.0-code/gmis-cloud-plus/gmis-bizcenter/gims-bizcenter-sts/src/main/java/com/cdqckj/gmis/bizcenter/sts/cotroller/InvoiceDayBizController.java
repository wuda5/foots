package com.cdqckj.gmis.bizcenter.sts.cotroller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.bizcenter.sts.enums.StsInvoiceTypeEnum;
import com.cdqckj.gmis.common.domain.Separate.SeparateListData;
import com.cdqckj.gmis.common.domain.search.InitParamUtil;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.utils.LocalDateUtil;
import com.cdqckj.gmis.invoice.enumeration.InvoiceKindEnum;
import com.cdqckj.gmis.statistics.InvoiceDayApi;
import com.cdqckj.gmis.statistics.vo.InvoiceDayKindVo;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * <p>
 * 前端控制器
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-11-02
 */
@Slf4j
@Validated
@RestController
@RequestMapping("biz/sts/invoiceDay")
@Api(value = "InvoiceDay", tags = "发票")
public class InvoiceDayBizController {

    @Autowired
    InvoiceDayApi invoiceDayApi;

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/14 13:28
    * @remark  售气收费看板-发票统计
    */
    private List<InvoiceDayKindVo> panelInvoiceKind(@RequestBody StsSearchParam stsSearchParam){

        List<InvoiceDayKindVo> stsNowList = invoiceDayApi.panelInvoiceKind(stsSearchParam).getData();
        SeparateListData<InvoiceDayKindVo> sepInvoiceDayKind = new SeparateListData<>(stsNowList);

        List<InvoiceDayKindVo> resultList = new ArrayList<>(InvoiceKindEnum.values().length);
        for (InvoiceKindEnum e : InvoiceKindEnum.values()) {
            InvoiceDayKindVo vo = sepInvoiceDayKind.getTheDataByKey(e.getCode());
            if (vo == null){
                vo = new InvoiceDayKindVo();
                vo.setAmount(0);
                vo.setTotalAmount(BigDecimal.ZERO);
                vo.setTotalTax(BigDecimal.ZERO);
            }
            vo.setInvoiceKindCode(e.getCode());
            vo.setInvoiceKindName(e.getDesc());
            resultList.add(vo);
        }
        return resultList;
    }

    /**
     * @auth lijianguo
     * @date 2020/11/20 15:41
     * @remark 售气收费看板-发票统计-时间比较
     */
    @ApiOperation(value = "售气收费看板-发票统计-时间比较")
    @PostMapping("/panel/panelInvoiceKindCompare")
    public R<PanelTimeGroupVo> panelInvoiceKindCompare(@RequestBody StsSearchParam stsSearchParam){
        if(stsSearchParam.getStartDay() == null) {
            stsSearchParam.setStartDay(LocalDateUtil.yearBegin(LocalDate.now()));
        }
        HashMap searchData = invoiceTypeSts(stsSearchParam).getData();

        LocalDate now = LocalDate.now();
        stsSearchParam.setStartDay(LocalDateUtil.monthBegin(now));
        stsSearchParam.setEndDay(LocalDateUtil.nextMonthBegin(now));
        HashMap thisMothData = invoiceTypeSts(stsSearchParam).getData();

        stsSearchParam.setStartDay(LocalDateUtil.monthBeginChangeNum(now, -1L));
        stsSearchParam.setEndDay(LocalDateUtil.monthBeginChangeNum(now, 0L));
        HashMap lastMothData = invoiceTypeSts(stsSearchParam).getData();

        stsSearchParam.setStartDay(LocalDateUtil.yearChangeNum(LocalDateUtil.monthBegin(now), -1L));
        stsSearchParam.setEndDay(LocalDateUtil.yearChangeNum(LocalDateUtil.nextMonthBegin(now), -1L));
        HashMap lastYearData = invoiceTypeSts(stsSearchParam).getData();

        PanelTimeGroupVo panelTimeGroupVo = new PanelTimeGroupVo<>();
        panelTimeGroupVo.setSearchDate(searchData);
        panelTimeGroupVo.setNowDate(thisMothData);
        panelTimeGroupVo.setLastMonthDate(lastMothData);
        panelTimeGroupVo.setLastYearDate(lastYearData);
        for (StsInvoiceTypeEnum e : StsInvoiceTypeEnum.values()){
            StsTypeVo typeVo = new StsTypeVo(e.getName(), e.getDesc());
            panelTimeGroupVo.addStsType(typeVo);
        }
        return R.success(panelTimeGroupVo);
    }

    @ApiOperation(value = "柜台日常综合-发票统计")
    @PostMapping("/invoiceTypeSts")
    R<HashMap> invoiceTypeSts(@RequestBody StsSearchParam stsSearchParam){
        InitParamUtil.setNowDayAndMonth(stsSearchParam);
        return invoiceDayApi.invoiceTypeSts(stsSearchParam);
    }

}
