package com.cdqckj.gmis.operation;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.entity.SuperEntity;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.operation.dto.OperationProcessPageDTO;
import com.cdqckj.gmis.operation.dto.OperationProcessSaveDTO;
import com.cdqckj.gmis.operation.dto.OperationProcessUpdateDTO;
import com.cdqckj.gmis.operation.entity.OperationProcess;
import com.cdqckj.gmis.operation.hystrix.OperationProcessBizApiFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
@FeignClient(name = "${gmis.feign.operation-server:gmis-operation}", fallback = OperationProcessBizApiFallBack.class
        , path = "/operationProcess", qualifier = "OperationProcessBizApi")
public interface OperationProcessBizApi {

    /**
     * 运行维护流程信息新增
     *
     * @param saveDTO 保存参数
     * @return 实体
     */
    @PostMapping
    public R<OperationProcess> save(@RequestBody @Validated OperationProcessSaveDTO saveDTO);
    /**
     * 运行维护流程信息修改
     *
     * @param updateDTO
     * @return
     */
    @PutMapping
    public R<OperationProcess> update(@RequestBody @Validated(SuperEntity.Update.class) OperationProcessUpdateDTO updateDTO);
    /**
     * 运行维护流程信息分页查询
     *
     * @param params
     * @return
     */
    @PostMapping(value = "/page")
    public R<Page<OperationProcess>> page(@RequestBody @Validated PageParams<OperationProcessPageDTO> params);
}
