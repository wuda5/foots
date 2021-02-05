package com.cdqckj.gmis.charges.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.charges.dao.GasmeterSettlementDetailMapper;
import com.cdqckj.gmis.charges.entity.GasmeterSettlementDetail;
import com.cdqckj.gmis.charges.service.GasmeterSettlementDetailService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import com.cdqckj.gmis.charges.vo.SettlementArrearsVO;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.utils.UserOrgIdUtil;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 业务实现类
 * 气表结算明细
 * </p>
 *
 * @author tp
 * @date 2020-09-15
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class GasmeterSettlementDetailServiceImpl extends SuperServiceImpl<GasmeterSettlementDetailMapper, GasmeterSettlementDetail> implements GasmeterSettlementDetailService {
    @Override
    public R<BigDecimal> getSettlementArrears(SettlementArrearsVO arrearsVO) {
        LbqWrapper<GasmeterSettlementDetail> wrapper = new LbqWrapper<>();
        wrapper.eq(GasmeterSettlementDetail::getGasmeterCode,arrearsVO.getGasMeterCode());
        wrapper.eq(GasmeterSettlementDetail::getDataStatus,1);
        wrapper.between(GasmeterSettlementDetail::getBillingDate,arrearsVO.getStartDate(),arrearsVO.getEndDate());
        List<GasmeterSettlementDetail> list = baseMapper.selectList(wrapper);
        BigDecimal arrearsMoney = BigDecimal.ZERO;
        if(list!=null&&list.size()>0){
            int index = 0;
            for(GasmeterSettlementDetail detail:list){
                if(index==0){
                    log.info("中心计费后付费账单统计开始数据:【{}】", JSONUtil.toJsonStr(detail));
                }
                if(index==(list.size()-1)){
                    log.info("中心计费后付费账单统计结束数据:【{}】", JSONUtil.toJsonStr(detail));
                }
                arrearsMoney = arrearsMoney.add(detail.getArrearsMoney());
                index ++;
            }
        }
        return R.success(arrearsMoney);
    }

    @Override
    public BigDecimal stsGeneralGasMeterUseGas(StsSearchParam stsSearchParam) {
        String dataScope = UserOrgIdUtil.getUserDataScopeStr();
        BigDecimal num = this.baseMapper.stsGeneralGasMeterUseGas(stsSearchParam, dataScope);
        if (num == null){
            num = BigDecimal.ZERO;
        }
        return num;
    }
}
