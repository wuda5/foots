package com.cdqckj.gmis.lot;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.IotR;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.iot.qc.dto.IotAlarmSaveDTO;
import com.cdqckj.gmis.iot.qc.dto.IotGasMeterCommandDetailSaveDTO;
import com.cdqckj.gmis.iot.qc.entity.IotAlarm;
import com.cdqckj.gmis.iot.qc.entity.IotGasMeterCommandDetail;
import com.cdqckj.gmis.iot.qc.vo.CommandEchoVO;
import com.cdqckj.gmis.iot.qc.vo.CommandVO;
import com.cdqckj.gmis.iot.qc.vo.RetryVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "${gmis.feign.iot-qc-server:gmis-iot-qc-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/iotGasMeterCommandDetail", qualifier = "iotGasMeterCommandDetailBizApi")
public interface IotGasMeterCommandDetailBizApi {
    @PostMapping
    R<IotGasMeterCommandDetail> save(@RequestBody IotGasMeterCommandDetailSaveDTO saveDTO);
}
