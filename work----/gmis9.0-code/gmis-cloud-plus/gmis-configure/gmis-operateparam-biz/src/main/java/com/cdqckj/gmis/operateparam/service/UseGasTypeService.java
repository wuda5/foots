package com.cdqckj.gmis.operateparam.service;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.operateparam.entity.UseGasType;
import com.cdqckj.gmis.operateparam.vo.UseGasTypeVO;

/**
 * <p>
 * 业务接口
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-06-29
 */
public interface UseGasTypeService extends SuperService<UseGasType> {

    public R<UseGasType> getUseGasType(String useGasTypeName);

    /**
     * 查询最新一天用气类型数据
     * @return
     */
    UseGasType queryRentUseGasType();

    /**
     * 通过id查询用气类型及价格方案
     * @param id
     * @return
     */
    UseGasTypeVO queryUseGasTypeAndPrice(Long id);


}
