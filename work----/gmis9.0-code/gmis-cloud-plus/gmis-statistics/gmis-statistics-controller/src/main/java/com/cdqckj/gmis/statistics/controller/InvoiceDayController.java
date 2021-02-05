package com.cdqckj.gmis.statistics.controller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.common.domain.Separate.SeparateListData;
import com.cdqckj.gmis.invoice.InvoiceBizApi;
import com.cdqckj.gmis.invoice.enumeration.InvoiceKindEnum;
import com.cdqckj.gmis.invoice.enumeration.InvoiceTypeEnum;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.operateparam.InvoiceParamBizApi;
import com.cdqckj.gmis.statistics.service.InvoiceDayService;
import com.cdqckj.gmis.statistics.vo.InvoiceDayKindVo;
import com.cdqckj.gmis.common.domain.sts.InvoiceDayStsVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
@RequestMapping("sts/invoiceDay")
@Api(value = "InvoiceDay", tags = "发票的统计数据")
public class InvoiceDayController extends BaseController {

    @Autowired
    InvoiceDayService invoiceDayService;

    @Autowired
    InvoiceBizApi invoiceBizApi;

    /**
     * @auth lijianguo
     * @date 2020/11/11 14:56
     * @remark 统计当天的发票的类型
     */
    @ApiOperation(value = "发票的类型")
    @GetMapping("/invoiceKind")
    public R<List<InvoiceDayKindVo>> invoiceKind(Long uId, LocalDateTime stsDay){

        List<InvoiceDayKindVo> stsNowList = invoiceDayService.stsKindNow(uId, null, stsDay);
        return R.success(stsNowList);
    }

    /**
     * @auth lijianguo
     * @date 2020/11/20 15:41
     * @remark 售气收费看板-发票统计
     */
    @ApiOperation(value = "售气收费看板-发票统计")
    @PostMapping("/panel/panelInvoiceKind")
    public R<List<InvoiceDayKindVo>> panelInvoiceKind(@RequestBody StsSearchParam stsSearchParam){

        List<InvoiceDayKindVo> stsNowList = invoiceDayService.panelInvoiceKind(stsSearchParam);
        return R.success(stsNowList);
    }
    
    /**
    * @Author: lijiangguo
    * @Date: 2020/12/18 11:03
    * @remark 柜台日常综合-发票统计
    */
    @ApiOperation(value = "柜台日常综合-发票统计")
    @PostMapping("/invoiceTypeSts")
    public R<HashMap> invoiceTypeSts(@RequestBody StsSearchParam stsSearchParam){

        HashMap<String, Object> result = new HashMap<>();
        InvoiceDayStsVo generalTotal = getInvoiceDayStsVo(stsSearchParam, InvoiceTypeEnum.GENERAL_INVOICE.toString(), InvoiceTypeEnum.GENERAL_INVOICE.getDesc());
        result.put("general", generalTotal);
        InvoiceDayStsVo specialTotal = getInvoiceDayStsVo(stsSearchParam, InvoiceTypeEnum.SPECIAL_INVOICE.toString(), InvoiceTypeEnum.SPECIAL_INVOICE.getDesc());
        result.put("special", specialTotal);
        InvoiceDayStsVo electronicTotal = getInvoiceDayStsVo(stsSearchParam, InvoiceTypeEnum.ELECTRONIC_INVOICE.toString(), InvoiceTypeEnum.ELECTRONIC_INVOICE.getDesc());
        result.put("electronic", electronicTotal);
        Integer totalNum = generalTotal.getAmount() + specialTotal.getAmount() + electronicTotal.getAmount();
        result.put("totalNum", totalNum);
        BigDecimal totalMoney = generalTotal.getTotalAmount().add(specialTotal.getTotalAmount()).add(electronicTotal.getTotalAmount());
        result.put("totalMoney", totalMoney);
        return R.success(result);
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/18 15:56
    * @remark 设置统计数据
    */
    private InvoiceDayStsVo getInvoiceDayStsVo(StsSearchParam stsSearchParam, String code, String desc) {

        List<InvoiceDayStsVo> stsList;
        if (stsRawDataType()){
            stsSearchParam.addSearchKey("type", code);
            stsList = invoiceBizApi.invoiceStsByType(stsSearchParam).getData();
        }else {
            stsList = invoiceDayService.invoiceTypeGroupByKind(stsSearchParam, code);
        }
        InvoiceDayStsVo generalTotal = new InvoiceDayStsVo();
        generalTotal.setKt(code);
        generalTotal.setKtName(desc);
        for (InvoiceDayStsVo vo: stsList){
            generalTotal.setAmount(generalTotal.getAmount() + vo.getAmount());
            generalTotal.setTotalAmount(generalTotal.getTotalAmount().add(vo.getTotalAmount()));
            generalTotal.setTotalTax(generalTotal.getTotalTax().add(vo.getTotalTax()));
        }
        SeparateListData<InvoiceDayStsVo> sepData = new SeparateListData<>(stsList);
        for (InvoiceKindEnum kindEnum: InvoiceKindEnum.values()){
            InvoiceDayStsVo vo = sepData.getTheDataByKey(kindEnum.getCode());
            if (vo == null) {
                vo = new InvoiceDayStsVo();
            }
            vo.setKt(kindEnum.getCode());
            vo.setKtName(kindEnum.getDesc());
            generalTotal.getKindList().add(vo);
        }
        return generalTotal;
    }


}
