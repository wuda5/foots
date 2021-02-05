package com.cdqckj.gmis.common.domain.tenant;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.cdqckj.gmis.context.BaseContextHandler;
import io.seata.core.context.RootContext;
import io.seata.tm.api.GlobalTransaction;
import io.seata.tm.api.GlobalTransactionContext;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author: lijianguo
 * @time: 2020/10/20 11:04
 * @remark: 请输入类说明
 */
@Log4j2
public class GlobalTxThreadPoolDefault implements GlobalTxThreadPool {

    @Override
    public CompletionService<TenantResult> produceService(RealProcess process, List<MulTenant> mulTenantList, ThreadPoolExecutor cachedThreadPool) {

        GlobalTransaction globalTransaction = GlobalTransactionContext.getCurrentOrCreate();
        /** 线程池的并行 **/
        CompletionService<TenantResult> completionService =  new ExecutorCompletionService<TenantResult>(cachedThreadPool);
        for (MulTenant mulTenant : mulTenantList) {
            completionService.submit(new Callable<TenantResult>() {
                @Override
                public TenantResult call() throws Exception {
                    if (globalTransaction.getXid() != null) {
                        RootContext.bind(globalTransaction.getXid());
                    }else {
                        RootContext.unbind();
                    }
                    TenantResult tenantResult = new TenantResult(mulTenant.getCode(), mulTenant.getName());
                    Object threadResult;
                    try {
                        BaseContextHandler.setTenant(mulTenant.getCode());
                        DynamicDataSourceContextHolder.push(mulTenant.getCode());
                        threadResult = process.realProcess();
                        tenantResult.setResult(threadResult);
                        log.info("多租户的线程信息:{} {} {} getXid{}", Thread.currentThread().getName(),
                                Thread.currentThread().getId(), mulTenant.getCode(), globalTransaction.getXid());
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.error("threadPoolWithResult 处理失败", e.getMessage());
                        DynamicDataSourceContextHolder.poll();
                        tenantResult.setFailWithMsg(e.getMessage());
                    }
                    return tenantResult;
                }
            });
        }
        return completionService;
    }
}
