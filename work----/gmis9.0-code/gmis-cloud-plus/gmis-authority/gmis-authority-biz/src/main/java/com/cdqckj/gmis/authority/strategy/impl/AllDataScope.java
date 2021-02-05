package com.cdqckj.gmis.authority.strategy.impl;

import com.cdqckj.gmis.authority.entity.core.Org;
import com.cdqckj.gmis.authority.strategy.AbstractDataScopeHandler;
import com.cdqckj.gmis.authority.service.core.OrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 所有
 *
 * @author gmis
 * @version 1.0
 * @date 2019-06-08 16:27
 */
@Component("ALL")
public class AllDataScope implements AbstractDataScopeHandler {

    @Autowired
    private OrgService orgService;

    @Override
    public List<Long> getOrgIds(List<Long> orgList, Long userId) {
        List<Org> list = orgService.lambdaQuery().select(Org::getId).list();
        return list.stream().map(Org::getId).collect(Collectors.toList());
    }


}
