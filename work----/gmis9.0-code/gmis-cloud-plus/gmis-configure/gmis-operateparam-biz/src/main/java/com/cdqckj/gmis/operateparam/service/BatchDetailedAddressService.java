package com.cdqckj.gmis.operateparam.service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.operateparam.dto.BatchDetailedAddressPageDTO;
import com.cdqckj.gmis.operateparam.dto.BatchDetailedAddressUpdateDTO;
import com.cdqckj.gmis.operateparam.entity.BatchDetailedAddress;

/**
 * <p>
 * 业务接口
 * 
 * </p>
 *
 * @author 王华侨
 * @date 2020-09-18
 */
public interface BatchDetailedAddressService extends SuperService<BatchDetailedAddress> {

    Boolean check(BatchDetailedAddressUpdateDTO communityUpdateDTO);
    IPage findPage(IPage<BatchDetailedAddress> page, BatchDetailedAddressPageDTO model);
}
