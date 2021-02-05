package com.cdqckj.gmis.executor.jobhandler;

import cn.hutool.core.date.DateTime;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.pay.PayBizApi;
import com.cdqckj.gmis.tenant.entity.Tenant;
import com.cdqckj.gmis.tenant.service.TenantService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 抄表计划定时任务
 * 每天凌晨执行
 */
@JobHandler(value = "wxBillJobHandler")
@Component
@Slf4j
public class WxBillJobHandler extends IJobHandler {

    @Autowired
    private TenantService tenantService;
    @Autowired
    private PayBizApi payBizApi;

    @Override
    public ReturnT<String> execute2(String param) {
        List<Tenant> tenantList = tenantService.getAllTenant();
        //多租户，过期的计划全部置为完结
        DateTime time = new DateTime();
        tenantList.stream().forEach(dto -> {
            String str = "";
            String code = dto.getCode();
            //切换数据库
            BaseContextHandler.setTenant(code);
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            String nowDate = simpleDateFormat.format(date);
            System.out.println("当前时间："+nowDate);
            Calendar calendar = Calendar.getInstance();//此时打印它获取的是系统当前时间
            calendar.add(Calendar.DATE, -1);    //得到前一天
            String yestedayDate = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
            System.out.println("昨天时间："+yestedayDate);
            String logInfo = null;
            try {
                R<Boolean> result = payBizApi.queryAndSaveBill(yestedayDate);
                logInfo = "微信对账：编号为"+code+"的租户:"+dto.getName()+"微信下载账单成功，返回信息："+result.getMsg();
            } catch (Exception e) {
                logInfo = "微信对账：编号为"+code+"的租户:"+dto.getName()+"下载微信账单报错："+e.getMessage();
                e.printStackTrace();
            }finally {
                log.info(logInfo);
                XxlJobLogger.log(logInfo);
            }
        });
        return SUCCESS;
    }

}
