package com.cdqckj.gmis.operation;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.log.annotation.SysLog;
import com.cdqckj.gmis.operation.dto.OperationAcceptancePageDTO;
import com.cdqckj.gmis.operation.dto.OperationAcceptanceSaveDTO;
import com.cdqckj.gmis.operation.entity.OperationAcceptance;
import com.cdqckj.gmis.operation.hystrix.OperationAcceptanceBizApiFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
@FeignClient(name = "${gmis.feign.operation-server:gmis-operation}", fallback = OperationAcceptanceBizApiFallBack.class
        , path = "/operationAcceptance", qualifier = "OperationAcceptanceBizApi")
public interface OperationAcceptanceBizApi {
    /**
     * 运行维护分页列表查询
     * @param params
     * @return
     */
    @PostMapping(value = "/page")
    @SysLog(value = "'运维分页列表查询:第' + #params?.current + '页, 显示' + #params?.size + '行'", response = false)
    public R<Page<OperationAcceptance>> page(@RequestBody @Validated PageParams<OperationAcceptancePageDTO> params);

    /**
     * 新增运行维护受理信息
     * @param saveDTO
     * @return
     */
    @PostMapping
    @SysLog(value = "新增", request = false)
    public R<OperationAcceptance> save(@RequestBody @Validated OperationAcceptanceSaveDTO saveDTO);
}
