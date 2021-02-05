package com.cdqckj.gmis.operateparam.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.DeleteStatusEnum;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.operateparam.dao.GiveReductionConfMapper;
import com.cdqckj.gmis.operateparam.dto.GiveReductionQueryDTO;
import com.cdqckj.gmis.operateparam.entity.GiveReductionConf;
import com.cdqckj.gmis.operateparam.service.GiveReductionConfService;
import io.seata.common.util.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 业务实现类
 * 赠送减免活动配置
 * </p>
 *
 * @author gmis
 * @date 2020-08-27
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class GiveReductionConfServiceImpl extends SuperServiceImpl<GiveReductionConfMapper, GiveReductionConf> implements GiveReductionConfService {
    public R<List<GiveReductionConf>> queryEffectiveGiveReduction(GiveReductionQueryDTO queryDTO){

        //开始时间要小于
        LbqWrapper<GiveReductionConf> wrapper=new LbqWrapper<GiveReductionConf>()
                .ge(GiveReductionConf:: getEndTime, LocalDate.now())
                .le(GiveReductionConf:: getStartTime,LocalDate.now())
                .eq(GiveReductionConf:: getDataStatus, DataStatusEnum.NORMAL.getValue())
                .eq(GiveReductionConf:: getDeleteStatus, DeleteStatusEnum.NORMAL.getValue());
        if(queryDTO.getActivityScene()!=null){
            wrapper=wrapper.eq(GiveReductionConf:: getActivityScene,queryDTO
                    .getActivityScene().getCode());
        }
        if(CollectionUtils.isNotEmpty(queryDTO.getTollItemIds())){
            wrapper=wrapper.in(GiveReductionConf:: getTollItemId,queryDTO.getTollItemIds());
        }
        List<GiveReductionConf> list=list(wrapper);
        List<GiveReductionConf> result=new ArrayList<>();
        if(CollectionUtils.isNotEmpty(list)){
            if(queryDTO.getUseGasTypeId()!=null){
                for (GiveReductionConf conf : list) {
                    if(StringUtils.isBlank(conf.getUseGasTypes())){
                        result.add(conf);
                    }else{
//                        [{"use_gas_type_id":"1331082386422628352","use_gas_type_name":"用气B"}]
                        JSONArray arr= JSON.parseArray(conf.getUseGasTypes());
                        for(int i=0;i<arr.size();i++){
                            if(queryDTO.getUseGasTypeId().toString().equals(arr.getJSONObject(i).getString("use_gas_type_id"))){
                                result.add(conf);
                                break;
                            }
                        }
                    }
                }

            }else{
                result.addAll(list);
            }
        }
      return R.success(result);
    }
}
