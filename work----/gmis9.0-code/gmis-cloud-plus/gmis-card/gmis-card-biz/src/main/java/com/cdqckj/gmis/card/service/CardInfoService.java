package com.cdqckj.gmis.card.service;

import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.card.entity.CardInfo;
import com.cdqckj.gmis.card.entity.ConcernsCardInfo;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;

/**
 * <p>
 * 业务接口
 * 气表卡信息
 * </p>
 *
 * @author tp
 * @date 2020-11-06
 */
public interface CardInfoService extends SuperService<CardInfo> {
    ConcernsCardInfo getConcernsCardInfo(String gasCode,String customerCode);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/21 10:39
    * @remark 统计卡的数量
    */
    Long stsSendCardNum(StsSearchParam stsSearchParam);
}
