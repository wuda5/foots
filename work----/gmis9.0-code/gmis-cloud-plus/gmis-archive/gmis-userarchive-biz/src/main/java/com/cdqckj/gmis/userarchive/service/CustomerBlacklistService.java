package com.cdqckj.gmis.userarchive.service;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.userarchive.entity.CustomerBlacklist;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 * 业务接口
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-07-14
 */
public interface CustomerBlacklistService extends SuperService<CustomerBlacklist> {
    CustomerBlacklist findBlacklistStatusByCustomerCode(@Param("customerCode") String customerCode);

    Boolean SetBlacklist(@RequestParam("customerCode") String customerCode);

    Boolean RemoveBlacklist(@RequestParam("customerCode")  String customerCode);
}
