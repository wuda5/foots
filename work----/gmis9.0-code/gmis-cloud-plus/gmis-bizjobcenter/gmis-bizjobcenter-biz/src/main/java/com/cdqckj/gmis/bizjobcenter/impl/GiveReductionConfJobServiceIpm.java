package com.cdqckj.gmis.bizjobcenter.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.bizjobcenter.GiveReductionConfJobService;
import com.cdqckj.gmis.operateparam.GiveReductionConfBizApi;
import com.cdqckj.gmis.operateparam.dto.GiveReductionConfUpdateDTO;
import com.cdqckj.gmis.operateparam.entity.GiveReductionConf;
import com.cdqckj.gmis.utils.BeanPlusUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@DS("#thread.tenant")
public class GiveReductionConfJobServiceIpm implements GiveReductionConfJobService {
    @Autowired
    private GiveReductionConfBizApi giveReductionConfBizApi;
    @Override
    public void updateGiveReductionStatus() {
        GiveReductionConf giveReductionConf=new GiveReductionConf();
        giveReductionConf.setDataStatus(1);
        List<GiveReductionConf> data = giveReductionConfBizApi.query(giveReductionConf).getData();
        if(data !=null && data.size()>0){
            for (GiveReductionConf datum : data) {
                if(datum.getEndTime().isBefore(LocalDate.now())){
                    datum.setDataStatus(DataStatusEnum.NOT_AVAILABLE.getValue());
                    GiveReductionConfUpdateDTO giveReductionConfUpdateDTO = BeanPlusUtil.toBean(datum,GiveReductionConfUpdateDTO.class);
                    giveReductionConfBizApi.update(giveReductionConfUpdateDTO);
                }
            }
        }
    }
}
