package com.cdqckj.gmis.devicearchive.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.domain.sts.StsInfoBaseVo;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.database.mybatis.conditions.update.LbuWrapper;
import com.cdqckj.gmis.devicearchive.dao.CustomerGasMeterRelatedMapper;
import com.cdqckj.gmis.devicearchive.dto.CustomerGasMeterRelatedUpdateDTO;
import com.cdqckj.gmis.devicearchive.entity.CustomerGasMeterRelated;
import com.cdqckj.gmis.devicearchive.service.CustomerGasMeterRelatedService;
import com.cdqckj.gmis.devicearchive.vo.BatchUserDeviceVO;
import com.cdqckj.gmis.devicearchive.vo.MeterRelatedVO;
import com.cdqckj.gmis.userarchive.dto.CustomerDeviceDTO;
import com.cdqckj.gmis.userarchive.vo.CustomerDeviceVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 业务实现类
 *
 * </p>
 *
 * @author gmis
 * @date 2020-07-03
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class CustomerGasMeterRelatedServiceImpl extends SuperServiceImpl<CustomerGasMeterRelatedMapper, CustomerGasMeterRelated> implements CustomerGasMeterRelatedService {
    @Override
    public Page<CustomerDeviceDTO> findCustomerDevicePage(CustomerDeviceVO params) {
        Page<CustomerDeviceDTO> page = new Page<>(params.getPageNo(),params.getPageSize());
        return baseMapper.findCustomerDevicePage(page, params);
    }

    @Override
    public int updateBatchUserDevice(List<BatchUserDeviceVO> userGasVo) {
        return baseMapper.updateBatchUserDevice(userGasVo);
    }

    @Override
    public CustomerGasMeterRelated findOneByCode(String code) {
        LbuWrapper<CustomerGasMeterRelated> customerGasMeterRelatedLbuWrapper=new LbuWrapper<>();
        customerGasMeterRelatedLbuWrapper.eq(CustomerGasMeterRelated::getGasMeterCode,code);
        //add by hc 2020/11/05
        customerGasMeterRelatedLbuWrapper.in(CustomerGasMeterRelated::getDataStatus,DataStatusEnum.NORMAL.getValue());
        return  baseMapper.selectOne(customerGasMeterRelatedLbuWrapper);
    }

    @Override
    public List<CustomerGasMeterRelated> findCustomerAndGasMeterList(CustomerGasMeterRelated customerGasMeterRelated) {
        LbuWrapper<CustomerGasMeterRelated> customerGasMeterRelatedLbuWrapper=new LbuWrapper<>();
        customerGasMeterRelatedLbuWrapper.eq(CustomerGasMeterRelated::getCustomerCode,customerGasMeterRelated.getCustomerCode());
        customerGasMeterRelatedLbuWrapper.eq(CustomerGasMeterRelated::getGasMeterCode,customerGasMeterRelated.getGasMeterCode());
        customerGasMeterRelatedLbuWrapper.eq(CustomerGasMeterRelated::getDataStatus,customerGasMeterRelated.getDataStatus());
        return baseMapper.selectList(customerGasMeterRelatedLbuWrapper);
    }

    @Override
    public List<CustomerGasMeterRelated> findPreOpenAccount() {
        LbuWrapper<CustomerGasMeterRelated> customerGasMeterRelatedLbuWrapper=new LbuWrapper<>();
        customerGasMeterRelatedLbuWrapper.eq(CustomerGasMeterRelated::getDataStatus, DataStatusEnum.NORMAL.getValue());
        return baseMapper.selectList(customerGasMeterRelatedLbuWrapper);
    }

    @Override
    public List<CustomerGasMeterRelated> findNormalOpenAccount() {
        LbuWrapper<CustomerGasMeterRelated> customerGasMeterRelatedLbuWrapper=new LbuWrapper<>();
        customerGasMeterRelatedLbuWrapper.eq(CustomerGasMeterRelated::getDataStatus, DataStatusEnum.NORMAL.getValue());
        return baseMapper.selectList(customerGasMeterRelatedLbuWrapper);
    }

    @Override
    public Boolean check(CustomerGasMeterRelatedUpdateDTO customerGasMeterRelatedUpdateDTO) {
        return super.count(Wraps.<CustomerGasMeterRelated>lbQ()
                .eq(CustomerGasMeterRelated::getGasMeterCode,customerGasMeterRelatedUpdateDTO.getGasMeterCode())
                .eq(CustomerGasMeterRelated::getDataStatus, DataStatusEnum.NORMAL.getValue()))>0;
    }

    @Override
    public Boolean checkChargeNo(CustomerGasMeterRelatedUpdateDTO customerGasMeterRelatedUpdateDTO) {
        return super.count(Wraps.<CustomerGasMeterRelated>lbQ()
                .eq(CustomerGasMeterRelated::getCustomerChargeNo,customerGasMeterRelatedUpdateDTO.getCustomerChargeNo()))>0;
    }

    /**
     * 通过表具code和客户code逻辑删除有效的关联管理
     *
     * @param updateDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CustomerGasMeterRelated deleteByCustomerAndMeter(CustomerGasMeterRelatedUpdateDTO updateDTO) {
        LbqWrapper<CustomerGasMeterRelated> lbqWrapper = Wraps.lbQ();
        lbqWrapper.eq(CustomerGasMeterRelated::getCustomerCode, updateDTO.getCustomerCode())
                .eq(CustomerGasMeterRelated::getGasMeterCode, updateDTO.getGasMeterCode())
                .eq(CustomerGasMeterRelated::getDataStatus, DataStatusEnum.NORMAL.getValue());

        CustomerGasMeterRelated related = baseMapper.selectOne(lbqWrapper);
        related.setDataStatus(DataStatusEnum.NOT_AVAILABLE.getValue());
        related.setOperType(updateDTO.getOperType());
        related.setUpdateTime(LocalDateTime.now());
        related.setUpdateUser(BaseContextHandler.getUserId());
        updateById(related);
        return related;
    }

    /**
     * 通过客户编号查询所有有效的关联关系和表具状态
     *
     * @param customerCode 客户编号
     * @return 关联关系列表
     */
    @Override
    public List<MeterRelatedVO> queryAllRelation(String customerCode) {
        return baseMapper.queryAllRelation(customerCode);
    }

    @Override
    public List<StsInfoBaseVo<String, Long>> accountNowTypeFrom(StsSearchParam stsSearchParam) {
        return this.baseMapper.accountNowTypeFrom(stsSearchParam);
    }
}
