package com.cdqckj.gmis.pay.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.pay.dao.WxAppMapper;
import com.cdqckj.gmis.pay.entity.WxApp;
import com.cdqckj.gmis.pay.service.WxAppService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-06-04
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class WxAppServiceImpl extends SuperServiceImpl<WxAppMapper, WxApp> implements WxAppService {
}
