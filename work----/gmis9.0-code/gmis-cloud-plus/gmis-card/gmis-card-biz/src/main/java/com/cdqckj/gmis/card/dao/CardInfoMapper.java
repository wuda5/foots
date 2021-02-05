package com.cdqckj.gmis.card.dao;

import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.card.entity.CardInfo;
import com.cdqckj.gmis.card.entity.ConcernsCardInfo;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * 气表卡信息
 * </p>
 *
 * @author tp
 * @date 2020-11-06
 */
@Repository
public interface CardInfoMapper extends SuperMapper<CardInfo> {
    /**
     * 获取兴趣关注点IC卡信息
     * @param gasCode
     * @return
     */
    ConcernsCardInfo getConcernsCardInfo(@Param("gasCode") String gasCode,@Param("customerCode") String customerCode);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/21 10:40
    * @remark 统计卡的数量
    */
    Long stsSendCardNum(@Param("stsSearchParam") StsSearchParam stsSearchParam, @Param("dataScope") String dataScope);
}
