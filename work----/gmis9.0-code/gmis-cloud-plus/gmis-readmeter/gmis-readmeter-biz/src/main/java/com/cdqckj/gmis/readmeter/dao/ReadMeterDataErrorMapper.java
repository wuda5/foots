package com.cdqckj.gmis.readmeter.dao;

import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.readmeter.entity.ReadMeterDataError;

import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * 抄表导入错误数据临时记录
 * </p>
 *
 * @author gmis
 * @date 2020-09-29
 */
@Repository
public interface ReadMeterDataErrorMapper extends SuperMapper<ReadMeterDataError> {

}
