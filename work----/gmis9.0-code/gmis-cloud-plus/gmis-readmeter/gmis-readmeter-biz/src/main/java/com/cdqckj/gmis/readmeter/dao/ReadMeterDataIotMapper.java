package com.cdqckj.gmis.readmeter.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.readmeter.dto.ReadMeterDataIotPageDTO;
import com.cdqckj.gmis.readmeter.entity.ReadMeterDataIot;
import com.cdqckj.gmis.readmeter.vo.ReadMeterDataIotVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * 抄表数据
 * </p>
 *
 * @author gmis
 * @date 2020-07-13
 */
@Repository
public interface ReadMeterDataIotMapper extends SuperMapper<ReadMeterDataIot> {
    @Select("select * from cb_read_meter_data_iot ${ew.customSqlSegment}")
    List<ReadMeterDataIot> getMeterDataByCode(@Param(Constants.WRAPPER) Wrapper<ReadMeterDataIot> wrapper);

    /**
     * 查询上一次数据
     * @param gasMeterNumber
     * @param dataTime
     * @return
     */
    ReadMeterDataIot queryPreviousData(@Param("gasMeterCode")String gasMeterCode, @Param("gasMeterNumber") String gasMeterNumber,@Param("customerCode") String customerCode,
                                             @Param("dataTime") LocalDateTime dataTime);

    /**
     * 分页查询
     *
     * @param page   分页对象
     * @param params 查询参数
     * @return 分页列表数据
     */
    Page<ReadMeterDataIotVo> pageList(IPage<ReadMeterDataIotVo> page, @Param("params") ReadMeterDataIotPageDTO params);

    /**
     * 获取
     * @param gasMeterCode
     * @param customerCode
     * @return
     */
    List<ReadMeterDataIot> findSettleData(@Param("gasMeterCode") String gasMeterCode,@Param("customerCode") String customerCode);

    /**
     *  获取最新抄表数据-不区分dataType
     * @param gasMeterNumber 表身号
     * @param gasMeterCode 表身编号
     * @param customerCode 客户编号
     * @param dataTime 冻结时间
     * @return 最新抄表数据
     */
    ReadMeterDataIot getLatestData(@Param("gasMeterNumber")String gasMeterNumber, @Param("gasMeterCode")String gasMeterCode,
                                   @Param("customerCode")String customerCode, @Param("dataTime")LocalDateTime dataTime);

    /**
     * 根据欠费编号更新抄表数据
     * @param gasOweCode
     * @return
     */
    int updateByGasOweCode(@Param("gasOweCode")String gasOweCode);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/29 15:49
    * @remark 物联网表的气量
    */
    BigDecimal stsInternetGasMeterGas(@Param("stsSearchParam") StsSearchParam stsSearchParam, @Param("type") String type, @Param("dataScope") String dataScope);
}