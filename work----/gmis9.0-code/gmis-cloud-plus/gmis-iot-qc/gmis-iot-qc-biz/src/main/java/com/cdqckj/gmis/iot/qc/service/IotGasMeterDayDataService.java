package com.cdqckj.gmis.iot.qc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.iot.qc.entity.IotGasMeterDayData;
import com.cdqckj.gmis.iot.qc.vo.CommandEchoVO;
import com.cdqckj.gmis.iot.qc.vo.CommandVO;
import com.cdqckj.gmis.iot.qc.vo.DayDataParamsVO;
import com.cdqckj.gmis.iot.qc.vo.DayDataVO;

import java.time.LocalDateTime;


/**
 * <p>
 * 业务接口
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-10-15
 */
public interface IotGasMeterDayDataService extends SuperService<IotGasMeterDayData> {
    /**
     * 每日上报数据查询
     * @param params
     * @return
     */
    Page<DayDataVO> queryDayData(DayDataParamsVO params);
}
