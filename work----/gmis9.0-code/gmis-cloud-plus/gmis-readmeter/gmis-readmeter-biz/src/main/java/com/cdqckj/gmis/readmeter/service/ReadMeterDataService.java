package com.cdqckj.gmis.readmeter.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.readmeter.dto.ReadMeterDataPageDTO;
import com.cdqckj.gmis.readmeter.entity.ReadMeterData;
import com.cdqckj.gmis.readmeter.enumeration.ProcessEnum;
import com.cdqckj.gmis.readmeter.vo.GasMeterReadStsVo;
import com.cdqckj.gmis.statistics.vo.MeterPlanNowStsVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 业务接口
 * 抄表数据
 * </p>
 *
 * @author gmis
 * @date 2020-07-13
 */
public interface ReadMeterDataService extends SuperService<ReadMeterData> {
    R<List<ReadMeterData>> getMeterDataByCode(List<String> meterCodelist, List<Integer> yearlist, List<Integer> monthlist, ProcessEnum process);

    R<List<ReadMeterData>> getHistory(List<String> meterCodelist, LocalDate time);

    R<Boolean> deleteData(List<ReadMeterData> ids);

    IPage<ReadMeterData> pageReadMeterData(PageParams<ReadMeterDataPageDTO> params,List<String> codeList) throws Exception;

    R<List<ReadMeterData>> selectGasCode(@Param("codeList") List<java.lang.String> codeList);

    R<Boolean> deleteByGasCode(List<String> list);

    IPage<MeterPlanNowStsVo> stsReadMeter(@RequestBody StsSearchParam stsSearchParam);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/7 14:00
    * @remark 抄表数据的统计
    */
    GasMeterReadStsVo generalGasMeterReadSts(StsSearchParam stsSearchParam);

    /**
     * 查询上一条数据
     * @param gasMeterCode
     * @param customerCode
     * @param dataTime
     * @return
     */
    ReadMeterData getPreviousData(String gasMeterCode, String customerCode, LocalDate dataTime);
    /**
     * @Author: lijiangguo
     * @Date: 2021/1/21 15:04
     * @remark 统计抄表的用气量
     * @return
     */
    BigDecimal stsGeneralGasMeter(StsSearchParam stsSearchParam);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/28 16:41
    * @remark 请添加函数说明
    */
    List<MeterPlanNowStsVo> stsReadMeterByMonth(Page resultPage, List<String> readMeterDate, StsSearchParam stsSearchParam);
}
