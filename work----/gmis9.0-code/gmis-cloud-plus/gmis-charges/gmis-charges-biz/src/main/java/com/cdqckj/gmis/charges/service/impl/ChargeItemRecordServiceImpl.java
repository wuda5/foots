package com.cdqckj.gmis.charges.service.impl;

import cn.hutool.core.util.PageUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.charges.dto.ChargeItemRecordPageDTO;
import com.cdqckj.gmis.charges.dto.ChargeItemRecordResDTO;
import com.cdqckj.gmis.charges.vo.ChargeItemVO;
import com.cdqckj.gmis.charges.dao.ChargeItemRecordMapper;
import com.cdqckj.gmis.charges.entity.ChargeItemRecord;
import com.cdqckj.gmis.charges.service.ChargeItemRecordService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.domain.sts.StsInfoBasePlusVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 业务实现类
 * 缴费项记录
 * </p>
 *
 * @author tp
 * @date 2020-09-07
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class ChargeItemRecordServiceImpl extends SuperServiceImpl<ChargeItemRecordMapper, ChargeItemRecord> implements ChargeItemRecordService {

    @Override
    public List<ChargeItemRecord> getChangeItemByChargeNo(String chargeNo) {
        return this.baseMapper.getChangeItemByChargeNo(chargeNo);
    }

    /**
     * 查询表具燃气费最后一次缴费时间和缴费次数
     *
     * @param gasMeterCode 表具编号
     * @return 查询结果
     */
    @Override
    public ChargeItemVO getLastUpdateTimeAndCount(String gasMeterCode) {
        return baseMapper.getLastUpdateTimeAndCount(gasMeterCode);
    }
    public Page<ChargeItemRecordResDTO> pageListByMeterCustomerCode(PageParams<ChargeItemRecordPageDTO> params){
        Page<ChargeItemRecordResDTO> page=new Page<>();
        int count=baseMapper.pageListByMeterCustomerCodeCount(params.getModel());
        List<ChargeItemRecordResDTO> list= baseMapper.pageListByMeterCustomerCode(params.getModel(), PageUtil.getStart((int)params.getCurrent()-1,(int)params.getSize()),(int)params.getSize());
        page.setRecords(list);
        page.setCurrent(params.getCurrent());
        page.setPages(PageUtil.totalPage(count,(int)params.getSize()));
        page.setSize(params.getSize());
        page.setTotal(count);
         return page;
    }

    @Override
    public List<StsInfoBasePlusVo<String, Long>> stsGasFeeAndType(StsSearchParam stsSearchParam) {
        return this.baseMapper.stsGasFeeAndType(stsSearchParam);
    }

    @Override
    public BigDecimal stsCardGasMeter(StsSearchParam stsSearchParam) {
        return this.baseMapper.stsCardGasMeter(stsSearchParam);
    }
}
