package com.cdqckj.gmis.bizcenter.invoice.service;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.bizcenter.invoice.dto.ReceiptPrintDTO;
import com.cdqckj.gmis.invoice.entity.ReceiptPrintRecord;
import com.cdqckj.gmis.security.model.SysUser;

/**
 * @author ASUS
 */
public interface ReceiptPrintService {

    public R<ReceiptPrintRecord> add(ReceiptPrintDTO saveDTO, SysUser user);
}
