package com.cdqckj.gmis.biztemporary.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.biztemporary.dao.GasTypeChangeRecordMapper;
import com.cdqckj.gmis.biztemporary.entity.GasTypeChangeRecord;
import com.cdqckj.gmis.biztemporary.service.GasTypeChangeRecordService;
import com.cdqckj.gmis.biztemporary.vo.GasTypeChangeRecordVO;
import com.cdqckj.gmis.biztemporary.vo.PriceChangeVO;
import com.cdqckj.gmis.common.utils.UserOrgIdUtil;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.operateparam.PriceSchemeBizApi;
import com.cdqckj.gmis.operateparam.entity.PriceScheme;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 业务实现类
 * 客户用气类型变更记录
 * </p>
 *
 * @author gmis
 * @date 2020-11-16
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class GasTypeChangeRecordServiceImpl extends SuperServiceImpl<GasTypeChangeRecordMapper, GasTypeChangeRecord> implements GasTypeChangeRecordService {

    @Autowired
    private PriceSchemeBizApi priceSchemeBizApi;

    @Override
    public List<HashMap<String, Object>> queryEx(GasTypeChangeRecord record) {
        QueryWrapper<GasTypeChangeRecord> query = Wrappers.query();
        query.eq("cr.gas_meter_code",record.getGasMeterCode());
        return baseMapper.queryEx(query);
    }

    @Override
    public PriceScheme getPriceSchemeByRecord(PriceChangeVO priceChangeVO) {
        LbqWrapper<GasTypeChangeRecord> wrapper = new LbqWrapper<>();
        wrapper.eq(GasTypeChangeRecord::getNewPriceId,priceChangeVO.getPriceId());
        wrapper.eq(GasTypeChangeRecord::getGasMeterCode,priceChangeVO.getGasMeterCode());
        //wrapper.le(GasTypeChangeRecord::getStartTimeNew,priceChangeVO.getRecordTime());
        wrapper.orderByDesc(GasTypeChangeRecord::getChangeTime);
        List<GasTypeChangeRecord> listNew = baseMapper.selectList(wrapper);
        PriceScheme priceScheme = null;
        if(listNew!=null&&listNew.size()>0){
            GasTypeChangeRecord gas = listNew.get(0);
            Long priceId = null;
            if(gas.getStartTimeNew().isBefore(priceChangeVO.getRecordTime())
                    ||gas.getStartTimeNew().isEqual((priceChangeVO.getRecordTime()))){
                priceId = gas.getOldPriceId();
                priceScheme = priceSchemeBizApi.queryById(priceId).getData();
            }else{
                LbqWrapper<GasTypeChangeRecord> wrapperNode = new LbqWrapper<>();
                wrapperNode.eq(GasTypeChangeRecord::getNewPriceId,gas.getOldPriceId());
                wrapperNode.eq(GasTypeChangeRecord::getGasMeterCode,priceChangeVO.getGasMeterCode());
                wrapperNode.le(GasTypeChangeRecord::getStartTimeOld,priceChangeVO.getRecordTime());
                wrapperNode.orderByDesc(GasTypeChangeRecord::getChangeTime);
                List<GasTypeChangeRecord> listNode = baseMapper.selectList(wrapperNode);
                if(listNode!=null&&listNode.size()>0){
                    priceId = listNode.get(0).getOldPriceId();
                    priceScheme = priceSchemeBizApi.queryById(priceId).getData();
                }else {
                    priceChangeVO.setPriceId(listNode.get(0).getOldPriceId());
                    getPriceSchemeByRecord(priceChangeVO);
                }
            }
        }
        return priceScheme;
    }

    /**
     * 业务关注点用气类型变更记录列表查询
     *
     * @param queryInfo 查询参数
     * @return 变更记录列表
     */
    @Override
    public List<GasTypeChangeRecordVO> queryFocusInfo(GasTypeChangeRecord queryInfo) {
        List<Long> orgIds = UserOrgIdUtil.getUserDataScopeList();
        return baseMapper.queryFocusInfo(queryInfo, orgIds);
    }
}
