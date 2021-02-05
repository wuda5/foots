package com.cdqckj.gmis.charges.controller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.entity.SuperEntity;
import com.cdqckj.gmis.charges.dto.GasmeterSettlementDetailPageDTO;
import com.cdqckj.gmis.charges.dto.GasmeterSettlementDetailSaveDTO;
import com.cdqckj.gmis.charges.dto.GasmeterSettlementDetailUpdateDTO;
import com.cdqckj.gmis.charges.entity.GasmeterSettlementDetail;
import com.cdqckj.gmis.charges.service.GasmeterSettlementDetailService;
import com.cdqckj.gmis.charges.vo.SettlementArrearsVO;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.domain.sts.StsDateVo;
import com.cdqckj.gmis.common.utils.LocalDateUtil;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.security.annotation.PreAuth;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <p>
 * 前端控制器
 * 气表结算明细
 * </p>
 *
 * @author tp
 * @date 2020-09-15
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/gasmeterSettlementDetail")
@Api(value = "GasmeterSettlementDetail", tags = "气表结算明细")
@PreAuth(replace = "gasmeterSettlementDetail:")
public class GasmeterSettlementDetailController extends SuperController<GasmeterSettlementDetailService, Long, GasmeterSettlementDetail, GasmeterSettlementDetailPageDTO, GasmeterSettlementDetailSaveDTO, GasmeterSettlementDetailUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<GasmeterSettlementDetail> gasmeterSettlementDetailList = list.stream().map((map) -> {
            GasmeterSettlementDetail gasmeterSettlementDetail = GasmeterSettlementDetail.builder().build();
            //TODO 请在这里完成转换
            return gasmeterSettlementDetail;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(gasmeterSettlementDetailList));
    }

    @ApiOperation(value = "根据编号查询数据")
    @PostMapping("/getListByNos")
    @GlobalTransactional
    public R<List<GasmeterSettlementDetail>> getListByNos(@RequestParam(value = "nos[]") List<String> nos){
        return R.success(baseService.list(new LbqWrapper<GasmeterSettlementDetail>().in(GasmeterSettlementDetail::getSettlementNo,nos)));
    }


    @ApiOperation(value = "根据表具编号查询最新一条结算数据")
    @GetMapping("/getLatestOne")
    public R<GasmeterSettlementDetail> getLatestOne(@RequestParam(value = "gasMeterCode")String gasMeterCode){
        LbqWrapper<GasmeterSettlementDetail> lbqWrapper = Wraps.lbQ();
                lbqWrapper.eq(GasmeterSettlementDetail::getGasmeterCode,gasMeterCode)
                        .orderByDesc(SuperEntity::getCreateTime).last("limit 1");
        return R.success(baseService.getOne(lbqWrapper));
    }

    @ApiOperation(value = "获取物联网表中心计费后付费欠费金额")
    @PostMapping(value = "/getSettlementArrears")
    R<BigDecimal> getSettlementArrears(@RequestBody SettlementArrearsVO arrearsVO){
        return baseService.getSettlementArrears(arrearsVO);
    }

    @ApiOperation(value = "根据抄表数据ID查询结算数据")
    @GetMapping("/getByReadMeterDataId")
    public R<GasmeterSettlementDetail> getByReadMeterDataId(@RequestParam(value = "readMeterDataId")Long readMeterDataId){
        LbqWrapper<GasmeterSettlementDetail> lbqWrapper = Wraps.lbQ();
        lbqWrapper.eq(GasmeterSettlementDetail::getReadmeterDataId,readMeterDataId);
        return R.success(baseService.getOne(lbqWrapper));
    }

    @ApiOperation(value = "根据结算明细编号查询结算数据")
    @GetMapping("/getBySettlementNo")
    public R<GasmeterSettlementDetail> getBySettlementNo(@RequestParam(value = "settlementNo") String settlementNo) {
        LbqWrapper<GasmeterSettlementDetail> lbqWrapper = Wraps.lbQ();
        lbqWrapper.eq(GasmeterSettlementDetail::getSettlementNo, settlementNo);
        return R.success(baseService.getOne(lbqWrapper));
    }

    /**
     * @Author: lijiangguo
     * @Date: 2021/1/29 10:43
     * @remark 普表的用气量 月/年/总量
     */
    @PostMapping("sts/stsGeneralGasMeterGas")
    R<StsDateVo> stsGeneralGasMeterGas(@RequestBody StsSearchParam stsSearchParam){

        StsDateVo vo = new StsDateVo();
        vo.setTodayNum(null);

        stsSearchParam.setStartDay(LocalDateUtil.monthBegin(LocalDate.now()));
        stsSearchParam.setEndDay(LocalDateUtil.nextMonthBegin(LocalDate.now()));
        BigDecimal month = this.baseService.stsGeneralGasMeterUseGas(stsSearchParam);

        stsSearchParam.setStartDay(LocalDateUtil.yearBegin(LocalDate.now()));
        stsSearchParam.setEndDay(LocalDateUtil.nextYearBegin(LocalDate.now()));
        BigDecimal year = this.baseService.stsGeneralGasMeterUseGas(stsSearchParam);

        stsSearchParam.setStartDay(null);
        stsSearchParam.setEndDay(null);
        BigDecimal all = this.baseService.stsGeneralGasMeterUseGas(stsSearchParam);

        vo.setMonthNum(month);
        vo.setYearNum(year);
        vo.setAllNum(all);
        return R.success(vo);
    }
}
