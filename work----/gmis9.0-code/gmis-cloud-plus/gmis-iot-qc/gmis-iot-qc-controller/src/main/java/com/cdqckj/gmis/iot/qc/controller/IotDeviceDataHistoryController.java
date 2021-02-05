package com.cdqckj.gmis.iot.qc.controller;

import com.cdqckj.gmis.base.IotR;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.iot.qc.dto.IotDeviceDataHistoryPageDTO;
import com.cdqckj.gmis.iot.qc.dto.IotDeviceDataHistorySaveDTO;
import com.cdqckj.gmis.iot.qc.dto.IotDeviceDataHistoryUpdateDTO;
import com.cdqckj.gmis.iot.qc.entity.IotDeviceDataHistory;
import com.cdqckj.gmis.iot.qc.service.IotDeviceDataHistoryService;
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
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-10-14
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/receive")
@Api(value = "IotDeviceDataHistory", tags = "接收qnms3.0上报的数据")
public class IotDeviceDataHistoryController extends SuperController<IotDeviceDataHistoryService, Long, IotDeviceDataHistory, IotDeviceDataHistoryPageDTO, IotDeviceDataHistorySaveDTO, IotDeviceDataHistoryUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<IotDeviceDataHistory> iotDeviceDataHistoryList = list.stream().map((map) -> {
            IotDeviceDataHistory iotDeviceDataHistory = IotDeviceDataHistory.builder().build();
            //TODO 请在这里完成转换
            return iotDeviceDataHistory;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(iotDeviceDataHistoryList));
    }

    @ApiOperation(value = "推送过来的数据(由传感系统3.0调用)", notes = "推送过来的数据(由传感系统3.0调用)")
    @PostMapping(value = "/data")
    public IotR receiveData(@RequestBody Map<String,Object> map) throws Exception{
        return baseService.receiveData(map);
    }

}
