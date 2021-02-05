package com.cdqckj.gmis.tenant.controller;

import com.cdqckj.gmis.common.domain.tenant.MulTenant;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.tenant.entity.Tenant;
import com.cdqckj.gmis.tenant.service.TenantService;
import com.cdqckj.gmis.utils.BeanPlusUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.List;

/**
 * @author: lijianguo
 * @time: 2020/9/29 15:54
 * @remark: 管理平台的基础类
 */
@Log4j2
public class MainBaseController {

    @Autowired
    private TenantService tenantService;

    @Autowired
    private HttpServletRequest request;

    /**
     * @auth lijianguo
     * @date 2020/9/29 15:54
     * @remark 获取登录用户的Id
     */
    protected long getAdminUserId(){
        Long userId = BaseContextHandler.getUserId();
        return 1L;
    }

    /**
     * @auth lijianguo
     * @date 2020/10/8 11:16
     * @remark 获取所有的租户包括-default
     */
    protected List<MulTenant> getAllTenant(){

        List<Tenant> tenantList = tenantService.getAllTenant();
        Iterator iterator = tenantList.iterator();
        // 方便测试不处理默认的数据库
        while (iterator.hasNext()){
            Tenant tenant = (Tenant) iterator.next();
            if (tenant.getCode().equals("0000")){
                iterator.remove();
            }
        }
        return BeanPlusUtil.toBeanList(tenantList, MulTenant.class);
    }



}
