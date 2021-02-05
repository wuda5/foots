package com.cdqckj.gmis.archive;

import com.cdqckj.gmis.archive.hystrix.CustomerGasMeterBindBizApiFallback;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.devicearchive.dto.CustomerGasMeterBindSaveDTO;
import com.cdqckj.gmis.devicearchive.dto.CustomerGasMeterBindUpdateDTO;
import com.cdqckj.gmis.devicearchive.dto.GasMeterBindPrame;
import com.cdqckj.gmis.devicearchive.dto.GasMeterBindResult;
import com.cdqckj.gmis.devicearchive.entity.CustomerGasMeterBind;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "${gmis.feign.userarchive-server:gmis-userarchive-server}", fallback = CustomerGasMeterBindBizApiFallback.class
        , path = "/customerGasMeterBind", qualifier = "customerGasMeterBindBiz")
public interface CustomerGasMeterBindBizApi {
    @PostMapping(value = "/saveList")
    R<CustomerGasMeterBind> saveList(@RequestBody List<CustomerGasMeterBindSaveDTO> list) ;

    @RequestMapping(method = RequestMethod.PUT)
    R<CustomerGasMeterBind> update(@RequestBody CustomerGasMeterBindUpdateDTO updateDTO);

    @PostMapping ("/getGasMeterInfo")
    R<List<GasMeterBindResult>> getGasMeterInfo(@RequestBody GasMeterBindPrame gasMeterBindPrame);

}
