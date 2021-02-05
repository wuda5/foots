package com.cdqckj.gmis.calculate.controller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.calculate.AdjustPriceProcessService;
import com.cdqckj.gmis.charges.dto.AdjustPriceProcessPageDTO;
import com.cdqckj.gmis.charges.dto.AdjustPriceProcessSaveDTO;
import com.cdqckj.gmis.charges.dto.AdjustPriceProcessUpdateDTO;
import com.cdqckj.gmis.charges.entity.AdjustPriceProcess;
import com.cdqckj.gmis.security.annotation.PreAuth;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <p>
 * 前端控制器
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-11-09
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/adjustPriceProcess")
@Api(value = "AdjustPriceProcess", tags = "调价补差流程信息")
@PreAuth(replace = "adjustPriceProcess:")
public class AdjustPriceProcessController extends SuperController<AdjustPriceProcessService, Long, AdjustPriceProcess, AdjustPriceProcessPageDTO, AdjustPriceProcessSaveDTO, AdjustPriceProcessUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<AdjustPriceProcess> adjustPriceProcessList = list.stream().map((map) -> {
            AdjustPriceProcess adjustPriceProcess = AdjustPriceProcess.builder().build();
            //TODO 请在这里完成转换
            return adjustPriceProcess;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(adjustPriceProcessList));
    }
}
