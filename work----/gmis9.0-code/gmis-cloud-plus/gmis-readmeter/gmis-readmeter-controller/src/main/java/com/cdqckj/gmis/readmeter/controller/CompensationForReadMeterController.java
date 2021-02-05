package com.cdqckj.gmis.readmeter.controller;

import com.cdqckj.gmis.readmeter.entity.CompensationForReadMeter;
import com.cdqckj.gmis.readmeter.dto.CompensationForReadMeterSaveDTO;
import com.cdqckj.gmis.readmeter.dto.CompensationForReadMeterUpdateDTO;
import com.cdqckj.gmis.readmeter.dto.CompensationForReadMeterPageDTO;
import com.cdqckj.gmis.readmeter.service.CompensationForReadMeterService;
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
 * 抄表调价补差信息
 * </p>
 *
 * @author gmis
 * @date 2020-07-13
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/compensationForReadMeter")
@Api(value = "CompensationForReadMeter", tags = "抄表调价补差信息")
@PreAuth(replace = "compensationForReadMeter:")
public class CompensationForReadMeterController extends SuperController<CompensationForReadMeterService, Long, CompensationForReadMeter, CompensationForReadMeterPageDTO, CompensationForReadMeterSaveDTO, CompensationForReadMeterUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<CompensationForReadMeter> compensationForReadMeterList = list.stream().map((map) -> {
            CompensationForReadMeter compensationForReadMeter = CompensationForReadMeter.builder().build();
            //TODO 请在这里完成转换
            return compensationForReadMeter;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(compensationForReadMeterList));
    }
}
