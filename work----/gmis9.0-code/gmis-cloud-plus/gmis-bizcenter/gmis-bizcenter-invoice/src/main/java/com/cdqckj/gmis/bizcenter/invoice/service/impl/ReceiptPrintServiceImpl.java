package com.cdqckj.gmis.bizcenter.invoice.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.bizcenter.invoice.dto.ReceiptPrintDTO;
import com.cdqckj.gmis.bizcenter.invoice.service.ReceiptPrintService;
import com.cdqckj.gmis.invoice.ReceiptPrintRecordBizApi;
import com.cdqckj.gmis.invoice.dto.ReceiptPrintRecordSaveDTO;
import com.cdqckj.gmis.invoice.entity.ReceiptPrintRecord;
import com.cdqckj.gmis.security.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * @author ASUS
 */
@Service
public class ReceiptPrintServiceImpl implements ReceiptPrintService {

    @Autowired
    private ReceiptPrintRecordBizApi receiptPrintRecordBizApi;

    @Override
    public R<ReceiptPrintRecord> add(ReceiptPrintDTO saveDTO, SysUser user) {
        ReceiptPrintRecordSaveDTO save = BeanUtil.copyProperties(saveDTO, ReceiptPrintRecordSaveDTO.class);
        save.setOperatorId(user.getId());
        save.setOperatorName(user.getName());
        save.setPrintDate(LocalDate.now());
        return receiptPrintRecordBizApi.save(save);
    }
}
