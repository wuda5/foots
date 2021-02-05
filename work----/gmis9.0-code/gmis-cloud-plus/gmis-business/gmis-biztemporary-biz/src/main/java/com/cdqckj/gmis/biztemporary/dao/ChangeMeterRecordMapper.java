package com.cdqckj.gmis.biztemporary.dao;

import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.biztemporary.entity.ChangeMeterRecord;
import com.cdqckj.gmis.biztemporary.vo.ChangeMeterRecordVO;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * 气表换表记录
 * </p>
 *
 * @author gmis
 * @date 2020-11-11
 */
@Repository
public interface ChangeMeterRecordMapper extends SuperMapper<ChangeMeterRecord> {

    /**
     * 业务关注点数据查询
     *
     * @param customerCode 用户编号
     * @param gasMeterCode 表具编号
     * @return 关注数据
     */
    List<ChangeMeterRecordVO> queryFocusInfo(@Param("customerCode") String customerCode, @Param("gasMeterCode") String gasMeterCode,
                    @Param("orgIds") List<Long> orgIds);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/16 16:52
    * @remark 换表
    */
    Long stsChangeMeterNum(@Param("stsSearchParam") StsSearchParam stsSearchParam, @Param("dataScope") String dataScope);
}
