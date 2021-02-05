package com.cdqckj.gmis.biztemporary.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.biztemporary.dao.SupplementGasRecordMapper;
import com.cdqckj.gmis.biztemporary.entity.SupplementGasRecord;
import com.cdqckj.gmis.biztemporary.service.SupplementGasRecordService;
import com.cdqckj.gmis.biztemporary.vo.SupplementGasRecordVO;
import com.cdqckj.gmis.common.utils.UserOrgIdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 业务实现类
 * 补气记录
 * </p>
 *
 * @author gmis
 * @date 2020-12-09
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class SupplementGasRecordServiceImpl extends SuperServiceImpl<SupplementGasRecordMapper, SupplementGasRecord> implements SupplementGasRecordService {
    /**
     * 查询业务关注点补卡记录列表
     *
     * @param customerCode 用户编号
     * @param gasMeterCode 表具编号
     * @return 数据列表
     */
    @Override
    public List<SupplementGasRecordVO> queryFocusInfo(String customerCode, String gasMeterCode) {
        List<Long> orgIds = UserOrgIdUtil.getUserDataScopeList();
        return baseMapper.queryFocusInfo(customerCode, gasMeterCode, orgIds);
    }
}
