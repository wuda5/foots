package com.cdqckj.gmis.biztemporary.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.base.DeleteStatusEnum;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.biztemporary.dao.ChangeMeterRecordMapper;
import com.cdqckj.gmis.biztemporary.dto.BusinessCheckResultDTO;
import com.cdqckj.gmis.biztemporary.dto.ChangeMeterRecordSaveDTO;
import com.cdqckj.gmis.biztemporary.entity.ChangeMeterRecord;
import com.cdqckj.gmis.biztemporary.enums.ChangeTypeEnum;
import com.cdqckj.gmis.biztemporary.service.ChangeMeterRecordService;
import com.cdqckj.gmis.biztemporary.vo.ChangeMeterRecordVO;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.enums.PayStatusEnum;
import com.cdqckj.gmis.common.utils.UserOrgIdUtil;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 业务实现类
 * 气表换表记录
 * </p>
 *
 * @author gmis
 * @date 2020-11-11
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class ChangeMeterRecordServiceImpl extends SuperServiceImpl<ChangeMeterRecordMapper, ChangeMeterRecord> implements ChangeMeterRecordService {


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ChangeMeterRecord addWithMeterInfo(ChangeMeterRecordSaveDTO saveDTO) {

        ChangeMeterRecord model = BeanUtil.toBean(saveDTO, ChangeMeterRecord.class);
        //组装字段
        String changeType = saveDTO.getOldAmountMark() +
                "_TO_" +
                saveDTO.getNewAmountMark();
        model.setChangeType(ChangeTypeEnum.get(changeType).getCode());
        //设置换表场景
        model.setChangeScene(saveDTO.getOldMeterType() + "_TO_" + saveDTO.getNewMeterType());
        //保存数据
        save(model);

        return model;
    }

    /**
     * 检查是否已有换表数据
     *
     * @param oldMeterCode 旧表编号
     * @param customerCode 客户编号
     * @return 校验结果
     */
    @Override
    public BusinessCheckResultDTO<ChangeMeterRecord> check(String oldMeterCode, String customerCode) {
        BusinessCheckResultDTO<ChangeMeterRecord> result = new BusinessCheckResultDTO<>();
        LbqWrapper<ChangeMeterRecord> lbqWrapperOld = Wraps.lbQ();
        lbqWrapperOld.eq(ChangeMeterRecord::getOldMeterCode, oldMeterCode)
                .eq(ChangeMeterRecord::getCustomerCode, customerCode)
                .eq(ChangeMeterRecord::getStatus, PayStatusEnum.WAIT_PAY.getCode())
                .eq(ChangeMeterRecord::getDeleteStatus, DeleteStatusEnum.NORMAL.getValue());

        ChangeMeterRecord oldMeterRecord = baseMapper.selectOne(lbqWrapperOld);

        if (Objects.isNull(oldMeterRecord)) {
            result.setFlag(false);
            result.setStep(1);
            oldMeterRecord = new ChangeMeterRecord();
            oldMeterRecord.setOldMeterCode(oldMeterCode);
            oldMeterRecord.setCustomerCode(customerCode);
        } else {
            result.setFlag(true);
            result.setStep(oldMeterRecord.getStatus());
        }
        result.setBusData(oldMeterRecord);
        return result;
    }

    /**
     * 校验新表数据是否可用
     *
     * @param oldMeterCode 旧表编号
     * @param newMeterCode 新表编号
     * @return 校验结果:true or false
     */
    @Override
    public Boolean validateNewMeter(String oldMeterCode, String newMeterCode) {
        LbqWrapper<ChangeMeterRecord> lbqWrapperOld = Wraps.lbQ();
        lbqWrapperOld.ne(ChangeMeterRecord::getOldMeterCode, oldMeterCode)
                .eq(ChangeMeterRecord::getNewMeterCode, newMeterCode)
                .eq(ChangeMeterRecord::getDeleteStatus, DeleteStatusEnum.NORMAL.getValue());
        ChangeMeterRecord oldMeterRecord = baseMapper.selectOne(lbqWrapperOld);
        return Objects.isNull(oldMeterRecord);
    }

    /**
     * 支付成功后更新数据状态
     *
     * @param id 数据id
     * @return 更新的数据
     */
    @Override
    public ChangeMeterRecord updateAfterPaid(Long id) {
        ChangeMeterRecord record = baseMapper.selectById(id);
        if (Objects.isNull(record)) {
            log.error("未查询到换表记录数据：" + id);
            throw new BizException("未查询到换表记录数据：" + id);
        }
        record.setDeleteStatus(DeleteStatusEnum.NORMAL.getValue());
        record.setStatus(PayStatusEnum.SUCCESS.getCode());
        updateById(record);
        return record;
    }

    /**
     * 业务关注点数据查询
     *
     * @param customerCode 用户编号
     * @param gasMeterCode 表具编号
     * @return 关注数据
     */
    @Override
    public List<ChangeMeterRecordVO> queryFocusInfo(String customerCode, String gasMeterCode) {
        List<Long> orgIds = UserOrgIdUtil.getUserDataScopeList();
        return baseMapper.queryFocusInfo(customerCode, gasMeterCode, orgIds);
    }

    @Override
    public Long stsChangeMeterNum(StsSearchParam stsSearchParam) {
        String dataScope = UserOrgIdUtil.getUserDataScopeStr();
        return this.baseMapper.stsChangeMeterNum(stsSearchParam, dataScope);
    }
}
