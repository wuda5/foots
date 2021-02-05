package com.cdqckj.gmis.devicearchive.service;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.devicearchive.entity.GasMeterInfo;
import com.cdqckj.gmis.devicearchive.vo.MeterInfoVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 业务接口
 * 气表使用情况
 * </p>
 *
 * @author 王华侨
 * @date 2020-09-02
 */
public interface GasMeterInfoService extends SuperService<GasMeterInfo> {
    /**
     * 根据气表编号获取
     * @param gascode
     * @return
     */
    GasMeterInfo getByGasMeterCode(String gascode);

    GasMeterInfo getByMeterAndCustomerCode(String gasMeterCode,String customerCode);

    /**
     * 调价清零更新
      * @param meterInfoVO
     * @return
     */
    R<Integer> updateByPriceId(MeterInfoVO meterInfoVO);

    /**
     * 周期清零
     * @param meterInfoVO
     * @return
     */
    R<Integer> updateCycleByPriceId(@Param("meterInfoVO")MeterInfoVO meterInfoVO);
}
