package com.cdqckj.gmis.devicearchive.controller;

import com.cdqckj.gmis.devicearchive.entity.BatchGas;
import com.cdqckj.gmis.devicearchive.dto.BatchGasSaveDTO;
import com.cdqckj.gmis.devicearchive.dto.BatchGasUpdateDTO;
import com.cdqckj.gmis.devicearchive.dto.BatchGasPageDTO;
import com.cdqckj.gmis.devicearchive.service.BatchGasService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.R;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cdqckj.gmis.security.annotation.PreAuth;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 * 前端控制器
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-08-14
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/batchGas")
@Api(value = "BatchGas", tags = "批量建档模块")
//@PreAuth(replace = "batchGas:")
public class BatchGasController extends SuperController<BatchGasService, Long, BatchGas, BatchGasPageDTO, BatchGasSaveDTO, BatchGasUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<BatchGas> batchGasList = list.stream().map((map) -> {
            BatchGas batchGas = BatchGas.builder().build();
            //TODO 请在这里完成转换
            return batchGas;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(batchGasList));
    }
}
