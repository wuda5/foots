package com.cdqckj.gmis.operateparam.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.operateparam.entity.PurchaseLimit;
import com.cdqckj.gmis.operateparam.vo.PurchaseLimitVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 *
 * </p>
 *
 * @author gmis
 * @date 2020-07-06
 */
@Repository
public interface PurchaseLimitMapper extends SuperMapper<PurchaseLimit> {

    /**
     * 分页查新用户的限购信息列表
     *
     * @param page    分页对象
     * @param queryParams 查询条件
     * @return 限购信息列表
     */
    Page<PurchaseLimit> queryCustomerLimitInfo(IPage<PurchaseLimit> page, @Param("queryParams")PurchaseLimitVO queryParams );
}
