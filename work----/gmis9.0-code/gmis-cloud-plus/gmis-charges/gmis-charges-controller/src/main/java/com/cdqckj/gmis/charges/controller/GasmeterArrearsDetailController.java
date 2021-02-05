package com.cdqckj.gmis.charges.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.entity.Entity;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.charges.dto.GasmeterArrearsDetailPageDTO;
import com.cdqckj.gmis.charges.dto.GasmeterArrearsDetailSaveDTO;
import com.cdqckj.gmis.charges.dto.GasmeterArrearsDetailUpdateDTO;
import com.cdqckj.gmis.charges.entity.GasmeterArrearsDetail;
import com.cdqckj.gmis.charges.service.GasmeterArrearsDetailService;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.domain.sts.StsDateVo;
import com.cdqckj.gmis.common.utils.LocalDateUtil;
import com.cdqckj.gmis.common.utils.UserOrgIdUtil;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <p>
 * 前端控制器
 * 气表欠费明细
 * </p>
 *
 * @author tp
 * @date 2020-09-07
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/gasmeterArrearsDetail")
@Api(value = "GasmeterArrearsDetail", tags = "气表欠费明细")
//@PreAuth(replace = "gasmeterArrearsDetail:")
public class GasmeterArrearsDetailController extends SuperController<GasmeterArrearsDetailService, Long, GasmeterArrearsDetail, GasmeterArrearsDetailPageDTO, GasmeterArrearsDetailSaveDTO, GasmeterArrearsDetailUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<GasmeterArrearsDetail> gasmeterArrearsDetailList = list.stream().map((map) -> {
            GasmeterArrearsDetail gasmeterArrearsDetail = GasmeterArrearsDetail.builder().build();
            //TODO 请在这里完成转换
            return gasmeterArrearsDetail;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(gasmeterArrearsDetailList));
    }

    /**
     * 修改收费状态为完成
     *
     * @param ids 欠费记录ID
     * @return 修改结果
     */
    @ApiOperation(value = "修改收费状态为完成", notes = "修改收费状态为完成")
    @PostMapping("/updateChargeStatusComplete")
    public R<Integer> updateChargeStatusComplete(@RequestBody List<Long> ids) {
        return R.success(baseService.updateChargeStatusComplete(ids));
    }

    /**
     * 根据抄表ids查询 欠费记录
     * @param readMeterDataIds 抄表ids
     * @return 欠费记录信息
     */
    @ApiOperation(value = "根据抄表ids查询 欠费记录", notes = "根据抄表ids查询 欠费记录")
    @PostMapping("/getByReadMeterIds")
    public R<List<GasmeterArrearsDetail>> getByReadMeterIds(@RequestBody List<Long> readMeterDataIds) {
        if(CollectionUtils.isNotEmpty(readMeterDataIds)) {
            List<GasmeterArrearsDetail> list= baseService.getBaseMapper()
                    .selectList(new LbqWrapper<GasmeterArrearsDetail>()
                            .in(GasmeterArrearsDetail::getReadmeterDataId, readMeterDataIds));
            list.forEach(e ->{
                e.setTotalAmount(e.getLatepayAmount().add(e.getArrearsMoney()));
            });
            return R.success(list);
        }else{
            return  R.success(new ArrayList<>());
        }
    }

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/7 11:05
    * @remark 统计欠费的数据
    */
    @ApiOperation(value = "统计欠费的数据")
    @PostMapping("/stsArrearsFee")
    public R<BigDecimal> stsArrearsFee(@RequestBody StsSearchParam stsSearchParam) {
        BigDecimal fee = baseService.stsArrearsFee(stsSearchParam);
        return R.success(fee);
    }


    /**
     * 根据抄表id查询欠费记录
     *
     * @param readMeterId 抄表id
     * @return 欠费记录信息
     */
    @ApiOperation(value = "根据抄表id查询 欠费记录", notes = "根据抄表id查询 欠费记录")
    @GetMapping("/getByReadMeterId")
    public R<GasmeterArrearsDetail> getByReadMeterId(@RequestParam("readMeterId") Long readMeterId) {
        return R.success(baseService.getOne(new LbqWrapper<GasmeterArrearsDetail>()
                .eq(GasmeterArrearsDetail::getReadmeterDataId, readMeterId)
                .gt(GasmeterArrearsDetail::getArrearsMoney, 0)));
    }

    /**
     * 根据结算编号查询欠费记录
     *
     * @param settlementNo 结算编号
     * @return 欠费记录信息
     */
    @ApiOperation(value = "根据抄表id查询 欠费记录", notes = "根据抄表id查询 欠费记录")
    @GetMapping("/getBySettlementNo")
    public R<GasmeterArrearsDetail> getBySettlementNo(@RequestParam("settlementNo") String settlementNo) {
        return R.success(baseService.getOne(new LbqWrapper<GasmeterArrearsDetail>()
                .eq(GasmeterArrearsDetail::getSettlementNo, settlementNo)
                .gt(GasmeterArrearsDetail::getArrearsMoney, 0)));
    }

    /**
     * 根据结算编号集合查询欠费记录列表
     *
     * @param settlementNoList 结算编号
     * @return 欠费记录信息
     */
    @ApiOperation(value = "根据抄表id查询 欠费记录", notes = "根据抄表id查询 欠费记录")
    @PostMapping("/queryBySettlementNoList")
    public List<GasmeterArrearsDetail> queryBySettlementNoList(@RequestBody List<String> settlementNoList) {
        return baseService.list(new LbqWrapper<GasmeterArrearsDetail>()
                .in(GasmeterArrearsDetail::getSettlementNo, settlementNoList)
                .gt(GasmeterArrearsDetail::getArrearsMoney, 0));
    }

    /**
     * 是否有账单生成
     * @param gasmeterArrearsDetail
     * @return
     */
    @ApiOperation(value = "查询是否有账单生成", notes = "查询是否有账单生成")
    @PostMapping("/checkExits")
    public  boolean checkExits(@RequestBody GasmeterArrearsDetail gasmeterArrearsDetail){
        return baseService.checkExits(gasmeterArrearsDetail);
    }

    /**
     * @Author: lijiangguo
     * @Date: 2021/1/21 16:29
     * @remark 后付费普通表 后付费物联网表
     */
    @PostMapping("sts/stsAfterGasMeter")
    R<StsDateVo> stsAfterGasMeter(@RequestBody StsSearchParam stsSearchParam){

        String gasMeterType = stsSearchParam.getSearchKeyValue("gasMeterType");
        StsDateVo vo = new StsDateVo();
        LocalDate now = LocalDate.now();
        stsSearchParam.setStartDay(now);
        stsSearchParam.setEndDay(LocalDateUtil.nextDay(now));
        BigDecimal today = this.baseService.stsAfterGasMeter(stsSearchParam, gasMeterType);

        stsSearchParam.setStartDay(LocalDateUtil.monthBegin(now));
        stsSearchParam.setEndDay(LocalDateUtil.nextMonthBegin(now));
        BigDecimal month = this.baseService.stsAfterGasMeter(stsSearchParam, gasMeterType);

        stsSearchParam.setStartDay(LocalDateUtil.yearBegin(now));
        stsSearchParam.setEndDay(LocalDateUtil.nextYearBegin(now));
        BigDecimal year = this.baseService.stsAfterGasMeter(stsSearchParam, gasMeterType);

        stsSearchParam.setStartDay(null);
        stsSearchParam.setEndDay(null);
        BigDecimal all = this.baseService.stsAfterGasMeter(stsSearchParam, gasMeterType);

        vo.setTodayNum(today);
        vo.setMonthNum(month);
        vo.setYearNum(year);
        vo.setAllNum(all);
        return R.success(vo);
    }

    /**
     * 右侧业务关注点分页查询缴费记录
     * @param params
     * @return
     */
    @ApiOperation(value = "右侧业务关注点分页查询缴费记录")
    @PostMapping(value = "/pageQueryFocusInfo")
    R<Page<GasmeterArrearsDetail>> pageQueryFocusInfo(@RequestBody PageParams<GasmeterArrearsDetailPageDTO> params){
        Page<GasmeterArrearsDetail> page = new Page<>(params.getCurrent(), params.getSize());
        GasmeterArrearsDetail model = BeanUtil.toBean(params.getModel(), GasmeterArrearsDetail.class);
        LbqWrapper<GasmeterArrearsDetail> wrapper = Wraps.lbQ(model);
        wrapper.in(GasmeterArrearsDetail::getOrgId, UserOrgIdUtil.getUserDataScopeList())
                .orderByDesc(Entity::getUpdateTime);
        this.baseService.page(page, wrapper);
        return success(page);
    }

}
