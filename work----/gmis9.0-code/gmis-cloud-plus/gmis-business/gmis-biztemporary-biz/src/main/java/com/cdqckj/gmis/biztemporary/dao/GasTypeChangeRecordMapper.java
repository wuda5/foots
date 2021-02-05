package com.cdqckj.gmis.biztemporary.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.biztemporary.entity.GasTypeChangeRecord;
import com.cdqckj.gmis.biztemporary.vo.GasTypeChangeRecordVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * 客户用气类型变更记录
 * </p>
 *
 * @author gmis
 * @date 2020-11-16
 */
@Repository
public interface GasTypeChangeRecordMapper extends SuperMapper<GasTypeChangeRecord> {

    /**
     * 批量查询
     * @author hc
     * @return
     */
    @Select("SELECT cr.*,au.`name` operator FROM gt_gas_type_change_record cr LEFT JOIN c_auth_user au on au.id = cr.create_user ${ew.customSqlSegment} limit 20")
    @ResultMap(value = "BaseResultMap")
    List<HashMap<String, Object>> queryEx(@Param(Constants.WRAPPER) Wrapper<GasTypeChangeRecord> wrapper);


    /**
     * 业务关注点用气类型变更记录列表查询
     *
     * @param queryInfo 查询参数
     * @return 变更记录列表
     */
    List<GasTypeChangeRecordVO> queryFocusInfo(@Param("queryInfo") GasTypeChangeRecord queryInfo, @Param("orgIds") List<Long> orgIds);
}
