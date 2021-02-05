package com.cdqckj.gmis.userarchive.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.userarchive.dto.CustomerDeviceDTO;
import com.cdqckj.gmis.userarchive.entity.PurchaseLimitCustomer;

import com.cdqckj.gmis.userarchive.vo.CustomerDeviceLimitVO;
import com.cdqckj.gmis.userarchive.vo.CustomerDeviceVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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
public interface PurchaseLimitCustomerMapper extends SuperMapper<PurchaseLimitCustomer> {
    /**
     * 查询客户设备限购列表
     * @param page
     * @return
     */
    Page<CustomerDeviceDTO> findLimitCustomerPage(Page<CustomerDeviceDTO> page, @Param("params") CustomerDeviceLimitVO params);

    /**
     * 根据限购id批量查询
     * @param limitIds
     * @return
     */
    List<PurchaseLimitCustomer> selectByIds(@Param("limitIds")List<Long> limitIds);
}
