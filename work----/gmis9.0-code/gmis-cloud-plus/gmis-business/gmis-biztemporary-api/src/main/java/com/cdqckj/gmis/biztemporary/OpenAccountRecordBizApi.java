package com.cdqckj.gmis.biztemporary;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.biztemporary.dto.OpenAccountRecordSaveDTO;
import com.cdqckj.gmis.biztemporary.dto.OpenAccountRecordUpdateDTO;
import com.cdqckj.gmis.biztemporary.entity.OpenAccountRecord;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.domain.sts.StsInfoBaseVo;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

/**
 * @author songyz
 */
@FeignClient(name = "${gmis.feign.gmis-business-server:gmis-business-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/openAccountRecord", qualifier = "OpenAccountRecordBizApi")
public interface OpenAccountRecordBizApi {
    /**
     * 批量查询
     * @param data 批量查询
     * @return 查询结果
     */
    @ApiOperation(value = "批量查询", notes = "批量查询")
    @PostMapping("/query")
    public R<List<OpenAccountRecord>> query(@RequestBody OpenAccountRecord data);
    /**
     * 修改
     * @param updateDTO
     * @return
     */
    @ApiOperation(value = "修改")
    @PutMapping
    public R<OpenAccountRecord> update(@RequestBody OpenAccountRecordUpdateDTO updateDTO);
    /**
     * 通过关联id修改
     * @param updateDTO
     * @return
     */
    @ApiOperation(value = "通过关联id修改")
    @PutMapping("/updateByRelateId")
    public R<OpenAccountRecord> updateByRelateId(@RequestBody OpenAccountRecordUpdateDTO updateDTO);
    /**
     * 新增
     *
     * @param saveDTO 保存参数
     * @return 实体
     */
    @ApiOperation(value = "新增")
    @PostMapping
    R<OpenAccountRecord> save(@RequestBody @Valid OpenAccountRecordSaveDTO saveDTO);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/16 15:44
    * @remark 开户的数量
    */
    @ApiOperation(value = "开户的数量")
    @PostMapping("/sts/stsOpenAccountRecord")
    R<Long> stsOpenAccountRecord(@RequestBody StsSearchParam stsSearchParam);

    /**
     * @Author: lijiangguo
     * @Date: 2021/1/16 15:44
     * @remark 开户的统计
     */
    @PostMapping("/sts/stsOpenCustomerType")
    R<List<StsInfoBaseVo<String,Long>>> stsOpenCustomerType(@RequestBody StsSearchParam stsSearchParam);
}
