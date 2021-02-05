package com.cdqckj.gmis.biztemporary.service;

import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.biztemporary.entity.SupplementGasRecord;
import com.cdqckj.gmis.biztemporary.vo.SupplementGasRecordVO;

import java.util.List;

/**
 * <p>
 * 业务接口
 * 补气记录
 * </p>
 *
 * @author gmis
 * @date 2020-12-09
 */
public interface SupplementGasRecordService extends SuperService<SupplementGasRecord> {


    /**
     * 查询业务关注点补卡记录列表
     *
     * @param gasMeterCode 表具编号
     * @param customerCode 用户编号
     * @return 数据列表
     */
    List<SupplementGasRecordVO> queryFocusInfo(String customerCode, String gasMeterCode);
}
