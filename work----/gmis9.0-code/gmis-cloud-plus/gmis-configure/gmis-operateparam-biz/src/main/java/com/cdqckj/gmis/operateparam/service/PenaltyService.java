package com.cdqckj.gmis.operateparam.service;

import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.operateparam.dto.PenaltyUpdateDTO;
import com.cdqckj.gmis.operateparam.entity.Penalty;

/**
 * <p>
 * 业务接口
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-06-29
 */
public interface PenaltyService extends SuperService<Penalty> {
    /**
     * 获取最新一条数据
     * @return
     */
    Penalty queryRecentRecord();

    Penalty findPenaltyName(String executeName);
    String check(PenaltyUpdateDTO updateDTO);
    /**
     * 根据用气类型id查询滞纳金
     * @param useGasId
     * @return
     */
    Penalty queryByUseGasId(Long useGasId);
}
