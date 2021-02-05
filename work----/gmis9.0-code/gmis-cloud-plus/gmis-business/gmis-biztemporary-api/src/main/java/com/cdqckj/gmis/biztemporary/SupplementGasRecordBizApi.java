package com.cdqckj.gmis.biztemporary;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.biztemporary.dto.SupplementGasRecordPageDTO;
import com.cdqckj.gmis.biztemporary.dto.SupplementGasRecordSaveDTO;
import com.cdqckj.gmis.biztemporary.dto.SupplementGasRecordUpdateDTO;
import com.cdqckj.gmis.biztemporary.entity.SupplementGasRecord;
import com.cdqckj.gmis.biztemporary.vo.SupplementGasRecordVO;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author ASUS
 */
@FeignClient(name = "${gmis.feign.gmis-business-server:gmis-business-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/supplementGasRecord", qualifier = "SupplementGasRecordBizApi")
public interface SupplementGasRecordBizApi {

    /**
     * 新增
     *
     * @param saveDTO 保存参数
     * @return 实体
     */
    @PostMapping
    R<SupplementGasRecord> save(@RequestBody @Valid SupplementGasRecordSaveDTO saveDTO);

    /**
     * 分页查询补气记录
     *
     * @param pageDTO 分页查询参数
     * @return 补气记录分页列表
     */
    @PostMapping(value = "/page")
    R<Page<SupplementGasRecord>> page(@RequestBody PageParams<SupplementGasRecordPageDTO> pageDTO);

    /**
     * 查询未完成的数据
     *
     * @param gasMeterCode 气表编号
     * @return 补气记录
     */
    @GetMapping("/queryUnfinishedRecord")
    R<SupplementGasRecord> queryUnfinishedRecord(@RequestParam("gasMeterCode") String gasMeterCode);

    /**
     * 更新数据
     *
     * @param updateDTO 更新参数
     * @return 更新后补气实体对象
     */
    @PutMapping
    R<SupplementGasRecord> update(@RequestBody SupplementGasRecordUpdateDTO updateDTO);

    /**
     * ID查询补气记录
     *
     * @param id 主键id
     * @return 补气记录
     */
    @GetMapping("/{id}")
    R<SupplementGasRecord> get(@PathVariable("id") Long id);

    /**
     * 统计补气次数
     *
     * @param gasMeterCode 表具编号
     * @return 补气次数
     */
    @GetMapping("/countCardRep")
    R<Integer> countCardRep(@RequestParam(value = "gasMeterCode") String gasMeterCode);


    /**
     * 补气流程中更新数据状态为待上表
     *
     * @param gasMeterCode 表具编号
     * @return 更新后补气实体对象
     */
    @PostMapping("updateStatusAfterOperate")
    R<SupplementGasRecord> updateStatusAfterOperate(@RequestBody String gasMeterCode);

    /**
     * 补气操作流程中更新数据状态为SUCCESS
     *
     * @param gasMeterCode 表具编号
     * @return 更新后补气实体对象
     */
    @PostMapping("updateStatusToSuccess")
    R<SupplementGasRecord> updateStatusToSuccess(@RequestBody String gasMeterCode);

    /**
     * 查询业务关注点列表数
     *
     * @param customerCode 用户编号
     * @param gasMeterCode 表具编号
     * @return 数据列表
     */
    @GetMapping("/queryFocusInfo")
    R<List<SupplementGasRecordVO>> queryFocusInfo(@RequestParam("customerCode") String customerCode,
                                                  @RequestParam(value = "gasMeterCode", required = false) String gasMeterCode);
}
