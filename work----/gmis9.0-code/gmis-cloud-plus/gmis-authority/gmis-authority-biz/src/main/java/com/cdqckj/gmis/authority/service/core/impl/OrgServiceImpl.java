package com.cdqckj.gmis.authority.service.core.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.authority.dao.core.OrgMapper;
import com.cdqckj.gmis.authority.entity.auth.RoleOrg;
import com.cdqckj.gmis.authority.entity.core.Org;
import com.cdqckj.gmis.authority.service.auth.RoleOrgService;
import com.cdqckj.gmis.authority.service.auth.UserService;
import com.cdqckj.gmis.authority.service.core.OrgService;
import com.cdqckj.gmis.base.service.SuperCacheServiceImpl;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.utils.MapHelper;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import static com.cdqckj.gmis.common.constant.CacheKey.ORG;

/**
 * <p>
 * 业务实现类
 * 组织
 * </p>
 *
 * @author gmis
 * @date 2019-07-22
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class OrgServiceImpl extends SuperCacheServiceImpl<OrgMapper, Org> implements OrgService {
    @Autowired
    private RoleOrgService roleOrgService;
    @Autowired
    private UserService userService;

    @Override
    protected String getRegion() {
        return ORG;
    }

    @Override
    public List<Org> findChildren(List<Long> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        // MySQL 全文索引
        String applySql = String.format(" MATCH(tree_path) AGAINST('%s' IN BOOLEAN MODE) ", StringUtils.join(ids, " "));

        return super.list(Wraps.<Org>lbQ().in(Org::getId, ids).or(query -> query.apply(applySql)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean remove(List<Long> ids) {
        List<Org> list = this.findChildren(ids);
        List<Long> idList = list.stream().mapToLong(Org::getId).boxed().collect(Collectors.toList());

        boolean bool = !idList.isEmpty() ? super.removeByIds(idList) : true;

        // 删除自定义类型的数据权限范围
        roleOrgService.remove(Wraps.<RoleOrg>lbQ().in(RoleOrg::getOrgId, idList));
        return bool;
    }

    @Override
    public Map<Serializable, Object> findOrgByIds(Set<Serializable> ids) {
        List<Org> list = getOrgs(ids);

        //key 是 组织id， value 是org 对象
        ImmutableMap<Serializable, Object> typeMap = MapHelper.uniqueIndex(list, Org::getId, (org) -> org);
        return typeMap;
    }

    private List<Org> getOrgs(Set<Serializable> ids) {
        if (ids.isEmpty()) {
            return Collections.emptyList();
        }
        List<Long> idList = ids.stream().mapToLong(Convert::toLong).boxed().collect(Collectors.toList());

        List<Org> list = null;
        if (idList.size() <= 1000) {
            list = idList.stream().map(this::getByIdCache).filter(Objects::nonNull).collect(Collectors.toList());
        } else {
            LbqWrapper<Org> query = Wraps.<Org>lbQ()
                    .in(Org::getId, idList)
                    .eq(Org::getStatus, true);
            list = super.list(query);

            if (!list.isEmpty()) {
                list.forEach(item -> {
                    String itemKey = key(item.getId());
                    cacheChannel.set(getRegion(), itemKey, item);
                });
            }
        }
        return list;
    }

    @Override
    public Map<Serializable, Object> findOrgNameByIds(Set<Serializable> ids) {
        List<Org> list = getOrgs(ids);

        //key 是 组织id， value 是org 对象
        ImmutableMap<Serializable, Object> typeMap = MapHelper.uniqueIndex(list, Org::getId, Org::getLabel);
        return typeMap;
    }

    @Override
    public List<Org> findOrgPage(String name, Boolean status) {
        Org org = new Org();
        org.setLabel(name);
        org.setStatus(status);
        org.setStatus(status);
        Long userId = BaseContextHandler.getUserId();
        Map<String, Object> map = userService.getDataScopeById(userId);
        List<Long> orgIds = (List<Long>) map.get("orgIds");
        LbqWrapper<Org> wrapper = Wraps.lbQ();
        // ${ew.customSqlSegment} 语法一定要手动eq like 等
        wrapper.like(Org::getLabel, org.getLabel())
                .in(Org::getId,orgIds)
                .eq(Org::getStatus, org.getStatus()).orderByAsc(Org::getSortValue).orderByAsc(Org::getCreateTime);
        List<Org> orgList = baseMapper.findOrgList(wrapper);
        List<Org> list = baseMapper.selectList(null);
        return buildOrgList(list,orgList);
    }

    /**
     * 获取用户所属组织及其父级组织
      * @param list
     * @param orgList
     * @return
     */
    private List<Org> buildOrgList(List<Org> list,List<Org> orgList){
        for(int n=0;n<list.size();n++){
            Org org = list.get(n);
            for(int i=0;i<orgList.size();i++){
                Org node = orgList.get(i);
                if(orgList.contains(org)){
                    continue;
                }
                if(node.getParentId()!=0){
                    if(node.getParentId().equals(org.getId())){
                        orgList.add(org);
                        this.buildOrgList(list,orgList);
                    }
                }
            }
        }
        return orgList;
    }

    @Override
    public List<Org> allOnlineOrg() {
        return this.baseMapper.allOnlineOrg();
    }

    @Override
    public List<Org> userAllOrg(Long userId) {
        return this.baseMapper.userAllOrg(userId);
    }
}
