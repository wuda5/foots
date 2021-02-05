package com.cdqckj.gmis.authority.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.authority.api.hystrix.ParameterBizApiFallback;
import com.cdqckj.gmis.authority.dto.common.ParameterPageDTO;
import com.cdqckj.gmis.authority.dto.common.ParameterSaveDTO;
import com.cdqckj.gmis.authority.dto.common.ParameterUpdateDTO;
import com.cdqckj.gmis.authority.entity.common.Parameter;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.entity.SuperEntity;
import com.cdqckj.gmis.base.request.PageParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

/**
 * 参数配置
 * @author gmis
 */
@FeignClient(name = "${gmis.feign.authority-server:gmis-authority-server}", fallback = ParameterBizApiFallback.class
        , path = "/parameter", qualifier = "ParameterBizApi")
public interface ParameterBizApi {


    /**
     * 新增
     *
     * @param saveDTO 保存参数
     * @return 实体
     */
    @ApiOperation(value = "新增")
    @PostMapping
    public R<Parameter> save(@RequestBody @Valid ParameterSaveDTO saveDTO);

    /**
     * 修改
     *
     * @param updateDTO
     * @return
     */
    @ApiOperation(value = "修改")
    @PutMapping
    public R<Parameter> update(@RequestBody @Validated(SuperEntity.Update.class) ParameterUpdateDTO updateDTO);

    /**
     * 批量查询
     *
     * @param data 批量查询
     * @return 查询结果
     */
    @ApiOperation(value = "批量查询", notes = "批量查询")
    @PostMapping("/query")
    public R<List<Parameter>> query(@RequestBody Parameter data);

    /**
     * 分页查询
     *
     * @param params
     * @return
     */
    @ApiOperation(value = "分页列表查询")
    @PostMapping(value = "/page")
    public R<IPage<Parameter>> page(@RequestBody @Validated PageParams<ParameterPageDTO> params);
}
