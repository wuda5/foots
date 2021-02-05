package com.cdqckj.gmis.securityed;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.log.annotation.SysLog;
import com.cdqckj.gmis.securityed.dto.SecurityCheckProcessSaveDTO;
import com.cdqckj.gmis.securityed.dto.SecurityCheckRecordSaveDTO;
import com.cdqckj.gmis.securityed.entity.SecurityCheckProcess;
import com.cdqckj.gmis.securityed.entity.SecurityCheckRecord;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "${gmis.feign.securityed-server:gmis-securityed-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/securityCheckProcess", qualifier = "securityCheckProcessApi")
public interface SecurityCheckProcessApi {
    @PostMapping
    R<SecurityCheckProcess> save(@RequestBody @Valid SecurityCheckProcessSaveDTO saveDTO);

    @PostMapping(value = "/saveList")
    R<SecurityCheckProcess> saveList(@RequestBody List<SecurityCheckProcessSaveDTO> list);

    @PostMapping("/query")
    R<List<SecurityCheckProcess>> query(@RequestBody SecurityCheckProcess data);
}
