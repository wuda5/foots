package com.cdqckj.gmis.bizcenter.operation.config.service.impl;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperCenterServiceImpl;
import com.cdqckj.gmis.bizcenter.operation.config.service.BizHallLimitService;
import com.cdqckj.gmis.operateparam.BusinessHallBizApi;
import com.cdqckj.gmis.operateparam.BusinessHallQuotaRecordBizApi;
import com.cdqckj.gmis.operateparam.dto.BusinessHallQuotaRecordSaveDTO;
import com.cdqckj.gmis.operateparam.entity.BusinessHallQuotaRecord;
import com.cdqckj.gmis.systemparam.dto.BusinessHallUpdateDTO;
import com.cdqckj.gmis.systemparam.entity.BusinessHall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BizHallLimitServiceImpl extends SuperCenterServiceImpl implements BizHallLimitService {
    @Autowired
    private BusinessHallBizApi businessHallBizApi;
    @Autowired
    private BusinessHallQuotaRecordBizApi businessHallQuotaRecordBizApi;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<BusinessHallQuotaRecord> saveBusinessHallQuotaRecord(BusinessHallQuotaRecordSaveDTO saveDTO) {
        //测试方便，暂时写在这
        BusinessHallUpdateDTO businessHall = new BusinessHallUpdateDTO();
        BusinessHall businessHallEntity = (businessHallBizApi.queryById(saveDTO.getBusinessHallId())).getData();
        if("CUMULATIVE".equalsIgnoreCase(saveDTO.getQuotaType().getCode())){
            saveDTO.setBalance(saveDTO.getMoney().add(businessHallEntity.getBalance()));
        }else{
            saveDTO.setBalance(saveDTO.getMoney());
        }
        saveDTO.setTotalMoney(saveDTO.getBalance());
        businessHall.setBalance(saveDTO.getBalance());
        businessHall.setPointType(saveDTO.getQuotaType().getDesc());
        businessHall.setRestrictStatus(saveDTO.getDataStatus());
        businessHall.setId(saveDTO.getBusinessHallId());
        businessHallBizApi.update(businessHall);
        return businessHallQuotaRecordBizApi.save(saveDTO);
    }
}
