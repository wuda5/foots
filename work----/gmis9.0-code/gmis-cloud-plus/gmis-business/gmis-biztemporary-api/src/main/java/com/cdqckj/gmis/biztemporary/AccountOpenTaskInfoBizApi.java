package com.cdqckj.gmis.biztemporary;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.biztemporary.entity.AccountOpenTaskInfo;
import com.cdqckj.gmis.biztemporary.hystrix.AccountOpenTaskInfoBizApiFallback;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author songyz
 */
@FeignClient(name = "${gmis.feign.gmis-business-server:gmis-business-server}", fallback = AccountOpenTaskInfoBizApiFallback.class
        , path = "/accountOpenTaskInfo", qualifier = "AccountOpenTaskInfoBizApi")
public interface AccountOpenTaskInfoBizApi {
    /**
     * 加载最后的导入开户任务信息
     * @return
     */
    @ApiOperation(value = "加载最后的导入开户任务信息")
    @PostMapping("/queryImportTaskInfo")
    public R<AccountOpenTaskInfo> queryImportTaskInfo();
}
