package com.cdqckj.gmis.executor.jobhandler.calculate;

import com.alibaba.fastjson.JSONObject;
import com.cdqckj.gmis.bizjobcenter.CalculateBizApi;
import com.cdqckj.gmis.common.utils.StringUtil;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.executor.jobhandler.vo.JobParamsVO;
import com.cdqckj.gmis.tenant.entity.Tenant;
import com.cdqckj.gmis.tenant.service.TenantService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@JobHandler(value = "penaltyJobHandler")
@Component
@Slf4j
public class PenaltyJobHandler extends IJobHandler {

    @Autowired
    private TenantService tenantService;
    @Autowired
    private CalculateBizApi calculateBizApi;
    @Override
    public ReturnT<String> execute2(String param) throws Exception {
        log.info("****************滞纳金计算定时任务********************");
        JobParamsVO jobParamsVO = null;
        if(!StringUtil.isNullOrBlank(param)){
            jobParamsVO = JSONObject.parseObject(param,JobParamsVO.class);
        }
        if(jobParamsVO!=null){
            BaseContextHandler.setTenant(jobParamsVO.getTenantCode());
            calculateBizApi.calculatePenaltyEX(jobParamsVO.getTenantCode(),jobParamsVO.getExecuteDate());
        }else{
            List<Tenant> tenantList = tenantService.getAllTenant();
            for(Tenant tenant:tenantList){
                BaseContextHandler.setTenant(tenant.getCode());
                calculateBizApi.calculatePenalty();
            }
        }
        return SUCCESS;
    }
}
