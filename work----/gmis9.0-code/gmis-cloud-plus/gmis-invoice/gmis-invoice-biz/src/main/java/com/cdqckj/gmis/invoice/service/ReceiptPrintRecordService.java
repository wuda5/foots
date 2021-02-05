package com.cdqckj.gmis.invoice.service;

import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.invoice.entity.ReceiptPrintRecord;
import com.cdqckj.gmis.systemparam.entity.TemplateItem;
import com.cdqckj.gmis.systemparam.enumeration.TemplateTypeEnum;

/**
 * <p>
 * 业务接口
 * 票据打印记录
 * </p>
 *
 * @author gmis
 * @date 2020-10-22
 */
public interface ReceiptPrintRecordService extends SuperService<ReceiptPrintRecord> {

    /**
     * 获取打印模板
     *
     * @param templateCode
     * @return
     */
    public TemplateItem getPrintTemplate(TemplateTypeEnum.Type.Ticket templateCode);


}
