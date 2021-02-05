package com.cdqckj.gmis.invoice.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.exception.code.ExceptionCode;
import com.cdqckj.gmis.invoice.dao.ReceiptPrintRecordMapper;
import com.cdqckj.gmis.invoice.entity.ReceiptPrintRecord;
import com.cdqckj.gmis.invoice.service.ReceiptPrintRecordService;
import com.cdqckj.gmis.systemparam.entity.TemplateItem;
import com.cdqckj.gmis.systemparam.enumeration.DefaultEnum;
import com.cdqckj.gmis.systemparam.enumeration.TemplateTypeEnum;
import com.cdqckj.gmis.systemparam.service.TemplateItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 * 业务实现类
 * 票据打印记录
 * </p>
 *
 * @author gmis
 * @date 2020-10-22
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class ReceiptPrintRecordServiceImpl extends SuperServiceImpl<ReceiptPrintRecordMapper, ReceiptPrintRecord> implements ReceiptPrintRecordService {

    @Autowired
    TemplateItemService templateItemService;

    /**
     * 获取打印模板
     *
     * @param templateCode
     * @return
     */
    @Override
    public TemplateItem getPrintTemplate(TemplateTypeEnum.Type.Ticket templateCode) {

        LbqWrapper<TemplateItem> wrapper = Wraps.lbQ();
        wrapper.eq(TemplateItem::getTemplateCode, templateCode.getCode())
                .eq(TemplateItem::getDefaultStatus, DefaultEnum.NOT_REVIEWED.getStatus())
                .last("limit 1");
        TemplateItem one = templateItemService.getOne(wrapper);
        if (Objects.isNull(one)) {
            throw new BizException(ExceptionCode.NOT_FOUND.getCode(), "未查询到对应模板, 请先进行相应模板配置。");
        }
        return one;
    }
}
