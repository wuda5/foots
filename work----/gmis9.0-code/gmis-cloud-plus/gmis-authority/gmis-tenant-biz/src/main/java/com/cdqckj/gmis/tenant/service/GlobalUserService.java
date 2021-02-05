package com.cdqckj.gmis.tenant.service;

import com.cdqckj.gmis.tenant.entity.GlobalUser;
import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.tenant.dto.GlobalUserSaveDTO;
import com.cdqckj.gmis.tenant.dto.GlobalUserUpdateDTO;

/**
 * <p>
 * 业务接口
 * 全局账号
 * </p>
 *
 * @author gmis
 * @date 2019-10-25
 */
public interface GlobalUserService extends SuperService<GlobalUser> {

    /**
     * 检测账号是否可用
     *
     * @param account
     * @return
     */
    Boolean check(String account);

    /**
     * 新建用户
     *
     * @param data
     * @return
     */
    GlobalUser save(GlobalUserSaveDTO data);


    /**
     * 修改
     *
     * @param data
     * @return
     */
    GlobalUser update(GlobalUserUpdateDTO data);
}
