package com.cdqckj.gmis.charges.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.charges.dao.RechargeRecordMapper;
import com.cdqckj.gmis.charges.entity.RechargeRecord;
import com.cdqckj.gmis.charges.service.RechargeRecordService;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 业务实现类
 * 充值记录
 * </p>
 *
 * @author tp
 * @date 2020-09-07
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class RechargeRecordServiceImpl extends SuperServiceImpl<RechargeRecordMapper, RechargeRecord> implements RechargeRecordService {


    @Override
    public List<RechargeRecord> getListByMeterAndCustomerCode(String gasMeterCode, String customerCode){
        return baseMapper.selectList(new LbqWrapper<RechargeRecord>()
                .eq(RechargeRecord::getGasmeterCode,gasMeterCode)
                .eq(RechargeRecord::getCustomerCode,customerCode)
                .eq(RechargeRecord::getDataStatus, DataStatusEnum.NORMAL.getValue())
                .orderByDesc(RechargeRecord::getCreateTime)
        );
    }

    @Override
    public IPage getPageByMeterAndCustomerCode(String gasMeterCode, String customerCode, IPage page){
        return baseMapper.selectPage(page,new LbqWrapper<RechargeRecord>()
                .eq(RechargeRecord::getGasmeterCode,gasMeterCode)
                .eq(RechargeRecord::getCustomerCode,customerCode)
                .eq(RechargeRecord::getDataStatus, DataStatusEnum.NORMAL.getValue())
                .orderByDesc(RechargeRecord::getCreateTime)
        );
    }

    @Override
    public RechargeRecord getByChargeNo(String chargeNo){
        return baseMapper.selectOne(new LbqWrapper<RechargeRecord>()
                .eq(RechargeRecord::getChargeNo,chargeNo)
        );
    }
}
