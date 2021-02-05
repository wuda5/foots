package com.cdqckj.gmis.charges.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.charges.dto.GasmeterSettlementDetailPageDTO;
import com.cdqckj.gmis.charges.dto.GasmeterSettlementDetailSaveDTO;
import com.cdqckj.gmis.charges.dto.GasmeterSettlementDetailUpdateDTO;
import com.cdqckj.gmis.charges.entity.GasmeterSettlementDetail;
import com.cdqckj.gmis.charges.vo.SettlementArrearsVO;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.domain.sts.StsDateVo;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * 结算明细API
 * @author tp
 * @Date 2020-08-28
 */
@FeignClient(name = "${gmis.feign.gmis-charges-server:gmis-charges-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/gasmeterSettlementDetail", qualifier = "GasmeterSettlementDetailBizApi")
public interface GasmeterSettlementDetailBizApi {
    /**
     * 保存结算明细
     * @param saveDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    R<GasmeterSettlementDetail> save(@RequestBody GasmeterSettlementDetailSaveDTO saveDTO);

    /**
     * 更新结算明细
     * @param updateDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    R<GasmeterSettlementDetail> update(@RequestBody GasmeterSettlementDetailUpdateDTO updateDTO);

    /**
     * 删除结算明细
     * @param id
     * @return
     */
    @RequestMapping(value = "/logicalDelete",method = RequestMethod.DELETE)
    R<Boolean> delete(@RequestParam("ids[]") List<Long> id);

    /**
     * 根据编号查询数据
     * @param nos
     * @return
     */
    @PostMapping("/getListByNos")
    R<List<GasmeterSettlementDetail>> getListByNos(@RequestParam(value = "nos[]") List<String> nos);

    /**
     * 分页查询结算明细
     * @param params
     * @return
     */
    @PostMapping(value = "/page")
    R<Page<GasmeterSettlementDetail>> page(@RequestBody PageParams<GasmeterSettlementDetailPageDTO> params);

    /**
     * ID查询结算明细
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    R<GasmeterSettlementDetail> get(@PathVariable("id") Long id);

    /**
     * 查询结算明细
     * @param queryInfo
     * @return
     */
    @PostMapping(value = "/query")
    R<List<GasmeterSettlementDetail>> query(@RequestBody GasmeterSettlementDetail queryInfo);


    /**
     * 批量修改结算明细
     * @param list
     * @return
     */
    @RequestMapping(value = "/updateBatch",method = RequestMethod.PUT)
    R<Boolean> updateBatch(@RequestBody List<GasmeterSettlementDetailUpdateDTO> list);

    /**
     * 批量保存结算明细
     * @param saveDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "/saveList")
    R<GasmeterSettlementDetail> saveList(@RequestBody List<GasmeterSettlementDetailSaveDTO> saveDTO);

    /**
     * 根据表具编号查询最新一条结算数据
     * @param gasMeterCode 表具编号
     * @return 最新的结算数据
     */
    @GetMapping("/getLatestOne")
    R<GasmeterSettlementDetail> getLatestOne(@RequestParam(value = "gasMeterCode")String gasMeterCode);

    /**
     * 获取物联网表中心计费后付费欠费金额
     * @param arrearsVO
     * @return
     */
    @PostMapping(value = "/getSettlementArrears")
    R<BigDecimal> getSettlementArrears(@RequestBody SettlementArrearsVO arrearsVO);

    /**
     * 根据 抄表数据ID查询结算数据
     * @param readMeterDataId 抄表数据ID
     * @return 结算数据
     */
    @GetMapping("/getByReadMeterDataId")
    R<GasmeterSettlementDetail> getByReadMeterDataId(@RequestParam(value = "readMeterDataId")Long readMeterDataId);


    /**
     * 根据 结算明细编号查询结算数据
     * @param settlementNo 结算明细编号
     * @return 结算数据
     */
    @GetMapping("/getBySettlementNo")
    R<GasmeterSettlementDetail> getBySettlementNo(@RequestParam(value = "settlementNo")String settlementNo);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/29 10:43
    * @remark 普表的用气量 月/年/总量
    */
    @PostMapping("sts/stsGeneralGasMeterGas")
    R<StsDateVo> stsGeneralGasMeterGas(@RequestBody StsSearchParam stsSearchParam);
}
