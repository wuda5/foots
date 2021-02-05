package com.cdqckj.gmis.bizcenter.device.archives.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.bizcenter.device.archives.service.GasMeterFactoryService;
import com.cdqckj.gmis.device.GasMeterFactoryBizApi;
import com.cdqckj.gmis.gasmeter.dto.GasMeterFactoryPageDTO;
import com.cdqckj.gmis.gasmeter.dto.GasMeterFactorySaveDTO;
import com.cdqckj.gmis.gasmeter.dto.GasMeterFactoryUpdateDTO;
import com.cdqckj.gmis.gasmeter.entity.GasMeterFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p>
 * 气表工厂前端控制器
 * </p>
 * @author gmis
 * @date 2020-07-01
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/gasmeter/gasMeterFactory")
@Api(value = "gasMeterFactory", tags = "气表厂家")
//@PreAuth(replace = ":")
public class GasMeterFactoryController {

    @Autowired
    public GasMeterFactoryBizApi gasMeterFactoryBizApi;
    @Autowired
    public GasMeterFactoryService gasMeterFactoryService;

    @ApiOperation(value = "新增气表厂家信息")
    @PostMapping("/add")
    public R<GasMeterFactory> saveGasMeterFactory(@RequestBody GasMeterFactorySaveDTO gasMeterFactorySaveDTO){
        return gasMeterFactoryService.saveGasMeter(gasMeterFactorySaveDTO);
    }

    @ApiOperation(value = "更新气表厂家信息")
    @PutMapping("/update")
    public R<GasMeterFactory> updateGasMeterFactory(@RequestBody GasMeterFactoryUpdateDTO gasMeterFactoryUpdateDTO){
        return gasMeterFactoryService.updateGasMeter(gasMeterFactoryUpdateDTO);
    }

    @ApiOperation(value = "批量更新气表厂家信息")
    @PutMapping("/updateBatch")
    public R<Boolean> updateGasMeterFactory(@RequestBody List<GasMeterFactoryUpdateDTO> upadateBatch){
        return gasMeterFactoryService.updateBatchGasMeter(upadateBatch);
    }

    @ApiOperation(value = "分页查询气表厂家信息")
    @PostMapping("/page")
    public R<Page<GasMeterFactory>> pageGasMeterFactory(@RequestBody PageParams<GasMeterFactoryPageDTO> params){
        return gasMeterFactoryBizApi.page(params);
    }

    @ApiOperation(value = "按条件查询气表厂家信息")
    @PostMapping("/query")
    public R<List<GasMeterFactory>> queryGasMeterFactory(@RequestBody GasMeterFactory queryDTO){
        return gasMeterFactoryBizApi.query(queryDTO);
    }

    @ApiOperation(value = "根据ID查询厂家信息")
    @GetMapping("/getById/{id}")
    public R<GasMeterFactory> getById(@PathVariable(value = "id") Long id){
        return gasMeterFactoryBizApi.get(id);
    }

    /**
     * @author hc
     * @param gasMeterFactory
     * @return
     */
    @ApiOperation(value = "厂家重复性校验")
    @PostMapping("/check")
    public R<Boolean> gasMeterFactoryCheck(@RequestBody GasMeterFactory gasMeterFactory){


        return R.success(gasMeterFactoryService.gasMeterFactoryCheck(gasMeterFactory));
    }
}
