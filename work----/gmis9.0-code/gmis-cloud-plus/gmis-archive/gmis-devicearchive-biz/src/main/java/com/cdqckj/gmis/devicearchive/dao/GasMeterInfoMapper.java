package com.cdqckj.gmis.devicearchive.dao;

import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.devicearchive.entity.GasMeterInfo;

import com.cdqckj.gmis.devicearchive.vo.MeterInfoVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * 气表使用情况
 * </p>
 *
 * @author 王华侨
 * @date 2020-09-02
 */
@Repository
public interface GasMeterInfoMapper extends SuperMapper<GasMeterInfo> {
    /**
     * 调价清零更新
     * @param meterInfoVO
     * @return
     */
    int updateByPriceId(@Param("meterInfoVO")MeterInfoVO meterInfoVO);

    /**
     * 周期清零
     * @param meterInfoVO
     * @return
     */
    int updateCycleByPriceId(@Param("meterInfoVO")MeterInfoVO meterInfoVO);
}
