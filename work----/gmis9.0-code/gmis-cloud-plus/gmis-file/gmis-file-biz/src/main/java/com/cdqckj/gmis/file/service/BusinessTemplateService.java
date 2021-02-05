package com.cdqckj.gmis.file.service;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.file.entity.BusinessTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 业务接口
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-08-14
 */
public interface BusinessTemplateService extends SuperService<BusinessTemplate> {

    public R<Boolean> exportTemplateFile(String templateCode,
                                    HttpServletRequest request, HttpServletResponse response);
    public R<String> exportTemplate(String templateCode);

}
