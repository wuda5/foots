package com.cdqckj.gmis.executor.jobhandler.operateconfig;

import com.cdqckj.gmis.bizjobcenter.GiveReductionConfJobBizApi;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.operateparam.GiveReductionConfBizApi;
import com.cdqckj.gmis.tenant.entity.Tenant;
import com.cdqckj.gmis.tenant.service.TenantService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@JobHandler(value = "giveReductionConfHandler")
@Component
@Slf4j
public class GiveReductionConfHandler extends IJobHandler {
    @Autowired
    private GiveReductionConfJobBizApi giveReductionConfJob;
    @Autowired
    private TenantService tenantService;
    @Override
    public ReturnT<String> execute2(String param) throws Exception {
        log.info("****************赠送减免活动生效定时任务********************");
        List<Tenant> tenantList = tenantService.getAllTenant();
        for(Tenant tenant:tenantList){
            BaseContextHandler.setTenant(tenant.getCode());
//            Map<String,String> headMap =  new HashMap<String,String>();
//            headMap.put("tenant", tenant.getCode());
//            doPost("http://172.16.92.156:8804/priceschemejob/isActivate",headMap,null);
            giveReductionConfJob.updateGiveReductionStatus();
        }
        return SUCCESS;
    }
}
