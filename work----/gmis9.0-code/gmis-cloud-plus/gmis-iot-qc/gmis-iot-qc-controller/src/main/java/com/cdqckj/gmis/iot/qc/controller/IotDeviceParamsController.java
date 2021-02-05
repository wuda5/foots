package com.cdqckj.gmis.iot.qc.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.iot.qc.dto.IotDeviceParamsPageDTO;
import com.cdqckj.gmis.iot.qc.dto.IotDeviceParamsSaveDTO;
import com.cdqckj.gmis.iot.qc.dto.IotDeviceParamsUpdateDTO;
import com.cdqckj.gmis.iot.qc.entity.IotDeviceParams;
import com.cdqckj.gmis.iot.qc.service.IotDeviceParamsService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cdqckj.gmis.security.annotation.PreAuth;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 * 前端控制器
 * 物联网设备参数设置记录表
 * </p>
 *
 * @author gmis
 * @date 2020-12-08
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/iotDeviceParams")
@Api(value = "IotDeviceParams", tags = "物联网设备参数设置记录表")
//@PreAuth(replace = "iotDeviceParams:")
public class IotDeviceParamsController extends SuperController<IotDeviceParamsService, Long, IotDeviceParams, IotDeviceParamsPageDTO, IotDeviceParamsSaveDTO, IotDeviceParamsUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<IotDeviceParams> iotDeviceParamsList = list.stream().map((map) -> {
            IotDeviceParams iotDeviceParams = IotDeviceParams.builder().build();
            //TODO 请在这里完成转换
            return iotDeviceParams;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(iotDeviceParamsList));
    }

    /**
     * 通过表身号集合查询
     *
     * @param gasMeterNumbers
     * @return
     */
    @PostMapping("/queryByNumbers")
    public R<List<IotDeviceParams>> queryByNumbers(@RequestBody List<String> gasMeterNumbers){
        LbqWrapper<IotDeviceParams> wp = Wraps.<IotDeviceParams>lbQ()
                .in(IotDeviceParams::getGasMeterNumber, gasMeterNumbers);

        return  R.success(this.getBaseService().list(wp)) ;
    }
}
