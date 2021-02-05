package com.cdqckj.gmis.charges.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.charges.dto.*;
import com.cdqckj.gmis.charges.entity.ChargeRecord;
import com.cdqckj.gmis.charges.vo.StsCounterStaffVo;
import com.cdqckj.gmis.charges.vo.StsGasLadderDetailFeeVo;
import com.cdqckj.gmis.charges.vo.StsGasLadderFeeVo;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.domain.sts.StsDateVo;
import com.cdqckj.gmis.common.domain.sts.StsInfoBaseVo;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 缴费记录API
 * @author tp
 * @Date 2020-08-28
 */
@FeignClient(name = "${gmis.feign.gmis-charges-server:gmis-charges-server}" , fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/chargeRecord", qualifier = "chargeRecordBizApi")
public interface ChargeRecordBizApi {
    /**
     * 保存缴费记录
     * @param saveDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    R<ChargeRecord> save(@RequestBody ChargeRecordSaveDTO saveDTO);

    /**
     * 更新缴费记录
     * @param updateDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    R<ChargeRecord> update(@RequestBody ChargeRecordUpdateDTO updateDTO);

    /**
     * 删除缴费记录
     * @param id
     * @return
     */
    @RequestMapping(value = "/logicalDelete",method = RequestMethod.DELETE)
    R<Boolean> delete(@RequestParam("ids[]") List<Long> id);
    /**
     * 分页查询缴费记录
     * @param params
     * @return
     */
    @PostMapping(value = "/page")
    R<Page<ChargeRecord>> page(@RequestBody PageParams<ChargeRecordPageDTO> params);

    /**
     * 分页查询缴费记录带是否申请退费标识
     * @param params
     * @return
     */
    @PostMapping(value = "/pageList")
    R<Page<ChargeRecordResDTO>> pageList(@RequestBody PageParams<ChargeListReqDTO> params);

    /**
     * ID查询缴费记录
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    R<ChargeRecord> get(@PathVariable("id") Long id);

    /**
     * 查询缴费记录
     * @param queryInfo
     * @return
     */
    @PostMapping(value = "/query")
    R<List<ChargeRecord>> query(@RequestBody ChargeRecord queryInfo);

    /**
     * 查询一段时间内的缴费记录
     * @param startTime
     * @param endTime
     * @return
     */
    @PostMapping("/queryChargesRecord")
    R<List<ChargeRecord>> queryChargesRecord(@RequestParam(value = "startTime", required = false) LocalDateTime startTime,
                                           @RequestParam(value = "endTime", required = false) LocalDateTime endTime);

    /**
     * 修改票据发票状态
     * @param chargeNo
     * @param invoiceType
     * @param invoiceStatus
     * @return
     */
    @PostMapping("/updateReceiptOrInvoiceStatus")
    R<Boolean> updateReceiptOrInvoiceStatus(@RequestParam(value = "chargeNo") String chargeNo,
                                            @RequestParam(value = "invoiceType") String invoiceType,
                                            @RequestParam(value = "invoiceStatus") String invoiceStatus
    );
    /**
     * 根据收费单号查询收费信息
     * @param chargeNo
     * @return
     */
    @GetMapping("/getChargeInfoByNo")
    R<ChargeRecord> getChargeRecordByNo(@RequestParam(value = "chargeNo") String chargeNo);
    /**
     * 是否可扎帐
     * @param chargeUserId
     * @param startTime
     * @param endTime
     * @return
     */
    @ApiOperation(value = "是否可扎帐")
    @PostMapping("/isSummaryBill")
    R<Boolean> isSummaryBill(@RequestParam("chargeUserId") long chargeUserId, @RequestParam(value = "startTime", required = false) LocalDateTime startTime, @RequestParam(value = "endTime", required = false) LocalDateTime endTime);
    /**
     * 查询
     * @param ids 主键id
     * @return 查询结果
     */
    @ApiOperation(value = "根据id集合批量查询", notes = "根据id集合批量查询")
    @PostMapping("/queryList")
    R<List<ChargeRecord>> queryList(@RequestParam("ids[]") List<Long> ids);

    @PostMapping("/counterStaff")
    @ApiOperation(value = "业绩看板_柜台人员统计")
    R<Page<StsCounterStaffVo>> counterStaff(@RequestBody StsSearchParam stsSearchParam);
    /**
     * 批量修改
     * @param list
     * @return
     */
    @ApiOperation(value = "修改")
    @PutMapping(value = "/updateBatch")
    R<Boolean> updateBatchById(@RequestBody List<ChargeRecordUpdateDTO> list);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/13 9:24
    * @remark 统计收费的信息
    */
    @ApiOperation(value = "统计收费的信息")
    @PostMapping(value = "/stsGasLadderFee")
    R<List<StsGasLadderFeeVo>> stsGasLadderFee(@RequestBody StsSearchParam stsSearchParam);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/13 16:01
    * @remark 统计阶梯收费的详细信息
    */
    @ApiOperation(value = "统计阶梯收费的详细信息")
    @PostMapping(value = "/stsGasLadderDetailFee")
    R<List<StsGasLadderDetailFeeVo>> stsGasLadderDetailFee(@RequestBody StsSearchParam stsSearchParam);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/15 15:24
    * @remark 收费的方式
    */
    @PostMapping("sts/feeByChargeMethod")
    R<List<StsInfoBaseVo<String, BigDecimal>>> stsFeeByChargeMethod(@RequestBody StsSearchParam stsSearchParam);

    /**
     * @Author: lijiangguo
     * @Date: 2021/1/15 15:24
     * @remark 收费的类型
     */
    @PostMapping("sts/chargeFeeItemType")
    R<List<StsInfoBaseVo<String, BigDecimal>>> stsChargeFeeItemType(@RequestBody StsSearchParam stsSearchParam);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/22 15:01
    * @remark 预付费表(IC卡表 物联网预付费 物联网表端计费)
    */
    @PostMapping("sts/stsBeforeGasMeter")
    R<StsDateVo> stsBeforeGasMeter(@RequestBody StsSearchParam stsSearchParam);


    /**
     * 右侧业务关注点分页查询缴费记录
     * @param params
     * @return
     */
    @PostMapping(value = "/pageQueryFocusInfo")
    R<Page<ChargeRecord>> pageQueryFocusInfo(@RequestBody PageParams<ChargeRecordPageDTO> params);
}
