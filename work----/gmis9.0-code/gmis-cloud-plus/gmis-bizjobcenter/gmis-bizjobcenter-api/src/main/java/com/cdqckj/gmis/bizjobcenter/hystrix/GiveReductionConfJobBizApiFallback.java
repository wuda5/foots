package com.cdqckj.gmis.bizjobcenter.hystrix;

import com.cdqckj.gmis.bizjobcenter.GiveReductionConfJobBizApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class GiveReductionConfJobBizApiFallback implements GiveReductionConfJobBizApi {
    @Override
    public void updateGiveReductionStatus() {
        log.info("修改赠送活动状态服务接口失败");
    }
}
