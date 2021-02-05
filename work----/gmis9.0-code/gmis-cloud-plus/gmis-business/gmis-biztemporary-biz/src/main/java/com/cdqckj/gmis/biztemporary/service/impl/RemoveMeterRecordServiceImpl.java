package com.cdqckj.gmis.biztemporary.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.base.DeleteStatusEnum;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.biztemporary.dao.RemoveMeterRecordMapper;
import com.cdqckj.gmis.biztemporary.dto.BusinessCheckResultDTO;
import com.cdqckj.gmis.biztemporary.entity.RemoveMeterRecord;
import com.cdqckj.gmis.biztemporary.service.RemoveMeterRecordService;
import com.cdqckj.gmis.biztemporary.vo.RemoveMeterRecordVO;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.utils.UserOrgIdUtil;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 业务实现类
 * 气表拆表记录
 * </p>
 *
 * @author gmis
 * @date 2020-11-11
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class RemoveMeterRecordServiceImpl extends SuperServiceImpl<RemoveMeterRecordMapper, RemoveMeterRecord> implements RemoveMeterRecordService {
    /**
     * 拆表前检查：是否已有记录
     *
     * @param gasMeterCode 表具编号
     * @param customerCode 客户编号
     * @return 检查结果
     */
    @Override
    public BusinessCheckResultDTO<RemoveMeterRecord> check(String gasMeterCode, String customerCode) {
        BusinessCheckResultDTO<RemoveMeterRecord> result = new BusinessCheckResultDTO<>();
        LbqWrapper<RemoveMeterRecord> lbqWrapper = Wraps.lbQ();
        lbqWrapper.eq(RemoveMeterRecord::getMeterCode, gasMeterCode)
                .eq(RemoveMeterRecord::getCustomerCode, customerCode)
                .eq(RemoveMeterRecord::getDeleteStatus, DeleteStatusEnum.NORMAL.getValue());
        RemoveMeterRecord removeMeterRecord = baseMapper.selectOne(lbqWrapper);
        if (Objects.isNull(removeMeterRecord)) {
            result.setFlag(false);
            result.setStep(1);
            removeMeterRecord = new RemoveMeterRecord();
            removeMeterRecord.setMeterCode(gasMeterCode);
            removeMeterRecord.setCustomerCode(customerCode);
        } else {
            result.setFlag(true);
            result.setStep(removeMeterRecord.getStatus());
            result.setBusData(removeMeterRecord);
        }
        return result;
    }

    /**
     * 业务关注点数据查询
     *
     * @param customerCode 用户编号
     * @param gasMeterCode 表具编号
     * @return 关注数据
     */
    @Override
    public List<RemoveMeterRecordVO> queryFocusInfo(String customerCode, String gasMeterCode) {
        List<Long> orgIds = UserOrgIdUtil.getUserDataScopeList();
        return baseMapper.queryFocusInfo(customerCode, gasMeterCode, orgIds);
    }

    @Override
    public Long stsRemoveMeterNum(StsSearchParam stsSearchParam) {
        String dataScope = UserOrgIdUtil.getUserDataScopeStr();
        return this.baseMapper.stsRemoveMeterNum(stsSearchParam, dataScope);
    }
}
