package com.cdqckj.gmis.archive.hystrix;

import com.cdqckj.gmis.archive.InputOutputMeterStoryBizApi;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.devicearchive.dto.InputOutputMeterStoryVo;
import com.cdqckj.gmis.devicearchive.entity.InputOutputMeterStory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InputOutputMeterStoryBizApiFallback implements InputOutputMeterStoryBizApi {
    @Override
    public R<InputOutputMeterStory> saveList(List<InputOutputMeterStoryVo> list) {
        return R.timeout();
    }
}
