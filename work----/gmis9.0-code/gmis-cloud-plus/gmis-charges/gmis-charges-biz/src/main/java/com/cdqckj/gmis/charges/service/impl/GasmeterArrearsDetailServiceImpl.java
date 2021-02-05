package com.cdqckj.gmis.charges.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.charges.dao.GasmeterArrearsDetailMapper;
import com.cdqckj.gmis.charges.entity.GasmeterArrearsDetail;
import com.cdqckj.gmis.charges.service.GasmeterArrearsDetailService;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 业务实现类
 * 气表欠费明细
 * </p>
 *
 * @author tp
 * @date 2020-09-07
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class GasmeterArrearsDetailServiceImpl extends SuperServiceImpl<GasmeterArrearsDetailMapper, GasmeterArrearsDetail> implements GasmeterArrearsDetailService {

    public int updateChargeStatusComplete(List<Long> ids){
        return baseMapper.updateChargeStatusComplete(ids);
    }

    @Override
    public BigDecimal stsArrearsFee(StsSearchParam stsSearchParam) {
        return this.baseMapper.stsArrearsFee(stsSearchParam);
    }

    @Override
    public boolean checkExits(GasmeterArrearsDetail gasmeterArrearsDetail) {
        List<GasmeterArrearsDetail> gasmeterArrearsDetails = baseMapper.selectList(Wraps.<GasmeterArrearsDetail>lbQ()
                .eq(GasmeterArrearsDetail::getCustomerCode,gasmeterArrearsDetail.getCustomerCode())
                .eq(GasmeterArrearsDetail::getGasmeterCode,gasmeterArrearsDetail.getGasmeterCode())
                .eq(GasmeterArrearsDetail::getDataStatus, DataStatusEnum.NORMAL.getValue())
                .eq(GasmeterArrearsDetail::getReadmeterMonth,gasmeterArrearsDetail.getReadmeterMonth())
        );
        if(gasmeterArrearsDetails!=null&&gasmeterArrearsDetails.size()>0){return true;}
        else{return false;}
    }

    @Override
    public boolean checkNB21Exits(GasmeterArrearsDetail gasmeterArrearsDetail) {
        List<GasmeterArrearsDetail> gasmeterArrearsDetails = baseMapper.selectList(Wraps.<GasmeterArrearsDetail>lbQ()
                .eq(GasmeterArrearsDetail::getCustomerCode,gasmeterArrearsDetail.getCustomerCode())
                .eq(GasmeterArrearsDetail::getGasmeterCode,gasmeterArrearsDetail.getGasmeterCode())
                .eq(GasmeterArrearsDetail::getDataStatus, DataStatusEnum.NORMAL.getValue())
                .eq(GasmeterArrearsDetail::getArrearsStatus, "UNCHARGE")
                .eq(GasmeterArrearsDetail::getReadmeterMonth,gasmeterArrearsDetail.getReadmeterMonth())
        );
        if(gasmeterArrearsDetails!=null&&gasmeterArrearsDetails.size()>0){return true;}
        else{return false;}
    }

    @Override
    public GasmeterArrearsDetail checkArrearsMonth(GasmeterArrearsDetail gasmeterArrearsDetail) {
        List<GasmeterArrearsDetail> gasmeterArrearsDetails = baseMapper.selectList(Wraps.<GasmeterArrearsDetail>lbQ()
                .eq(GasmeterArrearsDetail::getCustomerCode,gasmeterArrearsDetail.getCustomerCode())
                .eq(GasmeterArrearsDetail::getGasmeterCode,gasmeterArrearsDetail.getGasmeterCode())
                .eq(GasmeterArrearsDetail::getDataStatus, 1)
                .orderByDesc(GasmeterArrearsDetail::getReadmeterMonth)

        );
        if(gasmeterArrearsDetails!=null&&gasmeterArrearsDetails.size()>0){return gasmeterArrearsDetails.get(0);}
        return null;
    }

    @Override
    public Boolean updateBathArrears(List<Long> idsList) {
        int counts = baseMapper.update(null,Wraps.<GasmeterArrearsDetail>lbU()
                .set(GasmeterArrearsDetail::getLatepayAmount,null)
                .set(GasmeterArrearsDetail::getLateFeeStartDate,null)
                .set(GasmeterArrearsDetail::getLatepayDays,null)
                .in(GasmeterArrearsDetail::getId,idsList));
        if(counts>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public BigDecimal stsAfterGasMeter(StsSearchParam stsSearchParam, String type) {
        return this.baseMapper.stsAfterGasMeter(stsSearchParam, type);
    }
}
