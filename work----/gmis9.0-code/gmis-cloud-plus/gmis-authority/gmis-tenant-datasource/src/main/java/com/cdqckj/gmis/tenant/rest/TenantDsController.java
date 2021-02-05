package com.cdqckj.gmis.tenant.rest;

import com.cdqckj.gmis.tenant.service.DataSourceService;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.tenant.dto.DataSourceSaveDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * 数据源初始化
 *
 * @author gmis
 * @date 2020年04月05日16:34:04
 */
@Slf4j
@RestController
@RequestMapping("/ds")
@Api(value = "TenantDs", tags = "数据源")
public class TenantDsController {

    @Autowired
    private DataSourceService dataSourceService;

    @ApiOperation(value = "初始化数据源", notes = "初始化数据源")
    @GetMapping(value = "/init")
    public R<Boolean> init(@RequestParam("tenant") String tenant) {
        dataSourceService.initDataSource(tenant);
        return R.success(true);
    }

    @GetMapping(value = "/remove")
    @ApiOperation("删除数据源")
    public R<Set<String>> remove(@RequestParam("tenant") String tenant) {
        return R.success(dataSourceService.remove(tenant));
    }

    @GetMapping
    @ApiOperation("获取当前所有数据源")
    public R<Set<String>> list() {
        return R.success(dataSourceService.all());
    }

    @PostMapping
    @ApiOperation("基础Druid数据源")
    public R<Set<String>> saveDruid(@Validated @RequestBody DataSourceSaveDTO dto) {
        return R.success(dataSourceService.saveDruid(dto));
    }

}
