package com.cdqckj.gmis.archive;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.domain.sts.StsInfoBaseVo;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.userarchive.dto.MeterAccountCancelPageDTO;
import com.cdqckj.gmis.userarchive.dto.MeterAccountCancelSaveDTO;
import com.cdqckj.gmis.userarchive.dto.MeterAccountCancelUpdateDTO;
import com.cdqckj.gmis.userarchive.entity.MeterAccountCancel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author ASUS
 */
@FeignClient(name = "${gmis.feign.userarchive-server:gmis-userarchive-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/meterAccountCancel", qualifier = "MeterAccountCancelBizApi")
public interface MeterAccountCancelBizApi {

    /**
     * 新增
     *
     * @param saveDTO 保存参数
     * @return 实体
     */
    @PostMapping
    R<MeterAccountCancel> save(@RequestBody @Valid MeterAccountCancelSaveDTO saveDTO);

    /**
     * 批量新增
     *
     * @param list
     * @return
     */
    @PostMapping(value = "/saveList")
    R<MeterAccountCancel> saveList(@RequestBody List<MeterAccountCancelSaveDTO> list);


    /**
     * 分页查询换表申请
     *
     * @param pageDTO 分页参数
     * @return 销户列表
     */
    @PostMapping(value = "/page")
    R<Page<MeterAccountCancel>> page(@RequestBody PageParams<MeterAccountCancelPageDTO> pageDTO);


    /**
     * 更新数据
     *
     * @param updateDTO 更新参数
     * @return 更新对象
     */
    @PutMapping
    R<MeterAccountCancel> update(@RequestBody MeterAccountCancelUpdateDTO updateDTO);

    /**
     * ID查询换表申请
     *
     * @param id 主键id
     * @return 查询返回销户记录
     */
    @GetMapping("/{id}")
    R<MeterAccountCancel> get(@PathVariable("id") Long id);

    /**
     * 查询业务关注点列表数
     *
     * @param customerCode 用户编号
     * @param gasMeterCode 表具编号
     * @return 数据列表
     */
    @GetMapping("/queryFocusInfo")
    R<List<MeterAccountCancel>> queryFocusInfo(@RequestParam("customerCode") String customerCode,
                                               @RequestParam(value = "gasMeterCode", required = false) String gasMeterCode);

    /**
     * @Author: lijiangguo
     * @Date: 2021/1/16 15:44
     * @remark 销户统计
     */
    @PostMapping("/sts/stsCancelCustomerType")
    R<List<StsInfoBaseVo<String,Long>>> stsCancelCustomerType(@RequestBody StsSearchParam stsSearchParam);
}
