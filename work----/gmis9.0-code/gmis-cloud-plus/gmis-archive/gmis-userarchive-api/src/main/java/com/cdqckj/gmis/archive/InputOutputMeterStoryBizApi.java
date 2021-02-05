package com.cdqckj.gmis.archive;


import com.cdqckj.gmis.archive.hystrix.InputOutputMeterStoryBizApiFallback;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.devicearchive.dto.InputOutputMeterStoryVo;
import com.cdqckj.gmis.devicearchive.entity.InputOutputMeterStory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "${gmis.feign.userarchive-server:gmis-userarchive-server}", fallback = InputOutputMeterStoryBizApiFallback.class
        , path = "/inputOutputMeterStory", qualifier = "inputOutputMeterStoryBizApi")
public interface InputOutputMeterStoryBizApi {
     @PostMapping(value = "/saveList")
     R<InputOutputMeterStory> saveList(@RequestBody List<InputOutputMeterStoryVo> list);
}
