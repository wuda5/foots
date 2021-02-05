package com.cdqckj.gmis.operateparam.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.operateparam.dao.PurchaseLimitMapper;
import com.cdqckj.gmis.operateparam.entity.PurchaseLimit;
import com.cdqckj.gmis.operateparam.service.PurchaseLimitService;
import com.cdqckj.gmis.operateparam.vo.PurchaseLimitVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 业务实现类
 *
 * </p>
 *
 * @author gmis
 * @date 2020-07-06
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class PurchaseLimitServiceImpl extends SuperServiceImpl<PurchaseLimitMapper, PurchaseLimit> implements PurchaseLimitService {
    @Override
    public List<PurchaseLimit> getAllRecord() {
        LbqWrapper<PurchaseLimit> wrapper = new LbqWrapper<PurchaseLimit>();
        wrapper.eq(PurchaseLimit::getDataStatus, 1);
        return baseMapper.selectList(wrapper);
    }

    /**
     * 查询用户的限购信息
     *
     * @param pageParams 分页查询参数
     * @return 限购信息列表
     */
    @Override
    public Page<PurchaseLimit> queryCustomerLimitInfo(PageParams<PurchaseLimitVO> pageParams) {
        IPage<PurchaseLimit> page = pageParams.getPage();
        return baseMapper.queryCustomerLimitInfo(page, pageParams.getModel());
    }

}
