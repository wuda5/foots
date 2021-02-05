package com.cdqckj.gmis.securityed;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.entity.SuperEntity;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.log.annotation.SysLog;
import com.cdqckj.gmis.securityed.dto.*;
import com.cdqckj.gmis.securityed.entity.SecurityCheckItems;
import com.cdqckj.gmis.securityed.entity.SecurityCheckResult;
import com.cdqckj.gmis.securityed.vo.ScItemsOperVo;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "${gmis.feign.securityed-server:gmis-securityed-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/securityCheckItems", qualifier = "securityCheckItemsApi")
public interface SecurityCheckItemsApi {

    @PostMapping(value = "/saveList")
    R<SecurityCheckItems> saveList(@RequestBody List<SecurityCheckItemsSaveDTO> list);

    @PutMapping
    R<SecurityCheckItems> update(@RequestBody @Validated(SuperEntity.Update.class) SecurityCheckItemsUpdateDTO updateDTO);

    @PostMapping(value = "/page")
    R<Page<SecurityCheckItems>> page(@RequestBody @Validated PageParams<SecurityCheckItemsPageDTO> params);

    @PutMapping(value = "/updateBatch")
    @GlobalTransactional
     R<Boolean> updateBatchById(@RequestBody List<SecurityCheckItemsUpdateDTO> list);

    /**
     * 条件查询
     * @return
     */
    @PostMapping("/query")
    R<List<SecurityCheckItems>> query(@RequestBody SecurityCheckItems query);

    @PostMapping("/queryOne")
    R<SecurityCheckItems> queryOne(@RequestBody SecurityCheckItems data);

    /**
     * wu 根据 安检计划编号 获取对应 安检隐患列表信息
     *
     * @return
     */
    @ApiOperation(value = "根据 安检计划编号scNo 获取对应 安检隐患列表信息")
    @GetMapping("/findChecks")
    public R<List<ScItemsOperVo>> findFilesByInstallNumber(@RequestParam(name = "scNo") String scNo);

    @ApiOperation(value = "条件删除（真实删除）")
    @DeleteMapping(value = "/deleteReal")
    R<Boolean> deleteReal(@RequestBody SecurityCheckItems entity);
}
