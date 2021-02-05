package com.cdqckj.gmis.bizcenter.operation.config.service.impl;

import com.alibaba.fastjson.JSON;
import com.cdqckj.gmis.base.MessageConstants;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperCenterServiceImpl;
import com.cdqckj.gmis.bizcenter.operation.config.service.GaslimitService;
import com.cdqckj.gmis.operateparam.PurchaseLimitBizApi;
import com.cdqckj.gmis.operateparam.dto.PurchaseLimitSaveDTO;
import com.cdqckj.gmis.operateparam.dto.PurchaseLimitUpdateDTO;
import com.cdqckj.gmis.operateparam.entity.PurchaseLimit;
import com.cdqckj.gmis.utils.BeanPlusUtil;
import com.cdqckj.gmis.utils.DateUtils;
import com.cdqckj.gmis.utils.I18nUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;

/**
 * @author 65427
 */
@Service
public class GaslimitServiceImpl extends SuperCenterServiceImpl implements GaslimitService {

    @Autowired
    PurchaseLimitBizApi purchaseApi;

    @Autowired
    private I18nUtil i18nUtil;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<PurchaseLimit> savePurchaseLimit(PurchaseLimitSaveDTO saveDTO) {
        //测试方便暂时写在这
        //判断时间是否满足要求
        R<PurchaseLimit> res = null;
        List<PurchaseLimit> list = purchaseApi.getAllRecord();
        if(list!=null&&list.size()>0){
            if(saveDTO.getSelectType()==0){
                saveDTO.setEndTime(saveDTO.getStartTime().plusMonths((saveDTO.getCycle()*saveDTO.getCycleNum())));
            }
            List<Long> gasTypeIdOn = JSON.parseArray(saveDTO.getUseGasTypeId(),Long.class);
            for(PurchaseLimit purchaseLimit:list){
                List<Long> gasTypeIdIn = JSON.parseArray(purchaseLimit.getUseGasTypeId(),Long.class);
                gasTypeIdIn.retainAll(gasTypeIdOn);
                if(purchaseLimit.getLimitName().trim().equals(saveDTO.getLimitName().trim())){
                    res = R.fail(getLangMessage(MessageConstants.GAS_LIMIT_SET));
                    return res;
                }
                if(gasTypeIdIn.size()==0){
                    continue;
                }
                boolean isCross = DateUtils.isDateCross(DateUtils.localDate2Date(saveDTO.getStartTime()),
                        DateUtils.localDate2Date(saveDTO.getEndTime()),
                        DateUtils.localDate2Date(purchaseLimit.getStartTime()),
                        DateUtils.localDate2Date(purchaseLimit.getEndTime()));
                if(isCross){
                    res = R.fail(String.format(getLangMessage(MessageConstants.GAS_TIME_EXIST),saveDTO.getStartTime(),saveDTO.getEndTime()));
                    return res;
                }
            }
        }
        //按月和按时间段分别保存
        if(saveDTO.getSelectType()==0){
            LocalDate startTime = saveDTO.getStartTime();
            LocalDate endTime = (saveDTO.getStartTime().plusMonths(saveDTO.getCycle())).minusDays(1);
            DecimalFormat df=new DecimalFormat("00");
            PurchaseLimitSaveDTO entityDto = BeanPlusUtil.toBean(saveDTO,PurchaseLimitSaveDTO.class);
            for(int i=0;i<saveDTO.getCycleNum();i++){
                entityDto.setStartTime(startTime);
                entityDto.setEndTime(endTime);
                entityDto.setLimitName((saveDTO.getLimitName()+df.format((i+1))));
                purchaseApi.save(entityDto);
                startTime = startTime.plusMonths(saveDTO.getCycle());
                endTime = (startTime.plusMonths(saveDTO.getCycle())).minusDays(1);
            }
            return R.successDef();
        }else{
            return purchaseApi.save(saveDTO);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<PurchaseLimit> updatePurchaseLimit(PurchaseLimitUpdateDTO updateDTO) {
        //测试方便暂时写在这
        R<PurchaseLimit> res = null;
        List<PurchaseLimit> list = purchaseApi.getAllRecord();
            if(list!=null&&list.size()>0){
                List<Long> gasTypeIdOn = JSON.parseArray(updateDTO.getUseGasTypeId(),Long.class);
                for(PurchaseLimit purchaseLimit:list){
                    //判断是否是自身（自身不做限制）
                    if(!purchaseLimit.getId().equals(updateDTO.getId())){
                        List<Long> gasTypeIdIn = JSON.parseArray(purchaseLimit.getUseGasTypeId(),Long.class);
                        gasTypeIdIn.retainAll(gasTypeIdOn);
                        if(purchaseLimit.getLimitName().trim().equals(updateDTO.getLimitName().trim())){
                            res = R.fail(getLangMessage(MessageConstants.GAS_LIMIT_SET));
                            return res;
                        }
                        if(gasTypeIdIn.size()==0){
                            continue;
                        }
                        boolean isCross = DateUtils.isDateCross(DateUtils.localDate2Date(updateDTO.getStartTime()),
                                DateUtils.localDate2Date(updateDTO.getEndTime()),
                                DateUtils.localDate2Date(purchaseLimit.getStartTime()),
                                DateUtils.localDate2Date(purchaseLimit.getEndTime()));
                        if(isCross){
                            res = R.fail(String.format(getLangMessage(MessageConstants.GAS_TIME_EXIST),updateDTO.getStartTime(),updateDTO.getEndTime()));
                            return res;
                        }
                    }
                }
            }
            return purchaseApi.update(updateDTO);
    }
}
