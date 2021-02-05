package com.cdqckj.gmis.bizcenter.securitycheckrecord.service;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.operation.dto.OrderRecordUpdateDTO;
import com.cdqckj.gmis.operation.entity.OrderRecord;
import com.cdqckj.gmis.securityed.dto.SecurityCheckRecordSaveDTO;
import com.cdqckj.gmis.securityed.dto.SecurityCheckRecordUpdateDTO;
import com.cdqckj.gmis.securityed.entity.SecurityCheckRecord;
import com.cdqckj.gmis.securityed.vo.SendOrderVo;

import java.util.List;

public interface SecurityCheckRecordService {
    R<SecurityCheckRecord> saveList(List<SecurityCheckRecordSaveDTO> securityCheckRecordSaveDTOS);

    R<SecurityCheckRecord> approvaled(SecurityCheckRecordUpdateDTO params);

    R<SecurityCheckRecord> reject(SecurityCheckRecordUpdateDTO params);

    R<SecurityCheckRecord> endSecurityCheckRecord(SecurityCheckRecordUpdateDTO params);

    R<SecurityCheckRecord> leaflet(SendOrderVo params);

    R<SecurityCheckRecord> giveOrder(SendOrderVo params);

    R<OrderRecord> confirmOrder(OrderRecordUpdateDTO params);

    R<SecurityCheckRecord> refuseOrder(SendOrderVo params);

    R<SecurityCheckRecord> transferOrder(SendOrderVo params);
}
