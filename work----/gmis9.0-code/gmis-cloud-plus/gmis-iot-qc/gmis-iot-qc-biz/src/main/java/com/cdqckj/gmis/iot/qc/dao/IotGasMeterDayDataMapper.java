package com.cdqckj.gmis.iot.qc.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.iot.qc.entity.IotGasMeterDayData;
import com.cdqckj.gmis.iot.qc.vo.CommandEchoVO;
import com.cdqckj.gmis.iot.qc.vo.CommandVO;
import com.cdqckj.gmis.iot.qc.vo.DayDataParamsVO;
import com.cdqckj.gmis.iot.qc.vo.DayDataVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


/**
 * <p>
 * Mapper 接口
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-10-15
 */
@Repository
public interface IotGasMeterDayDataMapper extends SuperMapper<IotGasMeterDayData> {

    /**
     * 每日上报数据查询
     * @param page
     * @param params
     * @return
     */
    Page<DayDataVO> queryDayData(Page<DayDataVO> page, @Param("params") DayDataParamsVO params);
}
