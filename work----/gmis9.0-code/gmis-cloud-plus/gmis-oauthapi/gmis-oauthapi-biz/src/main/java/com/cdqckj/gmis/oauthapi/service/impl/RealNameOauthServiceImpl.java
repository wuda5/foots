package com.cdqckj.gmis.oauthapi.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.oauthapi.dao.RealNameOauthMapper;
import com.cdqckj.gmis.oauthapi.entity.RealNameOauth;
import com.cdqckj.gmis.oauthapi.service.RealNameOauthService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * app应用实名认证表：租户客户关联表
 * </p>
 *
 * @author gmis
 * @date 2020-10-13
 */
@Slf4j
@Service
@DS("master")
public class RealNameOauthServiceImpl extends SuperServiceImpl<RealNameOauthMapper, RealNameOauth> implements RealNameOauthService {
}
