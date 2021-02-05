package com.cdqckj.gmis.operateparam.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.database.mybatis.auth.DataScope;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.database.mybatis.conditions.query.QueryWrap;
import com.cdqckj.gmis.operateparam.dao.StreetMapper;
import com.cdqckj.gmis.operateparam.dto.StreetPageDTO;
import com.cdqckj.gmis.operateparam.dto.StreetUpdateDTO;
import com.cdqckj.gmis.operateparam.entity.Community;
import com.cdqckj.gmis.operateparam.entity.Street;
import com.cdqckj.gmis.operateparam.service.CommunityService;
import com.cdqckj.gmis.operateparam.service.StreetService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 业务实现类
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-07-14
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class StreetServiceImpl extends SuperServiceImpl<StreetMapper, Street> implements StreetService {
    @Autowired
    public CommunityService communityService;
    @Override
    public Boolean deleteStreetAndCommunity(List<Long> ids) {
        for(Long streetId:ids){
            LbqWrapper<Community> wrapper = new LbqWrapper<>();
            wrapper.eq(Community::getStreetId,streetId);
            List<Community> communityList = communityService.getBaseMapper().selectList(wrapper);
            if(communityList!=null&&communityList.size()>0){
                communityList.stream().forEach(e->e.setDeleteStatus(1));
                return communityService.updateBatchById(communityList);
            }
        }
        return false;
    }


    public IPage<Street> findPage(IPage<Street> page, StreetPageDTO streetPageDTO) {
        Street street = BeanUtil.toBean(streetPageDTO, Street.class);
        //Wraps.lbQ(station); 这种写法值 不能和  ${ew.customSqlSegment} 一起使用
        LbqWrapper<Street> wrapper = Wraps.lbQ();
        wrapper.like(Street::getStreetName, street.getStreetName());
        wrapper.eq(Street::getProvinceCode,street.getProvinceCode());
        wrapper.eq(Street::getCityCode,street.getCityCode());
        wrapper.eq(Street::getAreaCode,street.getAreaCode());
        // ${ew.customSqlSegment} 语法一定要手动eq like 等
        wrapper.eq(Street::getDeleteStatus, street.getDeleteStatus());
        wrapper.eq(Street::getDataStatus, street.getDataStatus());
        return baseMapper.findPage(page, wrapper, new DataScope());
    }

    @Override
    public Boolean check(StreetUpdateDTO streetUpdateDTO) {

        LbqWrapper<Street> wraps = Wraps.<Street>lbQ()
                .eq(Street::getProvinceCode,streetUpdateDTO.getProvinceCode())
                .ne(Street::getId,streetUpdateDTO.getId())
                .eq(Street::getAreaCode,streetUpdateDTO.getAreaCode()).eq(Street::getCityCode,streetUpdateDTO.getCityCode())
                .eq(Street::getStreetName,streetUpdateDTO.getStreetName());
        wraps.setTheDataScope(new DataScope());
        Boolean result = super.count(wraps)>0;
        return result;

    }
}
