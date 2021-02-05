package com.cdqckj.gmis.biztemporary.controller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.biztemporary.dto.GasMeterTempPageDTO;
import com.cdqckj.gmis.biztemporary.dto.GasMeterTempSaveDTO;
import com.cdqckj.gmis.biztemporary.dto.GasMeterTempUpdateDTO;
import com.cdqckj.gmis.biztemporary.entity.GasMeterTemp;
import com.cdqckj.gmis.biztemporary.service.GasMeterTempService;
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
 * 气表档案临时表
 * </p>
 *
 * @author songyz
 * @date 2021-01-04
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/gasMeterTemp")
@Api(value = "GasMeterTemp", tags = "气表档案临时表")
@PreAuth(replace = "gasMeterTemp:")
public class GasMeterTempController extends SuperController<GasMeterTempService, Long, GasMeterTemp, GasMeterTempPageDTO, GasMeterTempSaveDTO, GasMeterTempUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<GasMeterTemp> gasMeterTempList = list.stream().map((map) -> {
            GasMeterTemp gasMeterTemp = GasMeterTemp.builder().build();
            //TODO 请在这里完成转换
            return gasMeterTemp;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(gasMeterTempList));
    }

    /**
     * 校验
     * @param params
     * @return
     */
    @ApiOperation("校验")
    @PostMapping("/check")
    public Boolean check(@RequestBody GasMeterTemp params){
        return baseService.check(params);
    }
}
