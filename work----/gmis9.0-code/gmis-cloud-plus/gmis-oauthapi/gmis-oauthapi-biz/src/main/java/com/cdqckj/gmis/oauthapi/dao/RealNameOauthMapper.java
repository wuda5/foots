package com.cdqckj.gmis.oauthapi.dao;

import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.oauthapi.entity.RealNameOauth;

import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * app应用实名认证表：租户客户关联表
 * </p>
 *
 * @author gmis
 * @date 2020-10-13
 */
@Repository
public interface RealNameOauthMapper extends SuperMapper<RealNameOauth> {

}
