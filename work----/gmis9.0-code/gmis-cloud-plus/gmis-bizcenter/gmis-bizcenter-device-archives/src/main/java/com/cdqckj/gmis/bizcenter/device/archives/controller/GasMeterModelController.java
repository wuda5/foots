package com.cdqckj.gmis.bizcenter.device.archives.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.bizcenter.device.archives.service.GasMeterModelService;
import com.cdqckj.gmis.device.GasMeterModelBizApi;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.gasmeter.dto.GasMeterModelPageDTO;
import com.cdqckj.gmis.gasmeter.dto.GasMeterModelSaveDTO;
import com.cdqckj.gmis.gasmeter.dto.GasMeterModelUpdateDTO;
import com.cdqckj.gmis.gasmeter.entity.GasMeterModel;
import com.cdqckj.gmis.utils.BeanPlusUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p>
 * 气表型号管理前端控制器
 * </p>
 * @author lmj
 * @date 2020-07-01
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/gasmeter/GasMeterModel")
@Api(value = "GasMeterModel", tags = "气表型号")
public class GasMeterModelController {

    @Autowired
    public GasMeterModelBizApi userGasMeterVersionBizApi;

    @Autowired
    private GasMeterModelBizApi gasMeterModelBizApi;
    @Autowired
    public GasMeterModelService gasMeterModelService;

    @ApiOperation(value = "新增气表型号信息")
    @PostMapping("/add")
    public R<GasMeterModel> saveGasMeterModel(@RequestBody GasMeterModelSaveDTO userGasMeterVersionSaveDTO){
        /*
        * TODO  修改后 没交验
        * */
        Boolean two = gasMeterModelBizApi.queryGasMeter(BeanPlusUtil.toBean(userGasMeterVersionSaveDTO, GasMeterModel.class)).getData();
        if (two){
            throw new BizException("同一厂商下的版本号下，规格名同时，型号名不可重复");
        }
        return gasMeterModelBizApi.save(userGasMeterVersionSaveDTO);
    }

    @ApiOperation(value = "更改气表型号状态")
    @PutMapping("/update")
    public R<GasMeterModel> updateGasMeterModel(@RequestBody GasMeterModelUpdateDTO userGasMeterVersionUpdateDTO){
        /*
         * TODO  修改后 没交验
         * */
        Boolean two = gasMeterModelBizApi.queryGasMeter(BeanPlusUtil.toBean(userGasMeterVersionUpdateDTO, GasMeterModel.class)).getData();
        if (two){
            throw new BizException("同一厂商下的版本号下，规格名同时，型号名不可重复");
        }
        return gasMeterModelBizApi.update(userGasMeterVersionUpdateDTO);
    }

    @ApiOperation(value = "批量更新气表型号信息")
    @PutMapping("/updateBatch")
    public R<Boolean> updateGasMeterModel(@RequestBody List<GasMeterModelUpdateDTO> upadateBatch){
        return gasMeterModelService.updateBatchModel(upadateBatch);
    }

    @ApiOperation(value = "分页查询气表型号信息")
    @PostMapping("/page")
    public R<Page<GasMeterModel>> pageGasMeterModel(@RequestBody PageParams<GasMeterModelPageDTO> params){

        R<Page<GasMeterModel>> daR = userGasMeterVersionBizApi.page(params);
        for(GasMeterModel item : daR.getData().getRecords()){
            if(null!=item.getMaxWordWheel()){
                item.setMaxWordWheel(item.getMaxWordWheel().stripTrailingZeros());
            }
        }
        return daR;
    }

    @ApiOperation(value = "按条件查询气表型号信息")
    @PostMapping("/query")
    public R<List<GasMeterModel>> queryGasMeterModel(@RequestBody GasMeterModel queryDTO){
        return userGasMeterVersionBizApi.query(queryDTO);
    }
    @ApiOperation(value = "根据ID查询型号信息")
    @GetMapping("/getById/{id}")
    public R<GasMeterModel> getById(@PathVariable(value = "id") Long id){
        return userGasMeterVersionBizApi.get(id);
    }
}
