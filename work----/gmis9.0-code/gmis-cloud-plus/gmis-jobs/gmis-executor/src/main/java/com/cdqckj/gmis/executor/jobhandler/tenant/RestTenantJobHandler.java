package com.cdqckj.gmis.executor.jobhandler.tenant;

import cn.hutool.core.util.StrUtil;
import com.cdqckj.gmis.tenant.dao.InitDbMapper;
import com.cdqckj.gmis.tenant.service.GlobalUserService;
import com.cdqckj.gmis.tenant.service.TenantService;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.tenant.entity.GlobalUser;
import com.cdqckj.gmis.tenant.entity.Tenant;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 重置 演示环境的数据库
 *
 * @author gmis
 * @date 2020年01月16日14:45:58
 */
@JobHandler(value = "restTenantJobHandler")
@Component
@Slf4j
public class RestTenantJobHandler extends IJobHandler {

    /**
     * 内置租户
     */
    private final static String DEF_TENANT = "0000";
    private final static String ADMIN_TENANT = "admin";

    @Autowired
    private TenantService tenantService;
    @Autowired
    private GlobalUserService globalUserService;
    @Autowired
    private InitDbMapper initDbMapper;

    @Value("${gmis.database.tenantDatabasePrefix:gmis_base}")
    private String database;

    @Override
    public ReturnT<String> execute2(String param) throws Exception {
        XxlJobLogger.log("执行参数--->param={} ", param);

        List<Tenant> list = tenantService.list(Wraps.<Tenant>lbQ().eq(Tenant::getCode, param).ne(Tenant::getCode, DEF_TENANT));

        List<String> tenantCodeList = list.parallelStream().map(Tenant::getCode).collect(Collectors.toList());

        //删除租户库
        if (!tenantCodeList.isEmpty()) {
            tenantService.remove(Wraps.<Tenant>lbQ().in(Tenant::getCode, tenantCodeList));
            tenantCodeList.forEach((tenant) -> initDbMapper.dropDatabase(database + StrUtil.UNDERLINE + tenant));
        }

        //删除全局用户
        globalUserService.remove(Wraps.<GlobalUser>lbQ().notIn(GlobalUser::getTenantCode, DEF_TENANT, ADMIN_TENANT));
        return SUCCESS;
    }
}
