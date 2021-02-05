package com.cdqckj.gmis.readmeter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.entity.SuperEntity;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.readmeter.dto.*;
import com.cdqckj.gmis.readmeter.entity.ReadMeterDataIot;
import com.cdqckj.gmis.readmeter.entity.ReadMeterDataIotError;
import com.cdqckj.gmis.readmeter.entity.ReadMeterPlanScope;
import com.cdqckj.gmis.readmeter.enumeration.ProcessEnum;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 抄表导入
 * @author 65427
 */
@FeignClient(name = "${gmis.feign.readmeter-server:gmis-readmeter-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/readMeterDataIotError", qualifier = "readMeterDataIotErrorApi")
public interface ReadMeterDataIotErrorApi {

    @PostMapping
    R<ReadMeterDataIotErrorSaveDTO> addIotError(@RequestBody ReadMeterDataIotErrorSaveDTO readMeterDataIotErrorSaveDTO);

    @GetMapping(value = "/queryByGasMeterNumber")
    R<ReadMeterDataIotError> queryByGasMeterNumber(@RequestParam(value = "gasMeterNumber") String gasMeterNumber);

    @PutMapping
    R<ReadMeterDataIotError> update(@RequestBody ReadMeterDataIotErrorUpdateDTO updateDTO);

    /**
     * @param updateBatch
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT,value = "/updateBatch")
    R<Boolean> updateBatch(@RequestBody List<ReadMeterDataIotErrorUpdateDTO> updateBatch);

    /**
     *
     * @param params
     * @return
     */
    @PostMapping(value = "/page")
    R<Page<ReadMeterDataIotError>> page(@RequestBody PageParams<ReadMeterDataIotErrorPageDTO> params);

    @PostMapping(value = "/queryByMeterNo")
    List<ReadMeterDataIotError> queryByMeterNo(@RequestBody List<String> meterNoList);

    /**
     * 批量更新
     * @param list
     * @return
     */
    @PostMapping(value = "/updateBathMeter")
    Boolean updateBathMeter(@RequestBody List<ReadMeterDataIotError> list);
}
