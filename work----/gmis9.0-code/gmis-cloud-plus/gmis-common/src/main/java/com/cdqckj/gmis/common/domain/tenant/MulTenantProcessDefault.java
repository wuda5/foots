package com.cdqckj.gmis.common.domain.tenant;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.cdqckj.gmis.common.key.RedisCommonKey;
import com.cdqckj.gmis.context.BaseContextHandler;
import io.seata.core.context.RootContext;
import io.seata.tm.api.GlobalTransaction;
import io.seata.tm.api.GlobalTransactionContext;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author: lijianguo
 * @time: 2020/9/29 18:09
 * @remark: 处理多租户的工具类，可以选择1）线程池 2)本地线程 等各种方式处理
 */
@Log4j2
public class MulTenantProcessDefault implements MulTenantProcess {

    /** 租户信息 **/
    private MulTenantData mulTenantData;

    /** 缓存 **/
    private RedisTemplate redisTemplate;

    /** 缓存 **/
    private GlobalTxThreadPool globalTransactionThreadPool;

    /** 线程池 **/
    ThreadPoolExecutor cachedThreadPool;

    /** 关于事务东西 **/
    TransactionTemplate transactionTemplate;

    /** 数据源 **/
    DynamicRoutingDataSource dataSource;

    /**
     * @auth lijianguo
     * @date 2020/10/8 13:29
     * @remark 初始化函数
     */
    public MulTenantProcessDefault(MulTenantData mulTenantData, RedisTemplate redisTemplate, GlobalTxThreadPool globalTransactionThreadPool,
                                   TransactionTemplate transactionTemplate, DynamicRoutingDataSource dataSource) {
        this.mulTenantData = mulTenantData;
        this.redisTemplate = redisTemplate;
        this.globalTransactionThreadPool = globalTransactionThreadPool;
        this.transactionTemplate = transactionTemplate;
        this.dataSource = dataSource;
        cachedThreadPool = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        MulTenantThreadFactory threadFactory = new MulTenantThreadFactory(null);
        cachedThreadPool.setThreadFactory(threadFactory);
    }

    public MulTenantProcessDefault(MulTenantData mulTenantData, RedisTemplate redisTemplate,
                                   TransactionTemplate transactionTemplate,DynamicRoutingDataSource dataSource) {
        this(mulTenantData, redisTemplate, new GlobalTxThreadPoolDefault(), transactionTemplate, dataSource);
    }

    @Override
    public MulTenant getAdminTenant() {
        MulTenant admin = new MulTenant();
        admin.setCode("");
        admin.setName("管理平台");
        return admin;
    }

