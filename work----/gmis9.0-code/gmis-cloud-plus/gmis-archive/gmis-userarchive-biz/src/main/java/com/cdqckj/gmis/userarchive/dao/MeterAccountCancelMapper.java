package com.cdqckj.gmis.userarchive.dao;

import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.domain.sts.StsInfoBaseVo;
import com.cdqckj.gmis.userarchive.entity.MeterAccountCancel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * 销户记录
 * </p>
 *
 * @author gmis
 * @date 2020-12-14
 */
@Repository
public interface MeterAccountCancelMapper extends SuperMapper<MeterAccountCancel> {

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/19 16:27
    * @remark 销户
    */
    List<StsInfoBaseVo<String, Long>> stsCancelCustomerType(@Param("stsSearchParam") StsSearchParam stsSearchParam);
}
