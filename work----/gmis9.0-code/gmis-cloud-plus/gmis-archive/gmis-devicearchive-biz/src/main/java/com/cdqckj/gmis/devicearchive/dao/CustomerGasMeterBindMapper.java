package com.cdqckj.gmis.devicearchive.dao;

import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.devicearchive.dto.GasMeterBindPrame;
import com.cdqckj.gmis.devicearchive.dto.GasMeterBindResult;
import com.cdqckj.gmis.devicearchive.entity.CustomerGasMeterBind;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * 客户气表绑定关系表 - 代缴业务
 * </p>
 *
 * @author wanghuaqiao
 * @date 2020-10-16
 */
@Repository
public interface CustomerGasMeterBindMapper extends SuperMapper<CustomerGasMeterBind> {

    List<GasMeterBindResult> getGasMeterInfo(@Param("gasMeterBindPrame") GasMeterBindPrame gasMeterBindPrame);
}
