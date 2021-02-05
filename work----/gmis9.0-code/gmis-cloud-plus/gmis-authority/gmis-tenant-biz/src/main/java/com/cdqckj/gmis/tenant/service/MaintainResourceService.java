package com.cdqckj.gmis.tenant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cdqckj.gmis.authority.entity.auth.Menu;
import com.cdqckj.gmis.authority.entity.auth.Resource;

import java.util.List;

/**
 * @author: lijianguo
 * @time: 2020/9/29 14:52
 * @remark: 运维平台的菜单的资源
 */
public interface MaintainResourceService extends IService<Resource> {

    /**
     * @auth lijianguo
     * @date 2020/9/29 15:05
     * @remark 这个菜单上面的资源
     */
    List<Resource> getMenuResource(Long menuId);
}
