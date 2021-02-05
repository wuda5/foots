package com.cdqckj.gmis.operateparam;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

/**
 * 模块信息
 * @author 65427
 */
@FeignClient(name = "${gmis.feign.operateparam-server:gmis-operateparam-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class, path = "/functionSwitchPlus")
public interface FunctionSwitchPlusBizApi {

    @ApiOperation(value = "获取一个一个配置项目")
    @GetMapping("/getSystemSetting")
    R<String> getSystemSetting(@RequestParam("item") String item);


    /**
     * @author hc
     * @date 2020/12/05
     * @return
     */
    @ApiOperation("获取所有配置:转化为map")
    @GetMapping("/getSysAllSettingMap")
    R<HashMap<String,Object>> getSysAllSettingMap();

    /**
     * @author hc
     * @date 2020/12/05
     * @param data
     * @return
     */
    @ApiOperation("更新所有配置")
    @PostMapping("/updateSysAllSetting")
    R<Boolean> updateSysAllSetting(@RequestBody HashMap<String,Object> data);
}
