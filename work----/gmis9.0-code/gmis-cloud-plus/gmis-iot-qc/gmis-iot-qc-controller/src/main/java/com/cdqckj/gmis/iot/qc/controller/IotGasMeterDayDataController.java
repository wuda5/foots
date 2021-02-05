package com.cdqckj.gmis.iot.qc.controller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.iot.qc.dto.IotGasMeterDayDataPageDTO;
import com.cdqckj.gmis.iot.qc.dto.IotGasMeterDayDataSaveDTO;
import com.cdqckj.gmis.iot.qc.dto.IotGasMeterDayDataUpdateDTO;
import com.cdqckj.gmis.iot.qc.entity.IotGasMeterDayData;
import com.cdqckj.gmis.iot.qc.service.IotGasMeterDayDataService;
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
 * @date 2020-10-15
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/iotGasMeterDayData")
@Api(value = "IotGasMeterDayData", tags = "")
@PreAuth(replace = "iotGasMeterDayData:")
public class IotGasMeterDayDataController extends SuperController<IotGasMeterDayDataService, Long, IotGasMeterDayData, IotGasMeterDayDataPageDTO, IotGasMeterDayDataSaveDTO, IotGasMeterDayDataUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<IotGasMeterDayData> iotGasMeterDayDataList = list.stream().map((map) -> {
            IotGasMeterDayData iotGasMeterDayData = IotGasMeterDayData.builder().build();
            //TODO 请在这里完成转换
            return iotGasMeterDayData;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(iotGasMeterDayDataList));
    }
}
