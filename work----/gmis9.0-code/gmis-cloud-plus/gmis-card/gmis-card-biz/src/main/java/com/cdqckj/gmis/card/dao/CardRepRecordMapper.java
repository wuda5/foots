package com.cdqckj.gmis.card.dao;

import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.card.entity.CardRepRecord;
import com.cdqckj.gmis.card.vo.CardRepRecordVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * 补换卡记录
 * </p>
 *
 * @author tp
 * @date 2020-11-06
 */
@Repository
public interface CardRepRecordMapper extends SuperMapper<CardRepRecord> {

    /**
     * 查询业务关注点补卡记录列表
     *
     * @param gasMeterCode 表具编号
     * @param customerCode 用户编号
     * @return 数据列表
     */
    List<CardRepRecordVO> queryFocusInfo(@Param("customerCode") String customerCode, @Param("gasMeterCode") String gasMeterCode,
                                         @Param("orgIds") List<Long> orgIds);
}
