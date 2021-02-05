package com.cdqckj.gmis.biztemporary;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.biztemporary.dto.BusinessCheckResultDTO;
import com.cdqckj.gmis.biztemporary.dto.RemoveMeterRecordPageDTO;
import com.cdqckj.gmis.biztemporary.dto.RemoveMeterRecordSaveDTO;
import com.cdqckj.gmis.biztemporary.dto.RemoveMeterRecordUpdateDTO;
import com.cdqckj.gmis.biztemporary.entity.RemoveMeterRecord;
import com.cdqckj.gmis.biztemporary.vo.RemoveMeterRecordVO;
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
        , path = "/removeMeterRecord", qualifier = "RemoveMeterRecordBizApi")
public interface RemoveMeterRecordBizApi {

    /**
     * 新增
     *
     * @param saveDTO 保存参数
     * @return 实体
     */
    @PostMapping
    public R<RemoveMeterRecord> save(@RequestBody @Valid RemoveMeterRecordSaveDTO saveDTO);

    /**
     * 分页查询换表申请
     *
     * @param pageDTO
     * @return
     */
    @PostMapping(value = "/page")
    public R<Page<RemoveMeterRecord>> page(@RequestBody PageParams<RemoveMeterRecordPageDTO> pageDTO);


    /**
     * 更新数据
     *
     * @param updateDTO
     * @return
     */
    @PutMapping
    public R<RemoveMeterRecord> update(@RequestBody RemoveMeterRecordUpdateDTO updateDTO);

    /**
     * ID查询换表申请
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    R<RemoveMeterRecord> get(@PathVariable("id") Long id);


    /**
     * 根据缴费编号查询拆表记录
     *
     * @param chargeNo 缴费编号
     * @return 拆表记录
     */
    @GetMapping("/getOneByChargeNo")
    R<RemoveMeterRecord> getOneByChargeNo(@RequestParam("chargeNo") String chargeNo);

    /**
     * 拆表前检查：是否已有记录
     *
     * @param gasMeterCode 表具编号
     * @param customerCode 客户编号
     * @return 检查结果
     */
    @GetMapping("/check")
    R<BusinessCheckResultDTO<RemoveMeterRecord>> check(@RequestParam("gasMeterCode") String gasMeterCode,
                                                       @RequestParam("customerCode") String customerCode);

    /**
     * 查询业务关注点列表数
     *
     * @param customerCode 用户编号
     * @param gasMeterCode 表具编号
     * @return 数据列表
     */
    @GetMapping("/queryFocusInfo")
    R<List<RemoveMeterRecordVO>> queryFocusInfo(@RequestParam("customerCode") String customerCode,
                                                @RequestParam(value = "gasMeterCode", required = false) String gasMeterCode);

    /**
     * @Author: lijiangguo
     * @Date: 2021/1/16 16:36
     * @remark 拆表的统计
     */
    @ApiOperation(value = "拆表的统计")
    @PostMapping("/sts/stsRemoveMeterNum")
    R<Long> stsRemoveMeterNum(@RequestBody StsSearchParam stsSearchParam);
}
