package com.cdqckj.gmis.operateparam.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.operateparam.entity.PurchaseLimit;
import com.cdqckj.gmis.operateparam.vo.PurchaseLimitVO;

import java.util.List;

/**
 * <p>
 * 业务接口
 *
 * </p>
 *
 * @author gmis
 * @date 2020-07-06
 */
public interface PurchaseLimitService extends SuperService<PurchaseLimit> {
    public List<PurchaseLimit> getAllRecord();


    /**
     * 查询用户的限购信息
     *
     * @param pageParams 分页查询参数
     * @return 限购信息列表
     */
    Page<PurchaseLimit> queryCustomerLimitInfo(PageParams<PurchaseLimitVO> pageParams);

}
