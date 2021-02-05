package com.cdqckj.gmis.gasmeter.controller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.update.LbuWrapper;
import com.cdqckj.gmis.gasmeter.dto.GasMeterModelPageDTO;
import com.cdqckj.gmis.gasmeter.dto.GasMeterModelSaveDTO;
import com.cdqckj.gmis.gasmeter.dto.GasMeterModelUpdateDTO;
import com.cdqckj.gmis.gasmeter.entity.GasMeterModel;
import com.cdqckj.gmis.gasmeter.entity.GasMeterVersion;
import com.cdqckj.gmis.gasmeter.service.GasMeterModelService;
import com.cdqckj.gmis.security.annotation.PreAuth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <p>
 * 前端控制器
 * 气表型号
 * </p>
 *
 * @author gmis
 * @date 2020-08-24
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/gasMeterModel")
@Api(value = "GasMeterModel", tags = "气表型号")
@PreAuth(replace = "gasMeterModel:")
public class GasMeterModelController extends SuperController<GasMeterModelService, Long, GasMeterModel, GasMeterModelPageDTO, GasMeterModelSaveDTO, GasMeterModelUpdateDTO> {

    @ApiOperation(value = "禁用此厂商下面的 型号（先禁用了厂商下的版号后）,0禁用，1开启 ???")
    @PostMapping("/updateModelStatusByFactoryIds")
    R<Boolean> updateModelStatusByFactoryIds(@RequestParam("factoryIds[]") List<Long> factoryIds, @RequestParam("setModelState") int setModeState){
            LbuWrapper<GasMeterModel> wrapper = Wraps.lbU();
            wrapper.set(GasMeterModel::getDataStatus,setModeState)
                    .in(GasMeterModel::getCompanyId,factoryIds);
            baseService.update(wrapper);
            return R.success();
        }
    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<GasMeterModel> gasMeterModelList = list.stream().map((map) -> {
            GasMeterModel gasMeterModel = GasMeterModel.builder().build();
            //TODO 请在这里完成转换
            return gasMeterModel;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(gasMeterModelList));
    }

    /**
     * 查询型号
     * @param gasMeterModel
     * @return
     */
    @ApiOperation(value = "查询型号 当前状态")
    @PostMapping("/queryGasMeter")
    R<Boolean> queryGasMeter(@RequestBody GasMeterModel gasMeterModel) {
        return success(this.baseService.queryGasMeterModelCheck(gasMeterModel));
    }

    /**
     * 查询型号
     * @param gasMeterModel
     * @return
     */
    @ApiOperation(value = "查询型号")
    @PostMapping("/queryModel")
    R<GasMeterModel> queryModel(@RequestBody GasMeterModel gasMeterModel) {
        return success(this.baseService.queryModel(gasMeterModel));
    }
}
