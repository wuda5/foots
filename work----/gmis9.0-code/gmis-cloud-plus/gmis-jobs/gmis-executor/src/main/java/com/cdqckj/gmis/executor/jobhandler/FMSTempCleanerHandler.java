package com.cdqckj.gmis.executor.jobhandler;

import com.cdqckj.gmis.base.BizTypeEnum;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.file.api.AttachmentApi;
import com.cdqckj.gmis.file.entity.Attachment;
import com.cdqckj.gmis.file.service.AttachmentService;
import com.cdqckj.gmis.tenant.entity.Tenant;
import com.cdqckj.gmis.tenant.service.TenantService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 抄表计划定时任务
 * 每天凌晨执行
 */
@JobHandler(value = "FMSTempCleanerHandler")
@Component
@Slf4j
public class FMSTempCleanerHandler extends IJobHandler {

    @Autowired
    private TenantService tenantService;
    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private AttachmentApi attachmentApi;


    @Override
    public ReturnT<String> execute2(String param) {
        List<Tenant> tenantList = tenantService.getAllTenant();
        tenantList.stream().forEach(dto -> {
            String code = dto.getCode();
            Boolean bool = true;
            //切换数据库
            BaseContextHandler.setTenant(code);
            List<Attachment> list = attachmentService.list(BizTypeEnum.TEMPORARY_DOCUMENTS.getCode());
            if(list.size()>0){
                List<Long> ids = list.stream().map(Attachment::getId).collect(Collectors.toList());
                bool = attachmentApi.deleteByIds(ids).getData();
            }
            String mess = bool?"成功":"失败";
            XxlJobLogger.log("定时清理腾讯云上，租户编号为"+code+"下，TEMPORARY_DOCUMENTS文件夹下的临时文件："+mess);
        });
        return SUCCESS;
    }


}
