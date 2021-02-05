package com.cdqckj.gmis.tenant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cdqckj.gmis.authority.entity.auth.Menu;

import java.util.List;

/**
 * @author: lijianguo
 * @time: 2020/9/29 11:02
 * @remark: 运维平台的菜单处理
 */
public interface MaintainMenuService extends IService<Menu> {

    /**
     * @auth lijianguo
     * @date 2020/9/29 11:44
     * @remark 获取所有的菜单
     */
    List<Menu> getAllMenuList();

}
