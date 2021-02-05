package com.cdqckj.gmis.executor.jobhandler.operateconfig;

import com.cdqckj.gmis.bizjobcenter.PriceJobBizApi;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.tenant.entity.Tenant;
import com.cdqckj.gmis.tenant.service.TenantService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@JobHandler(value = "priceJobHandler")
@Component
@Slf4j
public class PriceJobHandler  extends IJobHandler {
    @Autowired
    private TenantService tenantService;
    @Autowired
    private PriceJobBizApi priceJobBizApi;
    @Override
    public ReturnT<String> execute2(String param) throws Exception {
        log.info("****************价格方案生效定时任务********************");
        List<Tenant> tenantList = tenantService.getAllTenant();
        for(Tenant tenant:tenantList){
            BaseContextHandler.setTenant(tenant.getCode());
            priceJobBizApi.isActivatePriceScheme();
        }
        return SUCCESS;
    }
}
