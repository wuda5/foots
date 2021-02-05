package com.cdqckj.gmis.gasmeter.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.gasmeter.dto.GasMeterFactoryPageDTO;
import com.cdqckj.gmis.gasmeter.dto.GasMeterFactorySaveDTO;
import com.cdqckj.gmis.gasmeter.dto.GasMeterFactoryUpdateDTO;
import com.cdqckj.gmis.gasmeter.entity.GasMeterFactory;
import com.cdqckj.gmis.gasmeter.service.GasMeterFactoryService;
import com.cdqckj.gmis.security.annotation.PreAuth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <p>
 * 前端控制器
 * 气表厂家
 * </p>
 *
 * @author gmis
 * @date 2020-08-24
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/gasMeterFactory")
@Api(value = "GasMeterFactory", tags = "气表厂家")
@PreAuth(replace = "gasMeterFactory:")
public class GasMeterFactoryController extends SuperController<GasMeterFactoryService, Long, GasMeterFactory, GasMeterFactoryPageDTO, GasMeterFactorySaveDTO, GasMeterFactoryUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<GasMeterFactory> gasMeterFactoryList = list.stream().map((map) -> {
            GasMeterFactory gasMeterFactory = GasMeterFactory.builder().build();
            //TODO 请在这里完成转换
            return gasMeterFactory;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(gasMeterFactoryList));
    }

    @ApiOperation(value = "查询当前状态厂家")
    @PostMapping("/queryGasMeter")
    R<Boolean> queryGasMeter(@RequestBody GasMeterFactory gasMeterFactory) {
        return success(this.baseService.queryGasMeter(gasMeterFactory));
    }
    @ApiOperation(value = "根据名称查询厂家")
    @PostMapping("/queryFactoryByName")
    R<GasMeterFactory> queryFactoryByName(@RequestBody GasMeterFactory gasMeterFactory) {
        return success(this.baseService.queryFactoryByName(gasMeterFactory));
    }

    @Override
    public R<IPage<GasMeterFactory>> page(@RequestBody PageParams<GasMeterFactoryPageDTO> params) {
        return R.success(baseService.pageQuery(params));
    }
}
