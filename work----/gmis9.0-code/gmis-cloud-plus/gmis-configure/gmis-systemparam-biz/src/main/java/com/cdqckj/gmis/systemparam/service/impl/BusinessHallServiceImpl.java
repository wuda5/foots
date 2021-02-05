package com.cdqckj.gmis.systemparam.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.authority.entity.core.Station;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.database.mybatis.auth.DataScope;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.injection.annonation.InjectionResult;
import com.cdqckj.gmis.systemparam.dao.BusinessHallMapper;
import com.cdqckj.gmis.systemparam.dto.BusinessHallPageDTO;
import com.cdqckj.gmis.systemparam.entity.BusinessHall;
import com.cdqckj.gmis.systemparam.service.BusinessHallService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 营业厅信息表
 * </p>
 *
 * @author gmis
 * @date 2020-07-01
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class BusinessHallServiceImpl extends SuperServiceImpl<BusinessHallMapper, BusinessHall> implements BusinessHallService {

    @Override
    @InjectionResult
    public IPage<BusinessHall> findPage(IPage<BusinessHall> page, BusinessHallPageDTO businessHallPageDTO) {
        BusinessHall businessHall = BeanUtil.toBean(businessHallPageDTO, BusinessHall.class);

        //Wraps.lbQ(station); 这种写法值 不能和  ${ew.customSqlSegment} 一起使用
        LbqWrapper<BusinessHall> wrapper = Wraps.lbQ();

        // ${ew.customSqlSegment} 语法一定要手动eq like 等
        wrapper.like(BusinessHall::getBusinessHallName, businessHall.getBusinessHallName())
                .eq(BusinessHall::getBusinessHallStatus, businessHall.getBusinessHallStatus())
                .eq(BusinessHall::getDeleteStatus, businessHall.getDeleteStatus());

        return baseMapper.findPage(page, wrapper, new DataScope());
    }

    @Override
    public BusinessHall queryByOrgId(Long orgId) {
        LbqWrapper<BusinessHall> wrapper = new LbqWrapper<>();
        wrapper.eq(BusinessHall::getOrgId,orgId);
        return getBaseMapper().selectOne(wrapper);
    }
}
