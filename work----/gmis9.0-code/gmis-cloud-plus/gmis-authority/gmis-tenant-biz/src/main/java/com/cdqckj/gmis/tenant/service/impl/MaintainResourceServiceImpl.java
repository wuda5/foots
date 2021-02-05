package com.cdqckj.gmis.tenant.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.authority.dao.auth.ResourceMapper;
import com.cdqckj.gmis.authority.entity.auth.Resource;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.tenant.service.MaintainResourceService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: lijianguo
 * @time: 2020/9/29 14:53
 * @remark: 请输入类说明
 */
@Log4j2
@Service
@DS("master")
public class MaintainResourceServiceImpl extends SuperServiceImpl<ResourceMapper, Resource> implements MaintainResourceService {

    @Override
    public List<Resource> getMenuResource(Long menuId) {
        return this.baseMapper.getMenuResource(menuId);
    }
}
