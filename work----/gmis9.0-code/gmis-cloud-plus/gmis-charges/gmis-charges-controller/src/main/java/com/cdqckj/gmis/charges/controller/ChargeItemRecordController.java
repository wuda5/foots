package com.cdqckj.gmis.charges.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.charges.dto.ChargeItemRecordPageDTO;
import com.cdqckj.gmis.charges.dto.ChargeItemRecordResDTO;
import com.cdqckj.gmis.charges.dto.ChargeItemRecordSaveDTO;
import com.cdqckj.gmis.charges.dto.ChargeItemRecordUpdateDTO;
import com.cdqckj.gmis.charges.entity.ChargeItemRecord;
import com.cdqckj.gmis.charges.entity.ChargeRecord;
import com.cdqckj.gmis.charges.service.ChargeItemRecordService;
import com.cdqckj.gmis.charges.service.ChargeRecordService;
import com.cdqckj.gmis.charges.vo.ChargeItemVO;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.domain.sts.StsDateVo;
import com.cdqckj.gmis.common.domain.sts.StsInfoBasePlusVo;
import com.cdqckj.gmis.common.domain.sts.StsInfoBaseVo;
import com.cdqckj.gmis.common.utils.LocalDateUtil;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.utils.BeanPlusUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


/**
 * <p>
 * 前端控制器
 * 缴费项记录
 * </p>
 *
 * @author tp
 * @date 2020-09-07
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/chargeItemRecord")
@Api(value = "ChargeItemRecord", tags = "缴费项记录")
//@PreAuth(replace = "chargeItemRecord:")
public class ChargeItemRecordController extends SuperController<ChargeItemRecordService, Long, ChargeItemRecord, ChargeItemRecordPageDTO, ChargeItemRecordSaveDTO, ChargeItemRecordUpdateDTO> {


    @Autowired
    ChargeRecordService chargeRecordService;

    @Autowired
    ChargeItemRecordService chargeItemRecordService;

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list) {
        List<ChargeItemRecord> chargeItemRecordList = list.stream().map((map) -> {
            ChargeItemRecord chargeItemRecord = ChargeItemRecord.builder().build();
            //TODO 请在这里完成转换
            return chargeItemRecord;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(chargeItemRecordList));
    }

    /**
     * @auth lijianguo
     * @date 2020/10/27 14:36
     * @remark 查询
     */
    @PostMapping(value = "/getChangeItemByChargeNo")
    R<List<ChargeItemRecord>> getChangeItemByChargeNo(@RequestParam String chargeNo) {
        List<ChargeItemRecord> itemRecords = baseService.getChangeItemByChargeNo(chargeNo);
        return R.success(itemRecords);
    }

    /**
     * 获取表具燃气费缴纳次数和最近一次缴纳时间
     *
     * @param gasMeterCode 表具编号
     * @return 查询结果
     */
    @GetMapping("/getLastUpdateTimeAndCount")
    R<ChargeItemVO> getLastUpdateTimeAndCount(@RequestParam("gasMeterCode") String gasMeterCode) {
        return R.success(baseService.getLastUpdateTimeAndCount(gasMeterCode));
    }

    /**
     * 分页查询
     * @param params
     * @return
     */
    @PostMapping(value = "/pageList")
    R<IPage< ChargeItemRecordResDTO>> pageList(@RequestBody PageParams< ChargeItemRecordPageDTO> params){
        return R.success(chargeItemRecordService.pageListByMeterCustomerCode(params));

    }

    /**
     * @Author: lijiangguo
     * @Date: 2021/1/20 10:13
     * @remark 售气收费看板-用气类型统计
     */
    @PostMapping("sts/gasFeeAndType")
    R<List<StsInfoBasePlusVo<String, Long>>> stsGasFeeAndType(@RequestBody StsSearchParam stsSearchParam){
        List<StsInfoBasePlusVo<String, Long>> sts = this.baseService.stsGasFeeAndType(stsSearchParam);
        return R.success(sts);
    }

    /**
     * @Author: lijiangguo
     * @Date: 2021/1/20 10:13
     * @remark 卡表的售气量
     */
    @PostMapping("sts/stsCardGasMeter")
    R<StsDateVo> stsCardGasMeter(@RequestBody StsSearchParam stsSearchParam){

        StsDateVo vo = new StsDateVo();
        LocalDate now = LocalDate.now();
        stsSearchParam.setStartDay(now);
        stsSearchParam.setEndDay(LocalDateUtil.nextDay(now));
        BigDecimal today = this.baseService.stsCardGasMeter(stsSearchParam);

        stsSearchParam.setStartDay(LocalDateUtil.monthBegin(now));
        stsSearchParam.setEndDay(LocalDateUtil.nextMonthBegin(now));
        BigDecimal month = this.baseService.stsCardGasMeter(stsSearchParam);

        stsSearchParam.setStartDay(LocalDateUtil.yearBegin(now));
        stsSearchParam.setEndDay(LocalDateUtil.nextYearBegin(now));
        BigDecimal year = this.baseService.stsCardGasMeter(stsSearchParam);

        stsSearchParam.setStartDay(null);
        stsSearchParam.setEndDay(null);
        BigDecimal all = this.baseService.stsCardGasMeter(stsSearchParam);

        vo.setTodayNum(today);
        vo.setMonthNum(month);
        vo.setYearNum(year);
        vo.setAllNum(all);
        return R.success(vo);
    }
}
