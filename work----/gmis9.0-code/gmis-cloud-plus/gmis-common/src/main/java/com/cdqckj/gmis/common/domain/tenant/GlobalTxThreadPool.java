package com.cdqckj.gmis.common.domain.tenant;

import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author: lijianguo
 * @time: 2020/10/15 11:22
 * @remark: 请输入接口说明
 */
public interface GlobalTxThreadPool {

    /**
     * @auth lijianguo
     * @date 2020/10/15 11:22
     * @remark 基于多线程的实现
     */
    CompletionService<TenantResult> produceService(RealProcess process, List<MulTenant> mulTenantList, ThreadPoolExecutor cachedThreadPool);
}
