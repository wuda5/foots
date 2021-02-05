package com.cdqckj.gmis.charges.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.charges.dto.GasmeterArrearsDetailPageDTO;
import com.cdqckj.gmis.charges.dto.GasmeterArrearsDetailSaveDTO;
import com.cdqckj.gmis.charges.dto.GasmeterArrearsDetailUpdateDTO;
import com.cdqckj.gmis.charges.entity.GasmeterArrearsDetail;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.domain.sts.StsDateVo;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
/**
 * 结算欠费明细API
 * @author tp
 * @Date 2020-08-28
 */
@FeignClient(name = "${gmis.feign.gmis-charges-server:gmis-charges-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/gasmeterArrearsDetail", qualifier = "GasmeterArrearsDetailBizApi")
public interface GasmeterArrearsDetailBizApi {
    /**
     * 保存结算欠费明细
     * @param saveDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    R<GasmeterArrearsDetail> save(@RequestBody GasmeterArrearsDetailSaveDTO saveDTO);

    /**
     * 更新结算欠费明细
     * @param updateDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    R<GasmeterArrearsDetail> update(@RequestBody GasmeterArrearsDetailUpdateDTO updateDTO);

    /**
     * 删除结算欠费明细
     * @param id
     * @return
     */
    @RequestMapping(value = "/logicalDelete",method = RequestMethod.DELETE)
    R<Boolean> delete(@RequestParam("ids[]") List<Long> id);

    /**
     * 分页查询结算欠费明细
     * @param params
     * @return
     */
    @PostMapping(value = "/page")
    R<Page<GasmeterArrearsDetail>> page(@RequestBody PageParams<GasmeterArrearsDetailPageDTO> params);

    /**
     * ID查询结算欠费明细
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    R<GasmeterArrearsDetail> get(@PathVariable("id") Long id);

    /**
     * 查询欠费明细
     * @param queryInfo
     * @return
     */
    @PostMapping(value = "/query")
    R<List<GasmeterArrearsDetail>> query(@RequestBody GasmeterArrearsDetail queryInfo);


    /**
     * 批量修改欠费记录
     * @param list
     * @return
     */
    @RequestMapping(value = "/updateBatch",method = RequestMethod.PUT)
    R<Boolean> updateBatch(@RequestBody List<GasmeterArrearsDetailUpdateDTO> list);

    /**
     * 批量修改欠费收费状态为完成
     * @param ids
     * @return
     */
    @PostMapping(value = "/updateChargeStatusComplete")
    R<Integer> updateChargeStatusComplete(@RequestBody List<Long> ids);


    @PostMapping("/queryList")
    R<List<GasmeterArrearsDetail>> queryList(@RequestParam("ids[]") List<Long> ids);


    /**
     * 根据抄表ids查询 欠费记录
     * @param readMeterDataIds 抄表ids
     * @return 欠费记录信息
     */
    @PostMapping("/getByReadMeterIds")
    R<List<GasmeterArrearsDetail>> getByReadMeterIds(@RequestBody List<Long> readMeterDataIds);

    @ApiOperation(value = "统计欠费的数据")
    @PostMapping("/stsArrearsFee")
    R<BigDecimal> stsArrearsFee(@RequestBody StsSearchParam stsSearchParam);

    /**
     * 根据抄表id查询欠费记录
     *
     * @param readMeterId 抄表id
     * @return 欠费记录信息
     */
    @GetMapping("/getByReadMeterId")
    R<GasmeterArrearsDetail> getByReadMeterId(@RequestParam("readMeterId") Long readMeterId);

    /**
     * 根据结算编号查询欠费记录
     *
     * @param settlementNo 结算编号
     * @return 欠费记录信息
     */
    @GetMapping("/getBySettlementNo")
    R<GasmeterArrearsDetail> getBySettlementNo(@RequestParam("settlementNo") String settlementNo);

    /**
     * 根据结算编号查询欠费记录
     *
     * @param settlementNoList 结算编号集合
     * @return 欠费记录信息
     */
    @PostMapping("/queryBySettlementNoList")
    List<GasmeterArrearsDetail> queryBySettlementNoList(@RequestBody List<String> settlementNoList);

    /**
     * 是否有账单生成
     * @param gasmeterArrearsDetail
     * @return
     */
    @PostMapping("/checkExits")
    public  boolean checkExits(@RequestBody GasmeterArrearsDetail gasmeterArrearsDetail);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/21 16:29
    * @remark 后付费普通表 后付费物联网表
    */
    @PostMapping("sts/stsAfterGasMeter")
    R<StsDateVo> stsAfterGasMeter(@RequestBody StsSearchParam stsSearchParam);

    /**
     * 右侧业务关注点分页查询缴费记录
     * @param params
     * @return
     */
    @PostMapping(value = "/pageQueryFocusInfo")
    R<Page<GasmeterArrearsDetail>> pageQueryFocusInfo(@RequestBody PageParams<GasmeterArrearsDetailPageDTO> params);
}