    @Override
    public Boolean adminTenantUser() {
        String tenant = BaseContextHandler.getTenant();
        if (StringUtils.isBlank(tenant)){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<MulTenant> getAllTenant() {
        return getTenantList();
    }

    @Override
    public List<MulTenant> getAllTenantIncludeAdmin() {

        List<MulTenant> mulTenantList = getTenantList();
        List<MulTenant> allTenantList = new ArrayList<>(mulTenantList);
        MulTenant admin = new MulTenant();
        admin.setCode("");
        admin.setName("管理平台");
        allTenantList.add(admin);
        return allTenantList;
    }

    @Override
    public void clearTenantCache() {
        redisTemplate.delete(RedisCommonKey.TENANT_CACHE_KEY);
    }

    /**
     * @auth lijianguo
     * @date 2020/9/29 18:16
     * @remark 获取租户
     */
    private List<MulTenant> getTenantList () {

        List<MulTenant> mulTenantList = redisTemplate.opsForList().range(RedisCommonKey.TENANT_CACHE_KEY, 0, -1);
        if (mulTenantList == null || mulTenantList.size() == 0) {
            if (mulTenantData == null) {
                throw new RuntimeException("没有注入mulTenantData实现的Bean");
            }
            mulTenantList = mulTenantData.getAllTenantForMulTenant();
            for (MulTenant mulTenant: mulTenantList) {
                redisTemplate.opsForList().leftPush(RedisCommonKey.TENANT_CACHE_KEY, mulTenant);
            }
        }
        return mulTenantList;
    }

    @Override
    public MulTenantResult runInOtherTenant(RealProcess process) {
        List<MulTenant> mulTenantList = getTenantList();
        MulTenantResult mulTenantResult = runInOtherTenant(process, mulTenantList);
        return mulTenantResult;
    }

    @Override
    public MulTenantResult runInOtherTenant(RealProcess process, List<MulTenant> mulTenantList) {

        MulTenantResult mulTenantResult = new MulTenantResult(mulTenantList.size());
        if (mulTenantList == null || mulTenantList.size() == 0){
            return mulTenantResult;
        }
        int txType = TxInfoTypeThreadLocal.getTxType();
        int rollType = TxInfoTypeThreadLocal.getTxRoll();
        // 没有事务
        if (txType == 0){
            for (MulTenant mulTenant : mulTenantList) {
                noTransaction(process, mulTenant, mulTenantResult);
            }
        // 本地事务
        }else if (txType == 1){
            for (MulTenant mulTenant : mulTenantList) {
                localTransaction(process, mulTenant, mulTenantResult);
            }
        // 分布式事务
        }else{
            for (MulTenant mulTenant : mulTenantList) {
                globalTransactionNew(process, mulTenant, mulTenantResult);
            }
            if (rollType == 0) {
                mulTenantResult.checkAndThrowException();
            }
        }
        return mulTenantResult;
    }

    /**
     * @auth lijianguo
     * @date 2020/11/2 9:54
     * @remark 没有事务注解在 一个线程里面处理--就可以切换租户
     */
    private void noTransaction(RealProcess process, MulTenant mulTenant, MulTenantResult mulTenantResult ) {
        FutureTask<TenantResult> futureTask = new FutureTask<TenantResult>((Callable<TenantResult>) () -> {

            RootContext.unbind();
            BaseContextHandler.setTenant(mulTenant.getCode());
            DynamicDataSourceContextHolder.push(mulTenant.getCode());

            TenantResult tenantResult = new TenantResult(mulTenant.getCode(), mulTenant.getName());
            try {
                Object result = process.realProcess();
                tenantResult.setResult(result);
            } finally {
                DynamicDataSourceContextHolder.clear();
            }
            return tenantResult;
        });
        try {
           cachedThreadPool.execute(futureTask);
//            new Thread(futureTask).start();
            TenantResult tenantResult = futureTask.get();
            mulTenantResult.addOneResult(tenantResult);
        } catch (InterruptedException e) {
            log.error(e);
            throw new RuntimeException(e.getMessage());
        } catch (ExecutionException e) {
            log.error(e);
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * @auth lijianguo
     * @date 2020/11/2 10:03
     * @remark 切换租户--本地事务
     */
    private void localTransaction(RealProcess process, MulTenant mulTenant, MulTenantResult mulTenantResult) {

        FutureTask<TenantResult> futureTask = new FutureTask<TenantResult>((Callable<TenantResult>) () -> {

            RootContext.unbind();
            BaseContextHandler.setTenant(mulTenant.getCode());
            DynamicDataSourceContextHolder.push(mulTenant.getCode());

            DataSourceTransactionManager transactionManager = (DataSourceTransactionManager) transactionTemplate.getTransactionManager();
            DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
            definition.setName("手动事务开启--每个线程起一个事务");
            definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            TransactionStatus txStatus = transactionManager.getTransaction(definition);
            TenantResult tenantResult = new TenantResult(mulTenant.getCode(), mulTenant.getName());
            try {
                Object result = process.realProcess();
                tenantResult.setResult(result);
            } catch (Exception e) {
                tenantResult.setFailWithMsg(e.getMessage());
                transactionManager.rollback(txStatus);
                log.error(e);
                throw new RuntimeException(e.getMessage());
            } finally {
                DynamicDataSourceContextHolder.clear();
            }
            transactionManager.commit(txStatus);
            return tenantResult;
        });
        try {
            cachedThreadPool.execute(futureTask);
            TenantResult tenantResult = futureTask.get();
            mulTenantResult.addOneResult(tenantResult);
        } catch (InterruptedException e) {
            log.error(e);
            throw new RuntimeException(e.getMessage());
        } catch (ExecutionException e) {
            log.error(e);
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * @auth lijianguo
     * @date 2020/11/2 11:47
     * @remark 切换租户-分布式事务
     */
    private MulTenantResult globalTransaction(RealProcess process, List<MulTenant> mulTenantList, MulTenantResult mulTenantResult) {

        CompletionService<TenantResult> completionService = globalTransactionThreadPool.produceService(process, mulTenantList, cachedThreadPool);
        for(int i = 0; i < mulTenantList.size(); i++){
            try {
                // 这里是一个租户的处理结果
                TenantResult tenantResult = completionService.take().get();
                mulTenantResult.addOneResult(tenantResult);
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.error("线程池出问题了 InterruptedException");
            } catch (ExecutionException e) {
                e.printStackTrace();
                log.error("线程池出问题了 ExecutionException");
            }
        }
        return mulTenantResult;
    }

    /**
     * @auth lijianguo
     * @date 2020/11/6 11:25
     * @remark 请输入备注
     */
    private void globalTransactionNew(RealProcess process, MulTenant mulTenant, MulTenantResult mulTenantResult) {

        GlobalTransaction globalTransaction = GlobalTransactionContext.getCurrentOrCreate();
        FutureTask<TenantResult> futureTask = new FutureTask<TenantResult>((Callable<TenantResult>) () -> {

            RootContext.unbind();
            BaseContextHandler.setTenant(mulTenant.getCode());
            DynamicDataSourceContextHolder.push(mulTenant.getCode());

            if (globalTransaction.getXid() != null) {
                RootContext.bind(globalTransaction.getXid());
            }
            TenantResult tenantResult = new TenantResult(mulTenant.getCode(), mulTenant.getName());
            try {
                Object result = process.realProcess();
                tenantResult.setResult(result);
            } finally {
                DynamicDataSourceContextHolder.clear();
            }
            return tenantResult;
        });
        try {
            cachedThreadPool.execute(futureTask);
            TenantResult tenantResult = futureTask.get();
            mulTenantResult.addOneResult(tenantResult);
        } catch (InterruptedException e) {
            log.error(e);
            throw new RuntimeException(e.getMessage());
        } catch (ExecutionException e) {
            log.error(e);
            throw new RuntimeException(e.getMessage());
        }
    }
}
