package com.cdqckj.gmis.iot.qc.controller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.iot.qc.dto.IotGasMeterCommandDetailPageDTO;
import com.cdqckj.gmis.iot.qc.dto.IotGasMeterCommandDetailSaveDTO;
import com.cdqckj.gmis.iot.qc.dto.IotGasMeterCommandDetailUpdateDTO;
import com.cdqckj.gmis.iot.qc.entity.IotGasMeterCommandDetail;
import com.cdqckj.gmis.iot.qc.service.IotGasMeterCommandDetailService;
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
 * 物联网气表指令明细数据
 * </p>
 *
 * @author gmis
 * @date 2020-10-15
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/iotGasMeterCommandDetail")
@Api(value = "IotGasMeterCommandDetail", tags = "物联网气表指令明细数据")
@PreAuth(replace = "iotGasMeterCommandDetail:")
public class IotGasMeterCommandDetailController extends SuperController<IotGasMeterCommandDetailService, Long, IotGasMeterCommandDetail, IotGasMeterCommandDetailPageDTO, IotGasMeterCommandDetailSaveDTO, IotGasMeterCommandDetailUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<IotGasMeterCommandDetail> iotGasMeterCommandDetailList = list.stream().map((map) -> {
            IotGasMeterCommandDetail iotGasMeterCommandDetail = IotGasMeterCommandDetail.builder().build();
            //TODO 请在这里完成转换
            return iotGasMeterCommandDetail;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(iotGasMeterCommandDetailList));
    }
}
