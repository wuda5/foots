package com.cdqckj.gmis.biztemporary;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.biztemporary.dto.BusinessCheckResultDTO;
import com.cdqckj.gmis.biztemporary.dto.ChangeMeterRecordPageDTO;
import com.cdqckj.gmis.biztemporary.dto.ChangeMeterRecordSaveDTO;
import com.cdqckj.gmis.biztemporary.dto.ChangeMeterRecordUpdateDTO;
import com.cdqckj.gmis.biztemporary.entity.ChangeMeterRecord;
import com.cdqckj.gmis.biztemporary.vo.ChangeMeterRecordVO;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author ASUS
 */
@FeignClient(name = "${gmis.feign.gmis-business-server:gmis-business-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/changeMeterRecord", qualifier = "ChangeMeterRecordBizApi")
public interface ChangeMeterRecordBizApi {

    /**
     * 新增
     *
     * @param saveDTO 保存参数
     * @return 实体
     */
    @PostMapping
    R<ChangeMeterRecord> save(@RequestBody @Valid ChangeMeterRecordSaveDTO saveDTO);

    /**
     * 更新数据
     *
     * @param updateDTO
     * @return
     */
    @PutMapping
    public R<ChangeMeterRecord> update(@RequestBody ChangeMeterRecordUpdateDTO updateDTO);

    /**
     * 支付成功后更新数据状态
     *
     * @param id 数据id
     * @return 更新的记录
     */
    @PostMapping(value = "/updateAfterPaid")
    public R<ChangeMeterRecord> updateAfterPaid(@RequestBody Long id);


    /**
     * 新增
     *
     * @param saveDTO 保存参数
     * @return 实体
     */
    @PostMapping(value = "/addWithMeterInfo")
    R<ChangeMeterRecord> addWithMeterInfo(@RequestBody @Valid ChangeMeterRecordSaveDTO saveDTO);

    /**
     * 分页查询换表申请
     *
     * @param pageDTO 分页参数
     * @return 分页列表数据
     */
    @PostMapping(value = "/page")
    R<Page<ChangeMeterRecord>> page(@RequestBody PageParams<ChangeMeterRecordPageDTO> pageDTO);

    /**
     * ID查询换表申请
     *
     * @param id 主键id
     * @return 换表记录
     */
    @GetMapping("/{id}")
    R<ChangeMeterRecord> get(@PathVariable("id") Long id);

    /**
     * 根据缴费编号查询换表记录
     *
     * @param chargeNo 缴费编号
     * @return 换表记录
     */
    @GetMapping("/getOneByChargeNo")
    R<ChangeMeterRecord> getOneByChargeNo(@RequestParam("chargeNo") String chargeNo);

    /**
     * 批量查询
     *
     * @param changeMeterRecord 查询参数
     * @return 换表记录列表
     */
    @PostMapping("/query")
    R<List<ChangeMeterRecord>> query(@RequestBody ChangeMeterRecord changeMeterRecord);

    /**
     * 新增前数据校验
     *
     * @param gasMeterCode 表具编号
     * @param customerCode 客户编号
     * @return 检查结果
     */
    @GetMapping("/check")
    R<BusinessCheckResultDTO<ChangeMeterRecord>> check(@RequestParam("gasMeterCode") String gasMeterCode,
                                                       @RequestParam("customerCode") String customerCode);

    /**
     * 保存前校验新表是否可用
     *
     * @param oldMeterCode 表具编号
     * @param newMeterCode 客户编号
     * @return 检查结果
     */
    @GetMapping("/validateNewMeter")
    R<Boolean> validateNewMeter(@RequestParam("oldMeterCode") String oldMeterCode,
                                @RequestParam("newMeterCode") String newMeterCode);

    /**
     * 查询业务关注点列表数
     *
     * @param customerCode 用户编号
     * @param gasMeterCode 表具编号
     * @return 数据列表
     */
    @GetMapping("/queryFocusInfo")
    R<List<ChangeMeterRecordVO>> queryFocusInfo(@RequestParam("customerCode") String customerCode,
                                                @RequestParam(value = "gasMeterCode", required = false) String gasMeterCode);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/16 16:49
    * @remark 换表数据统计
    */
    @ApiOperation(value = "换表数据统计")
    @PostMapping("/sts/stsChangeMeterNum")
    R<Long> stsChangeMeterNum(@RequestBody StsSearchParam stsSearchParam);
}
