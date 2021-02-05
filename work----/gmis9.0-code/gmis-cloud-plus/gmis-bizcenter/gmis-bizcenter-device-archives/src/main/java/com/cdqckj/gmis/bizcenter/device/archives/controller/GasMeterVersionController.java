package com.cdqckj.gmis.bizcenter.device.archives.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.bizcenter.device.archives.service.GasMeterVersionService;
import com.cdqckj.gmis.device.GasMeterVersionBizApi;
import com.cdqckj.gmis.gasmeter.dto.GasMeterVersionPageDTO;
import com.cdqckj.gmis.gasmeter.dto.GasMeterVersionSaveDTO;
import com.cdqckj.gmis.gasmeter.dto.GasMeterVersionUpdateDTO;
import com.cdqckj.gmis.gasmeter.entity.GasMeterVersion;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p>
 * 气表版本前端控制器
 * </p>
 * @author lmj
 * @date 2020-07-01
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/gasmeter/gasMeterVersion")
@Api(value = "gasMeterVersion", tags = "气表版号")
//@PreAuth(replace = "businessHall:")
public class GasMeterVersionController {

    @Autowired
    public GasMeterVersionBizApi gasMeterVersionBizApi;
    @Autowired
    public GasMeterVersionService gasMeterVersionService;

    @ApiOperation(value = "新增气表版号信息")
    @PostMapping("/add")
    public R<GasMeterVersion> saveGasMeterVersion(@RequestBody GasMeterVersionSaveDTO gasMeterVersionSaveDTO){

        return gasMeterVersionService.saveGasMeterVersion(gasMeterVersionSaveDTO);
    }

    @ApiOperation(value = "更新气表版号信息")
    @PutMapping("/update")
    public R<GasMeterVersion> updateGasMeterVersion(@RequestBody GasMeterVersionUpdateDTO gasMeterVersionUpdateDTO){
        return gasMeterVersionService.updateGasMeterVersion(gasMeterVersionUpdateDTO);
    }

    @ApiOperation(value = "批量更新气表版号信息")
    @PutMapping("/updateBatch")
    public R<Boolean> updateGasMeterVersion(@RequestBody List<GasMeterVersionUpdateDTO> upadateBatch){
        return gasMeterVersionService.updateBatchGasMeter(upadateBatch);
    }


    @ApiOperation(value = "分页查询气表版号信息")
    @PostMapping("/page")
    public R<Page<GasMeterVersion>> pageGasMeterVersion(@RequestBody PageParams<GasMeterVersionPageDTO> params){
        return gasMeterVersionBizApi.page(params);
    }

    @ApiOperation(value = "按条件查询气表版号信息")
    @PostMapping("/query")
    public R<List<GasMeterVersion>> queryGasMeterFactory(@RequestBody GasMeterVersion queryDTO){
        return gasMeterVersionBizApi.query(queryDTO);
    }


    @ApiOperation(value = "根据ID查询版号信息")
    @GetMapping("/getById/{id}")
    public R<GasMeterVersion> getById(@PathVariable(value = "id") Long id){
        return gasMeterVersionBizApi.get(id);
    }

}
