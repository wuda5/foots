package com.cdqckj.gmis.biztemporary.hystrix;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.biztemporary.AccountOpenTaskInfoBizApi;
import com.cdqckj.gmis.biztemporary.entity.AccountOpenTaskInfo;
import org.springframework.stereotype.Component;

/**
 * @author songyz
 */
@Component
public class AccountOpenTaskInfoBizApiFallback implements AccountOpenTaskInfoBizApi {
    @Override
    public R<AccountOpenTaskInfo> queryImportTaskInfo() {
        return R.timeout();
    }
}
