package com.cdqckj.gmis.operateparam.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.operateparam.dao.PenaltyMapper;
import com.cdqckj.gmis.operateparam.dto.PenaltyUpdateDTO;
import com.cdqckj.gmis.operateparam.entity.Penalty;
import com.cdqckj.gmis.operateparam.service.PenaltyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 业务实现类
 *
 * </p>
 *
 * @author gmis
 * @date 2020-06-29
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class PenaltyServiceImpl extends SuperServiceImpl<PenaltyMapper, Penalty> implements PenaltyService {
    @Override
    public Penalty queryRecentRecord() {
        return getBaseMapper().queryRecentRecord();
    }

    @Override
    public Penalty findPenaltyName(String executeName) {
        LbqWrapper<Penalty> cgrParams = new LbqWrapper<>();
        cgrParams.eq(Penalty::getExecuteName,executeName);
        return  baseMapper.selectOne(cgrParams);
    }

    @Override
    public String check(PenaltyUpdateDTO updateDTO) {
        List<String> list = JSON.parseArray(updateDTO.getUseGasTypeId(),String.class);
        for(String id:list){
            if(super.count(Wraps.<Penalty>lbQ().ne(Penalty::getId,updateDTO.getId()).
                    like(Penalty::getUseGasTypeId,id))>0){
                return id;
            }
        }
        return "";
    }

    @Override
    public Penalty queryByUseGasId(Long useGasId) {
        return baseMapper.selectOne(Wraps.<Penalty>lbQ().eq(Penalty::getDataStatus,1).
                like(Penalty::getUseGasTypeId,(useGasId.toString())));
    }
}
