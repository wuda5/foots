package com.cdqckj.gmis.lot;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.iot.qc.entity.IotGasMeterUploadData;
import com.cdqckj.gmis.iot.qc.vo.DayDataParamsVO;
import com.cdqckj.gmis.iot.qc.vo.DayDataVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "${gmis.feign.iot-qc-server:gmis-iot-qc-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/meter", qualifier = "IotGasMeterUploadDataBizApi")
public interface IotGasMeterUploadDataBizApi {

    @PostMapping("/queryOne")
    R<IotGasMeterUploadData> queryOne(@RequestBody IotGasMeterUploadData query);

    @GetMapping(value = "/queryActivateMeter")
    IotGasMeterUploadData queryActivateMeter(@RequestParam(value = "gasMeterNumber")String gasMeterNumber);

    @PostMapping("/queryData")
    R<Page<DayDataVO>> queryData(@RequestBody DayDataParamsVO params);
}
