package com.cdqckj.gmis.securityed;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.entity.SuperEntity;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.log.annotation.SysLog;
import com.cdqckj.gmis.securityed.dto.*;
import com.cdqckj.gmis.securityed.entity.SecurityCheckResult;
import com.cdqckj.gmis.securityed.vo.AppCkResultVo;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "${gmis.feign.securityed-server:gmis-securityed-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/securityCheckResult", qualifier = "securityCheckResultApi")
public interface SecurityCheckResultApi {
    @PostMapping(value = "/saveList")
    R<SecurityCheckResult> saveList(@RequestBody List<SecurityCheckResultSaveDTO> list);

    @PostMapping
    R<SecurityCheckResult> save(@RequestBody @Valid SecurityCheckResultSaveDTO saveDTO);

    @PostMapping(value = "/page")
    R<Page<SecurityCheckResult>> page(@RequestBody @Validated PageParams<SecurityCheckResultPageDTO> params);

    @PutMapping
    R<SecurityCheckResult> update(@RequestBody @Validated(SuperEntity.Update.class) SecurityCheckResultUpdateDTO updateDTO);

    @PostMapping("/queryOne")
    R<SecurityCheckResult> queryOne(@RequestBody SecurityCheckResult query);


    @ApiOperation(value = "条件删除（真实删除）")
    @DeleteMapping(value = "/deleteReal")
     R<Boolean> deleteReal(@RequestBody SecurityCheckResult entity);


    @ApiOperation(value = "录入或编辑安检结果（含安全隐患列表信息）")
    @PostMapping(value = "/saveCheckResult")
    R<SecurityCheckResult> saveCheckResult(@RequestBody AppCkResultVo params);


}
