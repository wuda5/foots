package com.cdqckj.gmis.authority.strategy.impl;

import com.cdqckj.gmis.authority.entity.core.Org;
import com.cdqckj.gmis.authority.strategy.AbstractDataScopeHandler;
import com.cdqckj.gmis.authority.service.core.OrgService;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.exception.code.ExceptionCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义模式
 *
 * @author gmis
 * @version 1.0
 * @date 2019-06-08 16:31
 */
@Component("CUSTOMIZE")
public class CustomizeDataScope implements AbstractDataScopeHandler {

    @Autowired
    private OrgService orgService;

    @Override
    public List<Long> getOrgIds(List<Long> orgList, Long userId) {
        if (orgList == null || orgList.isEmpty()) {
            throw new BizException(ExceptionCode.BASE_VALID_PARAM.getCode(), "自定义数据权限类型时，组织不能为空","Organization cannot be empty when defining data permission type");
        }
        List<Org> children = orgService.findChildren(orgList);
        return children.stream().mapToLong(Org::getId).boxed().collect(Collectors.toList());
    }
}
