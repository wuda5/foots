package com.cdqckj.gmis.readmeter.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.authority.entity.auth.User;
import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.readmeter.entity.ReadMeterDataIotError;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>
 * 业务接口
 * 抄表数据
 * </p>
 *
 * @author gmis
 * @date 2020-11-23
 */
public interface ReadMeterDataIotErrorService extends SuperService<ReadMeterDataIotError> {
    /**
     * 根据表身号查询
     * @param gasMeterNumber
     * @return
     */
    ReadMeterDataIotError queryByGasMeterNumber(String gasMeterNumber);

    /**
     *
     * @param page
     * @param wrapper
     * @return
     */
    IPage<ReadMeterDataIotError> findPage(IPage<ReadMeterDataIotError> page, LbqWrapper<ReadMeterDataIotError> wrapper);

    /**
     * 根据气表编号查询
     * @param meterNoList
     * @return
     */
    List<ReadMeterDataIotError> queryByMeterNo(List<String> meterNoList);

    /**
     * 批量更新
     * @param list
     * @return
     */
    Boolean updateBathMeter(List<ReadMeterDataIotError> list);
}
