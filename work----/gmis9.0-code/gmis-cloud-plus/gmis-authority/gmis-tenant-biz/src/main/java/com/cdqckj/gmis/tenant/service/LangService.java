package com.cdqckj.gmis.tenant.service;

import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.tenant.entity.Lang;

import java.util.List;

/**
 * <p>
 * 业务接口
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-06-08
 */
public interface LangService extends SuperService<Lang> {
    List<Lang> initLang();
}
