package com.cdqckj.gmis.systemparam.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.authority.entity.auth.User;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.systemparam.dto.BusinessHallPageDTO;
import com.cdqckj.gmis.systemparam.entity.BusinessHall;

/**
 * <p>
 * 业务接口
 * 营业厅信息表
 * </p>
 *
 * @author gmis
 * @date 2020-07-01
 */
public interface BusinessHallService extends SuperService<BusinessHall> {
    /**
     * 数据权限 分页
     *
     * @param page
     */
    IPage<BusinessHall> findPage(IPage<BusinessHall> page, BusinessHallPageDTO businessHallPageDTO);

    /**
     * 根据orgId查询营业厅
     * @param orgId
     * @return
     */
    BusinessHall queryByOrgId(Long orgId);
}
