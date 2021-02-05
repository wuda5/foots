package com.cdqckj.gmis.readmeter.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.readmeter.dto.ReadMeterDataPageDTO;
import com.cdqckj.gmis.readmeter.entity.ReadMeterData;
import com.cdqckj.gmis.readmeter.vo.GasMeterReadStsVo;
import com.cdqckj.gmis.statistics.vo.MeterPlanNowStsVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
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
public interface ReadMeterDataMapper extends SuperMapper<ReadMeterData> {
    @Select("select * from cb_read_meter_data ${ew.customSqlSegment}")
    List<ReadMeterData> getMeterDataByCode(@Param(Constants.WRAPPER) Wrapper<ReadMeterData> wrapper);

    IPage<ReadMeterData> pageReadMeterData(IPage<ReadMeterData> page, @Param("parmes") ReadMeterDataPageDTO parmes, @Param("codeList") List<String> codeList,@Param("orgIds") List<Long> orgIds);

    List<ReadMeterData> selectGasCode(@Param("codeList") List<String> codeList);

    IPage<MeterPlanNowStsVo> stsReadMeter(IPage<ReadMeterData> page,@Param("stsSearchParam") StsSearchParam stsSearchParam);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/7 14:02
    * @remark 统计抄表
    */
    GasMeterReadStsVo generalGasMeterReadSts(@Param("stsSearchParam") StsSearchParam stsSearchParam, @Param("dataScope") String dataScope);

    /**
     * 查询上一条数据
     * @param gasMeterCode
     * @param customerCode
     * @param dataTime
     * @return
     */
    ReadMeterData queryPreviousData(@Param("gasMeterCode") String gasMeterCode, @Param("customerCode") String customerCode,
                                       @Param("dataTime") LocalDate dataTime);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/21 13:36
    * @remark 总的抄表人员
    */
    Integer totalCbNum(@Param("stsSearchParam") StsSearchParam stsSearchParam);

    /**
     * @Author: lijiangguo
     * @Date: 2021/1/21 15:05
     * @remark 普表的用气量
     */
    BigDecimal stsGeneralGasMeter(@Param("stsSearchParam") StsSearchParam stsSearchParam, @Param("dataScope") String dataScope);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/28 16:56
    * @remark 统计
    */
    List<MeterPlanNowStsVo> stsReadMeterByMonth(@Param("resultPage") Page resultPage, @Param("readMeterDateStr") String readMeterDateStr, @Param("stsSearchParam") StsSearchParam stsSearchParam, @Param("dataScope") String dataScope);
}