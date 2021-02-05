package com.cdqckj.gmis.operateparam.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.database.mybatis.auth.DataScope;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.operateparam.dao.CommunityMapper;
import com.cdqckj.gmis.operateparam.dto.CommunityPageDTO;
import com.cdqckj.gmis.operateparam.dto.CommunitySaveDTO;
import com.cdqckj.gmis.operateparam.dto.CommunityUpdateDTO;
import com.cdqckj.gmis.operateparam.entity.Community;
import com.cdqckj.gmis.operateparam.entity.Street;
import com.cdqckj.gmis.operateparam.service.CommunityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 *
 * </p>
 *
 * @author gmis
 * @date 2020-06-22
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class CommunityServiceImpl extends SuperServiceImpl<CommunityMapper, Community> implements CommunityService {

    @Override
    public IPage<Community> getCommunityListByStreetId(Integer pageNo, Integer pageSize, Long streetId) {
        IPage<Community> page = new Page<>(pageNo,pageSize);
        LbqWrapper<Community> wrapper = new LbqWrapper<>();
        wrapper.eq(Community::getStreetId,streetId);
        IPage<Community> communityIPage = baseMapper.selectPage(page,wrapper);
        return communityIPage;
    }

    @Override
    public IPage<Community> findPage(IPage<Community> page, CommunityPageDTO communityPageDTO) {
        Community community = BeanUtil.toBean(communityPageDTO, Community.class);
        //Wraps.lbQ(station); 这种写法值 不能和  ${ew.customSqlSegment} 一起使用
        LbqWrapper<Community> wrapper = Wraps.lbQ();
        wrapper.like(Community::getCommunityName, community.getCommunityName());
        wrapper.eq(Community::getStreetId,community.getStreetId());
        // ${ew.customSqlSegment} 语法一定要手动eq like 等
        wrapper.eq(Community::getDeleteStatus, community.getDeleteStatus());
        wrapper.eq(Community::getDataStatus, community.getDataStatus());
        return baseMapper.findPage(page, wrapper, new DataScope());
    }

    @Override
    public Boolean check(CommunityUpdateDTO communityUpdateDTO) {
        return  super.count(Wraps.<Community>lbQ()
                .eq(Community::getCommunityName, communityUpdateDTO.getCommunityName()).
                        ne(Community::getId,communityUpdateDTO.getId()).
                        eq(Community::getStreetId,communityUpdateDTO.getStreetId())) > 0;
    }
}
