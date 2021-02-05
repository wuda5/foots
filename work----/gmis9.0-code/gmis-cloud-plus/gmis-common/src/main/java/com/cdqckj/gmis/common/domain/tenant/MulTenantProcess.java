package com.cdqckj.gmis.common.domain.tenant;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: lijianguo
 * @time: 2020/9/29 18:59
 * @remark: 请输入接口说明
 */
public interface MulTenantProcess {

    /**
     * @auth lijianguo
     * @date 2020/10/12 9:02
     * @remark 获取管理员的租户
     */
    MulTenant getAdminTenant();

    /**
     * @auth lijianguo
     * @date 2020/10/12 9:33
     * @remark 管理平台的租户-返回true
     */
    Boolean adminTenantUser();

    /**
     * @auth lijianguo
     * @date 2020/10/10 13:48
     * @remark 获取所有的租户
     */
    List<MulTenant> getAllTenant();

    /**
     * @auth lijianguo
     * @date 2020/10/10 14:14
     * @remark 获取租户包括管理平台
     */
    List<MulTenant> getAllTenantIncludeAdmin();

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/14 18:07
    * @remark 清空租户的缓存
    */
    void clearTenantCache();

    /**
     * @auth lijianguo
     * @date 2020/11/2 11:03
     * @remark 在其他的租户数据库中运行
     */
    MulTenantResult runInOtherTenant(RealProcess process);
    MulTenantResult runInOtherTenant(RealProcess process, List<MulTenant> mulTenantList);
}
