package com.cdqckj.gmis.card.service;

import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.card.entity.CardRepRecord;
import com.cdqckj.gmis.card.vo.CardRepRecordVO;

import java.util.List;

/**
 * <p>
 * 业务接口
 * 补换卡记录
 * </p>
 *
 * @author tp
 * @date 2020-11-06
 */
public interface CardRepRecordService extends SuperService<CardRepRecord> {

    /**
     * 查询业务关注点补卡记录列表
     *
     * @param gasMeterCode 表具编号
     * @param customerCode 用户编号
     * @return 数据列表
     */
    List<CardRepRecordVO> queryFocusInfo(String customerCode, String gasMeterCode);
}
