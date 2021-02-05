package com.cdqckj.gmis.statistics.domain.log.process;

import com.cdqckj.gmis.invoice.entity.Invoice;
import com.cdqckj.gmis.invoice.enumeration.InvoiceStatusEnum;
import com.cdqckj.gmis.statistics.domain.log.FormatRowData;
import com.cdqckj.gmis.statistics.domain.log.ProcessTypeEnum;
import com.cdqckj.gmis.statistics.domain.log.SysServiceBean;
import com.cdqckj.gmis.statistics.domain.log.TableUpdateLog;
import com.cdqckj.gmis.statistics.domain.log.util.DateStsUtil;
import com.cdqckj.gmis.statistics.domain.log.util.EntityFieldUtil;
import com.cdqckj.gmis.statistics.entity.InvoiceDay;
import com.cdqckj.gmis.statistics.service.InvoiceDayService;
import io.swagger.annotations.ApiModelProperty;

import java.util.Arrays;

/**
 * @author: lijianguo
 * @time: 2020/11/3 9:02
 * @remark: 发票的工具类当发票状态-改变为开票成功的时候记录
 */
public class InvoiceLog implements TableUpdateLog<Invoice> {

    @ApiModelProperty(value = "对应的类的名字")
    private Class className;

    @ApiModelProperty(value = "系统的服务类")
    private SysServiceBean sysServiceBean;

    @ApiModelProperty(value = "每天的票据的记录")
    private InvoiceDayService invoiceDayService;

    public InvoiceLog(Class className, SysServiceBean sysServiceBean) {
        this.className = className;
        this.sysServiceBean = sysServiceBean;
        this.invoiceDayService = sysServiceBean.getInvoiceDayService();
    }

    @Override
    public Class getEntityClass() {
        return className;
    }

    @Override
    public Boolean logNeedProcess(FormatRowData<Invoice> formatRowData, ProcessTypeEnum typeEnum) {

        Boolean change = formatRowData.anyColChange("invoice_status","invalid_status");
        Boolean sucStatus = formatRowData.getAfterRowValue().getInvoiceStatus().equals(InvoiceStatusEnum.OPEN_SUCCESS.getCode());
        // invalid_status 变为成功的操作
        if (change){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Boolean updateLog(FormatRowData<Invoice> formatRowData) {

        Invoice after = formatRowData.getAfterRowValue();
        // 发票开票成功
        if (formatRowData.colChange("invoice_status") && after.getInvoiceStatus().equals(InvoiceStatusEnum.OPEN_SUCCESS.getCode())){
            op(after, after.getInvoiceKind());
        }
        // 发票作废
        if (formatRowData.colChange("invalid_status") && after.getInvalidStatus().equals("1")){
            op(after, "2");
        }
        return true;
    }

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/5 11:52
    * @remark 处理数据
    */
    private void op(Invoice after, String invoiceKind){

        InvoiceDay logData = new InvoiceDay();
        logData.setCompanyCode(after.getCompanyCode());
        logData.setOrgId(after.getOrgId());
        logData.setBusinessHallId(sysServiceBean.getBusinessHallId(after.getOrgId()));
        logData.setCreateUserId(after.getCreateUser());
        logData.setTotalAmount(after.getTotalAmount());
        logData.setTotalTax(after.getTotalTax());
        logData.setInvoiceKind(invoiceKind);
        logData.setInvoiceType(after.getInvoiceType());
        logData.setUpdateUser(after.getUpdateUser());
        logData.setCreateTime(after.getCreateTime());
        logData.setUpdateTime(after.getUpdateTime());
        logData.setStsDay(DateStsUtil.dayBeginTime(after.getCreateTime()));
        String searchSql = EntityFieldUtil.searchSameRecordSql(logData, Arrays.asList("amount","totalAmount","totalTax"));

        InvoiceDay dataBaseRecord = invoiceDayService.getTheDayRecord(searchSql);
        if (dataBaseRecord == null){
            logData.setAmount(1);
            invoiceDayService.save(logData);
        }else {
            dataBaseRecord.setAmount(dataBaseRecord.getAmount() + 1);
            dataBaseRecord.setTotalAmount(dataBaseRecord.getTotalAmount().add(logData.getTotalAmount()));
            dataBaseRecord.setTotalTax(dataBaseRecord.getTotalTax().add(logData.getTotalTax()));
            invoiceDayService.updateById(dataBaseRecord);
        }
    }
}
