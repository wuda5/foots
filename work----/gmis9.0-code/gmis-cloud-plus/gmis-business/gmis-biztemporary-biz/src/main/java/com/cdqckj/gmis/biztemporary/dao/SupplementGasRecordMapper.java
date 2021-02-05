package com.cdqckj.gmis.biztemporary.dao;

import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.biztemporary.entity.SupplementGasRecord;
import com.cdqckj.gmis.biztemporary.vo.SupplementGasRecordVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * 补气记录
 * </p>
 *
 * @author gmis
 * @date 2020-12-09
 */
@Repository
public interface SupplementGasRecordMapper extends SuperMapper<SupplementGasRecord> {

    /**
     * 查询业务关注点补卡记录列表
     *
     * @param gasMeterCode 表具编号
     * @param customerCode 用户编号
     * @return 数据列表
     */
    List<SupplementGasRecordVO> queryFocusInfo(@Param("customerCode") String customerCode, @Param("gasMeterCode") String gasMeterCode,
                                               @Param("orgIds") List<Long> orgIds);
}
