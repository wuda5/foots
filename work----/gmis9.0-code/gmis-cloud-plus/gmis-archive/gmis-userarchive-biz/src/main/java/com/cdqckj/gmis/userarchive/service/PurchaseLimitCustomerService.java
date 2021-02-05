package com.cdqckj.gmis.userarchive.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.userarchive.dto.CustomerDeviceDTO;
import com.cdqckj.gmis.userarchive.entity.PurchaseLimitCustomer;
import com.cdqckj.gmis.userarchive.vo.CustomerDeviceLimitVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

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
public interface PurchaseLimitCustomerService extends SuperService<PurchaseLimitCustomer> {
    /**
     * 查询客户设备限购列表
     * @param params
     * @return
     */
    Page<CustomerDeviceDTO> findLimitCustomerPage(CustomerDeviceLimitVO params);

    /**
     * 根据限购id查询
     * @param limitIds
     * @return
     */
    List<PurchaseLimitCustomer> selectByIds(List<Long> limitIds);
}
