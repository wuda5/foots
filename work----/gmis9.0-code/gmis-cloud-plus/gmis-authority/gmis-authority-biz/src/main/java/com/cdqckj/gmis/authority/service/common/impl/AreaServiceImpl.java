package com.cdqckj.gmis.authority.service.common.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.authority.dao.common.AreaMapper;
import com.cdqckj.gmis.authority.entity.common.Area;
import com.cdqckj.gmis.authority.service.common.AreaService;
import com.cdqckj.gmis.base.service.SuperCacheServiceImpl;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.cdqckj.gmis.common.constant.CacheKey.AREA;

/**
 * <p>
 * 业务实现类
 * 地区表
 * </p>
 *
 * @author gmis
 * @date 2019-07-02
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class AreaServiceImpl extends SuperCacheServiceImpl<AreaMapper, Area> implements AreaService {

    @Override
    protected String getRegion() {
        return AREA;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean recursively(List<Long> ids) {
        boolean removeFlag = removeByIds(ids);
        delete(ids);
        return removeFlag;
    }

    @Override
    public List<Area> queryEx() {

        return baseMapper.queryEx();
    }

    private void delete(List<Long> ids) {
        // 查询子节点
        List<Long> childIds = super.listObjs(Wraps.<Area>lbQ().select(Area::getId).in(Area::getParentId, ids), Convert::toLong);
        if (!childIds.isEmpty()) {
            removeByIds(childIds);
            delete(childIds);
        }
        log.debug("退出地区数据递归");
    }
}
